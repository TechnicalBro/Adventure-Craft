package com.caved_in.adventurecraft.loot;

import com.caved_in.adventurecraft.loot.command.LootCommand;
import com.caved_in.adventurecraft.loot.debug.DebugLootGenerator;
import com.caved_in.adventurecraft.loot.generator.LootGenerator;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.adventurecraft.loot.listener.LootGenerateListener;
import com.caved_in.commons.plugin.BukkitPlugin;
import org.bukkit.inventory.ItemStack;

public class AdventureLoot extends BukkitPlugin {
    private static AdventureLoot instance;

    private static LootGenerator generator;
    
    public static AdventureLoot getInstance() {
        return instance;
    }
    
    @Override
    public void startup() {
        instance = this;

        generator = new LootGenerator(this);

        registerDebugActions(
                new DebugLootGenerator()
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

        public static ItemStack generateItem(LootSettings settings) {
            return generator.createItem(settings);
        }

        public static LootSettingsBuilder getSettingsBuilder() {
            return new LootSettingsBuilder();
        }
    }
}
