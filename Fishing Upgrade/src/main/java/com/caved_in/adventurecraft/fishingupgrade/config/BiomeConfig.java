package com.caved_in.adventurecraft.fishingupgrade.config;

import org.bukkit.block.Biome;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "biome")
public class BiomeConfig {
    
    private Biome type;

    private List<FishingLoot> biomeLoot = new ArrayList<>();
    
    public BiomeConfig() {

    }
    
    public BiomeConfig addLoot(FishingLoot loot) {
        
        
    }
}
