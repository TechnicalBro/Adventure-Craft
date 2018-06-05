package com.devsteady.loot.generator.data;

import com.devsteady.loot.generator.settings.LootSettings;
import com.devsteady.onyx.utilities.ListUtils;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.Skip;
import com.devsteady.onyx.yml.YamlConfig;
import lombok.ToString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ToString(of = {"slot", "names"})
public class NameTable extends YamlConfig {

	@Path("slot")
	private String slot;

	@Path("potential-names")
	private List<ChancedName> names = new ArrayList<>();

	@Skip
	private LootSettings parent;

	public NameTable(NameSlot slot, List<ChancedName> names) {
		this.slot = slot.getName();
		this.names = names;
	}

	public NameTable parent(LootSettings parent) {
		this.parent = parent;
		return this;
	}

	public NameTable(NameSlot slot) {
		this.slot = slot.getName();
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
		return NameSlot.getSlot(slot);
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