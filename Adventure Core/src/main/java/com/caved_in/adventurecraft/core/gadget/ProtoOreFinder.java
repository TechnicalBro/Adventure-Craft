package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.LimitedGadget;
import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ProtoOreFinder extends LimitedGadget {
    
    private static ProtoOreFinder instance = null;

    public static ProtoOreFinder getInstance() {
        if (instance == null) {
            instance = new ProtoOreFinder();
        }
        return instance;
    }

    protected ProtoOreFinder() {
        super(ItemBuilder.of(Material.POTATO_ITEM).name("&9Ore Finder: &eIRON"), 20);
    }

    @Override
    public void use(Player player) {
        int depthLevel = Blocks.getBlockTypeDistance(player.getLocation(), Material.IRON_ORE, 20);
        Chat.actionMessage(player, String.format("&c&lYou're &e%s&c block away from the nearest &6IRON ORE",depthLevel));
    }

    @Override
    public void onBreak(Player p) {

    }

    @Override
    public int id() {
        return 13001;
    }
}
