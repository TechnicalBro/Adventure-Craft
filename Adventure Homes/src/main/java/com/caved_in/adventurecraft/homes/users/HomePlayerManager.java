package com.caved_in.adventurecraft.homes.users;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.commons.game.players.UserManager;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.UUID;

public class HomePlayerManager extends UserManager<HomePlayer> {
    private AdventureHomes core = null;
    
    private Serializer serializer = new Persister();

    public HomePlayerManager() {
        super(HomePlayer.class);
        core = AdventureHomes.getInstance();
    }
    
    public boolean hasOfflineData(UUID id) {
        return getUserFile(id).exists();
    }
    
    public boolean loadData(UUID id) {
        File userFile = getUserFile(id);
        if (!userFile.exists()) {
            return false;
        }
        
        
        boolean loaded = false;

        try {
            HomePlayer player = serializer.read(HomePlayer.class,userFile);
            if (player != null) {
                addUser(player);
                loaded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loaded;
    }
    
    public boolean save(UUID id) {
        if (!hasData(id)) {
            core.debug("There's no data loaded for the id: " + id.toString());
            return false;
        }
        File userFile = getUserFile(id);
        
        HomePlayer player = getUser(id);
        boolean saved = false;
        try {
            serializer.write(player,userFile);
            saved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saved;
    }
    
    public void saveAll() {
        for(HomePlayer user : allUsers()) {
            if (!save(user.getId())) {
                core.debug("ERROR SAVING DATA FOR " + user.getName());
            }
        }
    }
    
    private File getUserFile(UUID id) {
        return new File(core.getUserDataFolder(),String.format("%s.xml",id.toString()));
    }
}
