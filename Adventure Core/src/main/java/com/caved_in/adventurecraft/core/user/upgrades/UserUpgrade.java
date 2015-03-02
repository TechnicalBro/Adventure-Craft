package com.caved_in.adventurecraft.core.user.upgrades;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.commons.utilities.NumberUtil;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "upgrade")
public class UserUpgrade implements PlayerUpgrade {
    
    private Type type;

    @Attribute(name = "level")
    private int level;
    
    @Attribute(name = "type")
    private String typeName;
    
    //TODO Make skills upgradeable past the global max under conditions; with premium, etc, maybe?
    
    public UserUpgrade(Type type, int level) {
        this.type = type;
        this.typeName = type.getName();
        this.level = level;
    }
    
    public UserUpgrade(@Attribute(name = "type")String type,@Attribute(name = "level")int level) {
        this.typeName = type;
        this.type = Type.getTypeByName(typeName);
        this.level = level;
    }
    
    @Override
    public Type getType() {
        return type;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getMaxLevel() {
        return AdventureCore.Properties.MAX_UPGRADE_LEVEL;
    }
    
    public void addLevel() {
        level++;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public double getChance() {
        return 0.5 * level;
    }
    
    public boolean chanceCheck() {
        return NumberUtil.percentCheck((int)getLevel());
        
    }
}
