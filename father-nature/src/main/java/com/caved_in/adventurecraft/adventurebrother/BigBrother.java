package com.caved_in.adventurecraft.adventurebrother;

import com.caved_in.adventurecraft.adventurebrother.action.ActionLogManager;
import com.caved_in.adventurecraft.adventurebrother.command.BigBrotherCommands;
import com.caved_in.adventurecraft.adventurebrother.config.CustomMessageCollection;
import com.caved_in.adventurecraft.adventurebrother.config.CustomMessageConfig;
import com.caved_in.adventurecraft.adventurebrother.debug.DebugActionLogManagerContents;
import com.caved_in.adventurecraft.adventurebrother.states.ActiveState;
import com.caved_in.adventurecraft.adventurebrother.states.GameAction;
import com.caved_in.adventurecraft.adventurebrother.states.InactiveState;
import com.caved_in.adventurecraft.adventurebrother.user.MinionManager;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.MiniGame;
import com.caved_in.commons.plugin.BukkitPlugin;
import org.apache.commons.io.FileUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BigBrother extends MiniGame<MinionManager> {

    public static final String LOGS_FOLDER = "plugins/BigBrother/logs/";
    public static final String MESSAGES_FOLDER = "plugins/BigBrother/messages/";

    private static BigBrother instance = null;

    public static BigBrother getInstance() {
        return instance;
    }

    @Override
    public void startup() {
        instance = this;

        registerUserManager(
                MinionManager.class
        );

        registerGameStates(
                new InactiveState(),
                new ActiveState()
        );

        registerFeatures(
                ActionLogManager.getInstance()
        );

        registerDebugActions(
                new DebugActionLogManagerContents()
        );

        registerCommands(new BigBrotherCommands());

        Chat.debug("Loading logs from logs folder " + LOGS_FOLDER);
        ActionLogManager.getInstance().loadLogs(new File(LOGS_FOLDER));
        Chat.debug("Loading complete m'fucka");

        //todo create configuration have global actions, such as server commands. Save uuid to config.
    }

    @Override
    public void shutdown() {
        ActionLogManager.getInstance().saveLogs();
    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis";
    }

    @Override
    public void initConfig() {

        File logFolder = new File(LOGS_FOLDER);

        if (!logFolder.exists()) {
            logFolder.mkdirs();
            debug("Created logs folder");
        }

        File messagesFolder = new File(MESSAGES_FOLDER);

        if (!messagesFolder.exists()) {
            messagesFolder.mkdirs();
            debug("Created messages folder!");
        }

        /*
        Load all the custom messages, used by Bigbrother to
        generate some swaaaaaaag output!
         */

        Set<CustomMessageCollection> messageCollectionSet = new HashSet<>();

        Serializer serializer = new Persister(new AnnotationStrategy());
        for(GameAction action : GameAction.values()) {
            String fileName = CustomMessageConfig.getConfigFile(action);

            File messageFile = new File(MESSAGES_FOLDER + fileName);

            boolean err = false;
            if (!messageFile.exists()) {
                try {
                    FileUtils.touch(messageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    err = true;
                }
            }

            if (err) {
                err = false;
                continue;
            }

            CustomMessageCollection collection = null;
            try {
                collection = serializer.read(CustomMessageCollection.class,messageFile);
            } catch (Exception e) {
                e.printStackTrace();
                err = true;
            }

            if (err || collection == null) {
                continue;
            }

            messageCollectionSet.add(collection);
        }

        //todo add swear filter loading.
        //todo add global swear filter option for players
    }

    @Override
    public long tickDelay() {
        return 20;
        //todo implement option in config to choose tick delay
    }
}
