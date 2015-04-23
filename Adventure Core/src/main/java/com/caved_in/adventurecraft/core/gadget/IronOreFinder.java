package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.LimitedGadget;
import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IronOreFinder extends LimitedGadget {
    
    private static IronOreFinder instance = null;

    public static IronOreFinder getInstance() {
        if (instance == null) {
            instance = new IronOreFinder();
        }
        return instance;
    }

    protected IronOreFinder() {
        super(ItemBuilder.of(Material.GOLDEN_CARROT).name("&eIron Ore Finder"), 50);
    }

    @Override
    public void use(Player player) {
        int depthLevel = Blocks.getBlockTypeDistance(player.getLocation(), Material.IRON_ORE, 20);
        Chat.actionMessage(player, String.format("&c&lYou're &e%s&c block away from the nearest &6Iron Ore",depthLevel));
    }

    @Override
    public void onBreak(Player p) {

    }

    @Override
    public int id() {
        return 13001;
    }
}
