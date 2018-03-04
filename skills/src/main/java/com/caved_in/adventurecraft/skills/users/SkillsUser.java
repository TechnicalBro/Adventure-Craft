package com.caved_in.adventurecraft.skills.users;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.event.PlayerGainSkillExpEvent;
import com.caved_in.adventurecraft.skills.event.PlayerLevelSkillEvent;
import com.caved_in.adventurecraft.skills.menu.skills.SkillsMenu;
import com.caved_in.adventurecraft.skills.skills.SkillPerk;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import com.caved_in.adventurecraft.skills.users.data.SkillEntry;
import com.caved_in.adventurecraft.skills.users.data.SkillPerkData;
import com.caved_in.adventurecraft.skills.util.ExpTables;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.player.User;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.HashMap;
import java.util.Map;


public class SkillsUser extends User {
    private static AdventureSkills core = AdventureSkills.getInstance();

    @ElementMap(name = "player-skills", entry = "skill", value = "data", key = "type", keyType = SkillType.class, valueType = SkillEntry.class, attribute = true)
    private Map<SkillType, SkillEntry> skills = new HashMap<>();

    @ElementMap(name = "player-skill-perks", entry = "perk", value = "data", key = "type", keyType = SkillType.class, valueType = SkillPerkData.class,attribute = true)
    private Map<SkillType, SkillPerkData> perks = new HashMap<>();

    @Element(name = "action-exp-bar")
    private boolean actionExpBar = true;

    public SkillsUser(Player p) {
        super(p);
        initSkills();
    }

    public SkillsUser(@Element(name = "name") String name, @Element(name = "uuid") String uid, @Element(name = "world") String world,
                      @ElementMap(name = "player-skills", entry = "skill", value = "data", key = "type", keyType = SkillType.class, valueType = SkillEntry.class, attribute = true) Map<SkillType, SkillEntry> skills,
                      @ElementMap(name = "player-skill-perks", entry = "perk", value = "data", key = "type", keyType = SkillType.class, valueType = SkillPerkData.class,attribute = true) Map<SkillType, SkillPerkData> perks,
                      @Element(name = "action-exp-bar") boolean actionExpBar) {
        super(name, uid, world);
        this.skills = skills;
        this.perks = perks;
        this.actionExpBar = actionExpBar;
        //Initialize all the skills that aren't currently in the map!
        initSkills();
    }

    private void initSkills() {
        for (SkillType type : SkillType.values()) {
            if (!skills.containsKey(type)) {
                skills.put(type, new SkillEntry());
                perks.put(type, new SkillPerkData(type));
            }
        }
    }

    public int getLevel(SkillType skill) {
        return skills.get(skill).getLevel();
    }

    public int getExp(SkillType skill) {
        return skills.get(skill).getExp();
    }

    public void addExp(SkillType type, int exp) {
        SkillEntry data = skills.get(type);

        int previousLevel = data.getLevel();

        data.addExp(exp);

        PlayerGainSkillExpEvent expEvent = new PlayerGainSkillExpEvent(getPlayer(), type, exp);
        Plugins.callEvent(expEvent);

        while (data.hasLevelUp()) {
            PlayerLevelSkillEvent levelEvent = new PlayerLevelSkillEvent(getPlayer(), type, previousLevel, data.getLevel());
            Plugins.callEvent(levelEvent);

            addSkillPoint(type, 1);

            previousLevel = data.getLevel();
        }
    }

    public int getExpUntilNextLevel(SkillType skill) {
        int currentLevel = getLevel(skill);
        int currentExp = getExp(skill);

        int nextLevel = currentLevel + 1;
        int nextExp = ExpTables.xpForLevel(nextLevel);

        return nextExp - currentExp;
    }

    public int getExpDifferenceInLevels(SkillType skill) {
        int currentLevel = getLevel(skill);
        int currentExp = ExpTables.xpForLevel(currentLevel);

        int nextLevel = currentLevel + 1;
        int nextExp = ExpTables.xpForLevel(nextLevel);

        return nextExp - currentExp;
    }

    public boolean hasActionBarEnabled() {
        return actionExpBar;
    }

    public int getCombatLevel() {
        int cmb = 1;
        cmb += getLevel(SkillType.MELEE);
        cmb += getLevel(SkillType.ARCHERY);
        cmb += getLevel(SkillType.MAGIC);

        cmb = (int) Math.round(cmb / 3 + 2);

        return cmb;
    }

    public Map<SkillType, SkillEntry> getSkillData() {
        return skills;
    }

    public Map<SkillType, SkillPerkData> getPerkData() {
        return perks;
    }

    public SkillPerkData getPerkData(SkillType type) {
        return perks.get(type);
    }

    public int getPerkLevel(SkillPerk perk) {
        SkillPerkData data = perks.get(perk.getSkill());
        return data.getLevel(perk);
    }

    public boolean canLevelPerk(SkillPerk perk) {
        SkillPerkData data = getPerkData(perk.getSkill());
        return data.getLevel(perk) < perk.getMaxSkillLevel();
    }

    public boolean levelPerk(SkillPerk perk) {
        if (!canLevelPerk(perk)) {
            return false;
        }

        removeSkillPoint(perk.getSkill(), 1);
        getPerkData(perk.getSkill()).addLevel(perk);
        Chat.debug("User " + getName() + " perk " + perk.toString() + " is now level " + getPerkLevel(perk));
        return true;
    }

    public boolean hasPerkRequirements(SkillPerk perk) {
        if (!perk.hasRequirement()) {
            return true;
        }

        SkillPerkData data = getPerkData(perk.getSkill());
        SkillPerk requirement = perk.getRequiredSkill();
        if (!hasPerkUnlocked(requirement)) {
            return false;
        }

        int reqLevel = requirement.getParentPerkRequiredLevel();
        return getPerkLevel(perk) >= reqLevel;
    }

    public boolean hasPerkUnlocked(SkillPerk perk) {
        return getPerkData(perk.getSkill()).hasPerkUnlocked(perk);
    }

    public int getSkillPoints(SkillType skill) {
        return skills.get(skill).getSkillPoints();
    }

    public void addSkillPoint(SkillType skill, int points) {
        skills.get(skill).addSkillPoints(points);
    }

    public boolean hasSkillPoint(SkillType type) {
        return skills.get(type).getSkillPoints() > 0;
    }

    public void removeSkillPoint(SkillType skill, int points) {
        skills.get(skill).removeSkillPoints(points);
    }

    public void openSkillsMenu() {
        new SkillsMenu(this).openMenu(getPlayer());
    }

    //todo can disable action bar if boss bar is enabled.
    //can't disabe action bar to leave no message.
    //if disabling boss bar and action bar is false, action bar will go true!

}
