package com.caved_in.adventurecraft.skills.skills;

import com.caved_in.commons.utilities.Scaler;
import com.google.common.collect.Sets;

import java.util.Set;

public enum SkillPerk {
    MINING_AUTO_SMELT("Auto Smelt", SkillType.MINING, 10, 50, new Scaler(1, 1, 50, 70)),
    MINING_DOUBLE_DROP("Double Drop", SkillType.MINING, 45, 5, new Scaler(1, 15, 5, 65)),
    MINING_BLAST_PICK("Blast Pick", SkillType.MINING, 99, 1, new Scaler(1, 100, 1, 100), MINING_AUTO_SMELT, 50);

    private String name;
    private SkillType skill;
    private int levelRequired;
    private int maxSkillLevel;
    private Scaler scaler;
    private SkillPerk requiredPerk = null;
    private int parentPerkRequiredLevel = -1;

    SkillPerk(String name, SkillType skill, int levelRequired, int maxSkillLevel, Scaler skillScaler) {
        this.name = name;
        this.skill = skill;
        this.levelRequired = levelRequired;
        this.maxSkillLevel = maxSkillLevel;
        this.scaler = skillScaler;
    }

    SkillPerk(String name, SkillType skill, int levelRequired, int maxSkillLevel, Scaler scaler, SkillPerk parent, int parentLevelRequired) {
        this(name, skill, levelRequired, maxSkillLevel, scaler);
        this.requiredPerk = parent;
        this.parentPerkRequiredLevel = parentLevelRequired;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public int getMaxSkillLevel() {
        return maxSkillLevel;
    }

    public Scaler getScaler() {
        return scaler;
    }

    public boolean isRandomChance(double level) {
        return scaler.isRandomChance(level);
    }

    public double getScale(double level) {
        return scaler.scale(level);
    }

    public boolean hasRequirement() {
        return requiredPerk != null;
    }

    public SkillPerk getRequiredSkill() {
        return requiredPerk;
    }

    public int getParentPerkRequiredLevel() {
        return parentPerkRequiredLevel;
    }

    public SkillType getSkill() {
        return skill;
    }

    public static Set<SkillPerk> getPerks(SkillType type) {
        switch (type) {
            case MINING:
                return Sets.newHashSet(MINING_AUTO_SMELT, MINING_BLAST_PICK, MINING_DOUBLE_DROP);
            case MELEE:
                break;
            case TAMING:
                break;
            case MAGIC:
                break;
            case AGILITY:
                break;
            case STEALTH:
                break;
            case ARCHERY:
                break;
        }

        return Sets.newHashSet();
    }

    @Override
    public String toString() {
        return name;
    }

}
