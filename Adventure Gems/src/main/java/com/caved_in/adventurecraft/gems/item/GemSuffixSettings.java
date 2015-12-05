package com.caved_in.adventurecraft.gems.item;

import com.caved_in.adventurecraft.loot.effects.*;
import com.caved_in.commons.chat.Chat;
import com.mysql.jdbc.StringUtils;
import org.bukkit.enchantments.Enchantment;

import java.util.*;

public class GemSuffixSettings {

    public static GemSuffixSettings[] DEFAULT_SUFFIX_SETTINGS = {
            of(Enchantment.ARROW_DAMAGE, "Eagles").addDescriptor(2, "Fierce", "Soaring"),
            of(Enchantment.ARROW_FIRE, "Burning Rain"),
            of(Enchantment.ARROW_INFINITE, "BRANDON PLZ"),
            of(Enchantment.ARROW_KNOCKBACK, "Arrow Drive"),
            of(Enchantment.DAMAGE_ALL, "Strength"),
            of(Enchantment.DAMAGE_ARTHROPODS, "Spider Stomping"),
            of(Enchantment.DAMAGE_UNDEAD, "Dead Guy Slaying"),
            of(Enchantment.DEPTH_STRIDER, "Depth Stride"),
            of(Enchantment.DIG_SPEED, "Burrowing").addDescriptor(2, "Hastey"),
            of(Enchantment.DURABILITY,"Fortitude"),
            of(Enchantment.FIRE_ASPECT, "Flames").addDescriptor(1, "Ashed", "Low").addDescriptor(2, "Raging", "Blue", "Scorching"),
            of(Enchantment.KNOCKBACK, "Force").addDescriptor(1, "Sheer", "", "Mighty").addDescriptor(2, "Great", "Emmense").addDescriptor(3, "Brutal", "Barbaric", "Unrelenting"),
            of(Enchantment.LOOT_BONUS_BLOCKS, "Rewards"),
            of(Enchantment.LOOT_BONUS_MOBS, "Looting"),
            of(Enchantment.LUCK, "Lucky Lures"),
            of(Enchantment.OXYGEN, "Water Breathing"),
            of(Enchantment.PROTECTION_ENVIRONMENTAL, "Shielding"),
            of(Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection"),
            of(Enchantment.PROTECTION_FALL, "Acrobatic Balance"),
            of(Enchantment.PROTECTION_FIRE, "Fire Walkers"),
            of(Enchantment.PROTECTION_PROJECTILE, "Aerial Protection"),
            of(Enchantment.SILK_TOUCH, "Delicate Hands"),
            of(Enchantment.THORNS, "Thorns"),
            of(Enchantment.WATER_WORKER, "Underwater Masonry"),
            of(Enchantment.LURE,"Fishery"),
            /*
            Suffix Settings for the Item Effects! (Only includes default settings)
             */
            of(BackstabEffect.getInstance(),"Back-Stabbing"),
            of(BleedEffect.getInstance(),"Blood Spatter"),
            of(CriticalStrikeEffect.getInstance(),"Precision Strike"),
            of(FlameStrikeEffect.getInstance(),"Flaming Infernos"),
            of(LifeLeechEffect.getInstance(),"Vampirism"),
            of(PhaseEffect.getInstance(),"Wormholes"),
            of(PoisonEffect.getInstance(),"Toxicities"),
    };

    private static Map<Enchantment, GemSuffixSettings> DEFAULT_SUFFIX_MAP = new HashMap<>();

    private static Map<String, GemSuffixSettings> DEFAULT_EFFECT_SUFFIX_MAP = new HashMap<>();

    static {
        for (GemSuffixSettings settings : DEFAULT_SUFFIX_SETTINGS) {
            if (settings.hasEffect()) {
                DEFAULT_EFFECT_SUFFIX_MAP.put(settings.getEffect().name(),settings);
            } else {
                DEFAULT_SUFFIX_MAP.put(settings.enchant, settings);
            }
        }
    }

    public static GemSuffixSettings getDefaultFor(Enchantment enchantment) {
        return DEFAULT_SUFFIX_MAP.get(enchantment);
    }

    public static GemSuffixSettings getDefaultFor(ItemEffect effect) {
        return DEFAULT_EFFECT_SUFFIX_MAP.get(effect.name());
    }

    public static GemSuffixSettings of(Enchantment enchant, String suffix, int lvlMin, int lvlMax) {
        return new GemSuffixSettings(enchant, lvlMin, lvlMax, suffix);
    }

    public static GemSuffixSettings of(ItemEffect effect, String suffix) {
        return new GemSuffixSettings(effect,suffix);
    }

    /**
     * Create a new instance with any level of the given enchantment having the given suffix.
     *
     * @param enchant enchantment to create suffix data for.
     * @param suffix  suffix to attach to the enchantment
     * @return new instance with any level of the given enchantment having the given suffix.
     */
    public static GemSuffixSettings of(Enchantment enchant, String suffix) {
        return new GemSuffixSettings().suffix(suffix).enchant(enchant).range(1, Integer.MAX_VALUE);
    }

    public Enchantment enchant;

    public int minimumLevel;
    public int maximumLevel;

    public String suffix = "";

    public LevelDescriptor suffixDescriptions = new LevelDescriptor();

    private ItemEffect effect = null;

    public GemSuffixSettings() {

    }

    public GemSuffixSettings(Enchantment enchant, int min, int max, String suffix) {
        this.enchant = enchant;
        this.minimumLevel = min;
        this.maximumLevel = max;
        this.suffix = suffix;
    }

    public GemSuffixSettings(ItemEffect effect, String suffix) {
        this.effect = effect;
        this.suffix = suffix;
    }

    public GemSuffixSettings effect(ItemEffect effect) {
        this.effect = effect;
        return this;
    }

    public GemSuffixSettings enchant(Enchantment enchant) {
        this.enchant = enchant;
        return this;
    }

    public GemSuffixSettings suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public GemSuffixSettings addDescriptor(int lvl, String... text) {
        suffixDescriptions.level(lvl, text);
        return this;
    }

    public GemSuffixSettings range(int min, int max) {
        this.minimumLevel = min;
        this.maximumLevel = max;
        return this;
    }

    public boolean inRange(Enchantment enchant, int lvl) {
        boolean inRange = this.enchant.equals(enchant) && lvl >= minimumLevel && lvl <= maximumLevel;
//        if (!inRange) {
//            try {
////                Chat.debug(String.format("Enchantment %s @ [%s] not in range {%s : %s}", enchant.getName(), lvl, minimumLevel, maximumLevel));
//            } catch (NullPointerException e) {
//
//            }
//        }
        return inRange;
    }

    public String getSuffix(int lvl) {
        String descriptor = suffixDescriptions.forLevel(lvl);
        if (StringUtils.isNullOrEmpty(descriptor)) {
            return suffix;
        }

        return String.format("%s %s", descriptor, suffix);
    }

    public boolean hasEffect() {
        return effect != null;
    }

    public ItemEffect getEffect() {
        return effect;
    }
}
