package com.caved_in.adventurecraft.core;

import com.caved_in.adventurecraft.core.command.ExchangeCommand;
import com.caved_in.adventurecraft.core.listener.PlayerConnectionListener;
import com.caved_in.adventurecraft.core.listener.PlayerGivePlayerFlowerListener;
import com.caved_in.adventurecraft.core.listener.PlayerHandleBunnyListener;
import com.caved_in.adventurecraft.core.user.AdventurePlayer;
import com.caved_in.adventurecraft.core.user.AdventurerPlayerManager;
import com.caved_in.adventurecraft.core.user.upgrades.PlayerUpgrade;
import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.item.ItemBuilder;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;

public class AdventureCore extends CraftGame<AdventurerPlayerManager> {

    /*
    Singleton instance of the adventure core!
     */
    private static AdventureCore instance = null;

    /*
    Manager of the user classes- Handles all operations related to players
    and specific data or operations on them inside the game, or proxies towards it.
    It just works :D
     */
    private AdventurerPlayerManager userManager = null;

    /*
    Vault Economy! Used for handling player data and moolah!
     */
    private static Economy economy = null;

    private static final String USER_DATA_FOLDER = "plugins/Adventure-Core/users/";

    public static AdventureCore getInstance() {
        return instance;
    }

    @Override
    public void startup() {
        instance = this;

        userManager = new AdventurerPlayerManager();
        
        if (!setupEconomy()) {
            debug("Unable to setup economy!");
            onDisable();
        }

        registerListeners(
                new PlayerConnectionListener(this, userManager),
                new PlayerHandleBunnyListener(),
                new PlayerGivePlayerFlowerListener()
        );

        registerCommands(
                new ExchangeCommand()
        );
        
        registerRecipes();
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
        File userFolder = new File(USER_DATA_FOLDER);
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public long tickDelay() {
        return 20;
    }

    @Override
    public AdventurerPlayerManager getUserManager() {
        return userManager;
    }

    public File getUserDataFolder() {
        return new File(USER_DATA_FOLDER);
    }

    private void registerRecipes() {
        Server server = getServer();
        /*
        Make saddles craftable with 4 leather!
         */

        ShapelessRecipe saddleRecipe = new ShapelessRecipe(ItemBuilder.of(Material.SADDLE).item()).addIngredient(4, Material.LEATHER);

        if (server.addRecipe(saddleRecipe)) {
            debug("The recipe for saddles has been registered; 4 LEATHER SHAPELESS");
        }

    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    
    public Economy getEconomy() {
        return economy;
    }
    
    public static class API {
        public static int getUpgradeLevel(Player player, PlayerUpgrade.Type upgrade) {
            return getUserData(player).getUpgradeData(upgrade).getLevel();
        }
        
        public static AdventurePlayer getUserData(Player player) {
            return instance.getUserManager().getUser(player);
        }
        
        public static boolean hasEconomy() {
            return getEconomy() != null;
            
        }
        
        public static Economy getEconomy() {
            return instance.getEconomy();
        }
    }

    public static class Properties {
        public static final int GOLD_PER_EXP = 3;
        
        /*
        The hard cap on how much players can upgrade their skills, how many times.
         */
        public static final int MAX_UPGRADE_LEVEL = 30;
    
        /*
        How much each individual level will cost to upgrade
         */
        public static final int BASE_PRICE_PER_LEVEL = 1500;
    }
}
