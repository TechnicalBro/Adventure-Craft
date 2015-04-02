package com.caved_in.adventurecraft.gems.listener;

import com.caved_in.adventurecraft.gems.event.GemCraftEvent;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GemCraftListener implements Listener {
    
    @EventHandler
    public void onGemCraft(GemCraftEvent e) {
        Chat.messageConsole("====== GEM CRAFT EVENT ======");
        Chat.messageConsole("-->         GEM          <--");


        Chat.messageConsole(Messages.itemInfo(e.getGem()));
        Chat.messageConsole("-->         ITEM         <--");
        Chat.messageConsole(Messages.itemInfo(e.getTarget()));
    }
}
