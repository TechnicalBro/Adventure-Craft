package com.caved_in.adventurecraft.core.user.upgrades;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface PlayerUpgrade {
    
    public static enum Type {
        MINING("mining"),
        FISHING("fishing"),
        CRAFTING("crafting"),
        SWORD_FIGHTING("swords"),
        ARCHERY("archery"),
        SMITHING("smithing"),
        POTION_MAKING("potions"),
        WOOD_CHOPPING("woodcutting"),
        BUILDING("building");
        
        private static Map<String, Type> types = new HashMap<>();
        
        static {
            for(Type type : EnumSet.allOf(Type.class)) {
                types.put(type.getName(),type);
            }
        }
        
        private String name;
        Type(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public static Type getTypeByName(String name) {
            return types.get(name);
        }
    }
    
    public Type getType();
    
    public int getLevel();
    
    public int getMaxLevel();
}
