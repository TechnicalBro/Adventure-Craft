package com.caved_in.adventurecraft.core.loot;

import com.caved_in.adventurecraft.core.gadget.DocileArrowGadget;
import com.caved_in.adventurecraft.core.gadget.ExplosiveArrowGadget;
import com.caved_in.adventurecraft.core.gadget.SlowingArrowGadget;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.generator.data.*;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.commons.item.Attributes;
import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class CreatureLootTable {

    private static LootSettings TIER_1_SWORD_SETTINGS = new LootSettingsBuilder().addNames(NameSlot.BASE,
            ChancedName.of(100, "Sword"),
            ChancedName.of(50, "Long Sword"),
            ChancedName.of(10, "Rapier")
    ).addNames(NameSlot.PREFIX,
            ChancedName.of(10, "Shining"),
            ChancedName.of(10, "Glistening"),
            ChancedName.of(20, "Noble"),
            ChancedName.of(20, "&bRighteous"),
            ChancedName.of(10, "Badass")
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
            .addLoot(new ChancedItemData(20, Material.IRON_SWORD))
            .addLoot(new ChancedItemData(20, Material.GOLD_SWORD)).addLoot(ChancedItemData.of(70,Material.WOOD_SWORD)).defaultLoot(Material.STONE_SWORD).build();

    private static LootSettings TIER_1_BOW_SETTINGS = new LootSettingsBuilder()
            .addLoot(ChancedItemData.of(100, Material.BOW))
            .addEnchantment(ChancedEnchantment.of(20, Enchantment.ARROW_DAMAGE, 1))
            .addEnchantment(ChancedEnchantment.of(9, Enchantment.ARROW_DAMAGE, 2))
            .addEnchantment(ChancedEnchantment.of(1, Enchantment.ARROW_DAMAGE, 3))
            .addEnchantment(ChancedEnchantment.of(10, Enchantment.ARROW_FIRE, 1))
            .addEnchantment(ChancedEnchantment.of(25, Enchantment.ARROW_KNOCKBACK, 1))
            .build();

    private static LootTable TIER_1_ARROW_SETTINGS = new LootTable()
            .add(ChancedItemStack.of(
                    ItemBuilder.of(DocileArrowGadget.getInstance().getItem()).amount(4).item(), 5
            )).add(ChancedItemStack.of(
                            ExplosiveArrowGadget.getInstance().getItem(), 5
                    )
            ).add(ChancedItemStack.of(
                            ItemBuilder.of(SlowingArrowGadget.getInstance().getItem()).amount(3).item(), 5)

            );
        /*
        private static LootTable TIER_1_ARMOR_SETTINGS = new LootTable()
                .add(

                        
                )*/

    private static LootTable CREEPER_LOOT_TABLE = new LootTable()
            .add(TIER_1_ARROW_SETTINGS)
            .add(TIER_1_SWORD_SETTINGS)
            .add(TIER_1_BOW_SETTINGS);
        


}
