package com.caved_in.adventurecraft.adventurebrother.config;

import com.caved_in.adventurecraft.adventurebrother.states.GameAction;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

public class CustomMessageCollection {
    @Element(name = "type",type= GameAction.class)
    public GameAction type;

    @ElementList(name = "messages",entry = "m",type = CustomMessage.class)
    public List<CustomMessage> messages = new ArrayList<>();
}
