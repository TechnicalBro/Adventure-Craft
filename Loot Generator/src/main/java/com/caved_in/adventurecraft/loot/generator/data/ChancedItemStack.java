package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.commons.config.XmlItemStack;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "chanced-item")
public class ChancedItemStack {

	@Element(name = "item",type = XmlItemStack.class)
	private XmlItemStack item;

	@Attribute(name = "chance")
	private int chance = 100;

	public ChancedItemStack(@Element(name = "item",type = XmlItemStack.class)XmlItemStack item, @Attribute(name= "chance") int chance) {
		this.item = item;
		this.chance = chance;
	}

	public ChancedItemStack(ItemStack item, int chance) {
		this(XmlItemStack.fromItem(item), chance);
	}

	public ChancedItemStack() {

	}

	public ChancedItemStack item(ItemStack item) {
		this.item = XmlItemStack.fromItem(item);
		return this;
	}

	public ItemStack item() {
		return this.item.getItemStack();
	}

	public ChancedItemStack chance(int chance) {
		this.chance = chance;
		return this;
	}

	public int chance() {
		return chance;
	}

}
