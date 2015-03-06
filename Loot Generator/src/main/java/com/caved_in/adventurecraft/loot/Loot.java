package com.caved_in.adventurecraft.loot;

import com.caved_in.commons.plugin.BukkitPlugin;

public class Loot extends BukkitPlugin {
    private static Loot instance;
    
    public static Loot getInstance() {
        return instance;
        
    }
    
    @Override
    public void startup() {
        instance = this;
        
    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis";
    }

    @Override
    public void initConfig() {

    }
}
