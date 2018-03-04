package com.caved_in.adventurecraft.homes.listener;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.adventurecraft.homes.event.PlayerTeleportHomeEvent;
import com.caved_in.adventurecraft.homes.users.HomePlayer;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.warp.Warp;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTeleportHomeListener implements Listener {

    private static PlayerTeleportHomeListener instance = null;

    public static PlayerTeleportHomeListener getInstance() {
        if (instance == null) {
            instance = new PlayerTeleportHomeListener();
        }
        return instance;
    }

    protected PlayerTeleportHomeListener() {

    }

    @EventHandler
    public void onPlayerTeleportHome(PlayerTeleportHomeEvent e) {
        Player player = e.getPlayer();
        Warp warp = e.getHome();

        if (AdventureHomes.Properties.requirePermission()) {
            if (!Players.hasPermission(player, AdventureHomes.Properties.getPermissionRequired())) {
                Chat.format(player, "&cYou don't have the permissions required to teleport home!");
                e.setCancelled(true);
                return;
            }
        }

        HomePlayer user = AdventureHomes.API.getUser(player);
        if (!AdventureHomes.Properties.allowCombatTeleport()) {
            if (user.isOnCombatTag()) {
                Chat.format(player, "&c&lYou're unable to teleport while on combat cooldown!");
                e.setCancelled(true);
                return;
            }
        }

        if (AdventureHomes.Properties.hasTeleportCooldown()) {
            if (user.isOnTeleportCooldown()) {
                Chat.format(player, "&c&lYou're unable to teleport while on cooldown!");
                e.setCancelled(true);
                return;
            }
        }

        if (AdventureHomes.Properties.requirePayment()) {
            if (!AdventureHomes.API.hasEconomy()) {
                return;
            }

            Economy economy = AdventureHomes.API.getEconomy();
            double paymentRequired = AdventureHomes.Properties.paymentRequired();

            if (!economy.has(player, paymentRequired)) {
                Chat.format(player, "&cYou don't the &o%s&r&c required to perform this action.", economy.format(paymentRequired));
                e.setCancelled(true);
                return;
            }

            EconomyResponse response = economy.withdrawPlayer(player, paymentRequired);
            if (!response.transactionSuccess()) {
                e.setCancelled(true);
                return;
            }

            Chat.format(player, "&eYou've been charged &6%s&e for usage of the home teleport.", economy.format(paymentRequired));
        }
    }
}
