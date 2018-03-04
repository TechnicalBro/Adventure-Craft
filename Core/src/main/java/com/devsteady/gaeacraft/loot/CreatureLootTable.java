package com.devsteady.gaeacraft.loot;

import com.caved_in.adventurecraft.core.gadget.*;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.commons.item.Attributes;
import com.caved_in.commons.item.ItemBuilder;
import com.devsteady.gaeacraft.gadget.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class CreatureLootTable {

    protected CreatureLootTable() {

    }


    public static LootSettings TIER_1_SWORD_SETTINGS = new LootSettingsBuilder().addNames(NameSlot.BASE,
            ChancedName.of(100, "Sword"),
            ChancedName.of(50, "Long Sword"),
            ChancedName.of(10, "Rapier")
    ).addNames(NameSlot.PREFIX,
            ChancedName.of(10, "Shining"),
            ChancedName.of(10, "Glistening"),
            ChancedName.of(20, "Noble"),
            ChancedName.of(20, "&bRighteous"),
            ChancedName.of(10, "Badass"),
            ChancedName.of(50, "Rusty"),
            ChancedName.of(50, "Cracked"),
            ChancedName.of(50, "Brittle"),
            ChancedName.of(10, "Military"),
            ChancedName.of(50, "Fallen"),
            ChancedName.of(10, "Shadowing"),
            ChancedName.of(5, "Malice")
    ).addNames(NameSlot.SUFFIX,
            ChancedName.of(10, "of Badassery"),
            ChancedName.of(10, "of Knights"),
            ChancedName.of(1, "of King Arthur"),
            ChancedName.of(5, "of Knights"),
            ChancedName.of(5, "of Other-Worldly Mojo"),
            ChancedName.of(10, "of Thievery"),
            ChancedName.of(5, "of the Bear"),
            ChancedName.of(15, "of Scrummaging"),
            ChancedName.of(10, "of Bards"),
            ChancedName.of(15, "of Summoners"),
            ChancedName.of(15, "of Warming"),
            ChancedName.of(1, "of the &cCerberus"),
            ChancedName.of(2, "of &bDamascus"),
            ChancedName.of(15, "of Enlightment"),
            ChancedName.of(15, "of Energy"),
            ChancedName.of(15, "of the Mind"),
            ChancedName.of(1, "of Pancake Sorcery"),
            ChancedName.of(20, "of the Tiger"),
            ChancedName.of(20, "of the Fox"),
            ChancedName.of(20, "of Substinence"),
            ChancedName.of(20, "of Ages"),
            ChancedName.of(20, "of the Leech"),
            ChancedName.of(20, "of the Lamprey"),
            ChancedName.of(20, "of Ennui"),
            ChancedName.of(20, "of Radiance")
    ).addEnchantment(new ChancedEnchantment(5, Enchantment.DAMAGE_ALL, 2))
            .addEnchantment(new ChancedEnchantment(20, Enchantment.DAMAGE_ALL, 1))
            .addEnchantment(new ChancedEnchantment(10, Enchantment.KNOCKBACK, 1))
            .addLoot(new ChancedItemData(15, Material.IRON_SWORD).damageRange(2.15, 5.6, 6.1, 10.8))
            .addLoot(new ChancedItemData(5, Material.GOLD_SWORD).damageRange(3.9, 6.7, 6.8, 9.5))
            .addLoot(ChancedItemData.of(25, Material.STONE_SWORD).damageRange(2.1, 4.24, 4.3, 6.15))
            .addLoot(ChancedItemData.of(100, Material.WOOD_SWORD).damageRange(2.1, 3.7, 4.1, 6.0))
            .addItemEffect(3, CriticalStrikeEffect.getInstance())
            .addItemEffect(1, BleedEffect.getInstance())
            .randomName(true)
            .build();


    public static LootSettings TIER_2_SWORD_SETTINGS = new LootSettingsBuilder().addNames(NameSlot.BASE,
            ChancedName.of(100, "Sword"),
            ChancedName.of(60, "Two Hander"),
            ChancedName.of(60, "Long Sword"),
            ChancedName.of(30, "Rapier"),
            ChancedName.of(25, "Sabre"),
            ChancedName.of(25, "Greatsword"),
            ChancedName.of(25, "Slicer"),
            ChancedName.of(25, "Doomblade"),
            ChancedName.of(10, "Champions Blade"),
            ChancedName.of(5, "Executioner"),
            ChancedName.of(19, "Slicer"),
            ChancedName.of(10, "Swift-Blade"),
            ChancedName.of(10, "Quick-Blade"),
            ChancedName.of(10, "Reaver"),
            ChancedName.of(10, "Carver"),
            ChancedName.of(25, "Warblade"),
            ChancedName.of(12, "Stormcaller"),
            ChancedName.of(30, "Katana"),
            ChancedName.of(10, "Shadow Steel"),
            ChancedName.of(25, "Scimitar"),
            ChancedName.of(35, "Dark Blade"),
            ChancedName.of(1, "Infamy")
    ).addNames(NameSlot.PREFIX,
            ChancedName.of(20, "Shining"),
            ChancedName.of(20, "Glistening"),
            ChancedName.of(15, "Noble"),
            ChancedName.of(20, "&bRighteous"),
            ChancedName.of(15, "Badass"),
            ChancedName.of(10, "Deadly"),
            ChancedName.of(5, "Malevolent"),
            ChancedName.of(5, "Dragon-Forged"),
            ChancedName.of(1, "God-Like"),
            ChancedName.of(20, "Chivalrous"),
            ChancedName.of(20, "Frozen"),
            ChancedName.of(20, "Fancy"),
            ChancedName.of(2, "King's"),
            ChancedName.of(2, "Queen's"),
            ChancedName.of(15, "Rusty"),
            ChancedName.of(40, "Tempered"),
            ChancedName.of(25, "Reincarnated"),
            ChancedName.of(15, "Hellish"),
            ChancedName.of(15, "Ghoulish"),
            ChancedName.of(15, "Screaming"),
            ChancedName.of(15, "Ghastly"),
            ChancedName.of(15, "Banished"),
            ChancedName.of(10, "Demonic"),
            ChancedName.of(20, "Skull-Crushing"),
            ChancedName.of(15, "Lonely"),
            ChancedName.of(15, "Exiled"),
            ChancedName.of(15, "Soul-Ful"),
            ChancedName.of(15, "Withering"),
            ChancedName.of(15, "Burnished"),
            ChancedName.of(15, "Eternal"),
            ChancedName.of(15, "Mangling"),
            ChancedName.of(15, "Ebony")
    ).addNames(NameSlot.SUFFIX,
            ChancedName.of(10, "of Badassery"),
            ChancedName.of(10, "of Knights"),
            ChancedName.of(3, "of King Arthur"),
            ChancedName.of(3, "of Kings"),
            ChancedName.of(8, "of Other-Worldly Mojo"),
            ChancedName.of(15, "of Thievery"),
            ChancedName.of(10, "of the Bear"),
            ChancedName.of(15, "of Scrummaging"),
            ChancedName.of(10, "of Bards"),
            ChancedName.of(15, "of Summoners"),
            ChancedName.of(15, "of Warming"),
            ChancedName.of(4, "of the &cCerberus"),
            ChancedName.of(2, "of &bDamascus"),
            ChancedName.of(15, "of Enlightment"),
            ChancedName.of(15, "of Energy"),
            ChancedName.of(15, "of the Mind"),
            ChancedName.of(4, "of Pancake Sorcery"),
            ChancedName.of(20, "of the Tiger"),
            ChancedName.of(20, "of the Fox"),
            ChancedName.of(20, "of Substinence"),
            ChancedName.of(20, "of Ages"),
            ChancedName.of(20, "of the Leech"),
            ChancedName.of(20, "of the Lamprey"),
            ChancedName.of(20, "of Ennui"),
            ChancedName.of(20, "of Radiance"),
            ChancedName.of(10, ", protector of the Wind"),
            ChancedName.of(10, "of Ending Misery"),
            ChancedName.of(10, "token of the End"),
            ChancedName.of(15, "of the Damned"),
            ChancedName.of(15, "of Oblivion"),
            ChancedName.of(15, "of the Covenant"),
            ChancedName.of(15, "of the Corrupted"),
            ChancedName.of(15, "of Seerculls"),
            ChancedName.of(15, "of Desecration"),
            ChancedName.of(15, "of Suffering")
    ).addEnchantment(new ChancedEnchantment(5, Enchantment.DAMAGE_ALL, 1, 3))
            .addEnchantment(new ChancedEnchantment(10, Enchantment.KNOCKBACK, 1))
            .addLoot(new ChancedItemData(24, Material.IRON_SWORD).damageRange(5.15, 6.6, 7.1, 13.8))
            .addLoot(new ChancedItemData(10, Material.GOLD_SWORD).damageRange(5.9, 8.7, 10.8, 14.5))
            .addLoot(ChancedItemData.of(35, Material.STONE_SWORD).damageRange(3.75, 6.24, 7.3, 11.15))
            .addLoot(ChancedItemData.of(70, Material.WOOD_SWORD).damageRange(3.1, 5.25, 5.9, 9.9))
            .addItemEffect(10, CriticalStrikeEffect.getInstance())
            .addItemEffect(7, BleedEffect.getInstance())
            .addItemEffect(4, PhaseEffect.getInstance())
            .addItemEffect(7, FlameStrikeEffect.getInstance())
            .addItemEffect(7, PoisonEffect.getInstance())
            .addItemEffect(10, BackstabEffect.getInstance())
            .displayDamage(true)
            .randomName(true)
            .build();

    public static LootSettings TIER_1_BOW_SETTINGS = new LootSettingsBuilder()
            .addLoot(ChancedItemData.of(100, Material.BOW).damageRange(2.9, 4.5, 4.6, 8.1))
            .addEnchantment(ChancedEnchantment.of(5, Enchantment.ARROW_DAMAGE, 1, 2))
            .addEnchantment(ChancedEnchantment.of(5, Enchantment.ARROW_KNOCKBACK, 1, 2))
            .build();

    public static LootSettings TIER_2_BOW_SETTINGS = new LootSettingsBuilder()
            .addLoot(ChancedItemData.of(100, Material.BOW).damageRange(3.9, 4.5, 6.6, 10.1))
            .addEnchantment(ChancedEnchantment.of(6, Enchantment.ARROW_DAMAGE, 2, 3))
            .addEnchantment(ChancedEnchantment.of(6, Enchantment.ARROW_KNOCKBACK, 1, 2))
            .build();


    public static LootTable TIER_1_ARROW_SETTINGS = new LootTable()
            .add(ChancedItemStack.of(
                    ItemBuilder.of(DocileArrowGadget.getInstance().getItem()).amount(4).item(), 5
            )).add(ChancedItemStack.of(
                            ExplosiveArrowGadget.getInstance().getItem(), 5
                    )
            ).add(ChancedItemStack.of(
                            ItemBuilder.of(SlowingArrowGadget.getInstance().getItem()).amount(3).item(), 5)

            );

    public static LootSettings TIER_1_ARMOR_SETTINGS = LootSettings.createBuilder()
            .addLoot(new ChancedItemData(5, Material.LEATHER_BOOTS))
            .addLoot(new ChancedItemData(10, Material.LEATHER_HELMET).attribute(
                    new RandomizedAttribute().type(Attributes.AttributeType.GENERIC_MAX_HEALTH).amountRange(1, 2).chance(6).addOperation(100, Attributes.Operation.ADD_NUMBER)
            ))
            .addLoot(new ChancedItemData(5, Material.LEATHER_CHESTPLATE))
            .addLoot(new ChancedItemData(5, Material.LEATHER_LEGGINGS))
            .addEnchantment(new ChancedEnchantment(5, Enchantment.DURABILITY, 1))
            .addEnchantment(new ChancedEnchantment(5, Enchantment.PROTECTION_ENVIRONMENTAL, 1))
            .addEnchantment(new ChancedEnchantment(2, Enchantment.THORNS, 1))
            .build().randomName(false);

    public static LootSettings TIER_2_ARMOR_SETTINGS = LootSettings.createBuilder()
            .addLoot(
                    new ChancedItemData(5, Material.IRON_HELMET).attribute(
                            new RandomizedAttribute().chance(30).type(Attributes.AttributeType.GENERIC_MAX_HEALTH).addOperation(100, Attributes.Operation.ADD_NUMBER).amountRange(1.0, 2.5).name("Health")
                    )
            ).addLoot(
                    new ChancedItemData(2, Material.IRON_HELMET).attribute(
                            new RandomizedAttribute().chance(20).type(Attributes.AttributeType.GENERIC_MAX_HEALTH).addOperation(100, Attributes.Operation.ADD_NUMBER).amountRange(3, 6).name("Health")
                    )).addLoot(
                    new ChancedItemData(5, Material.IRON_HELMET).attribute(
                            new RandomizedAttribute().chance(10).type(Attributes.AttributeType.GENERIC_MAX_HEALTH).addOperation(100, Attributes.Operation.ADD_NUMBER).amountRange(2, 4).name("Health")
                    ))
            .build().randomName(false);


    public static LootTable GADGET_LOOT_TABLE = new LootTable()
            /*
            Utility gadgets; Purpose / Fun.
             */
            .add(5, MonsterExamineGadget.getInstance().getItem())
            .add(2, PartyCrackerGadget.getInstance().getItem())
            /*
            Arrows with custom effects!
             */
            .add(6, GrapplingArrowGadget.getInstance().getItem())
            .add(6, DocileArrowGadget.getInstance().getItem())
            .add(6, ExplosiveArrowGadget.getInstance().getItem())
            .add(6, EnderArrowGadget.getInstance().getItem())
            .add(6, HealingArrow.getInstance().getItem())
            .add(6, KinArrowGadget.getInstance().getItem())
            .add(6, SlowingArrowGadget.getInstance().getItem())
            /*
            Utility Gadgets, used to aid gameplay.
             */
            .add(6, CoalFinder.getInstance().getItem())
            .add(6, GoldFinder.getInstance().getItem())
            .add(6, IronOreFinder.getInstance().getItem());

    public static LootTable WEAPON_LOOT_TABLE = new LootTable()
            .add(TIER_1_SWORD_SETTINGS)
            .add(TIER_2_SWORD_SETTINGS)
            .add(TIER_1_BOW_SETTINGS)
            .add(TIER_2_BOW_SETTINGS)
            .add(TIER_1_ARROW_SETTINGS);

    public static LootTable ARMOR_LOOT_TABLE = new LootTable()
            .add(TIER_1_ARMOR_SETTINGS)
            .add(TIER_2_ARMOR_SETTINGS);

    public static LootTable GLOBAL_LOOT_TABLE = new LootTable()
            .add(WEAPON_LOOT_TABLE)
            .add(ARMOR_LOOT_TABLE)
            .add(GADGET_LOOT_TABLE);

    public static LootTable SKELETON_SPECIFIC_LOOT = new LootTable()
            .add(GADGET_LOOT_TABLE)
            .add(WEAPON_LOOT_TABLE)
            .add(ARMOR_LOOT_TABLE);

    public static LootTable CREEPER_SPECIFIC_LOOT = new LootTable()
            .add(GADGET_LOOT_TABLE)
            .add(WEAPON_LOOT_TABLE)
            .add(ARMOR_LOOT_TABLE);

    public static LootTable ENDER_SPECIFIC_LOOT = new LootTable()
            .add(GADGET_LOOT_TABLE)
            .add(WEAPON_LOOT_TABLE)
            .add(ARMOR_LOOT_TABLE);

    public static LootTable NETHER_SPECIFIC_LOOT = new LootTable()
            .add(GADGET_LOOT_TABLE)
            .add(WEAPON_LOOT_TABLE)
            .add(ARMOR_LOOT_TABLE);

}
