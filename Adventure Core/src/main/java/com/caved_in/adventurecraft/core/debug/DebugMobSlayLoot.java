package com.caved_in.adventurecraft.core.debug;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.adventurecraft.core.listener.MobSlayListener;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class DebugMobSlayLoot implements DebugAction {
	@Override
	public void doAction(Player player, String... strings) {
		int amount = 10;

		for(int i = 0; i < amount; i++) {
			Optional<ItemStack> item = AdventureLoot.API.generateItem(MobSlayListener.MOB_LOOT);

			if (!item.isPresent()) {
				continue;
			}

			Players.giveItem(player, item.get(),true);
		}
	}

	@Override
	public String getActionName() {
		return "ac_mob_loot_generation";
	}
}
