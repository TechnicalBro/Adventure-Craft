package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.LimitedGadget;
import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GoldFinder extends LimitedGadget {

    private static GoldFinder instance = null;

    public static GoldFinder getInstance() {
        if (instance == null) {
            instance = new GoldFinder();
        }
        return instance;
    }

    protected GoldFinder() {
        super(ItemBuilder.of(Material.GOLDEN_CARROT).name("&eGold Ore Finder"), 50);
    }

    @Override
    public void use(Player player) {
        int depthLevel = Blocks.getBlockTypeDistance(player.getLocation(), Material.GOLD_ORE, 20);
        Chat.actionMessage(player, String.format("&c&lYou're &e%s&c block away from the nearest &6Gold Ore",depthLevel));
    }

    @Override
    public void onBreak(Player p) {

    }

    @Override
    public int id() {
        return 13003;
    }
}
