package com.caved_in.adventurecraft.gems.item;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.javatuples.Pair;

import java.util.Optional;

public class GemGenerator {
    
    public static String GEM_NAME_BASE = "&d%s Gem of %s";
    
    public Optional<ItemStack> generateGem(GemType type, GemSettings settings) {
        Material material = type.getType();
        
        ChancedEnchantment enchantment = settings.getRandomEnchantment();
        
        Optional<Pair<Enchantment, Integer>> eInfo = enchantment.getOptionalEnchantmentTuple();
        
        if (!eInfo.isPresent()) {
            return Optional.empty();
        }

        Pair<Enchantment, Integer> enchantInfo = eInfo.get();

        String prefix = settings.getPrefixForLevel(enchantInfo.getValue1());
        String suffix = settings.getSuffixFor(enchantInfo.getValue0(),enchantInfo.getValue1());
        
        return Optional.of(ItemBuilder.of(material).name(String.format(GEM_NAME_BASE,prefix,suffix)).enchantment(enchantment.getEnchantment(),enchantment.getLevel()).item());
        
    }
    
    public ItemStack createGem(GemType type, GemSettings settings) {
        Material material = type.getType();
        
        ChancedEnchantment chanceEnchant = settings.getRandomEnchantment();
        
        if (chanceEnchant == null) {
//            Chat.debug("NULL CHANCED ENCHANTMENT");
            return null;
        }
        
        Enchantment enchant = chanceEnchant.getEnchantment();
        int level = chanceEnchant.getLevel();
        
        String prefix = settings.getPrefixForLevel(level);
        String suffix = settings.getSuffixFor(enchant,level);
        String name = String.format(GEM_NAME_BASE,prefix,suffix);
        
//        Chat.debug(String.format("Creating Gem '%s' with enchantment [%s] @ lvl (%s)",name,enchant.getName(),level));
        return ItemBuilder.of(material).name(name).enchantment(enchant,level).item();
    }
}
