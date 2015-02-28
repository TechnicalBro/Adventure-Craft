package com.caved_in.adventurecraft.homes.users;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.commons.player.User;
import com.caved_in.commons.warp.Warp;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "user")
public class HomePlayer extends User {

    @ElementList(name = "homes",entry = "home",type = Warp.class,required = true,inline = false,empty = false)
    private List<Warp> homes = new ArrayList<>();
    
    public HomePlayer(Player p) {
        super(p);
    }
    
    public HomePlayer(@Element(name = "name")String name, @Element(name="uuid")String uid, @Element(name = "world")String world,
                      @ElementList(name = "homes",entry = "home",type = Warp.class,required = true,inline = false,empty = false)List<Warp> homes) {
        super(name,uid,world);
        this.homes = homes;
    }

    public List<Warp> getHomes() {
        return homes;
    }

    public int getMaxHomes() {
        Player player = getPlayer();
        int maxHomes = 1;
        for(int i = AdventureHomes.Properties.MAX_HOME_COUNT; i >= 1; i--) {
            if (!player.hasPermission(String.format(AdventureHomes.Properties.HOME_COUNT_PERMISSION_BASE,i))) {
                continue;
            }
            
            maxHomes = i;
            break;
        }
        return maxHomes;
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
        for(Warp warp : homes) {
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
