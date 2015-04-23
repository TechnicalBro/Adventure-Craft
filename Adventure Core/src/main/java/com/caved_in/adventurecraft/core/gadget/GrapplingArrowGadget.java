package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.game.guns.BaseArrow;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class GrapplingArrowGadget extends BaseArrow {
    private static GrapplingArrowGadget instance = null;

    public static GrapplingArrowGadget getInstance() {
        if (instance == null) {
            instance = new GrapplingArrowGadget();
        }
        return instance;
    }

    protected GrapplingArrowGadget() {
        super(ItemBuilder.of(Material.ARROW).name("&6Grappling Arrow").lore("&eGrapple your foe and bring","&eyourself to them!"));
    }

    @Override
    public boolean onDamage(LivingEntity livingEntity, Player player) {
        Players.teleport(player,livingEntity);
        return true;
    }

    @Override
    public int id() {
        return 133005;
    }
}
