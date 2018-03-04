package com.devsteady.loot.effects;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BackstabEffect implements ItemEffect {
    private static BackstabEffect effect = null;

    public static BackstabEffect getInstance() {
        if (effect == null) {
            effect = new BackstabEffect();
        }
        return effect;
    }

    @Override
    public String name() {
        return "BackStab";
    }

    @Override
    public boolean verify(ItemStack item) {
        if (!Items.hasLore(item)) {
            return false;
        }

        return Items.loreContains(item, "- BackStab:");
    }

    @Override
    public boolean onPlayerDamagePlayer(Player attacker, Player damaged, double damage) {
        return onPlayerDamageLivingEntity(attacker, damaged, damage);
    }

    @Override
    public boolean onPlayerBreakBlock(Player player, Block block) {
        return false;
    }

    @Override
    public boolean onPlayerDrop(Player player, Item item) {
        return false;
    }

    @Override
    public boolean onPlayerDamageEntity(Player player, Entity entity, double damage) {
        return false;
    }

    @Override
    public boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity, double damage) {
        if (!Locations.isBehind(player, entity)) {
            return false;
        }

        ItemStack hand = player.getItemInHand();
        int dmg = getDamage(hand);
        if (dmg == -1) {
            Chat.messageOps("Player " + player.getName() + " has a glitched item with BackStab");
            return false;
        }

        Entities.damage(entity, dmg);
        return true;
    }

    private static final int MIN_DAMAGE = 3;
    private static final int MAX_DAMAGE = 15;

    @Override
    public void apply(ItemStack item) {
        if (verify(item)) {
            return;
        }

        int damage = NumberUtil.getRandomInRange(MIN_DAMAGE, MAX_DAMAGE);

        Items.addLore(item, "&e- &cBackStab&e: &a+&6" + damage + " &edamage on BackStab");
    }

    public int getDamage(ItemStack item) {
        if (!verify(item)) {
            return -1;
        }

        String backLine = StringUtil.stripColor(Items.getLoreLineContaining(item, "- BackStab"));
        String damage = StringUtils.substringBetween(backLine, "+", " damage");
        return Integer.parseInt(damage);
    }
}
