package com.caved_in.adventurecraft.gems.item;

import com.caved_in.commons.utilities.ListUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;

public class LevelDescriptor {
    
    //todo implement chances for each descriptor!
    private Map<Integer, List<String>> levelDescriptionMap = new HashMap<>();
    
    public LevelDescriptor() {
    }

    
    public LevelDescriptor level(int level, String... text) {
        if (!levelDescriptionMap.containsKey(level)) {
            levelDescriptionMap.put(level, Lists.newArrayList(text));
            return this;
        }
        
        List<String> descriptors = levelDescriptionMap.get(level);
        Collections.addAll(descriptors,text);
        descriptors = Lists.newArrayList(Sets.newHashSet(text));
        levelDescriptionMap.put(level,descriptors);
        return this;
    }

    public String forLevel(int lvl) {
        if (!levelDescriptionMap.containsKey(lvl)) {
            return null;
        }
        
        return ListUtils.getRandom(levelDescriptionMap.get(lvl));
    }
    
    public List<String> allForLevel(int lvl) {
        return levelDescriptionMap.get(lvl);
    }
}
