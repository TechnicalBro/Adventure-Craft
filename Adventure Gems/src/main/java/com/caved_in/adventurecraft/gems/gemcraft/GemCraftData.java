package com.caved_in.adventurecraft.gems.gemcraft;

import com.caved_in.adventurecraft.gems.AdventureGems;
import com.caved_in.adventurecraft.gems.event.GemCraftEvent;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.item.EnchantWrapper;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.item.ToolType;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Set;
import java.util.UUID;

public class GemCraftData {
    private UUID id;
    private String name;

    private ItemStack gem;

    private ItemStack itemStack;


    public GemCraftData(ItemStack gem, Player player) {
        this.gem = gem;
        this.id = player.getUniqueId();
        this.name = player.getName();
    }

    public GemCraftData item(ItemStack item) {
        this.itemStack = item;
        return this;
    }

    public boolean integrityPreserved() {
        if (!Players.isOnline(id)) {
            return false;
        }

        Player p = Players.getPlayer(id);

        if (canCraft()) {
            return Players.hasItem(p, itemStack) && Players.hasItem(p, gem);
        }

        if (itemStack == null && gem != null) {
            return Players.hasItem(p, gem);
        }

        return false;
    }

    public boolean canCraft() {
        return itemStack != null && gem != null;
    }

    public boolean performCombination() {

        if (!canCraft() || !integrityPreserved()) {
//            Chat.debug("No integrity / unable to craft items");
            return false;
        }

        Player p = Players.getPlayer(id);
        PlayerInventory pInv = p.getInventory();

        Set<EnchantWrapper> gemEnchantments = Items.getEnchantments(gem);

        boolean itemEnhanced = AdventureGems.API.isEnhanced(itemStack);
        
//        Chat.debug("Item Enhanced? : " + String.valueOf(itemEnhanced));

        ItemStack modified = null;

        int enhancedAmount = 0;

        if (itemEnhanced) {
            enhancedAmount = AdventureGems.API.getEnhancementsCount(itemStack);
        }
//        Chat.debug("Item has " + enhancedAmount + " enhancements");

        if (AdventureGems.API.isGem(itemStack)) {

            if (enhancedAmount >= AdventureGems.Settings.MAX_GEM_COMBINES) {
                Chat.message(p, "&7<> &c&lThis gem cannot be enhanced any more.");
                return false; //max enhancements reached for dis.
            }
            
            for(EnchantWrapper wrapper : gemEnchantments) {
				Enchantment e = wrapper.getEnchantment();
				/*
				Check if any of the existing enchantments conflict with the enchantment being added!
				 */
				for(EnchantWrapper currentEnchant : Items.getEnchantments(itemStack)) {
					if (currentEnchant.getEnchantment().conflictsWith(e)) {
						return false;
					}
				}
                Items.addUnsafeEnchantment(itemStack,wrapper.getEnchantment(),wrapper.getLevel());
            }

//            Items.addEnchantments(itemStack, gemEnchantments);

            modified = itemStack.clone();
        } else {
            Material type = itemStack.getType();

            switch (type) {
                case LEATHER_BOOTS:
                case LEATHER_CHESTPLATE:
                case LEATHER_HELMET:
                case LEATHER_LEGGINGS:
                case IRON_BOOTS:
                case IRON_LEGGINGS:
                case IRON_CHESTPLATE:
                case IRON_HELMET:
                case GOLD_HELMET:
                case GOLD_CHESTPLATE:
                case GOLD_LEGGINGS:
                case GOLD_BOOTS:
                case DIAMOND_HELMET:
                case DIAMOND_CHESTPLATE:
                case DIAMOND_LEGGINGS:
                case DIAMOND_BOOTS:
                case CHAINMAIL_BOOTS:
                case CHAINMAIL_CHESTPLATE:
                case CHAINMAIL_HELMET:
                case CHAINMAIL_LEGGINGS:
                case WOOD_AXE:
                case IRON_AXE:
                case GOLD_AXE:
                case STONE_AXE:
                case DIAMOND_AXE:
                case WOOD_SWORD:
                case IRON_SWORD:
                case GOLD_SWORD:
                case STONE_SWORD:
                case DIAMOND_SWORD:
                case WOOD_HOE:
                case STONE_HOE:
                case IRON_HOE:
                case GOLD_HOE:
                case DIAMOND_HOE:
                case WOOD_SPADE:
                case STONE_SPADE:
                case IRON_SPADE:
                case GOLD_SPADE:
                case DIAMOND_SPADE:
                case BOW:
                case FISHING_ROD:
//                    Chat.debug("Valid type!! --> " + type.name());
                    break;
                default:
                    return false;
            }

            if (enhancedAmount >= AdventureGems.Settings.MAX_ITEM_ADDITIONS) {
                Chat.message(p, "&7<> &c&lThis item cannot be enhanced any more.");
                return false;
            }

            Items.addEnchantments(itemStack, gemEnchantments);

            modified = itemStack.clone();
        }

        String modSearch = "[+" + enhancedAmount + "]";
        enhancedAmount += 1;
        String modReplace = "[+" + enhancedAmount + "]";
        
//        Chat.debug("Searching for " + modSearch,"Replacing with " + modReplace);
        
        String itemName = Items.getName(itemStack);
        
        if (itemName == null) {
            return false;
        }
        
        if (itemEnhanced) {
            itemName = StringUtils.replace(itemName,modSearch,modReplace);
        } else {
            itemName = StringUtils.join(itemName," &e",modReplace);
        }
        
        Items.setName(modified,itemName);

        GemCraftEvent event = new GemCraftEvent(p, gem, itemStack);

        Plugins.callEvent(event);

        //todo check if cancelled, and such. Move confirmation menu to the listener, not the handler

        Inventories.clearSlot(pInv, Inventories.getSlotOf(pInv, gem));
        Inventories.clearSlot(pInv, Inventories.getSlotOf(pInv, itemStack));

        p.updateInventory();
        Players.giveItem(p, modified);
        return true;
    }

    public boolean hasGem() {
        return gem != null;
    }

    public boolean hasItem() {
        return itemStack != null;
    }
}
