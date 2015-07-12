package com.caved_in.adventurecraft.afk.command;

import com.caved_in.commons.command.Command;
import org.bukkit.entity.Player;

public class AfkCommand {
	@Command(identifier = "afk",description = "Go afk! You won't take damage!",onlyPlayers = true)
	public void onAfkCommand(Player player) {
		
	}
}
