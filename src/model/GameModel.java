package model;


import java.util.Stack;


import resource.*;
import resource.Card.Suit;
import model.WorkingStackManager.Index;

public final class GameModel {
	

	
	
	private static final Deck cardDeck = new Deck();
	private static Stack<Card> discardPile = new Stack<Card>();
	private static final SuitStackManager suitStack = new SuitStackManager();
	private static final WorkingStackManager workStack = new WorkingStackManager();
	public String aa = "hello world";
	
	public static final GameModel ENGINE = new GameModel();
	
	
	private GameModel(){}
	
	 
	
	public void reset(){
		cardDeck.shuffle();
		suitStack.reset();
		discardPile = new Stack<Card>();
		workStack.initialize(cardDeck);
		/*
		 * if discardDeck is finalized
		 * while(!discardDeck.isEmpty()){discardDeck.pop()}
		 */	
	}
	
	private static boolean canMoveToSuitStack(Card pCard, Suit pSuit){
		return true;
	}
	
	public void moveToSuitStack(Card pCard, Suit pSuit){
		
		assert canMoveToSuitStack(pCard,pSuit);
		suitStack.push(pCard);
		
		
	}
	
	private static boolean canMoveToWorkStack(Card pCard, Index pIndex){
		return true;
	}
	
	public void moveToWorkStack(Card pCard, Index pIndex){
		assert canMoveToWorkStack(pCard,pIndex);
		workStack.push(pCard,pIndex);
	}
	
	public void discard(){
		assert !deckIsEmpty();
		this.discardPile.push(this.cardDeck.draw());
	}
	
	/** replenish the deck
	 * 
	 */
//	public void replenish
	
	public static boolean deckIsEmpty(){
		return cardDeck.isEmpty();
	}
	
	public boolean pileIsEmpty(){
		return discardPile.isEmpty();
	}
	
	/**
	 * 
	 * @return the current score of the game
	 */
	public int getScore(){
		return suitStack.getScore();
	}
	
	/**
	 * 
	 * @return an instance of the GameModel class
	 */
	
	public static GameModel getInstance(){
		return ENGINE;
	}
}
