package com.devsteady.loot.effects;

import com.caved_in.commons.threading.RunnableManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemEffect {
    String name();

    boolean verify(ItemStack item);

    default int getActivationChance(ItemStack item) {
        return 0;
    }

    boolean onPlayerDamagePlayer(Player attacker, Player damaged, double damage);

	boolean onPlayerBreakBlock(Player player, Block block);

	boolean onPlayerDrop(Player player, Item item);

	boolean onPlayerDamageEntity(Player player, Entity entity,double damage);

	boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity, double damage);

    void apply(ItemStack item);

    default boolean onActivate(Player player) {
        return false;
    }

}
