package com.caved_in.adventurecraft.skills.nms.minecraft_1_8_R3;

import com.caved_in.adventurecraft.skills.nms.NativeActionHandler;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NativeHandler_1_8_R3 implements NativeActionHandler {

    @Override
    public void setMovementSpeed(Entity entity, double val) {
        net.minecraft.server.v1_8_R3.Entity nms_entity = ((CraftEntity) entity).getHandle();
        ((EntityInsentient) nms_entity).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(val);
    }

    @Override
    public boolean isFromMobSpawner(Entity entity) {
        return ((CraftEntity) entity).getHandle().fromMobSpawner;
    }

    @Override
    public Player getNearestPlayer(Location loc, double range) {
        World world = loc.getWorld();
        EntityHuman human = ((CraftWorld) world).getHandle().findNearbyPlayer(loc.getX(), loc.getY(), loc.getZ(), range);
        return human != null ? (Player) human.getBukkitEntity() : null;
    }
}
