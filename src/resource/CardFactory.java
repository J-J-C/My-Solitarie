package resource;


import resource.Card.Rank;
import resource.Card.Suit;

public class CardFactory {
	
	private static final Card[][] cardSet = new Card[4][13];
	
	public Card getCard(Rank pRank, Suit pSuit){
		int rankIndex = pRank.ordinal();
		int suitIndex = pSuit.ordinal();
		if(cardSet[suitIndex][rankIndex] == null){
			cardSet[suitIndex][rankIndex] = new Card(pRank, pSuit);
		}
		return cardSet[suitIndex][rankIndex];
	}

}
