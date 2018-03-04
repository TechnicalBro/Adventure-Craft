package com.devsteady.gaeacraft.user;

import com.devsteady.gaeacraft.AdventureCore;
import com.caved_in.commons.player.User;
import com.caved_in.commons.time.DateUtils;
import com.caved_in.commons.time.TimeHandler;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AdventurePlayer extends User {
    private static AdventureCore core = AdventureCore.getInstance();

    @Element(name = "has-received-start-book")
    private boolean hasReceivedStartBook = false;

    @Element(name = "last-played", type = Date.class)
    private Date lastPlayedTimestamp;

    @Element(name = "first-played", type = Date.class)
    private Date firstPlayed;

    public AdventurePlayer(Player p) {
        super(p);
        firstPlayed = new Date();
        lastPlayedTimestamp = new Date();
    }

    public AdventurePlayer(@Element(name = "name") String name, @Element(name = "uuid") String uid, @Element(name = "world") String world,
                           @Element(name = "has-received-start-book") boolean hasReceivedStartBook, @Element(name = "last-played", type = Date.class) Date lastPlayed,
                           @Element(name = "first-played", type = Date.class) Date firstPlayed) {
        super(name, uid, world);
        this.hasReceivedStartBook = hasReceivedStartBook;
        this.lastPlayedTimestamp = lastPlayed;
        this.firstPlayed = firstPlayed;
    }

    public void updateLastPlayed() {
        lastPlayedTimestamp = new Date();
    }

    public boolean hasReceivedStartBook() {
        return hasReceivedStartBook;
    }

    public double getEconomyMoney() {
        return core.getEconomy().getBalance(getPlayer());
    }

    public long getLastTimePlayedTimestamp() {
        return lastPlayedTimestamp.getTime();
    }

    public long getFirstPlayedTimestamp() {
        return firstPlayed.getTime();
    }

    public String getLastTimePlayed() {
        return TimeHandler.timeDurationToWords(lastPlayedTimestamp.getTime());
    }

    public String getFirstTimePlayed() {
        return TimeHandler.timeDurationToWords(firstPlayed.getTime());
    }

    public Date getDateLastPlayed() {
        return lastPlayedTimestamp;
    }

    public Date getDateFirstPlayed() {
        return firstPlayed;
    }

    public int getDaysSinceLastPlayed() {
        Date today = new Date();
        Date lastPlayed = getDateLastPlayed();

        long daysDifference = today.getTime() - lastPlayed.getTime();

        long days = TimeUnit.DAYS.convert(daysDifference, TimeUnit.MILLISECONDS);
        return (int) days;
    }
}
