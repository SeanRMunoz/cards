package org.munoz_family.examples.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.munoz_family.examples.cards.Card.Rank;
import org.munoz_family.examples.cards.Card.Suit;

public class DeckTest {

	private Deck deck;
	private static final int rankCount = Rank.values().length;
	private static final int suitCount = Suit.values().length;
	private static final int countTotalCards = rankCount * suitCount;
	private static final Card cardFirstInDeck = new Card(Rank.values()[0], Suit.values()[0]);;
	private static final Card cardLastInDeck = new Card(Rank.values()[rankCount-1], Suit.values()[suitCount-1]);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		deck = new Deck();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeck() {
		assertNotNull(deck);
	}

	@Test
	public void testInitializeDeck() {
		// TEST no cards left in deck
		deck.getCards().clear();
		assertEquals(null, deck.dealCard());
		
		// TEST 1st card in non-shuffled deck
		deck.initializeDeck();
		assertEquals(cardFirstInDeck, deck.dealCard());
	}

	@Test
	public void testDealCard() {
		// TEST 1st card in non-shuffled deck
		assertEquals(cardFirstInDeck, deck.dealCard());
		
		// TEST dealing from null deck
		deck.setCards(null);
		assertNull(deck.dealCard());
	}

	@Test
	public void testDealCards() {
		// TEST deal all cards but last, then test last card in deck
		List<Card> deltCards = deck.dealCards(countTotalCards-1);
		assertEquals(countTotalCards-1, deltCards.size());
		assertEquals(cardLastInDeck, deck.dealCard());
		
		// TEST all cards dealt, should be none left in deck
		assertEquals(0, deck.dealCards(1).size());
		
		// TEST dealing from null deck
		deck.setCards(null);
		assertEquals(0, deck.dealCards(1).size());

		// TEST deal out ALL cards available, then one more
		deck.initializeDeck();
		assertEquals(countTotalCards, deck.dealCards(countTotalCards).size());
		assertEquals(0, deck.dealCards(1).size());
		
		// TEST deal out MORE cards than available
		deck.initializeDeck();
		assertEquals(0, deck.dealCards(countTotalCards+10).size());
	}

	@Test
	public void testShuffleCards() {
		// TEST 1st card in shuffled deck
		deck.shuffleCards();
		assertNotEquals(cardFirstInDeck, deck.dealCard());
		
		boolean isException = false;
		try {
			deck.setCards(null);
			deck.shuffleCards();
		} catch (Exception e) {
			isException = true;
		}
		assertFalse(isException);
	}

	@Test
	public void testPrintDeck() {
		boolean isException = false;
		try {
			System.out.println("*** Printing EMPTY Card Deck ***");
			deck.setCards(null);
			deck.printDeck();
		} catch (Exception e) {
			isException = true;
		}
		assertFalse(isException);
		
		System.out.println("*** Printing ORDERED Card Deck ***");
		deck.initializeDeck();
		deck.printDeck();
		
		System.out.println("*** Printing SHUFFLED Card Deck ***");
		deck.shuffleCards();
		deck.printDeck();
		
		System.out.println("*** Printing CUSTOM Card Deck ***");
		deck.setCards(new LinkedHashSet<>(Arrays.asList(cardFirstInDeck,cardLastInDeck)));
		deck.printDeck();
	}

	@Test
	public void testGetCards() {
		// Test new deck has 52 cards
		Collection<Card> cards = deck.getCards();
		assertEquals(countTotalCards, cards.size());
		assertTrue(cards.contains(new Card(Rank.ACE, Suit.SPADES)));
	}

	@Test
	public void testSetCards() {
		// TEST no cards left in deck
		deck.setCards(null);
		assertEquals(null, deck.dealCard());
		
		// TEST set custom deck and deal cards in expected order
		deck.setCards(new LinkedHashSet<>(Arrays.asList(cardFirstInDeck,cardLastInDeck)));
		assertEquals(cardFirstInDeck, deck.dealCard());
		assertEquals(cardLastInDeck, deck.dealCard());
	}

}
