package com.caved_in.adventurecraft.loot.effects;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.Effects;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.NumberUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class BleedEffect implements ItemEffect {
    private static BleedEffect instance = null;

    public static BleedEffect getInstance() {
        if (instance == null) {
            instance = new BleedEffect();
        }
        return instance;
    }

    @Override
    public String name() {
        return "Bleed";
    }

    @Override
    public boolean verify(ItemStack item) {
        if (!Items.hasLore(item)) {
            return false;
        }

        return Items.loreContains(item, "- Bleed:");
    }

    private String getBleedLine(ItemStack item) {
        int bleedLine = Items.getLoreLineNumberContaining(item, "- Bleed:");

        return Items.getLore(item, bleedLine + 1);
    }

    @Override
    public boolean onPlayerDamagePlayer(Player attacked, Player damaged, double damage) {
        return onDamageEntity(attacked, damaged, damage);
    }

    @Override
    public boolean onPlayerBreakBlock(Player player, Block block) {
        return true;
    }

    @Override
    public boolean onPlayerDrop(Player player, Item item) {
        return true;
    }

    @Override
    public boolean onPlayerDamageEntity(Player player, Entity entity, double damage) {
        return onDamageEntity(player, entity,damage);
    }

    @Override
    public boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity, double damage) {
        return onDamageEntity(player, entity, damage);
    }


    private static final int MIN_CHANCE = 1;
    private static final int MAX_CHANCE = 15;

    private static final int MIN_DAMAGE = 2;

    private static final int MAX_DAMAGE = 10;

    private static final int MIN_SECONDS = 2;

    private static final int MAX_SECONDS = 5;

    @Override
    public void apply(ItemStack item) {
        if (verify(item)) {
            return;
        }

        int chance = NumberUtil.getRandomInRange(MIN_CHANCE, MAX_CHANCE);

        int damage = NumberUtil.getRandomInRange(MIN_DAMAGE, MAX_DAMAGE);

        int seconds = NumberUtil.getRandomInRange(MIN_SECONDS, MAX_SECONDS);

        Items.addLore(item, "- &4&lBleed:");
        Items.addLore(item, " - &6" + chance + "&e% chance to deal &6" + damage + "&e dmg/&6" + seconds + "&es!");
    }

    @Override
    public int getActivationChance(ItemStack item) {
        String bleedLine = getBleedLine(item);

        bleedLine = ChatColor.stripColor(bleedLine);

        String chanceLine = StringUtils.substringBetween(bleedLine, " - ", "% chance");

        return Integer.parseInt(chanceLine);
    }

    public int getDamage(ItemStack item) {
        String bleedLine = getBleedLine(item);

        bleedLine = ChatColor.stripColor(bleedLine).replaceAll("  - ", "");

        String damage = StringUtils.substringBetween(bleedLine, "deal ", " dmg/");
        return Integer.parseInt(damage.replace(" ", ""));
    }

    public int getSeconds(ItemStack item) {
        String bleedLine = getBleedLine(item);

        bleedLine = ChatColor.stripColor(bleedLine).replace("  - ", "");

        String seconds = StringUtils.substringBetween(bleedLine, "/", "s!");

        return Integer.parseInt(seconds.replace(" ", ""));
    }

    public boolean onDamageEntity(Player player, Entity entity, double originalDamage) {
        ItemStack hand = player.getItemInHand();

        int chance = getActivationChance(hand);

        if (!NumberUtil.percentCheck(chance)) {
            return false;
        }

        int damage = getDamage(hand);

        int seconds = getSeconds(hand);

        int maxTicks = NumberUtil.getRandomInRange(3, 7);

        BleedThread.spawn(player, entity, damage, maxTicks, seconds);
        return true;
    }

    private static class BleedThread extends BukkitRunnable {

        public static int spawn(Player player, Entity entity, int damage, int maxTicks, int seconds) {
            BleedThread thread = new BleedThread(player, entity, damage, maxTicks);

            BukkitTask task = thread.runTaskTimer(AdventureLoot.getInstance(), TimeHandler.getTimeInTicks(seconds, TimeType.SECOND), TimeHandler.getTimeInTicks(seconds, TimeType.SECOND));
            return task.getTaskId();
        }

        private int damage;
        private int maxTicks;
        private int ticks = 0;
        private UUID entityId;
        private UUID playerId;

        public BleedThread(Player player, Entity entity, int damage, int maxTicks) {
            this.playerId = player.getUniqueId();
            this.entityId = entity.getUniqueId();
            this.damage = damage;
            this.maxTicks = maxTicks;
        }

        @Override
        public void run() {
            if (ticks >= maxTicks) {
                cancel();
                return;
            }

            if (!Players.isOnline(playerId)) {
                cancel();
                return;
            }

            Player damager = Players.getPlayer(playerId);

            LivingEntity entity = Entities.getEntityByUUID(entityId);

            if (entity == null || entity.isDead() || !entity.isValid()) {
                cancel();
                return;
            }

            Entities.damage(entity, damage, damager);
            Effects.playBleedEffect(entity, 4);

            ticks += 1;
        }
    }
}
