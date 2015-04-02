package com.caved_in.adventurecraft.core.user.upgrades;

import com.caved_in.adventurecraft.core.AdventureCore;
import org.simpleframework.xml.Element;

//todo Implement syntax so I can initialize this like Mining<PlayerMineBlockEvent>
public class MiningUpgrade {

	private final int COST_PER_LEVEL = 1500;
	private final double INCREASE_PER_LEVEL = 0.25;

	@Element(name = "mining-level")
	private int miningLevel = 1;

	private int getLootMultiplier() {
		return miningLevel;
	}

	public MiningUpgrade() {

	}

	public int getLevel() {
		return miningLevel;
	}

	public boolean isMaxed() {
		return miningLevel >= AdventureCore.Properties.MAX_UPGRADE_LEVEL;
	}
}
