 package test;
 import static org.junit.Assert.assertEquals;


 import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

 import resource.Card;
 import resource.Card.Rank;
 import resource.Card.Suit;
 import resource.Deck;
public class TestDeck {
	
	private Deck aDeck;
	
	
	@Before 
	public void setUp()
	{
		aDeck = new Deck();
		aDeck.shuffle();
	}
	
	@Test 
	public void is52()
	{
		assertEquals(52, aDeck.getCards().size());
		assertTrue(aDeck.getCards().contains(new Card(Rank.ACE, Suit.DIAMONDS)));
		assertTrue(aDeck.getCards().contains(new Card(Rank.KING, Suit.HEARTS)));
		assertTrue(aDeck.getCards().contains(new Card(Rank.NINE, Suit.DIAMONDS)));
		assertTrue(aDeck.getCards().contains(new Card(Rank.ACE, Suit.CLUBS)));
		assertTrue(aDeck.getCards().contains(new Card(Rank.THREE, Suit.SPADES)));
		
	}

}
