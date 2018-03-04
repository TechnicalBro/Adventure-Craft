package com.caved_in.adventurecraft.skills.menu.skills;

import com.caved_in.adventurecraft.skills.skills.SkillType;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.data.SkillEntry;
import com.caved_in.commons.menu.ItemMenu;

import java.util.Map;

public class SkillsMenu extends ItemMenu {
    public SkillsMenu(SkillsUser user) {
        super("Your Skills",1);

        int index = 1;
        for(Map.Entry<SkillType, SkillEntry> entry : user.getSkillData().entrySet()) {
            addMenuItem(new SkillsMenuItem(entry.getKey(),entry.getValue()),index++);
        }
    }
}
