package com.caved_in.adventurecraft.homes.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerTeleportHomeEvent extends PlayerEvent {
    
    public PlayerTeleportHomeEvent(Player who) {
        super(who);
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
