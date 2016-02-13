package test;

import static org.junit.Assert.assertEquals;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import resource.Card.*;
import strategy.Strategy;
import model.GameModel;
import model.WorkingStackManager.Index;
import move.DiscardMove;
import move.Move;
import move.SuitStackMove;
import move.WorkStackMove;
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
	public void testDiscardFunction()
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
		System.out.println(GameModel.getInstance().toString());
	}
	
	@Test
	public void testDiscardMove() throws Exception
	{
		// testing autoplay with own stub
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
		
		Field strategy = GameModel.getInstance().getClass().getDeclaredField("aStrategy");
		strategy.setAccessible(true);
		strategy.set(GameModel.getInstance(), new Strategy(){

			@Override
			public Move computeNextMove(GameModel pEngine) {
				// TODO Auto-generated method stub
				return new DiscardMove();
			}
			
		});
		GameModel.getInstance().autoPlay();
		assertEquals(testCards[1], GameModel.getInstance().peekDiscard());
		GameModel.getInstance().autoPlay();
		assertEquals(testCards[2], GameModel.getInstance().peekDiscard());
		GameModel.getInstance().autoPlay();
		assertEquals(testCards[3], GameModel.getInstance().peekDiscard());
		
		System.out.println(testCards[1].toString());
		System.out.println(testCards[2].toString());
		System.out.println(testCards[3].toString());
		
		
	}
	
	@Test
	public void testSuitMove() throws Exception
	{
	
		Field strategy = GameModel.getInstance().getClass().getDeclaredField("aStrategy");
		strategy.setAccessible(true);
		
		strategy.set(GameModel.getInstance(), new Strategy(){

			@Override
			public Move computeNextMove(GameModel pEngine) {
				// TODO Auto-generated method stub
				return new SuitStackMove(new Card(Rank.QUEEN,Suit.DIAMONDS), null);
			}
			
		});
		GameModel.getInstance().autoPlay();
		assertTrue(GameModel.getInstance().peekSuitStackCard(Suit.DIAMONDS) !=new Card(Rank.QUEEN,Suit.DIAMONDS) );
		try{
			GameModel.getInstance().peekSuitStackCard(Suit.DIAMONDS);
		}catch (AssertionError a){
			
		}
		
		strategy.set(GameModel.getInstance(), new Strategy(){

			@Override
			public Move computeNextMove(GameModel pEngine) {
				// TODO Auto-generated method stub
				return new SuitStackMove(new Card(Rank.ACE,Suit.CLUBS), null);
			}
			
		});
		GameModel.getInstance().autoPlay();
		assertEquals(new Card(Rank.ACE,Suit.CLUBS), GameModel.getInstance().peekSuitStackCard(Suit.CLUBS));
		
		strategy.set(GameModel.getInstance(), new Strategy(){

			@Override
			public Move computeNextMove(GameModel pEngine) {
				// TODO Auto-generated method stub
				return new SuitStackMove(new Card(Rank.TWO,Suit.CLUBS), null);
			}
			
		});
		GameModel.getInstance().autoPlay();
		assertEquals(new Card(Rank.TWO,Suit.CLUBS), GameModel.getInstance().peekSuitStackCard(Suit.CLUBS));	
	}
	
	

}
