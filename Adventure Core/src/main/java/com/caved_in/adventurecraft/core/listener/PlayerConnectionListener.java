package com.caved_in.adventurecraft.core.listener;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.adventurecraft.core.user.AdventurerPlayerManager;
import com.caved_in.commons.chat.Title;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {
    private AdventureCore core;
    private AdventurerPlayerManager users;
    
    private static TitleBuilder welcomeTitleBuilder = new TitleBuilder().title("").fadeIn(1).stay(2).fadeOut(1);
    
    public PlayerConnectionListener(AdventureCore core, AdventurerPlayerManager userManager) {
        this.core = core;
        this.users = userManager;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        
        if (users.hasOfflineData(p.getUniqueId())) {
            boolean loaded = users.loadData(p.getUniqueId());
            if (!loaded) {
                core.debug("Unable to load data for the player");
            } else {
                core.debug("Loaded data for " + p.getName() + " from file");
            }
        } else {
            users.addUser(p);
            core.debug("Player " + p.getName() + " has joined adventure craft for the first time!");
            welcomeTitleBuilder.subtitle(String.format("%sWelcome %s to %sAdventure Craft",ChatColor.YELLOW.toString(),p.getName(),ChatColor.GOLD.toString())).build().broadcast();
            Players.giveItem(p, Items.makeItem(Material.WOOD_PICKAXE),Items.makeItem(Material.WOOD_SWORD), ItemBuilder.of(Material.FISHING_ROD).durability((short)10).name("&eWorn Fishing Rod").item(),Items.makeItemAmount(Material.APPLE,3));
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        
        boolean saved = users.save(player.getUniqueId());
        if (saved) {
            core.debug("Saved data for " + player.getName());
        } else {
            core.debug("Failed to save data for " + player.getName());
        }
    }
    
    @EventHandler 
    public void onPlayerKick(PlayerKickEvent e) {
        Player player = e.getPlayer();

        boolean saved = users.save(player.getUniqueId());
        if (saved) {
            core.debug("Saved data for " + player.getName());
        } else {
            core.debug("Failed to save data for " + player.getName());
        }
    }
}
