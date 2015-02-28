package com.caved_in.adventurecraft.core.listener;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.event.PlayerDamagePlayerEvent;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerGivePlayerFlowerListener implements Listener {
    @EventHandler
    public void onPlayerVersusPlayerEvent(PlayerDamagePlayerEvent e) {
        Player swinger = e.getPlayer();
        
        if (!Items.isFlower(swinger.getItemInHand())) {
            return;
        }

        Players.removeFromHand(swinger,1);

        ParticleEffects.sendToLocation(ParticleEffects.HEART,e.getTarget().getEyeLocation(), NumberUtil.getRandomInRange(7,10));
        
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEntityEvent e) {
        Entity clicked = e.getRightClicked();
        if (!(clicked instanceof Player)) {
            return;
        }
        
        Player target = (Player)clicked;
        Player player = e.getPlayer();
        
        if (!Items.isFlower(player.getItemInHand())) {
            return;
        }
        
        Players.removeFromHand(player,1);

        ParticleEffects.sendToLocation(ParticleEffects.HEART,target.getEyeLocation(), NumberUtil.getRandomInRange(7,10));
    }
}
