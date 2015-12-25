package com.caved_in.adventurecraft.core.listener;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerGiveCatMilkListener implements Listener {

    private static PlayerGiveCatMilkListener instance = null;

    public static PlayerGiveCatMilkListener getInstance() {
        if (instance == null) {
            instance = new PlayerGiveCatMilkListener();
        }
        return instance;
    }

    protected PlayerGiveCatMilkListener() {

    }

    @EventHandler
    public void onPlayerGiveCatMilk(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        Player player = e.getPlayer();

        if (Players.handIsEmpty(player)) {
            return;
        }

        if (!(entity instanceof Ocelot)) {
            return;
        }

        ItemStack hand = player.getItemInHand();
        if (hand.getType() != Material.MILK_BUCKET) {
            return;
        }

        Ocelot cat = (Ocelot) entity;

        boolean tamed = cat.isTamed();

        AnimalTamer owner = null;
        if (tamed) {
            owner = cat.getOwner();
        }

        if (owner != null) {
            if (owner.getUniqueId().equals(player.getUniqueId())) {
                Chat.actionMessage(player, "&c&lThey slurp up the milk and cuddle you with looooove!");
            } else {
                Chat.actionMessage(player, "&c&lYou give cat some milk and it thanks you with affection");
            }

            ParticleEffects.sendToLocation(ParticleEffects.HEART, cat.getEyeLocation(), NumberUtil.getRandomInRange(10, 15));
            Entities.restoreHealth(cat);
            Entities.removePotionEffects(cat);
            Players.removeFromHand(player, 1);
            return;
        }

        cat.setOwner(player);
        cat.setTamed(true);
        ParticleEffects.sendToLocation(ParticleEffects.HEART, cat.getEyeLocation(), NumberUtil.getRandomInRange(10, 15));
        Entities.restoreHealth(cat);
        Entities.removePotionEffects(cat);

        Chat.actionMessage(player, "&a&lYou tame the kitty, and gain yourself a new companion!");
        //todo implement giving nametag to new owner.
        Players.removeFromHand(player, 1);
    }
}
