package com.caved_in.adventurecraft.fishing.listeners;

import com.caved_in.adventurecraft.fishing.AdventureFishing;
import com.caved_in.adventurecraft.fishing.config.PluginConfig;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.shards.Shards;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishingListener implements Listener {
    
    private TitleBuilder lootFoundTitle = new TitleBuilder().title("").fadeOut(1).fadeIn(1).stay(3);
    
    @EventHandler
    public void onFishingEvent(PlayerFishEvent e) {
        PlayerFishEvent.State state = e.getState();

        switch (state) {
            case CAUGHT_FISH:
            case CAUGHT_ENTITY:
                if (!NumberUtil.percentCheck(PluginConfig.LOOT_CHANCE)) {
                    return;
                }

                ItemStack fishingLoot = AdventureFishing.Config.getRandomLoot();

                Players.giveItem(e.getPlayer(), fishingLoot, true);
//                Chat.actionMessage(e.getPlayer(), String.format("&9You've found a(n) %s&9 while fishing!"));
                lootFoundTitle.title("&eFishing Catch!").subtitle(Items.getName(fishingLoot)).build().send(e.getPlayer());

                Shards.chanceSpawn(e.getPlayer().getLocation(),PluginConfig.SHARDS_ON_LOOT_CHANCE,NumberUtil.getRandomInRange(1,2));
                break;
            default:
                break;
        }
    }
}
