package com.caved_in.adventurecraft.loot.effects;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.potion.Potions;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class PoisonEffect implements ItemEffect {
    private static PoisonEffect instance = null;

    public static PoisonEffect getInstance() {
        if (instance == null) {
            instance = new PoisonEffect();
        }

        return instance;
    }

    @Override
    public String name() {
        return "Poison";
    }

    @Override
    public boolean verify(ItemStack item) {
        if (!Items.hasLore(item)) {
            return false;
        }

        return Items.loreContains(item, "- Poison");
    }

    private String getPoisonLine(ItemStack item) {
        int poison = Items.getLoreLineNumberContaining(item, "- Poison");

        return Items.getLore(item, poison + 1);
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
        return true;
    }

    @Override
    public boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity, double damage) {
        return onDamageEntity(player, entity, damage);
    }


    private static final int MIN_CHANCE = 2;
    private static final int MAX_CHANCE = 15;

    private static final int MIN_DAMAGE = 1;

    private static final int MAX_DAMAGE = 10;

    private static final int MIN_SECONDS = 3;

    private static final int MAX_SECONDS = 10;

    @Override
    public void apply(ItemStack item) {
        if (verify(item)) {
            return;
        }

        int chance = NumberUtil.getRandomInRange(MIN_CHANCE, MAX_CHANCE);

        int seconds = NumberUtil.getRandomInRange(MIN_SECONDS, MAX_SECONDS);

        Items.addLore(item, "- &aPoison:");
        Items.addLore(item, " - &6" + chance + "&e% chance to deal &aPoison&e for &c" + seconds + "&es!");
    }

    @Override
    public int getActivationChance(ItemStack item) {
        String poison = getPoisonLine(item);
        poison = ChatColor.stripColor(poison);
        String chanceLine = StringUtils.substringBetween(poison, " - ", "% chance");
        return Integer.parseInt(chanceLine.replace(" ", ""));
    }

    public int getSeconds(ItemStack item) {
        String poison = getPoisonLine(item);
        poison = ChatColor.stripColor(poison).replace("  - ", "");
        String seconds = StringUtils.substringBetween(poison, "for ", "s!");
        return Integer.parseInt(seconds.replace(" ", ""));
    }

    public boolean onDamageEntity(Player player, LivingEntity entity, double damage) {
        ItemStack hand = player.getItemInHand();

        int chance = getActivationChance(hand);

        if (!NumberUtil.percentCheck(chance)) {
            return false;
        }

        int seconds = getSeconds(hand);
        Entities.addPotionEffect(entity, Potions.getPotionEffect(PotionEffectType.POISON, 1, (int) TimeHandler.getTimeInTicks(seconds, TimeType.SECOND)));
        ParticleEffects.sendToLocation(ParticleEffects.MAGIC_CRIT, entity.getEyeLocation(), NumberUtil.getRandomInRange(7, 10));
        return true;
    }
}
