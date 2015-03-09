package com.caved_in.adventurecraft.core.user.upgrades.data;

import org.simpleframework.xml.Element;

//todo Implement syntax so I can initialize this like Mining<PlayerMineBlockEvent>
public class Mining {

	private final int COST_PER_LEVEL = 1500;
	private final double INCREASE_PER_LEVEL = 0.05;

	@Element(name = "mining-level")
	private int miningLevel = 1;

	private int getLootMultiplier() {
		return miningLevel;
	}
}
