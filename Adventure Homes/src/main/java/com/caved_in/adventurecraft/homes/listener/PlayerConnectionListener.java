package com.caved_in.adventurecraft.homes.listener;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.adventurecraft.homes.users.HomePlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerConnectionListener implements Listener {
    private AdventureHomes plugin;
    private HomePlayerManager users;
    

    public PlayerConnectionListener(AdventureHomes core, HomePlayerManager userManager) {
        this.plugin = core;
        this.users = userManager;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        
        
        if (users.hasOfflineData(id)) {
            boolean loaded = users.loadData(id);
            if (!loaded) {
                //todo player has no homes loaded, no need to continue.
                plugin.debug("Unable to load data for the player");
                return;
            } else {
                plugin.debug("Loaded in data for " + p.getName());
            }
        } else {
            users.addUser(p);
            plugin.debug("Created default homes data for " + p.getName());
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        
        String name = player.getName();
        boolean saved = users.save(player.getUniqueId());
        if (saved) {
            plugin.debug("Saved data for " + name);
        } else {
            plugin.debug("Failed to save data for " + name);
        }
    }
    
    @EventHandler 
    public void onPlayerKick(PlayerKickEvent e) {
        Player player = e.getPlayer();

        boolean saved = users.save(player.getUniqueId());
        if (saved) {
            plugin.debug("Saved data for " + player.getName());
        } else {
            plugin.debug("Failed to save data for " + player.getName());
        }
    }
}
