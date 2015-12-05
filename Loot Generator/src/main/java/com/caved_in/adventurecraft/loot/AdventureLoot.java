package com.caved_in.adventurecraft.loot;

import com.caved_in.adventurecraft.loot.command.LootCommand;
import com.caved_in.adventurecraft.loot.debug.DebugGenerateWithItemEffect;
import com.caved_in.adventurecraft.loot.debug.DebugItemEffect;
import com.caved_in.adventurecraft.loot.debug.DebugLootGenerator;
import com.caved_in.adventurecraft.loot.debug.DebugLootTableSerialization;
import com.caved_in.adventurecraft.loot.effects.*;
import com.caved_in.adventurecraft.loot.generator.LootGenerator;
import com.caved_in.adventurecraft.loot.generator.data.LootTable;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettingsBuilder;
import com.caved_in.adventurecraft.loot.listener.ItemEffectListener;
import com.caved_in.adventurecraft.loot.listener.LootGenerateListener;
import com.caved_in.adventurecraft.loot.users.ItemUserManager;
import com.caved_in.adventurecraft.loot.util.ItemHandler;
import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.game.listener.UserManagerListener;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.plugin.Plugins;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class AdventureLoot extends CraftGame<ItemUserManager> {
    private static AdventureLoot instance;

    private static LootGenerator generator;

    //Core of this plugins functionality, the listener where all actions
    //regarding item effects take place, and are processed per item that was used!
    private ItemEffectListener itemEffectListener;

    //Listener used to handle the connections of incoming and outgoing players.
    private UserManagerListener userManagerListener;

    //Ahh! The item user manager, handles all our custom player data!
    private ItemUserManager userManager;

    //The item Effect handler manages the registration of all item effects!
    private ItemHandler itemEffectHandler;

    public static AdventureLoot getInstance() {
        return instance;
    }

    @Override
    public void startup() {
        instance = this;

        generator = new LootGenerator(this);

        userManager = new ItemUserManager();

        itemEffectHandler = new ItemHandler(this);

        registerListeners(
                userManagerListener = new UserManagerListener(this),
                itemEffectListener = new ItemEffectListener(this),
                new LootGenerateListener()
        );

        registerDebugActions(
                DebugItemEffect.getInstance(),
                new DebugLootGenerator(),
                new DebugLootTableSerialization(),
                new DebugGenerateWithItemEffect()
        );

        getItemEffectHandler().registerItemEffects(
                FlameStrikeEffect.getInstance(),
                CriticalStrikeEffect.getInstance(),
                BleedEffect.getInstance(),
                BackstabEffect.getInstance(),
                PoisonEffect.getInstance(),
                PhaseEffect.getInstance(),
                LifeLeechEffect.getInstance()
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

    @Override
    public void update() {

    }

    @Override
    public long tickDelay() {
        //todo implement tickable effects into game thread.
        return 20;
    }

    @Override
    public ItemUserManager getUserManager() {
        return userManager;
    }

    public ItemHandler getItemEffectHandler() {
        return itemEffectHandler;
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

        public static void registerItemEffects(ItemEffect... effects) {
            getInstance().getItemEffectHandler().registerItemEffects(effects);
        }
    }
}
