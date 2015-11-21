package com.caved_in.adventurecraft.adventurebrother.config;

import com.caved_in.adventurecraft.adventurebrother.states.GameAction;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

public class GameActionConvertor implements Converter<GameAction> {
    @Override
    public GameAction read(InputNode inputNode) throws Exception {
        final String value = inputNode.getNext("game-action").getValue();

        for(GameAction action : GameAction.values()) {
            if (action.toString().equalsIgnoreCase(value)) {
                return action;
            }
        }

        throw new IllegalArgumentException("There's no game action by the name of " + value);
    }

    @Override
    public void write(OutputNode outputNode, GameAction gameAction) throws Exception {
        OutputNode child = outputNode.getChild("game-action");
        child.setValue(gameAction.toString());

    }
}
