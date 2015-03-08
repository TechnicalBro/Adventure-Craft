package com.caved_in.adventurecraft.loot.generator.settings;

import com.caved_in.adventurecraft.loot.generator.data.ChancedEnchantment;
import com.caved_in.adventurecraft.loot.generator.data.ChancedItemData;
import com.caved_in.adventurecraft.loot.generator.data.ChancedName;
import com.caved_in.adventurecraft.loot.generator.data.NameSlot;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import java.util.stream.Stream;

public class LootSettingsBuilder {

	private LootSettings settings;

	public LootSettingsBuilder() {
		settings = new LootSettings();
	}

	public LootSettingsBuilder defaultLoot(ChancedItemData data) {
		settings.itemTable().defaultMaterial(data);
		return this;
	}

	public LootSettingsBuilder addLoot(ChancedItemData itemData) {
		settings.itemTable().add(itemData);
		return this;
	}

	public LootSettingsBuilder loreDisplayDamage(boolean show) {
		settings.lore().displayDamage(show);
		return this;
	}

	public LootSettingsBuilder addLore(String... lines) {
		settings.lore().addLore(lines);
		return this;
	}

	public LootSettingsBuilder loreDamageFormat(String format) {
		settings.lore().displayDamageFormat(format);
		return this;
	}

	public LootSettingsBuilder addNames(NameSlot slot, ChancedName... names) {
		switch (slot) {
			case PREFIX:
				settings.prefixes().add(names);
				break;
			case BASE:
				settings.baseNames().add(names);
				break;
			case SUFFIX:
				settings.suffixes().add(names);
				break;
			default:
				break;
		}

		return this;
	}

	public LootSettingsBuilder addEnchantment(ChancedEnchantment enchantment) {
		settings.enchantments().add(enchantment);
		return this;
	}

	public LootSettings build() {
		return settings;
	}
}
