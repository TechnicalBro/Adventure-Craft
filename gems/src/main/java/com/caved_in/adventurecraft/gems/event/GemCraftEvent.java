package com.caved_in.adventurecraft.gems.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class GemCraftEvent extends Event{
    private static HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    private ItemStack gem;
    private ItemStack target;
    private Player player;
    
    public GemCraftEvent(Player who, ItemStack gem, ItemStack target) {
        this.player = who;
        this.gem = gem;
        this.target = target;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public ItemStack getGem() {
        return gem;
    }

    public ItemStack getTarget() {
        return target;
    }
    
    
}
