package com.devsteady.gaeacraft.listener;

import com.devsteady.gaeacraft.AdventureCore;
import com.devsteady.gaeacraft.loot.CreatureLootTable;
import com.caved_in.adventurecraft.gems.AdventureGems;
import com.caved_in.adventurecraft.gems.item.GemSettings;
import com.caved_in.adventurecraft.gems.item.GemType;
import com.caved_in.adventurecraft.loot.AdventureLoot;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.MobType;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.utilities.ListUtils;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MobSlayListener implements Listener {

    private static MobSlayListener instance = null;

    public static MobSlayListener getInstance() {
        if (instance == null) {
            instance = new MobSlayListener();
        }
        return instance;
    }

    private Map<EntityType, Integer> mobLootChances = new HashMap<EntityType, Integer>() {{
        put(MobType.BLAZE.getEntityType(), 5);
        put(MobType.SPIDER.getEntityType(), 5);
        put(MobType.SKELETON.getEntityType(), 5);
        put(EntityType.CREEPER, 10);
        put(EntityType.CAVE_SPIDER, 5);
        put(EntityType.WITCH, 20);
        put(EntityType.ENDERMAN, 10);
        put(EntityType.WITHER, 100);
        put(EntityType.GHAST, 15);
        put(EntityType.ZOMBIE, 10);
        put(EntityType.PIG_ZOMBIE, 10);
        put(EntityType.SLIME, 5);
        put(EntityType.MAGMA_CUBE, 5);
        put(EntityType.SILVERFISH, 4);
        put(EntityType.GIANT, 100);
        put(EntityType.VILLAGER, 15);
        //todo remove after testing
        put(EntityType.COW, 10);
        put(EntityType.CHICKEN, 2);
    }};

    private List<ItemStack> generatedGems = new ArrayList<>();

    private GemSettings settings = new GemSettings().defaultPrefixData().defaultSuffixData().defaultEnchantsFor(Enchantment.values());

    protected MobSlayListener() {
        regenerateGems();
    }

    public void regenerateGems() {
        generatedGems.clear();
        Chat.debug("Regenerated the gems! 50 new ones!");

        for (int i = 0; i < 50; i++) {
            generatedGems.add(AdventureGems.API.createItem(GemType.EMERALD, settings));
        }
    }

    @EventHandler
    public void onPlayerKillMobEvent(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (!mobLootChances.containsKey(entity.getType())) {
            return;
        }

        double dropChance = mobLootChances.get(entity.getType()) * AdventureCore.Properties.DROP_MULTIPLIER;

        Chat.debug("There's a " + dropChance + "% drop chance for " + entity.getType().name() + "!");

        if (!NumberUtil.percentCheck(dropChance)) {
            return;
        }

        ItemStack drop = null;
        Optional<ItemStack> item = null;

        switch (entity.getType()) {
            case SKELETON:
                item = AdventureLoot.API.generateItem(CreatureLootTable.SKELETON_SPECIFIC_LOOT);
                break;
            case PIG_ZOMBIE:
            case GHAST:
            case WITHER:
            case BLAZE:
            case MAGMA_CUBE:
            case SLIME:
                item = AdventureLoot.API.generateItem(CreatureLootTable.NETHER_SPECIFIC_LOOT);
                break;
            case ENDERMAN:
                item = AdventureLoot.API.generateItem(CreatureLootTable.ENDER_SPECIFIC_LOOT);
                break;
            case CREEPER:
                item = AdventureLoot.API.generateItem(CreatureLootTable.CREEPER_SPECIFIC_LOOT);
            default:
                break;
        }

        if (item == null) {
            item = AdventureLoot.API.generateItem(CreatureLootTable.GLOBAL_LOOT_TABLE);
        }

        if (!item.isPresent()) {
            if (NumberUtil.percentCheck(dropChance)) {
                drop = ListUtils.getRandom(generatedGems);
            } else {
                drop = AdventureLoot.API.createItem(CreatureLootTable.GLOBAL_LOOT_TABLE);
            }
        } else {
            drop = item.get();
        }

        if (drop == null) {
            if (NumberUtil.percentCheck(dropChance)) {
                drop = ListUtils.getRandom(generatedGems);
            } else {
                drop = AdventureLoot.API.createItem(CreatureLootTable.GLOBAL_LOOT_TABLE);
            }
        }

        if (drop == null) {
            return;
        }
        //todo optionally add this to the killers inventory
        Item droppedItem = Worlds.dropItem(entity, drop, true);

        if (droppedItem == null) {
            return;
        }

        ParticleEffects.sendToLocation(ParticleEffects.FLAME, entity.getLocation(), NumberUtil.getRandomInRange(15, 25));
        ItemHaloListener.makeHalo(droppedItem);
        
        if (entity.getKiller() != null) {
            Sounds.playSound(entity.getKiller(), Sound.ANVIL_USE);
            Chat.message(entity.getKiller(), "&6&lYou've received a special drop!");
        }
    }
}
