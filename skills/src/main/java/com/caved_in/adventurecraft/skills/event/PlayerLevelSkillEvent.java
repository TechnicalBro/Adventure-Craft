package com.caved_in.adventurecraft.skills.event;

import com.caved_in.adventurecraft.skills.skills.SkillType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerLevelSkillEvent extends PlayerEvent {
    private static HandlerList handlers = new HandlerList();

    private SkillType skill;

    private int previousLevel;
    private int reachedLevel;

    public PlayerLevelSkillEvent(Player who, SkillType skill, int previousLevel, int reachedLevel) {
        super(who);
        this.skill = skill;
        this.previousLevel = previousLevel;
        this.reachedLevel = reachedLevel;
    }

    public SkillType getSkill() {
        return skill;
    }

    public int getPreviousLevel() {
        return previousLevel;
    }

    public int getReachedLevel() {
        return reachedLevel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
