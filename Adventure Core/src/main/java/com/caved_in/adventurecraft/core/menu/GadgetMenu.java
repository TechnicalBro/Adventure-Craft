package com.caved_in.adventurecraft.core.menu;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.MenuItem;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Set;

public class GadgetMenu extends ItemMenu {
    public GadgetMenu() {
        super("Gadgets Menu", Menus.getRows(Gadgets.getGadgetCount()));
        
        Collection<Gadget> gadgets = Gadgets.getAllGadgets();
        int i = 0;
        for (Gadget gadget : gadgets) {
            addMenuItem(new GadgetMenuItem(gadget),i++);
        }
    }
    
    
    public static class GadgetMenuItem extends MenuItem {
        private int id;
        
        public GadgetMenuItem(Gadget gadget) {
            super(Items.getName(gadget.getItem()),gadget.getItem().getData());
            this.id = gadget.id();
        }

        @Override
        public void onClick(Player player) {
            Players.giveItem(player, Gadgets.getGadget(id).getItem());
            Chat.actionMessage(player,"&cBam! New Gadgets added!");
            close(player);
        }
    }
}
