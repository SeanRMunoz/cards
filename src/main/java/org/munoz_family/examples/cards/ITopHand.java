package org.munoz_family.examples.cards;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.munoz_family.examples.cards.PokerHand.HandRank;

public interface ITopHand {

	Map<HandRank, List<Deck>> getTopHand(Collection<Card> hand);
}
