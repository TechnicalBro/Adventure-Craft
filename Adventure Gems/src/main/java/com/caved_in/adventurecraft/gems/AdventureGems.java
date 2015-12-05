package com.caved_in.adventurecraft.gems;

import com.caved_in.adventurecraft.gems.debug.DebugGemGenerator;
import com.caved_in.adventurecraft.gems.gemcraft.GemCraftHandler;
import com.caved_in.adventurecraft.gems.item.GemGenerator;
import com.caved_in.adventurecraft.gems.item.GemSettings;
import com.caved_in.adventurecraft.gems.item.GemType;
import com.caved_in.adventurecraft.gems.listener.GemCraftListener;
import com.caved_in.adventurecraft.gems.listener.PlayerInteractListener;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.effects.ItemEffect;
import com.caved_in.adventurecraft.loot.util.ItemHandler;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
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

        /*
        Initialize the API!
         */
        API.init();

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
        //todo javadocs for all of this.
        private static ItemHandler itemEffectHandler;

        protected static void init() {
            itemEffectHandler = AdventureLoot.getInstance().getItemEffectHandler();
        }

        public static ItemHandler getItemEffectHandler() {
            return itemEffectHandler;
        }

        public static boolean isGem(ItemStack item) {
            Material type = item.getType();

            if (type != Material.EMERALD && type != Material.DIAMOND) {
                return false;
            }

            if (!Items.nameContains(item, "Gem of")) {
                Chat.debug("Name " + Items.getName(item) + " doesn't contain 'Gem of', apparantly :o");
                return false;
            }

            //todo check against every effect / see if any effect in lore?
            if (!Items.hasEnchantments(item) && !Items.loreContains(item, "+")) {
                return false;
            }

            return true;
        }

        public static ItemStack createItem(GemType type, GemSettings settings) {
            return generator.createGem(type, settings);
        }

        public static Optional<ItemStack> generateItem(GemType type, GemSettings settings) {
            return generator.generateGem(type, settings);
        }

        public static boolean isEnhanced(ItemStack item) {
            return Items.nameContains(item, Settings.LEVEL_SEARCH_STRING);
        }

        public static int getEnhancementsCount(ItemStack item) {
            if (!isEnhanced(item)) {
                return 0;
            }

            String level = StringUtils.substringBetween(Items.getName(item), Settings.LEVEL_SEARCH_STRING, Settings.LEVEL_SEARCH_END_STRING);
            if (!StringUtils.isNumeric(level)) {
                Chat.debug("Found item " + Items.getName(item) + " with level [" + level + "] as their level");
                return 0;
            }

            return Integer.parseInt(level);
        }

        public static boolean hasItemEffect(ItemStack gem) {
            return isGem(gem) && Items.loreContains(gem, "+");
        }

        public static boolean hasMultipleItemEffects(ItemStack gem) {
            return isGem(gem) && Items.getLoreLinesContaining(gem, "+").size() > 1;
        }

        @Deprecated
        public static ItemEffect getItemEffect(ItemStack gem) {
            if (!isGem(gem)) {
                return null;
            }

            String effectLine = Items.getLoreLineContaining(gem, "+ ");
            effectLine = StringUtil.stripColor(effectLine);
            String effectName = StringUtils.substringBetween(effectLine, "+ ", "!");
            Chat.debug("Parsed '" + effectLine + "' as effect line containing '" + effectName + "' as effect name!");

            if (itemEffectHandler.effectExists(effectName)) {
                return itemEffectHandler.getEffect(effectName);
            }

            return null;
        }

        public static List<ItemEffect> getItemEffects(ItemStack gem) {
            List<ItemEffect> effects = new ArrayList<>();

            if (!isGem(gem)) {
                return null;
            }

            List<String> lines = Items.getLoreLinesContaining(gem, "+ ");

            for (String line : lines) {
                String effectLine = StringUtil.stripColor(line);
                String effectName = StringUtils.substringBetween(effectLine, "+ ", "!");
                Chat.debug("Parsed '" + effectLine + "' as effect line containing '" + effectName + "' as effect name!");
                if (itemEffectHandler.effectExists(effectName)) {
                    effects.add(itemEffectHandler.getEffect(effectName));
                } else {
                    getInstance().debug("Unable to find effect '" + effectName + "'");
                }
            }

            return effects;
        }
    }

    public static class Settings {
        //todo move this to yaml or json serialization. (Xml at a last effort)
        public static final int MAX_GEM_COMBINES = 2;

        public static final int MAX_ITEM_ADDITIONS = 3;

        public static final String LEVEL_SEARCH_STRING = "[+";
        public static final String LEVEL_SEARCH_END_STRING = "]";
    }

    public static class Messages {

    }
}
