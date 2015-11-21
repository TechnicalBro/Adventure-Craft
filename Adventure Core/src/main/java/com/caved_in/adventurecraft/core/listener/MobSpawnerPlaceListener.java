package com.caved_in.adventurecraft.core.listener;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.Items;
import com.mysql.jdbc.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MobSpawnerPlaceListener implements Listener {

    @EventHandler
    public void onMobSpawnerPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (e.isCancelled()) {
            return;
        }

        if (block.getType() != Material.MOB_SPAWNER) {
            return;
        }

        ItemStack hand = e.getItemInHand();

        if (!Items.hasLore(hand)) {
            return;
        }

        String mobLore = Items.getLore(hand,0);

        if (mobLore == null || StringUtils.isNullOrEmpty(mobLore)) {
            return;
        }

        EntityType type = EntityType.valueOf(mobLore);

        CreatureSpawner spawner = (CreatureSpawner)block.getState();

        spawner.setSpawnedType(type);

        Chat.message(player,"&cSet spawner to &e" + mobLore);
    }
}
