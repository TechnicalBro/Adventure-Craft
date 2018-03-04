package com.caved_in.adventurecraft.skills.event;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerGainSkillExpEvent extends PlayerEvent {
    private static HandlerList handlers = new HandlerList();

    private SkillType skill;
    private int expGained;

    public PlayerGainSkillExpEvent(Player who, SkillType skill, int exp) {
        super(who);
        this.skill = skill;
        this.expGained = exp;
    }

    public SkillType getSkill() {
        return skill;
    }

    public int getExpGained() {
        return expGained;
    }

    public int getTotalExp() {
        return AdventureSkills.API.getExp(getPlayer(), skill);
    }

    public int getLevel() {
        return AdventureSkills.API.getLevel(getPlayer(), skill);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
