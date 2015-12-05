package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.game.item.BaseWeapon;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Set;

public class MonsterExamineGadget extends BaseWeapon {
    private static MonsterExamineGadget instance = null;

    public static MonsterExamineGadget getInstance() {
        if (instance == null) {
            instance = new MonsterExamineGadget();
        }

        return instance;
    }

    protected MonsterExamineGadget() {
        super(ItemBuilder.of(Material.STICK).name("&eMonster Examine").lore("Whack or use it on a mob to", "retrieve information regarding them!"));
    }

    @Override
    public void onSwing(Player p) {
        performTargetExamine(p);
    }

    private void performTargetExamine(Player p) {
        Location targetLoc = Players.getTargetLocation(p, 10);

        Set<LivingEntity> target = Entities.getLivingEntitiesNearLocation(targetLoc, 1.1);

        if (target.isEmpty()) {
            Chat.actionMessage(p, "&c&lThere's no monsters to examine in range.");
            return;
        }

        for (LivingEntity entity : target) {
            if (entity.getUniqueId().equals(p.getUniqueId())) {
                continue;
            }

            Chat.actionMessage(p, String.format("&eCurrent Health: &a%s &6- &eMaximum Health: &c%s", Entities.getCurrentHealth(entity), Entities.getMaxHealth(entity)));
            ParticleEffects.sendToLocation(ParticleEffects.SPELL_WITCH, entity.getEyeLocation(), 12);
            break;
        }

        if (NumberUtil.percentCheck(15)) {
            Chat.message(p,"&c&lYour &6Monster Examine&c gadget degrades after use!");
            Players.removeFromHand(p,1);
        }
    }

    @Override
    public void onActivate(Player p) {
        performTargetExamine(p);
    }

    @Override
    public void onAttack(Player p, LivingEntity e) {
        Chat.actionMessage(p, String.format("&eCurrent Health: &a%s &6- &eMaximum Health: &c%s", Entities.getCurrentHealth(e), Entities.getMaxHealth(e)));
        ParticleEffects.sendToLocation(ParticleEffects.SPELL_WITCH, e.getEyeLocation(), 12);
    }

    @Override
    public void onBreak(Player p) {

    }
}
