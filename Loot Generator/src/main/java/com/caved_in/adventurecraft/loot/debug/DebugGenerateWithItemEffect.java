package com.caved_in.adventurecraft.loot.debug;

import com.caved_in.adventurecraft.adventureitems.AdventureItems;
import com.caved_in.adventurecraft.adventureitems.effects.CriticalStrikeEffect;
import com.caved_in.adventurecraft.adventureitems.effects.FlameStrikeEffect;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.generator.data.ChancedEnchantment;
import com.caved_in.adventurecraft.loot.generator.data.ChancedItemData;
import com.caved_in.adventurecraft.loot.generator.data.ChancedName;
import com.caved_in.adventurecraft.loot.generator.data.NameSlot;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugGenerateWithItemEffect implements DebugAction {

    private LootSettings TIER_1_SWORD_SETTINGS = new LootSettingsBuilder().addNames(NameSlot.BASE,
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
    )
            .addItemEffect(5, new FlameStrikeEffect())
            .addItemEffect(5, new CriticalStrikeEffect())
            .addEnchantment(new ChancedEnchantment(5, Enchantment.DAMAGE_ALL, 2, 2))
            .addEnchantment(new ChancedEnchantment(20, Enchantment.DAMAGE_ALL, 1, 2))
            .addEnchantment(new ChancedEnchantment(10, Enchantment.KNOCKBACK, 1, 2))
            .addLoot(new ChancedItemData(20, Material.IRON_SWORD).damageRange(3.1, 4.0, 4.5, 6.1))
            .addLoot(new ChancedItemData(20, Material.GOLD_SWORD).damageRange(3.5, 4.0, 5.5, 7.0)).addLoot(ChancedItemData.of(70, Material.WOOD_SWORD).damageRange(1.0, 2.0, 3.0, 3.5)).build();

    private LootSettings TIER_1_BOW_SETTINGS = new LootSettingsBuilder()
            .addLoot(ChancedItemData.of(100, Material.BOW).damageRange(4.5, 5.0, 10.6, 11.1))
            .addEnchantment(ChancedEnchantment.of(20, Enchantment.ARROW_DAMAGE, 1))
            .addEnchantment(ChancedEnchantment.of(9, Enchantment.ARROW_DAMAGE, 2))
            .addEnchantment(ChancedEnchantment.of(1, Enchantment.ARROW_DAMAGE, 3))
            .addEnchantment(ChancedEnchantment.of(10, Enchantment.ARROW_FIRE, 1))
            .addEnchantment(ChancedEnchantment.of(25, Enchantment.ARROW_KNOCKBACK, 1))
            .build();

    @Override
    public void doAction(Player player, String... strings) {
        ItemStack itemWithEffect = null;
        while (itemWithEffect == null || !AdventureItems.getInstance().getItemEffectHandler().hasEffect(itemWithEffect)) {
            itemWithEffect = AdventureLoot.API.createItem(TIER_1_SWORD_SETTINGS);
        }

        Players.giveItem(player, itemWithEffect, true);
        debug(Messages.itemInfo(itemWithEffect));
    }

    @Override
    public String getActionName() {
        return "generate_with_effect";
    }
}
