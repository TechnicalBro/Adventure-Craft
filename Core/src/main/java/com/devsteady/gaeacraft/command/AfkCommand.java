package com.devsteady.gaeacraft.command;

import com.caved_in.commons.Commons;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.menus.confirmation.ConfirmationMenu;
import org.bukkit.entity.Player;

public class AfkCommand {
    @Command(identifier = "afk", description = "Go afk! You won't take damage!", onlyPlayers = true)
    public void onAfkCommand(Player p) {
        ConfirmationMenu.of("Return from AFK?").onOpen((itemMenu, player) -> {
            Commons.getInstance().getPlayerHandler().getData(player).setGodMode(true);
        }).onClose((itemMenu, player) -> {
            Commons.getInstance().getPlayerHandler().getData(player).setGodMode(false);
        }).onConfirm(ItemMenu::closeMenu).exitOnClickOutside(false);
    }
}
