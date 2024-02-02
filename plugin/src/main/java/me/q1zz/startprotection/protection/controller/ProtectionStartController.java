package me.q1zz.startprotection.protection.controller;

import lombok.RequiredArgsConstructor;
import me.q1zz.startprotection.configuration.PluginConfig;
import me.q1zz.startprotection.message.MessageAnnouncer;
import me.q1zz.startprotection.protection.Protection;
import me.q1zz.startprotection.protection.ProtectionService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
public class ProtectionStartController implements Listener {

    private final PluginConfig pluginConfig;
    private final MessageAnnouncer announcer;
    private final ProtectionService protectionService;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        final Player player = event.getPlayer();
        final UUID playerId = player.getUniqueId();

        final Protection protection = this.protectionService.getProtection(playerId);

        if(protection.hasBeenActivated()) {
            return;
        }

        final Duration protectionTime = this.pluginConfig.getProtection().getTime();

        protection.startProtection(protectionTime);

        this.protectionService.saveProtection(protection);
        this.announcer.sendMessage(player, this.pluginConfig.getMessages().getProtectionStart());
    }

}
