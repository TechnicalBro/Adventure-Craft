package com.devsteady.loot.generator.data;

import org.bukkit.enchantments.Enchantment;
import org.javatuples.Pair;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "enchantment")
public class ChancedEnchantment {
    @Attribute(name = "name")
    private String enchantName;

    @Attribute(name = "minimum-level")
    private int minLevel;

    @Attribute(name = "maximum-level")
    private int maxLevel = 0;

    @Attribute(name = "glow", required = false)
    private boolean glow = true;

    @Attribute(name = "chance")
    private int chance = 10;


    @Deprecated
    public static ChancedEnchantment of(int chance, Enchantment enchant, int level) {
        return of(chance, enchant, level, level);
    }

    public static ChancedEnchantment of(int chance, Enchantment enchant, int min, int max) {
        return new ChancedEnchantment(chance, enchant, min, max);
    }

    public ChancedEnchantment(@Attribute(name = "name") String enchantName, @Attribute(name = "minimum-level") int minLevel, @Attribute(name = "maximum-level") int maxLevel, @Attribute(name = "glow", required = false) boolean glow, @Attribute(name = "chance") int chance) {
        this.enchantName = enchantName;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.glow = glow;
        this.chance = chance;
    }

    public ChancedEnchantment(int chance, Enchantment enchantment, int minLevel, int maxLevel) {
        chance(chance);
        enchantment(enchantment);
        level(minLevel, maxLevel);
    }

    public ChancedEnchantment(int chance, Enchantment enchantment, int level) {
        this(chance,enchantment,level,level);
    }

    public ChancedEnchantment() {

    }

    public ChancedEnchantment chance(int chance) {
        this.chance = chance;
        return this;
    }

    public int getChance() {
        return chance;
    }

    public ChancedEnchantment enchantment(Enchantment enchant) {
        this.enchantName = enchant.getName();
        return this;
    }

    public ChancedEnchantment level(int min, int max) {
        this.minLevel = min;
        this.maxLevel = max;
        return this;
    }

    public ChancedEnchantment level(int lvl) {
        this.minLevel = lvl;
        this.maxLevel = lvl;
        return this;
    }

    public ChancedEnchantment glow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public Pair<Integer, Integer> getLevelRange() {
        return Pair.with(minLevel,maxLevel);
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public Enchantment getEnchantment() {
        return Enchantment.getByName(enchantName);
    }

    public boolean hasGlow() {
        return glow;
    }
}