package model;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Stack;

import resource.Card;
import resource.CardView;
import resource.Deck;

/**
 * 
 * @author JiajunChen
 *
 */
public class WorkingStackManager 
{
	/**
	 * 
	 * @author JiajunChen
	 *
	 */
	public enum Index 
	{
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX;
	}
	
	private static HashMap<Index, Stack<CardView>> workingManager = new HashMap<>();
	
	
	/**
	 * Constructor .
	 */
	public WorkingStackManager(){}
	
	
	/**
	 * reset the whole thing.
	 */
	private void reset()
	{
		for(Index index: Index.values())
		{
			workingManager.put(index, new Stack<CardView>());
		}
	}
	/**
	 * 
	 * @param pDeck the deck
	 */
	public void initialize(Deck pDeck)
	{
		String a = "";
		this.reset();
		for(Index index : Index.values())
		{
			a += "Index: " + index + " [ ";
			String b = "";
			int temp = index.ordinal();
			while(temp != -1)
			{
				workingManager.get(index).push(new CardView(pDeck.draw()));
				b = workingManager.get(index).peek().getCard().toString() + ", " + b;
				temp--;
			}
			workingManager.get(index).peek().setVisible(true);
			a = a + b + "]\n";
		}
		System.out.println(a + "||||||||||||||||||||||||||");
	}
	
	
	/**
	 * @param pIndex index
	 * @return the top card in the corresponding suit stack 
	 */
	public Card peek(Index pIndex)
	{
		assert !this.isEmpty(pIndex);
		return workingManager.get(pIndex).peek().getCard();
	}
	
	/**
	 * @param pIndex index
	 * @return remove and return the top card in the corresponding suit stack
	 */
	public Card pop(Index pIndex)
	{
		assert !this.isEmpty(pIndex);
		return workingManager.get(pIndex).pop().getCard();
	}
	
	/**
	 * @param pCard card to push
	 * @param pIndex destination
	 */
	public void push(Card pCard, Index pIndex) 
	{
		workingManager.get(pIndex).push(new CardView(pCard));
		workingManager.get(pIndex).peek().setVisible(true);
	}
	
	/**
	 * @param pIndex destination
	 * @return if current suit stack is empty
	 */
	public boolean isEmpty(Index pIndex)
	{
		return workingManager.get(pIndex).isEmpty();
	}
	
	/**
	 * @return if the loading is complete remove at this point
	 */
	
	// kinda unnecessary
	public boolean isCompelete()
	{
		int x = 1;
		for(Stack<CardView> workingStack : workingManager.values())
		{
			if(workingStack.size() != x)
			{
				return false;
			}
			x++;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @param pIndex a
	 * @return List of visible card of the specific index stack
	 */
	public ArrayList<CardView> getVisibleCards(Index pIndex)
	{
		Stack<CardView> target = workingManager.get(pIndex);
		Stack<CardView> temp = new Stack<>();
		ArrayList<CardView> visibleCards = new ArrayList<>();
		while(!target.isEmpty())
		{
			CardView card = target.pop();
			if(card.isVisible())
			{
				visibleCards.add(card);
			}
			temp.push(card);
		}
		while(!temp.isEmpty())
		{
			workingManager.get(pIndex).push(temp.pop());
		}
		return visibleCards;
		
	}

	

}
