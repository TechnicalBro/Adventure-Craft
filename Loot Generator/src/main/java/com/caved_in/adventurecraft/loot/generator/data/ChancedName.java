package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.commons.utilities.NumberUtil;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import javax.print.attribute.standard.NumberUp;
import java.util.Optional;
import java.util.Set;

@Root(name = "chanced-name")
public class ChancedName {
	@Attribute(name = "chance")
	private int chance = 100;

	@Attribute(name = "name")
	private String name;

	private NameSlot preventSlot = null;

	public static ChancedName of(int chance, String name) {
		return new ChancedName(chance,name);
	}

	public ChancedName(@Attribute(name = "chance")int chance, @Attribute(name = "name")String name) {
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
		this.preventSlot = slot;
		return this;
	}

	public NameSlot getPreventSlot() {
		return preventSlot;
	}

	public Optional<String> getChancedName() {
		return Optional.ofNullable(NumberUtil.percentCheck(getChance()) ? getName() : null);
	}
}
