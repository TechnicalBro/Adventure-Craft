package com.devsteady.loot.users;


import com.devsteady.onyx.game.listener.IUserManagerHandler;
import com.devsteady.onyx.game.players.UserManager;
import org.bukkit.entity.Player;

public class ItemUserManager extends UserManager<ItemUser> implements IUserManagerHandler {
    public ItemUserManager() {
        super(ItemUser.class);
    }

    @Override
    public void handleJoin(Player player) {
        addUser(new ItemUser(player));
    }

    @Override
    public void handleLeave(Player player) {
        addUser(new ItemUser(player));
    }
}
