package com.caved_in.adventurecraft.core.user;

import com.caved_in.commons.player.User;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;

public class AdventurePlayer extends User {
    
    @Element(name = "has-received-start-book")
    private boolean hasReceivedStartBook = false;

    public AdventurePlayer(Player p) {
        super (p);
    }
    
    public AdventurePlayer(@Element(name = "name")String name, @Element(name="uuid")String uid, @Element(name = "world")String world,
                           @Element(name = "has-received-start-book")boolean hasReceivedStartBook) {
        super(name,uid,world);
        this.hasReceivedStartBook = hasReceivedStartBook;
    }

    public boolean hasReceivedStartBook() {
        return hasReceivedStartBook;
    }
}
