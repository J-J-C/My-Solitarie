package model;



import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;
import java.util.Stack;

import move.Move;
import move.NullMove;
import resource.Deck;
import resource.Card;
import resource.Card.Rank;
import resource.CardView;
//import resource.CardView;
//import strategy.DefaultStrategy;
import strategy.Strategy;

/**
 * GameModel.
 * @author JiajunChen
 *
 */

// in sigleton, only the instacne can be static 
@Immutable(inspector = "Jiajun ", date = "2016/02/15")
public final class GameModel 
{
	public static final GameModel ENGINE = new GameModel();
	
	private final Deck aCardDeck = new Deck();
	private Stack<Card> aDiscardPile = new Stack<Card>();
	// private static Stack<Card> remainingPile = new Stack<Card>();
	private final SuitStackManager aSuitStack = new SuitStackManager();
	private final WorkingStackManager aWorkStack = new WorkingStackManager();
	//private Strategy aStrategy = new DefaultStrategy();
	private Stack<Move> moveStack = new Stack<>();
	private ArrayList<GameModelObserver> aListener = new ArrayList<>();
	private int aState = 0;

	private GameModel(){}
	
	public interface Index 
	{}
	
	/**
	 * Places where a card can be obtained.
	 */
	public enum CardSources implements Index
	{ DISCARD_PILE  }
	
	/**
	 * Represents the different stacks where cards
	 * can be accumulated.
	 */
	public enum StackIndex implements Index
	{ FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH }
	
	/**
	 * Represents the different stacks where completed
	 * suits can be accumulated.
	 */
	public enum SuitStackIndex implements Index
	{
		FIRST, SECOND, THIRD, FOURTH;
	}
	
	
	/**
	 * reset the game.
	 */
	public void reset()
	{
		aCardDeck.shuffle();
		aSuitStack.reset();
		aDiscardPile = new Stack<Card>();
		aWorkStack.initialize(aCardDeck);
		notifyObserver();
		//aStrategy = new DefaultStrategy();
	}
	
	/**
	 * Set to play strategy for the GameModel.
	 * Used for autoplay.
	 * @param pStrategy
	 */
	public void setStrategy(Strategy pStrategy)
	{
		//this.aStrategy = pStrategy;
	}
	/**
	 * check is a move to suit stack is valid or not
	 * 
	 * @param pCard
	 * @param pSuit
	 * @return can move or not
	 */
	public boolean canMoveToSuitStack(Card pCard, SuitStackIndex pSuit)
	{
		if(aSuitStack.isEmpty(pSuit))
		{
			return pCard.getRank() == Rank.ACE;
		}
		Card fromSuit = aSuitStack.peek(pSuit);
		return pCard.getSuit() == fromSuit.getSuit() && pCard.getRank().ordinal() - fromSuit.getRank().ordinal() == 1;
	}
	/**
	 * The actual move method
	 * 
	 * @param pCard card
	 * @param pSuit suit
	 */
	public void moveToSuitStack(Card pCard, SuitStackIndex pSuit)
	{
		assert canMoveToSuitStack(pCard, pSuit);
		aSuitStack.push(pCard);
	}
	
	/**
	 * 
	 * @param pCard
	 * @param destination
	 * @return
	 */
	public boolean canMoveToWorkStack(Card pCard, StackIndex destination)
	{
		if(pCard.getRank() == Rank.KING && aWorkStack.isEmpty(destination))
		{
			return true;
		}
		else if(aWorkStack.isEmpty(destination))
		{
			return false;
		}
		else
		{
			Card fromIndex = aWorkStack.peek(destination);
			return Math.abs(pCard.getSuit().ordinal()-fromIndex.getSuit().ordinal()) == 1 && 
					pCard.getRank().ordinal() - fromIndex.getRank().ordinal() == -1;
		}
		
	}
	
	/**
	 * 
	 * @param pCard card
	 * @param pIndex index
	 */
	public void moveToWorkStack(Card pCard, StackIndex pIndex)
	{
		assert canMoveToWorkStack(pCard, pIndex);
		aWorkStack.push(pCard, pIndex);
	}
	
	/**
	 * discard a card from the deck.
	 */
	public void discard()
	{
		assert !isEmptyDeck();
		aDiscardPile.push(aCardDeck.draw());
		aState++;
		notifyObserver();
	}
	

	
	/**
	 * @return deck status
	 */
	public boolean isEmptyDeck()
	{
		return aCardDeck.isEmpty();
	}
	
	/**
	 * @return discard pile status
	 */
	public boolean isEmptyDiscardPile()
	{
		return aDiscardPile.isEmpty();
	}
	
	/**
	 * 
	 * @param pSuit suit
	 * @return card
	 */
	public Card peekSuitStackCard(SuitStackIndex pSuit)
	{
		
		return aSuitStack.peek(pSuit);
	}
	/**
	 * 
	 * @param pIndex index
	 * @return card
	 */
	public Card peekWorkStackCard(StackIndex pIndex)
	{
		return aWorkStack.peek(pIndex);
	}
	
	/**
	 * Pop a card from the index. Used for autoPlay only
	 * 
	 */
	public Card popCard(Index pIndex)
	{
		if(pIndex.getClass().equals(StackIndex.class)){
			return aWorkStack.pop((StackIndex) pIndex);
		}else if(pIndex.getClass().equals(SuitStackIndex.class)){
			return aSuitStack.pop((SuitStackIndex) pIndex);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 
	 * @return card
	 */
	public Card peekDiscard()
	{
		return aDiscardPile.peek();
	}
	
	/**
	 * 
	 * @return
	 */
	public Card popDiscardPile()
	{
		return aDiscardPile.pop();
		
	}
	
	/**
	 * push a card back to the deck.
	 */
	public void push(Card pCard)
	{
		aCardDeck.push(pCard);
	}
	
	/**
	 * Return the list of card that is visible in the Index
	 * @param pIndex index
	 * @return card
	 */
	public List<CardView> getAllCards(StackIndex pIndex)
	{
		return aWorkStack.getCards(pIndex);
	}
	
	/**
	 * @return the current score of the game
	 */
	public int getScore()
	{
		return aSuitStack.getScore();
	}
	
	/**
	 * @return an instance of the GameModel class
	 */
	
	public static GameModel getInstance()
	{
		return ENGINE;
	}
	
	/**
	 * 
	 */
	public void addObserver(GameModelObserver pView){
		this.aListener.add(pView);
	}
	
	private void notifyObserver(){
		for( GameModelObserver observer : aListener )
		{
			observer.stateChanged();
		}
	}
	
	/**
	 * autoplay game.
	 * @param strategy 
	 * @param pStrategy 111
	 */
//	public boolean autoPlay()
//	{
//		Move move = aStrategy.computeNextMove(this);
//		move.perform(this);
//		moveStack.push(move);
//		return move.getClass().equals(NullMove.class);
//	}
	
	/**
	 * Undo feature of the game
	 * @return
	 * @throws  
	 */
	public boolean undo(){
		
		if(!moveStack.isEmpty()){
			Move aMove = moveStack.pop();
			if(aMove.getClass().equals(NullMove.class)){
				return false;
			}else{
				aMove.undo(this);
				return true;
			}
		}else{
			return false;
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public String toString()
	{
		String string = "";
		String workStack = "Working Stack Status";
	//	String suitStack = "Suit Stack Status";
	//	String discard = "Discard Pile Status";
		
		string = workStack +"\n";
		String temp = "";
		for(StackIndex index: StackIndex.values())
		{
			//temp += Arrays.toString(aWorkStack.getVisibleCards(index).toArray()) + "\n";
		}
		String segment = "------------------------------------";
		string = string+temp+segment+"\n";
		String a = "Suit Stack Status \n";
		for(SuitStackIndex suit:SuitStackIndex.values())
		{
			if(!aSuitStack.isEmpty(suit))
			{
				a += aSuitStack.peek(suit).toString()+"  "+suit.toString()+"\n";
			}
			else
			{
				a += "empty "+suit.toString()+"\n";
			}
		}
		
		String seal = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
		return seal+string + a + "cumlative moves " + aState + "\n"+ seal;
	}
}
	
	


