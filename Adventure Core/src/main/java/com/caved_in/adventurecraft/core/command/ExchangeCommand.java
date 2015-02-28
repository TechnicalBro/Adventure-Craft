package com.caved_in.adventurecraft.core.command;

import com.caved_in.adventurecraft.core.AdventureCore;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class ExchangeCommand {
    
    private Economy economy;
    
    public ExchangeCommand() {
        economy = AdventureCore.getInstance().getEconomy();
    }
    
    @Command(identifier = "exchange",description = "Convert your Tunnels EXP to Gold and vice versa")
    public void onExchangeCommand(Player player) {

        Chat.message(player,
                "&6How to exchange &aTunnels Exp &6<-> &eGold&6:",
                "&eFor &a1 Exp &eyou get &63 Gold&e, and vice versa",
                "&b * &eUse /exchange exp <number>&a to receive &egold",
                "&b * &eUse /exchange gold <number>&a to receive &eexp"
                );
    }
    
    @Command(identifier = "exchange gold",description = "Convert your Gold into Tunnels EXP")
    public void onExchangeGoldCommand(Player player, @Arg(name = "amount")int amount) {
        if (!economy.has(player,amount)) {
            Chat.message(player,"&eYou don't have enough gold to make the exchange.");
            return;
        }

        EconomyResponse response = economy.withdrawPlayer(player,amount);

        if (!response.transactionSuccess()) {
            Chat.message(player,"&cError performing exchange: &e" + response.errorMessage);
            return;
        }

        int receivingExp = Math.round(amount / AdventureCore.Properties.GOLD_PER_EXP);
        Players.giveMoney(player, receivingExp, false);
        Chat.message(player, String.format("&aYou receive &e%s&a exp from the exchange!",receivingExp));
        Chat.message(player, String.format("&6You have &e%s&6 gold remaining", economy.getBalance(player)));
    }
    
    @Command(identifier = "exchange exp",description = "Convert your Tunnels EXP into Gold")
    public void onExchangeExpCommand(Player player, @Arg(name = "amount")int amount) {
        int depositAmount = amount * AdventureCore.Properties.GOLD_PER_EXP;
        
        int playerExp = Players.getMoney(player);
        
        if (playerExp < amount) {
            Chat.message(player, "&eYou don't have enough exp to complete the exchange.");
            return;
        }
        
        EconomyResponse response = economy.depositPlayer(player,depositAmount);
        
        if (!response.transactionSuccess()) {
            Chat.message(player,"&cError performing exchange: &e" + response.errorMessage);
            return;
        }
        
        Players.removeMoney(player,amount);
        Chat.message(player, String.format("&aYou receive &e%s&a gold from the exchange!", depositAmount));
        Chat.message(player,String.format("&6You now have &e%s&6 exp remaining",Players.getMoney(player)));
    }
}
