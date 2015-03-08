package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.commons.config.XmlEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "enchantment")
public class ChancedEnchantment extends XmlEnchantment {
	@Attribute(name = "chance")
	private int chance = 10;

	public ChancedEnchantment(@Attribute(name = "name") String enchantName, @Attribute(name = "level") int level, @Attribute(name = "glow", required = false) boolean glow, @Attribute(name = "chance") int chance) {
		super(enchantName, level, glow);
		this.chance = chance;
	}

	public ChancedEnchantment(int chance, Enchantment enchantment, int level) {
		super(enchantment, level);
		chance(chance);
	}

	public ChancedEnchantment() {

	}

	public ChancedEnchantment enchantment(Enchantment enchantment) {
		super.enchantment(enchantment);
		return this;
	}

	public ChancedEnchantment level(int level) {
		super.level(level);
		return this;
	}

	public ChancedEnchantment chance(int chance) {
		this.chance = chance;
		return this;
	}

	public int getChance() {
		return chance;
	}
}