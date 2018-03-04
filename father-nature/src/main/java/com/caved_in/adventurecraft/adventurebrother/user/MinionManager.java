package com.caved_in.adventurecraft.adventurebrother.user;

import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.player.User;

import java.util.Set;
import java.util.stream.Collectors;

public class MinionManager extends UserManager<Minion> {

    public MinionManager() {
        super(Minion.class);
    }

    public Set<Minion> getAllInBigBrotherChannel() {
        return allUsers().stream().filter(Minion::isInBigBrotherChatChannel).collect(Collectors.toSet());
    }
}
