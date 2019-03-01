package org.munoz_family.examples.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.munoz_family.examples.cards.Card;
import org.munoz_family.examples.cards.Card.Rank;
import org.munoz_family.examples.cards.Card.Suit;

public class CardTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testCard_NullParams() {
		Card card = null;
		try {
			// Test null for BOTH Rank & Suit
			card = new Card(null, null);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().toLowerCase().contains("must provide valid"));
		}
		assertNull(card);
		
		try {
			// Test null for Suit
			card = new Card(Rank.QUEEN, null);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().toLowerCase().contains("suit"));
		}
		assertNull(card);
		
		try {
			// Test null for Rank
			card = new Card(null, Suit.HEARTS);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().toLowerCase().contains("rank"));
		}
		assertNull(card);
	}
	
	@Test
	public void testCard_ValidParams() {
		Card card = null;
		try {
			card = new Card(Rank.QUEEN, Suit.HEARTS);
		} catch (Exception e) {
			assertFalse(e instanceof NullPointerException);
		}
		assertNotNull(card);
		assertEquals(Rank.QUEEN, card.getRank());
		assertEquals(Suit.HEARTS, card.getSuit());
	}

}
