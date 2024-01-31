package me.q1zz.startprotection;

import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import me.q1zz.startprotection.command.handler.InvalidUsageHandler;
import me.q1zz.startprotection.command.handler.MessageHandler;
import me.q1zz.startprotection.configuration.PluginConfig;
import me.q1zz.startprotection.database.DatabaseManager;
import me.q1zz.startprotection.message.Message;
import me.q1zz.startprotection.message.MessageAnnouncer;
import me.q1zz.startprotection.message.serializer.MessageSerializer;
import me.q1zz.startprotection.protection.ProtectionRepository;
import me.q1zz.startprotection.protection.ProtectionService;
import me.q1zz.startprotection.protection.ProtectionTask;
import me.q1zz.startprotection.protection.command.ProtectionCommand;
import me.q1zz.startprotection.protection.controller.ProtectionCacheController;
import me.q1zz.startprotection.protection.controller.ProtectionDamageController;
import me.q1zz.startprotection.protection.controller.ProtectionStartController;
import me.q1zz.startprotection.util.legacy.LegacyColorProcessor;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class StartProtectionPlugin extends JavaPlugin {

    private PluginConfig pluginConfig;
    private DatabaseManager databaseManager;
    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {

        final Stopwatch stopwatch = Stopwatch.createStarted();
        final File dataFolder = this.getDataFolder();

        this.initConfigs(dataFolder);

        this.databaseManager = new DatabaseManager(this.pluginConfig, dataFolder);
        this.databaseManager.connect();

        final DocumentPersistence persistence = this.databaseManager.persistence();
        final PersistenceCollection protectionCollection = PersistenceCollection.of(ProtectionRepository.class);

        persistence.registerCollection(protectionCollection);

        final BukkitAudiences audiences = BukkitAudiences.create(this);
        final MiniMessage miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();
        final MessageAnnouncer messageAnnouncer = new MessageAnnouncer(audiences, miniMessage);

        final Server server = this.getServer();
        final PluginManager pluginManager = server.getPluginManager();

        final ProtectionRepository protectionRepository = RepositoryDeclaration.of(ProtectionRepository.class)
                .newProxy(persistence, protectionCollection, this.getClassLoader());
        final ProtectionService protectionService = new ProtectionService(protectionRepository, this.pluginConfig);
        final ProtectionTask protectionTask = new ProtectionTask(this.pluginConfig, messageAnnouncer, protectionService);

        protectionTask.runTaskTimerAsynchronously(this, 20L, 20L);

        Stream.of(
                new ProtectionStartController(this.pluginConfig, messageAnnouncer, protectionService),
                new ProtectionCacheController(this.pluginConfig, protectionService),
                new ProtectionDamageController(this.pluginConfig, messageAnnouncer, protectionService)
        ).forEach(listener -> pluginManager.registerEvents(listener, this));

        this.liteCommands = LiteBukkitFactory.builder()

                // Default messages
                .message(LiteBukkitMessages.PLAYER_ONLY, input -> "Command only for players!")

                // Handlers
                .result(Message.class, new MessageHandler(messageAnnouncer))

                // Default handlers
                .invalidUsage(new InvalidUsageHandler(this.pluginConfig, messageAnnouncer))

                // Commands
                .commands(LiteCommandsAnnotations.of(
                        new ProtectionCommand(this.pluginConfig, messageAnnouncer, protectionService)
                ))

                // Building
                .build();

        this.getLogger().info("Successfully started in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms!");
    }

    @Override
    public void onDisable() {
        if(this.databaseManager != null) this.databaseManager.close();
        if(this.liteCommands != null) this.liteCommands.unregister();
    }

    private void initConfigs(@NotNull File parentFolder) {
        this.pluginConfig = ConfigManager.create(PluginConfig.class, (config) -> {
           config.withConfigurer(new YamlBukkitConfigurer(), new SerdesCommons(), registry -> {
             registry.register(new MessageSerializer());
           });
           config.withBindFile(new File(parentFolder, "configuration.yml"));
           config.withRemoveOrphans(true);
           config.saveDefaults();
           config.load(true);
        });
    }

}
