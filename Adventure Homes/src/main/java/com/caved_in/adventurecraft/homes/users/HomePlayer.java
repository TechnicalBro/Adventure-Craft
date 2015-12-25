package com.caved_in.adventurecraft.homes.users;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.player.User;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.warp.Warp;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "user")
public class HomePlayer extends User {

    @ElementList(name = "homes", entry = "home", type = Warp.class, required = true, inline = false, empty = false)
    private List<Warp> homes = new ArrayList<>();

    @Element(name = "combat-timestamp")
    private long combatTimestamp = 0;

    @Element(name = "logged-out-on-combat")
    private boolean logoutOnTag = false;

    @Element(name = "teleport-timestamp")
    private long teleportTimestamp = 0;

    public HomePlayer(Player p) {
        super(p);
    }

    public HomePlayer(@Element(name = "name") String name, @Element(name = "uuid") String uid, @Element(name = "world") String world,
                      @ElementList(name = "homes", entry = "home", type = Warp.class, required = true, inline = false, empty = false) List<Warp> homes,
                      @Element(name = "combat-timestamp") long timestamp,
                      @Element(name = "logged-out-on-combat") boolean logoutOnTag,
                      @Element(name = "teleport-timestamp") long teleportTimestamp) {
        super(name, uid, world);
        this.homes = homes;
        this.combatTimestamp = timestamp;
        this.logoutOnTag = logoutOnTag;
        this.teleportTimestamp = teleportTimestamp;
    }

    public long getCombatTagExpiry() {
        return combatTimestamp;
    }

    public boolean isOnCombatTag() {
        return System.currentTimeMillis() < getCombatTagExpiry();
    }

    public void updateCombatTag() {
        combatTimestamp = Long.sum(System.currentTimeMillis(), TimeHandler.getTimeInMilles(AdventureHomes.Properties.combatTeleportCooldown(), TimeType.SECOND));
        Chat.debug("Updated combat tag for user " + getName());
    }

    public void loginAfterCombatLog() {
        if (!AdventureHomes.Properties.allowCombatTeleport()) {
            return;
        }
        combatTimestamp = Long.sum(System.currentTimeMillis(),TimeHandler.getTimeInMilles(AdventureHomes.Properties.combatTeleportCooldown(),TimeType.SECOND) * 3);
        this.actionMessage("&cYou logged out while you were combat tagged.");
        format("&7You've got a remaining time of %s on your teleport cooldown for combat logging.",TimeHandler.trimDurationDifferenceToWords(System.currentTimeMillis(),combatTimestamp));
        logoutOnTag = false;
    }

    public boolean hasLoggedOutDuringCombat() {
        return logoutOnTag;
    }

    public void setLogoutDuringCombat(boolean b) {
        logoutOnTag = b;
    }

    public long getTeleportTimestamp() {
        return teleportTimestamp;
    }

    public void updateTeleportCooldown() {
        teleportTimestamp = Long.sum(System.currentTimeMillis(), TimeHandler.getTimeInMilles(AdventureHomes.Properties.teleportCooldownTime(),TimeType.SECOND));
    }

    public boolean isOnTeleportCooldown() {
        return System.currentTimeMillis() < teleportTimestamp;
    }

    public List<Warp> getHomes() {
        return homes;
    }

    public int getMaxHomes() {
        return 1;
    }

    public int getHomeCount() {
        return homes.size();
    }

    public boolean canCreateHome() {
        return getHomeCount() < getMaxHomes();
    }

    public PlayerHomeAction addHome(Warp warp) {
        if (!canCreateHome()) {
            return PlayerHomeAction.MAX_HOMES_REACHED;
        }

        if (hasHome(warp.getName())) {
            return PlayerHomeAction.DUPLICATE_NAME;
        }

        homes.add(warp);

        return PlayerHomeAction.ADDED;
    }

    public boolean hasHome(String name) {
        for (Warp warp : homes) {
            if (!warp.getName().equalsIgnoreCase(name)) {
                continue;
            }

            return true;
        }

        return false;
    }

    public boolean hasHome() {
        return homes.size() > 0;
    }

    public PlayerHomeAction deleteHome(Warp warp) {
        if (!hasHome()) {
            return PlayerHomeAction.NO_HOMES_AVAILABLE;
        }

        if (hasHome(warp.getName())) {
            if (homes.remove(warp)) {
                return PlayerHomeAction.HOME_DELETED;
            } else {
                return PlayerHomeAction.FAILED_TO_DELETE;
            }
        } else {
            return PlayerHomeAction.HOME_NONEXISTANT;
        }
    }
}
