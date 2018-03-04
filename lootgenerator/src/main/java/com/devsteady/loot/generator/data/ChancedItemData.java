package com.devsteady.loot.generator.data;

import com.caved_in.commons.item.Items;
import com.caved_in.commons.utilities.NumberUtil;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.javatuples.Pair;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.Optional;

@Root(name = "chanced-material")
public class ChancedItemData {
    @Attribute(name = "chance")
    private int chance = 100;

    @Attribute(name = "material")
    private Material material;

    @Attribute(name = "data-value", required = false)
    private int dataValue = 0;

    private Pair<Double, Double> damageMinRange = null;

    private Pair<Double, Double> damageMaxRange = null;

    private Pair<Double, Double> damageRange = null;

    private boolean overrideDamageApplications = false;

    //todo implement serialization
    private RandomizedAttribute attribute = null;

    public static ChancedItemData of(int chance, Material material) {
        return new ChancedItemData(chance, material);
    }

    public static ChancedItemData of(int chance, MaterialData data) {
        return new ChancedItemData(chance, data);
    }

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

    public ChancedItemData damageRange(double minMin, double minMax, double maxMin, double maxMax) {
        damageMinRange = Pair.with(minMin, minMax);
        damageMaxRange = Pair.with(maxMin, maxMax);
        return this;
    }

    public ChancedItemData damageRange(double min, double max) {
        damageRange = Pair.with(min, max);
        return this;
    }

    public ChancedItemData forceDamageApplication(boolean force) {
        this.overrideDamageApplications = force;
        return this;
    }

    public boolean hasMinMaxDamageRanges() {
        if (!overrideDamageApplications && !Items.isWeapon(getMaterial())) {
            return false;
        }
        return damageMinRange != null && damageMaxRange != null;
    }

    public boolean hasDamageRange() {
        if (!overrideDamageApplications && !Items.isWeapon(getMaterial())) {
            return false;
        }

        return damageRange != null;
    }

    public Pair<Double, Double> getMinDamageRange() {
        return damageMinRange;
    }

    public Pair<Double, Double> getMaxDamageRange() {
        return damageMaxRange;
    }

    public Pair<Double, Double> getDamageRange() {
        return damageRange;
    }

    public int getChance() {
        return chance;
    }

    public MaterialData getMaterialData() {
        Preconditions.checkNotNull(getMaterial(), "Unable to create item as material is null");
        if (dataValue > 0) {
            return new MaterialData(getMaterial(), (byte) dataValue);
        } else {
            return new MaterialData(getMaterial());
        }
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
