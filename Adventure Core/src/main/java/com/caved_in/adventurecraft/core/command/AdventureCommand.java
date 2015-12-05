package com.caved_in.adventurecraft.core.command;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.menu.ItemFormat;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.menu.PageDisplay;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdventureCommand {

    private static HelpScreen help = Menus.generateHelpScreen("Adventure Command Help", PageDisplay.SHORTHAND, ItemFormat.SINGLE_DASH, ChatColor.YELLOW, ChatColor.GOLD)
            .addEntry("adventure settings", "Used to manage all the settings modifiable in Vortechs")
            .addEntry("adventure settings droprate rate(double)", "Used to modify the global drop rate multiplier");

    @Command(identifier = "adventure", permissions = "vortechs.admin")
    public void onAdventureCommand(Player player) {
        help.sendTo(player,1,7);
    }

    @Command(identifier = "adventure settings",permissions = "vortechs.admin")
    public void onAdventureSettingsCommand(Player player, @Arg(name = "setting")String setting, @Arg(name = "value")String value) {

        switch (setting.toLowerCase()) {
            case "droprate":
                if (!StringUtils.isNumeric(value)) {
                    Chat.message(player, Messages.invalidCommandUsage("adventure","settings","droprate (integer)"));
                    return;
                }

                double dropMultiplier = Double.parseDouble(value);
                AdventureCore.Properties.DROP_MULTIPLIER = dropMultiplier;
                Chat.format(player,"&7[&cVortechs&7]&6 Drop Multiplier has been assigned to &e%s",dropMultiplier);
                break;
            default:
                help.sendTo(player,1,7);
                break;
        }
    }

}
