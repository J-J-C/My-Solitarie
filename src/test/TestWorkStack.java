package test;
import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.GameModel.StackIndex;
import model.WorkingStackManager;
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
		test.push(aCard, StackIndex.FIRST);
		assertEquals(aCard, test.peek(StackIndex.FIRST));
		assertEquals(aCard, test.pop(StackIndex.FIRST));
		assertFalse(test.isEmpty(StackIndex.FIFTH));
		test.pop(StackIndex.FIRST);
		assertTrue(test.isEmpty(StackIndex.FIRST));
	}
	
	@Test (expected = EmptyStackException.class)
	public void testForException()
	{
		test.pop(StackIndex.FIRST);
		test.pop(StackIndex.FIRST);
		test.pop(StackIndex.FIRST);
	}
	
	@Test
	public void testForExpcetion2()
	{
		try
		{
			test.pop(StackIndex.FIRST);
			test.pop(StackIndex.FIRST);
			test.pop(StackIndex.FIRST);
		}
		catch (Exception EmptyStackException)
		{
			System.out.println("Worked!");
		}
	}

}
