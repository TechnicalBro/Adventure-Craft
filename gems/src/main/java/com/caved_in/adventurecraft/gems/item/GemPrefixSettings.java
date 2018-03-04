package com.caved_in.adventurecraft.gems.item;

public class GemPrefixSettings {
    public static final GemPrefixSettings[] DEFAULT_SETTINGS = {
            of("Crude",1,1),
            of("Cracked",2,2),
            of("Refined",3,3),
            of("Polished",4,4),
            of("Untainted",5,5),
            of("Enriched",6,9),
            of("Magnificent",10,10),
            of("Pristine",11,11),
            of("Unsullied",12,12),
            of("Perfected",13,Integer.MAX_VALUE)
    };
    
    public int minimumLevel;
    public int maximumLevel;

    public String prefix;
    
    public static GemPrefixSettings of(String prefix, int lvlMin, int lvlMax) {
        return new GemPrefixSettings(lvlMin,lvlMax,prefix);
    }

    public GemPrefixSettings() {

    }

    public GemPrefixSettings(int min, int max, String prefix) {
        this.minimumLevel = min;
        this.maximumLevel = max;
        this.prefix = prefix;
    }

    public GemPrefixSettings prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public GemPrefixSettings range(int min, int max) {
        this.minimumLevel = min;
        this.maximumLevel = max;
        return this;
    }

    public boolean inRange(int lvl) {
        return lvl >= minimumLevel && lvl <= maximumLevel;
    }
}