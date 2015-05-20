package com.caved_in.adventurecraft.shop.menu;

import com.caved_in.adventurecraft.shop.shop.ShopItem;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.menu.MenuItem;
import org.bukkit.entity.Player;

public class ShopMenuItem extends MenuItem {
	private ShopItem item;

	public ShopMenuItem(ShopItem item) {
		super(Items.getFormattedMaterialName(item.getItem()),item.getItem().getData());
		this.item = item;
	}

	@Override
	public void onClick(Player player) {

	}
}
