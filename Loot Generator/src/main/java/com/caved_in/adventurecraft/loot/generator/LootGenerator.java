package com.caved_in.adventurecraft.loot.generator;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.effects.ItemEffect;
import com.caved_in.adventurecraft.loot.event.LootGenerateEvent;
import com.caved_in.adventurecraft.loot.generator.data.*;
import com.caved_in.adventurecraft.loot.generator.settings.ItemEnchantmentSettings;
import com.caved_in.adventurecraft.loot.generator.settings.ItemLoreSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.exceptions.ItemCreationException;
import com.caved_in.commons.game.item.WeaponProperties;
import com.caved_in.commons.item.Attributes;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.utilities.NumberUtil;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.javatuples.Pair;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class LootGenerator {

    private static DecimalFormat damageFormat = new DecimalFormat("#.##");

    private AdventureLoot plugin;

    public LootGenerator(AdventureLoot plugin) {
        this.plugin = plugin;

        damageFormat.setRoundingMode(RoundingMode.CEILING);
    }

    public ItemStack createItem(LootSettings settings) {
        Optional<ItemStack> optItem = generateItem(settings);
        while (!optItem.isPresent()) {
            optItem = generateItem(settings);
        }
        return optItem.get();
    }

    public ItemStack createItem(LootTable table) {
        Optional<ItemStack> item = generateItem(table);

        while (!item.isPresent()) {
            item = generateItem(table);
        }
        return item.get();
    }


    public Optional<ItemStack> generateItem(LootTable table) {
        if (table.hasItems()) {
            ChancedItemStack chanceItem = table.getChancedItem();
            if (NumberUtil.percentCheck(chanceItem.chance())) {
                return Optional.of(chanceItem.item());
            }
        }

        LootSettings settings = table.getRandom();

        if (settings == null) {
            return Optional.empty();
        }

        return generateItem(settings);
    }

    /**
     * Based on the LootSettings create a random itemTable!
     *
     * @param settings settings used to create the itemTable.
     */
    public Optional<ItemStack> generateItem(LootSettings settings) {
        double rarity = 0;

        //Whether or not the itemTable has a random name
        boolean hasRandomName = settings.hasRandomName();
        /*
        Data relating to the itemTable type
         */
        MaterialTable data = settings.itemTable();

        /*
        The NameSettings for the basenames, prefixes, and suffixes.

        Used in the creation of the items for generating the name of the itemTable!
         */
        NameTable baseNames = settings.baseNames();
        NameTable prefixes = settings.prefixes();
        NameTable suffixes = settings.suffixes();

		/*
        Retrieve the lore settings to be used when generating items.
		 */
        ItemLoreSettings lore = settings.lore();

		/*
        Retrieve the weapon properties used to control whether or not
		it's breakable, droppable, etc!
		 */
        WeaponProperties weaponProperties = settings.weaponProperties();

        /*
        Retrieve the enchantment settings, used to apply enchantments (with a chance)
		to the generated items.
		 */
        ItemEnchantmentSettings enchants = settings.enchantments();

		/*
        Get a random material to create the item with.
		 */
        ChancedItemData itemData = data.getRandomData();

        boolean hasMinMaxDamageRanges = itemData.hasMinMaxDamageRanges();

        boolean hasDamageRange = itemData.hasDamageRange();

		/*
        Retrieve the optional material data; If It's present then we'll create the item
		though if not, we'll return an empty optional.
		 */
        Optional<MaterialData> material = itemData.getChancedMaterialData();

		/*
        Retrieve a random attribute to apply to this item.
		 */
        Optional<RandomizedAttribute> attribute = itemData.getAttribute();

        ItemBuilder item = null;

        if (material.isPresent() && material.get().getItemType() != Material.AIR) {
            item = ItemBuilder.of(material.get());
        } else {
            return Optional.empty();
        }

        String generatedName = null;

        /*
        Get the name to assign to the itemTable; If a random name is desired then poll from names available
        from combining the suffixes, prefixes, and such!
         */
        if (hasRandomName) {

            StringBuilder nameBuilder = new StringBuilder("&r&f");

            ChancedName baseName = null;
            ChancedName prefixName = null;
            ChancedName suffixName = null;

            String namePrefix = null;
            String nameBase = null;
            String nameSuffix = null;

            NameSlot prefixPreventSlot = null;
            NameSlot suffixPreventSlot = null;
            NameSlot basePreventSlot = null;

            while (nameBase == null) {
                ChancedName randomBaseName = baseNames.getRandomName();

                if (!NumberUtil.percentCheck(randomBaseName.getChance())) {
                    continue;
                }

                baseName = randomBaseName;
                nameBase = randomBaseName.getName();
                basePreventSlot = randomBaseName.getPreventSlot();
            }

            for (ChancedName prefix : prefixes.getChancedNames()) {
                if (!NumberUtil.percentCheck(prefix.getChance())) {
                    continue;
                }

                namePrefix = prefix.getName();
                prefixName = prefix;
                prefixPreventSlot = prefix.getPreventSlot();
                break;
            }

            for (ChancedName suffix : suffixes.getChancedNames()) {
                if (!NumberUtil.percentCheck(suffix.getChance())) {
                    continue;
                }

                nameSuffix = suffix.getName();
                suffixPreventSlot = suffix.getPreventSlot();
                suffixName = suffix;
            }

            if (prefixName != null) {
                if ((suffixName != null) && (basePreventSlot == NameSlot.PREFIX || suffixPreventSlot == NameSlot.PREFIX)) {
                    int baseChance = baseName.getChance();
                    int suffixChance = suffixName.getChance();
                    int prefixChance = prefixName.getChance();


                    /*
                    Take precedence of what's to be generated

                    If the prefix chance of spawning is lower than the base chance, and the base takes higher
                    precedence than the suffix, or if the prefix
                    takes higher order than the suffix, and the suffix
                    takes higher order than the base then we'll add it in, if either are true

                    Though if the prefix isn't at the bottom of the list, then shits not gonna spawn!
                     */
                    if ((prefixChance < baseChance && baseChance < suffixChance) || (prefixChance < suffixChance && suffixChance < baseChance)) {
                        nameBuilder.append(prefixName);
                        rarity += 1;
                    }
                } else {
                    nameBuilder.append(namePrefix).append(" ");
                }
            }

            nameBuilder.append(nameBase).append(" ");

            if (suffixName != null) {
                if ((prefixName != null) && (prefixPreventSlot == NameSlot.SUFFIX || basePreventSlot == NameSlot.SUFFIX)) {
                    int baseChance = baseName.getChance();
                    int suffixChance = suffixName.getChance();
                    int prefixChance = prefixName.getChance();

                    if ((suffixChance < baseChance && baseChance < prefixChance) || (suffixChance < prefixChance && prefixChance < baseChance)) {
                        nameBuilder.append(nameSuffix);
                        rarity += 1;
                    }
                } else {
                    nameBuilder.append(nameSuffix);
                }
            }

            generatedName = nameBuilder.toString();
        } else {
            generatedName = settings.getLootName();

            if (StringUtils.isNullOrEmpty(generatedName)) {
                generatedName = Items.getFormattedMaterialName(itemData.getMaterial());
            }
        }

        if (generatedName != null) {
            item.name(generatedName);
        } else {

            item.name(Items.getFormattedMaterialName(itemData.getMaterial()));
        }

        /*
        If the loot settings call for enchantments to be put on the itemTable, then do so!
         */
        if (enchants.hasEnchantments()) {
            List<ChancedEnchantment> enchantmentList = enchants.getEnchantments();

            //todo Implement a limit to how many enchantments can be applied

            /*
            Loop through all the enchantments, and if it passes the selection check
            then apply it to the current itemTable!
             */
            for (ChancedEnchantment enchantment : enchantmentList) {

                if (!NumberUtil.percentCheck(enchantment.getChance())) {
                    continue;
                }

                int minLvl = enchantment.getMinLevel();
                int maxLvl = enchantment.getMaxLevel();

                int generatedLevel = 0;

                if (minLvl == maxLvl) {
                    generatedLevel = minLvl;
                } else {
                    generatedLevel = NumberUtil.getRandomInRange(minLvl, maxLvl);
                }

                item.enchantment(enchantment.getEnchantment(), generatedLevel, enchantment.hasGlow());
                rarity += 1;
            }
        }

        /*
        Apply any attributes that might be attached to the loot
         */
        if (attribute.isPresent()) {
            RandomizedAttribute randomAttr = attribute.get();

            Optional<Attributes.Attribute> itemAttr = randomAttr.getRandomizedAttribute();

            if (itemAttr.isPresent()) {
                rarity += 1;
                item.addAttribute(itemAttr.get());
            }
        }

		/*
        If theres a min-max damage range, or a basic damage range and the lore
		doesn't have its damaged displayed or has no lore available, then we're going to
		assure that the damage is displayed on the object.

		Also, as a measure, enable the display of item rarity at this step.
		 */
        if ((hasDamageRange || hasMinMaxDamageRanges) && (!lore.hasDamageDisplayed() || !lore.hasLore())) {
            lore.displayDamage(true);
            lore.displayRarity(true);
        }

		/*
        Below we calculate the damage ranges for the item! (If any)
		 */

        double damageMin = 0;
        double damageMax = 0;
        boolean applyDamages = true;

		/*
		If there's a min-max damage ranges for minMin,minMax,maxMin,maxMax, then we need to
		generate a number to the item within each range for these damage values!
		 */
        if (hasMinMaxDamageRanges) {
            Pair<Double, Double> damageMinRange = itemData.getMinDamageRange();
            Pair<Double, Double> damageMaxRange = itemData.getMaxDamageRange();

            Double minMin = damageMinRange.getValue0();
            Double minMax = damageMinRange.getValue1();

            Double maxMin = damageMaxRange.getValue0();
            Double maxMax = damageMaxRange.getValue1();

			/*
			Assign the damage minimum on a value between the min-floor and cieling.
			 */
            damageMin = NumberUtil.getRandomInRange(minMin, minMax);
            damageMin = NumberUtil.round(damageMin, 2);

			/*
			Assign the damage maximum on a value between the max floor and cieling.
			 */
            damageMax = NumberUtil.getRandomInRange(maxMin, maxMax);
            damageMax = NumberUtil.round(damageMax, 2);
        } else if (hasDamageRange) {
			/*
			Otherwise if we only have a singular damage range, just assign the min and max to the item!
			 */
            Pair<Double, Double> damageRange = itemData.getDamageRange();
            damageMin = damageRange.getValue0();
            damageMax = damageRange.getValue1();

            damageMin = NumberUtil.round(damageMin, 2);
            damageMax = NumberUtil.round(damageMax, 2);
        } else if (settings.weaponProperties().hasDamageRange()) {
            damageMin = settings.weaponProperties().getMinDamage();
            damageMax = settings.weaponProperties().getMaxDamage();

            damageMin = NumberUtil.round(damageMin, 2);
            damageMax = NumberUtil.round(damageMax, 2);
        } else {
            applyDamages = false;
        }


        /*
        All the lore to apply to the item!
        Use the lore applied by the general settings in the loot table as a base
        for the item.
         */
        List<String> loreLines = Lists.newArrayList(lore.getLore());

        /*
        If we're applying the damages to the item, then we must add the damage
        values to the lore of the item.
         */
        if (applyDamages) {
            loreLines.add("");
            loreLines.add(String.format(lore.getDamageFormat(), damageMin, damageMax));
        }

        Optional<ItemEffect> generatedEffect = Optional.empty();

        if (settings.hasEffectSettings()) {
            generatedEffect = settings.effectSettings().getChancedEffect();

            if (generatedEffect.isPresent()) {
                rarity += 1;
            }
        }

        /*
        If we're to assign a rarity on the lores item, and there's a rarity
        value already calculated; Then we're going to append the rarity of the item
        to it's lore; For an awesome display!
         */
        if (lore.hasRarityDisplayed() && rarity > 0) {
            loreLines.add("");
            StringBuilder rarityDisplay = new StringBuilder();
            for (int i = 0; i <= rarity; i++) {
                rarityDisplay.append("*");
            }

            loreLines.add(String.format(lore.getRarityFormat(), rarityDisplay));
        }

        //todo implement lore for undroppable items
        //todo implement lore for all weapon property values

        /*
        Check if there's any lines of lore to be applied to the item, and if so:
        Do it! JUST, DO IT!
         */
        if (loreLines.size() > 0) {
            item.lore(loreLines);
        }

        ItemStack generatedLoot = null;
        try {
            generatedLoot = item.item();
        } catch (RuntimeException e) {
        }

        if (generatedLoot == null) {
            return Optional.empty();
        }

        if (generatedEffect.isPresent()) {
            ItemEffect e = generatedEffect.get();

            e.apply(generatedLoot);
            Chat.debug("Applied " + e.name() + " to " + Items.getName(generatedLoot));
        }

        LootGenerateEvent event = new LootGenerateEvent(settings, generatedLoot);
        Plugins.callEvent(event);

        return Optional.of(generatedLoot);
    }
}
