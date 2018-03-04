package com.devsteady.loot.generator.data;

import com.devsteady.loot.generator.settings.LootSettings;
import com.caved_in.commons.utilities.ListUtils;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "loot-table")
public class LootTable {

	@ElementList(name = "loot-settings",entry = "settings",type = LootSettings.class, required = false)
	private List<LootSettings> lootSettings = new ArrayList<>();

	@ElementList(name = "chanced-items",entry = "item",type = ChancedItemStack.class,required = false)
	private List<ChancedItemStack> chancedItems = new ArrayList<>();

	@ElementList(name = "children",entry = "table",type = LootTable.class,required = false)
	private List<LootTable> childTables = new ArrayList<>();

	public LootTable(
			@ElementList(name = "loot-settings",entry = "settings",type = LootSettings.class, required = false)List<LootSettings> settings,
			@ElementList(name = "chanced-items",entry = "item",type = ChancedItemStack.class,required = false)List<ChancedItemStack> items,
			@ElementList(name = "children",entry = "table",type = LootTable.class,required = false)List<LootTable> children
			)
	{
		this.lootSettings = settings;
		this.chancedItems = items;
		this.childTables = children;
	}

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

    //todo implement methods to clean the item effects off of lore. Maybe?

	public LootSettings getRandom() {
		if (lootSettings.isEmpty()) {
			return null;
		}

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
