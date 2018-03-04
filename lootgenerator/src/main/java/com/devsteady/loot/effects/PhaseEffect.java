package com.devsteady.loot.effects;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PhaseEffect implements ItemEffect {

    private static PhaseEffect instance = null;

    public static PhaseEffect getInstance() {
        if (instance == null) {
            instance = new PhaseEffect();
        }
        return instance;
    }

    public PhaseEffect() {
        for (int i = MIN_COOLDOWN; i < MAX_COOLDOWN; i++) {
            indexedCooldowns.put(i, new Cooldown(i));
        }
    }

    @Override
    public String name() {
        return "Phase";
    }

    @Override
    public boolean verify(ItemStack item) {
        if (!Items.hasLore(item)) {
            return false;
        }

        return Items.loreContains(item, "- Phase:");
    }

    @Override
    public boolean onPlayerDamagePlayer(Player attacker, Player damaged, double damage) {
        return false;
    }

    @Override
    public boolean onPlayerBreakBlock(Player player, Block block) {
        return false;
    }

    @Override
    public boolean onPlayerDrop(Player player, Item item) {
        return false;
    }

    @Override
    public boolean onPlayerDamageEntity(Player player, Entity entity, double damage) {
        return false;
    }

    @Override
    public boolean onPlayerDamageLivingEntity(Player player, LivingEntity entity, double damage) {
        return false;
    }

    @Override
    public boolean onActivate(Player player) {
        ItemStack hand = player.getItemInHand();

        int cooldown = getCooldownTime(hand);

        if (isOnCooldown(cooldown, player)) {
            Chat.actionMessage(player, "&cPhase is still on Cooldown");
            return false;
        }

        int distance = getPhaseDistance(hand);

        //todo stop phasing through blocks / walls.
        Players.teleport(player, Players.getTargetLocation(player, distance));
        setOnCooldown(cooldown, player);
        return false;
    }

    private static final int MIN_COOLDOWN = 10;
    private static final int MAX_COOLDOWN = 30;

    private static final int MIN_PHASE_DISTANCE = 5;

    private static final int MAX_PHASE_DISTANCE = 15;

    @Override
    public void apply(ItemStack item) {
        if (verify(item)) {
            return;
        }

        int distance = NumberUtil.getRandomInRange(MIN_PHASE_DISTANCE, MAX_PHASE_DISTANCE);

        int cooldown = NumberUtil.getRandomInRange(MIN_COOLDOWN, MAX_COOLDOWN);

        Items.addLore(item, "- &ePhase: &aTele &c" + distance + "&a blocks on use. &e" + cooldown + " &asecond(s) cooldown");
    }

    public int getPhaseDistance(ItemStack item) {
        if (!verify(item)) {
            return -1;
        }

        String phaseLine = Items.getLoreLineContaining(item, "- Phase");

        Validate.notNull(phaseLine);

        phaseLine = StringUtil.stripColor(phaseLine).replace("- Phase:", "");
        String distanceLine = StringUtils.substringBetween(phaseLine, "Tele ", " blocks");
        return Integer.parseInt(distanceLine.replace(" ", ""));
    }

    public int getCooldownTime(ItemStack item) {
        if (!verify(item)) {
            return -1;
        }

        String cooldownLine = Items.getLoreLineContaining(item, "- Phase");
        Validate.notNull(cooldownLine);

        cooldownLine = StringUtil.stripColor(cooldownLine).replace("- Phase:", "");
        cooldownLine = StringUtils.substringBetween(cooldownLine, ". ", "second(s) cooldown");
        return Integer.parseInt(cooldownLine.replace(" ", ""));
    }


    private static Map<Integer, Cooldown> indexedCooldowns = new HashMap<>();

    public boolean isOnCooldown(int seconds, Player player) {
        if (!indexedCooldowns.containsKey(seconds)) {
            return false;
        }

        Cooldown cooldown = indexedCooldowns.get(seconds);
        return cooldown.isOnCooldown(player);
    }

    public void setOnCooldown(int seconds, Player player) {
        if (!indexedCooldowns.containsKey(seconds)) {
            return;
        }

        Cooldown cooldown = indexedCooldowns.get(seconds);
        cooldown.setOnCooldown(player);
    }
}
