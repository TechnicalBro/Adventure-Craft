package com.caved_in.adventurecraft.adventureitems.effects;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FlameStrikeEffect implements ItemEffect {

	@Override
	public String name() {
		return "Flame Strike";
	}

	@Override
	public boolean verify(ItemStack item) {
		if (!Items.hasLore(item)) {
			return false;
		}

		return Items.loreContains(item, "+-+ Fire Strike");
	}

	@Override
	public boolean onPlayerDamagePlayer(Player attacked, Player damaged) {
		return onDamageEntity(attacked,damaged);
	}

	@Override
	public boolean onPlayerBreakBlock(Player player, Block block) {
		block.setType(Material.FIRE);
		return true;
	}

	@Override
	public boolean onPlayerDrop(Player player, Item item) {
		Entities.burn(player, 2, TimeType.SECOND);
		item.setPickupDelay(Integer.MAX_VALUE);
		item.setFireTicks(90);

		Worlds.clearDroppedItems(item.getLocation(), 2, 3, TimeType.SECOND);
		return true;
	}

	@Override
	public boolean onPlayerDamageEntity(Player player, Entity entity) {
		return onDamageEntity(player,entity);
	}

	@Override
	public boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity) {
		return onDamageEntity(player,entity);
	}

	@Override
	public void apply(ItemStack item) {
		if (verify(item)) {
			return;
		}

		Items.addLore(item,"&c+-+ Fire Strike");
		Chat.debug(Messages.itemInfo(item));
	}

	public boolean onDamageEntity(Player player, Entity entity) {
		Entities.burn(entity, NumberUtil.getRandomInRange(2, 4),TimeType.SECOND);
		return true;
	}
}
