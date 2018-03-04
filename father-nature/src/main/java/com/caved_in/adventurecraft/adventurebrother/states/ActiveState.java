package com.caved_in.adventurecraft.adventurebrother.states;

import com.caved_in.adventurecraft.adventurebrother.BigBrother;
import com.caved_in.adventurecraft.adventurebrother.action.ActionLogManager;
import com.caved_in.adventurecraft.adventurebrother.action.MinionAction;
import com.caved_in.adventurecraft.adventurebrother.event.MinionLogActionEvent;
import com.caved_in.adventurecraft.adventurebrother.user.Minion;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.MiniGameState;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.BasicTicker;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ActiveState extends MiniGameState {

    @Override
    public void update() {
        ActionLogManager.getInstance().tick();
    }

    @Override
    public int id() {
        return 2;
    }

    @Override
    public boolean switchState() {
        return !Players.isOnline(1);
    }

    @Override
    public int nextState() {
        return 1;
    }

    @Override
    public void setup() {
        debug("Big Brother is in the air! Here we go privacy invasion! HEYYYOOOOO");
        setSetup(true);
    }

    @Override
    public void destroy() {

    }

    private Minion getMinion(Player player) {
        return BigBrother.getInstance().getUserManager().getUser(player);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private String getTimestampString() {
        return sdf.format(new Date());
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();

        Minion minion = getMinion(p);
        //todo implement formatting.
        minion.getActionLog().addAction(GameAction.CHAT, message);
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        Player killed = e.getEntity();
        Minion deadMinion = getMinion(killed);

        if (killed.getKiller() == null) {
            //todo implement location that the player was killed @
            deadMinion.getActionLog().addAction(GameAction.DEATH, e.getDeathMessage());
            return;
        }

        Player killer = killed.getKiller();
        Minion killerMinion = getMinion(killer);

        killerMinion.getActionLog().addAction(GameAction.KILL_PLAYER, "Killed Player " + killed.getName() + " @ " + Messages.locationCoords(killed.getLocation()) + " | " + e.getDeathMessage());
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (entity instanceof Player) {
            return;
        }

        if (entity.getKiller() == null) {
            return;
        }

        Player killer = entity.getKiller();

        Minion killerMinion = getMinion(killer);
        killerMinion.getActionLog().addAction(GameAction.KILL_ENTITY, "Slayed entity " + entity.getType().name() + " @ " + Messages.locationCoords(entity.getLocation()) + " | Drops[" + StringUtil.joinString(e.getDrops().stream().map(Items::getName).collect(Collectors.toList()), ", ", 0));
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }


        Player p = e.getPlayer();
        Block block = e.getBlock();

        Minion minion = getMinion(p);

        ItemStack hand = null;

        if (!Players.handIsEmpty(p)) {
            hand = p.getItemInHand();
        }

        minion.getActionLog().addAction(GameAction.BREAK_BLOCK, String.format("%s @ %s using %s", block.getType().name(), Messages.locationCoords(block.getLocation()), hand == null ? "their Hand" : Items.getName(hand)));
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Player p = e.getPlayer();
        Block block = e.getBlock();

        ItemStack hand = null;
        if (!Players.handIsEmpty(p)) {
            hand = p.getItemInHand();
        }

        Minion minion = getMinion(p);

        minion.getActionLog().addAction(GameAction.PLACE_BLOCK, String.format("Type %s [%s] @ %s", block.getType().name(), Items.getName(hand), Messages.locationCoords(block.getLocation())));
    }

    @EventHandler
    public void onPerformCommand(PlayerCommandPreprocessEvent e) {
        if (e.isCancelled()) {
            return;
        }


        String message = e.getMessage();
        Player p = e.getPlayer();

        Minion minion = getMinion(p);

        minion.getActionLog().addAction(GameAction.PERFORM_COMMAND,message);
    }

    @EventHandler
    public void onPlayerCraftItemEvent(CraftItemEvent e) {
        if (e.isCancelled()) {
            return;
        }

        List<HumanEntity> entities = e.getViewers();
        ItemStack crafted = e.getRecipe().getResult();

        List<Player> players = entities.stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).collect(Collectors.toList());

        if (players.isEmpty()) {
            return;
        }

        for(Player player : players) {
            Minion minion = getMinion(player);

            minion.getActionLog().addAction(GameAction.CRAFT_ITEM,StringUtil.joinString(Messages.itemInfo(crafted),"\r\n"));
        }
    }


    @EventHandler
    public void onMinionLogEvent(MinionLogActionEvent e) {
        MinionAction action = e.getAction();
        Minion minion = e.getMinion();

        for(Minion listener : BigBrother.getInstance().getUserManager().allUsers()) {
            String message = "[&7" + minion.getName() + "&r] " + action.toString();

            if (listener.isMonitoringMinion(minion)) {
                listener.message(message);
                continue;
            }

            if (listener.isInBigBrotherChatChannel()) {
                listener.message(message);
            }
        }
    }

}
