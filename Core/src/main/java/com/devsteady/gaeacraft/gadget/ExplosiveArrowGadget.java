package com.devsteady.gaeacraft.gadget;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.Effects;
import com.caved_in.commons.game.guns.BaseArrow;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.sound.Sounds;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ExplosiveArrowGadget extends AdventureArrow {
    private static ExplosiveArrowGadget instance = null;

    public static ExplosiveArrowGadget getInstance() {
        if (instance == null) {
            instance = new ExplosiveArrowGadget();
        }
        return instance;
    }

    protected ExplosiveArrowGadget() {
        super(ItemBuilder.of(Material.ARROW).name("&eExplosive Arrow").lore("&c&lKABOOM","&eUse with any bow and wreak havoc!").item());
    }

	@Override
	public boolean doDamage(LivingEntity damaged, Player shooter) {
		EntityType type = damaged.getType();

		if (type == EntityType.CREEPER) {
			Creeper creeper = (Creeper)damaged;
			creeper.setPowered(true);
			Chat.actionMessage(shooter,"&cCareful, that's dangerous!");
			return true;
		}

		Effects.explode(damaged.getLocation(),2f,false,false);
		Sounds.playSound(shooter, Sound.EXPLODE,0.7f,0.9f);
		return true;
	}

}
