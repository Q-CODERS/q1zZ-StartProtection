package me.q1zz.startprotection.protection.controller;

import lombok.RequiredArgsConstructor;
import me.q1zz.startprotection.configuration.PluginConfig;
import me.q1zz.startprotection.protection.ProtectionService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@RequiredArgsConstructor
public class ProtectionCacheController implements Listener {

    private final PluginConfig pluginConfig;
    private final ProtectionService protectionService;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if(!this.pluginConfig.getCache().isRemoveWhenLeave()) {
            return;
        }

        final Player player = event.getPlayer();
        final UUID playerId = player.getUniqueId();

        this.protectionService.removeFromCache(playerId);
    }

}
