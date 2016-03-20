package test;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import resource.Card;
import resource.Card.Rank;
import resource.Card.Suit;



public class TestCard {
	
	@Test
	public void testToString()
	{
		assertEquals("ACE of CLUBS", new Card(Rank.ACE, Suit.CLUBS).toString());
		assertEquals("TWO of CLUBS", new Card(Rank.TWO, Suit.CLUBS).toString());
		assertEquals("KING of CLUBS", new Card(Rank.KING, Suit.CLUBS).toString());
		assertEquals("ACE of DIAMONDS", new Card(Rank.ACE, Suit.DIAMONDS).toString());
		assertEquals("TWO of DIAMONDS", new Card(Rank.TWO, Suit.DIAMONDS).toString());
		assertEquals("KING of DIAMONDS", new Card(Rank.KING, Suit.DIAMONDS).toString());
		assertEquals("ACE of HEARTS", new Card(Rank.ACE, Suit.HEARTS).toString());
		assertEquals("TWO of HEARTS", new Card(Rank.TWO, Suit.HEARTS).toString());
		assertEquals("KING of HEARTS", new Card(Rank.KING, Suit.HEARTS).toString());
		assertEquals("ACE of SPADES", new Card(Rank.ACE, Suit.SPADES).toString());
		assertEquals("TWO of SPADES", new Card(Rank.TWO, Suit.SPADES).toString());
		assertEquals("KING of SPADES", new Card(Rank.KING, Suit.SPADES).toString());
	}


	

}
