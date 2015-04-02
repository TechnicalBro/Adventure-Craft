package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.commons.utilities.ListUtils;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.*;
import java.util.stream.Collectors;

@Root(name = "material-table")
public class MaterialTable {
	@Element(name = "default-material",type = ChancedItemData.class)
	private MaterialData defaultData = new MaterialData(Material.LEATHER);

	@Element(name = "allow-duplicate-entries")
	private boolean duplicates = false;

	@ElementList(name = "materials",entry = "m",required = false,type = ChancedItemData.class)
	private List<ChancedItemData> materials = new ArrayList<>();

	private LootSettings parent;

	private ChancedItemData lastGenerated = null;

	public MaterialTable(MaterialData defaultData) {
		this.defaultData = defaultData;
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

	public MaterialTable defaultMaterial(MaterialData data) {
		this.defaultData = data;
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

	public MaterialData getDefaultMaterial() {
		return defaultData;
	}

	public List<ChancedItemData> getMaterials() {
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
