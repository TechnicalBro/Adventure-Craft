package com.caved_in.adventurecraft.loot.generator.data;

import com.caved_in.adventurecraft.loot.generator.settings.LootSettings;
import com.caved_in.commons.utilities.ListUtils;
import lombok.ToString;
import org.apache.commons.lang.Validate;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Root(name = "name-settings")
@ToString(of = {"slot", "names"})
public class NameTable {

	@Attribute(name = "slot")
	private NameSlot slot;

	@ElementList(name = "potential-names", entry = "name",type = ChancedName.class)
	private List<ChancedName> names = new ArrayList<>();

	private LootSettings parent;

	public NameTable(@Attribute(name = "slot") NameSlot slot, @ElementList(name = "potential-names", entry = "name",type = ChancedName.class) List<ChancedName> names) {
		this.slot = slot;
		this.names = names;
	}

	public NameTable parent(LootSettings parent) {
		this.parent = parent;
		return this;
	}

	public NameTable(NameSlot slot) {
		this.slot = slot;
	}

	public NameTable add(String name) {
		names.add(new ChancedName(100,name));
		return this;
	}

	public NameTable add(int chance, String name) {
		names.add(new ChancedName(chance,name));
		return this;
	}

	public NameTable add(ChancedName... names) {
		Collections.addAll(this.names,names);
		return this;
	}

	public List<String> getNames() {
		return names.stream().map(ChancedName::getName).collect(Collectors.toList());
	}

	public List<ChancedName> getChancedNames() {
		return names;
	}

	public NameSlot getSlot() {
		return slot;
	}

	public LootSettings parent() {
		return parent;
	}

	public ChancedName getRandomName() {
		if (names.isEmpty()) {
			return null;
		}

		return ListUtils.getRandom(names);
	}
}