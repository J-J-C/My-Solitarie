package test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import resource.Card.*;
import model.GameModel;
import model.*;
import resource.*;

public class TestGameModel {
	
	
	@Before
	public void setUp() throws Exception
	{
		Field deckField = GameModel.class.getDeclaredField("aCardDeck");
		deckField.setAccessible(true);
		deckField.set(GameModel.getInstance(), new Deck());
		GameModel.getInstance().reset();
	}
	
	@Test
	// redo 
	public void testDiscard()
	{
		assertTrue(GameModel.getInstance().isEmptyDiscardPile());
		assertFalse(GameModel.getInstance().isEmptyDeck());// 3 of hearts
		Card[] testCards = new Card[25];
		int index = 25-1;
		for(Suit suit: Suit.values())
		{
			for(Rank rank: Rank.values())
			{
				if (index == -1) break;
				testCards[index] = new Card(rank, suit);
				index--;
			}
		}
		for( int i = 0; i < 24; i++ )
		{
			assertFalse(GameModel.getInstance().isEmptyDeck());
			GameModel.getInstance().discard();
			// Test a few cards
			
			assertEquals(testCards[i+1], GameModel.getInstance().peekDiscard());
			assertFalse(GameModel.getInstance().isEmptyDiscardPile());
		}
		assertTrue(GameModel.getInstance().isEmptyDeck());
	}

}
