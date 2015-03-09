package com.caved_in.adventurecraft.core.user;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.adventurecraft.core.user.upgrades.PlayerUpgrade;
import com.caved_in.adventurecraft.core.user.upgrades.UserUpgrade;
import com.caved_in.commons.player.User;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class AdventurePlayer extends User {
    private static AdventureCore core = AdventureCore.getInstance();

    @Element(name = "has-received-start-book")
    private boolean hasReceivedStartBook = false;
    
    @ElementMap(key = "type",keyType = PlayerUpgrade.Type.class,entry = "upgrade",value = "data",valueType = UserUpgrade.class,required = false)
    public Map<PlayerUpgrade.Type,UserUpgrade> upgrades = new HashMap<>();
    
    public AdventurePlayer(Player p) {
        super (p);
        initUpgrades();
    }
    
    public AdventurePlayer(@Element(name = "name")String name, @Element(name="uuid")String uid, @Element(name = "world")String world,
                           @Element(name = "has-received-start-book")boolean hasReceivedStartBook,
                           @ElementMap(key = "type",keyType = PlayerUpgrade.Type.class,entry = "upgrade",value = "data",valueType = UserUpgrade.class,required = false)Map<PlayerUpgrade.Type,UserUpgrade> upgrades) {
        super(name,uid,world);
        this.hasReceivedStartBook = hasReceivedStartBook;
        
        if (upgrades == null) {
            initUpgrades();
        } else {
            this.upgrades = upgrades;
        }
    }
    
    private void initUpgrades() {
        this.upgrades = new HashMap<>();
        for(PlayerUpgrade.Type type : EnumSet.allOf(PlayerUpgrade.Type.class)) {
            upgrades.put(type,new UserUpgrade(type,1));
        }
    }

    public boolean hasReceivedStartBook() {
        return hasReceivedStartBook;
    }
    
    public UserUpgrade getUpgradeData(PlayerUpgrade.Type type) {
        return upgrades.get(type);
    }
    
    public double getEconomyMoney() {
        return core.getEconomy().getBalance(getPlayer());
    }
}
