package com.caved_in.adventurecraft.gems.item;


import com.caved_in.commons.chat.Chat;
import com.google.common.collect.Lists;
import org.bukkit.enchantments.Enchantment;

import java.util.*;
import java.util.stream.Collectors;

import com.caved_in.commons.utilities.ListUtils;

public class GemSettings {
    
    private Map<Enchantment, List<ChancedEnchantment>> enchants = new HashMap<>();

    private List<GemPrefixSettings> prefixSettings = new ArrayList<>();
    
    private List<GemSuffixSettings> suffixSettings = new ArrayList<>();

    private boolean usingDefaultPrefixes = false;
    
    private boolean usingDefaultSuffixes = false;
    
    public GemSettings() {

    }
    
    public GemSettings addEnchantment(ChancedEnchantment enchantment) {
        Enchantment enchant = enchantment.getEnchantment();
        if (!enchants.containsKey(enchant)) {
            enchants.put(enchant, Lists.newArrayList(enchantment));
        } else {
            enchants.get(enchant).add(enchantment);
        }
        return this;
    }

    public GemSettings addPrefixData(GemPrefixSettings settings) {
        if (usingDefaultPrefixes) {
            prefixSettings.clear();
            usingDefaultPrefixes = false;
        }
        
        prefixSettings.add(settings);
        return this;
    }
    
    public GemSettings addSuffixData(GemSuffixSettings settings) {
        if (usingDefaultSuffixes) {
            suffixSettings.clear();
            usingDefaultSuffixes = false;
        }
        
        suffixSettings.add(settings);
        return this;
    }
    
    public boolean hasSuffixForEnchantment(Enchantment enchantment) {
        for(GemSuffixSettings suffixData : suffixSettings) {
            if (enchantment.equals(suffixData.enchant)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasSuffixForEnchantment(Enchantment enchantment, int level) {
        for(GemSuffixSettings suffixData : suffixSettings) {
            if (suffixData.inRange(enchantment,level)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean hasPrefixForLevel(int level) {
        return getPrefixForLevel(level) != null;
    }
    
    public String getPrefixForLevel(int level) {
        for(GemPrefixSettings prefixInfo : prefixSettings) {
            if (!prefixInfo.inRange(level)) {
                continue;
            }
            
            return prefixInfo.prefix;
        }
        
        return null;
    }
    
    public boolean hasEnchantment(Enchantment enchantment) {
        return enchants.containsKey(enchantment);
    }
    
    public boolean hasEnchantment(Enchantment enchantment, int lvl) {
        if (!hasEnchantment(enchantment)) {
            return false;
        }
        
        for(ChancedEnchantment chanceEnchant : enchants.get(enchantment)) {
            if (chanceEnchant.getLevel() == lvl) {
                return true;
            }
        }
        
        return false;
    }

    public ChancedEnchantment getRandomEnchantment() {
        List<ChancedEnchantment> enchantments = new ArrayList<>();
        enchants.values().stream().forEach(enchantments::addAll);
        return ListUtils.getRandom(enchantments);
    }

    public GemSettings defaultPrefixData() {
        prefixSettings.clear();
        Collections.addAll(prefixSettings,GemPrefixSettings.DEFAULT_SETTINGS);
        return this;
    }

    public GemSettings clearSuffixFor(Enchantment enchant) {
        List<GemSuffixSettings> settings = suffixSettings.stream().filter(e -> !e.enchant.equals(enchant)).collect(Collectors.toList());
        this.suffixSettings = settings;
        return this;
    }

    public GemSettings clearPrefixData() {
        prefixSettings.clear();
        return this;
    }

    public String getSuffixFor(Enchantment enchantment, int level) {
        GemSuffixSettings settings = getSuffixSettings(enchantment,level);
        if (settings == null) {
//            Chat.debug("SETTINGS FOR SUFFIX " + enchantment.getName() + " @ lvl " + level + " is null!");
            settings = GemSuffixSettings.getDefaultFor(enchantment);
        }
        
        if (settings == null) {
            Chat.messageOps("DEFAULT SETTINGS FOR SUFFIX " + enchantment.getName() + " @ lvl " + level + " is null! Get brandon to fix pls");
            return "Forgotten Features";
        }
        
        return settings.getSuffix(level);
    }

    public GemSuffixSettings getSuffixSettings(Enchantment enchant, int level) {
        GemSuffixSettings settings = null;
        for(GemSuffixSettings ss : suffixSettings) {
            if (ss.inRange(enchant, level)) {
                settings = ss;
                break;
            }
        }
        return settings;
    }

    public GemSettings defaultSuffixData() {
        usingDefaultSuffixes = true;
        suffixSettings.clear();
        Collections.addAll(suffixSettings,GemSuffixSettings.DEFAULT_SUFFIX_SETTINGS);
        return this;
    }

    public GemSettings defaultEnchantsFor(Enchantment... enchantments) {
        for(Enchantment enchant : enchantments) {
            List<ChancedEnchantment> chancedEnchants = ChancedEnchantment.defaultFor(enchant);
            chancedEnchants.forEach(this::addEnchantment);
        }
        return this;
    }
}
