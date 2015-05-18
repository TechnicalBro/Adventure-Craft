package com.caved_in.adventurecraft.loot.listener;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageEntityListener implements Listener {

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		Entity damagerEntity = e.getDamager();

		//todo look for custom data values and attributes
	}

}
