package com.devsteady.loot.generator.settings;

import com.devsteady.loot.generator.data.ChancedEnchantment;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.YamlConfig;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString(of = {"enchantments"})
public class ItemEnchantmentSettings extends YamlConfig {

    @Path("enchantments")
    @Getter
    private List<ChancedEnchantment> enchantments = new ArrayList<>();

    @Path("maximum-enchantment-count")
    @Getter
    private int maximumEnchantments = 1;

    private LootSettings parent;

    public ItemEnchantmentSettings(List<ChancedEnchantment> enchantment,int maximumEnchantments) {
        this.enchantments = enchantment;
        this.maximumEnchantments = maximumEnchantments;
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

    public ItemEnchantmentSettings maximumEnchantments(int count) {
        this.maximumEnchantments = count;
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