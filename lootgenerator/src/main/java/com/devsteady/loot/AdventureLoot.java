package com.devsteady.loot;

import com.devsteady.loot.command.LootCommand;
import com.devsteady.loot.debug.DebugGenerateWithItemEffect;
import com.devsteady.loot.debug.DebugItemEffect;
import com.devsteady.loot.debug.DebugLootGenerator;
import com.devsteady.loot.debug.DebugLootTableSerialization;
import com.devsteady.loot.effects.*;
import com.devsteady.loot.generator.LootGenerator;
import com.devsteady.loot.generator.data.LootTable;
import com.devsteady.loot.generator.settings.LootSettings;
import com.devsteady.loot.generator.settings.LootSettingsBuilder;
import com.devsteady.loot.listener.ItemEffectListener;
import com.devsteady.loot.listener.LootGenerateListener;
import com.devsteady.loot.users.ItemUserManager;
import com.devsteady.loot.util.ItemHandler;
import com.devsteady.onyx.game.CraftGame;
import com.devsteady.onyx.game.listener.UserManagerListener;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.Set;

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

        API.init();

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

        private static AdventureLoot instance = null;

        private static ItemHandler itemHandler = null;

        private static boolean init = false;

        public static void init() {
            if (init) {
                return;
            }

            instance = getInstance();
            itemHandler = instance.getItemEffectHandler();
            init = true;
        }

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
            itemHandler.registerItemEffects(effects);
        }

        public static boolean hasItemEffect(ItemStack item) {
            return itemHandler.hasEffect(item);
        }

        public static ItemEffect getEffect(String name) {
            return itemHandler.getEffect(name);
        }

        public static boolean effectExists(String name) {
            return itemHandler.effectExists(name);
        }

        public static Set<ItemEffect> getEffects(ItemStack item) {
            return itemHandler.getEffects(item);
        }

        public static boolean hasDamageRange(ItemStack item) {
            if (item == null) {
                return false;
            }
            return itemHandler.hasDamageRange(item);
        }
    }
}
