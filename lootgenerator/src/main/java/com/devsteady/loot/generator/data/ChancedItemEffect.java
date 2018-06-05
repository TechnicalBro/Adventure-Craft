package com.devsteady.loot.generator.data;

import com.devsteady.loot.AdventureLoot;
import com.devsteady.loot.effects.ItemEffect;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.YamlConfig;
import lombok.Getter;

public class ChancedItemEffect extends YamlConfig {

    public static ChancedItemEffect of(int chance, ItemEffect effect) {
        return new ChancedItemEffect().chance(chance).effect(effect);
    }

    @Getter
    private ItemEffect effect;

    @Path("chance")
    @Getter
    private int chance;

    @Path("name")
    @Getter
    private String name;

    public ChancedItemEffect(int chance, String effectName) {
        this.chance = chance;
        if (!AdventureLoot.getInstance().getItemEffectHandler().effectExists(effectName)) {
            throw new IllegalAccessError("Unable to initiate chanced item effect for " + effectName);
        }
        this.name = effectName;
        this.effect = AdventureLoot.getInstance().getItemEffectHandler().getEffect(effectName);
    }

    public ChancedItemEffect() {

    }

    public ChancedItemEffect effect(ItemEffect e) {
        this.effect = e;
        this.name = e.name();
        return this;
    }

    public ChancedItemEffect chance(int chance) {
        this.chance = chance;
        return this;
    }

    public int chance() {
        return chance;
    }

    public ItemEffect effect() {
        return effect;
    }
}
