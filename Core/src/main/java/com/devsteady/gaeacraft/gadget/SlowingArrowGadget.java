package com.devsteady.gaeacraft.gadget;

import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.game.guns.BaseArrow;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.potion.Potions;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SlowingArrowGadget extends AdventureArrow {
    private static SlowingArrowGadget instance = null;

    private PotionEffect effect;
    public static SlowingArrowGadget getInstance() {
        if (instance == null) {
            instance = new SlowingArrowGadget();
        }
        return instance;
    }

    protected SlowingArrowGadget() {
        super(ItemBuilder.of(Material.ARROW).name("&bCrippling Arrow").lore("&eAny foe hit with these will","&ebe slowed in their tracks").item());
        effect = Potions.getPotionEffect(PotionEffectType.SLOW,2, (int) TimeHandler.getTimeInTicks(5, TimeType.SECOND));
    }

	@Override
	public boolean doDamage(LivingEntity target, Player shooter) {
		Entities.addPotionEffect(target	,effect);
		return true;
	}
}
