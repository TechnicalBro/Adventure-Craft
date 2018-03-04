package com.caved_in.adventurecraft.adventurebrother.config;

import com.caved_in.adventurecraft.adventurebrother.states.GameAction;
import lombok.ToString;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "game-message")
@ToString(of = {"type","message"})
public class CustomMessage {
    public static CustomMessage of(GameAction type, String msg) {
        return new CustomMessage().set(type,msg);
    }

    @Element(name = "type",type = GameAction.class,required = true)
    public GameAction type;

    @Element(name = "msg")
    public String message;

    public CustomMessage() {}

    public CustomMessage(@Element(name = "type",type = GameAction.class,required = true)GameAction type, @Element(name = "msg")String msg) {
        this.type = type;
        this.message = msg;
    }

    public CustomMessage set(GameAction type, String msg) {
        this.type = type;
        this.message = msg;
        return this;
    }
}
