package org.munoz_family.examples.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.munoz_family.examples.cards.PokerHandRanking.HandRank;

public class PokerDeckTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Deck.setPrintPrefix("   ");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTopHand_RoyalFlush() {

		PokerHand pokerHand = new PokerDeck().getTopHand();

		// Show results
		System.out.println( HandRank.ROYAL_FLUSH );
		pokerHand.print();

		// Confirm Ranking(s)
		assertEquals(HandRank.ROYAL_FLUSH, pokerHand.asHandRank());
		assertEquals(1, pokerHand.asHandRanks().size());

		// Confirm Card(s)/Order
		assertEquals(5, pokerHand.asCards().size());
	}

	@Test
	public void testGetTopHand_StraightAceLow() {
		
		Card lowCard, highCard; 
		HandRank testHandRank = HandRank.STRAIGHT;
		PokerHand pokerHand = new PokerDeck(  
						new Card(Rank.DUECE, Suit.SPADES),
		     highCard = new Card(Rank.FIVE, Suit.SPADES),
						new Card(Rank.THREE, Suit.SPADES),
						new Card(Rank.TEN, Suit.CLUBS),
			  lowCard = new Card(Rank.ACE, Suit.HEARTS),
						new Card(Rank.FOUR, Suit.CLUBS)
			).getTopHand();

		// Show results
		System.out.println( testHandRank + " (ACE low)" );
		pokerHand.print();

		// Confirm Ranking(s)
		assertEquals(testHandRank, pokerHand.asHandRank());
		assertEquals(1, pokerHand.asHandRanks().size());
		
		// Confirm Card(s)/Order
		assertEquals(5, pokerHand.asCards().size());
		assertEquals(highCard, pokerHand.asCards().get(0));
		assertEquals(lowCard, pokerHand.asCards().get(pokerHand.asCards().size()-1));
	}
	
	@Test
	public void testGetTopHand_StraightAceHigh() {
		
		Card lowCard, highCard; 
		HandRank testHandRank = HandRank.STRAIGHT;
		PokerHand pokerHand = new PokerDeck(  
						new Card(Rank.DUECE, Suit.SPADES),
						new Card(Rank.JACK, Suit.HEARTS),
			 highCard = new Card(Rank.ACE, Suit.HEARTS),
						new Card(Rank.THREE, Suit.SPADES),
						new Card(Rank.KING, Suit.SPADES),
		      lowCard = new Card(Rank.TEN, Suit.SPADES),
						new Card(Rank.QUEEN, Suit.CLUBS),
						new Card(Rank.FOUR, Suit.CLUBS)
			).getTopHand();

		// Show results
		System.out.println( testHandRank + " (ACE high)" );
		pokerHand.print();

		// Confirm Ranking(s)
		assertEquals(testHandRank, pokerHand.asHandRank());
		assertEquals(1, pokerHand.asHandRanks().size());
		
		// Confirm Card(s)/Order
		assertEquals(5, pokerHand.asCards().size());
		assertEquals(highCard, pokerHand.asCards().get(0));
		assertEquals(lowCard, pokerHand.asCards().get(pokerHand.asCards().size()-1));
	}
	
	@Test
	public void testGetTopHand_HighCard() {

		Card highCard; 
		HandRank testHandRank = HandRank.HIGH_CARD;
		PokerHand pokerHand = new PokerDeck(  
						   new Card(Rank.DUECE, Suit.SPADES),
						   new Card(Rank.JACK, Suit.HEARTS),
				highCard = new Card(Rank.QUEEN, Suit.HEARTS),
						   new Card(Rank.TEN, Suit.HEARTS),
						   new Card(Rank.THREE, Suit.CLUBS)
			).getTopHand();
		
		// Show results
		System.out.println( testHandRank );
		pokerHand.print();

		// Confirm Ranking(s)
		assertEquals("Hand rank", testHandRank, pokerHand.asHandRank());
		assertEquals("Hand", 1, pokerHand.asHandRanks().size());
		
		// Confirm Card(s)/Order
		assertEquals("Card count", 1, pokerHand.asCards().size());
		assertEquals("High card", highCard, pokerHand.asCards().get(0));
		
	}


	@Test
	public void testGetTopHand_FullHouse() {
		
		Card lowCard, highCard; 
		HandRank testHandRank = HandRank.FULL_HOUSE;
		PokerHand pokerHand = new PokerDeck(  
						new Card(Rank.DUECE, Suit.SPADES),
		      lowCard = new Card(Rank.KING, Suit.CLUBS),
		                new Card(Rank.KING, Suit.DIAMONDS),
						new Card(Rank.THREE, Suit.SPADES),
						new Card(Rank.TEN, Suit.CLUBS),
						new Card(Rank.ACE, Suit.CLUBS),
			 highCard = new Card(Rank.QUEEN, Suit.HEARTS),
						new Card(Rank.QUEEN, Suit.CLUBS),
						new Card(Rank.QUEEN, Suit.DIAMONDS),
						new Card(Rank.FOUR, Suit.CLUBS)
			).getTopHand();

		// Show results
		System.out.println( testHandRank + " (Queens over Kings)" );
		pokerHand.print();

		// Confirm Ranking(s)
		assertEquals("Hand rank", testHandRank, pokerHand.asHandRank());
		assertEquals("Hand", 1, pokerHand.asHandRanks().size());
		
		// Confirm Card(s)/Order
		assertEquals("Card count", 5, pokerHand.asCards().size());
		assertEquals("High card", highCard, pokerHand.asCards().get(0));
		assertEquals("Low card", lowCard, pokerHand.asCards().get(pokerHand.asCards().size()-1));
	}
	
	@Test
	public void testGetTopHand_Flush() {
		
		Card lowCard, highCard; 
		HandRank testHandRank = HandRank.FLUSH;
		PokerHand pokerHand = new PokerDeck(  
				new Card(Rank.DUECE, Suit.SPADES),
	 highCard = new Card(Rank.KING, Suit.CLUBS),
				new Card(Rank.THREE, Suit.SPADES),
				new Card(Rank.EIGHT, Suit.CLUBS),
				new Card(Rank.TEN, Suit.CLUBS),
				new Card(Rank.QUEEN, Suit.HEARTS),
				new Card(Rank.QUEEN, Suit.CLUBS),
				new Card(Rank.QUEEN, Suit.DIAMONDS),
 	  lowCard = new Card(Rank.FOUR, Suit.CLUBS)
				).getTopHand();
		
		// Show results
		System.out.println( testHandRank + " (Clubs)" );
		pokerHand.print();
		
		// Confirm Ranking(s)
		assertEquals("Hand rank", testHandRank, pokerHand.asHandRank());
		assertEquals("Hand", 1, pokerHand.asHandRanks().size());
		
		// Confirm Card(s)/Order
		assertEquals("Card count", 5, pokerHand.asCards().size());
		assertEquals("High card", highCard, pokerHand.asCards().get(0));
		assertEquals("Low card", lowCard, pokerHand.asCards().get(pokerHand.asCards().size()-1));
	}
	
	@Test
	public void testGetTopHand_TwoPair() {
		
		Card lowCard, highCard; 
		HandRank testHandRank = HandRank.TWO_PAIR;
		PokerHand pokerHand = new PokerDeck(  
				new Card(Rank.DUECE, Suit.SPADES),
				new Card(Rank.KING, Suit.DIAMONDS),
	 highCard = new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.THREE, Suit.SPADES),
				new Card(Rank.TEN, Suit.CLUBS),
	  lowCard = new Card(Rank.ACE, Suit.CLUBS),
				new Card(Rank.QUEEN, Suit.HEARTS),
				new Card(Rank.QUEEN, Suit.CLUBS),
				new Card(Rank.FOUR, Suit.CLUBS)
				).getTopHand();
		
		// Show results
		System.out.println( testHandRank + " (Kings & Queens + high card)" );
		pokerHand.print();
		
		// Confirm Ranking(s)
		assertEquals("Hand rank", testHandRank, pokerHand.asHandRank());
		assertEquals("Hand + high card", 2, pokerHand.asHandRanks().size());
		
		// Confirm Card(s)/Order
		assertEquals("Card count", 5, pokerHand.asCards().size());
		assertEquals("High card", highCard, pokerHand.asCards().get(0));
		assertEquals("Low card", lowCard, pokerHand.asCards().get(pokerHand.asCards().size()-1));
	}
	
	@Ignore
	@Test
	public void testGetTopFlushInt() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetTopFlushSuitInt() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetTopStraight() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetTopStraightFlushIntRank() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetTopStraightFlushSuitInt() {
		fail("Not yet implemented");
	}

}
