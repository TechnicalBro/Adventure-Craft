package com.caved_in.adventurecraft.adventurebrother.states;

import com.caved_in.adventurecraft.adventurebrother.config.GameActionConvertor;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root()
@Convert(GameActionConvertor.class)
public enum GameAction {
    CHAT("[&aChat&r]","chat"),
    LOGIN("[&9Login&r]","login"),
    LOGOUT("[&4Logout&r]","logout"),
    KILL_PLAYER("[&cPlayer Kill&r]","pk","playerkill","killplayer"),
    DEATH("[&8Death&r]","die","death"),
    KILL_ENTITY("[&eKill Entity&r]","killentity","slay","kill"),
    BREAK_BLOCK("[&6Break Block&r]","break","breakblock","blockbreak"),
    PLACE_BLOCK("[&5Place Block&r]","place","placeblock","blockplace"),
    INTERACT_BLOCK("[Not Yet Implemented - Interact Block]","nyi"),
    PERFORM_COMMAND("[&bCommand&r]","cmd","command","console"),
    CRAFT_ITEM("[&bCraft Item&r]","craft","craftitem","itemcraft");

    private String format;
    private String[] aliases;

    GameAction(String format, String... aliases) {
        this.format = format;
        this.aliases = aliases;
    }

    public String getChatFormat() {
        return this.format;
    }

    public boolean isAlias(String text) {
        for(String alias : aliases) {
            if (alias.equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name();
    }
}
