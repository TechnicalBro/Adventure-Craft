package com.devsteady.loot.generator.settings;

import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.Skip;
import com.devsteady.onyx.yml.YamlConfig;
import lombok.Getter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString(of = {"damageDisplay", "damageFormat", "lore"})
public class ItemLoreSettings extends YamlConfig {
	@Path("display-damage")
	@Getter
	private boolean damageDisplay = true;

	@Path("damage-format")
	//todo implement method to check damage ranges on item based on the damageFormat string
	@Getter
	private String damageFormat = "&cDeals &e%s&c to &e%s&c damage!";

	@Getter
	@Path("rarity-format")
	private String rarityFormat = "&eItem Rarity: &a%s";

	@Getter
	@Path("display-rarity")
	private boolean displayRarity = true;

	@Path("lines")
	private List<String> lore = new ArrayList<>();

	@Skip
	private LootSettings parent = null;

	public ItemLoreSettings(boolean damageDisplay,String damageFormat,List<String> lore) {
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
	
	public ItemLoreSettings displayRarity(boolean val) {
		this.displayRarity = val;
		return this;
	}
	
	public ItemLoreSettings rarityFormat(String s) {
		this.rarityFormat = s;
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

	public ItemLoreSettings parent(LootSettings parent) {
		this.parent = parent;
		return this;
	}

	public LootSettings parent() {
		return parent;
	}
}