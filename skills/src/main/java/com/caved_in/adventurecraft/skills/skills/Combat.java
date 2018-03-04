package com.caved_in.adventurecraft.skills.skills;

import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class Combat {
    public static double ELITE_MOB_BONUS_MULTIPLIER = 0.45;

    public static double BOSS_MOB_BONUS_MULTIPLIER = 1.1;

    public static int BONUS_MOB_EXP_PER_LEVEL = 4;

    private static final Map<EntityType, Integer> MOB_SLAY_EXP_VALUES = new HashMap<EntityType, Integer>() {{
        put(EntityType.SPIDER, 10);
        put(EntityType.CREEPER, 20);
        put(EntityType.ENDERMAN, 10);
        put(EntityType.BLAZE, 15);
        put(EntityType.PIG_ZOMBIE, 10);
        put(EntityType.ENDER_DRAGON, 300);
        put(EntityType.WITHER, 250);
        put(EntityType.GHAST, 30);
        put(EntityType.GIANT, 50);
        put(EntityType.MAGMA_CUBE, 5);
        put(EntityType.ZOMBIE, 10);
        put(EntityType.SILVERFISH, 5);
        put(EntityType.SLIME, 5);
        put(EntityType.SKELETON, 15);
        put(EntityType.CAVE_SPIDER, 10);
    }};

    public static int getExpForMob(EntityType type) {
        return MOB_SLAY_EXP_VALUES.containsKey(type) ? MOB_SLAY_EXP_VALUES.get(type) : 0;
    }
}
