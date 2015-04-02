package com.caved_in.adventurecraft.core.listener;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.item.ToolType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBreakTreeListener implements Listener {

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!Items.isTool(player.getItemInHand(), ToolType.AXE)) {
			return;
		}

		Blocks.breakTreeSafely(player,event.getBlock(),true,true);
	}
}
