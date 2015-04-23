package com.caved_in.adventurecraft.core.menu;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.SubMenuItem;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.jake.survival.townygui.StemTownGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class AdventureMenu extends ItemMenu {
    public AdventureMenu(Player player) {
        super(String.format("%s's Adventure Planner", player), 1);
    
        /*
        Add the players homes to the menu! So they can access
        everything from a central location.
         */
        addMenuItem(new SubMenuItem("&eYour Home(s)", new MaterialData(Material.PAPER),AdventureHomes.API.getMenu(player).returnAlternative(this)),1);

        addMenuItem(new SubMenuItem("&aTowny Menu",new MaterialData(Material.GRASS), StemTownGUI.getInstance().getMenu()),2);
        
        /* 
        If the player can enter debug mode, then we're gonna just give them
        the gadget menu; So they can enjoy dat shit.
         */
        if (player.hasPermission(Perms.DEBUG_MODE)) {
            addMenuItem(new SubMenuItem("&6Gadget Selection Menu",new MaterialData(Material.PAPER),new GadgetMenu()),3);
        

        }
        //todo implement check for if player is debugging, and if so add an item with some neat-o stuffs!
    }

}
