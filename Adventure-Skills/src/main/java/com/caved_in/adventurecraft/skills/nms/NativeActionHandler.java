package com.caved_in.adventurecraft.skills.nms;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface NativeActionHandler {
    void setMovementSpeed(Entity entity, double val);

    boolean isFromMobSpawner(Entity entity);

    Player getNearestPlayer(Location loc, double range);

}
