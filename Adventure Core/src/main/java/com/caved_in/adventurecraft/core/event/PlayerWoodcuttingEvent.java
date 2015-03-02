package com.caved_in.adventurecraft.core.event;

import com.caved_in.adventurecraft.core.user.upgrades.UserUpgrade;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerWoodcuttingEvent extends PlayerEvent {
    private static HandlerList handlers = new HandlerList();

    private int playerLevel = 0;
    private Block block;
    private ItemStack axe;
    
    public PlayerWoodcuttingEvent(Player who) {
        super(who);
        
        
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public Block getBlock() {
        return block;
    }

    public ItemStack getAxe() {
        return axe;
    }
}
