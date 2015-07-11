package com.caved_in.adventurecraft.adventureitems.event;

import com.caved_in.adventurecraft.adventureitems.effects.ItemEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerUseItemEffectEvent extends PlayerEvent {
    public static HandlerList handlers = new HandlerList();

    private ItemEffect itemEffect;

    public PlayerUseItemEffectEvent(Player who,ItemEffect itemEffect) {
        super(who);
        this.itemEffect = itemEffect;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
