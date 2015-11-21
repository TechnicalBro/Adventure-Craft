package com.caved_in.adventurecraft.loot.generator.settings;

import com.caved_in.adventurecraft.loot.generator.data.ChancedEnchantment;
import lombok.ToString;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "enchantment-settings")
@ToString(of = {"enchantments"})
public class ItemEnchantmentSettings {

    @ElementList(name = "enchantments", entry = "enchantment", type = ChancedEnchantment.class)
    private List<ChancedEnchantment> enchantments = new ArrayList<>();

    @Element(name = "maximum-enchantment-count")
    private int maximumEnchantments = 1;

    private LootSettings parent;

    public ItemEnchantmentSettings(
            @ElementList(name = "enchantments", entry = "enchantment", type = ChancedEnchantment.class) List<ChancedEnchantment> enchantment,
            @Element(name = "maximum-enchantment-count") int maximumEnchantments
    ) {
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