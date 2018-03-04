package com.caved_in.adventurecraft.skills;

import com.caved_in.adventurecraft.skills.command.SkillsCommand;
import com.caved_in.adventurecraft.skills.config.SkillsConfig;
import com.caved_in.adventurecraft.skills.listener.*;
import com.caved_in.adventurecraft.skills.nms.NMS;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import com.caved_in.adventurecraft.skills.states.EmptyServerState;
import com.caved_in.adventurecraft.skills.states.PlayingState;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.SkillsUserManager;
import com.caved_in.adventurecraft.skills.util.ExpTables;
import com.caved_in.commons.game.MiniGame;
import com.caved_in.commons.plugin.Plugins;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AdventureSkills extends MiniGame<SkillsUserManager> {
    private static AdventureSkills instance = null;

    public static AdventureSkills getInstance() {
        return instance;
    }

    private SkillsConfig skillsConfig = null;

    private static final String USER_DATA_FOLDER = "plugins/Adventure-Skills/users/";

    private static final String CONFIG_LOCATION = "plugins/Adventure-Skills/Config.xml";

    @Override
    public void startup() {
        instance = this;

        /*
        Initialize the NMS Handler for native interactions.
         */
        NMS.init();

        registerUserManager(SkillsUserManager.class);

        /*
        Initialize the API for plugins, internal, and external
        usage! Has easy access to everything required!
         */
        API.init();

        /*
        Initialize the EXP Tables! Cashing the first 500 levels.
         */
        ExpTables.init();

        setUserManagerListener(new PlayerConnectionListener(this));

        registerGameStates(
                new EmptyServerState(),
                new PlayingState()
        );

        registerListeners(
                /*
                Used to modify the speed of entities based on their level!
                 */
                EntityModifyListener.getInstance(),
                /*
                Used to handle the mining skill in players!
                 */
                PlayerMineListener.getInstance(),
                /*
                Used to handle the gaining of levels and exp in players skills!
                 */
                PlayerSkillsListener.getInstance(),
                /*
                The listener used to handle the deaths of entities and
                assigning EXP to each specific skill
                that the player uses to slay the entity!

                Swords & Axes are Melee, Bows are Archery!

                Magic to follow!
                 */
                MobDamageDeathListener.getInstance()
        );

        registerCommands(
                new SkillsCommand()
        );

    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis - TheGamersCave";
    }

    @Override
    public void initConfig() {
        Serializer serializer = new Persister();

        File users = new File(USER_DATA_FOLDER);
        if (!users.exists()) {
            users.mkdirs();
        }

        File configFile = new File(CONFIG_LOCATION);
        if (configFile.exists()) {
            try {
                skillsConfig = serializer.read(SkillsConfig.class, configFile);
                debug("Intialized Skills skillsConfig from file!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            skillsConfig = new SkillsConfig();
            try {
                serializer.write(skillsConfig, configFile);
                debug("Saved Skills skillsConfig to file!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
        Initialize the skills configuration!
         */
        Config.init(skillsConfig);
    }

    @Override
    public long tickDelay() {
        return 20;
    }

    public String getUserDataFolder() {
        return USER_DATA_FOLDER;
    }

    public SkillsConfig getPluginConfig() {
        return skillsConfig;
    }

    public static class Config {

        public static double EXP_REQUIREMENT_FACTOR = 1.0;

        public static int MOB_LEVEL_MODIFY_CAP = 200;


        public static void init(SkillsConfig config) {
            EXP_REQUIREMENT_FACTOR = config.getExpRequirementFactor();
            MOB_LEVEL_MODIFY_CAP = config.getMobLevelModifyCap();
        }
    }

    public static class API {
        private static SkillsUserManager users;

        private static boolean initialized = false;

        public static void init() {
            if (initialized) {
                return;
            }

            users = getInstance().getUserManager();

            initialized = true;
        }

        public static boolean hasBossBarAPI() {
            return Plugins.isEnabled("BossBarAPI");
        }

        public static SkillsUser getUser(Player player) {
            return users.getUser(player);
        }

        public static int getLevel(Player player, SkillType type) {
            return getUser(player).getLevel(type);
        }

        public static int getExp(Player player, SkillType type) {
            return getUser(player).getExp(type);
        }

        public static void addExp(Player player, SkillType type, int exp) {
            getUser(player).addExp(type, exp);
        }
    }
}



