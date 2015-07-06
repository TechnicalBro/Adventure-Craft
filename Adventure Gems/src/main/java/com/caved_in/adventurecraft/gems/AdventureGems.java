package com.caved_in.adventurecraft.gems;

import com.caved_in.adventurecraft.gems.debug.DebugGemGenerator;
import com.caved_in.adventurecraft.gems.gemcraft.GemCraftHandler;
import com.caved_in.adventurecraft.gems.item.GemGenerator;
import com.caved_in.adventurecraft.gems.item.GemSettings;
import com.caved_in.adventurecraft.gems.item.GemType;
import com.caved_in.adventurecraft.gems.listener.GemCraftListener;
import com.caved_in.adventurecraft.gems.listener.PlayerInteractListener;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.plugin.BukkitPlugin;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class AdventureGems extends BukkitPlugin {
    private static GemGenerator generator;
    private static AdventureGems instance;
    
    private GemCraftHandler gemHandler;

    public static AdventureGems getInstance() {
        return instance;
    }

    @Override
    public void startup() {
        instance = this;
        
        gemHandler = new GemCraftHandler(this);
        
        registerListeners(
                new PlayerInteractListener(),
                new GemCraftListener()
        );
        
        registerDebugActions(DebugGemGenerator.getInstance());
    
        generator = new GemGenerator();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis";
    }

    @Override
    public void initConfig() {

    }

    public GemCraftHandler getGemHandler() {
        return gemHandler;
    }

    public static class API {
        public static boolean isGem(ItemStack item) {
            Material type = item.getType();
            
            if (type != Material.EMERALD && type != Material.DIAMOND) {
                return false;
            }
            
            if (!Items.nameContains(item,"Gem of")) {
                Chat.debug("Name " + Items.getName(item) + " doesn't contain 'Gem of', apparantly :o");
                return false;
            }
            
            if (!Items.hasEnchantments(item)) {
                return false;
            }
            
            return true;
        }
        
        public static ItemStack createItem(GemType type, GemSettings settings) {
            return generator.createGem(type,settings);
        }
        
        public static Optional<ItemStack> generateItem(GemType type, GemSettings settings) {
            return generator.generateGem(type,settings);
        }
        
        public static boolean isEnhanced(ItemStack item) {
            return Items.nameContains(item, Settings.LEVEL_SEARCH_STRING);
        }
        
        public static int getEnhancementsCount(ItemStack item) {
            if (!isEnhanced(item)) {
                return 0;
            }
            
            String level = StringUtils.substringBetween(Items.getName(item),Settings.LEVEL_SEARCH_STRING,Settings.LEVEL_SEARCH_END_STRING);
            if (!StringUtils.isNumeric(level)) {
                Chat.debug("Found item " + Items.getName(item) + " with level [" + level + "] as their level");
                return 0;
            }
            
            return Integer.parseInt(level);
        }
    }
    
    public static class Settings {
        public static final int MAX_GEM_COMBINES = 2;
        
        public static final int MAX_ITEM_ADDITIONS = 3;
        
        public static final String LEVEL_SEARCH_STRING = "[+";
        public static final String LEVEL_SEARCH_END_STRING = "]";
    }
    
    public static class Messages {
        
    }
}
