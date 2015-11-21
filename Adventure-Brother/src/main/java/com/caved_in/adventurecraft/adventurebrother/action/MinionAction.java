package com.caved_in.adventurecraft.adventurebrother.action;

import com.caved_in.adventurecraft.adventurebrother.states.GameAction;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.text.SimpleDateFormat;
import java.util.Date;

@Root(name = "action")
public class MinionAction {
    @Attribute(name = "type")
    public GameAction type;

    @Attribute(name = "timestamp")
    public long timestamp;

    @Attribute(name = "info")
    public String message;

    @Override
    public String toString() {
        return type.getChatFormat() + " &b @ [" + getTimestampString() + "] &r " + message;
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private String getTimestampString() {
        return sdf.format(new Date(timestamp));
    }


}
