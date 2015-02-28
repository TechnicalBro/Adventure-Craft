package com.caved_in.adventurecraft.homes;

import com.caved_in.adventurecraft.homes.command.HomeCommands;
import com.caved_in.adventurecraft.homes.listener.PlayerConnectionListener;
import com.caved_in.adventurecraft.homes.menu.HomeMenu;
import com.caved_in.adventurecraft.homes.users.HomePlayer;
import com.caved_in.adventurecraft.homes.users.HomePlayerManager;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class AdventureHomes extends BukkitPlugin {
    private static AdventureHomes instance = null;

    private static final String USER_DATA_FOLDER = "plugins/Adventure-Homes/users/";

    private HomePlayerManager users = null;

    public static AdventureHomes getInstance() {
        return instance;
    }

    @Override
    public void startup() {
        instance = this;
        
        /*
        Create the manager for all the users who join the server.
         */
        users = new HomePlayerManager();
        
        registerListeners(
                /*
                Register the connection listener, which requires
                the homes plugin and our actual player manager.
                 */
                new PlayerConnectionListener(this,users)
        );
        
        registerCommands(
                new HomeCommands()
        );
        
        getThreadManager().registerSyncRepeatTask("Auto Save",() -> {
            users.saveAll();
            debug("Info: User data has been auto saved");
        },20, TimeHandler.getTimeInTicks(30, TimeType.SECOND));
    }

    @Override
    public void shutdown() {
        users.saveAll();
        debug("Saved all user data!");
    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis";
    }

    @Override
    public void initConfig() {
        File userDataFolder = new File(USER_DATA_FOLDER);
        if (!userDataFolder.exists()) {
            if (userDataFolder.mkdirs()) {
                debug("Created the default user data folder!");
            } else {
                debug("UNABLE TO LOAD THE ADVENTURE-HOMES PLUGIN DUE TO FILE PERMISSION ERROR.","  - WAS NOT ABLE TO CREATE USER DATA FOLDER @ " + USER_DATA_FOLDER);
                onDisable();
            }
        }
    }
    
    public File getUserDataFolder() {
        return new File(USER_DATA_FOLDER);
    }
    
    public HomePlayerManager getUserManager() {
        return users;
    }
    
    public static class API {
        
        public static HomePlayer getUser(UUID id) {
            return AdventureHomes.instance.getUserManager().getUser(id);
        }
        
        public static HomePlayer getUser(Player player) {
            return getUser(player.getUniqueId());
        }
        
        public static HomeMenu getMenu(Player player) {
            return new HomeMenu(getUser(player));
        }
        
        public static boolean openMenu(Player player) {
            if (getUser(player).hasHome()) {
                getMenu(player).openMenu(player);
                return true;
            } else {
                return false;
            }
        }
    }
    
    public static class Properties {
        public static final int MAX_HOME_COUNT = 25;
        
        public static final String HOME_COUNT_PERMISSION_BASE = "home.limit.%s";
    }
}
