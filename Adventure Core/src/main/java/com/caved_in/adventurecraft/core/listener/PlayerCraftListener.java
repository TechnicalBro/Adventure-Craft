package com.caved_in.adventurecraft.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Recipe;

public class PlayerCraftListener implements Listener {

	@EventHandler
	public void onPlayerCraft(CraftItemEvent e) {
		InventoryAction action = e.getAction();
		InventoryType.SlotType slotType = e.getSlotType();
		int slotNum = e.getSlot();
		CraftingInventory craftInv = e.getInventory();
		ClickType clickType = e.getClick();
		Recipe recipe = e.getRecipe();

		//todo implement loot generation / custom items for crafting
		
	}

}
