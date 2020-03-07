package org.munoz_family.examples.cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	private Collection<Card> cards;

	public Deck() {
		initializeDeck();
	}

	public Deck(Collection<Card> cards) {
		setCards(cards);
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

	public Card getHighCard() {
		List<Card> sortCards = new ArrayList<>( getCards() );
		Collections.sort( sortCards, Collections.reverseOrder() );
		return sortCards.get(0);
	}
	
	public Card getLowCard() {
		List<Card> sortCards = new ArrayList<>( getCards() );
		Collections.sort( sortCards );
		return sortCards.get(0);
	}
	
	public Collection<Card> getSuitTop( int count ) {
		
		Card topFlushCard = Stream.of(Suit.values())
			.flatMap( x -> getSuitTop(x, count).stream())
			.sorted(Collections.reverseOrder())
			.findFirst()
			.orElse(null);
		
		return topFlushCard != null
				? getSuitTop( topFlushCard.getSuit(), count )
				: new ArrayList<Card>(0);
	}
	
	public Collection<Card> getSuitTop( Suit suit, int count ) {
		return getSuitCount(suit) >= count 
				? getCards().stream()
						.sorted(Collections.reverseOrder())
						.filter(x -> x.getSuit() == suit)
						.limit(count)
						.collect(Collectors.toList())
				: new ArrayList<Card>(0);
	}

	public List<Card> getTopStraightFlush(int count) {
		List<Card> straightFlush = new ArrayList<>(0);
		Map<Suit, List<Card>> mapFlush = new HashMap<>();
		List<Card> allStraightFlushCards = new ArrayList<>();
		for (Suit suit : Card.Suit.values()) {
			List<Card> straightFlushTmp = getTopStraightFlush(suit, count);
			mapFlush.put(suit, straightFlushTmp);
			allStraightFlushCards.addAll(straightFlushTmp);
		}
		Deck tempDeck = new Deck(allStraightFlushCards);
		List<Card> topCard = (List<Card>) tempDeck.getSuitTop(1);
		straightFlush = topCard.size() > 0 ? mapFlush.get(topCard.get(0).getSuit()) : straightFlush;
		return straightFlush;
	}	
	
	public List<Card> getTopStraightFlush(Suit suit, int count) {
		List<Card> straightFlush = new ArrayList<Card>();
		if (getSuitCount(suit) >= count) {
			List<Card> sorted = getSuitTopSorted(suit);
			List<Card> largestStraightList = new ArrayList<>();
			for (int index = 0, priorNum = -1; index < sorted.size(); ++index) {
				Card curCard = sorted.get(index);
				int curNum = curCard.getRank().ordinal();
				boolean isSequence = (priorNum == -1) || (priorNum - curNum == 1);
				priorNum = curNum;
				if (!isSequence) {
					straightFlush.clear();
					straightFlush.add(curCard);
				} else {
					straightFlush.add(curCard);
				}
				if (straightFlush.size() > largestStraightList.size()) {
					largestStraightList.clear();
					largestStraightList.addAll(straightFlush);
				}
				boolean isNotEnoughCardsLeft = sorted.size() - index <= largestStraightList.size();
				boolean isCountMet = straightFlush.size() >= count;
				if (isNotEnoughCardsLeft || isCountMet) {
					break;
				}
			}
		}
		return straightFlush.size() >= count ? straightFlush : new ArrayList<Card>();
	}
	
	public int getSuitCount( Suit suit ) {
		return (int) getCards().stream().filter(x -> x.getSuit() == suit).count();
	}
	
	public List<Card> getSuitTopSorted( Suit suit ) {
		return getCards().stream()				
				.filter(x -> x.getSuit() == suit)
				.sorted(Collections.reverseOrder())
				.collect(Collectors.toList());
	}
	
/*
	public List<Card> getStraightTopTest( Suit suit, int count ) {
		return getCards().stream()				
				.filter(x -> x.getSuit() == suit)
				.sorted(Collections.reverseOrder())
				.filter(x -> {
					int curNum = x.getRank().ordinal();
					return Deck.isSequence(curNum);
					})
				.limit(count)
				.collect(Collectors.toList());
	}

	private static int priorNum = -1;
	private static boolean isSequence(int curNum) {
		boolean isSequence = (priorNum == -1) || (priorNum - curNum == 1);
		priorNum = curNum;
		return isSequence;
	}
*/
	
	public Collection<Card> getRankTop( int count ) {
		return getCards().size() >= count 
				? getCards().stream()
						.sorted(Collections.reverseOrder())
						.limit(count)
						.collect(Collectors.toList())
						: new ArrayList<Card>(0);
	}
	
	// Getters & Setters...
	public Collection<Card> getCards() {
		return cards;
	}

	public void setCards(Collection<Card> cards) {
		this.cards = cards;
	}

}
