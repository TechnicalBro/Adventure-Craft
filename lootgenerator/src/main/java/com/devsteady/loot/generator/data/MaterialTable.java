package com.devsteady.loot.generator.data;

import com.devsteady.loot.generator.settings.LootSettings;
import com.devsteady.onyx.utilities.ListUtils;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.Skip;
import com.devsteady.onyx.yml.YamlConfig;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import java.util.*;
import java.util.stream.Collectors;

public class MaterialTable extends YamlConfig {
	@Path("allow-duplicates")
	private boolean duplicates = false;

	@Path("materials")
	private List<ChancedItemData> materials = new ArrayList<>();

	@Skip
	private LootSettings parent;

	@Skip
	private ChancedItemData lastGenerated = null;

	@Deprecated
	public MaterialTable(MaterialData data) {
		materials.add(ChancedItemData.of(100,data));
	}

	public MaterialTable parent(LootSettings parent) {
		this.parent = parent;
		return this;
	}

	public MaterialTable add(ChancedItemData data) {
		this.materials.add(data);
		clearDuplicates();
		return this;
	}

	public MaterialTable add(int chance, Material material) {
		materials.add(new ChancedItemData(chance,material,0));
		clearDuplicates();
		return this;
	}

	public MaterialTable add(int chance, Material material, int datavalue) {
		materials.add(new ChancedItemData(chance,material,datavalue));
		clearDuplicates();
		return this;
	}

	public boolean has(Material material) {
		return materials.stream().anyMatch(cm -> cm.getMaterial() == material);
	}

	public MaterialTable allowDuplicates(boolean val) {
		this.duplicates = val;
		return this;
	}

	public MaterialTable remove(Material m) {
		if (materials.size() > 0) {
			materials = materials.stream().filter(cm -> cm.getMaterial() != m).collect(Collectors.toList());
		}
		return this;
	}

	public ChancedItemData getRandomData() {
		if (materials.isEmpty()) {
			return null;
		}

		ChancedItemData data = ListUtils.getRandom(materials);

		lastGenerated = data;
		return data;
	}

	public ChancedItemData getLastGenerated() {
		return lastGenerated;
	}

	public List<ChancedItemData> getItemData() {
		return materials;
	}

	public LootSettings parent() {
		return parent;
	}

	private void clearDuplicates() {
		Set<MaterialData> types = new HashSet<>();

		List<ChancedItemData> newData = new ArrayList<>();
		for(ChancedItemData data : materials) {
			if (!types.contains(data.getMaterialData())) {
				types.add(data.getMaterialData());
				newData.add(data);
			}
		}

		materials = newData;
	}
}
