package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.commons.config.XmlEnchantment;
import com.caved_in.commons.game.item.WeaponProperties;
import com.caved_in.commons.utilities.ListUtils;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

public class LootSettings {

    @Element(name = "item-info",type = ItemTypeSettings.class)
    private ItemTypeSettings itemInfo;
    
    @Element(name = "weapon-settings",type = WeaponProperties.class)
    private WeaponProperties weaponProperties = new WeaponProperties(0,true,true,2,5);
    
    @Element(name = "random-name")
    private boolean randomName = true;
    
    @Element(name = "item-name",required = false)
    private String name;
    
    @Element(name = "name-base",type = ItemNameSettings.class,required = false)
    private ItemNameSettings base = new ItemNameSettings(NameSlot.BASE);
    
    @Element(name = "prefixes",type = ItemNameSettings.class,required = false)
    private ItemNameSettings prefixes = new ItemNameSettings(NameSlot.PREFIX);
    
    @Element(name = "suffixes",type = ItemNameSettings.class,required = false)
    private ItemNameSettings suffixes = new ItemNameSettings(NameSlot.SUFFIX);
    
    @Element(name = "lore-settings",type = ItemLoreSettings.class)
    private ItemLoreSettings loreSettings = new ItemLoreSettings();
    
    @Element(name = "enchantment-settings",type = ItemEnchantmentSettings.class)
    private ItemEnchantmentSettings enchantmentSettings = new ItemEnchantmentSettings();
    
    public LootSettings(@Element(name = "weapon-settings", type = WeaponProperties.class)WeaponProperties weaponProperties,
                        @Element(name = "item-info",type = ItemTypeSettings.class)ItemTypeSettings itemInfo,
                        @Element(name = "random-name")boolean randomName,
                        @Element(name = "item-name",required = false)String name,
                        @Element(name = "name-base",type = ItemNameSettings.class,required = false)ItemNameSettings base,
                        @Element(name = "prefixes", type = ItemNameSettings.class,required = false)ItemNameSettings prefixes,
                        @Element(name = "suffixes", type = ItemNameSettings.class,required = false)ItemNameSettings suffixes,
                        @Element(name = "lore-settings",type = ItemLoreSettings.class)ItemLoreSettings loreSettings,
                        @Element(name = "enchantment-settings",type = ItemEnchantmentSettings.class)ItemEnchantmentSettings enchantmentSettings
    
    ) {
        this.itemInfo = itemInfo;
        this.randomName = randomName;
        this.name = name;
        this.weaponProperties = weaponProperties;
        this.base = base;
        this.prefixes = prefixes;
        this.suffixes = suffixes;
        this.loreSettings = loreSettings;
        this.enchantmentSettings = enchantmentSettings;
    }
    
    public LootSettings() {
        itemInfo = new ItemTypeSettings();
    }
    
    public WeaponProperties weaponProperties() {
        return weaponProperties;
    }
    
    public ItemNameSettings baseNames() {
        return base.parent(this);
    }
    
    public ItemNameSettings prefixes() {
        return prefixes.parent(this);
    }
    
    public ItemNameSettings suffixes() {
        return suffixes.parent(this);
    }
    
    public ItemEnchantmentSettings enchantments() {
        return enchantmentSettings;
    }
    
    public LootSettings name(String name) {
        this.name = name;
        return this;
    }
    
    public ItemTypeSettings item() {
        return itemInfo.parent(this);
    }

    public boolean hasRandomName() {
        return randomName;
    }
    
    public String getLootName() {
        return name;
    }
    
    @Root(name = "enchantment-settings")
    public static class ItemEnchantmentSettings {
        
        @ElementList(name = "enchantments",entry = "enchantment",type = ChancedEnchantment.class)
        private List<ChancedEnchantment> enchantments = new ArrayList<>();
        
        private LootSettings parent;
        
        public ItemEnchantmentSettings(
                @ElementList(name = "enchantments",entry = "enchantment",type = ChancedEnchantment.class)List<ChancedEnchantment> enchantment
        ) {
            this.enchantments = enchantment;
        }
        
        public ItemEnchantmentSettings() {
            this.enchantments = new ArrayList<>();
        }
        
        public ItemEnchantmentSettings parent(LootSettings parent) {
            this.parent = parent;
            return this;
        }
        
        public ItemEnchantmentSettings add(ChancedEnchantment enchantment) {
            enchantments.add(enchantment);
            return this;
        }
        
        public LootSettings parent() {
            return parent;
        }

        public List<ChancedEnchantment> getEnchantments() {
            return enchantments;
        }
        
        public boolean hasEnchantments() {
            return getEnchantments() != null && getEnchantments().size() > 0;
        }
    }
    
    @Root(name = "enchantment")
    public static class ChancedEnchantment extends XmlEnchantment {
        @Attribute(name = "chance")
        private int chance = 10;
        
        public ChancedEnchantment(@Attribute(name = "name") String enchantName, @Attribute(name = "level") int level, @Attribute(name = "glow", required = false) boolean glow,@Attribute(name = "chance")int chance) {
            super(enchantName, level, glow);
            this.chance = chance;
        }
        
        public ChancedEnchantment chance(int chance) {
            this.chance = chance;
            return this;
        }

        public int getChance() {
            return chance;
        }
    }
    
    @Root(name = "lore-settings")
    public static class ItemLoreSettings {
        @Element(name = "display-damage")
        private boolean damageDisplay = true;
        
        @Element(name = "damage-format",required = false)
        private String damageFormat = "&cDeals &e%s&c to &e%s&c damage!";
        
        @ElementList(name = "lines",entry = "line",required = false)
        private List<String> lore = new ArrayList<>();
        
        public ItemLoreSettings(
                @Element(name = "display-damage")boolean damageDisplay,
                @Element(name = "damage-format",required = false)String damageFormat,
                @ElementList(name = "lines",entry = "line",required = false)List<String> lore) {
            this.lore = lore;
            this.damageDisplay = damageDisplay;
            this.damageFormat = damageFormat;
        }
        
        public ItemLoreSettings() {
            lore = new ArrayList<>();
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
    }

    @Root(name = "name-settings")
    public static class ItemNameSettings {
        
        @Attribute(name = "slot")
        private NameSlot slot;
        
        @ElementList(name = "potential-names",entry = "name")
        private List<String> names = new ArrayList<>();

        private LootSettings parent;
        
        public ItemNameSettings(@Attribute(name = "slot")NameSlot slot,@ElementList(name = "potential-names",entry = "name")List<String> names) {
            this.slot = slot;
            this.names = names;
        }
        
        public ItemNameSettings parent(LootSettings parent) {
            this.parent = parent;
            return this;
        }
        
        public ItemNameSettings(NameSlot slot) {
            this.slot = slot;
        }
        
        public ItemNameSettings add(String name) {
            names.add(name);
            return this;
        }
        
        public List<String> getNames() {
            return names;
        }
        
        public NameSlot getSlot() {
            return slot;
        }
        
        public LootSettings parent() {
            return parent;
        }
        
        public String getRandomName() {
            return ListUtils.getRandom(getNames());
        }
    }
    
    public static enum NameSlot {
        PREFIX, BASE, SUFFIX
    }
    
    @Root(name = "item-info")
    public static class ItemTypeSettings {
        @Attribute(name = "item-type")
        private Material material = Material.AIR;

        @Attribute(name = "data-value", required = false)
        private int dataValue = 0;

        @Attribute(name = "spawn-chance")
        private int spawnChance = 100;
        
        private LootSettings parent;

        public ItemTypeSettings(@Attribute(name = "item-type")Material material,@Attribute(name = "data-value",required = false)int dataValue,@Attribute(name = "spawn-chance")int spawnChance) {
            this.material = material;
            this.dataValue = dataValue;
            this.spawnChance = spawnChance;
        }
        
        public ItemTypeSettings() {
            
            
        }
        
        public ItemTypeSettings material(Material material) {
            this.material = material;
            return this;
        }

        public ItemTypeSettings materialData(MaterialData data) {
            this.material = data.getItemType();
            this.dataValue = data.getData();
            return this;
        }

        public ItemTypeSettings chance(int chance) {
            this.spawnChance = chance;
            return this;
        }
        
        public ItemTypeSettings parent(LootSettings parent) {
            this.parent = parent;
            return this;
        }
        
        public LootSettings parent() {
            return parent;
        }

        public Material getMaterial() {
            return material;
        }

        public int getDataValue() {
            return dataValue;
        }

        public int getSpawnChance() {
            return spawnChance;
        }
    }
}
