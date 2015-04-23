package com.caved_in.adventurecraft.core.debug;

import com.caved_in.adventurecraft.core.gadget.IronOreFinder;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class DebugProtoFinder implements DebugAction {
    @Override
    public void doAction(Player player, String... strings) {
        Players.giveItem(player, IronOreFinder.getInstance().getItem());
    }

    @Override
    public String getActionName() {
        return "prototype_orefinder";
    }
}
