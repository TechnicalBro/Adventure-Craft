package com.devsteady.loot.util;

import com.devsteady.loot.AdventureLoot;
import com.devsteady.loot.effects.ItemEffect;
import com.devsteady.onyx.item.Items;
import com.devsteady.onyx.types.Tuple;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemHandler {
    private Set<ItemEffect> itemEffects = new HashSet<>();

    private AdventureLoot game;

    public ItemHandler(AdventureLoot game) {
        this.game = game;
    }

    public void registerItemEffects(ItemEffect... effects) {
        Collections.addAll(itemEffects, effects);
    }

    public Set<ItemEffect> getItemEffects() {
        return itemEffects;
    }

    public boolean hasEffect(ItemStack item) {
        if (item == null) {
            return false;
        }

        for(ItemEffect effect : itemEffects) {
            if (!effect.verify(item)) {
                continue;
            }

            return true;
        }

        return false;
    }

    public Set<ItemEffect> getEffects(ItemStack item) {
        Set<ItemEffect> effects = new HashSet<>();

        for(ItemEffect effect : itemEffects) {
            if (!effect.verify(item)) {
                continue;
            }

            effects.add(effect);
        }

        return effects;
    }

	public ItemEffect getEffect(String name) {
		for(ItemEffect effect : itemEffects) {
			if (effect.name().equalsIgnoreCase(name)) {
				return effect;
			}
		}

		return null;
	}

	public Set<String> getEffectNames() {
		return itemEffects.stream().map(ItemEffect::name).collect(Collectors.toSet());
	}

	public boolean effectExists(String name) {
        if (name == null || StringUtils.isEmpty(name)) {
            return false;
        }

		return getEffect(name) != null;
	}

    public boolean hasDamageRange(ItemStack item) {
        if (item == null) {
            return false;
        }

        if (Items.hasLore(item)) {
            List<String> loreLines = Items.getLore(item);

            if (loreLines == null){
                return false;
            }

            for (String line : loreLines) {
                if (line == null) {
                    continue;
                }
                if (StringUtils.containsIgnoreCase(line, "deals") && StringUtils.containsIgnoreCase(line, "damage!")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Tuple<Double, Double> getDamageRange(ItemStack item) {
        if (!hasDamageRange(item)) {
            return new Tuple<Double, Double>(0.0, 0.0);
        }

        double damageMin = 0;
        double damageMax = 0;

        if (Items.hasLore(item)) {
            List<String> loreLines = Items.getLore(item);
            if (loreLines == null) {
                return null;
            }

            for (String line : loreLines) {
                if (line == null) {
                    continue;
                }

                if (StringUtils.containsIgnoreCase(line, "deals") && StringUtils.containsIgnoreCase(line, "damage!")) {
                    //todo use regex formatting
                    line = line.toLowerCase();
                    String minDamageString = StringUtils.substringBetween(line, "deals ", " to");
                    String maxDamageString = StringUtils.substringBetween(line, String.format("%s to ", minDamageString)," damage!");

                    /*
                    Trim the color codes from the min and max damage strings
                    to allow doubles parsing.
                     */
                    minDamageString = ChatColor.stripColor(minDamageString.trim());
                    maxDamageString = ChatColor.stripColor(maxDamageString.trim());


                    damageMin = Double.parseDouble(minDamageString);
                    damageMax = Double.parseDouble(maxDamageString);

                    break;
                }
            }
        }

        return Pair.with(damageMin,damageMax);
    }
}
