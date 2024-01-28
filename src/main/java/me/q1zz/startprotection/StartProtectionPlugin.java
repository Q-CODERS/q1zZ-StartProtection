package me.q1zz.startprotection;

import me.q1zz.startprotection.message.MessageAnnouncer;
import me.q1zz.startprotection.util.legacy.LegacyColorProcessor;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class StartProtectionPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        final BukkitAudiences audiences = BukkitAudiences.create(this);
        final MiniMessage miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();
        final MessageAnnouncer messageAnnouncer = new MessageAnnouncer(audiences, miniMessage);

    }

    @Override
    public void onDisable() {

    }

}
