package com.caved_in.adventurecraft.adventureitems.effects;

import com.caved_in.adventurecraft.adventureitems.AdventureItems;
import com.caved_in.commons.game.CraftGame;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ItemEffectHandler {
    private Set<ItemEffect> itemEffects = new HashSet<>();

    private AdventureItems game;

    public ItemEffectHandler(AdventureItems game) {
        this.game = game;
    }

    public void registerItemEffects(ItemEffect... effects) {
        Collections.addAll(itemEffects, effects);
    }

    public Set<ItemEffect> getItemEffects() {
        return itemEffects;
    }

    public boolean hasEffect(ItemStack item) {
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
}
