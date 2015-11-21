package com.caved_in.adventurecraft.loot.debug;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.generator.LootGenerator;
import com.caved_in.adventurecraft.loot.generator.data.*;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.menus.confirmation.ConfirmationMenu;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.UUID;

public class DebugLootTableSerialization implements DebugAction {
    private LootTable table = new LootTable();

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
    ).addEnchantment(new ChancedEnchantment(5, Enchantment.DAMAGE_ALL, 2))
            .addEnchantment(new ChancedEnchantment(20, Enchantment.DAMAGE_ALL, 1))
            .addEnchantment(new ChancedEnchantment(10, Enchantment.KNOCKBACK, 1))
            .addLoot(new ChancedItemData(20, Material.IRON_SWORD))
            .addLoot(new ChancedItemData(20, Material.GOLD_SWORD)).addLoot(ChancedItemData.of(70,Material.WOOD_SWORD)).build();

    private LootSettings TIER_1_BOW_SETTINGS = new LootSettingsBuilder()
            .addLoot(ChancedItemData.of(100, Material.BOW))
            .addEnchantment(ChancedEnchantment.of(20, Enchantment.ARROW_DAMAGE, 1))
            .addEnchantment(ChancedEnchantment.of(9, Enchantment.ARROW_DAMAGE, 2))
            .addEnchantment(ChancedEnchantment.of(1, Enchantment.ARROW_DAMAGE, 3))
            .addEnchantment(ChancedEnchantment.of(10, Enchantment.ARROW_FIRE, 1))
            .addEnchantment(ChancedEnchantment.of(25, Enchantment.ARROW_KNOCKBACK, 1))
            .build();

    public DebugLootTableSerialization() {
        table.add(TIER_1_BOW_SETTINGS)
                .add(new LootTable().add(TIER_1_SWORD_SETTINGS))
                .add(10,ItemBuilder.of(Material.CHAINMAIL_LEGGINGS).item())
                .add(1,ItemBuilder.of(Material.DIAMOND_SWORD).item());
    }

    @Override
    public void doAction(Player player, String... strings) {
        if (strings.length == 0) {
            Serializer serializer = new Persister();

            UUID id = UUID.randomUUID();
            File lootTableFile = new File(Commons.DEBUG_DATA_FOLDER + id.toString() + ".xml");
            try {
                serializer.write(lootTableFile,lootTableFile);
                Chat.message(player, "&aSaved loot-table to file &e" + lootTableFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                Chat.message(player,"&cFailed to save file " + lootTableFile.getAbsolutePath());
            }
        }

        String action = strings[0];

        switch (action) {
            case "additem":
                if (!Players.hasItemInHand(player)) {
                    Chat.message(player, Messages.DEBUG_ACTION_REQUIRES_HAND_ITEM);
                    return;
                }

                int chance = StringUtil.getNumberAt(strings,1,20);
                table.add(chance,player.getItemInHand());
                Chat.message(player,"Added item " + Items.getName(player.getItemInHand()),"-- With Chance: " + chance);
                //add chanced item in hand with argument for chance
                break;
            case "addtable":
                if (!Players.hasItemInHand(player)) {
                    Chat.message(player,Messages.DEBUG_ACTION_REQUIRES_HAND_ITEM);
                    return;
                }
                int tableChance = StringUtil.getNumberAt(strings,1,20);
                table.add(new LootTable().add(tableChance,player.getItemInHand()));
                Chat.message(player,"Added item table with " + Items.getName(player.getItemInHand()),"-- With Chance: " + tableChance);
                //add new loot table with only the item in their hand to the parent table.
                //todo implement building table by name and use hashmap for the bases
                break;
            case "generate":
                ItemStack item = AdventureLoot.API.createItem(table);
                if (item == null) {
                    while (item == null) {
                        item = AdventureLoot.API.createItem(table);
                    }
                }

                Chat.debug(Messages.itemInfo(item));
                Chat.actionMessage(player, "&aGenerated Item: &r" + Items.getName(item));
                Players.giveItem(player,item,true);
                break;
            default:
                Chat.message(player,"&eValid Arguments are: &7additem&r,&7addtable,&7generate&r, No arguments for serialization");

                ConfirmationMenu serializeConfirm = ConfirmationMenu.of("Serialize Item?")
                        .onConfirm((itemMenu, player1) -> {
                            Serializer serializer = new Persister();

                            UUID id = UUID.randomUUID();
                            File lootTableFile = new File(Commons.DEBUG_DATA_FOLDER + id.toString() + ".xml");
                            try {
                                serializer.write(table,lootTableFile);
                                Chat.message(player1, "&aSaved loot-table to file &e" + lootTableFile.getAbsolutePath());
                            } catch (Exception e) {
                                e.printStackTrace();
                                Chat.message(player1,"&cFailed to save file " + lootTableFile.getAbsolutePath());
                            }
                            itemMenu.closeMenu(player1);
                        }).onDeny(ItemMenu::closeMenu);

                serializeConfirm.openMenu(player);
                return;
        }
    }

    @Override
    public String getActionName() {
        return "loot_table_serialization";
    }
}
