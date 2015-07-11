package com.caved_in.adventurecraft.adventureitems.effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemEffect {
    String name();

    boolean verify(ItemStack item);

    boolean onPlayerDamagePlayer(Player attacked, Player damaged);

    boolean onPlayerBreakBlock(Player player, Block block);

    boolean onPlayerDrop(Player player, Item item);

    boolean onPlayerDamageEntity(Player player, Entity entity);

    boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity);

    void apply(ItemStack item);
}
