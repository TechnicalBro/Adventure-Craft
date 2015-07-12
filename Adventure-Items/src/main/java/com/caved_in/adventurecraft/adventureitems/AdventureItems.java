package com.caved_in.adventurecraft.adventureitems;

import com.caved_in.adventurecraft.adventureitems.debug.DebugItemEffect;
import com.caved_in.adventurecraft.adventureitems.effects.CriticalStrikeEffect;
import com.caved_in.adventurecraft.adventureitems.effects.FlameStrikeEffect;
import com.caved_in.adventurecraft.adventureitems.effects.ItemEffectHandler;
import com.caved_in.adventurecraft.adventureitems.listeners.ItemEffectListener;
import com.caved_in.adventurecraft.adventureitems.users.ItemUserManager;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.game.listener.UserManagerListener;

public class AdventureItems extends CraftGame<ItemUserManager> {
    private static AdventureItems instance = null;

    //Core of this plugins functionality, the listener where all actions
    //regarding item effects take place, and are processed per item that was used!
    private ItemEffectListener itemEffectListener;

    //Listener used to handle the connections of incoming and outgoing players.
    private UserManagerListener userManagerListener;

    //Ahh! The item user manager, handles all our custom player data!
    private ItemUserManager userManager;

    //The item Effect handler manages the registration of all item effects!
    private ItemEffectHandler itemEffectHandler;

    @Override
    public void startup() {
        instance = this;

        userManager = new ItemUserManager();

        itemEffectHandler = new ItemEffectHandler(this);

        registerListeners(
                userManagerListener = new UserManagerListener(this),
                itemEffectListener = new ItemEffectListener(this)
        );

		registerDebugActions(
				DebugItemEffect.getInstance()
		);

		getItemEffectHandler().registerItemEffects(
				new FlameStrikeEffect(),
				new CriticalStrikeEffect()
		);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis - TheGamersCave";
    }

    @Override
    public void initConfig() {
        //Todo load item generation config
    }

    @Override
    public void update() {

    }

    @Override
    public long tickDelay() {
        return 20;
    }

    @Override
    public ItemUserManager getUserManager() {
        return userManager;
    }

    public ItemEffectHandler getItemEffectHandler() {
        return itemEffectHandler;
    }

    public static AdventureItems getInstance() {
        return instance;
    }
}
