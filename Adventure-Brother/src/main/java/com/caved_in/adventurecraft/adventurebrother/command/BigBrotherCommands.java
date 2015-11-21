package com.caved_in.adventurecraft.adventurebrother.command;

import com.caved_in.adventurecraft.adventurebrother.BigBrother;
import com.caved_in.adventurecraft.adventurebrother.action.ActionLog;
import com.caved_in.adventurecraft.adventurebrother.action.ActionLogManager;
import com.caved_in.adventurecraft.adventurebrother.action.MinionAction;
import com.caved_in.adventurecraft.adventurebrother.states.GameAction;
import com.caved_in.adventurecraft.adventurebrother.user.Minion;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.DateUtils;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class BigBrotherCommands {

    @Command(identifier = "bigbrother", permissions = "bigbrother.admin")
    public void onBigBrotherCommand(Player player) {
    }

    @Command(identifier = "bigbrother time", permissions = "bigbrother.admin")
    public void onBigBrotherCurrentTimeCommand(Player player) {
        Date time = new Date();

        Chat.message(player, String.format("&eCurrent time is: &a%s",getTimestampString(time)));
    }


    @Command(identifier = "bigbrother search", permissions = "bigbrother.admin")
    public void onBigBrotherSearchCommand(Player player, @Arg(name = "player") String playerName, @Arg(name = "actions") String actions, @Arg(name = "time") String time) {
        /*
        1. Check if player name is online and if so get their data,
           otherwise go to the action log manager and get it from there.

        2. Check if the 'actions' arg contains commas, and if so seperate them and collect
           a collection of GameActions to search for!

            2.1: Create aliases for the GameArgs for chat friendly usage / commmand friendly
                 usage.

        3. Check if the time argument is valid, and if so parse it for a duration!
           (Might have to do the parsing manually, not exactly sure on that one!)

        4. Using all of this we get the users action log, and search through any of the criteria matching this.

        ToDo: Implement action specific arguments like block type for block break, or command for command search, taking a multitude of them!
         */

        UUID minionId = null;
        if (!Players.isOnline(playerName)) {
            try {
                minionId = Players.getUUIDFromName(playerName);
            } catch (Exception e) {
                e.printStackTrace();
                Chat.message(player, "&cUnable to find uuid for player &e" + playerName);
                return;
            }
        } else {
            minionId = Players.getPlayer(playerName).getUniqueId();
            Chat.debug("Minion " + playerName + " is online, retrieving UUID that-a-way!");
        }

        Collection<GameAction> gameActions = new HashSet<>();

        if (actions.contains(",")) {
            String[] actionAliases = actions.split(",");
            for (String alias : actionAliases) {
                for (GameAction action : GameAction.values()) {
                    if (action.isAlias(alias)) {
                        gameActions.add(action);
                    }
                }
            }
        } else {
            for (GameAction action : GameAction.values()) {
                if (action.isAlias(actions)) {
                    gameActions.add(action);
                    break;
                }
            }

            if (gameActions.size() == 0) {
                Chat.message(player, "&cYou must include some actions to parse!");
                return;
            }
        }

        if (!ActionLogManager.getInstance().hasLog(minionId)) {
            Chat.message(player, "&cUnable to find minion log for " + playerName);
            return;
        }

        ActionLog minionLog = ActionLogManager.getInstance().getLog(minionId);

        long durationBefore = 0;
        try {
            durationBefore = DateUtils.parseDateDiff(time, false);
        } catch (Exception e) {
            e.printStackTrace();
            Chat.message(player, "&cUnable to parse time &e" + time);
            return;
        }

        if (durationBefore == 0) {
            //todo parse all results of actions
            Chat.message(player, "Parse of time 0 not yet implemented");
            return;
        }

        List<MinionAction> searchActions = minionLog.search(gameActions, durationBefore);


        if (searchActions == null || searchActions.isEmpty()) {
            Chat.message(player, "&cUnable to find actions for &e" + playerName + "&c after date &e" + getTimestampString(new Date(durationBefore)));
            return;
        }

        //todo better formatting on the output.

        //todo optional book output

        Chat.message(player, "&a&l==== Big Brother Log ====");
        for (MinionAction action : searchActions) {
            Chat.message(player, "[&7" + playerName + "&r] " + action.toString());
        }
        Chat.message(player, "&a&l===========================");
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private String getTimestampString(Date date) {
        return sdf.format(date);
    }


    @Command(identifier = "bigbrother listen", permissions = "bigbrother.admin")
    public void onBigBrotherListenCommand(Player player, @Arg(name = "minion", def = "global") String minion) {
        Minion playerMinion = BigBrother.getInstance().getUserManager().getUser(player);

        if (minion.equalsIgnoreCase("none")) {
            playerMinion.clearMonitoringMinion();
            playerMinion.setMonitoringGlobal(false);
        }

        if (minion.equalsIgnoreCase("global")) {

            playerMinion.setMonitoringGlobal(true);
            playerMinion.message("&aYou're now monitoring globally with &eBig Brother");
            playerMinion.clearMonitoringMinion();
            return;
        }

        Player listener = Players.getPlayer(minion);

        if (listener == null) {
            playerMinion.message("&ePlayer '&a" + minion + "&e' is &coffline.");
            return;
        }

        playerMinion.setMonitoringMinion(listener);
        playerMinion.message("&aYou're now monitoring '&e" + player.getName() + "'&a and their actions using &9Big Brother");
    }

    @Command(identifier = "bigbrother config swearfilter",permissions = "bigbrother.admin")
    public void onBigBrotherConfigSwearFilterCommand(Player player, @Arg(name = "option")String option) {

    }
}
