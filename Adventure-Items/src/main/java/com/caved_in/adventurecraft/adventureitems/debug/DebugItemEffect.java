package com.caved_in.adventurecraft.adventureitems.debug;

import com.caved_in.adventurecraft.adventureitems.AdventureItems;
import com.caved_in.adventurecraft.adventureitems.effects.ItemEffect;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugItemEffect implements DebugAction {
	private static DebugItemEffect instance = null;

	public static DebugItemEffect getInstance() {
		if (instance == null) {
			instance = new DebugItemEffect();
		}

		return instance;
	}

    public DebugItemEffect() {

    }

    @Override
    public void doAction(Player player, String... strings) {
        if (Players.handIsEmpty(player)) {
            player.setItemInHand(ItemBuilder.of(Material.DIAMOND_SWORD).item());
        }

		String effectName = StringUtil.joinString(strings," ",0);

		if (!AdventureItems.getInstance().getItemEffectHandler().effectExists(effectName)) {
			Chat.message(player, "&cThe Effect '&e" + effectName + "&c' does not exist.");

			Chat.message(player,"&e--- &aAvailable Effects&e ---");
			for(String name : AdventureItems.getInstance().getItemEffectHandler().getEffectNames()) {
				Chat.message(player,String.format("&e- &a%s",name));
			}
			return;
		}

		AdventureItems.getInstance().getItemEffectHandler().getEffect(effectName).apply(player.getItemInHand());
    	Chat.message(player, "&6You've added &e" + effectName + "&6 to your item!");
	}

    @Override
    public String getActionName() {
        return "item_effect";
    }
}
