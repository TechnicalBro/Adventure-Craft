package com.caved_in.adventurecraft.fishing.config;

import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.utilities.ListUtils;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/*
Todo: IMPLEMENT FILE-BASED CONFIG
 */
public class AdventureFishingConfig {
    
    //TOdo Implement biome specific config
    private static Map<Biome, BiomeConfig> biomeLoot = new HashMap<>();
    
    private static List<FishingLoot> fishingLoot;
    
    static {
        fishingLoot = Lists.newArrayList(
                FishingLoot.create().chance(60).item(ItemBuilder.of(Material.POTATO_ITEM).name("&bSea Spuds").lore("Found in water, grown with love").item()),
                FishingLoot.create().chance(26).item(ItemBuilder.of(Material.BONE).item()),
                FishingLoot.create().chance(10).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383),95)).name("&fWolf Spawn Egg").lore("&7Little runt washed up on the shores").item()),
                FishingLoot.create().chance(5).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383),90)).name("&dPorker Spawn Egg").lore("&7This oinker was sitting with the fish").item()),
                FishingLoot.create().chance(5).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383),55)).name("&aSea Slime Egg").lore("&7Legendary slime of the sea!").item()),
                FishingLoot.create().chance(5).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383),100)).name("&eHorse Egg").lore("&7Yes, horses come from eggs &a:)").item()),
                FishingLoot.create().chance(5).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383),65)).name("&eBat Egg").lore("&7Flightly little flyer, he's all yours!").item()),
                FishingLoot.create().chance(14).item(ItemBuilder.of(Material.WOOD_SWORD).name("&eChipped Wood Sword of the Seas").durability((short)5).lore("&7Found at the bottom of the sea,","&7it's blessed by the oceans powers!").enchantment(Enchantment.LOOT_BONUS_MOBS,1).enchantment(Enchantment.KNOCKBACK,1).item()),
                FishingLoot.create().chance(10).item(ItemBuilder.of(Material.STONE_SWORD).name("&eCracked Stone Sword of Sands").durability((short)10).lore("&7Found at the bottom of the sea,","&7it's sharpened by the sands","&7and enriched by the waves!").enchantment(Enchantment.DURABILITY,1).enchantment(Enchantment.DAMAGE_ALL,2).item())

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
