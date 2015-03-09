package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.commons.item.Attributes;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Optional;
import java.util.Random;

@Root(name = "chanced-material")
public class ChancedItemData {
	@Attribute(name = "chance")
	private int chance = 100;

	@Attribute(name = "material")
	private Material material;

	@Attribute(name = "data-value",required = false)
	private int dataValue = 0;

	//todo implement serialization
	private RandomizedAttribute attribute = null;

	public ChancedItemData(@Attribute(name = "chance") int chance, @Attribute(name = "material") Material material, @Attribute(name = "data-value", required = false) int dataValue) {
		this.chance = chance;
		this.material = material;
		this.dataValue = dataValue;
	}

	public ChancedItemData(int chance, Material material) {
		this.material = material;
		this.chance = chance;
	}

	public ChancedItemData(int chance, MaterialData data) {
		this.chance = chance;
		this.material = data.getItemType();
		this.dataValue = data.getData();
	}

	public ChancedItemData chance(int chance) {
		this.chance = chance;
		return this;
	}

	public ChancedItemData materialData(MaterialData data) {
		this.material = data.getItemType();
		this.dataValue = data.getData();
		return this;
	}

	public ChancedItemData attribute(RandomizedAttribute attribute) {
		this.attribute = attribute;
		return this;
	}

	public int getChance() {
		return chance;
	}

	public MaterialData getMaterialData() {
		return Items.getMaterialData(getMaterial(),dataValue);
	}

	public Material getMaterial() {
		return material;
	}

	public int getDataValue() {
		return dataValue;
	}

	public void setAttribute(RandomizedAttribute attribute) {
		this.attribute = attribute;
	}

	public Optional<RandomizedAttribute> getAttribute() {
		return Optional.ofNullable(attribute);
	}

	public Optional<MaterialData> getChancedMaterialData() {
		return NumberUtil.percentCheck(getChance()) ? Optional.of(getMaterialData()) : Optional.empty();
	}

}
