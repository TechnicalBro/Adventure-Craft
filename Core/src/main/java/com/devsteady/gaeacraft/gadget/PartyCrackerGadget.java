package com.devsteady.gaeacraft.gadget;

import com.devsteady.gaeacraft.AdventureCore;
import com.caved_in.commons.effect.Effects;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.fireworks.Fireworks;
import com.caved_in.commons.game.gadget.ItemGadget;
import com.caved_in.commons.game.gadget.LimitedGadget;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.ArrayUtils;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

import java.util.ArrayList;
import java.util.List;

public class PartyCrackerGadget extends LimitedGadget {
    private static PartyCrackerGadget instance = null;

    public static PartyCrackerGadget getInstance() {
        if (instance == null) {
            instance = new PartyCrackerGadget();
        }
        return instance;
    }


    private ParticleEffects[] effects = new ParticleEffects[]{
            ParticleEffects.BUBBLE,
            ParticleEffects.ENCHANTMENT_TABLE,
            ParticleEffects.FLAME,
            ParticleEffects.SPELL_INSTANT,
            ParticleEffects.HEART
    };

    public PartyCrackerGadget() {
        super(ItemBuilder.of(Material.GOLDEN_CARROT).name("&6The Party Cracker").lore("&aStarts the party with every use!", "&e5 Uses total!"), 5);
        properties().droppable(false).droppable(false);
    }

    @Override
    public void use(Player holder) {
        int randomActionInt = NumberUtil.getRandomInRange(1, 4);

        switch (randomActionInt) {
            case 1:
                Fireworks.playRandomFirework(holder.getLocation());
                break;
            case 2:
                Effects.strikeLightning(holder.getLocation(), false);
                break;
            case 3:
                ParticleEffects.sendToLocation(ArrayUtils.getRandom(effects), holder.getLocation(), NumberUtil.getRandomInRange(15, 25));
                break;
            case 4:
                List<Sheep> rainbowSheep = new ArrayList<>();

                List<Location> circleAround = Locations.getCircle(holder.getLocation(), 6);

                for (Location loc : circleAround) {
                    Sheep sheep = Entities.spawnRandomSheep(loc);
                    AdventureCore.API.setSpeedModifier(sheep, 0);
                }

                AdventureCore.getInstance().getThreadManager().runTaskLater(() -> {
                    for (Sheep sheep : rainbowSheep) {
                        if (sheep == null || sheep.isDead()) {
                            continue;
                        }

                        Effects.explode(sheep.getLocation(), 1.0f, false, false);
                        ParticleEffects.sendToLocation(ParticleEffects.SMOKE_NORMAL, sheep.getLocation(), NumberUtil.getRandomInRange(5, 9));
                        sheep.remove();
                    }
                }, TimeHandler.getTimeInTicks(3, TimeType.SECOND));
                break;
            default:
                break;
        }
    }
}
