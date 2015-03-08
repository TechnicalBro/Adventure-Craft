package com.caved_in.adventurecraft.loot.command;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import org.bukkit.entity.Player;

public class LootCommand {

	@Command(identifier = "loot",permissions = "adventurecraft.loot.admin")
	public void onLootCommand(Player player) {
		Chat.message(player,"&cComing Soon");
	}
}
