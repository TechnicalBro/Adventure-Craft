package com.devsteady.loot.generator.data;

import com.devsteady.onyx.item.Items;
import com.devsteady.onyx.utilities.NumberUtil;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.YamlConfig;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import java.util.Optional;

public class ChancedItemData extends YamlConfig {

    @Path("chance")
    @Getter
    @Setter
    private int chance = 100;

    @Path("material")
    @Getter @Setter
    private Material material;

    @Path(value="data-value",required=false)
    @Getter @Setter
    private int dataValue = 0;

    private double[] damageMinRange = null;

    private double[] damageMaxRange = null;

    private double[] damageRange = null;

    private boolean overrideDamageApplications = false;

    //todo implement serialization
    private RandomizedAttribute attribute = null;

    public static ChancedItemData of(int chance, Material material) {
        return new ChancedItemData(chance, material);
    }

    public static ChancedItemData of(int chance, MaterialData data) {
        return new ChancedItemData(chance, data);
    }

    public ChancedItemData(int chance,Material material,int dataValue) {
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
        damageMinRange = new double[] {minMin, minMax};
        damageMaxRange = new double[] {maxMin, maxMax};
        return this;
    }

    public ChancedItemData damageRange(double min, double max) {
        damageRange = new double[] {min, max};
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

    public MaterialData getMaterialData() {
        Preconditions.checkNotNull(getMaterial(), "Unable to create item as material is null");
        if (dataValue > 0) {
            return new MaterialData(getMaterial(), (byte) dataValue);
        } else {
            return new MaterialData(getMaterial());
        }
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
