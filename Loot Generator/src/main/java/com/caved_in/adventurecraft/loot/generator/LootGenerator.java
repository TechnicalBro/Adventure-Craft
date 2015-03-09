package com.caved_in.adventurecraft.loot.generator;

import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.adventurecraft.loot.event.LootGenerateEvent;
import com.caved_in.adventurecraft.loot.generator.data.*;
import com.caved_in.adventurecraft.loot.generator.settings.ItemEnchantmentSettings;
import com.caved_in.adventurecraft.loot.generator.settings.ItemLoreSettings;
import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.commons.game.item.WeaponProperties;
import com.caved_in.commons.item.Attributes;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.utilities.Str;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.Optional;

public class LootGenerator {

	private AdventureLoot plugin;

	public LootGenerator(AdventureLoot plugin) {
		this.plugin = plugin;
	}

	public ItemStack createItem(LootTable table) {
		return createItem(table.getRandom());
	}

	/**
	 * Based on the LootSettings create a random itemTable!
	 *
	 * @param settings settings used to create the itemTable.
	 */
	public ItemStack createItem(LootSettings settings) {
		double rarity = 0;

		//Whether or not the itemTable has a random name
		boolean hasRandomName = settings.hasRandomName();

		plugin.debug("Item hasRandomName: " + String.valueOf(hasRandomName));

        /*
        Data relating to the itemTable type
         */
		MaterialTable data = settings.itemTable();

		plugin.debug("Item Type Settings: " + data.toString());

        /*
        The NameSettings for the basenames, prefixes, and suffixes.

        Used in the creation of the items for generating the name of the itemTable!
         */
		NameTable baseNames = settings.baseNames();
		NameTable prefixes = settings.prefixes();
		NameTable suffixes = settings.suffixes();

		plugin.debug("Base Names: " + baseNames.toString(), "Prefixes : " + prefixes.toString(), "Suffixes: " + suffixes.toString());

		ItemLoreSettings lore = settings.lore();

		plugin.debug("Lore: " + lore.toString());

		WeaponProperties weaponProperties = settings.weaponProperties();

		plugin.debug("Weapon Properties: " + weaponProperties.toString());

		ItemEnchantmentSettings enchants = settings.enchantments();

		plugin.debug(enchants.toString());

		ChancedItemData itemData = data.getRandomData();

		Optional<MaterialData> material = itemData.getChancedMaterialData();

		Optional<RandomizedAttribute> attribute = itemData.getAttribute();

		ItemBuilder item = ItemBuilder.of(material.isPresent() ? material.get() : data.getDefaultMaterial());

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

					plugin.debug("==== PREFIX CHANCES ===", "Base Chance: " + baseChance, "Suffix Chance: " + suffixChance, "Prefix Chance: " + prefixChance);

                    /*
                    Take precidence of what's to be generated

                    If the prefix chance of spawning is lower than the base chance, and the base takes higher
                    precidence than the suffix, or if the prefix
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

					plugin.debug("==== SUFFIX CHANCES ===", "Base Chance: " + baseChance, "Suffix Chance: " + suffixChance, "Prefix Chance: " + prefixChance);

					if ((suffixChance < baseChance && baseChance < prefixChance) || (suffixChance < prefixChance && prefixChance < baseChance)) {
						nameBuilder.append(nameSuffix);
						rarity += 1;
					}
				} else {
					nameBuilder.append(nameSuffix);
				}
			}


			generatedName = nameBuilder.toString();

			plugin.debug("GENERATED NAME == " + generatedName);

		} else {
			generatedName = settings.getLootName();

			if (StringUtils.isNullOrEmpty(generatedName)) {
				generatedName = Items.getFormattedMaterialName(material.isPresent() ? material.get().getItemType() : data.getDefaultMaterial().getItemType());
			}
		}

		if (generatedName != null) {
			item.name(generatedName);
		} else {
			item.name(Items.getFormattedMaterialName(material.isPresent() ? material.get().getItemType() : data.getDefaultMaterial().getItemType()));
		}

		plugin.debug("Assigned Item Name: " + generatedName);

		//TODO Implement a custom listener for itemTable damages and apply weaponProperties to the itemTable itself.

        /*
        If the loot settings call for enchantments to be put on the itemTable, then do so!
         */
		if (enchants.hasEnchantments()) {
			List<ChancedEnchantment> enchantmentList = enchants.getEnchantments();

			//todo Implement a limit to how many enchantments can be applied

			//todo implement a min and max level for each enchantment.

            /*
            Loop through all the enchantments, and if it passes the selection check
            then apply it to the current itemTable!
             */
			for (ChancedEnchantment enchantment : enchantmentList) {

				if (!NumberUtil.percentCheck(enchantment.getChance())) {
					continue;
				}

				item.enchantment(enchantment.getEnchantment(), enchantment.getLevel(), enchantment.hasGlow());
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
				plugin.debug("ADDED ATTRIBUTE TO ITEM!!" + itemAttr.get().toString());
			}
		}


        /*
        Assign the lore to the itemTable!
         */

		if (lore.hasLore()) {
			List<String> loreLines = Lists.newArrayList(lore.getLore());

            /*
            If the lore demands the itemTable damage is displayed; We'll show that on the last line of the itemTable.
             */
			if (lore.hasDamageDisplayed()) {
				loreLines.add("");
				loreLines.add(String.format(lore.getDamageFormat(), weaponProperties.getMinDamage(), weaponProperties.getMaxDamage()));
			}

			if (lore.hasRarityDisplayed() && rarity > 0) {
				loreLines.add("");
				StringBuilder rarityDisplay = new StringBuilder();
				for (int i = 0; i < rarity; i++) {
					rarityDisplay.append("*");
				}

				loreLines.add(String.format(lore.getRarityFormat(), rarityDisplay));
			}
			item.lore(loreLines);
		}

		ItemStack generatedLoot = item.item();
		LootGenerateEvent event = new LootGenerateEvent(settings, generatedLoot);
		Plugins.callEvent(event);

		return generatedLoot;
	}
}
