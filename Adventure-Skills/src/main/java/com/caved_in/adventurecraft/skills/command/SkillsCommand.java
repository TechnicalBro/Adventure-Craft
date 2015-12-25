package com.caved_in.adventurecraft.skills.command;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.data.SkillEntry;
import com.caved_in.adventurecraft.skills.util.ExpTables;
import com.caved_in.commons.command.Command;
import org.bukkit.entity.Player;

import java.util.Map;

public class SkillsCommand {

    @Command(identifier = "skills")
    public void onSkillsCommand(Player player) {
        SkillsUser user = AdventureSkills.API.getUser(player);

        for (Map.Entry<SkillType, SkillEntry> entry : user.getSkillData().entrySet()) {
            user.format("&6%s &c-&6 Lvl &c%s &e(&7%s&r/&7%s&e)", entry.getKey().toString(), entry.getValue().getLevel(), entry.getValue().getExp(), ExpTables.xpForLevel(entry.getValue().getLevel() + 1));
        }
    }

    @Command(identifier = "skills menu")
    public void onSkillsMenuCommand(Player player) {
        AdventureSkills.API.getUser(player).openSkillsMenu();
    }
}
