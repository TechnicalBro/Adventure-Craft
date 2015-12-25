package com.caved_in.adventurecraft.skills.users;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.commons.game.players.UserManager;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.UUID;

public class SkillsUserManager extends UserManager<SkillsUser> {
    private AdventureSkills core;

    private Serializer serializer = new Persister(new AnnotationStrategy());

    public SkillsUserManager() {
        super(SkillsUser.class);
        core = AdventureSkills.getInstance();
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
            SkillsUser user = serializer.read(SkillsUser.class, userFile);
            if (user != null) {
                addUser(user);
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

        SkillsUser player = getUser(id);
        boolean saved = false;
        try {
            serializer.write(player, userFile);
            saved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saved;
    }

    private File getUserFile(UUID id) {
        return new File(core.getUserDataFolder(), String.format("%s.xml", id.toString()));
    }
}
