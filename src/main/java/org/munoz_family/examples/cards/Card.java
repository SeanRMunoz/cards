package org.munoz_family.examples.cards;

/**
 * Example of classic Playing Card class
 * 
 * @author Sean Munoz
 *
 */
public class Card {

	enum Rank {
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
		ACE,
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
		return "Card [rank=" + rank + ", suit=" + suit + "]";
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
	
}
