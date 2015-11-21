package com.caved_in.adventurecraft.adventurebrother.action;

import com.caved_in.adventurecraft.adventurebrother.BigBrother;
import com.caved_in.adventurecraft.adventurebrother.states.GameAction;
import com.caved_in.adventurecraft.adventurebrother.user.Minion;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.event.UserJoinEvent;
import com.caved_in.commons.game.event.UserQuitEvent;
import com.caved_in.commons.game.feature.GameFeature;
import com.caved_in.commons.time.BasicTicker;
import org.apache.commons.io.FileUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActionLogManager extends GameFeature {

    private Map<UUID, ActionLog> logs = new HashMap<>();

    private static ActionLogManager instance = null;

    private BasicTicker saveTicker = new BasicTicker(20);

    private BigBrother brother = null;

    public static ActionLogManager getInstance() {
        if (instance == null) {
            instance = new ActionLogManager();
        }

        return instance;
    }

    protected ActionLogManager() {
        super("action-log-manager");
        brother = BigBrother.getInstance();

        ClassLoader classLoader = BigBrother.class.getClassLoader();
        Thread.currentThread().setContextClassLoader(classLoader);
        Chat.debug("Initialized class loader for Action-log-manager thread.");
    }

    @Override
    public boolean allowExecute() {
        return saveTicker.allow();
    }

    public boolean hasLog(UUID id) {
        return logs.containsKey(id);
    }

    public Map<UUID, ActionLog> getContents() {
        return logs;
    }

    public ActionLog getLog(UUID id) {
        if (logs.containsKey(id)) {
            Chat.debug("Logs contains data for minion id " + id.toString() + " with value being : " + logs.get(id) == null ? "Null" : logs.get(id).toString());
        } else {
            Chat.debug("There's no data in logs for minion id " + id.toString());
        }
        return logs.get(id);
    }

    public void loadLogs(File logsFolder) {
        Collection<File> logFiles = FileUtils.listFiles(logsFolder, null, false);

        if (logFiles.isEmpty()) {
            Chat.debug("There's no log files ");
            return;
        }

        Serializer serializer = new Persister(new AnnotationStrategy());
        for (File logFile : logFiles) {
            try {
                ActionLog log = serializer.read(ActionLog.class, logFile);
                Chat.debug(log.toString());


                logs.put(log.getMinionId(), log);
                Chat.debug("Cached log file for minion " + log.getMinionId().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveLogs() {
        Set<ActionLog> logs = new HashSet<>();
        Chat.debug("Loading logs from users for saving");
        for (Minion minion : BigBrother.getInstance().getUserManager().allUsers()) {
            logs.add(minion.getActionLog());
        }

        logs.addAll(this.logs.values());
        Chat.debug("Loaded logs from map");
        if (logs.isEmpty()) {
            Chat.debug("Absolutely no log files in cache to serialize! Nigga we dumb as fuck");
            return;
        }

        Serializer serializer = new Persister(new AnnotationStrategy());
        for (ActionLog log : logs) {
            try {
                serializer.write(log, new File(BigBrother.LOGS_FOLDER + log.getMinionId().toString() + ".xml"));
                Chat.debug("Saved log for minion " + log.getMinionId() + " to file!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void saveLog(Minion minion) {
        ActionLog log = minion.getActionLog();

        Serializer serializer = new Persister(new AnnotationStrategy());

        try {
            serializer.write(log, new File(BigBrother.LOGS_FOLDER + log.getMinionId().toString()));
            Chat.debug("Saved log for minion " + log.getMinionId() + " to file!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onUserJoin(UserJoinEvent e) {
        //todo handling swap of action logs from local map to user file.

        JavaPlugin plugin = e.getPlugin();

        if (!(plugin instanceof BigBrother)) {
            return;
        }

        Minion minion = (Minion) e.getUser();

        if (!logs.containsKey(minion.getId())) {
            minion.setActionLog(new ActionLog(minion.getId()));
            Chat.debug("The action log manager doesn't have data for player " + minion.getName() + ", generating new ActionLog for them");
            return;
        }

        minion.setActionLog(logs.get(minion.getId()));
        Chat.debug("Gave minion " + minion.getName() + " their loaded action log");
        logs.remove(minion.getId());

        minion.getActionLog().addAction(GameAction.LOGIN, "Logged in @ " + getTimestampString());
    }


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private String getTimestampString() {
        return sdf.format(new Date());
    }

    @Override
    public void tick() {

        if (saveTicker.allow()) {
            brother.getUserManager().getAllInBigBrotherChannel().forEach(p -> p.message("&a&l[BigBrother] &c&lSaving all players log files"));
            saveLogs();
            saveTicker.reset();
            Chat.debug("Saved big brother logs!!!!");
        } else {
            saveTicker.tick();
        }

        //update local action log from players!

        for (Minion minion : brother.getUserManager().allUsers()) {
            ActionLog minionLog = minion.getActionLog();
            logs.put(minion.getId(), minionLog);
            Chat.debug("Updated cached data for " + minion.getId().toString());
        }
    }
}
