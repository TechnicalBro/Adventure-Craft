package com.caved_in.adventurecraft.homes.command;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.adventurecraft.homes.HomeMessages;
import com.caved_in.adventurecraft.homes.users.HomePlayer;
import com.caved_in.adventurecraft.homes.users.PlayerHomeAction;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.warp.Warp;
import org.bukkit.entity.Player;

public class HomeCommands {
    @Command(identifier = "home",permissions = "home.command",description = "Open a menu to manage your homes!")
    public void onHomeCommand(Player player) {
        if (!AdventureHomes.API.openMenu(player)) {
            Chat.message(player,HomeMessages.NO_HOMES_SET);
        }
    }
    
    @Command(identifier = "sethome",permissions = "home.command",description = "Create a home for yourself, with a nice fancy name to be remembered by!")
    public void onSetHomeCommand(Player player,@Wildcard @Arg(name = "name",description = "The name you wish to give your home!")String name) {
        HomePlayer user = AdventureHomes.API.getUser(player);
        if (!user.canCreateHome()) {
            Chat.message(player, HomeMessages.homeLimitReached(user));
            return;
        }
        
        Warp warp = new Warp(name,player.getLocation());
        PlayerHomeAction action = user.addHome(warp);
        
        switch (action) {
            case ADDED:
                Chat.message(player, HomeMessages.homeAdded(warp));
                break;
            case DUPLICATE_NAME:
                Chat.message(player,HomeMessages.duplicateHomeName(name));
                break;
            case MAX_HOMES_REACHED:
                Chat.message(player,HomeMessages.homeLimitReached(user));
                break;
            case ERROR:
                Chat.message(player,"&cAn Error has occurred while setting your home, contact a staff member or fill out a bug report, please &a<3");
                break;
        }
    }
}
