package com.caved_in.adventurecraft.skills.util;

import com.caved_in.adventurecraft.skills.AdventureSkills;

import java.util.HashMap;
import java.util.Map;

public class ExpTables {
    private static AdventureSkills core = AdventureSkills.getInstance();
    private static Map<Integer, Integer> expForLevels = new HashMap<>();

    public static void init() {
        for (int i = 1; i <= 500; i++) {
            int exp = (int) (((Math.pow(i, 2.25D) + i * i * 1.6D) * 4.6D * AdventureSkills.Config.EXP_REQUIREMENT_FACTOR + 1.0D) * 2.5); //Digit on the end is a multiplier!
            expForLevels.put(i, exp);
        }
    }

    public static int xpForLevel(int lvl) {
        if (lvl > 500) {
            return (int) ((170000.0D + Math.pow(lvl - 80, 3.6D)) * 2.5);
        }
        return expForLevels.get(lvl);
    }

    public static int getLevelAt(int exp) {
        int lvl = 1;
        for (int required = -1; exp > required; required = xpForLevel(lvl)) {
            lvl++;
        }
        return lvl - 1;
    }
}
