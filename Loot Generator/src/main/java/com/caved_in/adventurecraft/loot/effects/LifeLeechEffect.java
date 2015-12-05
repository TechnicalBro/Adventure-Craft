package com.caved_in.adventurecraft.loot.effects;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.utilities.NumberUtil;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class LifeLeechEffect implements ItemEffect {
    private static LifeLeechEffect instance = null;

    public static LifeLeechEffect getInstance() {
        if (instance == null) {
            instance = new LifeLeechEffect();
        }
        return instance;
    }

    @Override
    public String name() {
        return "Life Leech";
    }

    @Override
    public boolean verify(ItemStack item) {
        return Items.loreContains(item, "- Life Leech");
    }

    @Override
    public boolean onPlayerDamagePlayer(Player attacked, Player damaged, double damage) {
        return onPlayerDamage(attacked, damaged, damage);
    }

    @Override
    public boolean onPlayerBreakBlock(Player player, Block block) {
        return false;
    }

    @Override
    public boolean onPlayerDrop(Player player, Item item) {
        ParticleEffects.sendToLocation(ParticleEffects.NOTE, item.getLocation(), NumberUtil.getRandomInRange(5, 10));
        return true;
    }

    @Override
    public boolean onPlayerDamageEntity(Player player, Entity entity, double damage) {
        return false;
    }

    @Override
    public boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity, double damage) {
        return onPlayerDamage(player, entity, damage);
    }

    public boolean onPlayerDamage(Player player, Damageable entity, double damage) {
        ItemStack hand = player.getItemInHand();

        if (!NumberUtil.percentCheck(getActivationChance(hand))) {
            return false;
        }

        ParticleEffects.sendToLocation(ParticleEffects.HEART, player.getLocation(), NumberUtil.getRandomInRange(5, 10));
        Sounds.playSound(player, Sound.FIZZ);
        double healed = NumberUtil.round(damage / 2, 1);

        Players.restoreHealth(player,healed);
        return true;
    }

    private static final int MIN_CHANCE = 5;
    private static final int MAX_CHANCE = 15;

    private String getLeechLine(ItemStack hand) {
        if (!verify(hand)) {
            return null;
        }

        int line = Items.getLoreLineNumberContaining(hand, "Life Leech") + 1;
        return Items.getLore(hand, line);
    }

    @Override
    public void apply(ItemStack item) {
        if (verify(item)) {
            return;
        }

        int chance = NumberUtil.getRandomInRange(MIN_CHANCE, MAX_CHANCE);

        Items.addLore(item, "- &a&lLife Leech");
        Items.addLore(item, " -&e &6" + chance + "&e% chance to heal &61/2&e damage dealt");
    }


    @Override
    public int getActivationChance(ItemStack item) {
        String leech = getLeechLine(item);

        Validate.notNull(leech);

        leech = ChatColor.stripColor(leech).replace(" -", "");

        String chanceStr = StringUtils.substringBetween(leech, " ", "%");

        return Integer.parseInt(chanceStr.replace(" ", ""));
    }
}
