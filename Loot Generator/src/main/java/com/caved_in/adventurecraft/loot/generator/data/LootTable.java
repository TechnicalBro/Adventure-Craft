package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.commons.utilities.ListUtils;

import javax.swing.plaf.ListUI;
import java.util.ArrayList;
import java.util.List;

public class LootTable {
	private List<LootSettings> lootSettings = new ArrayList<>();

	public LootTable() {

	}

	public LootTable add(LootSettings settings) {
		this.lootSettings.add(settings);
		return this;
	}

	public LootSettings getRandom() {
		if (lootSettings.size() <= 1) {
			return lootSettings.get(0);
		}

		return ListUtils.getRandom(lootSettings);
	}
}
