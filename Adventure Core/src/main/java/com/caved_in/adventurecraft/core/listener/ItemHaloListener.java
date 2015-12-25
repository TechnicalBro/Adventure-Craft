package com.caved_in.adventurecraft.core.listener;

import com.caved_in.adventurecraft.gems.AdventureGems;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.commons.item.Items;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemHaloListener implements Listener {
    private static ItemHaloListener instance = null;

    public static ItemHaloListener getInstance() {
        if (instance == null) {
            instance = new ItemHaloListener();
        }

        return instance;
    }

    protected ItemHaloListener() {

    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDrop(PlayerDropItemEvent e) {
        if (e.isCancelled()) {
            return;
        }

        if (e.getItemDrop() == null) {
            return;
        }

        ItemStack dropItem = e.getItemDrop().getItemStack();

        if (dropItem == null) {
            return;
        }

        if (Items.hasEnchantments(dropItem) || AdventureGems.API.isGem(dropItem) || AdventureLoot.API.hasDamageRange(dropItem) || AdventureLoot.API.hasItemEffect(dropItem)) {
            makeHalo(e.getItemDrop());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemSpawn(ItemSpawnEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Item item = e.getEntity();

        if (item == null) {
            return;
        }

        ItemStack dropItem = e.getEntity().getItemStack();

        if (dropItem == null) {
            return;
        }

        if (Items.hasEnchantments(dropItem) || AdventureGems.API.isGem(dropItem) || AdventureLoot.API.hasDamageRange(dropItem) || AdventureLoot.API.hasItemEffect(dropItem)) {
            makeHalo(item);
        }
    }

    public static void makeHalo(Item item) {
        String name = Items.getName(item.getItemStack());
        item.setCustomNameVisible(true);
        item.setCustomName(name);
    }
}
