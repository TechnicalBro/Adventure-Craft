package com.devsteady.loot.generator.data;

import com.devsteady.onyx.item.Attributes;
import com.devsteady.onyx.utilities.NumberUtil;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ToString(of = {"chance","attribute","minAmount","maxAmount","name","type","operations"})
public class RandomizedAttribute {

	private int chance = 100;

	private Attributes.Attribute attribute;

	private double minAmount;

	private double maxAmount;

	private String name;

	private Attributes.AttributeType type;

	private List<ChancedOperation> operations = new ArrayList<>();

	public RandomizedAttribute() {

	}

	public RandomizedAttribute chance(int chance) {
		this.chance = chance;
		return this;
	}

	public RandomizedAttribute type(Attributes.AttributeType type) {
		this.type = type;
		return this;
	}

	public RandomizedAttribute addOperation(int chance, Attributes.Operation operation) {
		operations.add(new ChancedOperation().chance(chance).operation(operation));
		return this;
	}

	public RandomizedAttribute amountRange(double min, double max) {
		this.minAmount = min;
		this.maxAmount = max;
		return this;
	}

	public RandomizedAttribute name(String name) {
		this.name = name;
		return this;
	}

	public Optional<Attributes.Attribute> getRandomizedAttribute() {
		if (!NumberUtil.percentCheck(chance) || operations.isEmpty()) {
			return Optional.empty();
		}

		Attributes.Attribute.Builder builder = Attributes.Attribute.newBuilder()
				.amount(NumberUtil.randomDouble(minAmount,maxAmount))
				.name(name).type(type);

		Attributes.Operation operation = null;

		for(ChancedOperation potentialOperation : operations) {
			if (!NumberUtil.percentCheck(potentialOperation.chance)) {
				continue;
			}

			operation = potentialOperation.operation;
		}

		if (operation == null) {
			return Optional.empty();
		}

		attribute = builder.operation(operation).build();

		return Optional.of(attribute);
	}

	@ToString(of = {"chance","operation"})
	private static class ChancedOperation {
		public int chance = 70;
		public Attributes.Operation operation;

		public Optional<Attributes.Operation> getOperation() {
			return Optional.ofNullable(NumberUtil.percentCheck(chance) ? operation : null);
		}

		public ChancedOperation chance(int amount) {
			this.chance = amount;
			return this;
		}

		public ChancedOperation operation(Attributes.Operation operation) {
			this.operation = operation;
			return this;
		}
	}

	private static class ChancedAttribute {
		public int chance = 10;
		public Attributes.AttributeType type;

		public Optional<Attributes.AttributeType> getType() {
			return Optional.ofNullable(NumberUtil.percentCheck(chance) ? type : null);
		}

		public ChancedAttribute chance(int chance) {
			this.chance = chance;
			return this;
		}

		public ChancedAttribute type(Attributes.AttributeType type) {
			this.type = type;
			return this;
		}
	}
}
