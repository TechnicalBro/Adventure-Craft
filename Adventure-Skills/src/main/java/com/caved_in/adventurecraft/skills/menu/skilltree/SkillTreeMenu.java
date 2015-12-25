package com.caved_in.adventurecraft.skills.menu.skilltree;

import com.caved_in.adventurecraft.skills.skills.SkillPerk;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.menu.ItemMenu;

import java.util.Set;

public class SkillTreeMenu extends ItemMenu {
    public SkillTreeMenu(SkillsUser user, SkillType type) {
        super(Chat.format("%s Perks", type.toString()), 2);
        setExitOnClickOutside(false);
        Set<SkillPerk> perks = SkillPerk.getPerks(type);
        int index = 0;
        for (SkillPerk perk : perks) {
            addMenuItem(new SkillTreeMenuItem(perk, user), index++);
        }
    }
}
