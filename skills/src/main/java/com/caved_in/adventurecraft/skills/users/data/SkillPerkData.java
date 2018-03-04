package com.caved_in.adventurecraft.skills.users.data;

import com.caved_in.adventurecraft.skills.skills.SkillPerk;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.HashMap;
import java.util.Map;

@Root(name = "perk")
public class SkillPerkData {

    @Attribute(name = "skill")
    private SkillType skill;

    @ElementMap(name = "perks", entry = "perk", value = "level", keyType = SkillPerk.class, attribute = true)
    private Map<SkillPerk, Integer> perkLevels = new HashMap<>();

    public SkillPerkData(@Attribute(name = "skill") SkillType skill, @ElementMap(name = "perks", entry = "perk", value = "level", keyType = SkillPerk.class, attribute = true) Map<SkillPerk, Integer> perkLevels) {
        this.skill = skill;
        this.perkLevels = perkLevels;
    }

    public SkillPerkData(SkillType type) {
        this.skill = type;
        for (SkillPerk perk : SkillPerk.getPerks(type)) {
            perkLevels.put(perk, 0);
        }
    }

    public SkillType getSkill() {
        return skill;
    }

    public boolean hasPerkUnlocked(SkillPerk perk) {
        return perkLevels.containsKey(perk) && perkLevels.get(perk) > 0;
    }

    public boolean unlockPerk(SkillPerk perk) {
        if (hasPerkUnlocked(perk)) {
            return false;
        }
        perkLevels.put(perk, 1);
        return true;
    }

    public int getLevel(SkillPerk perk) {
        if (!perkLevels.containsKey(perk)) {
            return 0;
        }

        return perkLevels.get(perk);
    }

    public void setLevel(SkillPerk perk, int level) {
        perkLevels.put(perk, level);
    }

    public void addLevel(SkillPerk perk) {
        perkLevels.put(perk, getLevel(perk) + 1);
    }
}
