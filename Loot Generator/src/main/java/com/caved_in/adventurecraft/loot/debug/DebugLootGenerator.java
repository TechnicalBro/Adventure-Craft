package com.caved_in.adventurecraft.loot.debug;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.generator.data.*;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.item.Attributes;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;


public class DebugLootGenerator implements DebugAction {

	private final LootSettings CHEAP_SWORD_SETTINGS = AdventureLoot.API.getSettingsBuilder()
			.addLoot(
					new ChancedItemData(10, Material.STONE_SWORD)
							.attribute(new RandomizedAttribute()
											.name("Attack")
											.addOperation(10, Attributes.Operation.ADD_PERCENTAGE)
											.addOperation(10, Attributes.Operation.ADD_NUMBER)
											.addOperation(10, Attributes.Operation.MULTIPLY_PERCENTAGE)
											.type(Attributes.AttributeType.GENERIC_ATTACK_DAMAGE)
										.amountRange(0.4,1.8)
							)
			).defaultLoot(new ChancedItemData(100, Material.WOOD_SWORD).attribute(
							new RandomizedAttribute().name("Attack")
									.addOperation(10, Attributes.Operation.ADD_PERCENTAGE)
									.addOperation(10, Attributes.Operation.ADD_NUMBER)
									.addOperation(10, Attributes.Operation.MULTIPLY_PERCENTAGE)
									.type(Attributes.AttributeType.GENERIC_ATTACK_DAMAGE)
									.amountRange(0.3,1.1)
					)
			).addNames(NameSlot.BASE,
					ChancedName.of(50, "Sword"),
					ChancedName.of(10, "Long Sword"),
					ChancedName.of(60, "Short Sword"),
					ChancedName.of(70, "Training Sword")
			).addNames(NameSlot.PREFIX,
					ChancedName.of(1, "&6&lKing Arthurs").prevent(NameSlot.SUFFIX),
					ChancedName.of(10, "&eRigid"),
					ChancedName.of(50, "&7Cracked"),
					ChancedName.of(20, "&bRighteous"),
					ChancedName.of(40, "Slightly Worn"),
					ChancedName.of(60, "Beginners").prevent(NameSlot.SUFFIX),
					ChancedName.of(1, "&aGaias"),
					ChancedName.of(30, "&bPosoidens"),ChancedName.of(5, "&c&oNether Forged")
			).addNames(NameSlot.SUFFIX,
					ChancedName.of(5, "of Knights"),
					ChancedName.of(5, "of Other-Worldly Mojo"),
					ChancedName.of(10, "of Thievery"),
					ChancedName.of(5, "of the Bear"),
					ChancedName.of(15, "of Scrummaging"),
					ChancedName.of(10, "of Bards")
			)
			.addLore("&eThis is a debug Item!!","&6Try again for a new combo!")
			.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.DAMAGE_ALL).level(1).chance(3))
			.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.DAMAGE_ALL).level(2).chance(4))
			.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.KNOCKBACK).level(2).chance(4))
			.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.KNOCKBACK).level(1).chance(2))
			.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.DURABILITY).level(1).chance(3))
			.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.LOOT_BONUS_MOBS).level(1).chance(3))
			/*Prefixes the itemTable can be given */
			/*Suffixes the itemTable can be given */
							.loreDisplayDamage(false).build();


	public void doAction(Player player, String... strings) {
		Players.giveItem(player,AdventureLoot.API.generateItem(CHEAP_SWORD_SETTINGS));
	}

	@Override
	public String getActionName() {
		return "generate_loot";
	}
}