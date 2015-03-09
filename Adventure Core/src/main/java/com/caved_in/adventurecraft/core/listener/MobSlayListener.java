package com.caved_in.adventurecraft.core.listener;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.generator.data.*;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.MobType;
import com.caved_in.commons.item.Attributes;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.Map;

public class MobSlayListener implements Listener {

	private final LootTable MOB_LOOT = new LootTable()
			.add(new LootSettingsBuilder().addLoot(
							new ChancedItemData(40, Material.STONE_SWORD)
									.attribute(new RandomizedAttribute()
													.name("Attack")
													.addOperation(10, Attributes.Operation.ADD_PERCENTAGE)
													.addOperation(50, Attributes.Operation.ADD_NUMBER)
													.addOperation(10, Attributes.Operation.MULTIPLY_PERCENTAGE)
													.type(Attributes.AttributeType.GENERIC_ATTACK_DAMAGE)
													.amountRange(1.0, 3.8)
									)
					).addLoot(new ChancedItemData(5, Material.DIAMOND_SWORD)
									.attribute(new RandomizedAttribute()
											.name("Attack").addOperation(30, Attributes.Operation.ADD_NUMBER)
											.type(Attributes.AttributeType.GENERIC_ATTACK_DAMAGE)
											.amountRange(2, 5))
					)
							.addLoot(new ChancedItemData(20, Material.IRON_SWORD))
							.addLoot(new ChancedItemData(15, Material.GOLD_SWORD))
							.defaultLoot(new ChancedItemData(100, Material.WOOD_SWORD).attribute(
											new RandomizedAttribute().name("Attack")
													.addOperation(10, Attributes.Operation.ADD_PERCENTAGE)
													.addOperation(40, Attributes.Operation.ADD_NUMBER)
													.addOperation(10, Attributes.Operation.MULTIPLY_PERCENTAGE)
													.type(Attributes.AttributeType.GENERIC_ATTACK_DAMAGE)
													.amountRange(1.0, 2.0)
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
									ChancedName.of(30, "&bPosoidens"), ChancedName.of(5, "&c&oNether Forged")
							).addNames(NameSlot.SUFFIX,
									ChancedName.of(5, "of Knights"),
									ChancedName.of(5, "of Other-Worldly Mojo"),
									ChancedName.of(10, "of Thievery"),
									ChancedName.of(5, "of the Bear"),
									ChancedName.of(15, "of Scrummaging"),
									ChancedName.of(10, "of Bards")
							)
							.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.DAMAGE_ALL).level(1).chance(3))
							.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.DAMAGE_ALL).level(2).chance(4))
							.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.KNOCKBACK).level(2).chance(4))
							.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.KNOCKBACK).level(1).chance(2))
							.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.DURABILITY).level(1).chance(3))
							.addEnchantment(new ChancedEnchantment().enchantment(Enchantment.LOOT_BONUS_MOBS).level(1).chance(3))
			/*Prefixes the itemTable can be given */
			/*Suffixes the itemTable can be given */
							.loreDisplayDamage(false).build().randomName(true)
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
							.addEnchantment(new ChancedEnchantment(2, Enchantment.THORNS, 1))
							.build().randomName(false)
			)
			.add(
					LootSettings.createBuilder()
							.addLoot(
									new ChancedItemData(5, Material.IRON_HELMET).attribute(
											new RandomizedAttribute().chance(30).type(Attributes.AttributeType.GENERIC_MAX_HEALTH).addOperation(100, Attributes.Operation.ADD_NUMBER).amountRange(2, 4).name("Health")
									)
							).addLoot(
							new ChancedItemData(2, Material.IRON_HELMET).attribute(
									new RandomizedAttribute().chance(60).type(Attributes.AttributeType.GENERIC_MAX_HEALTH).addOperation(100, Attributes.Operation.ADD_NUMBER).amountRange(3, 6).name("Health")
							)).addLoot(
							new ChancedItemData(5, Material.IRON_HELMET).attribute(
									new RandomizedAttribute().chance(30).type(Attributes.AttributeType.GENERIC_MAX_HEALTH).addOperation(100, Attributes.Operation.ADD_NUMBER).amountRange(2, 4).name("Health")
							))
							.build().randomName(false)
			);

	private Map<EntityType, Integer> mobLootChances = new HashMap<EntityType, Integer>() {{
		put(MobType.BLAZE.getEntityType(), 5);
		put(MobType.SPIDER.getEntityType(), 5);
		put(MobType.SKELETON.getEntityType(), 5);
		put(EntityType.CREEPER, 10);
		put(EntityType.CAVE_SPIDER, 5);
		put(EntityType.WITCH, 20);
		put(EntityType.ENDERMAN, 10);
		put(EntityType.WITHER, 100);
		put(EntityType.GHAST, 15);
		put(EntityType.ZOMBIE, 10);
		put(EntityType.PIG_ZOMBIE, 10);
		put(EntityType.SLIME, 5);
		put(EntityType.MAGMA_CUBE, 5);
		put(EntityType.SILVERFISH, 4);
		put(EntityType.GIANT, 10);
		put(EntityType.VILLAGER, 15);
	}};

	@EventHandler
	public void onPlayerKillMobEvent(EntityDeathEvent e) {
		LivingEntity entity = e.getEntity();

		if (!mobLootChances.containsKey(entity.getType())) {
			return;
		}

		int chance = mobLootChances.get(entity.getType());

		if (!NumberUtil.percentCheck(chance)) {
			return;
		}

		ItemStack item = AdventureLoot.API.generateItem(MOB_LOOT);
		Worlds.dropItem(entity,item,true);
		ParticleEffects.sendToLocation(ParticleEffects.SPELL_INSTANT,entity.getLocation(),NumberUtil.getRandomInRange(15,25
		));
	}
}
