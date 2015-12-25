package com.caved_in.adventurecraft.skills.skills;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Mining {

    public static int getExpForBlock(Block block) {
        Material mat = block.getType();

        switch (mat) {
            case STONE:
                //todo check if optional config is enabled.
                return 1;
            case COAL_ORE:
                return 8;
            case IRON_ORE:
            case IRON_BARDING:
                return 8;
            case REDSTONE_ORE:
                return 10;
            case GOLD_ORE:
                return 12;
            case DIAMOND_ORE:
                return 50;
            case CLAY_BRICK:
                return 20;//todo 30
            case EMERALD_ORE:
                return 20;
            case QUARTZ_ORE:
                return 5;
            case LAPIS_ORE:
                return 5;
            default:
                return 0;
        }
    }

}
