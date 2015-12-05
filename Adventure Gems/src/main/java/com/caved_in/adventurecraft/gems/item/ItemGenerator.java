package com.caved_in.adventurecraft.gems.item;

import org.bukkit.inventory.ItemStack;

public interface ItemGenerator {
    ItemStack createItem(GemSettings settings);
}
