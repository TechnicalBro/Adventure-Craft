package com.caved_in.adventurecraft.core.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerShootSelfListener implements Listener {

    private static PlayerShootSelfListener instance = null;

    public static PlayerShootSelfListener getInstance() {
        if (instance == null) {
            instance = new PlayerShootSelfListener();
        }

        return instance;
    }

    protected PlayerShootSelfListener() {

    }
    
    private Set<UUID> fallDamagers = new HashSet<>();
    
    @EventHandler
    public void onPlayerDamageEvent(EntityDamageByEntityEvent e) {
        Entity damaged = e.getEntity();
        Entity damager = e.getDamager();
        
        if (e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (fallDamagers.contains(damaged.getUniqueId())) {
                    e.setCancelled(true);
                    fallDamagers.remove(damaged.getUniqueId());
                    return;
                }
            }
            
            return;
        }
        
        if (!(damager instanceof Arrow)) {
            return;
        }
        
        Arrow arrow = (Arrow)damager;


        ProjectileSource source = arrow.getShooter();
        Player shooter = null;
        
        if (!(source instanceof Player)) {
            return;
        }
        
        shooter = (Player)source;
        
        if (damaged.getUniqueId() != shooter.getUniqueId()) {
            return;
        }
        
        fallDamagers.add(damaged.getUniqueId());
    }
}
