package model;

import java.util.HashMap;

import java.util.Stack;

import resource.Card;
import resource.CardView;
import resource.Deck;

public class WorkingStackManager {
	
	public enum Index {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX;
	}
	
	private static HashMap<Index,Stack<CardView>> workingManager = new HashMap<>();
	
	
	/**
	 * Constructor 
	 */
	public WorkingStackManager(){}
	
	
	
	private void reset(){
		for(Index index: Index.values()){
			workingManager.put(index, new Stack<CardView>());
		}
	}
	
	public void initialize(Deck pDeck){
		this.reset();
		int index = 1;
		for(Stack<CardView> stack : workingManager.values()){
			int temp = index;
			while(temp != 0){
				stack.push(new CardView(pDeck.draw()));
				temp--;
			}
			stack.peek().setVisible(true);
			index++;
		}
	}
	
	
	/**
	 * @param aSuit
	 * @return the top card in the corresponding suit stack 
	 */
	public Card peek (Index pIndex){
		assert !this.isEmpty(pIndex);
		return workingManager.get(pIndex).peek().getCard();
	}
	
	/**
	 * @param aSuit
	 * @return remove and return the top card in the corresponding suit stack
	 */
	public Card pop(Index pIndex){
		assert !this.isEmpty(pIndex);
		return workingManager.get(pIndex).pop().getCard();
	}
	
	/**
	 * @param pCard card to push
	 */
	public void push(Card pCard, Index pIndex) {
		workingManager.get(pIndex).push(new CardView(pCard));
		workingManager.get(pIndex).peek().setVisible(true);
	}
	
	/**
	 * @param pSuit
	 * @return if current suit stack is empty
	 */
	public boolean isEmpty(Index pIndex){
		return workingManager.get(pIndex).isEmpty();
	}
	
	/**
	 * @return if the loading is complete remove at this point
	 */
	
	// kinda unnecessary
	public boolean isCompelete(){
		int x = 1;
		for(Stack<CardView> workingStack : workingManager.values()){
			if(workingStack.size() != x){
				return false;
			}
			x++;
		}
		return true;
	}

	

}
