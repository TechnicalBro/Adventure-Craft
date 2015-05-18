package com.caved_in.adventurecraft.shop.shop;

import com.caved_in.commons.config.XmlItemStack;
import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;

import java.util.List;

//todo implement drag & drop shop arrangement
public class ShopCategory {
	private int slot;

	private List<ShopItem> categoryItems = Lists.newArrayListWithCapacity(48);

	private XmlItemStack displayItem;

	public ShopCategory() {

	}

	public ShopCategory slot(int slot) {
		this.slot = slot;
		return this;
	}

	public ShopCategory addItem(ShopItem item) {
		categoryItems.add(item);
		return this;
	}

	public ShopCategory displayItem(ItemStack item) {
		this.displayItem = XmlItemStack.fromItem(item);
		return this;
	}

	public int getSlot() {
		return slot;
	}

	public List<ShopItem> getCategoryItems() {
		return categoryItems;
	}

	public XmlItemStack getDisplayItem() {
		return displayItem;
	}
}
