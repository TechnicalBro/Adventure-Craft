package com.caved_in.adventurecraft.gems.item;

import com.caved_in.adventurecraft.loot.effects.*;
import com.caved_in.commons.utilities.NumberUtil;

import java.util.Optional;

public class ChancedItemEffect {
    public static ChancedItemEffect[] DEFAULT_EFFECTS = {
            of(5, BackstabEffect.getInstance()),
            of(5, BleedEffect.getInstance()),
            of(5, CriticalStrikeEffect.getInstance()),
            of(5, FlameStrikeEffect.getInstance()),
            of(5, LifeLeechEffect.getInstance()),
            of(5, PhaseEffect.getInstance()),
            of(5, PoisonEffect.getInstance())
    };

    public static ChancedItemEffect of(int chance, ItemEffect effect) {
        return new ChancedItemEffect(chance, effect);
    }

    private ItemEffect effect;
    private int chance;


    public ChancedItemEffect(int chance, ItemEffect effect) {
        this.chance = chance;
        this.effect = effect;
    }

    public ChancedItemEffect effect(ItemEffect effect) {
        this.effect = effect;
        return this;
    }

    public ChancedItemEffect chance(int chance) {
        this.chance = chance;
        return this;
    }

    public ItemEffect getEffect() {
        return effect;
    }

    public int getChance() {
        return chance;
    }

    public Optional<ItemEffect> getOptionalItemEffect() {
        if (NumberUtil.percentCheck(getChance())) {
            return Optional.of(getEffect());
        } else {
            return Optional.empty();
        }
    }
}
