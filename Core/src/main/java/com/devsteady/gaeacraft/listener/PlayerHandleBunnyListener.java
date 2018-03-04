package com.devsteady.gaeacraft.listener;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.sound.Sounds;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerHandleBunnyListener implements Listener {

    private static PlayerHandleBunnyListener instance = null;

    public static PlayerHandleBunnyListener getInstance() {
        if (instance == null) {
            instance = new PlayerHandleBunnyListener();
        }
        return instance;
    }

    protected PlayerHandleBunnyListener() {

    }
    
    @EventHandler
    public void onPlayerInteractBunny(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity clicked = e.getRightClicked();
        
        if (clicked.getType() != EntityType.RABBIT) {
            return;
        }
        
        Rabbit rabbit = (Rabbit)clicked;
        
        if (Entities.hasPassenger(p)) {
            p.eject();
            Sounds.playSound(p, Sound.HORSE_SADDLE,1.0f,0.8f);
            Chat.actionMessage(p,"&6You take the rabbit off your head");
            return;
        }
        
        p.setPassenger(rabbit);
        Chat.actionMessage(p,"&aYou've put a rabbit on your head!");
        Sounds.playSound(p, Sound.HORSE_SADDLE);
    }
    
    @EventHandler
    public void onPlayerShiftRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        
        if (!Entities.hasPassenger(p)) {
            return;
        }
        
        if (!p.isSneaking()) {
            return;
        }
        
        switch (action) {
            case RIGHT_CLICK_BLOCK:
            case RIGHT_CLICK_AIR:
                p.eject();
                break;
        }
    }
}
