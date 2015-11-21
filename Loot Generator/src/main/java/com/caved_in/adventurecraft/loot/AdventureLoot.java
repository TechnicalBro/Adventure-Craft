package com.caved_in.adventurecraft.loot;

import com.caved_in.adventurecraft.adventureitems.AdventureItems;
import com.caved_in.adventurecraft.loot.command.LootCommand;
import com.caved_in.adventurecraft.loot.debug.DebugGenerateWithItemEffect;
import com.caved_in.adventurecraft.loot.debug.DebugLootGenerator;
import com.caved_in.adventurecraft.loot.debug.DebugLootTableSerialization;
import com.caved_in.adventurecraft.loot.generator.LootGenerator;
import com.caved_in.adventurecraft.loot.generator.data.LootTable;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.adventurecraft.loot.listener.LootGenerateListener;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.plugin.Plugins;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class AdventureLoot extends BukkitPlugin {
    private static AdventureLoot instance;

    private static LootGenerator generator;

    private static AdventureItems adventureItems = null;
    
    public static AdventureLoot getInstance() {
        return instance;
    }
    
    @Override
    public void startup() {
        instance = this;

        adventureItems = AdventureItems.getInstance();

        generator = new LootGenerator(this);

        registerDebugActions(
                new DebugLootGenerator(),
                new DebugLootTableSerialization(),
                new DebugGenerateWithItemEffect()
        );

        registerListeners(
                new LootGenerateListener()
        );

        registerCommands(
                new LootCommand()
        );
    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis";
    }

    @Override
    public void initConfig() {

    }

    public static class API {

        public static ItemStack createItem(LootTable table) {
            return generator.createItem(table);
        }

        public static ItemStack createItem(LootSettings settings) {
            return generator.createItem(settings);
        }

        public static Optional<ItemStack> generateItem(LootTable table) {
            return generator.generateItem(table);
        }

        public static Optional<ItemStack> generateItem(LootSettings settings) {
            return generator.generateItem(settings);
        }

        public static LootSettingsBuilder getSettingsBuilder() {
            return new LootSettingsBuilder();
        }

        public static boolean hasAdventureItems() {
            return adventureItems != null;
        }
    }
}
