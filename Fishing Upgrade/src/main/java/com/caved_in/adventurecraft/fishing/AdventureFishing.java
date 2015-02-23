package com.caved_in.adventurecraft.fishing;

import com.caved_in.adventurecraft.fishing.listeners.FishingListener;
import com.caved_in.commons.plugin.BukkitPlugin;

public class AdventureFishing extends BukkitPlugin {
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
