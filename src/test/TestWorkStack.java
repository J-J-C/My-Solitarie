package test;
import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.WorkingStackManager;
import model.WorkingStackManager.Index;
import resource.Card;
import resource.Card.Rank;
import resource.Card.Suit;
import resource.Deck;

public class TestWorkStack 
{
	private WorkingStackManager test = new WorkingStackManager();
	private Deck pDeck;
	
	
	@Before
	public void setUp(){
		pDeck = new Deck();
		pDeck.shuffle();
		test.initialize(pDeck);
	}
	
	@Test
	public void testTheBefore()
	{
		Card aCard = new Card(Rank.ACE, Suit.SPADES);
		test.push(aCard, Index.ONE);
		assertEquals(aCard, test.peek(Index.ONE));
		assertEquals(aCard, test.pop(Index.ONE));
		assertFalse(test.isEmpty(Index.FIVE));
		test.pop(Index.ONE);
		test.pop(Index.ONE);
		assertTrue(test.isEmpty(Index.ONE));
	}
	
	@Test (expected = EmptyStackException.class)
	public void testForException()
	{
		test.pop(Index.ONE);
		test.pop(Index.ONE);
		test.pop(Index.ONE);
	}
	
	@Test
	public void testForExpcetion2()
	{
		try
		{
			test.pop(Index.ONE);
			test.pop(Index.ONE);
			test.pop(Index.ONE);
		}
		catch (Exception EmptyStackException)
		{
			System.out.println("Worked!");
		}
	}

}
