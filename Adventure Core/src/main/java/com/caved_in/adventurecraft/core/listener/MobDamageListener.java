package com.caved_in.adventurecraft.core.listener;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.ItemMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobDamageListener implements Listener {
    private ItemMessage im = null;
    public MobDamageListener() {
        im = AdventureCore.getInstance().getItemMessage();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByPlayerEvent(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Entity eDamager = e.getDamager();
        Entity eDamaged = e.getEntity();

        if (!(eDamager instanceof Player)) {
            return;
        }

        Player player = (Player)eDamager;

        double damage = e.getFinalDamage();

        //Todo implement holograms for damage display.
    }

}
