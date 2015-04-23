package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.game.guns.BaseArrow;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class EnderArrowGadget extends BaseArrow {
    private static EnderArrowGadget instance = null;
    
    public static EnderArrowGadget getInstance() {
        if (instance == null) {
            instance = new EnderArrowGadget();
        }
        
        return instance;
    }
    
    protected EnderArrowGadget() {
        super(ItemBuilder.of(Material.ARROW).name("&aEnder Arrows").lore("&eShoot any foe to launch them straight up!").item());
    }

    @Override
    public boolean onDamage(LivingEntity livingEntity, Player player) {
        if (player.getVelocity().length() >= 0) {
            Entities.teleport(livingEntity,livingEntity.getLocation().add(0,NumberUtil.getRandomInRange(15,25),0));
            return true;
        }
        Entities.launchUpwards(livingEntity, NumberUtil.getRandomInRange(15,25),200);
        return true;
    }

    @Override
    public int id() {
        return 133007;
    }
}