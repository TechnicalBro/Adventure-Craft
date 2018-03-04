package com.caved_in.adventurecraft.homes.event;

import com.caved_in.commons.warp.Warp;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerTeleportHomeEvent extends PlayerEvent implements Cancellable{
    private static HandlerList handlers = new HandlerList();
    private Warp home;
    private boolean cancelled;

    public PlayerTeleportHomeEvent(Player who, Warp home) {
        super(who);
        this.home = home;
    }

    public Warp getHome() {
        return home;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
