package com.devsteady.loot.generator.settings;

import com.devsteady.loot.effects.ItemEffect;
import com.caved_in.adventurecraft.loot.generator.data.*;
import com.devsteady.loot.generator.data.ChancedEnchantment;
import com.devsteady.loot.generator.data.ChancedItemData;
import com.devsteady.loot.generator.data.ChancedName;
import com.devsteady.loot.generator.data.NameSlot;

public class LootSettingsBuilder {
    private LootSettings settings = new LootSettings();

    public static LootSettingsBuilder create() {
        return new LootSettingsBuilder();
    }

    public LootSettingsBuilder() {

    }

    public LootSettingsBuilder addLoot(ChancedItemData itemData) {
        settings.itemTable().add(itemData);
        return this;
    }

    public LootSettingsBuilder loreDisplayDamage(boolean show) {
        settings.lore().displayDamage(show);
        return this;
    }

    public LootSettingsBuilder addLore(String... lines) {
        settings.lore().addLore(lines);
        return this;
    }

    public LootSettingsBuilder loreDamageFormat(String format) {
        settings.lore().displayDamageFormat(format);
        return this;
    }

    public LootSettingsBuilder addNames(NameSlot slot, ChancedName... names) {
        switch (slot) {
            case PREFIX:
                settings.prefixes().add(names);
                break;
            case BASE:
                settings.baseNames().add(names);
                break;
            case SUFFIX:
                settings.suffixes().add(names);
                break;
            default:
                break;
        }

        return this;
    }

    public LootSettingsBuilder addEnchantment(ChancedEnchantment enchantment) {
        settings.enchantments().add(enchantment);
        return this;
    }

    public LootSettingsBuilder breakable(boolean val) {
        settings.weaponProperties().breakable(val);
        return this;
    }

    public LootSettingsBuilder droppable(boolean val) {
        settings.weaponProperties().droppable(val);
        return this;
    }

    public LootSettingsBuilder displayDamage(boolean val) {
        settings.lore().displayDamage(val);
        return this;
    }

    public LootSettingsBuilder displayRarity(boolean val) {
        settings.lore().displayRarity(val);
        return this;
    }

    public LootSettingsBuilder rarityFormat(String s) {
        settings.lore().rarityFormat(s);
        return this;
    }

    public LootSettingsBuilder damageFormat(String s) {
        settings.lore().displayDamageFormat(s);
        return this;
    }

    public LootSettingsBuilder addItemEffect(int chance, ItemEffect effect) {
        settings.effectSettings().add(chance, effect);
        return this;
    }

    public LootSettingsBuilder randomName(boolean randName) {
        settings.randomName(randName);
        return this;
    }

    public LootSettings build() {
        return settings;
    }
}
