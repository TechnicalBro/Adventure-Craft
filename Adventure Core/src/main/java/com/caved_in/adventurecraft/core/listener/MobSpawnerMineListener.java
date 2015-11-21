package com.caved_in.adventurecraft.core.listener;

import com.caved_in.commons.Messages;
import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class MobSpawnerMineListener implements Listener {

    @EventHandler
    public void onMobSpawnerMineEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (e.isCancelled()) {
            return;
        }

        if (Players.handIsEmpty(player)) {
            return;
        }

        if (block.getType() != Material.MOB_SPAWNER) {
            return;
        }

        ItemStack hand = player.getItemInHand();

        if (!Items.hasEnchantment(hand, Enchantment.SILK_TOUCH)) {
            return;
        }

        if (!Players.isPremium(player)) {
            Chat.actionMessage(player, "&7Purchasing either &bScout &7or &9Miner &7enables you to collect mob spawners");
            e.setCancelled(true);
            return;
        }

        BlockState state = block.getState();

        if (!(state instanceof CreatureSpawner)) {
            return;
        }

        CreatureSpawner spawner = (CreatureSpawner) state;

        //todo make itemstack with creature type in the lore

        EntityType spawnType = spawner.getSpawnedType();

        Chat.debug("Spawner @ " + Messages.locationCoords(block.getLocation()) + " is " + spawnType.name());

        ItemStack mobSpawner = ItemBuilder.of(Material.MOB_SPAWNER).lore(spawnType.name()).item();
        Players.giveItem(player, mobSpawner, true);
        e.setCancelled(true);
        Blocks.breakBlock(block,false,true);
    }
}
