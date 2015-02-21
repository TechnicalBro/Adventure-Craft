package com.caved_in.adventurecraft.fishingupgrade;

import com.caved_in.adventurecraft.fishingupgrade.listeners.FishingListener;
import com.caved_in.commons.plugin.BukkitPlugin;

public class FishingUpgrade extends BukkitPlugin {
    @Override
    public void startup() {
        
        registerListeners(new FishingListener());

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
