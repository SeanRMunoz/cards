package org.munoz_family.examples.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Normal playing card 'deck' representation where every card must have a unique
 * rank and suit.
 * 
 * @author Sean Munoz
 *
 */
public class Deck {

	private static String PRINT_PREFIX = "";
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
		if (size == 0) {
			setCards( new LinkedHashSet<>() );
		} else {
			initializeDeck();
			int maxDeckSize = getCards().size();
			if (size < 1 || size > maxDeckSize) {
				throw new RuntimeException("Invalid deck size. MUST be between 0 and " + maxDeckSize);
			}
			shuffleCards();
			dealCards(maxDeckSize - size);
		}
	}

	public Deck(Collection<Card> cards) {
		setCards(new LinkedHashSet<>(cards));
	}

	public Deck(Card... cards) {
		this(Arrays.asList(cards));
	}

	public static Deck emptyDeck() {
		return new Deck(0);
	}
	
	public List<Card> asList() {
		return new ArrayList<>(getCards());
	}

	public void initializeDeck() {
		cards = new LinkedHashSet<>(Rank.values().length * Suit.values().length);
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
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
				System.out.println(getPrintPrefix() + "Card " + cardNumber + " is a : " + card);
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
				: Deck.emptyDeck().asList();
	}

	public Collection<Card> getRankDistinct() {
		List<Card> straight = getCards().stream().collect(Collectors.groupingBy(Card::getRank)).entrySet().stream()
				.map(x -> x.getValue().get(0)).collect(Collectors.toList());
		return straight;
	}

	public int getSuitCount(Suit suit) {
		return (int) getCards().stream().filter(x -> x.getSuit() == suit).count();
	}

	public List<Card> getSuitTopSorted(Suit suit) {
		return getCards().stream().filter(x -> x.getSuit() == suit).sorted(getSortMethod())
				.collect(Collectors.toList());
	}

	public List<Card> getTopRankMatch(int count) {

		Entry<Rank, List<Card>> topRankMatch = getCards().stream()
				.sorted(getSortMethod())
				.collect(Collectors.groupingBy(Card::getRank))
				.entrySet().stream().filter(x -> x.getValue().size() >= count)
				.sorted(Comparator.comparing(y -> ((Entry<Rank, List<Card>>) y).getValue().size())
						.thenComparing(z -> new Deck(((Entry<Rank, List<Card>>) z).getValue())
								.setSortMethod(this.getSortMethod()).getHighCard().getRankValue())
						.reversed())
				.findFirst().orElse(null);

		return topRankMatch != null ? topRankMatch.getValue() : new ArrayList<>(0);
	}

	public static Card getCardByValue(Collection<Card> cards, int cardValue) {
		return cards.stream().filter(x -> Arrays.asList(x.getRank().getValues()).contains(cardValue)).findFirst()
				.orElse(null);
	}

	public void sort() {
		setCards(getCards().stream().sorted(getSortMethod()).collect(Collectors.toList()));
	}

	public void sortReversed() {
		setCards(getCards().stream().sorted(getSortMethod().reversed()).collect(Collectors.toList()));
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

	public String getPrintPrefix() {
		return PRINT_PREFIX;
	}

	public static void setPrintPrefix(String printPrefix) {
		Deck.PRINT_PREFIX = printPrefix;
	}

}
