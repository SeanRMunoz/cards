package org.munoz_family.examples.cards;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.munoz_family.examples.cards.Card.Rank;
import org.munoz_family.examples.cards.Card.Suit;

/**
 * Normal playing card 'deck' representation where every card must have a unique
 * rank and suit.
 * 
 * @author Sean Munoz
 *
 */
public class Deck {

	private Set<Card> cards;

	public Deck() {
		initializeDeck();
	}

	public void initializeDeck() {
		cards = new LinkedHashSet<>(Card.Rank.values().length * Card.Suit.values().length);
		for (Suit suit : Card.Suit.values()) {
			for (Rank rank : Card.Rank.values()) {
				Card card = new Card(rank, suit);
				cards.add(card);
			}
		}
	}

	public Card dealCard() {
		Card deltCard = null;
		if (cards != null && cards.iterator().hasNext()) {
			deltCard = cards.iterator().next();
			cards.remove(deltCard);
		}
		return deltCard;
	}

	public List<Card> dealCards(int cardCount) {
		List<Card> deltCards = new ArrayList<>();
		while (cardCount > 0 && cards != null && cardCount <= cards.size()) {
			deltCards.add(dealCard());
			--cardCount;
		}
		return deltCards;
	}
	
	public void shuffleCards() {
		if (cards != null) {
			// Shuffle cards by removing ALL cards into a List,
			// then add them back to the Set in random order
			List<Card> cardList = new ArrayList<>(cards.size());
			cardList.addAll(cards);
			cards.clear();
			while (!cardList.isEmpty()) {
				int cardNum = (int) (Math.random() * cardList.size());
				cards.add(cardList.remove(cardNum));
			}
		}
	}

	public void printDeck() {
		if (cards != null) {
			int cardNumber = 0;
			for (Card card : cards) {
				++cardNumber;
				System.out.println("Card " + cardNumber + " is a : " + card);
			}
		}
	}

	// Getters & Setters...
	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}

}
