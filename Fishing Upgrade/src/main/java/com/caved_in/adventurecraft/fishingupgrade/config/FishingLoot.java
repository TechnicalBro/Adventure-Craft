package com.caved_in.adventurecraft.fishingupgrade.config;

import com.caved_in.commons.config.XmlItemStack;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FishingLoot {

    private XmlItemStack serializedItem;
    
    private int chance = 0;

    public static FishingLoot create() {
        return new FishingLoot();
    }
    
    public FishingLoot(XmlItemStack itemStack, int chance) {
                
    }
    
    public FishingLoot() {
        
    }
    
    public FishingLoot item(ItemStack item) {
        this.serializedItem = XmlItemStack.fromItem(item);
        return this;
    }
    
    public FishingLoot item(ItemBuilder builder) {
        this.serializedItem = XmlItemStack.fromItem(builder.item());
        return this;
    }
    
    public FishingLoot chance(int chance) {
        this.chance = chance;
        return this;
    }
    
    public ItemStack spawn() {
        if (!NumberUtil.percentCheck(chance)) {
            return null;
            //TODO implement the NO_NULL_ITEMS check
        }
        
        return serializedItem.getItemStack();
    }
    
}
