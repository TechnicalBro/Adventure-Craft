package com.caved_in.adventurecraft.gems.item;

import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.enchantments.Enchantment;
import org.javatuples.Pair;
import org.javatuples.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.caved_in.commons.utilities.NumberUtil.percentCheck;

public class ChancedEnchantment {
    public static ChancedEnchantment[] DEFAULT_ENCHANTS = {
            of(60, Enchantment.ARROW_DAMAGE, 1),
            of(30, Enchantment.ARROW_DAMAGE, 2),
            of(10, Enchantment.ARROW_DAMAGE, 3),
            of(5, Enchantment.ARROW_DAMAGE, 4),
            of(1, Enchantment.ARROW_DAMAGE, 5),
            of(10, Enchantment.ARROW_FIRE, 1),
            of(10, Enchantment.ARROW_KNOCKBACK, 1),
            of(60, Enchantment.DAMAGE_ALL, 1),
            of(40, Enchantment.DAMAGE_ALL, 2),
            of(20, Enchantment.DAMAGE_ALL, 3),
            of(10, Enchantment.DAMAGE_ALL, 4),
            of(5, Enchantment.DAMAGE_ALL, 5),
            of(1, Enchantment.DAMAGE_ALL, 6),
            of(20, Enchantment.DAMAGE_ARTHROPODS, 1),
            of(15, Enchantment.DAMAGE_ARTHROPODS, 2),
            of(5, Enchantment.DAMAGE_ARTHROPODS, 3),
            of(20, Enchantment.DAMAGE_UNDEAD, 1),
            of(10, Enchantment.DAMAGE_UNDEAD, 2),
            of(3, Enchantment.DAMAGE_UNDEAD, 3),
            of(40, Enchantment.DIG_SPEED, 1),
            of(20, Enchantment.DIG_SPEED, 2),
            of(5, Enchantment.DIG_SPEED, 3),
            of(80, Enchantment.DURABILITY, 1),
            of(40, Enchantment.DURABILITY, 2),
            of(5, Enchantment.DURABILITY, 3),
            of(3, Enchantment.DURABILITY, 4),
            of(2, Enchantment.DURABILITY, 5),
            of(30, Enchantment.FIRE_ASPECT, 1),
            of(5, Enchantment.FIRE_ASPECT, 2),
            of(1, Enchantment.FIRE_ASPECT, 3),
            of(60, Enchantment.KNOCKBACK, 1),
            of(40, Enchantment.KNOCKBACK, 2),
            of(10, Enchantment.KNOCKBACK, 3),
            of(20, Enchantment.LOOT_BONUS_BLOCKS, 1),
            of(10, Enchantment.LOOT_BONUS_BLOCKS, 2),
            of(3, Enchantment.LOOT_BONUS_BLOCKS, 3),
            of(20, Enchantment.LOOT_BONUS_MOBS, 1),
            of(10, Enchantment.LOOT_BONUS_MOBS, 2),
            of(3, Enchantment.LOOT_BONUS_MOBS, 3),
            of(20, Enchantment.LUCK, 1),
            of(10, Enchantment.LUCK, 2),
            of(2, Enchantment.LUCK, 3),
            of(40, Enchantment.LURE, 1),
            of(20, Enchantment.LURE, 2),
            of(5, Enchantment.LURE, 3),
            of(30, Enchantment.OXYGEN, 1),
            of(15, Enchantment.OXYGEN, 2),
            of(5, Enchantment.OXYGEN, 3),
            of(60, Enchantment.PROTECTION_ENVIRONMENTAL, 1),
            of(35, Enchantment.PROTECTION_ENVIRONMENTAL, 2),
            of(25, Enchantment.PROTECTION_ENVIRONMENTAL, 3),
            of(15, Enchantment.PROTECTION_ENVIRONMENTAL, 4),
            of(5, Enchantment.PROTECTION_ENVIRONMENTAL, 5),
            of(3, Enchantment.PROTECTION_ENVIRONMENTAL, 6),
            of(60, Enchantment.PROTECTION_EXPLOSIONS, 1),
            of(35, Enchantment.PROTECTION_EXPLOSIONS, 2),
            of(25, Enchantment.PROTECTION_EXPLOSIONS, 3),
            of(15, Enchantment.PROTECTION_EXPLOSIONS, 4),
            of(5, Enchantment.PROTECTION_EXPLOSIONS, 5),
            of(3, Enchantment.PROTECTION_EXPLOSIONS, 6),
            of(60, Enchantment.PROTECTION_FALL, 1),
            of(35, Enchantment.PROTECTION_FALL, 2),
            of(25, Enchantment.PROTECTION_FALL, 3),
            of(15, Enchantment.PROTECTION_FALL, 4),
            of(5, Enchantment.PROTECTION_FALL, 5),
            of(3, Enchantment.PROTECTION_FALL, 6),
            of(60, Enchantment.PROTECTION_PROJECTILE, 1),
            of(35, Enchantment.PROTECTION_PROJECTILE, 2),
            of(25, Enchantment.PROTECTION_PROJECTILE, 3),
            of(15, Enchantment.PROTECTION_PROJECTILE, 4),
            of(5, Enchantment.PROTECTION_PROJECTILE, 5),
            of(3, Enchantment.PROTECTION_PROJECTILE, 6),
            of(10, Enchantment.SILK_TOUCH, 1),
            of(10, Enchantment.THORNS, 1),
            of(8, Enchantment.THORNS, 2),
            of(6, Enchantment.THORNS, 3),
            of(5, Enchantment.THORNS, 4),
            of(1, Enchantment.THORNS, 5),
            of(25, Enchantment.WATER_WORKER, 1),
            of(15, Enchantment.WATER_WORKER, 2),
            of(5, Enchantment.WATER_WORKER, 3),
    };

    private int chance;

    private Pair<Enchantment, Integer> enchantInfo;
    private String enchantName;

    public static ChancedEnchantment of(int chance, Enchantment enchant, int level) {
        return new ChancedEnchantment().chance(chance).enchant(enchant, level);
    }
    
    public static List<ChancedEnchantment> defaultFor(Enchantment enchant) {
        List<ChancedEnchantment> enchants = new ArrayList<>();
        
        for(ChancedEnchantment defaultEnchant : DEFAULT_ENCHANTS) {
            if (defaultEnchant.getEnchantment().equals(enchant)) {
                enchants.add(defaultEnchant);
            }
        }
        
        return enchants;
    }

    public ChancedEnchantment() {

    }

    public ChancedEnchantment enchant(Enchantment enchant, int level) {
        enchantInfo = new Pair<>(enchant, level);
        return this;
    }

    public ChancedEnchantment chance(int chance) {
        this.chance = chance;
        return this;
    }

    public ChancedEnchantment name(String name) {
        this.enchantName = name;
        return this;
    }

    public Optional<Pair<Enchantment, Integer>> getOptionalEnchantmentTuple() {
        if (NumberUtil.percentCheck(chance)) {
            return Optional.of(enchantInfo);
        } else {
            return Optional.empty();
        }
    }

    public Enchantment getEnchantment() {
        return enchantInfo.getValue0();
    }

    public int getLevel() {
        return enchantInfo.getValue1();
    }

    public String getEnchantName() {
        return enchantName;
    }

    public int getChance() {
        return chance;
    }
}
