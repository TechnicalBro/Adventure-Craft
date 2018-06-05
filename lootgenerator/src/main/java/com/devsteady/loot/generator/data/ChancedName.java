package com.devsteady.loot.generator.data;


import com.devsteady.onyx.utilities.NumberUtil;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.YamlConfig;

import java.util.Optional;

public class ChancedName extends YamlConfig{
	@Path("chance")
	private int chance = 100;

	@Path("name")
	private String name;

	@Path("prevent-slot")
	private String slot;

	public static ChancedName of(int chance, String name) {
		return new ChancedName(chance,name);
	}

	public ChancedName(int chance, String name) {
		this.chance = chance;
		this.name = name;
	}

	public int getChance() {
		return chance;
	}

	public String getName() {
		return name;
	}

	public ChancedName prevent(NameSlot slot) {
		this.slot = slot.getName();
		return this;
	}

	public NameSlot getPreventSlot() {
		return NameSlot.getSlot(this.slot);
	}

	public Optional<String> getChancedName() {
		return Optional.ofNullable(NumberUtil.percentCheck(getChance()) ? getName() : null);
	}
}
