package com.caved_in.adventurecraft.adventurebrother.action;

import lombok.ToString;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

@ToString(of = "actions")
public class MinionActionCollection {
    @ElementList(name = "actions",type = MinionAction.class,entry = "action")
    public List<MinionAction> actions = new ArrayList<>();
}
