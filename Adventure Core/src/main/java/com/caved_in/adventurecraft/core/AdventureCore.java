package com.caved_in.adventurecraft.core;

import com.caved_in.adventurecraft.core.command.ExchangeCommand;
import com.caved_in.adventurecraft.core.debug.DebugCustomArrows;
import com.caved_in.adventurecraft.core.debug.DebugHandCannon;
import com.caved_in.adventurecraft.core.debug.DebugMobSlayLoot;
import com.caved_in.adventurecraft.core.debug.DebugProtoFinder;
import com.caved_in.adventurecraft.core.gadget.*;
import com.caved_in.adventurecraft.core.listener.*;
import com.caved_in.adventurecraft.core.user.AdventurePlayer;
import com.caved_in.adventurecraft.core.user.AdventurerPlayerManager;
import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.palmergames.bukkit.towny.Towny;
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
    
    private MobSlayListener mobSlayListener;

    public static AdventureCore getInstance() {
        return instance;
    }

    @Override
    public void startup() {
        instance = this;

        userManager = new AdventurerPlayerManager();
        
        try {
            if (setupEconomy()) {
                debug("Economy has been setup!", "Registering exchange command");
                registerCommands(
                        new ExchangeCommand()
                );
                debug("Economy options setup");
            }
        } catch (NoClassDefFoundError e) {
            
        }
        
        mobSlayListener = new MobSlayListener();

        registerListeners(
                new PlayerConnectionListener(this, userManager),
                new PlayerHandleBunnyListener(),
                new PlayerGivePlayerFlowerListener(),
                mobSlayListener,
                new PlayerShootSelfListener()
//                new PlayerBreakTreeListener()
        );
        
        registerGadgets(
                //Prototype for the ore finder!
                IronOreFinder.getInstance(),
                CoalFinder.getInstance(),
                GoldFinder.getInstance(),
                DocileArrowGadget.getInstance(),
                ExplosiveArrowGadget.getInstance(),
                GrapplingArrowGadget.getInstance(),
                KinArrowGadget.getInstance(),
                SlowingArrowGadget.getInstance(),
                EnderArrowGadget.getInstance(),
				HealingArrow.getInstance(),
				new HandheldTntCannon()
        );

        registerDebugActions(
                new DebugMobSlayLoot(),
                new DebugProtoFinder(),
                new DebugCustomArrows(),
				new DebugHandCannon()
        );
        
        registerRecipes();
        
        getThreadManager().registerSyncRepeatTask("Regenerate gem loot",mobSlayListener::regenerateGems, TimeHandler.getTimeInTicks(5, TimeType.MINUTE),TimeHandler.getTimeInTicks(5,TimeType.MINUTE));
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

        ShapelessRecipe docileArrowRecipe = new ShapelessRecipe(DocileArrowGadget.getInstance().getItem())
                .addIngredient(1,Material.ARROW)
                .addIngredient(Material.SPIDER_EYE)
                .addIngredient(Material.BLAZE_ROD)
                .addIngredient(Material.GLOWSTONE_DUST);
        
        ShapelessRecipe explosiveArrowRecipe = new ShapelessRecipe(ExplosiveArrowGadget.getInstance().getItem())
                .addIngredient(Material.ARROW)
                .addIngredient(Material.TNT)
                .addIngredient(Material.REDSTONE);
        
        ShapelessRecipe grapplingArrowRecipe = new ShapelessRecipe(GrapplingArrowGadget.getInstance().getItem())
                .addIngredient(Material.ARROW)
                .addIngredient(Material.SLIME_BALL);
        
        ShapelessRecipe kinArrowRecipe = new ShapelessRecipe(KinArrowGadget.getInstance().getItem())
                .addIngredient(Material.ARROW)
                .addIngredient(Material.EMERALD)
                .addIngredient(Material.EGG);
        
        ShapelessRecipe slowingArrowRecipe = new ShapelessRecipe(SlowingArrowGadget.getInstance().getItem())
                .addIngredient(Material.ARROW)
                .addIngredient(Material.SPIDER_EYE)
                .addIngredient(Material.BLAZE_ROD)
                .addIngredient(Material.REDSTONE);
        
        ShapelessRecipe enderArrowRecipe = new ShapelessRecipe(EnderArrowGadget.getInstance().getItem()).addIngredient(Material.ARROW).addIngredient(Material.ENDER_PEARL);
        
        ShapelessRecipe ironOreFinder = new ShapelessRecipe(IronOreFinder.getInstance().getItem())
                .addIngredient(Material.COMPASS)
                .addIngredient(Material.IRON_BLOCK);
        
        ShapelessRecipe goldOreFinder = new ShapelessRecipe(GoldFinder.getInstance().getItem())
                .addIngredient(Material.COMPASS)
                .addIngredient(Material.GOLD_BLOCK);

        ShapelessRecipe coalFinder = new ShapelessRecipe(CoalFinder.getInstance().getItem())
                .addIngredient(Material.COMPASS)
                .addIngredient(Material.COAL_BLOCK);

		ShapelessRecipe healingArrow = new ShapelessRecipe(HealingArrow.getInstance().getItem())
				.addIngredient(Material.ARROW)
				.addIngredient(Material.COOKIE);

        if (server.addRecipe(goldOreFinder)) {
            debug("Registered the gold ore finder");
        }
        
        if (server.addRecipe(coalFinder)) {
            debug("Registered the coal finder!");
        }
        
        if (server.addRecipe(enderArrowRecipe)) {
            debug("Registered the ender arrow recipe!");
        }
        
        if (server.addRecipe(ironOreFinder)) {
            debug("Registered the iron ore finder recipe!");
        }
        
        if (server.addRecipe(slowingArrowRecipe)) {
            debug("Registered the slowing arrow recipe");
        }
        
        if (server.addRecipe(kinArrowRecipe)) {
            debug("Registered the kin arrow recipe");
        }
        
        if (server.addRecipe(grapplingArrowRecipe)) {
            debug("Registered the grappling arrow");
        }
        
        if (server.addRecipe(explosiveArrowRecipe)) {
            debug("Registered the explosive-arrow recipe");
        }
        
        if (server.addRecipe(docileArrowRecipe)) {
            debug("Registered the docile arrow recipe");
        }
        if (server.addRecipe(saddleRecipe)) {
            debug("The recipe for saddles has been registered; 4 LEATHER SHAPELESS");
        }

		if (server.addRecipe(healingArrow)) {
			debug("The recipe for healing arrows has been registered; 1 ARROW 1 COOKIE");
		}

    }

    private boolean setupEconomy() {
        if (!Plugins.isEnabled("Vault")) {
            return false;
        }
        
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
        
        public static AdventurePlayer getUserData(Player player) {
            return instance.getUserManager().getUser(player);
        }
        
        public static boolean hasEconomy() {
            return getEconomy() != null;
            
        }
        
        public static Economy getEconomy() {
            return instance.getEconomy();
        }

		public static boolean hasTowny() {
			return Plugins.getPlugin("Towny") != null;
		}

		public static Towny getTowny() {
			return (Towny)Plugins.getPlugin("Towny");
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
