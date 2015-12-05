package com.caved_in.adventurecraft.loot.effects;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.utilities.NumberUtil;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class CriticalStrikeEffect implements ItemEffect {
    private static CriticalStrikeEffect instance = null;

    public static CriticalStrikeEffect getInstance() {
        if (instance == null) {
            instance = new CriticalStrikeEffect();
        }
        return instance;
    }

    @Override
    public String name() {
        return "Critical Strike";
    }

    @Override
    public boolean verify(ItemStack item) {
        return Items.loreContains(item, "- Critical Hit:");
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
        ParticleEffects.sendToLocation(ParticleEffects.EXPLOSION_LARGE, item.getLocation(), NumberUtil.getRandomInRange(5, 10));
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

        double extraDamage = NumberUtil.getRandomInRange(1.5, 5.0);

        Entities.damage(entity, extraDamage);
        ParticleEffects.sendToLocation(ParticleEffects.CRIT, entity.getLocation(), NumberUtil.getRandomInRange(5, 10));
        return true;
    }

    private static final int MIN_CHANCE = 1;
    private static final int MAX_CHANCE = 12;

    @Override
    public void apply(ItemStack item) {
        if (verify(item)) {
            return;
        }

        int chance = NumberUtil.getRandomInRange(MIN_CHANCE, MAX_CHANCE);

        Items.addLore(item, "-&e Critical Hit: &a+ &6" + chance + "&e% chance");
    }


    @Override
    public int getActivationChance(ItemStack item) {
        String critLine = Items.getLoreLineContaining(item, "- Critical Hit:");

        Validate.notNull(critLine);

        critLine = ChatColor.stripColor(critLine).replace("- Critical Hit: ", "");

        String chanceStr = StringUtils.substringBetween(critLine, "+ ", "%");

        return Integer.parseInt(chanceStr);
    }
}
