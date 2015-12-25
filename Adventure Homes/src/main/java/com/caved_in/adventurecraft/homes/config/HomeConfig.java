package com.caved_in.adventurecraft.homes.config;

import org.simpleframework.xml.Element;

public class HomeConfig {

    @Element(name = "require-permission")
    private boolean requirePermission = false;

    @Element(name = "permission-required")
    private String permissionRequired = "home.command";

    @Element(name = "allow-combat-teleport")
    private boolean combatTeleport = false;

    @Element(name = "combat-cooldown")
    private int combatTeleportCooldown = 10;

    @Element(name = "require-payment-for-teleport")
    private boolean requirePayment = true;

    @Element(name = "payment-required")
    private double paymentRequired = 350;

    @Element(name = "teleport-cooldown")
    private boolean teleportCooldown = true;

    @Element(name = "cooldown-amount")
    private int cooldownAmount = 120;

    @Element(name = "max-home-count")
    private int maximumHomeCount = 25;

    public HomeConfig(@Element(name = "require-permission") boolean requirePermission, @Element(name = "permission-required") String permissionRequired, @Element(name = "allow-combat-teleport") boolean combatTeleport, @Element(name = "combat-cooldown") int combatTeleportCooldown, @Element(name = "require-payment-for-teleport") boolean requirePayment, @Element(name = "payment-required") double paymentRequired, @Element(name = "teleport-cooldown") boolean teleportCooldown, @Element(name = "cooldown-amount") int cooldownAmount, @Element(name = "max-home-count") int maximumHomeCount) {
        this.requirePermission = requirePermission;
        this.permissionRequired = permissionRequired;
        this.combatTeleport = combatTeleport;
        this.combatTeleportCooldown = combatTeleportCooldown;
        this.requirePayment = requirePayment;
        this.paymentRequired = paymentRequired;
        this.teleportCooldown = teleportCooldown;
        this.cooldownAmount = cooldownAmount;
        this.maximumHomeCount = maximumHomeCount;
    }

    public HomeConfig() {

    }

    public int getMaximumHomeCount() {
        return maximumHomeCount;
    }

    public void setMaximumHomeCount(int maximumHomeCount) {
        this.maximumHomeCount = maximumHomeCount;
    }

    public void setRequirePermission(boolean requirePermission) {
        this.requirePermission = requirePermission;
    }

    public void setPermissionRequired(String permissionRequired) {
        this.permissionRequired = permissionRequired;
    }

    public void setCombatTeleport(boolean combatTeleport) {
        this.combatTeleport = combatTeleport;
    }

    public void setCombatTeleportCooldown(int combatTeleportCooldown) {
        this.combatTeleportCooldown = combatTeleportCooldown;
    }

    public void setRequirePayment(boolean requirePayment) {
        this.requirePayment = requirePayment;
    }

    public void setPaymentRequired(double paymentRequired) {
        this.paymentRequired = paymentRequired;
    }

    public void setTeleportCooldown(boolean teleportCooldown) {
        this.teleportCooldown = teleportCooldown;
    }

    public void setCooldownAmount(int cooldownAmount) {
        this.cooldownAmount = cooldownAmount;
    }

    public boolean isRequirePermission() {
        return requirePermission;
    }

    public String getPermissionRequired() {
        return permissionRequired;
    }

    public boolean isCombatTeleport() {
        return combatTeleport;
    }

    public int getCombatTeleportCooldown() {
        return combatTeleportCooldown;
    }

    public boolean isRequirePayment() {
        return requirePayment;
    }

    public double getPaymentRequired() {
        return paymentRequired;
    }

    public boolean isTeleportCooldown() {
        return teleportCooldown;
    }

    public int getCooldownAmount() {
        return cooldownAmount;
    }
}
