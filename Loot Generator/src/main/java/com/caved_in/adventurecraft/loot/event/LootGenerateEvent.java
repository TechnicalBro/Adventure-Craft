package com.caved_in.adventurecraft.loot.event;

import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class LootGenerateEvent extends Event {
	private static HandlerList handlers = new HandlerList();

	private LootSettings lootSettings;
	private ItemStack itemStack;

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public LootGenerateEvent(LootSettings settings, ItemStack item) {
		this.lootSettings = settings;
		this.itemStack = item;
	}

	public LootSettings getSettings() {
		return lootSettings;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
