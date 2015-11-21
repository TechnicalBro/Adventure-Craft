package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.adventurecraft.adventureitems.AdventureItems;
import com.caved_in.adventurecraft.adventureitems.effects.ItemEffect;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "chanced-item-effect")
public class ChancedItemEffect {

    public static ChancedItemEffect of(int chance, ItemEffect effect) {
        return new ChancedItemEffect().chance(chance).effect(effect);
    }

    private ItemEffect effect;

    @Attribute(name = "chance")
    private int chance;

    @Attribute(name = "effect")
    private String effectName;

    public ChancedItemEffect(@Attribute(name = "chance")int chance, @Attribute(name = "effect")String effectName) {
        this.chance = chance;
        if (!AdventureItems.getInstance().getItemEffectHandler().effectExists(effectName)) {
            throw new IllegalAccessError("Unable to initiate chanced item effect for " + effectName);
        }
        this.effectName = effectName;
        this.effect = AdventureItems.getInstance().getItemEffectHandler().getEffect(effectName);
    }

    public ChancedItemEffect() {

    }

    public ChancedItemEffect effect(ItemEffect e) {
        this.effect = e;
        this.effectName = e.name();
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

    public String effectName() {
        return effectName;
    }
}
