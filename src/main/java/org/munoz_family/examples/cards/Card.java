package org.munoz_family.examples.cards;

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
	public int getRankValue() {
		return rank.getValue();
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
