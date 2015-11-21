package com.caved_in.adventurecraft.core.gadget;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.game.gadget.ItemGadget;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.time.Cooldown;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HandheldTntCannon extends ItemGadget {
    private Map<UUID, Long> shootCooldown = new HashMap<>();
    private static final int COOLDOWN_TIME = 850;
    
    public HandheldTntCannon() {
        super(ItemBuilder.of(Material.IRON_BARDING).name("&c&lTNT Cannon").lore("&eUsing live &cTNT&e as ammo","&edemolish anything in your path."));
    }

    @Override
    public void perform(Player player) {
        UUID playerId = player.getUniqueId();
        
        if (shootCooldown.containsKey(playerId)) {
            long shootAbleTime = shootCooldown.get(playerId);
            
            if (System.currentTimeMillis() < shootAbleTime) {
                Chat.actionMessage(player,"&cYour TNT Cannon is still on cooldown!");
                return;
            }
        }
        
        PlayerInventory inv = player.getInventory();

        int slotTnt = Inventories.getFirst(inv, Material.TNT);

        if (slotTnt == -1) {
            Chat.actionMessage(player,"&cThe TNT Cannon requires TNT as ammo.");
            return;
        }
        
        ItemStack ammoStack = inv.getItem(slotTnt);

        ammoStack = Items.removeFromStack(ammoStack,1);

		inv.setItem(slotTnt,ammoStack);

        TNTPrimed primedTnt = Entities.spawnPrimedTnt(player.getLocation());
		primedTnt.getVelocity().multiply(20);
         
        shootCooldown.put(playerId, Long.sum(System.currentTimeMillis(),COOLDOWN_TIME));
    }
}
