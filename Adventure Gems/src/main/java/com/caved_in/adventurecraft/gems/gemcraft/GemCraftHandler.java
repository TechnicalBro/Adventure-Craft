package com.caved_in.adventurecraft.gems.gemcraft;

import com.caved_in.adventurecraft.gems.AdventureGems;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.menu.menus.confirmation.ConfirmationMenu;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GemCraftHandler {
    private AdventureGems parent = null;

    private Map<UUID, GemCraftData> data = new HashMap<>();

    public GemCraftHandler(AdventureGems plugin) {
        this.parent = plugin;
    }

    public boolean hasData(Player p) {
        return data.containsKey(p.getUniqueId());
    }

    public boolean hasGemSelected(Player p) {
        return hasData(p) && data.get(p.getUniqueId()).hasGem();
    }

    public void setItem(Player p, ItemStack item) {
        if (!hasData(p)) {
            return;
        }
        data.get(p.getUniqueId()).item(item);
    }

    public void startCraft(Player p, ItemStack gem) {
        data.put(p.getUniqueId(), new GemCraftData(gem, p));
        Chat.actionMessage(p, "&a&lGem Selected;&c&lNow select the item you'd like to enhance!");
    }

    public void performCraft(Player p) {
        if (!hasData(p)) {
            //todo message player;

            return;
        }

        GemCraftData gemData = data.get(p.getUniqueId());

        /*
        Open a confirmation menu for the player, to assure they actually want to combine their items together!
         */
        ConfirmationMenu.of("Combine your gem(s) & item(s)?")
            .exitOnClickOutside(false)
            .onConfirm((menu, player) -> {
                        if (gemData.performCombination()) {
                            TitleBuilder.create().title("&aSuccess!").subtitle("&eYour items have been combined!").fadeIn(1).stay(2).fadeOut(1).build().send(player);
                        } else {
                            TitleBuilder.create().title("&eOops!").subtitle("&cYou've failed to combine the items.").fadeIn(1).stay(2).fadeOut(1).build().send(player);
                        }
                        menu.closeMenu(player);
                    }
            ).onDeny((menu, player) -> {
            Chat.actionMessage(player, "&c&lGem / Item Combination has been cancelled");
            menu.closeMenu(player);
        }).onClose((menu, player) -> {
            Chat.debug("Clearing data for " + player.getName());
            data.remove(p.getUniqueId());
            Chat.debug("Cleared the data for " + player.getName());
        }).openMenu(p);
    }
}
