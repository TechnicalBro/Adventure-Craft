package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.commons.utilities.ListUtils;
import org.bukkit.inventory.ItemStack;

import javax.swing.plaf.ListUI;
import java.util.ArrayList;
import java.util.List;

public class LootTable {
	private List<LootSettings> lootSettings = new ArrayList<>();

	private List<ChancedItemStack> chancedItems = new ArrayList<>();
	
	private List<LootTable> childTables = new ArrayList<>();

	public LootTable() {

	}
	
	public LootTable add(LootTable child) {
		childTables.add(child);
		return this;
	}

	public LootTable add(LootSettings settings) {
		this.lootSettings.add(settings);
		return this;
	}

	public LootTable add(int chance, ItemStack item) {
		this.chancedItems.add(new ChancedItemStack(item,chance));
		return this;
	}

	public LootTable add(ChancedItemStack item) {
		chancedItems.add(item);
		return this;
	}

	public LootSettings getRandom() {
		if (lootSettings.size() <= 1) {
			return lootSettings.get(0);
		}
		
		if (childTables.size() > 3) {
			return ListUtils.getRandom(childTables).getRandom();
		}

		return ListUtils.getRandom(lootSettings);
	}

	public boolean hasItems() {
		return getChancedItem() != null;
	}

	public ChancedItemStack getChancedItem() {
		List<ChancedItemStack> allChancedItems = new ArrayList<>();
		
		/*
		If the current loot table has child tables under it,
		then we'll need to add all the child items it contains to the list
		of all potential items.
		 */
		if (childTables.size() > 0) {
			for(LootTable table : childTables) {
				if (!table.hasItems()) {
					continue;
				}
				
				allChancedItems.addAll(table.getChancedItems());
			}
		}
		
		if (!chancedItems.isEmpty()) {
			allChancedItems.addAll(chancedItems);
		}
		
		if (allChancedItems.size() > 2) {
			return ListUtils.getRandom(allChancedItems);
		}
		
		return null;
	}
	
	public List<ChancedItemStack> getChancedItems() {
		return chancedItems;
	}
}
