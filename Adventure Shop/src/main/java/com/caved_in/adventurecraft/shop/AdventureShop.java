package com.caved_in.adventurecraft.shop;

import com.caved_in.commons.plugin.BukkitPlugin;
import net.milkbowl.vault.economy.Economy;

public class AdventureShop extends BukkitPlugin {
	private static Economy economy;
	
	@Override
	public void startup() {

	}

	@Override
	public void shutdown() {

	}

	@Override
	public String getAuthor() {
		return null;
	}

	@Override
	public void initConfig() {

	}
	
	public static class API {
		//todo implement hook to economy!
		
	}
}
