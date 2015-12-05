package com.caved_in.adventurecraft.core.debug;

import com.caved_in.adventurecraft.core.loot.CreatureLootTable;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.generator.data.LootTable;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class DebugMobSlayLoot implements DebugAction {

    private LootTable LOOT_TABLE = new LootTable()
            .add(CreatureLootTable.WEAPON_LOOT_TABLE)
            .add(CreatureLootTable.ARMOR_LOOT_TABLE)
            .add(CreatureLootTable.GADGET_LOOT_TABLE);

    public DebugMobSlayLoot() {

    }

    @Override
    public void doAction(Player player, String... strings) {
        int amount = 10;
        String tableOption = null;

        if (strings.length > 0) {
            tableOption = strings[0];
            Chat.debug(tableOption);
            if (strings.length == 2) {
                Chat.debug(strings[1]);
            }
        }

        if (tableOption == null) {
            Chat.message(player, Messages.properUsage("/debug mob_loot_generation sword_1/sword_2/armor_1/armor_2/bow_1/bow_2"));
            return;
        }

        Chat.debug("Table Selection: " + tableOption);

        LootSettings settings = null;

        switch (tableOption) {
            case "sword_1":
                settings = CreatureLootTable.TIER_1_SWORD_SETTINGS;
                break;
            case "sword_2":
                settings = CreatureLootTable.TIER_2_SWORD_SETTINGS;
                break;
            case "armor_1":
                settings = CreatureLootTable.TIER_1_ARMOR_SETTINGS;
                break;
            case "armor_2":
                settings = CreatureLootTable.TIER_2_ARMOR_SETTINGS;
                break;
            case "bow_1":
                settings = CreatureLootTable.TIER_1_BOW_SETTINGS;
                break;
            case "bow_2":
                settings = CreatureLootTable.TIER_2_BOW_SETTINGS;
                break;
            default:
                settings = CreatureLootTable.TIER_2_SWORD_SETTINGS;
                break;
        }

        for (int i = 0; i < amount; i++) {
            Optional<ItemStack> item = Optional.empty();

            while (!item.isPresent()) {
                item = AdventureLoot.API.generateItem(settings);
            }

            Players.giveItem(player, item.get(), true);
        }
    }

    @Override
    public String getActionName() {
        return "mob_loot_generation";
    }
}
