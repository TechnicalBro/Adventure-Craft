package com.devsteady.gaeacraft.gadget;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.game.gadget.LimitedGadget;
import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CoalFinder extends LimitedGadget {

    private static CoalFinder instance = null;

    public static CoalFinder getInstance() {
        if (instance == null) {
            instance = new CoalFinder();
        }
        return instance;
    }

    protected CoalFinder() {
        super(ItemBuilder.of(Material.GOLDEN_CARROT).name("&eCoal Finder"), 50);
    }

    @Override
    public void use(Player player) {
        int depthLevel = Blocks.getBlockTypeDistance(player.getLocation(), Material.COAL_ORE, 20);
        Chat.actionMessage(player, String.format("&c&lYou're &e%s&c block away from the nearest &6Coal&r&c&l vein",depthLevel));
    }

    @Override
    public void onBreak(Player p) {

    }
}
