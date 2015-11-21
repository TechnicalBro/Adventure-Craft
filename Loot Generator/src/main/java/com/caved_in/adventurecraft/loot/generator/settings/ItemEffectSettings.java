package com.caved_in.adventurecraft.loot.generator.settings;

import com.caved_in.adventurecraft.adventureitems.effects.ItemEffect;
import com.caved_in.adventurecraft.loot.generator.data.ChancedItemEffect;
import com.caved_in.adventurecraft.loot.generator.data.LootTable;
import com.caved_in.commons.utilities.ListUtils;
import com.caved_in.commons.utilities.NumberUtil;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemEffectSettings {

    private LootSettings parent;

    @ElementList(name = "effects",entry = "effect",type = ChancedItemEffect.class)
    private List<ChancedItemEffect> itemEffects = new ArrayList<>();

    public ItemEffectSettings(@ElementList(name = "effects",entry = "effect",type = ChancedItemEffect.class)List<ChancedItemEffect> effects) {
        this.itemEffects = effects;
    }

    public ItemEffectSettings() {

    }

    public ItemEffectSettings parent(LootSettings parent) {
        this.parent = parent;
        return this;
    }

    public ItemEffectSettings add(int chance, ItemEffect effect) {
        this.itemEffects.add(ChancedItemEffect.of(chance,effect));
        return this;
    }

    public boolean hasEffects() {
        return itemEffects.size() > 0;
    }

    public LootSettings parent() {
        return parent;
    }

    public Optional<ItemEffect> getChancedEffect() {
        ChancedItemEffect cEffect = ListUtils.getRandom(itemEffects);
        if (NumberUtil.percentCheck(cEffect.chance())) {
            return Optional.of(cEffect.effect());
        }
        return Optional.empty();
    }

    public List<ItemEffect> getEffects() {
        return itemEffects.stream().map(ChancedItemEffect::effect).collect(Collectors.toList());
    }

    public List<ChancedItemEffect> getChancedEffects() {
        return itemEffects;
    }
}
