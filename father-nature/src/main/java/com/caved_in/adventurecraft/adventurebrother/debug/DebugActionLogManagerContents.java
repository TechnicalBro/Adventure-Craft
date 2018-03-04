package com.caved_in.adventurecraft.adventurebrother.debug;

import com.caved_in.adventurecraft.adventurebrother.action.ActionLogManager;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import org.bukkit.entity.Player;

public class DebugActionLogManagerContents implements DebugAction {
    @Override
    public void doAction(Player player, String... strings) {
        Chat.message(player, ActionLogManager.getInstance().getContents().toString());
    }

    @Override
    public String getActionName() {
        return "actionlogmanager_contents";
    }
}
