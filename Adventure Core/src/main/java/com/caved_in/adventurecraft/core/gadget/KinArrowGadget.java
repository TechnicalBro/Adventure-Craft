package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.CreatureBuilder;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.entity.MobType;
import com.caved_in.commons.game.guns.BaseArrow;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class KinArrowGadget extends BaseArrow {
    private static KinArrowGadget instance = null;

    public static KinArrowGadget getInstance() {
        if (instance == null) {
            instance = new KinArrowGadget();
        }
        return instance;
    }

    protected KinArrowGadget() {
        super(ItemBuilder.of(Material.ARROW).name("&eArrow of Kin").lore("&eFire at any foe to force","&etheir kin to their aid!").item());
    }

    @Override
    public boolean onDamage(LivingEntity livingEntity, Player player) {
        if (!MobType.isMob(livingEntity.getType()) || livingEntity.hasMetadata("NPC")) {
            Chat.actionMessage(player, "&c&lOnly mobs can be forced to call kin");
            return false;
        }

        CreatureBuilder.of(livingEntity.getType()).asBaby(true).spawn(Locations.getRandomLocation(livingEntity.getLocation(),2));
        ParticleEffects.sendToLocation(ParticleEffects.EXPLOSION_HUGE,livingEntity.getEyeLocation(), NumberUtil.getRandomInRange(2,5));
        return true;
    }

    @Override
    public int id() {
        return 133003;
    }
}
