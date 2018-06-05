package com.devsteady.loot.generator.data;

import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.YamlConfig;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;

public class ChancedEnchantment extends YamlConfig {

    @Path("enchantment")
    @Getter
    private Enchantment enchant;

    @Path("minimum-level")
    @Getter
    private int minLevel;

    @Path("maximum-level")
    @Getter
    private int maxLevel = 0;

    @Path("chance")
    @Getter
    private int chance = 10;

    @Path("glow")
    @Getter
    private boolean glow = false;

    @Deprecated
    public static ChancedEnchantment of(int chance, Enchantment enchant, int level) {
        return of(chance, enchant, level, level);
    }

    public static ChancedEnchantment of(int chance, Enchantment enchant, int min, int max) {
        return new ChancedEnchantment(chance, enchant, min, max);
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
        this.enchant = enchant;
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

    public int[] getLevelRange() {
        return new int[] {minLevel,maxLevel};
    }
}