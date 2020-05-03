package org.munoz_family.examples.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

	public static final Comparator<Card> SORT_DEFAULT = Comparator.comparing(Card::getRank).thenComparing(Card::getSuit)
			.reversed();
	public static final Comparator<Card> SORT_VALUE = Comparator.comparing(Card::getRankValue)
			.thenComparing(Card::getSuit).reversed();

	private Collection<Card> cards;
	private Comparator<Card> sortMethod = SORT_DEFAULT;

	public Deck() {
		initializeDeck();
		shuffleCards();
	}

	public Deck(int size) {
		initializeDeck();
		int maxDeckSize = getCards().size();
		if (size < 1 || size > maxDeckSize) {
			throw new RuntimeException("Invalid deck size. MUST be between 1 and " + maxDeckSize);
		}
		shuffleCards();
		dealCards(maxDeckSize - size);
	}

	public Deck(Collection<Card> cards) {
		setCards(cards);
	}

	public Deck(Card... cards) {
		this.cards = new LinkedHashSet<>(Arrays.asList(cards));
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

	public List<Card> removeCards(Card... cardsToRemove) {
		List<Card> cardsRemoved = new ArrayList<>();
		for (Card card : cardsToRemove) {
			if (cards.remove(card)) {
				cardsRemoved.add(card);
			}
		}
		return cardsRemoved;
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
		printDeck(getCards());
	}

	public void printDeck(Collection<Card> cards) {
		if (cards != null) {
			int cardNumber = 0;
			for (Card card : cards) {
				++cardNumber;
				System.out.println("Card " + cardNumber + " is a : " + card);
			}
		}
	}

	public void printDeck(Comparator<Card> comparator) {
		List<Card> sortedCards = new ArrayList<>(getCards());
		Collections.sort(sortedCards, comparator);
		printDeck(sortedCards);
	}

	public Card getHighCard() {
		List<Card> sortCards = new ArrayList<>(getCards());
		Collections.sort(sortCards, getSortMethod());
		return sortCards.get(0);
	}

	public Card getLowCard() {
		List<Card> sortCards = new ArrayList<>(getCards());
		Collections.sort(sortCards, getSortMethod().reversed());
		return sortCards.get(0);
	}

	public List<Card> getTopCards(int count) {
		return getCards().size() >= count
				? getCards().stream().sorted(getSortMethod()).limit(count).collect(Collectors.toList())
				: new ArrayList<Card>(0);
	}

	public List<Card> getTopFlush(int count) {

		Card topFlushCard = Stream.of(Suit.values()).flatMap(x -> getTopFlush(x, count).stream())
				.sorted(getSortMethod()).findFirst().orElse(null);

		return topFlushCard != null ? getTopFlush(topFlushCard.getSuit(), count) : new ArrayList<Card>(0);
	}

	public List<Card> getTopFlush(Suit suit, int count) {
		return getSuitCount(suit) >= count ? getCards().stream().sorted(getSortMethod())
				.filter(x -> x.getSuit() == suit).limit(count).collect(Collectors.toList()) : new ArrayList<Card>(0);
	}

	public List<Card> getTopStraight(int count, Rank highCard) {

		List<Card> straight = getTopStraight(getRankDistinct(), count);

		// return straight;
		return (highCard == null) || (highCard != null && straight.size() > 0 && straight.get(0).getRank() == highCard)
				? straight
				: new ArrayList<>(0);
	}

	public Collection<Card> getRankDistinct() {
		List<Card> straight = getCards().stream().collect(Collectors.groupingBy(Card::getRank)).entrySet().stream()
				.map(x -> x.getValue().get(0)).collect(Collectors.toList());
		return straight;
	}

	public List<Card> getTopStraightFlush(int count, Rank highCard) {
		List<Card> straightFlush = new ArrayList<>(0);
		Map<Suit, List<Card>> mapFlush = new HashMap<>();
		List<Card> allStraightFlushCards = new ArrayList<>();
		for (Suit suit : Card.Suit.values()) {
			List<Card> straightFlushTmp = getTopStraightFlush(suit, count);
			mapFlush.put(suit, straightFlushTmp);
			allStraightFlushCards.addAll(straightFlushTmp);
		}
		Deck tempDeck = new Deck(allStraightFlushCards);
		List<Card> topCard = (List<Card>) tempDeck.getTopFlush(1);
		straightFlush = topCard.size() > 0 ? mapFlush.get(topCard.get(0).getSuit()) : straightFlush;

		return (highCard == null)
				|| (highCard != null && straightFlush.size() > 0 && straightFlush.get(0).getRank() == highCard)
						? straightFlush
						: new ArrayList<>(0);
	}

	public List<Card> getTopStraightFlush(Suit suit, int count) {
		List<Card> straightFlush = new ArrayList<Card>();
		if (getSuitCount(suit) >= count) {
			straightFlush = getTopStraight(getSuitTopSorted(suit), count);
		}
		return straightFlush;
	}

	private List<Card> getTopStraight(Collection<Card> cards, int count) {
		List<Integer> sortedValues = cards.stream().flatMap(x -> Arrays.stream(x.getRank().getValues()))
				.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		List<Card> straight = new ArrayList<Card>(count);
		List<Card> largestStraightList = new ArrayList<>(count);
		Integer priorNum = Card.WILD_CARD, index = 0;
		for (Integer curNum : sortedValues) {
			boolean isSequence = 
					(priorNum == Card.WILD_CARD) || 
					(priorNum - curNum == 1);
			priorNum = curNum;
			if (!isSequence) {
				straight.clear();
			}
			straight.add( getCardByValue(cards, curNum)  );
			if (straight.size() > largestStraightList.size()) {
				largestStraightList.clear();
				largestStraightList.addAll(straight);
			}
			boolean isEnoughCardsLeft = count - largestStraightList.size() < sortedValues.size() - index ;
			boolean isCountMet = straight.size() >= count;
			if (!isEnoughCardsLeft || isCountMet) {
				break;
			}
			++index;
		}
		return largestStraightList.size() >= count ? largestStraightList : new ArrayList<Card>(0);
	}

	public int getSuitCount(Suit suit) {
		return (int) getCards().stream().filter(x -> x.getSuit() == suit).count();
	}

	public List<Card> getSuitTopSorted(Suit suit) {
		return getCards().stream().filter(x -> x.getSuit() == suit).sorted(getSortMethod())
				.collect(Collectors.toList());
	}

	public List<Card> getTopRankMatch(int count) {

		Entry<Rank, List<Card>> topRankMatch = getCards().stream().collect(Collectors.groupingBy(Card::getRank))
				.entrySet().stream().filter(x -> x.getValue().size() >= count)
				.sorted(Comparator.comparing(y -> ((Entry<Rank, List<Card>>) y).getValue().size())
						.thenComparing(z -> new Deck(((Entry<Rank, List<Card>>) z).getValue())
								.setSortMethod(this.getSortMethod()).getHighCard().getRankValue())
						.reversed())
				// .thenComparing(z -> ((Entry<Rank, List<Card>>) z).getKey()).reversed())
				.findFirst().orElse(null);

		return topRankMatch != null ? topRankMatch.getValue() : new ArrayList<>(0);
	}

	private static Card getCardByValue(Collection<Card> cards, int cardValue) {
		return cards.stream().filter(x -> Arrays.asList(x.getRank().getValues()).contains(cardValue)).findFirst()
				.orElse(null);
	}

	@Override
	public String toString() {
		return "Deck [cards=" + cards + "]";
	}

	// Getters & Setters...
	public Collection<Card> getCards() {
		return cards;
	}

	public void setCards(Collection<Card> cards) {
		this.cards = cards;
	}

	public Comparator<Card> getSortMethod() {
		return sortMethod;
	}

	public Deck setSortMethod(Comparator<Card> sortMethod) {
		this.sortMethod = sortMethod;
		return this;
	}

}
