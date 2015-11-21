package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class HealingArrow extends AdventureArrow {
	private static HealingArrow instance = null;

	public static HealingArrow getInstance() {
		if (instance == null) {
			instance = new HealingArrow();
		}

		return instance;
	}

	//todo Finish healing cooldown
	private Cooldown healingCooldown = new Cooldown(5);

	protected HealingArrow() {
		super(ItemBuilder.of(Material.ARROW).name("&aArrows of Healing").lore("&eWhen hitting a player, it heals them!","&eWhen hitting a mob, it heals them too","&cCan you feel the love?"));
	}

	@Override
	public boolean doDamage(LivingEntity target, Player shooter) {
		int healingAmount = NumberUtil.getRandomInRange(5,7);

		Entities.setHealth(target, target.getHealth() + healingAmount);


		ParticleEffects.sendToLocation(ParticleEffects.HEART,target.getLocation(),NumberUtil.getRandomInRange(5,10));

		Chat.actionMessage(shooter,"&aI can feel it too.");
		return true;
	}

}
