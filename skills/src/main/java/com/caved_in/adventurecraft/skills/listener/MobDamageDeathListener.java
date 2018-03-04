package com.caved_in.adventurecraft.skills.listener;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.skills.Combat;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.item.WeaponType;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.entityspawningmechanic.data.EntityWrapper;
import com.caved_in.entityspawningmechanic.handlers.entity.EntityHandler;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobDamageDeathListener implements Listener {
    private static MobDamageDeathListener instance = null;

    private static AdventureSkills core = AdventureSkills.getInstance();

    public static MobDamageDeathListener getInstance() {
        if (instance == null) {
            instance = new MobDamageDeathListener();
        }

        return instance;
    }

    protected MobDamageDeathListener() {

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeathEvent(EntityDeathEvent e) {
        LivingEntity deadMob = e.getEntity();

        if (!Entities.hasKiller(deadMob)) {
            return;
        }

        Player playerKiller = deadMob.getKiller();
        if (Players.handIsEmpty(playerKiller)) {
            //todo check if player is using unarmed to kill.
            return;
        }

        ItemStack hand = playerKiller.getItemInHand();

        int expGained = Combat.getExpForMob(deadMob.getType());

        if (expGained == 0) {
            return;
        }

        if (!EntityHandler.hasWrapper(deadMob)) {
            return;
        }

        EntityWrapper entityData = EntityHandler.getWrapper(deadMob);

        int entityLevel = entityData.getLevel();

        double multiplier = 0;

        if (entityData.isElite()) {
            multiplier += Combat.ELITE_MOB_BONUS_MULTIPLIER;
        }

        if (entityData.isBoss()) {
            multiplier += Combat.BOSS_MOB_BONUS_MULTIPLIER;
        }

        /*
        Give the player bonus EXP based on the
        multipliers for Elite mobs / bosses
        and based on the level of the mob.
         */
        int bonusExp = (int) Math.round((expGained * multiplier) + (entityLevel * Combat.BONUS_MOB_EXP_PER_LEVEL));

        expGained += bonusExp;

        final int addExp = expGained;

        SkillsUser user = AdventureSkills.API.getUser(playerKiller);

        if (Items.isWeapon(hand, WeaponType.SWORD) || Items.isWeapon(hand, WeaponType.AXE)) {
            //todo handle specific sword skills and axe skills.
            user.addExp(SkillType.MELEE, addExp);
        }

        if (Items.isWeapon(hand, WeaponType.BOW)) {
            //todo allow archers to throw arrows!
            user.addExp(SkillType.ARCHERY, addExp);
        }

        /*
        Allow players to gain sneak EXP When killing mobs in stealth mode!
         */
        if (playerKiller.isSneaking()) {
            core.getThreadManager().runTaskLater(() -> {
                user.addExp(SkillType.STEALTH, (int) Math.round(addExp / 3));
            }, TimeHandler.getTimeInTicks(3, TimeType.SECOND));
        }

        //todo check hand if there's any exp bonus on their enchants / stats.
    }
}
