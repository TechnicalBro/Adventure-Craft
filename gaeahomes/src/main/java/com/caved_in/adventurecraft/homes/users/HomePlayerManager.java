package com.caved_in.adventurecraft.homes.users;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.players.UserManager;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
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
//            core.debug("There's no data loaded for the id: " + id.toString());
            return false;
        }
        File userFile = getUserFile(id);
        boolean saved = false;

        if (!userFile.exists()) {
            Chat.debug("User file for " + id.toString() + " doesn't exist!");
            Chat.debug("Attempting to create file " + id.toString());
            boolean created = false;
            try {
                created = userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!created) {
                Chat.messageOps("&cUnable to create data folder for user id[" + id.toString() + "]","&l&7Contact brandon and he'll fix this <3");
                return false;
            }
            HomePlayer player = getUser(id);

            if (player == null) {
                Chat.messageOps("&c&lPlayer with id " + id.toString() + " DOES NOT have a user object; Contact brandon IMMEDIATELY.");
                return false;
            }

            try {
                serializer.write(player, userFile);
                saved = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            HomePlayer player = getUser(id);
            try {
                serializer.write(player, userFile);
                saved = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (!saved)
                    Chat.messageOps("&cTHE SAME FUCKING FILE SAVE ERROR KEEPS HAPPENING- ERMAHGERHD");
            }
        }
        return saved;
    }
    
    public void saveAll() {
        for(HomePlayer user : allUsers()) {
            if (!save(user.getId())) {
//                core.debug("ERROR SAVING DATA FOR " + user.getName());
            }
        }
    }
    
    private File getUserFile(UUID id) {
        return new File(core.getUserDataFolder(),String.format("%s.xml",id.toString()));
    }
}
