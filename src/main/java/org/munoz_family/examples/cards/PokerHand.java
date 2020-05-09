package org.munoz_family.examples.cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PokerHand {

	public static final Comparator<Card> SORT_DEFAULT = Comparator.comparing(Card::getRank).thenComparing(Card::getSuit).reversed();
	
	public int 		cardCount;
	public RankType rankType;
	public SuitType suitType;
	public Rank 	rankSequenceTop;
	
	public PokerHand(int cardCount, RankType rankType, SuitType suitType) {
		super();
		this.cardCount = cardCount;
		this.rankType = rankType;
		this.suitType = suitType;
	}

	public PokerHand(int cardCount, RankType rankType, SuitType suitType, Rank rankSequenceTop) {
		super();
		this.cardCount = cardCount;
		this.rankType = rankType;
		this.suitType = suitType;
		this.rankSequenceTop = rankSequenceTop;
	}

	public enum RankType {
		RANK_SEQUENCE,
		RANK_SAME,
		RANK_ANY
	}
	
	public enum SuitType {
		SUIT_SAME,
		SUIT_ANY
	}
	
	public enum HandRank {

		ROYAL_FLUSH		( new PokerHand( 5, RankType.RANK_SEQUENCE, SuitType.SUIT_SAME, Rank.ACE ) ),
		STRAIGHT_FLUSH	( new PokerHand( 5, RankType.RANK_SEQUENCE, SuitType.SUIT_SAME ) ),
		FOUR_KIND		( new PokerHand( 4, RankType.RANK_SAME, SuitType.SUIT_ANY ) ),
		FULL_HOUSE		( new PokerHand[] { 
						  new PokerHand( 3, RankType.RANK_SAME, SuitType.SUIT_ANY ), 
						  new PokerHand( 2, RankType.RANK_SAME, SuitType.SUIT_ANY ) } ),
		FLUSH			( new PokerHand( 5, RankType.RANK_ANY, SuitType.SUIT_SAME ) ),
		STRAIGHT		( new PokerHand( 5, RankType.RANK_SEQUENCE, SuitType.SUIT_ANY ) ),
		THREE_KIND		( new PokerHand( 3, RankType.RANK_SAME, SuitType.SUIT_ANY ) ),
		TWO_PAIR		( new PokerHand[] { 
						  new PokerHand( 2, RankType.RANK_SAME, SuitType.SUIT_ANY ), 
						  new PokerHand( 2, RankType.RANK_SAME, SuitType.SUIT_ANY ) } ),
		PAIR			( new PokerHand( 2, RankType.RANK_SAME, SuitType.SUIT_ANY ) ),
		HIGH_CARD		( new PokerHand( 1, RankType.RANK_ANY, SuitType.SUIT_ANY ) ),
		;
		
		private PokerHand[] pokerHands;

		private HandRank(PokerHand[] pokerHands) {
			this.pokerHands = pokerHands;
		}
		
		private HandRank(PokerHand pokerHand) {
			this.pokerHands = new PokerHand[] {pokerHand};
		}
		
		public PokerHand[] getPokerHands() {
			return pokerHands;
		}

		public void setPokerHands(PokerHand[] pokerHands) {
			this.pokerHands = pokerHands;
		}

		public PokerHand getPokerHand() {
			return pokerHands != null && pokerHands.length > 0 ? pokerHands[0] : null;
		}

	}

	public static Map<HandRank, List<PokerDeck>> getTopHand(Collection<Card> cards, int sizeHand) {
		sizeHand = (sizeHand <= 0 || sizeHand > cards.size()) ? cards.size() : sizeHand;
		int unusedCardCount = cards.size() - sizeHand;
		Map<HandRank, List<PokerDeck>> topHands = new LinkedHashMap<>();
		PokerDeck unplayedCards = new PokerDeck(cards);
		List<Card> totalPlayedCards = new ArrayList<>();
		for (HandRank handRank : HandRank.values()) {
			if (unplayedCards.getCards().size() - unusedCardCount <= 0) {
				// Not more cards to play
				break;
			}
			totalPlayedCards.clear();
			for (PokerHand pokerHand : handRank.pokerHands) {
				if (unplayedCards.getCards().size() - unusedCardCount < pokerHand.cardCount ) {
					// Not enough cards left to satisfy this hand
					continue;
				}
				List<Card> playedCards = null;
				switch (pokerHand.rankType) {
				case RANK_SEQUENCE:
					if (pokerHand.suitType == SuitType.SUIT_SAME) {
						// ROYAL_FLUSH or STRAIGHT_FLUSH
						playedCards = unplayedCards.getTopStraightFlush(pokerHand.cardCount, pokerHand.rankSequenceTop);
					} else {
						// STRAIGHT
						playedCards = unplayedCards.getTopStraight(pokerHand.cardCount, pokerHand.rankSequenceTop);
					}
					break;
					
				case RANK_SAME:
					if (pokerHand.suitType == SuitType.SUIT_ANY) {
						// FOUR_KIND, FULL_HOUSE, THREE_KIND, TWO_PAIR, PAIR
						playedCards = unplayedCards.getTopRankMatch(pokerHand.cardCount);
						
					} else {
						// Invalid: RANK_SAME && SUIT_SAME
						throw new RuntimeException("Illegal poker hand defined!");
					}
					break;

				case RANK_ANY:
					if (pokerHand.suitType == SuitType.SUIT_SAME) {
						// FLUSH 
						playedCards = unplayedCards.getTopFlush(pokerHand.cardCount);
					} else {
						// HIGH_CARD
						playedCards = unplayedCards.getTopCards(pokerHand.cardCount);
					}
					break;
				default:
					// Invalid: Unknown RankType
					throw new RuntimeException("Unknown RankType!");
				}
				if (playedCards == null || playedCards.size() <= 0) {
					topHands.remove(handRank);
					unplayedCards.getCards().addAll(totalPlayedCards);
					totalPlayedCards.clear();
					break;
				}
				PokerDeck playedHand = new PokerDeck(playedCards);
				totalPlayedCards.addAll(playedCards);
				List<PokerDeck> playedHands = topHands.get(handRank);
				if (playedHands == null) {
					playedHands = new ArrayList<>();
					topHands.put(handRank, playedHands);
				}
				playedHands.add(playedHand);
				unplayedCards.getCards().removeAll(playedCards);
			} // foreach hand within a poker hand
		} // foreach poker hand
		return topHands;
	}

}
