package com.caved_in.adventurecraft.adventureitems.effects;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class CriticalStrikeEffect implements ItemEffect {
	private static CriticalStrikeEffect instance = null;

	private static final int CHANCE = 20;

	public static CriticalStrikeEffect getInstance() {
		if (instance == null) {
			instance = new CriticalStrikeEffect();
		}
		return instance;
	}

	@Override
	public String name() {
		return "Critical Strike";
	}

	@Override
	public boolean verify(ItemStack item) {
		return Items.loreContains(item,"- Critical Strike -");
	}

	@Override
	public boolean onPlayerDamagePlayer(Player attacked, Player damaged) {
		return onPlayerDamage(attacked,damaged);
	}

	@Override
	public boolean onPlayerBreakBlock(Player player, Block block) {
		return false;
	}

	@Override
	public boolean onPlayerDrop(Player player, Item item) {
		ParticleEffects.sendToLocation(ParticleEffects.EXPLOSION_LARGE,item.getLocation(),NumberUtil.getRandomInRange(5,10));
		return true;
	}

	@Override
	public boolean onPlayerDamageEntity(Player player, Entity entity) {
		return false;
	}

	@Override
	public boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity) {
		return onPlayerDamage(player,entity);
	}

	public boolean onPlayerDamage(Player player, Damageable entity) {
		if (!NumberUtil.percentCheck(CHANCE)) {
			return false;
		}

		double extraDamage = NumberUtil.getRandomInRange(1.5,4.0);
		Entities.damage(entity,extraDamage,player);
		ParticleEffects.sendToLocation(ParticleEffects.CRIT,entity.getLocation(),NumberUtil.getRandomInRange(5,10));
		return true;
	}

	@Override
	public void apply(ItemStack item) {
		if (verify(item)) {
			return;
		}
		
		Items.addLore(item, "&e- Critical Strike -");
	}
}
