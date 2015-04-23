package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.game.guns.BaseArrow;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.utilities.ArrayUtils;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DocileArrowGadget extends BaseArrow {
    private static DocileArrowGadget instance = null;

    private static EntityType[] DOCILE_TYPES = {
            EntityType.COW,
            EntityType.PIG,
            EntityType.CHICKEN,
            EntityType.RABBIT,
            EntityType.BAT,
            EntityType.SQUID,
            EntityType.MUSHROOM_COW,
            EntityType.OCELOT,
            EntityType.HORSE,
            EntityType.SHEEP,
            EntityType.SNOWMAN
    };
    
    
    public static DocileArrowGadget getInstance() {
        if (instance == null) {
            instance = new DocileArrowGadget();
        }
        return instance;
    }

    protected DocileArrowGadget() {
        super(ItemBuilder.of(Material.ARROW).name("&aDocile Arrow").lore("&eShoot any hostile monster","&e and be amazed!"));
    }

    @Override
    public boolean onDamage(LivingEntity entity, Player player) {
        if (!Entities.isHostile(entity)) {
            Chat.actionMessage(player,"&cA &aDocile Arrow&c will only work on hostile mobs!");
            return false;
        }
        
        EntityType randomDocile = ArrayUtils.getRandom(DOCILE_TYPES);
        ParticleEffects.sendToLocation(ParticleEffects.HEART,entity.getLocation(), NumberUtil.getRandomInRange(10,15));
        Entities.spawnLivingEntity(randomDocile,entity.getLocation());
        entity.remove();
        return true;
    }

    @Override
    public int id() {
        return 133004;
    }
}
