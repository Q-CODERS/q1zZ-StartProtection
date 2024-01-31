package me.q1zz.startprotection.protection.controller;

import lombok.RequiredArgsConstructor;
import me.q1zz.startprotection.configuration.PluginConfig;
import me.q1zz.startprotection.message.MessageAnnouncer;
import me.q1zz.startprotection.protection.Protection;
import me.q1zz.startprotection.protection.ProtectionService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@RequiredArgsConstructor
public class ProtectionDamageController implements Listener {
    private final PluginConfig pluginConfig;
    private final MessageAnnouncer announcer;
    private final ProtectionService protectionService;

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        if(event.isCancelled()) {
            return;
        }

        if(!(event.getDamager() instanceof Player damager)) {
            return;
        }

        if(!(event.getEntity() instanceof Player player)) {
            return;
        }

        final Protection protection = this.protectionService.getProtection(player.getUniqueId());

        if(!protection.isActive()) {
            return;
        }

        event.setCancelled(true);

        this.announcer.sendMessage(damager, this.pluginConfig.getMessages().getPlayerHasProtection());
    }

}
