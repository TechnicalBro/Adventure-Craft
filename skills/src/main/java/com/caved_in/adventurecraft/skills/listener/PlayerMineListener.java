package com.caved_in.adventurecraft.skills.listener;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.skills.Mining;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.SkillsUserManager;
import com.caved_in.commons.chat.Chat;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerMineListener implements Listener {
    private static PlayerMineListener instance = null;
    private static SkillsUserManager users;

    public static PlayerMineListener getInstance() {
        if (instance == null) {
            instance = new PlayerMineListener();
        }
        return instance;
    }

    protected PlayerMineListener() {
        users = AdventureSkills.getInstance().getUserManager();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent e) {

        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        Block block = e.getBlock();

        SkillsUser user = users.getUser(player);
        int expGained = Mining.getExpForBlock(block);

        if (expGained == 0) {
            return;
        }

        user.addExp(SkillType.MINING, Mining.getExpForBlock(block));
        //todo check if it's a player placed block!
    }
}
