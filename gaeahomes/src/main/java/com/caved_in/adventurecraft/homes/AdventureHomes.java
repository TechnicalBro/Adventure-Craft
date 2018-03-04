package com.caved_in.adventurecraft.homes;

import com.caved_in.adventurecraft.homes.command.HomeCommands;
import com.caved_in.adventurecraft.homes.config.HomeConfig;
import com.caved_in.adventurecraft.homes.listener.PlayerConnectionListener;
import com.caved_in.adventurecraft.homes.menu.HomeMenu;
import com.caved_in.adventurecraft.homes.users.HomePlayer;
import com.caved_in.adventurecraft.homes.users.HomePlayerManager;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.UUID;

public class AdventureHomes extends BukkitPlugin {
    private static AdventureHomes instance = null;

    private static final String USER_DATA_FOLDER = "plugins/Adventure-Homes/users/";

    private static final String CONFIG_FILE_LOCATION = "plugins/Adventure-Homes/Config.xml";

    private HomePlayerManager users = null;

    private static HomeConfig pluginConfig = null;

    public static AdventureHomes getInstance() {
        return instance;
    }

    private static Economy economy = null;

    @Override
    public void startup() {
        instance = this;
        
        /*
        Create the manager for all the users who join the server.
         */
        users = new HomePlayerManager();

        registerListeners(
                /*
                Register the connection listener, which requires
                the homes plugin and our actual player manager.
                 */
                new PlayerConnectionListener(this, users)
        );

        registerCommands(
                new HomeCommands()
        );

        getThreadManager().registerSyncRepeatTask("Auto Save", () -> {
            users.saveAll();
        }, 20, TimeHandler.getTimeInTicks(30, TimeType.SECOND));
    }

    @Override
    public void shutdown() {
        users.saveAll();
        debug("Saved all user data!");
    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis";
    }

    @Override
    public void initConfig() {
        Serializer serializer = new Persister();

        File userDataFolder = new File(USER_DATA_FOLDER);
        if (!userDataFolder.exists()) {
            if (userDataFolder.mkdirs()) {
                debug("Created the default user data folder!");
            } else {
                debug("UNABLE TO LOAD THE ADVENTURE-HOMES PLUGIN DUE TO FILE PERMISSION ERROR.", "  - WAS NOT ABLE TO CREATE USER DATA FOLDER @ " + USER_DATA_FOLDER);
                onDisable();
            }
        }

        File configurationFile = new File(CONFIG_FILE_LOCATION);
        if (!configurationFile.exists()) {
            pluginConfig = new HomeConfig();
            try {
                serializer.write(pluginConfig, configurationFile);
                debug("Created Adventure-Homes configuration file!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                pluginConfig = serializer.read(HomeConfig.class, configurationFile);
                debug("Loaded Adventure-Homes configuration file!");
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public File getUserDataFolder() {
        return new File(USER_DATA_FOLDER);
    }

    public HomePlayerManager getUserManager() {
        return users;
    }

    public static class API {

        public static HomePlayer getUser(UUID id) {
            return AdventureHomes.instance.getUserManager().getUser(id);
        }

        public static HomePlayer getUser(Player player) {
            return getUser(player.getUniqueId());
        }

        public static HomeMenu getMenu(Player player) {
            return new HomeMenu(getUser(player));
        }

        public static boolean openMenu(Player player) {
            if (getUser(player).hasHome()) {
                getMenu(player).openMenu(player);
                return true;
            } else {
                return false;
            }
        }

        public static boolean hasEconomy() {
            return economy != null;
        }

        public static Economy getEconomy() {
            return economy;
        }
    }

    public static class Properties {
        public static boolean requirePermission() {
            return pluginConfig.isRequirePermission();
        }

        public static String getPermissionRequired() {
            return pluginConfig.getPermissionRequired();
        }

        public static boolean allowCombatTeleport() {
            return pluginConfig.isCombatTeleport();
        }

        public static int combatTeleportCooldown() {
            return pluginConfig.getCombatTeleportCooldown();
        }

        public static boolean requirePayment() {
            return pluginConfig.isRequirePayment();
        }

        public static double paymentRequired() {
            return pluginConfig.getPaymentRequired();
        }

        public static boolean hasTeleportCooldown() {
            return pluginConfig.isTeleportCooldown();
        }

        public static int teleportCooldownTime() {
            return pluginConfig.getCooldownAmount();
        }

        public static int getMaxHomeCount() {
            return pluginConfig.getMaximumHomeCount();
        }
    }
}
