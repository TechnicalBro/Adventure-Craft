package com.caved_in.adventurecraft.loot.effects;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.world.Worlds;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class FlameStrikeEffect implements ItemEffect {
    private static FlameStrikeEffect instance = null;

    public static FlameStrikeEffect getInstance() {
        if (instance == null) {
            instance = new FlameStrikeEffect();
        }

        return instance;
    }

    @Override
    public String name() {
        return "Flame Strike";
    }

    @Override
    public boolean verify(ItemStack item) {
        if (!Items.hasLore(item)) {
            return false;
        }

        return Items.loreContains(item, "- Flame Strike");
    }

    private String getFireLine(ItemStack item) {
        int fireLine = Items.getLoreLineNumberContaining(item, "- Flame Strike");

        return Items.getLore(item, fireLine + 1);
    }

    @Override
    public boolean onPlayerDamagePlayer(Player attacked, Player damaged, double damage) {
        return onDamageEntity(attacked, damaged, damage);
    }

    @Override
    public boolean onPlayerBreakBlock(Player player, Block block) {
        block.setType(Material.FIRE);
        return true;
    }

    @Override
    public boolean onPlayerDrop(Player player, Item item) {
        Entities.burn(player, 2, TimeType.SECOND);
        item.setPickupDelay(Integer.MAX_VALUE);
        item.setFireTicks(90);

        Worlds.clearDroppedItems(item.getLocation(), 2, 3, TimeType.SECOND);
        return true;
    }

    @Override
    public boolean onPlayerDamageEntity(Player player, Entity entity, double dmg) {
        return onDamageEntity(player, entity, dmg);
    }

    @Override
    public boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity, double damage) {
        return onDamageEntity(player, entity, damage);
    }


    private static final int MIN_CHANCE = 1;
    private static final int MAX_CHANCE = 11;

    private static final int MIN_DAMAGE = 1;

    private static final int MAX_DAMAGE = 9;

    private static final int MIN_SECONDS = 1;

    private static final int MAX_SECONDS = 6;

    @Override
    public void apply(ItemStack item) {
        if (verify(item)) {
            return;
        }

        int chance = NumberUtil.getRandomInRange(MIN_CHANCE, MAX_CHANCE);

        int damage = NumberUtil.getRandomInRange(MIN_DAMAGE, MAX_DAMAGE);

        int seconds = NumberUtil.getRandomInRange(MIN_SECONDS, MAX_SECONDS);

        Items.addLore(item, "- &c&lFlame Strike");
        Items.addLore(item, " - &6" + chance + "&e% chance to deal &6" + damage + "&e/&6" + seconds + "&es");
    }

    @Override
    public int getActivationChance(ItemStack item) {
        String flameLine = getFireLine(item);
        flameLine = ChatColor.stripColor(flameLine).replace(" -", "");
        String chanceLine = StringUtils.substringBetween(flameLine, " ", "% chance");
        return Integer.parseInt(chanceLine.replace(" ", ""));
    }

    public int getDamage(ItemStack item) {
        String fireLine = getFireLine(item);
        fireLine = ChatColor.stripColor(fireLine).replaceAll("  -", "");
        String damage = StringUtils.substringBetween(fireLine, "deal ", "/");
        return Integer.parseInt(damage);
    }

    public int getSeconds(ItemStack item) {
        String fireLine = getFireLine(item);
        fireLine = ChatColor.stripColor(fireLine).replace("  --", "");
        String seconds = StringUtils.substringBetween(fireLine, "/", "s");
        return Integer.parseInt(seconds);
    }

    public boolean onDamageEntity(Player player, Entity entity, double damage) {
        ItemStack hand = player.getItemInHand();

        int chance = getActivationChance(hand);

        if (!NumberUtil.percentCheck(chance)) {
            return false;
        }

        int itemDamage = getDamage(hand);

        int seconds = getSeconds(hand);

        int maxTicks = NumberUtil.getRandomInRange(2, 4);

        FlameStrikeTickDamageThread.spawn(player, entity, itemDamage, maxTicks, seconds);
        return true;
    }

    private static class FlameStrikeTickDamageThread extends BukkitRunnable {

        public static int spawn(Player player, Entity entity, int damage, int maxTicks, int seconds) {
            FlameStrikeTickDamageThread thread = new FlameStrikeTickDamageThread(player, entity, damage, maxTicks);

            BukkitTask task = thread.runTaskTimer(AdventureLoot.getInstance(), TimeHandler.getTimeInTicks(seconds, TimeType.SECOND), TimeHandler.getTimeInTicks(seconds, TimeType.SECOND));
            return task.getTaskId();
        }

        private int damage;
        private int maxTicks;
        private int ticks = 0;
        private UUID entityId;
        private UUID playerId;

        public FlameStrikeTickDamageThread(Player player, Entity entity, int damage, int maxTicks) {
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

            LivingEntity entity = Entities.getEntityByUUID(entityId);

            if (entity == null || entity.isDead() || !entity.isValid()) {
                cancel();
                return;
            }

            Entities.damage(entity, damage);
            ParticleEffects.sendToLocation(ParticleEffects.DRIP_LAVA, entity.getLocation(), 7);

            ticks += 1;
        }
    }
}
