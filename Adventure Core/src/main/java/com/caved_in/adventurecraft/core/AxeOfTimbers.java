package com.caved_in.adventurecraft.core;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.ItemGadget;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AxeOfTimbers extends ItemGadget {

    private int id;
    public AxeOfTimbers(ItemStack item, int id) {
        super(item);
        this.id = id;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void perform(Player player) {
        Block blockAtCursor = Blocks.getBlockAt(Players.getTargetLocation(player,2));
        //TODO hook into towny and check if block that player is pointing at is protected
        //todo hook into worldguard and see if the block they're trying to break is protected

        if (Blocks.isOfAnyType(blockAtCursor, Material.CHEST,Material.FURNACE,Material.STORAGE_MINECART,Material.))
        //todo hook into LWC and see if the block they're breaking is protected
    }

    @Override
    public void onBreak(Player p) {
        Chat.actionMessage(p,"&cYour Huntsman Axe Shatters");
    }
}
