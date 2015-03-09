package com.caved_in.adventurecraft.fishing.loot;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.generator.data.ChancedEnchantment;
import com.caved_in.adventurecraft.loot.generator.data.ChancedItemData;
import com.caved_in.adventurecraft.loot.generator.data.LootTable;
import com.caved_in.adventurecraft.loot.generator.data.RandomizedAttribute;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.commons.item.Attributes;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class LootGeneratorSettings {

	private static LootTable lootTable = new LootTable()
			.add(
					LootSettings.create()
							.itemTable()
							.add(1, Material.DIAMOND)
							.add(1, Material.EMERALD)
							.add(1, Material.GOLD_INGOT)
							.add(15, Material.FEATHER)
							.add(3, Material.APPLE)
							.add(5, Material.ARROW)
							.add(15, Material.CLAY_BALL)
							.add(40, Material.DEAD_BUSH)
							.add(10, Material.COAL)
							.add(4, Material.BUCKET)
							.add(6, Material.COOKIE)
							.add(1, Material.EXP_BOTTLE)
							.add(10, Material.BONE)
							.add(20, Material.GRAVEL)
							.add(10, Material.COBBLESTONE)
							.add(10, Material.GOLD_NUGGET)
							.add(20, Material.EGG)
							.parent()
							.randomName(false)
							.lore().addLore("&bFound while Fishing!")
							.displayDamage(false)
							.parent()
			).add(
					LootSettings.createBuilder()
							.addLoot(new ChancedItemData(5, Material.LEATHER_BOOTS).attribute(
									new RandomizedAttribute().type(Attributes.AttributeType.GENERIC_MOVEMENT_SPEED).name("Speed").addOperation(100, Attributes.Operation.ADD_NUMBER).amountRange(1, 2).chance(100)
							))
							.addLoot(new ChancedItemData(10, Material.LEATHER_HELMET).attribute(
									new RandomizedAttribute().type(Attributes.AttributeType.GENERIC_MAX_HEALTH).amountRange(1, 2).chance(40).addOperation(100, Attributes.Operation.ADD_NUMBER)
							))
							.addLoot(new ChancedItemData(5, Material.LEATHER_CHESTPLATE))
							.addLoot(new ChancedItemData(5, Material.LEATHER_LEGGINGS))
							.addEnchantment(new ChancedEnchantment(5, Enchantment.DURABILITY, 1))
							.addEnchantment(new ChancedEnchantment(5, Enchantment.PROTECTION_ENVIRONMENTAL, 1))
							.addEnchantment(new ChancedEnchantment(2,Enchantment.THORNS,1))
							.build().randomName(false)
			);

	public static LootTable getLootTable() {
		return lootTable;
	}

}
