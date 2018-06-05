package com.devsteady.loot.debug;

import com.devsteady.loot.AdventureLoot;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.item.ItemBuilder;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.utilities.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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

		if (!AdventureLoot.getInstance().getItemEffectHandler().effectExists(effectName)) {
			Chat.message(player, "&cThe Effect '&e" + effectName + "&c' does not exist.");

			Chat.message(player,"&e--- &aAvailable Effects&e ---");
			for(String name : AdventureLoot.getInstance().getItemEffectHandler().getEffectNames()) {
				Chat.message(player,String.format("&e- &a%s",name));
			}
			return;
		}

		AdventureLoot.getInstance().getItemEffectHandler().getEffect(effectName).apply(player.getItemInHand());
    	Chat.message(player, "&6You've added &e" + effectName + "&6 to your item!");
	}

    @Override
    public String getActionName() {
        return "item_effect";
    }
}
