package com.caved_in.adventurecraft.adventurebrother.config;

import com.caved_in.adventurecraft.adventurebrother.states.GameAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomMessageConfig {

    private static Map<GameAction, String> actionFileLocations = new HashMap<>();

    private static Map<GameAction, List<CustomMessage>> gameMessages = new HashMap<>();

    static {
        for(GameAction action : GameAction.values()) {
            actionFileLocations.put(action,getConfigFileName(action));
        }
    }

    public static void loadCollection(CustomMessageCollection collection) {
        gameMessages.put(collection.type,collection.messages);
    }

    public static List<CustomMessage> getMessages(GameAction action) {
        return gameMessages.get(action);
    }

    public static String getConfigFile(GameAction action) {
        return String.format("%s.xml",actionFileLocations.get(action));
    }

    //todo move this to a global config file.
    protected static String getConfigFileName(GameAction action) {
        switch (action) {
            case CHAT:
                return "chat-messages";
            case LOGIN:
                return "login-messages";
            case LOGOUT:
                return "logout-messages";
            case KILL_PLAYER:
                return "player-kill-messages";
            case DEATH:
                return "death-messages";
            case KILL_ENTITY:
                return "entity-kill-messages";
            case BREAK_BLOCK:
                return "block-break-messages";
            case PLACE_BLOCK:
                return "place-block-messages";
            case INTERACT_BLOCK:
                return "interact-block-messages";
            case PERFORM_COMMAND:
                return "perform-command-messages";
            case CRAFT_ITEM:
                return "item-craft-messages";
            default:
                return "messages";
        }
    }

}
