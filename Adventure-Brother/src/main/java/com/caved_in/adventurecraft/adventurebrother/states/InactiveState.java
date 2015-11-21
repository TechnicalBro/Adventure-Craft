package com.caved_in.adventurecraft.adventurebrother.states;

import com.caved_in.commons.game.MiniGameState;
import com.caved_in.commons.player.Players;

public class InactiveState extends MiniGameState {
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
        debug("Big Brother is Inactive! Ain't no niggas online.");
        setSetup(true);
    }

    @Override
    public void destroy() {
        debug("Big Brother is HEATING UP NIGGA, HEATING UP.");
    }
}
