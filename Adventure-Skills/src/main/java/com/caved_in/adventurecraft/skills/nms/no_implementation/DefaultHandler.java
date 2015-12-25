package com.caved_in.adventurecraft.skills.nms.no_implementation;

import com.caved_in.adventurecraft.skills.nms.NativeActionHandler;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DefaultHandler implements NativeActionHandler {


    @Override
    public void setMovementSpeed(Entity entity, double val) {
//        throw new UnsupportedOperationException("No implementation available for the current spigot version!");
    }

    @Override
    public boolean isFromMobSpawner(Entity entity) {
//        throw new UnsupportedOperationException("No implementation available for the current spigot version!");
        return false;
    }

    @Override
    public Player getNearestPlayer(Location loc, double range) {
//        throw new UnsupportedOperationException("No implementation available for the current spigot version!");
        return null;
    }
}
