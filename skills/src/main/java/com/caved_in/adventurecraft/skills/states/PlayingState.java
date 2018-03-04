package com.caved_in.adventurecraft.skills.states;

import ca.wacos.nametagedit.NametagAPI;
import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.SkillsUserManager;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.MiniGameState;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import org.bukkit.entity.Player;

public class PlayingState extends MiniGameState {
    private static long combatUpdateTimeStamp = 0;
    private static long combatUpdateWait = TimeHandler.getTimeInMilles(10, TimeType.SECOND);

    private static SkillsUserManager users = null;

    public PlayingState() {
        users = AdventureSkills.getInstance().getUserManager();
    }

    public void update() {
        long timestamp = System.currentTimeMillis();

        /*
        Update all the online players nametags to contain their current combat
        levels! For display to other players!
         */
        if (timestamp >= combatUpdateTimeStamp) {
            if (users == null) {
                Chat.debug("User manager is null!!!!");
                return;
            }


            if (!users.hasUsers()) {
                Chat.debug("No users have been loaded. Error!!!");
                return;
            }
            //update players combat levels

            for (SkillsUser user : users.allUsers()) {
                NametagAPI.setSuffix(user.getName(), Chat.format(" - &6Lvl &c%s", user.getCombatLevel()));
            }

            combatUpdateTimeStamp = Long.sum(timestamp, combatUpdateWait);
        }

        //todo proc armor effects.
    }

    @Override
    public int id() {
        return 2;
    }

    @Override
    public boolean switchState() {
        return Players.getOnlineCount() == 0;
    }

    @Override
    public int nextState() {
        return 1;
    }

    @Override
    public void setup() {
        debug("Listening to all player interactions on a per-tick basis!");
        setSetup(true);
    }

    @Override
    public void destroy() {

    }
}
