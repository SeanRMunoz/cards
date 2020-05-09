package org.munoz_family.examples.cards;

import java.util.Comparator;
import java.util.stream.Stream;

public enum Rank {
	DUECE,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT,
	NINE,
	TEN,
	JACK,
	QUEEN,
	KING,
	ACE( KING.getValue()+1, DUECE.getValue()-1 ), // List multiples in order highest worth first
//	ACE( DUECE.getValue()-1, KING.getValue()+1 ), // List multiples in order highest worth first
	;

	private Integer[] values;

	Rank() {
		this.values = new Integer[] { this.ordinal() + 2 };
	}

	Rank(Integer... values) {
		this.values = values;
	}

	public boolean hasMultipleValues() {
		return values != null & values.length > 1;
	}

	public Integer[] getValues() {
		return values;
	}

	public void setValues(Integer[] values) {
		this.values = values;
	}

	public int getValue() {
		return values != null && values.length > 0 ? values[0] : 0;
	}

	public int getMinValue() {
		return values != null ? Stream.of(values).min(Comparator.comparing(Integer::valueOf)).get() : 0;
	}

	public int getMaxValue() {
		return values != null ? Stream.of(values).max(Comparator.comparing(Integer::valueOf)).get() : 0;
	}

	public void setValue(int value) {
		this.values = new Integer[] { value };
	}

}
