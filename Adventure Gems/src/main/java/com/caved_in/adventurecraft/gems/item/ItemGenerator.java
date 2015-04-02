package com.caved_in.adventurecraft.gems.item;

import org.bukkit.inventory.ItemStack;

public interface ItemGenerator {
    public ItemStack createItem(GemSettings settings);
}
