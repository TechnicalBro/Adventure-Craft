package com.caved_in.adventurecraft.skills.users.data;

import com.caved_in.adventurecraft.skills.util.ExpTables;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "skill")
public class SkillEntry {
    @Attribute(name = "level")
    private int level;

    @Attribute(name = "exp")
    private int exp;

    @Attribute(name = "skill-points", required = false)
    private int skillPoints = 0;

    public SkillEntry(@Attribute(name = "level") int level, @Attribute(name = "exp") int exp, @Attribute(name = "skill-points", required = false) int skillPoints) {
        this.level = level;
        this.exp = exp;
        this.skillPoints = skillPoints;
    }

    public SkillEntry() {
        level = 1;
        exp = ExpTables.xpForLevel(1);
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void addSkillPoint() {
        skillPoints += 1;
    }

    public void addSkillPoints(int point) {
        skillPoints += point;
    }

    public void removeSkillPoint() {
        if (skillPoints == 0) {
            return;
        }

        skillPoints -= 1;
    }

    public void removeSkillPoints(int amount) {
        if (skillPoints == 0 || skillPoints - amount <= 0) {
            return;
        }

        skillPoints -= amount;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void addExp(int exp) {
        this.exp += exp;
    }

    public boolean hasLevelUp() {
        if (ExpTables.getLevelAt(exp) == (level + 1)) {
            level += 1;
            return true;
        }
        return false;
    }
}
