package com.caved_in.adventurecraft.skills.config;


import org.simpleframework.xml.Element;

public class SkillsConfig {

    @Element(name = "exp-requirement")
    private double expRequirementFactor = 1.0;

    @Element(name = "mob-level-modify-cap")
    private int mobLevelModifyCap = 200;

    public SkillsConfig(@Element(name = "exp-requirement")double expRequirementFactor, @Element(name = "mob-level-modify-cap")int mobLevelModifyCap) {
        this.expRequirementFactor = expRequirementFactor;
        this.mobLevelModifyCap = mobLevelModifyCap;
    }

    public SkillsConfig() {

    }

    public double getExpRequirementFactor() {
        return expRequirementFactor;
    }

    public int getMobLevelModifyCap() {
        return mobLevelModifyCap;
    }
}
