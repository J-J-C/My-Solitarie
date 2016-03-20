package model;

import java.util.HashMap;


import java.util.Stack;
import resource.Card;
import model.GameModel.SuitStackIndex;
/**
 * COMP 303 My own SolitaireGame
 * 
 * A Suit Stack Manager manages those finishing suit stack
 * At this point index -> suit is fixed. 
 * 
 * Additional feature would be added to make it flexible between empty stack 
 * and unused suit
 * 
 *  
 *  
 * @author Jiajun Chen
 *
 */
public class SuitStackManager 
{
	

	// why this is a problem ????
	// Stack<Card>[] manager = new Stack<Card>[4];
	
	// using a Hashtable to manage those stack
	private final HashMap<SuitStackIndex, Stack<Card>> aManager = new HashMap<>();
	@SuppressWarnings("unused")
	private int aScore;
	
	/**
	 * Constructor.
	 */
	public SuitStackManager()
	{
		this.reset();
		this.aScore = 0;
	}
	/**
	 * @param pSuit the suit of the card
	 * @return the top card in the corresponding suit stack 
	 */
	public Card peek(SuitStackIndex pSuit)
	{
		assert !this.isEmpty(pSuit);
		return this.aManager.get(pSuit).peek();
	}
	
	/**
	 * @param pSuit the suit of the card
	 * @return remove and return the top card in the corresponding suit stack
	 */
	public Card pop(SuitStackIndex pSuit)
	{
		assert !this.isEmpty(pSuit);
		this.aScore--;
		return this.aManager.get(pSuit).pop();
	}
	
	/**
	 * @param pCard card to push
	 */
	public void push(Card pCard, SuitStackIndex pIndex) 
	{
		this.aManager.get(pIndex).push(pCard);
		this.aScore++;
	
	}
	
	/**
	 * @param pSuit suit of the card
	 * @return if current suit stack is empty
	 */
	public boolean isEmpty(SuitStackIndex pSuit)
	{
		return aManager.get(pSuit).isEmpty();
	}
	
	/**
	 * @return if all suit stack is complete
	 */
	public boolean isCompelete()
	{	
		final int number = 13;
		for(Stack<Card> suitstack : this.aManager.values())
		{
			if(suitstack.size() < number)
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * clear the stack.
	 */
	public void reset()
	{
		for(SuitStackIndex index : SuitStackIndex.values())
		{
			this.aManager.put(index, new Stack<Card>());
		}
	}
	
	/**
	 * 
	 * @return return the score
	 */
	public int getScore()
	{
		int score = 0;
		for(SuitStackIndex index: SuitStackIndex.values())
		{
			while(!aManager.get(index).isEmpty())
			{
				aManager.get(index).pop();
				score++;
			}
		}
		return score;
	}
	
	
	
	
}