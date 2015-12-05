package com.caved_in.adventurecraft.loot.generator.settings;

import com.caved_in.adventurecraft.loot.generator.data.MaterialTable;
import com.caved_in.adventurecraft.loot.generator.data.NameTable;
import com.caved_in.adventurecraft.loot.generator.data.NameSlot;
import com.caved_in.commons.game.item.WeaponProperties;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.javatuples.Pair;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "loot-settings")
@ToString(of = {"materialTable", "weaponProperties", "randomName", "name", "base", "prefixes", "suffixes", "loreSettings", "enchantmentSettings"})
public class LootSettings {

    @Element(name = "material-table", type = MaterialTable.class)
    private MaterialTable materialTable;

    @Element(name = "weapon-settings", type = WeaponProperties.class)
    private WeaponProperties weaponProperties = new WeaponProperties(0, true, true, 2, 5);

    @Element(name = "random-name")
    private boolean randomName = false;

    @Element(name = "itemTable-name", required = false)
    private String name;

    @Element(name = "base-name-table", type = NameTable.class, required = false)
    private NameTable base = new NameTable(NameSlot.BASE);

    @Element(name = "prefixes", type = NameTable.class, required = false)
    private NameTable prefixes = new NameTable(NameSlot.PREFIX);

    @Element(name = "suffixes", type = NameTable.class, required = false)
    private NameTable suffixes = new NameTable(NameSlot.SUFFIX);

    @Element(name = "lore-settings", type = ItemLoreSettings.class)
    private ItemLoreSettings loreSettings = new ItemLoreSettings();

    @Element(name = "enchantment-settings", type = ItemEnchantmentSettings.class)
    private ItemEnchantmentSettings enchantmentSettings = new ItemEnchantmentSettings();

    @Element(name = "effect-settings", type = ItemEffectSettings.class, required = false)
    private ItemEffectSettings effectSettings = new ItemEffectSettings();

    public static LootSettingsBuilder createBuilder() {
        return new LootSettingsBuilder();
    }

    public static LootSettings create() {
        return new LootSettings();
    }

    public LootSettings(@Element(name = "weapon-settings", type = WeaponProperties.class) WeaponProperties weaponProperties,
                        @Element(name = "material-table", type = MaterialTable.class) MaterialTable materialTable,
                        @Element(name = "random-name") boolean randomName,
                        @Element(name = "itemTable-name", required = false) String name,
                        @Element(name = "base-name-table", type = NameTable.class, required = false) NameTable base,
                        @Element(name = "prefixes-table", type = NameTable.class, required = false) NameTable prefixes,
                        @Element(name = "suffixes-table", type = NameTable.class, required = false) NameTable suffixes,
                        @Element(name = "lore-settings", type = ItemLoreSettings.class) ItemLoreSettings loreSettings,
                        @Element(name = "enchantment-settings", type = ItemEnchantmentSettings.class) ItemEnchantmentSettings enchantmentSettings,
                        @Element(name = "effect-settings", type = ItemEffectSettings.class, required = false) ItemEffectSettings effectSettings

    ) {
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
