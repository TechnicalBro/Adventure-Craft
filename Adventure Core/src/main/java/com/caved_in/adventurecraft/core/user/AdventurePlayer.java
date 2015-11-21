package com.caved_in.adventurecraft.core.user;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.commons.player.User;
import com.caved_in.commons.time.DateUtils;
import com.caved_in.commons.time.TimeHandler;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class AdventurePlayer extends User {
    private static AdventureCore core = AdventureCore.getInstance();

    @Element(name = "has-received-start-book")
    private boolean hasReceivedStartBook = false;

    @Element(name = "last-played")
    private long lastPlayedTimestamp;

    @Element(name = "first-played")
    private long firstPlayed;
    
    public AdventurePlayer(Player p) {
        super (p);
        firstPlayed = System.currentTimeMillis();
    }
    
    public AdventurePlayer(@Element(name = "name")String name, @Element(name="uuid")String uid, @Element(name = "world")String world,
                           @Element(name = "has-received-start-book")boolean hasReceivedStartBook) {
        super(name,uid,world);
        this.hasReceivedStartBook = hasReceivedStartBook;
    }

    public void updateLastPlayed() {
        lastPlayedTimestamp = System.currentTimeMillis();
    }

    public boolean hasReceivedStartBook() {
        return hasReceivedStartBook;
    }

    public double getEconomyMoney() {
        return core.getEconomy().getBalance(getPlayer());
    }

    public long getLastTimePlayedTimestamp() {
        return lastPlayedTimestamp;
    }

    public long getFirstPlayedTimestamp() {
        return firstPlayed;
    }

    public String getLastTimePlayed() {
        return TimeHandler.timeDurationToWords(lastPlayedTimestamp);
    }

    public String getFirstTimePlayed() {
        return TimeHandler.timeDurationToWords(firstPlayed);
    }

    public Date getDateLastPlayed() {
        return new Date(lastPlayedTimestamp);
    }

    public Date getDateFirstPlayed() {
        return new Date(firstPlayed);
    }

    public int getDaysSinceLastPlayed() {
        //todo fix this. It's broken.
        Date today = new Date(System.currentTimeMillis());
        Date lastPlayed = getDateLastPlayed();

        long daysDifference = today.getTime() - lastPlayed.getTime();

        long days = TimeUnit.DAYS.convert(daysDifference,TimeUnit.MILLISECONDS);
        return (int)days;
    }
}
