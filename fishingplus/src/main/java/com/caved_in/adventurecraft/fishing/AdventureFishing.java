package com.caved_in.adventurecraft.fishing;

import com.caved_in.adventurecraft.fishing.config.BiomeConfig;
import com.caved_in.adventurecraft.fishing.config.FishingLoot;
import com.caved_in.adventurecraft.fishing.listeners.FishingListener;
import com.caved_in.adventurecraft.fishing.loot.LootGeneratorSettings;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.generator.LootGenerator;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.ListUtils;
import com.caved_in.commons.utilities.NumberUtil;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.Option;
import java.util.*;

public class AdventureFishing extends BukkitPlugin {
	@Override
	public void startup() {

		registerListeners(new FishingListener());

		getThreadManager().registerSyncRepeatTask("",() -> {
			Config.createLootTable();
			debug("REGENERATED THE LOOT TABLES!");
		}, 0,TimeHandler.getTimeInTicks(10, TimeType.MINUTE));
	}

	@Override
	public void shutdown() {

	}

	@Override
	public String getAuthor() {
		return "Brandon Curtis";
	}

	@Override
	public void initConfig() {

	}

	public static class Settings {
		public static final int GENERATED_LOOT_CHANCE = 3;
	}

	public static class Config {
		//TOdo Implement biome specific config
		private static Map<Biome, BiomeConfig> biomeLoot = new HashMap<>();

		private static List<FishingLoot> fishingLoot = new ArrayList<>();

		public static void createLootTable() {
			fishingLoot.clear();

			Collections.addAll(fishingLoot,
					FishingLoot.create().chance(60).item(ItemBuilder.of(Material.POTATO_ITEM).name("&bSea Spuds").lore("Found in water, grown with love").item()),
					FishingLoot.create().chance(26).item(ItemBuilder.of(Material.BONE).item()),
				/* Mob spawn eggs */
					FishingLoot.create().chance(10).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383), 95)).name("&fWolf Spawn Egg").lore("&7Little runt washed up on the shores").item()),
					FishingLoot.create().chance(5).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383), 90)).name("&dPorker Spawn Egg").lore("&7This oinker was sitting with the fish").item()),
					FishingLoot.create().chance(5).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383), 55)).name("&aSea Slime Egg").lore("&7Legendary slime of the sea!").item()),
					FishingLoot.create().chance(5).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383), 100)).name("&eHorse Egg").lore("&7Yes, horses come from eggs &a:)").item()),
					FishingLoot.create().chance(5).item(ItemBuilder.of(Items.makeItem(Material.getMaterial(383), 65)).name("&eBat Egg").lore("&7Flightly little flyer, he's all yours!").item())
			);
		}

		public static Optional<ItemStack> getRandomLoot() {
			Optional<ItemStack> loot = null;
			if (NumberUtil.percentCheck(AdventureFishing.Settings.GENERATED_LOOT_CHANCE)) {
				loot = AdventureLoot.API.generateItem(LootGeneratorSettings.getLootTable());
			} else {
				while (loot == null) {
					loot = Optional.of(ListUtils.getRandom(fishingLoot).spawn());
				}
			}
			return loot;
		}

		public static ItemStack getRandomLoot(Biome biome) {
			return null;
		}

	}
}
