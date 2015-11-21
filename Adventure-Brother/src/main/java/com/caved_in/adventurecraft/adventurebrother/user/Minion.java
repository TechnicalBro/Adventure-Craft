package com.caved_in.adventurecraft.adventurebrother.user;

import com.caved_in.adventurecraft.adventurebrother.action.ActionLog;
import com.caved_in.commons.player.User;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;

import java.util.UUID;

public class Minion extends User {

    private ActionLog actionLog;

    private long playtimeInSeconds = 0;

    private boolean bigBrotherChatChannel = false;

    private UUID monitoringMinion = null;

    @Element(name = "swear-filter-enabled")
    private boolean swearFilter = true;

    public Minion(Player p) {
        super(p);
    }

    public ActionLog getActionLog() {
        return actionLog;
    }

    public boolean hasActionLog() {
        return actionLog != null;
    }

    public void setActionLog(ActionLog log) {
        this.actionLog = log;
    }

    public boolean isInBigBrotherChatChannel() {
        return bigBrotherChatChannel;
    }

    public void setMonitoringGlobal(boolean val) {
        bigBrotherChatChannel = val;
    }

    public void setMonitoringMinion(Player player) {
        monitoringMinion = player.getUniqueId();
    }

    public boolean isMonitoringMinion(Minion minion) {
        return hasMonitoringMinion() && minion.getId().equals(monitoringMinion);
    }

    public UUID getMonitoringMinion() {
        return monitoringMinion;
    }

    public boolean hasMonitoringMinion() {
        return monitoringMinion != null;
    }

    public void clearMonitoringMinion() {
        monitoringMinion = null;
    }

    @Override
    public void destroy() {
        super.destroy();
        actionLog = null;
        bigBrotherChatChannel = false;
        monitoringMinion = null;
    }

}
