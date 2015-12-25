package com.caved_in.adventurecraft.skills.states;

import com.caved_in.commons.game.MiniGameState;
import com.caved_in.commons.player.Players;

public class EmptyServerState extends MiniGameState {
    @Override
    public void update() {

    }

    @Override
    public int id() {
        return 1;
    }

    @Override
    public boolean switchState() {
        return Players.isOnline(1);
    }

    @Override
    public int nextState() {
        return 2;
    }

    @Override
    public void setup() {
        debug("Awaiting the arrival of Players before proceeding to next state!");
        setSetup(true);
    }

    @Override
    public void destroy() {

    }
}
