package com.caved_in.adventurecraft.shop.shop;

import com.caved_in.commons.config.XmlItemStack;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Element;

public class ShopItem {

	@Element(name = "item",type = XmlItemStack.class)
	private XmlItemStack item;

	@Element(name = "store-price")
	private double storeSellPrice;

	@Element(name = "player-sell-price")
	private double playerSellPrice;

	public static ShopItem create(ItemStack item, double sell, double buy) {
		return new ShopItem(item,sell,buy);
	}

	public ShopItem(ItemStack item, double sell, double buy) {
		this.item = XmlItemStack.fromItem(item);
		this.storeSellPrice = sell;
		this.playerSellPrice = buy;
	}

	public ShopItem item(ItemStack item) {
		this.item = XmlItemStack.fromItem(item);
		return this;
	}

	public ShopItem storeSellPrice(double price) {
		this.storeSellPrice = price;
		return this;
	}

	public ShopItem storeBuyPrice(double price) {
		this.playerSellPrice = price;
		return this;
	}

	public ItemStack getItem() {
		return item.getItemStack();
	}

	public double getStoreSellPrice() {
		return storeSellPrice;
	}

	public double getPlayerSellPrice() {
		return playerSellPrice;
	}
}
