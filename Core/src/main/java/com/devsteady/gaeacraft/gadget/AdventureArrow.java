package com.devsteady.gaeacraft.gadget;

import com.devsteady.gaeacraft.AdventureCore;
import com.caved_in.commons.game.guns.BaseArrow;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.plugin.Plugins;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.utils.CombatUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AdventureArrow extends BaseArrow {

	public AdventureArrow(ItemBuilder builder) {
		super(builder);
	}

	public AdventureArrow(ItemStack item) {
		super(item);
	}

	@Override
	public boolean onDamage(LivingEntity livingEntity, Player player) {
		if (AdventureCore.API.hasTowny()) {
			if (CombatUtil.preventDamageCall(AdventureCore.API.getTowny(),player,livingEntity)) {
				return false;
			}
		}

		return doDamage(livingEntity,player);
	}

	public abstract boolean doDamage(LivingEntity target, Player shooter);
}
