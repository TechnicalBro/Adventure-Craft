package com.caved_in.adventurecraft.skills.listener;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.nms.NMS;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.SkillsUserManager;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.utilities.Scaler;
import com.caved_in.entityspawningmechanic.event.EntityModifiedOnSpawnEvent;
import com.caved_in.entityspawningmechanic.event.EntityToBeSpawnedEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public class EntityModifyListener implements Listener {
    private static Scaler scaler = new Scaler(9, 0.17, AdventureSkills.Config.MOB_LEVEL_MODIFY_CAP, 0.33);

    private static EntityModifyListener instance = null;

    public static EntityModifyListener getInstance() {
        if (instance == null) {
            instance = new EntityModifyListener();
        }
        return instance;
    }

    protected EntityModifyListener() {

    }

    @EventHandler
    public void onEntitySpawnModified(EntityModifiedOnSpawnEvent e) {
        LivingEntity entity = e.getEntity();

        NMS.getNmsHandler().setMovementSpeed(entity, scaler.scale(e.getLevel()));

        //todo implement check for nearby player level, and implement a mod of a fraction of the players level!
    }

    @EventHandler
    public void onEntityToBeSpawned(EntityToBeSpawnedEvent e) {
        //todo implement check for nearby player level, and implement a mod of a fraction of the players level!

        Set<Player> players = Locations.getPlayersInRadius(e.getLoc(),20);

        for(Player player : players) {
            SkillsUser user = AdventureSkills.API.getUser(player);

        }

    }
}
