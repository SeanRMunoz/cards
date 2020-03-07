package org.munoz_family.examples.cards;

import java.util.ArrayList;
import java.util.Collections;
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
		System.out.println("3rd delt card : " + cardDeck.dealCard());
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
		
		Deck sortDeck = new Deck();
		List<Card> sortCards = new ArrayList<>( sortDeck.getCards() );
		Collections.sort( sortCards, Collections.reverseOrder() );
		sortDeck.setCards(sortCards);
		sortDeck.printDeck();

		System.out.println("----------------------------");

		Deck randomDeck = new Deck();
		randomDeck.shuffleCards();
		Deck pokerHand = new Deck( randomDeck.dealCards(13) );
//		Deck pokerHand = randomDeck;
		pokerHand.printDeck();
		System.out.println("High card    : " + pokerHand.getHighCard());
		System.out.println("Low  card    : " + pokerHand.getLowCard());
		System.out.println("Top 4 SPADES : " + pokerHand.getSuitTop(Suit.SPADES, 4));
		System.out.println("Top 2 Cards  : " + pokerHand.getRankTop(2));
		System.out.println("Top 3 FLUSH  : " + pokerHand.getSuitTop(3));
		System.out.println("Top 4 CLUBS  : " + pokerHand.getSuitTop(Suit.CLUBS, 4));
		System.out.println("Top 3 CLUBS Straight Flush : " + pokerHand.getTopStraightFlush(Suit.CLUBS, 3));
		System.out.println("Top 4 Straight Flush : " + pokerHand.getTopStraightFlush(4));
		
	}
	
}
