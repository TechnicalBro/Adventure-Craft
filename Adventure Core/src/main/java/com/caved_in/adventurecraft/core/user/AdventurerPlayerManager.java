package com.caved_in.adventurecraft.core.user;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.player.User;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.UUID;

public class AdventurerPlayerManager extends UserManager<AdventurePlayer> {
    private AdventureCore core = null;
    
    private Serializer serializer = new Persister();

    public AdventurerPlayerManager() {
        super(AdventurePlayer.class);
        core = AdventureCore.getInstance();
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
            AdventurePlayer adventurer = serializer.read(AdventurePlayer.class,userFile);
            if (adventurer != null) {
                addUser(adventurer);
                core.debug("");
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
        
        AdventurePlayer player = getUser(id);
        boolean saved = false;
        try {
            serializer.write(player,userFile);
            saved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saved;
    }
    
    private File getUserFile(UUID id) {
        return new File(core.getUserDataFolder(),String.format("%s.xml",id.toString()));
    }
    
    
}
