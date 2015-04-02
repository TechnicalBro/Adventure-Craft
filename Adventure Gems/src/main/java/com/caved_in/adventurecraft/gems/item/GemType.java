package com.caved_in.adventurecraft.gems.item;

import org.bukkit.Material;

public enum GemType {
    EMERALD(Material.EMERALD),
    DIAMOND(Material.DIAMOND);
    
    private Material type;
    GemType(Material type) {
        this.type = type;
    }

    public Material getType() {
        return type;
    }
}
