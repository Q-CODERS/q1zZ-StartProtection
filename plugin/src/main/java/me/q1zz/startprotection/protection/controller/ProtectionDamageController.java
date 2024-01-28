package me.q1zz.startprotection.protection.controller;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ProtectionDamageController implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        if(event.isCancelled()) {
            return;
        }



    }

}
