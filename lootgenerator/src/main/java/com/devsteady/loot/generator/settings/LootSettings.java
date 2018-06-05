package com.devsteady.loot.generator.settings;

import com.devsteady.loot.generator.data.MaterialTable;
import com.devsteady.loot.generator.data.NameTable;
import com.devsteady.loot.generator.data.NameSlot;
import com.devsteady.onyx.game.item.WeaponProperties;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.YamlConfig;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

@ToString(of = {"materialTable", "weaponProperties", "randomName", "name", "base", "prefixes", "suffixes", "loreSettings", "enchantmentSettings"})
public class LootSettings extends YamlConfig {

    @Path("material-table")
    private MaterialTable materialTable;

    @Path("weapon-properties")
    private WeaponProperties weaponProperties = new WeaponProperties().damage(2,5);

    @Path("generate-random-name")
    private boolean randomName = false;

    @Path("name")
    private String name;

    @Path("table.prefixes")
    private NameTable prefixes = new NameTable(NameSlot.PREFIX);

    @Path("table.bases")
    private NameTable base = new NameTable(NameSlot.BASE);

    @Path("table.suffixes")
    private NameTable suffixes = new NameTable(NameSlot.SUFFIX);

    @Path("lore-settings")
    private ItemLoreSettings loreSettings = new ItemLoreSettings();

    @Path("enchantment-settings")
    private ItemEnchantmentSettings enchantmentSettings = new ItemEnchantmentSettings();

    @Path(value="effect-settings",required = false)
    private ItemEffectSettings effectSettings = new ItemEffectSettings();

    public static LootSettingsBuilder createBuilder() {
        return new LootSettingsBuilder();
    }

    public static LootSettings create() {
        return new LootSettings();
    }

    public LootSettings(WeaponProperties weaponProperties,MaterialTable materialTable,boolean randomName,String name,NameTable base,NameTable prefixes,NameTable suffixes,ItemLoreSettings loreSettings,ItemEnchantmentSettings enchantmentSettings,ItemEffectSettings effectSettings) {
        this.materialTable = materialTable;
        this.randomName = randomName;
        this.name = name;
        this.weaponProperties = weaponProperties;
        this.base = base;
        this.prefixes = prefixes;
        this.suffixes = suffixes;
        this.loreSettings = loreSettings;
        this.enchantmentSettings = enchantmentSettings;
        this.effectSettings = effectSettings;
    }

    public LootSettings() {
        materialTable = new MaterialTable(new MaterialData(Material.AIR));
    }

    public WeaponProperties weaponProperties() {
        return weaponProperties;
    }

    public NameTable baseNames() {
        return base.parent(this);
    }

    public NameTable prefixes() {
        return prefixes.parent(this);
    }

    public NameTable suffixes() {
        return suffixes.parent(this);
    }

    public ItemEnchantmentSettings enchantments() {
        return enchantmentSettings;
    }

    public LootSettings name(String name) {
        this.name = name;
        return this;
    }

    public MaterialTable itemTable() {
        return materialTable.parent(this);
    }

    //todo implement checks for non-random-names
    public boolean hasRandomName() {
        return randomName;
    }

    public String getLootName() {
        return name;
    }

    public ItemLoreSettings lore() {
        return loreSettings.parent(this);
    }

    public LootSettings randomName(boolean randomName) {
        this.randomName = randomName;
        return this;
    }

    public boolean hasEffectSettings() {
        return effectSettings != null && effectSettings.hasEffects();
    }

    public ItemEffectSettings effectSettings() {
        return effectSettings;
    }
}
