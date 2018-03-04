package com.devsteady.gaeacraft.debug;

import com.devsteady.gaeacraft.gadget.IronOreFinder;
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
