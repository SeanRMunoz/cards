package org.munoz_family.examples.cards;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Example of classic Playing Card class
 * 
 * @author Sean Munoz
 *
 */
public class Card implements Comparable<Card> {

	public static final int WILD_CARD = -1; 

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
		JACK(10),
		QUEEN(10),
		KING(10),
		ACE(1,11),
		;
		private Integer [] values;

		Rank()
		{
			this.values = new Integer[] {this.ordinal() + 2};
		}
		
		Rank( Integer...values )
		{
			this.values = values;
		}
		
		public boolean hasMultipleValues()
		{
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
			return values != null ? Stream.of(values).min( Comparator.comparing( Integer::valueOf )).get() : 0;
		}
		public int getMaxValue() {
			return values != null ? Stream.of(values).max( Comparator.comparing( Integer::valueOf )).get() : 0;
		}
		public void setValue(int value) {
			this.values = new Integer[] {value};
		}
		
	}
	
	enum Suit {
		CLUBS,
		DIAMONDS,
		HEARTS,
		SPADES,
	}
	
	private Rank rank;
	private Suit suit;
	
	public Card(Rank rank, Suit suit) {
		super();
		if (rank == null) {
			throw new NullPointerException("ERROR: Must provide valid Rank when creating a card!");
		}
		if (suit == null) {
			throw new NullPointerException("ERROR: Must provide valid Suit when creating a card!");
		}
		this.rank = rank;
		this.suit = suit;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String valueString = this.getRank().hasMultipleValues()
				? "" + Stream.of( this.getRank().getValues() ).map( Object::toString ).collect( Collectors.joining(",") )
				: "" + this.getRank().getValue();
		
		return rank + "-" + suit + " (" + valueString + ")";
	}
	
	// Getters & Setters...
	public Rank getRank() {
		return rank;
	}
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	public Suit getSuit() {
		return suit;
	}
	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	@Override
	public int compareTo(Card o) {
		if ( o.equals( this ) ) {
			return 0;
		} 
		else if (this.rank.ordinal() != o.rank.ordinal()) {
			return this.rank.ordinal() - o.rank.ordinal();
		}
		else if (this.suit.ordinal() != o.suit.ordinal()) {
			return this.suit.ordinal() - o.suit.ordinal();
		}
		
		return 0;
	}
	
}
