package com.caved_in.adventurecraft.gems.listener;

import com.caved_in.adventurecraft.gems.AdventureGems;
import com.caved_in.adventurecraft.gems.gemcraft.GemCraftHandler;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    private AdventureGems plugin;
    private GemCraftHandler gemHandler = null;
    
    public PlayerInteractListener() {
        this.plugin = AdventureGems.getInstance();
        this.gemHandler = plugin.getGemHandler();
    }
    
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        
        Action action = e.getAction();

        switch (action) {
            case LEFT_CLICK_BLOCK:
            case LEFT_CLICK_AIR:
            case PHYSICAL:
                return;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                if (Players.handIsEmpty(p)) {
                    return;
                }

                ItemStack hand = e.getItem();
                //If the player doesn't have a gem in their hand, check for crafting!
                if (!AdventureGems.API.isGem(hand)) {
                    if (gemHandler.hasGemSelected(p)) {
                        e.setCancelled(true);

                        /*
                        Simple debug to check if the player is combining armor
                         */
                        if (Items.isArmor(hand)) {
                            if (Players.isDebugging(p)) {
                                Chat.message(p, "&cYou es crafting armor");
                            }
                        }

                        gemHandler.setItem(p, hand);
                        gemHandler.performCraft(p);
                    }
                    return;
                } else {
                    //todo fix gem's being combined
//                    if (gemHandler.hasGemSelected(p)) {
//                        Chat.debug(p.getName() + " has gem selected! Assigning item to " + Items.getName(hand));
//                        gemHandler.setItem(p, hand);
//
//                        Chat.debug("Performing craft!");
//                        gemHandler.performCraft(p);
//                        return;
//                    }
                    gemHandler.startCraft(p,hand);
                }
                break;
            default:
                break;
        }
        
    }
}
