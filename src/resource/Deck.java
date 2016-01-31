package resource;

import java.util.Collections;
import resource.Card.Rank;
import resource.Card.Suit;


import java.util.List;
import java.util.Stack;

public class Deck
{
	private static final CardFactory FACTORY = new CardFactory();
	private final Stack<Card> aCards = new Stack<>();
	// flyweight pattern
	
	
	/**
	 * 1.
	 */
	public Deck(){}
	
	// There are different ways of doing this (left as an exercise).
	public List<Card> getCards()
	{
		return Collections.unmodifiableList(aCards);
		// return new Deck(pDeck);
		// return new ArrayList<Card> (aCards);
		// return (List<Card>) aCards.clone();
		
	}
	
	
	
	/**
	 *  using the cardfactory to generate cards.
	 */
	public void shuffle()
	{
		aCards.clear();
		for( Suit suit : Suit.values() )
		{
			for( Rank rank : Rank.values())
			{
				aCards.push(FACTORY.getCard(rank, suit));
			}
		}
		Collections.shuffle(aCards);
	}
	
	public boolean isEmpty()
	{
		return aCards.isEmpty();
	}
	
	public Card draw()
	{
		return aCards.pop();
	}
}
