package com.caved_in.adventurecraft.adventurebrother.event;

import com.caved_in.adventurecraft.adventurebrother.action.MinionAction;
import com.caved_in.adventurecraft.adventurebrother.user.Minion;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

//todo implement cancellable for people unmonirotable? Perhaaaaps?
public class MinionLogActionEvent extends Event {
    private static HandlerList handlers = new HandlerList();

    private Minion minion;
    private MinionAction action;

    public MinionLogActionEvent(Minion minion, MinionAction action) {
        this.minion = minion;
        this.action = action;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Minion getMinion() {
        return minion;
    }

    public MinionAction getAction() {
        return action;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
