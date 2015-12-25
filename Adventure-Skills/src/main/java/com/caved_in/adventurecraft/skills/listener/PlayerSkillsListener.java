package com.caved_in.adventurecraft.skills.listener;

import com.caved_in.adventurecraft.skills.AdventureSkills;
import com.caved_in.adventurecraft.skills.event.PlayerGainSkillExpEvent;
import com.caved_in.adventurecraft.skills.event.PlayerLevelSkillEvent;
import com.caved_in.adventurecraft.skills.skills.SkillType;
import com.caved_in.adventurecraft.skills.users.SkillsUser;
import com.caved_in.adventurecraft.skills.users.SkillsUserManager;
import com.caved_in.adventurecraft.skills.util.ExpTables;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.fireworks.Fireworks;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.inventivetalent.bossbar.BossBarAPI;

public class PlayerSkillsListener implements Listener {
    private static PlayerSkillsListener instance = null;
    private static SkillsUserManager users;

    public static PlayerSkillsListener getInstance() {
        if (instance == null) {
            instance = new PlayerSkillsListener();
        }
        return instance;
    }

    protected PlayerSkillsListener() {
        users = AdventureSkills.getInstance().getUserManager();
    }

    @EventHandler
    public void onPlayerGainSkillExp(PlayerGainSkillExpEvent e) {
        Player p = e.getPlayer();
        SkillsUser user = users.getUser(p);
        int totalExp = e.getTotalExp();


        StringBuilder expBarBuilder = new StringBuilder();
        expBarBuilder.append("&6[&r&a&l");

        SkillType skill = e.getSkill();

        int expUntilNextLevel = user.getExpUntilNextLevel(skill);
        int expTotalTillLevel = user.getExpDifferenceInLevels(skill);
        int level = user.getLevel(skill);
        int nextLevel = level + 1;
        double levelPercent = (expUntilNextLevel * 100) / expTotalTillLevel;
        levelPercent = 100 - levelPercent;

//        if (user.hasBossBarEnabled()) {
//            BossBarAPI.setMessage(user.getPlayer(), Chat.format("&c&l%s &r&7- &eLvl &6&l%s", skill.toString(), user.getLevel(skill)), (float) levelPercent, 4, (float) levelPercent * 3);
//        }

        if (user.hasActionBarEnabled()) {

            int totalBars = 20;
            int greenBars = 0;
            int grayBars = 0;

            for (double i = 0; i < levelPercent; i += 5) {
                greenBars += 1;
            }

            if (greenBars > 20) {
                greenBars = 20;
            }

            if (greenBars > 0) {
                expBarBuilder.append(StringUtil.repeat(":", greenBars));
            }

            expBarBuilder.append("&r&7");

            grayBars = totalBars - greenBars;
            if (grayBars > 0) {
                expBarBuilder.append(StringUtil.repeat(":", grayBars));
            }

            expBarBuilder.append("&r&6]&r &7- &6").append(user.getLevel(e.getSkill())).append(" &e").append(e.getSkill().toString());

            Chat.actionMessage(p, expBarBuilder.toString());
        }
    }

    @EventHandler
    public void onPlayerLevelSkill(PlayerLevelSkillEvent e) {
        SkillType skill = e.getSkill();
        Player player = e.getPlayer();
        int level = e.getReachedLevel();

        TitleBuilder.create()
                .title(String.format("&c&l%s&r &6Level Up!", skill.toString()))
                .subtitle(String.format("&6Lvl &e&l%s &6Achieved!", level))
                .fadeIn(1)
                .stay(4)
                .fadeOut(2)
                .seconds()
                .build()
                .send(player);

        //todo check for specific unlocks on skill achieved.

        ParticleEffects.sendToLocation(ParticleEffects.ENCHANTMENT_TABLE, player.getLocation(), NumberUtil.getRandomInRange(15, 20));

        Fireworks.playRandomFireworks(player.getLocation(), 5);

        Sounds.playSound(player, Sound.LEVEL_UP, 1, 0.9F);
        Sounds.playSound(player, Sound.LEVEL_UP, 1, 1.1F);
        Sounds.playSound(player, Sound.LEVEL_UP, 1, 1.0F);
    }
}
