package com.caved_in.adventurecraft.adventureitems.listeners;

import com.caved_in.adventurecraft.adventureitems.AdventureItems;
import com.caved_in.adventurecraft.adventureitems.effects.ItemEffect;
import com.caved_in.adventurecraft.adventureitems.effects.ItemEffectHandler;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.event.PlayerDamagePlayerEvent;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class ItemEffectListener implements Listener {

    private ItemEffectHandler handler;

    public ItemEffectListener(AdventureItems plugin) {
        handler = plugin.getItemEffectHandler();
    }

    @EventHandler
    public void onPlayerDamagePlayer(PlayerDamagePlayerEvent e) {
        Player damaged = e.getTarget();
        Player attacker = e.getPlayer();

        if (Players.handIsEmpty(attacker)) {
            return;
        }

        ItemStack hand = attacker.getItemInHand();
        if (!handler.hasEffect(hand)) {
            return;
        }

        Set<ItemEffect> effects = handler.getEffects(hand);

        for(ItemEffect effect : effects) {
            Chat.message(attacker,String.format("&cEffect &a%s &cis being used against %s",effect.name(),damaged.getName()));
            if (!effect.onPlayerDamagePlayer(attacker,damaged)) {
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

        for(ItemEffect effect : effects) {
            Chat.message(player,String.format("Player %s has item proc effect %s when dropping",player.getName(),effect.name()));
            //If the item isn't supposed to be dropped, then cancel the event- Some
            //Effects won't let this happen, rather destroys them- sets aflame, etc
            //Let the effect do the heavy lifting.
            if (!effect.onPlayerDrop(player,dropped)) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {
        Entity entityDamager = e.getDamager();

        Entity damaged = e.getEntity();

        //We have another event listener to handle player specific damaging.
        if (damaged instanceof Player) {
            return;
        }

        if (!(entityDamager instanceof Player)) {
            return;
        }

        Player damager = (Player)entityDamager;

        if (!Players.hasItemInHand(damager)) {
            return;
        }

        ItemStack hand = damager.getItemInHand();

        if (!handler.hasEffect(hand)) {
            return;
        }

        Set<ItemEffect> effects = handler.getEffects(hand);

        //We have another method listening to livingentity damage
        if (damaged instanceof LivingEntity) {
            for (ItemEffect effect : effects) {
                if (effect.onPlayerDamageLivingEntity(damager, (LivingEntity)damaged)) {
                    Chat.message(damager, "&cYour Item has the effect: " + effect.name() + " become &aActive");
                } else {
                    //todo potentially cancel the event?
                }
            }
        } else {
            for (ItemEffect effect : effects) {
                if (effect.onPlayerDamageEntity(damager, damaged)) {
                    Chat.message(damager, "&cYour Item has the effect: " + effect.name() + " become &aActive");
                } else {
                    //todo potentially cancel the event?
                }
            }
        }

        //Todo check damage applications despite the item effects

    }

}
