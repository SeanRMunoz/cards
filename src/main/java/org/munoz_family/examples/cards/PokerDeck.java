package org.munoz_family.examples.cards;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.munoz_family.examples.cards.PokerHand.HandRank;

public class PokerDeck extends Deck {

	public PokerDeck() {
		super();
	}

	public PokerDeck(Card... cards) {
		super(cards);
	}

	public PokerDeck(Collection<Card> cards) {
		super(cards);
	}

	public PokerDeck(int size) {
		super(size);
	}

	public Map<HandRank, List<PokerDeck>> getTopHand() {
		return PokerHand.getTopHand(this.getCards(), 5);
	}

	public List<Card> getTopFlush(int count) {

		Card topFlushCard = Stream.of(Suit.values())
				.flatMap(x -> getTopFlush(x, count).stream())
				.sorted(getSortMethod())
				.findFirst()
				.orElse(null);

		return topFlushCard != null 
				? getTopFlush(topFlushCard.getSuit(), count) 
				: Deck.emptyDeck().asList();
	}

	public List<Card> getTopFlush(Suit suit, int count) {

		return getSuitCount(suit) >= count 
				? getCards().stream().sorted(getSortMethod())
						.filter(x -> x.getSuit() == suit)
						.limit(count).collect(Collectors.toList()) 
				: Deck.emptyDeck().asList();
	}

	public List<Card> getTopStraight(int count, Rank highCard) {

		List<Card> straight = getTopStraight(getRankDistinct(), count);

		return (highCard == null) || 
			   (highCard != null && straight.size() > 0 && straight.get(0).getRank() == highCard)
				? straight
				: Deck.emptyDeck().asList();
	}

	public List<Card> getTopStraightFlush(int count, Rank highCard) {
		List<Card> straightFlush = Deck.emptyDeck().asList();
		Map<Suit, List<Card>> mapFlush = new HashMap<>();
		List<Card> allStraightFlushCards = Deck.emptyDeck().asList();
		for (Suit suit : Suit.values()) {
			List<Card> straightFlushTmp = getTopStraightFlush(suit, count);
			mapFlush.put(suit, straightFlushTmp);
			allStraightFlushCards.addAll(straightFlushTmp);
		}
		PokerDeck tempDeck = new PokerDeck(allStraightFlushCards);
		List<Card> topCard = (List<Card>) tempDeck.getTopFlush(1);
		straightFlush = topCard.size() > 0 ? mapFlush.get(topCard.get(0).getSuit()) : straightFlush;

		return (highCard == null)
				|| (highCard != null && straightFlush.size() > 0 && straightFlush.get(0).getRank() == highCard)
						? straightFlush
						: Deck.emptyDeck().asList();
	}

	public List<Card> getTopStraightFlush(Suit suit, int count) {
		List<Card> straightFlush = Deck.emptyDeck().asList();
		if (getSuitCount(suit) >= count) {
			straightFlush = getTopStraight(getSuitTopSorted(suit), count);
		}
		return straightFlush;
	}

	private List<Card> getTopStraight(Collection<Card> cards, int count) {
		List<Integer> sortedValues = cards.stream().flatMap(x -> Arrays.stream(x.getRank().getValues()))
				.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		List<Card> straight = Deck.emptyDeck().asList();
		List<Card> largestStraightList = Deck.emptyDeck().asList();
		Integer priorNum = Card.WILD_CARD, index = 0;
		for (Integer curNum : sortedValues) {
			boolean isSequence = (priorNum == Card.WILD_CARD) || (priorNum - curNum == 1);
			priorNum = curNum;
			if (!isSequence) {
				straight.clear();
			}
			straight.add(getCardByValue(cards, curNum));
			if (straight.size() > largestStraightList.size()) {
				largestStraightList.clear();
				largestStraightList.addAll(straight);
			}
			boolean isEnoughCardsLeft = count - largestStraightList.size() < sortedValues.size() - index;
			boolean isCountMet = straight.size() >= count;
			if (!isEnoughCardsLeft || isCountMet) {
				break;
			}
			++index;
		}
		return largestStraightList.size() >= count ? largestStraightList : Deck.emptyDeck().asList();
	}

}
