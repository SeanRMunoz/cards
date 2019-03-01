package org.munoz_family.examples.cards;

import java.util.List;

import org.munoz_family.examples.cards.Card.Rank;
import org.munoz_family.examples.cards.Card.Suit;

public class App {

	public static void main(String[] args) {

		Deck cardDeck = new Deck();
		cardDeck.printDeck();

		System.out.println("1st delt card : " + cardDeck.dealCard());
		System.out.println("2nd delt card : " + cardDeck.dealCard());
		System.out.println("Cards remaining in deck : " + cardDeck.getCards().size());
		System.out.println("Shuffling...");
		cardDeck.shuffleCards();
		System.out.println("3nd delt card : " + cardDeck.dealCard());
		System.out.println("Cards remaining in deck : " + cardDeck.getCards().size());

		List<Card> deltCards = cardDeck.dealCards(5);
		System.out.println("Delt card count : " + deltCards.size());
		System.out.println("Delt cards : " + deltCards.toString());
		cardDeck.printDeck();
		
		Card addCard = new Card(Rank.ACE, Suit.SPADES);
		boolean addedCard = cardDeck.getCards().add(addCard);
		System.out.println("Able to add card : " + addCard + " > " + addedCard );
		
		int deckCardCount = Card.Rank.values().length * Card.Suit.values().length;
		System.out.println("Deck size : " + deckCardCount);
		
	}

}
