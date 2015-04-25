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

public class KinArrowGadget extends AdventureArrow {
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
	public boolean doDamage(LivingEntity damaged, Player shooter) {
		if (!MobType.isMob(damaged.getType()) || damaged.hasMetadata("NPC")) {
			Chat.actionMessage(shooter, "&c&lOnly mobs can be forced to call kin");
			return false;
		}

		CreatureBuilder.of(damaged.getType()).asBaby(true).spawn(Locations.getRandomLocation(damaged.getLocation(), 2));
		ParticleEffects.sendToLocation(ParticleEffects.EXPLOSION_HUGE,damaged.getEyeLocation(), NumberUtil.getRandomInRange(2,5));
		return true;
	}

	@Override
    public int id() {
        return 133003;
    }
}
