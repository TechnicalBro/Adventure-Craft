package com.caved_in.adventurecraft.core.listener;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.adventurecraft.core.gadget.MonsterExamineGadget;
import com.caved_in.adventurecraft.core.gadget.PartyCrackerGadget;
import com.caved_in.adventurecraft.core.loot.CreatureLootTable;
import com.caved_in.adventurecraft.core.user.AdventurePlayer;
import com.caved_in.adventurecraft.core.user.AdventurerPlayerManager;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.Title;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {
    private AdventureCore core;
    private AdventurerPlayerManager users;
    private Economy economy;

    private static TitleBuilder welcomeTitleBuilder = new TitleBuilder().title("").fadeIn(1).stay(2).fadeOut(1);

    private static TitleBuilder welcomeBackBuilder = new TitleBuilder().title("&aWelcome Back!").fadeIn(1).stay(2).fadeOut(1);

    public PlayerConnectionListener(AdventureCore core, AdventurerPlayerManager userManager) {
        this.core = core;
        this.users = userManager;
        this.economy = core.getEconomy();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (users.hasOfflineData(p.getUniqueId())) {
            boolean loaded = users.loadData(p.getUniqueId());
            if (!loaded) {
                core.debug("Unable to load data for the player");
                return;
            }

            //Welcome back package!
            AdventurePlayer user = users.getUser(p);
            int daysSinceLastPlayed = user.getDaysSinceLastPlayed();

            if (daysSinceLastPlayed >= AdventureCore.Properties.DAYS_BEFORE_WELCOME_BACK_PACKAGE) {

                //Create the welcome back title showing how many days since last played and send it to the player
                if (economy != null) {
                    economy.depositPlayer(p, AdventureCore.Properties.WELCOME_BACK_PACKAGE_MONEY);
                    Chat.actionMessage(p, "&6Welcome Back! &a+" + AdventureCore.Properties.WELCOME_BACK_PACKAGE_MONEY + "$");
                }

                PartyCrackerGadget.getInstance().giveTo(p);
                //todo move this welcome message to a config file!
                Chat.message(p, "&a&lWelcome back to the Vortechs!", "&c&lStart a party with your brand new &6Party Cracker");

            }

            Title title;
            if (daysSinceLastPlayed == 0) {
                title = welcomeBackBuilder.subtitle("&eLast Played &cToday&e!").build();
            } else {
                title = welcomeBackBuilder.subtitle(Chat.format("&eLast Played &c%s&e days ago", daysSinceLastPlayed)).build();
            }

            title.send(p);

            /*
            Update when the user last played.
             */
            user.updateLastPlayed();

        } else {
            users.addUser(p);
            core.debug("Player " + p.getName() + " has joined the Vortechs for the first time!");
            //todo move this welcome title formatting to a config file
            welcomeTitleBuilder.subtitle(String.format("%sWelcome %s to %sVortechs!", ChatColor.YELLOW.toString(), p.getName(), ChatColor.GOLD.toString())).build().broadcast();
            //todo move the items to a config file for when someone joins!
            Players.giveItem(p,
                    Items.makeItem(Material.STONE_PICKAXE),
                    AdventureLoot.API.createItem(CreatureLootTable.TIER_1_SWORD_SETTINGS),
                    ItemBuilder.of(Material.FISHING_ROD).durability((short) 10).name("&eWorn Fishing Rod").item(),
                    ItemBuilder.of(Material.APPLE).amount(5).item(),
                    MonsterExamineGadget.getInstance().getItem(),
                    PartyCrackerGadget.getInstance().getItem(),
                    Items.makeItem(Material.MILK_BUCKET)

            );
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        boolean saved = users.save(player.getUniqueId());
        if (saved) {
            core.debug("Saved data for " + player.getName());
        } else {
            core.debug("Failed to save data for " + player.getName());
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        Player player = e.getPlayer();

        boolean saved = users.save(player.getUniqueId());
        if (saved) {
            core.debug("Saved data for " + player.getName());
        } else {
            core.debug("Failed to save data for " + player.getName());
        }
    }
}
