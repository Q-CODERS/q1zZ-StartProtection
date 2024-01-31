package me.q1zz.startprotection.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import lombok.AllArgsConstructor;
import me.q1zz.startprotection.configuration.PluginConfig;
import me.q1zz.startprotection.message.MessageAnnouncer;
import org.bukkit.command.CommandSender;

import java.util.Map;

@AllArgsConstructor
public class InvalidUsageHandler implements dev.rollczi.litecommands.invalidusage.InvalidUsageHandler<CommandSender> {
    private final PluginConfig pluginConfig;
    private final MessageAnnouncer announcer;

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {

        final CommandSender sender = invocation.sender();
        final Schematic schematic = result.getSchematic();

        if(schematic.isOnlyFirst()) {
            this.announcer.sendMessage(sender, this.pluginConfig.getMessages().getCorrectUsage(), Map.of("{USAGE}", schematic.first()));
            return;
        }

        for (String usage : schematic.all()) {
            this.announcer.sendMessage(sender, this.pluginConfig.getMessages().getCorrectUsageEntry(), Map.of("{USAGE}", usage));
        }

    }

}
