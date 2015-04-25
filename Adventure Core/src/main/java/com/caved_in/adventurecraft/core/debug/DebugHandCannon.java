package com.caved_in.adventurecraft.core.debug;

import com.caved_in.adventurecraft.core.gadget.HandheldTntCannon;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.game.gadget.ItemGadget;
import org.bukkit.entity.Player;

public class DebugHandCannon implements DebugAction {
	@Override
	public void doAction(Player player, String... strings) {
		ItemGadget cannon = (ItemGadget)Gadgets.getGadget(144001);
		cannon.giveTo(player);
	}

	@Override
	public String getActionName() {
		return "hand_cannon";
	}
}
