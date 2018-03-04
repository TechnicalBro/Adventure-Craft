package com.devsteady.loot.users;


import com.caved_in.commons.game.players.UserManager;

public class ItemUserManager extends UserManager<ItemUser> {
    public ItemUserManager() {
        super(ItemUser.class);
    }
}
