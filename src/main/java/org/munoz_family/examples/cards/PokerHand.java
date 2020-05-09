package org.munoz_family.examples.cards;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.munoz_family.examples.cards.PokerHandRanking.HandRank;

public class PokerHand extends LinkedHashMap<HandRank, List<PokerDeck>> {

	private static final long serialVersionUID = 1L;

	public HandRank asHandRank() {
		return this.keySet().stream().findFirst().orElse(null);
	}

	public List<HandRank> asHandRanks() {
		return this.keySet().stream().collect(Collectors.toList());
	}

	public List<Card> asCards() {
		return this.values().stream()
				.flatMap(pokerDecks -> pokerDecks.stream())
				.flatMap(pokerDeck -> pokerDeck.getCards().stream())
				.collect(Collectors.toList());
	}
	
	public List<PokerDeck> asDecks() {
		return this.values().stream()
				.flatMap(pokerDecks -> pokerDecks.stream())
				.collect(Collectors.toList());
	}
	
	public void print() {
		this.values().stream()
				.flatMap(pokerDecks -> pokerDecks.stream())
				.forEach(Deck::printDeck);
	}
	
}
