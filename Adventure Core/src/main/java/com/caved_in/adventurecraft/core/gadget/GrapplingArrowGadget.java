package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.game.guns.BaseArrow;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class GrapplingArrowGadget extends AdventureArrow {
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
	public boolean doDamage(LivingEntity target, Player shooter) {
		Players.teleport(shooter,target);
		return true;
	}

	@Override
    public int id() {
        return 133005;
    }
}
