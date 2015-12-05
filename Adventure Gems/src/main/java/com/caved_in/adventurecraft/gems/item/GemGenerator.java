package com.caved_in.adventurecraft.gems.item;

import com.caved_in.adventurecraft.loot.effects.ItemEffect;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.utilities.NumberUtil;
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
        ChancedItemEffect effect = settings.getRandomItemEffect();

        Optional<Pair<Enchantment, Integer>> eInfo = enchantment.getOptionalEnchantmentTuple();
        Optional<ItemEffect> optionalEffect = effect.getOptionalItemEffect();

        /*
        If there's no item enchantment info or item effect present, then we're not
        generating a gem!
         */
        if (!eInfo.isPresent() && !optionalEffect.isPresent()) {
            return Optional.empty();
        }

        boolean useEnchant = eInfo.isPresent();
        boolean useEffect = optionalEffect.isPresent();

        //todo Optionally allow both enchant and item effect to be applied
        if (useEnchant && useEffect) {
            if (NumberUtil.percentCheck(50)) {
                useEffect = false;
            } else {
                useEnchant = false;
            }
        }

        String prefix = "";
        String suffix = "";

        if (useEnchant) {
            Pair<Enchantment, Integer> enchantInfo = eInfo.get();

            prefix = settings.getPrefixForLevel(enchantInfo.getValue1());
            suffix = settings.getSuffixFor(enchantInfo.getValue0(), enchantInfo.getValue1());

            return Optional.of(ItemBuilder.of(material).name(String.format(GEM_NAME_BASE, prefix, suffix)).enchantment(enchantment.getEnchantment(), enchantment.getLevel()).item());
        } else {
            ItemEffect iEffect = optionalEffect.get();
            suffix = settings.getSuffixSettings(iEffect).suffix;
            return Optional.of(ItemBuilder.of(material).name(String.format(GEM_NAME_BASE, prefix, suffix)).lore(String.format("&a+ &e%s!", iEffect.name())).item());
        }
    }

    public ItemStack createGem(GemType type, GemSettings settings) {
        Material material = type.getType();

        ChancedEnchantment chanceEnchant = settings.getRandomEnchantment();

        ChancedItemEffect chanceEffect = settings.getRandomItemEffect();

        if (chanceEnchant == null || chanceEffect == null) {
//            Chat.debug("NULL CHANCED ENCHANTMENT");
            return null;
        }

        boolean useEnchant = false;
        boolean useEffect = false;
        if (NumberUtil.percentCheck(50)) {
            useEffect = true;
        } else {
            useEnchant = true;
        }

        if (useEnchant) {
            Enchantment enchant = chanceEnchant.getEnchantment();
            int level = chanceEnchant.getLevel();

            String prefix = settings.getPrefixForLevel(level);
            String suffix = settings.getSuffixFor(enchant, level);
            String name = String.format(GEM_NAME_BASE, prefix, suffix);

//        Chat.debug(String.format("Creating Gem '%s' with enchantment [%s] @ lvl (%s)",name,enchant.getName(),level));
            return ItemBuilder.of(material).name(name).enchantment(enchant, level).item();
        } else {
            ItemEffect effect = chanceEffect.getEffect();
            String suffix = settings.getSuffixSettings(effect).suffix;
            String name = String.format(GEM_NAME_BASE, "", suffix);
            return ItemBuilder.of(material).name(name).lore(String.format("&a+ &e%s!", effect.name())).item();
        }
    }
}
