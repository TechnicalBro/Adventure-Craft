package com.caved_in.adventurecraft.skills.menu.skills;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.menu.skilltree.SkillTreeMenu;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.data.SkillEntry;
import com.caved_in.adventurecraft.skills.util.ExpTables;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.menu.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class SkillsMenuItem extends MenuItem {
    private SkillType skill;
    private SkillEntry data;

    public SkillsMenuItem(SkillType type, SkillEntry entry) {
        super(Chat.format("&a%s", type.toString()), type.getIcon(), entry.getLevel());
        this.skill = type;
        this.data = entry;
        setDescriptions(type.getDescription());
        addDescriptions(
                "",
                Chat.format("&7%s&r/&7%sxp", entry.getExp(), ExpTables.xpForLevel(entry.getLevel() + 1)),
                "",
                "&cAvailable Skill Points: &e" + entry.getSkillPoints()
        );
    }

    @Override
    public void onClick(Player player, ClickType type) {
        SkillsUser user = AdventureSkills.API.getUser(player);
        if (skill == SkillType.MINING) {
            getMenu().switchMenu(player, new SkillTreeMenu(user, skill));
        } else {
            user.format("&cUnable to open skill tree menu for skill %s as it's unimplemented.", skill.toString());
        }
    }
}
