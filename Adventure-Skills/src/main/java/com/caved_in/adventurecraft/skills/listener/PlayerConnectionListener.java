package com.caved_in.adventurecraft.skills.listener;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.SkillsUserManager;
import com.caved_in.commons.game.listener.UserManagerListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener extends UserManagerListener {
    private AdventureSkills core;
    private SkillsUserManager users;

    public PlayerConnectionListener(AdventureSkills game) {
        super(game);
        this.core = game;
        this.users = game.getUserManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (users.hasOfflineData(p.getUniqueId())) {
            boolean loaded = users.loadData(p.getUniqueId());
            if (!loaded) {
                core.debug("Unable to load data for the player");
                return;
            }

            //Welcome back package!
            SkillsUser user = users.getUser(p);
            //todo
        } else {
            users.addUser(p);
            core.debug("Player " + p.getName() + " has joined the Vortechs for the first time!");
            //todo
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
