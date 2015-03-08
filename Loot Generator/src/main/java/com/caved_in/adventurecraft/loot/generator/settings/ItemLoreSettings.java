package com.caved_in.adventurecraft.loot.generator.settings;

import lombok.ToString;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Root(name = "lore-settings")
@ToString(of = {"damageDisplay", "damageFormat", "lore"})
public class ItemLoreSettings {
	@Element(name = "display-damage")
	private boolean damageDisplay = true;

	@Element(name = "damage-format", required = false)
	private String damageFormat = "&cDeals &e%s&c to &e%s&c damage!";

	private String rarityFormat = "&eItem Rarity: &a%s";

	private boolean displayRarity = true;

	@ElementList(name = "lines", entry = "line", required = false)
	private List<String> lore = new ArrayList<>();

	public ItemLoreSettings(
			@Element(name = "display-damage") boolean damageDisplay,
			@Element(name = "damage-format", required = false) String damageFormat,
			@ElementList(name = "lines", entry = "line", required = false) List<String> lore) {
		this.lore = lore;
		this.damageDisplay = damageDisplay;
		this.damageFormat = damageFormat;
	}

	public ItemLoreSettings() {
		lore = new ArrayList<>();
	}

	public ItemLoreSettings displayDamage(boolean val) {
		this.damageDisplay = val;
		return this;
	}

	public ItemLoreSettings displayDamageFormat(String s) {
		this.damageFormat = s;
		return this;
	}

	public ItemLoreSettings addLore(String... lines) {
		Collections.addAll(lore, lines);
		return this;
	}

	public boolean hasLore() {
		return lore.size() > 0;
	}

	public boolean hasDamageDisplayed() {
		return damageDisplay;
	}

	public String getDamageFormat() {
		return damageFormat;
	}

	public List<String> getLore() {
		return lore;
	}

	public String getRarityFormat() {
		return rarityFormat;
	}

	public boolean hasRarityDisplayed() {
		return displayRarity;
	}
}