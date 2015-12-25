package com.caved_in.adventurecraft.loot.command;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.menu.*;
import com.caved_in.commons.menu.menus.confirmation.ConfirmationMenu;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LootCommand {
    private String damageFormat = "&cDeals &e%s&c to &e%s&c damage!";

    private static HelpScreen help = Menus.generateHelpScreen("Loot Command Help", PageDisplay.DEFAULT, ItemFormat.SINGLE_DASH, ChatColor.YELLOW, ChatColor.GOLD)
            .addEntry("loot ? [page]", "Display a detailed menu of all the loot sub-commands and actions")
            .addEntry("loot help", "Display a short list of the loot command, and sub-commands")
            .addEntry("loot damage", "Add a specific damage range to the item in your hand");

    @Command(identifier = "loot", permissions = "adventurecraft.loot.admin")
    public void onLootCommand(Player player) {
        Chat.message(player, "&cComing Soon");
    }

    @Command(identifier = "loot ?",permissions = "adventurecraft.loot.admin")
    public void onLootHelpCommand(Player player, @Arg(name = "page",def = "1")int page) {
        help.sendTo(player,page,7);
    }

    @Command(identifier = "loot damage", permissions = "adventurecraft.loot.admin")
    public void onLootDamageCommand(Player player, @Arg(name = "min") double min, @Arg(name = "max") double max) {
        if (Players.handIsEmpty(player)) {
            Chat.message(player, Messages.ITEM_IN_HAND_REQUIRED);
            return;
        }

        ConfirmationMenu.of("Apply the damage range?")
                .exitOnClickOutside(false)
                .onConfirm((itemMenu, player1) -> {
                            Items.addLore(
                                    player1.getItemInHand(),
                                    "",
                                    Chat.format(damageFormat, NumberUtil.round(min, 2), NumberUtil.round(max, 2))
                            );

                            itemMenu.closeMenu(player1);
                            Chat.actionMessage(player, "&aDamage range added to the item in your hand");
                        }
                )
                .onDeny(ItemMenu::closeMenu)
                .openMenu(player);
    }
}
