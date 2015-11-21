package com.caved_in.adventurecraft.adventurebrother.action;

import com.caved_in.adventurecraft.adventurebrother.BigBrother;
import com.caved_in.adventurecraft.adventurebrother.event.MinionLogActionEvent;
import com.caved_in.adventurecraft.adventurebrother.states.GameAction;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.time.DateUtils;
import lombok.ToString;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.*;

@Root(name = "action-log")
@ToString(of = {"minionId","actions"})
public class ActionLog {
    private UUID minionId;

    @Element(name = "minion-id")
    private String sMinionId;

    @ElementMap(name = "actions",entry = "a",value = "info",key = "action",keyType = GameAction.class,valueType = MinionActionCollection.class)
    private Map<GameAction, MinionActionCollection> actions = new HashMap<>();

    public ActionLog(UUID id) {
        this.minionId = id;
        this.sMinionId = id.toString();
    }

    public ActionLog(@Element(name = "minion-id")String minionId, @ElementMap(name = "actions",entry = "a",value = "info",key = "action",keyType = GameAction.class,valueType = MinionActionCollection.class)Map<GameAction,MinionActionCollection> actions) {
        this.sMinionId = minionId;
        this.minionId = UUID.fromString(minionId);
        this.actions = actions;
    }

    public void addAction(GameAction type, String message) {
        MinionAction action = new MinionAction();
        action.timestamp = System.currentTimeMillis();
        action.message = message;
        action.type = type;
        if (actions.containsKey(type)) {
            actions.get(type).actions.add(action);
        } else {
            MinionActionCollection collection = new MinionActionCollection();
            collection.actions.add(action);
            actions.put(type,collection);
        }

        MinionLogActionEvent event = new MinionLogActionEvent(BigBrother.getInstance().getUserManager().getUser(minionId),action);
        Plugins.callEvent(event);
    }

    public List<MinionAction> search(Collection<GameAction> actions, long timestampAfter) {
        if (actions.isEmpty()) {
            Chat.debug("No actions to search through");
            return null;
        }

        List<MinionAction> searchActions = new ArrayList<>();

        List<MinionAction> matchingActions = new ArrayList<>();

        for (GameAction action : actions) {
            if (!this.actions.containsKey(action)) {
                continue;
            }

            List<MinionAction> ma = this.actions.get(action).actions;

            if (ma == null || ma.isEmpty()) {
                continue;
            }

            matchingActions.addAll(ma);
        }

        //todo implement argument searches for the message of the action.

        for (MinionAction action : matchingActions) {
            if (action.timestamp >= timestampAfter) {
                searchActions.add(action);
                Chat.debug("Action " + action.toString() + " is after date search with " + DateUtils.formatDifference(action.timestamp - timestampAfter));
            } else {
                Chat.debug("Time difference in players action " + action.toString() + " is " + DateUtils.formatDifference(timestampAfter - action.timestamp));
            }
        }

        return searchActions;
    }

    public UUID getMinionId() {
        return minionId;
    }

}
