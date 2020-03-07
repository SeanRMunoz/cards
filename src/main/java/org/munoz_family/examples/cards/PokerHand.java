package org.munoz_family.examples.cards;

import java.util.Collection;

import org.munoz_family.examples.cards.Card.Rank;

public class PokerHand {

	public int 		cardCount;
	public RankType rankType;
	public SuitType suitType;
	public Rank 	rankSequenceBegin;
	public Rank 	rankSequenceEnd;
	
	public PokerHand(int cardCount, RankType rankType, SuitType suitType) {
		super();
		this.cardCount = cardCount;
		this.rankType = rankType;
		this.suitType = suitType;
	}

	public PokerHand(int cardCount, RankType rankType, SuitType suitType, Rank rankSequenceBegin,
			Rank rankSequenceEnd) {
		super();
		this.cardCount = cardCount;
		this.rankType = rankType;
		this.suitType = suitType;
		this.rankSequenceBegin = rankSequenceBegin;
		this.rankSequenceEnd = rankSequenceEnd;
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

		ROYAL_FLUSH		( new PokerHand( 5, RankType.RANK_SEQUENCE, SuitType.SUIT_SAME, Card.Rank.TEN, Card.Rank.ACE ) ),
		STRAIGHT_FLUSH	( new PokerHand( 5, RankType.RANK_SEQUENCE, SuitType.SUIT_SAME ) ),
		FOUR_KIND		( new PokerHand( 4, RankType.RANK_SAME, SuitType.SUIT_ANY ) ),
		FULL_HOUSE		( new PokerHand[] { new PokerHand( 3, RankType.RANK_SAME, SuitType.SUIT_ANY ), 
											new PokerHand( 2, RankType.RANK_SAME, SuitType.SUIT_ANY ) } ),
		FLUSH			( new PokerHand( 5, RankType.RANK_ANY, SuitType.SUIT_SAME ) ),
		STRAIGHT		( new PokerHand( 5, RankType.RANK_SEQUENCE, SuitType.SUIT_ANY ) ),
		THREE_KIND		( new PokerHand( 3, RankType.RANK_SAME, SuitType.SUIT_ANY ) ),
		TWO_PAIR		( new PokerHand[] { new PokerHand( 2, RankType.RANK_SAME, SuitType.SUIT_ANY ), 
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

		public Collection<Card> getHand(HandRank handRank, Collection<Card> cards) {
			Collection<Card> handCards = null;
			PokerHand pokerHand = handRank.getPokerHand(); 
			if(cards.size() < pokerHand.cardCount)
				return handCards;
			
			
			return handCards;			
		}
		
	}

}
