package com.caved_in.adventurecraft.fishing.loot;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;

public class LootGeneratorSettings {

	private static final LootSettings CHEAP_SWORD_SETTINGS = AdventureLoot.API.getSettingsBuilder()
			.addBaseName("Sword")
			.addBaseName("Long Sword")
			.addBaseName("Short Sword")
			.addBaseName("Training Sword")
			/*Prefixes the itemTable can be given */
			.addPrefix("Broken")
			.addPrefix("Cracked")
			.addPrefix("Battered")
			.addPrefix("Blessed")
			.addPrefix("Mud Stained")
			.addPrefix("Slightly Curved")
			.addPrefix("Blood Covered")
			.addPrefix("Glistening")
			.addPrefix("Pristine")
			.addPrefix("Poorly Made")
			/*Suffixes the itemTable can be given */
			.addSuffix("of the Knight")
			.addSuffix("of Seas")
			.addSuffix("of Fools")
			.addSuffix("of Hardship")
			.addSuffix("of Thieves")
			.addSuffix("of Noblemen")
			.addSuffix("of Haters")
			.addSuffix("for Horses")
			.addSuffix("")
			.addSuffix("")
			.addSuffix("")
			.addSuffix("")
			.loreDisplayDamage(false)
			.build();

	private static final LootSettings BROKEN_AXE_SETTINGS = new LootSettingsBuilder()
			.addPrefix("Broken")
			.addPrefix("Worn")
			.addPrefix("Battered")
			.addPrefix("Cracked")
			.addPrefix("Well Seasoned")
			.addPrefix("")
			.addBaseName("Foresters Axe")
			.addBaseName("Woodsman Axe")
			.addBaseName("Axe")
			.addSuffix("of the Ages")
			.addSuffix("of Masonry")
			.addSuffix("of Timber")
			.addSuffix("","","","","","","")
			.loreDisplayDamage(false)
			.build();



	public static LootSettings getCheapSword() {
		if (NumberUtil.percentCheck(10)) {
			return CHEAP_SWORD_SETTINGS.itemTable().material(Material.STONE_SWORD).parent();
		} else {
			return CHEAP_SWORD_SETTINGS.itemTable().material(Material.WOOD_SWORD).parent();
		}
	}


	public static LootSettings getBrokenAxeSettings() {
		if (NumberUtil.percentCheck(10)) {
			return BROKEN_AXE_SETTINGS.itemTable().material(Material.STONE_AXE).parent();
		} else {
			return BROKEN_AXE_SETTINGS.itemTable().material(Material.WOOD_AXE).parent();
		}
	}

}
