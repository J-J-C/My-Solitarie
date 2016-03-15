package resource;


import resource.Card.Rank;
import resource.Card.Suit;

/**
 * 
 * @author JiajunChen
 *
 */
public class CardFactory 
{
	
	private static final Card[][] CARDFACTORY = new Card[Suit.values().length][Rank.values().length];
	
	/**
	 * 
	 * @param pRank rank of the card
	 * @param pSuit suit of the card
	 * @return the card
	 */
	public static Card getCard(Rank pRank, Suit pSuit)
	{
		int rankIndex = pRank.ordinal();
		int suitIndex = pSuit.ordinal();
		if(CARDFACTORY[suitIndex][rankIndex] == null)
		{
			CARDFACTORY[suitIndex][rankIndex] = new Card(pRank, pSuit);
		}
		return CARDFACTORY[suitIndex][rankIndex];
	}
	
	public static Card getCard(String pString){
		Card temp = null;
		for(Card[] cards :CARDFACTORY){
			for(Card aCard: cards){
				if(aCard.toString().equals(pString)){
					temp = aCard;
					break;
				}
			}
		}
		return temp;
		
	}

}
