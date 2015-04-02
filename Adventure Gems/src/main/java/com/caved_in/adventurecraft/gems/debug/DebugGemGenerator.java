package com.caved_in.adventurecraft.gems.debug;

import com.caved_in.adventurecraft.gems.AdventureGems;
import com.caved_in.adventurecraft.gems.item.ChancedEnchantment;
import com.caved_in.adventurecraft.gems.item.GemGenerator;
import com.caved_in.adventurecraft.gems.item.GemSettings;
import com.caved_in.adventurecraft.gems.item.GemType;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class DebugGemGenerator implements DebugAction {
    private GemSettings gemSettings = new GemSettings();

    private static DebugGemGenerator instance = null;

    public static DebugGemGenerator getInstance() {
        if (instance == null) {
            instance = new DebugGemGenerator();
        }
        return instance;
    }

    protected DebugGemGenerator() {
        gemSettings.defaultPrefixData().defaultSuffixData().defaultEnchantsFor(Enchantment.values());
    }

    public void doAction(Player player, String... strings) {
        int amount = StringUtil.getNumberAt(strings, 0, 5);
        for (int i = 0; i < amount; i++) {
            Players.giveItem(player, AdventureGems.API.createItem(GemType.EMERALD, gemSettings));
        }
    }

    @Override
    public String getActionName() {
        return "gem_generation";
    }
}
