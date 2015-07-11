package com.caved_in.adventurecraft.adventureitems.debug;

import com.caved_in.adventurecraft.adventureitems.AdventureItems;
import com.caved_in.adventurecraft.adventureitems.effects.ItemEffect;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugItemEffect implements DebugAction {
    public DebugItemEffect() {
        AdventureItems.getInstance().getItemEffectHandler().registerItemEffects(new ItemEffect() {
            @Override
            public String name() {
                return "Flame Strike";
            }

            @Override
            public boolean verify(ItemStack item) {
                if (!Items.hasLore(item)) {
                    return false;
                }

                return Items.loreContains(item, "+-+ Fire Strike");
            }

            @Override
            public boolean onPlayerDamagePlayer(Player attacked, Player damaged) {
                return false;
            }

            @Override
            public boolean onPlayerBreakBlock(Player player, Block block) {
                return false;
            }

            @Override
            public boolean onPlayerDrop(Player player, Item item) {
                Entities.burn(player,2, TimeType.SECOND);
                item.setPickupDelay(Integer.MAX_VALUE);
                item.setFireTicks(90);

                Worlds.clearDroppedItems(item.getLocation(),2,3,TimeType.SECOND);
                return true;
            }

            @Override
            public boolean onPlayerDamageEntity(Player player, Entity entity) {
                return false;
            }

            @Override
            public boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity) {
                return false;
            }

            @Override
            public void apply(ItemStack item) {

            }
        });
    }

    @Override
    public void doAction(Player player, String... strings) {
        if (Players.handIsEmpty(player)) {
            player.setItemInHand(ItemBuilder.of(Material.DIAMOND_SWORD).item());
        }


    }

    @Override
    public String getActionName() {
        return "item_effect_1";
    }
}
