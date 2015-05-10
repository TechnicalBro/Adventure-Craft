package com.caved_in.adventurecraft.core.debug;

import com.caved_in.adventurecraft.core.gadget.*;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugCustomArrows implements DebugAction {
    @Override
    public void doAction(Player player, String... strings) {
        int amount = StringUtil.getNumberAt(strings,0,5);
        
        ItemStack docileArrow = DocileArrowGadget.getInstance().getItem();
        ItemStack explosiveArrow = ExplosiveArrowGadget.getInstance().getItem();
        ItemStack grapplingArrow = GrapplingArrowGadget.getInstance().getItem();
        ItemStack kinArrow = KinArrowGadget.getInstance().getItem();
        ItemStack slowingArrow = SlowingArrowGadget.getInstance().getItem();
        ItemStack enderArrow = EnderArrowGadget.getInstance().getItem();
		ItemStack healingArrow = HealingArrow.getInstance().getItem();

        docileArrow.setAmount(amount);
        explosiveArrow.setAmount(amount);
        grapplingArrow.setAmount(amount);
        kinArrow.setAmount(amount);
        slowingArrow.setAmount(amount);
        enderArrow.setAmount(amount);
		healingArrow.setAmount(amount);

        Players.giveItem(player, Items.makeItem(Material.BOW),docileArrow,explosiveArrow,grapplingArrow,
                kinArrow,slowingArrow,enderArrow,healingArrow);
        
    }

    @Override
    public String getActionName() {
        return "custom_arrows";
    }
}
