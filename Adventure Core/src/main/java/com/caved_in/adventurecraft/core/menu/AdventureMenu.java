package com.caved_in.adventurecraft.core.menu;

import com.caved_in.adventurecraft.homes.AdventureHomes;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.SubMenuItem;
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
    }

}
