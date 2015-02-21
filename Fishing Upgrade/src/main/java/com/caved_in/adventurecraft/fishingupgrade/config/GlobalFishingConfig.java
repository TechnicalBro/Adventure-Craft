package com.caved_in.adventurecraft.fishingupgrade.config;

import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.utilities.ListUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/*
Todo: IMPLEMENT FILE-BASED CONFIG
 */
public class GlobalFishingConfig {
    
    //TOdo Implement biome specific config
    private static Map<Biome, BiomeConfig> biomeLoot = new HashMap<>();
    
    private static List<FishingLoot> fishingLoot;
    
    static {
        fishingLoot = Lists.newArrayList(
                FishingLoot.create().chance(40).item(ItemBuilder.of(Material.POTATO_ITEM).name("&bSea Spuds").lore("Found in water, grown with love").item()),
                FishingLoot.create().chance(40).item(ItemBuilder.of(Material.BONE).item())
        );
    }
    
    public static ItemStack getRandomLoot() {
        ItemStack loot = null;
        while (loot == null) {
            loot = ListUtils.getRandom(fishingLoot).spawn();
        }
        return loot;
    }
    
    public static ItemStack getRandomLoot(Biome biome) {
        return null;
    }
    
}
