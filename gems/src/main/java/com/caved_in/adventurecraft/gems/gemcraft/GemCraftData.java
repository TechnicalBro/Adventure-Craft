package com.caved_in.adventurecraft.gems.gemcraft;

import com.caved_in.adventurecraft.gems.AdventureGems;
import com.caved_in.adventurecraft.gems.event.GemCraftEvent;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.effects.ItemEffect;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.item.EnchantWrapper;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.item.ToolType;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
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

    /**
     * Perform the gem combination; Adding the enchantments of the gem, to the receiving gem or item!
     *
     * @return true if the combination was successful, false otherwise!
     */
    public boolean performCombination() {

        if (!canCraft() || !integrityPreserved()) {
            Chat.debug("No integrity / unable to craft items");
            return false;
        }

        Player p = Players.getPlayer(id);
        PlayerInventory pInv = p.getInventory();

        boolean useEnchant = Items.hasEnchantments(gem);
        boolean useEffect = AdventureGems.API.hasItemEffect(gem);

        boolean itemEnhanced = AdventureGems.API.isEnhanced(itemStack);
        int enhancedAmount = 0;

        if (itemEnhanced) {
            enhancedAmount = AdventureGems.API.getEnhancementsCount(itemStack);
        }

        ItemStack modified = itemStack.clone();

        /*
        If we're enchanting the item, then perform the following actions!
         */
        if (useEnchant) {

            Set<EnchantWrapper> gemEnchantments = Items.getEnchantments(gem);

            if (AdventureGems.API.isGem(modified)) {

                if (enhancedAmount >= AdventureGems.Settings.MAX_GEM_COMBINES) {
                    Chat.message(p, "&7<> &c&lThis gem cannot be enhanced any more.");
                    return false; //max enhancements reached for dis.
                }

                for (EnchantWrapper wrapper : gemEnchantments) {
                    Enchantment e = wrapper.getEnchantment();
                /*
                Check if any of the existing enchantments conflict with the enchantment being added!
                */
                    for (EnchantWrapper currentEnchant : Items.getEnchantments(modified)) {
                        if (currentEnchant.getEnchantment().conflictsWith(e)) {
                            Chat.debug("Enchantment " + currentEnchant.getEnchantment().getName() + " conflicts with " + e.getName());
                            return false;
                        }
                    }

                    Items.addUnsafeEnchantment(modified, wrapper.getEnchantment(), wrapper.getLevel());
                }
            } else {
                Material type = modified.getType();

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

            /*
            Todo: Retrieve the enhancement limit from the rarity of the item.
             */
                if (enhancedAmount >= AdventureGems.Settings.MAX_ITEM_ADDITIONS) {
                    Chat.message(p, "&7<> &c&lThis item cannot be enhanced any more.");
                    return false;
                }

                for (EnchantWrapper wrapper : gemEnchantments) {
                    Items.addUnsafeEnchantment(modified, wrapper.getEnchantment(), wrapper.getLevel());
                }
            }
        } else if (useEffect) {
            List<ItemEffect> effects = AdventureGems.API.getItemEffects(gem);

            effects.forEach(e -> Chat.debug("Gem has Effect: " + e.name()));

            if (AdventureGems.API.isGem(modified)) {
                if (enhancedAmount >= AdventureGems.Settings.MAX_GEM_COMBINES) {
                    Chat.message(p, "&7<> &c&lThis gem cannot be enhanced any more.");
                    return false; //max enhancements reached for dis.
                }

                for (ItemEffect e : effects) {
                    Items.addLore(modified, String.format("&a+ &e%s!", e.name()));
                }
            } else {
                Material type = modified.getType();

                switch (type) {
//                    case LEATHER_BOOTS:
//                    case LEATHER_CHESTPLATE:
//                    case LEATHER_HELMET:
//                    case LEATHER_LEGGINGS:
//                    case IRON_BOOTS:
//                    case IRON_LEGGINGS:
//                    case IRON_CHESTPLATE:
//                    case IRON_HELMET:
//                    case GOLD_HELMET:
//                    case GOLD_CHESTPLATE:
//                    case GOLD_LEGGINGS:
//                    case GOLD_BOOTS:
//                    case DIAMOND_HELMET:
//                    case DIAMOND_CHESTPLATE:
//                    case DIAMOND_LEGGINGS:
//                    case DIAMOND_BOOTS:
//                    case CHAINMAIL_BOOTS:
//                    case CHAINMAIL_CHESTPLATE:
//                    case CHAINMAIL_HELMET:
//                    case CHAINMAIL_LEGGINGS:
//                    case FISHING_ROD:
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
                        break;
                    default:
                        return false;
                }


                if (enhancedAmount >= AdventureGems.Settings.MAX_ITEM_ADDITIONS) {
                    Chat.message(p, "&7<> &c&lThis item cannot be enhanced any more.");
                    return false;
                }

                for (ItemEffect e : effects) {
                    e.apply(modified);
                    Chat.debug("Applied Effect " + e.name() + " to " + Items.getName(modified));
                }

            }

        } else {
            return false;
        }

        String modSearch = "[+" + enhancedAmount + "]";
        enhancedAmount += 1;
        String modReplace = "[+" + enhancedAmount + "]";

//        Chat.debug("Searching for " + modSearch,"Replacing with " + modReplace);

        String itemName = Items.getName(modified);

        if (itemName == null) {
            return false;
        }

        if (itemEnhanced) {
            itemName = StringUtils.replace(itemName, modSearch, modReplace);
        } else {
            itemName = StringUtils.join(itemName, " &e", modReplace);
        }

        Items.setName(modified, itemName);

        //Create the event that will be called when a gem and target are being combined.
        GemCraftEvent event = new GemCraftEvent(p, gem, itemStack);

        Plugins.callEvent(event);

        //todo check if cancelled, and such. Move confirmation menu to the listener, not the handler

        int gemSlot = Inventories.getSlotOf(pInv, gem);
        int itemSlot = Inventories.getSlotOf(pInv, itemStack);

        if (gemSlot == -1) {
//            Chat.debug("Unable to find gem in players inventory!");
            return false;
        }

        //Clear the slot of the gem in the players inventory
        gem = Items.removeFromStack(gem, 1);

        //If the player has no more gems in the stack, then clear the whole stack
        if (gem == null || gem.getAmount() == 0) {
            Inventories.clearSlot(pInv, gemSlot);
        } else {
            //Otherwise we want to just remove a gem from the stack.
            Inventories.setItem(pInv, gemSlot, gem);
        }

        if (itemSlot == -1) {
            if (itemStack == null) {
//                Chat.debug("ITEM IS NULL WHILE COMBINING, FUCK THAT SHIT");
                return false;
            }

            int slot = Inventories.getFirst(pInv, itemStack);

//            Chat.debug("Found itemstack @ slot " + slot);

            ItemStack originalItem = pInv.getItem(slot);
            Items.removeFromStack(originalItem, 1);

            if (originalItem.getAmount() == 0 || originalItem == null) {
                Inventories.clearSlot(pInv, slot);
            } else {
                Inventories.setItem(pInv, slot, originalItem);
            }

//            Chat.debug("Was unable to find item at slot [" + itemSlot + "] // " + Items.getName(itemStack));
//            Chat.debug("Giving player modified item");
            Players.giveItem(p, modified, true);
            p.updateInventory();
            return true;
        }

        Inventories.setItem(pInv, itemSlot, modified);

        p.updateInventory();
        return true;
    }

    public boolean hasGem() {
        return gem != null;
    }

    public boolean hasItem() {
        return itemStack != null;
    }
}
