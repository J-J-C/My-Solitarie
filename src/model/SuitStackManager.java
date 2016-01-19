package model;

import java.util.HashMap;
import java.util.Stack;

import model.Card.Suit;

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
public class SuitStackManager {
	

	// why this is a problem ????
	// Stack<Card>[] manager = new Stack<Card>[4];
	
	// using a Hashtable to manage those stack
	private HashMap<Suit,Stack<Card>> manager = new HashMap<>();
	
	/**
	 * Constructor 
	 */
	public SuitStackManager(){
		reset();
	}
	/**
	 * @param aSuit
	 * @return the top card in the corresponding suit stack 
	 */
	public Card peek (Suit pSuit){
		assert !this.isEmpty(pSuit);
		return this.manager.get(pSuit).peek();
	}
	
	/**
	 * @param aSuit
	 * @return remove and return the top card in the corresponding suit stack
	 */
	public Card pop(Suit pSuit){
		assert !this.isEmpty(pSuit);
		return this.manager.get(pSuit).pop();
	}
	
	/**
	 * @param pCard card to push
	 */
	public void push(Card pCard) {
			this.manager.get(pCard.getSuit()).push(pCard);
	}
	
	/**
	 * @param pSuit
	 * @return if current suit stack is empty
	 */
	public boolean isEmpty(Suit pSuit){
		return manager.get(pSuit).isEmpty();
	}
	
	/**
	 * @return if all suit stack is complete
	 */
	public boolean isCompelete(){
		for(Stack<Card> suitstack : this.manager.values()){
			if(suitstack.size() < 13){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * reset the whole stack when receiving reset instruction
	 */
	public void reset(){
		for(Suit suit : Suit.values()){
			this.manager.put(suit, new Stack<Card>());
		}
	}
	
	// unnecessary, the verifcation part should be checked at game Listener
	class InvalidCardException extends Exception{
		public InvalidCardException(String message){
			super(message);
		}
	}
	
}
