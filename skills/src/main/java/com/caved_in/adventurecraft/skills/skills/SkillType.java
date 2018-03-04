package com.caved_in.adventurecraft.skills.skills;

import com.caved_in.commons.item.Items;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.simpleframework.xml.Root;

import java.util.List;

public enum SkillType {
    MINING("Mining", Items.getMaterialData(Material.IRON_PICKAXE, 0), Lists.newArrayList("&eSalvage and Mine!", "&eBrings plenty of perks!")),
    MELEE("Melee", Items.getMaterialData(Material.IRON_SWORD, 0), Lists.newArrayList("&eSlash & Smash your enemies!", "&eIncludes Swords & Axes")),
    TAMING("Taming", Items.getMaterialData(Material.LEASH, 0), Lists.newArrayList("&eTame beasts to fight alongside you")),
    MAGIC("Magic", Items.getMaterialData(Material.BLAZE_POWDER, 0), Lists.newArrayList("&eBlast your foes with spells", "&eEnchant yourself with many buffs", "&eAid your friends with power ups", "&eEverything is Magical!")),
    AGILITY("Agility", Items.getMaterialData(Material.LEATHER_BOOTS, 0), Lists.newArrayList("&eIncrease your physique, and stamina!")),
    STEALTH("Stealth", Items.getMaterialData(Material.FEATHER, 0), Lists.newArrayList("&eStrike foes from the shadows!")),
    ARCHERY("Archery", Items.getMaterialData(Material.BOW, 0), Lists.newArrayList("&eBecome a master of the Bow and Arrow!"));

    private String skillName;
    private MaterialData iconData;
    private List<String> description;

    SkillType(String name, MaterialData icon, List<String> description) {
        this.skillName = name;
        this.iconData = icon;
        this.description = description;
    }

    @Override
    public String toString() {
        return skillName;
    }

    public MaterialData getIcon() {
        return iconData;
    }

    public List<String> getDescription() {
        return description;
    }
}
