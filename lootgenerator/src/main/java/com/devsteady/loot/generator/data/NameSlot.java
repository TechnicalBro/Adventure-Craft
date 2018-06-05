package com.devsteady.loot.generator.data;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum NameSlot {
	PREFIX("prefix"),
	BASE("base"),
	SUFFIX("suffix");

	@Getter
	private String name;
	NameSlot(String name) {
		this.name = name;
	}

	private static Map<String, NameSlot> namedSlots = new HashMap<>();

	static {
		for(NameSlot slot : EnumSet.allOf(NameSlot.class)) {
			namedSlots.put(slot.getName(),slot);
		}
	}

	public static NameSlot getSlot(String name) {
		return namedSlots.get(name.toLowerCase());
	}
}