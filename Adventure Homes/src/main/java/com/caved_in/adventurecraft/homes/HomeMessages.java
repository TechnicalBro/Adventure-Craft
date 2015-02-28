package com.caved_in.adventurecraft.homes;

import com.caved_in.adventurecraft.homes.users.HomePlayer;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.warp.Warp;
import org.bukkit.entity.Player;

public class HomeMessages {
    public static final String NO_HOMES_SET = "&eYou have no homes set; Create a home using &a/sethome <name>";
    
    public static String homeDeleted(Warp warp) {
        return String.format("&eYour Home '&a%s&e' was &cdeleted.",warp.getName());
    }
    
    public static String homeDeletedError(Warp warp) {
        return String.format("&cERROR: &e%s&c was not deleted!",warp.getName());
    }

    public static String homeAdded(Warp warp) {
        return String.format("&eYou've made '&a%s&e' one of your home(s)!\n&7Access your homes by using &b/home",warp.getName());
    }
    
    public static String duplicateHomeName(String name) {
        return String.format("&cYou've already got a home named '&e%s&c'",name);
        
    }
    
    public static String homeNonExistant(Warp warp) {
        return homeNonExistant(warp.getName());
    }

    public static String homeNonExistant(String name) {
        return String.format("&eYou don't own a home named '&c%s&e'", name);
    }
    
    public static String homeLimitReached(Player player) {
        return homeLimitReached(AdventureHomes.API.getUser(player));
    }
    
    public static String homeLimitReached(HomePlayer player) {
        String message = String.format("&eYou've reached a maximum of &c%s&e homes.",player.getMaxHomes());
        if (!Players.isPremium(player.getId())) {
            return message + "\n&7By donating and supporting Tunnels you'll receive a rank and access to more homes";
        }
        return message;
    }
}
