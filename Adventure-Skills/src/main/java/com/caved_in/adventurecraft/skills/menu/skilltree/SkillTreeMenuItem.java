package com.caved_in.adventurecraft.skills.menu.skilltree;

import com.caved_in.adventurecraft.skills.menu.skills.SkillsMenu;
import com.caved_in.adventurecraft.skills.skills.SkillPerk;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.data.SkillPerkData;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.menu.MenuItem;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.material.MaterialData;

public class SkillTreeMenuItem extends MenuItem {
    private SkillPerk perk;
    private SkillsUser user;

    public SkillTreeMenuItem(SkillPerk perk, SkillsUser user) {
        super(perk.toString(), new MaterialData(Material.PAPER));
        this.perk = perk;
        this.user = user;
        if (!user.hasPerkUnlocked(perk)) {
            addDescriptions(Chat.format("&6Requirements: &eLevel %s%s", perk.getLevelRequired(), perk.hasRequirement() ? ", " + perk.getRequiredSkill().toString() + " lvl " + perk.getParentPerkRequiredLevel() + "" : ""));
            addDescriptions(Chat.format("&cClick to unlock when met!"));
        } else {
            //todo if the user doesn't have this perk unlocked, allow them to unlock it.
            addDescriptions(Chat.format("&aLevel: &e%s", user.getPerkLevel(perk)));
            addDescriptions("&6" + perk.getScale(user.getPerkLevel(perk)) + "&e% chance. &7(Next Level: " + NumberUtil.round(perk.getScale(user.getPerkLevel(perk) + 1), 2) + "%)");
        }
    }

    @Override
    public void onClick(Player player, ClickType type) {
        //todo implement 2 factor click and confirm for adding stats!
        if (!user.hasSkillPoint(perk.getSkill())) {
            user.format("&cYou don't have any skill points in %s", perk.getSkill().toString());
            return;
        }

        SkillPerkData data = user.getPerkData(perk.getSkill());
        if (data.hasPerkUnlocked(perk)) {

            if (!user.canLevelPerk(perk)) {
                user.format("&cYou're unable to level &e%s7c as it's already at &6Max Level", perk.toString());
                return;
            }

            if (user.levelPerk(perk)) {
                user.format("&6%s&e is now level %s!", perk.toString(), user.getPerkLevel(perk));
            } else {
                user.format("&cYou're unable to level your perk &o%s", perk.toString());
            }
            getMenu().switchMenu(player, new SkillTreeMenu(user, perk.getSkill()));
            return;
            //todo send message with remaining skill points
        } else {
            if (perk.hasRequirement()) {
                if (!user.hasPerkRequirements(perk)) {
                    user.message("&cYou don't have the requirements to unlock this perk");
                    user.format("%s Requires: &7%s level %s", Messages.YELLOW_INDENT_ARROW, perk.getRequiredSkill().toString(), perk.getParentPerkRequiredLevel());
                    return;
                }
            }

            data.unlockPerk(perk);
            user.removeSkillPoint(perk.getSkill(), 1);
            user.format("&aYou've unlocked &6%s&a!", perk.toString());
            getMenu().switchMenu(player, new SkillTreeMenu(user, perk.getSkill()));
        }
    }
}
