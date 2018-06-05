package com.devsteady.loot.generator.settings;

import com.devsteady.loot.effects.ItemEffect;
import com.devsteady.loot.generator.data.ChancedItemEffect;
import com.devsteady.onyx.utilities.ListUtils;
import com.devsteady.onyx.utilities.NumberUtil;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.Skip;
import com.devsteady.onyx.yml.YamlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemEffectSettings extends YamlConfig {

    @Skip
    private LootSettings parent;

    @Path("effects")
    private List<ChancedItemEffect> itemEffects = new ArrayList<>();

    public ItemEffectSettings(List<ChancedItemEffect> effects) {
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
