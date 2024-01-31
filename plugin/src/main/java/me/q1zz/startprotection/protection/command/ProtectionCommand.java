package me.q1zz.startprotection.protection.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import lombok.RequiredArgsConstructor;
import me.q1zz.startprotection.configuration.PluginConfig;
import me.q1zz.startprotection.message.MessageAnnouncer;
import me.q1zz.startprotection.protection.Protection;
import me.q1zz.startprotection.protection.ProtectionService;
import org.bukkit.entity.Player;

@Command(name = "protection", aliases = {"start-protection"})
@RequiredArgsConstructor
public class ProtectionCommand {

    private final PluginConfig pluginConfig;
    private final MessageAnnouncer announcer;
    private final ProtectionService protectionService;

    @Execute(name = "toggle")
    private void protectionToggle(@Context Player player) {

        final Protection protection = this.protectionService.getProtection(player.getUniqueId());

        if(!protection.isActive()) {
            this.announcer.sendMessage(player, this.pluginConfig.getMessages().getProtectionNotActive());
            return;
        }

        protection.endProtection();

        this.protectionService.saveProtection(protection);
        this.announcer.sendMessage(player, this.pluginConfig.getMessages().getProtectionDisabled());
    }

}
