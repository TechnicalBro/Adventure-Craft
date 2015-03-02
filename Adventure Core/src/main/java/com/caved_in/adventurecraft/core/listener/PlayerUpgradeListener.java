package com.caved_in.adventurecraft.core.listener;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.adventurecraft.core.user.AdventurePlayer;
import com.caved_in.adventurecraft.core.user.upgrades.PlayerUpgrade;
import com.caved_in.adventurecraft.core.user.upgrades.UserUpgrade;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.item.ToolType;
import com.caved_in.commons.world.Worlds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerUpgradeListener implements Listener {
    private AdventureCore core;
    
    public PlayerUpgradeListener(AdventureCore core) {
        this.core = core;
    }
    
    @EventHandler //TODO Create an event specifically for when players mine (block broken with pickaxe)
    public void onPlayerMineEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!ToolType.isType(ToolType.PICK_AXE,player.getItemInHand().getType())) {
            return;
        }

        AdventurePlayer user = core.getUserManager().getUser(player);
        UserUpgrade upgradeData = user.getUpgradeData(PlayerUpgrade.Type.WOOD_CHOPPING);
        if (!upgradeData.chanceCheck()) {
            return;
        }
    
    }
    
    @EventHandler //TODO Create an event specifically for when players chop wood (block break with axe in hand)
    public void onPlayerChopWood(BlockBreakEvent e) {
        Player player = e.getPlayer();
        
        if (!ToolType.isType(ToolType.AXE,player.getItemInHand().getType())) {
            return;
        }

        AdventurePlayer user = core.getUserManager().getUser(player);
        UserUpgrade upgradeData = user.getUpgradeData(PlayerUpgrade.Type.WOOD_CHOPPING);
        if (!upgradeData.chanceCheck()) {
            return;
        }

        
        Worlds.dropItem(player, Items.convertBlockToItem(e.getBlock()),true);
    }
}
