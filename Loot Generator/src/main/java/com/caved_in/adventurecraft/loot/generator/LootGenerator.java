package com.caved_in.adventurecraft.loot.generator;

import com.caved_in.adventurecraft.loot.Loot;
import com.caved_in.adventurecraft.loot.generator.data.LootSettings;
import com.caved_in.commons.game.item.WeaponProperties;

public class LootGenerator {
    
    private Loot plugin;
    
    public LootGenerator(Loot plugin) {
        this.plugin = plugin;
    }
    
    public void createItem(LootSettings settings) {
        boolean hasRandomName = settings.hasRandomName();
        
        String staticName = settings.getLootName();
        
        LootSettings.ItemNameSettings baseNames = settings.baseNames();
        LootSettings.ItemNameSettings prefixes = settings.prefixes();
        LootSettings.ItemNameSettings suffixes = settings.suffixes();

        WeaponProperties weaponProperties = settings.weaponProperties();
    }
}
