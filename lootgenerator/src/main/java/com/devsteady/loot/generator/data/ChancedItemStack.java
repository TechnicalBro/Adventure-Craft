package com.devsteady.loot.generator.data;

import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.YamlConfig;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class ChancedItemStack extends YamlConfig {

	@Path("item")
	@Getter
	private ItemStack item;

	@Path("chance")
	@Getter
	private int chance = 100;
	
	public static ChancedItemStack of(ItemStack item, int chance) {
		return new ChancedItemStack(item,chance);
	}

	public ChancedItemStack(ItemStack item,int chance) {
		this.item = item;
		this.chance = chance;
	}

	public ChancedItemStack() {

	}

	public ChancedItemStack item(ItemStack item) {
		this.item = item;
		return this;
	}

	public ChancedItemStack chance(int chance) {
		this.chance = chance;
		return this;
	}
}
