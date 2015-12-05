package com.caved_in.adventurecraft.loot.listener;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.effects.ItemEffect;
import com.caved_in.adventurecraft.loot.generator.LootGenerator;
import com.caved_in.adventurecraft.loot.util.ItemHandler;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.event.PlayerDamagePlayerEvent;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.javatuples.Pair;

import java.util.Set;

public class ItemEffectListener implements Listener {

    private ItemHandler handler;

    public ItemEffectListener(AdventureLoot plugin) {
        handler = plugin.getItemEffectHandler();
    }

    @EventHandler
    public void onPlayerActivate(PlayerInteractEvent e) {
        if (!e.hasItem()) {
            return;
        }

        Player player = e.getPlayer();

        Action action = e.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack hand = e.getItem();

        if (!handler.hasEffect(hand)) {
            return;
        }

        Set<ItemEffect> effects = handler.getEffects(hand);

        for (ItemEffect effect : effects) {
            Chat.debug(String.format("Player %s has effect %s proc on Item", player.getName(), effect.name()));
            if (!effect.onActivate(player)) {
                //todo something on fail.
                continue;
            }
        }

    }

    @EventHandler
    public void onPlayerDamagePlayer(PlayerDamagePlayerEvent e) {
        Player damaged = e.getTarget();
        Player attacker = e.getPlayer();

        if (Players.handIsEmpty(attacker)) {
            return;
        }

        ItemStack hand = attacker.getItemInHand();

        /*
        Check if the item has a damage range, and if so apply it to the event.
         */
        if (handler.hasDamageRange(hand)) {
            Pair<Double, Double> damagePair = handler.getDamageRange(hand);
            Double damageMin = damagePair.getValue0();
            Double damageMax = damagePair.getValue1();

            double damage = NumberUtil.getRandomInRange(damageMin, damageMax);
            damage = NumberUtil.round(damage, 2);
            //todo verify damage ranges.
            //todo damage the damaged with a random in range of the values on the item.
            damaged.damage(damage);
//            e.setDamage(NumberUtil.getRandomInRange(damageMin, damageMax));
        }

        if (!handler.hasEffect(hand)) {
            return;
        }

        Set<ItemEffect> effects = handler.getEffects(hand);

        for (ItemEffect effect : effects) {
            if (!effect.onPlayerDamagePlayer(attacker, damaged, e.getDamage())) {
                //todo potentially cancel the event?
                continue;
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        Item dropped = e.getItemDrop();

        ItemStack droppedItemStack = dropped.getItemStack();

        if (!handler.hasEffect(droppedItemStack)) {
            return;
        }

        Set<ItemEffect> effects = handler.getEffects(droppedItemStack);

        for (ItemEffect effect : effects) {
            //If the item isn't supposed to be dropped, then cancel the event- Some
            //Effects won't let this happen, rather destroys them- sets aflame, etc
            //Let the effect do the heavy lifting.
            if (!effect.onPlayerDrop(player, dropped)) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {
        Entity entityDamager = e.getDamager();

        Entity damaged = e.getEntity();

        Player damager = null;

        //We have another event listener to handle player specific damaging.
        if (damaged instanceof Player) {
            return;
        }

        if (entityDamager.getType() == EntityType.ARROW) {
            Arrow arrow = (Arrow) entityDamager;
            ProjectileSource source = arrow.getShooter();

            if (source == null) {
                return;
            }

            if (!(source instanceof LivingEntity)) {
                return;
            }

            LivingEntity shooter = (LivingEntity) source;

            if (!(shooter instanceof Player)) {
                return;
            }
            damager = (Player) shooter;
        }

        if (entityDamager.getType() == EntityType.SNOWBALL) {
            Snowball snowball = (Snowball) entityDamager;
            ProjectileSource source = snowball.getShooter();

            if (source == null) {
                return;
            }

            if (!(source instanceof LivingEntity)) {
                return;
            }

            LivingEntity shooter = (LivingEntity) source;

            if (!(shooter instanceof Player)) {
                damager = (Player) shooter;
            }
        }

        if (damager == null && (entityDamager instanceof Player)) {
            damager = (Player) entityDamager;
        }

        if (damager == null) {
            return;
        }

        if (!Players.hasItemInHand(damager)) {
            return;
        }

        ItemStack hand = damager.getItemInHand();

        /*
        Check if the item has a damage range, and if so apply it to the event.
         */
        if (handler.hasDamageRange(hand)) {

            Pair<Double, Double> damagePair = handler.getDamageRange(hand);
            Double damageMin = damagePair.getValue0();
            Double damageMax = damagePair.getValue1();
            //todo verify damage ranges.

            double damage = NumberUtil.getRandomInRange(damageMin, damageMax);
            damage = NumberUtil.round(damage, 2);

            if (hand.getType() == Material.BOW && entityDamager.getType() != EntityType.ARROW) {
                e.setDamage(NumberUtil.round(damage / 2, 2));
                Chat.actionMessage(damager, "&7Whacking enemies with your Bow does half the Damage");
            } else {
                e.setDamage(damage);
            }
        }

        if (!handler.hasEffect(hand)) {
            return;
        }

        Set<ItemEffect> effects = handler.getEffects(hand);

        //We have another method listening to livingentity damage
        if (damaged instanceof LivingEntity) {
            for (ItemEffect effect : effects) {
                if (effect.onPlayerDamageLivingEntity(damager, (LivingEntity) damaged, e.getDamage())) {
                    Chat.debug("&cPlayer " + damager.getName() + " has the effect: " + effect.name() + " become &aActive");
                } else {
                    //todo potentially cancel the event?
                }
            }
        } else {
            for (ItemEffect effect : effects) {
                if (effect.onPlayerDamageEntity(damager, damaged,e.getDamage())) {
                    Chat.debug("&cPlayer " + damager.getName() + " has the effect: " + effect.name() + " become &aActive");
                } else {
                    //todo potentially cancel the event?
                }
            }
        }
    }

}
