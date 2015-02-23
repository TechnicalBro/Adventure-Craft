package com.caved_in.adventurecraft.core;

import com.caved_in.adventurecraft.core.listener.PlayerConnectionListener;
import com.caved_in.adventurecraft.core.user.AdventurerPlayerManager;
import com.caved_in.commons.game.CraftGame;

import java.io.File;

public class AdventureCore extends CraftGame<AdventurerPlayerManager> {
    
    /*
    Singleton instance of the adventure core!
     */
    private static AdventureCore instance = null;
    
    /*
    Manager of the user classes- Handles all operations related to players
    and specific data or operations on them inside the game, or proxies towards it.
    It just works :D
     */
    private AdventurerPlayerManager userManager = null;
    
    /*
    User listener that handles connections for players to the craft game.
     */

    private static final String USER_DATA_FOLDER = "plugins/Adventure-Core/users/";
    
    public static AdventureCore getInstance() {
        return instance;
    }
    
    @Override
    public void startup() {
        instance = this;
        
        userManager = new AdventurerPlayerManager();
        
        registerListeners(
                new PlayerConnectionListener(this,userManager)
        );
        
    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis";
    }

    @Override
    public void initConfig() {
        File userFolder = new File(USER_DATA_FOLDER);
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public long tickDelay() {
        return 20;
    }

    @Override
    public AdventurerPlayerManager getUserManager() {
        return userManager;
    }
    
    public File getUserDataFolder() {
        return new File(USER_DATA_FOLDER);
    }
}
