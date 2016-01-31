package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import resource.Deck;
import resource.Card;
import resource.Card.Rank;
import resource.Card.Suit;
import resource.CardView;
import model.GameModel.MoveInWorkStack;
import model.GameModel.Strategy;
import model.WorkingStackManager.Index;

/**
 * GameModel.
 * @author JiajunChen
 *
 */
public final class GameModel 
{
	public static final GameModel ENGINE = new GameModel();
	
	private static final Deck CARDDECK = new Deck();
	private static Stack<Card> discardPile = new Stack<Card>();
	// private static Stack<Card> remainingPile = new Stack<Card>();
	private static final SuitStackManager SUITSTACK = new SuitStackManager();
	private static final WorkingStackManager WORKSTACK = new WorkingStackManager();
	private static int state = 0;

	private GameModel(){}
	
	/**
	 * reset the game.
	 */
	public void reset()
	{
		CARDDECK.shuffle();
		SUITSTACK.reset();
		discardPile = new Stack<Card>();
		// remainingPile = new Stack<Card>();
		WORKSTACK.initialize(CARDDECK);
//		while(!cardDeck.isEmpty()){
//			remainingPile.push(cardDeck.draw());
//		}
		/*
		 * if discardDeck is finalized
		 * while(!discardDeck.isEmpty()){discardDeck.pop()}
		 */	
	}
	
	private static boolean canMoveToSuitStack(Card pCard, Suit pSuit)
	{
		if(SUITSTACK.isEmpty(pSuit))
		{
			return pCard.getRank() == Rank.ACE;
		}
		Card fromSuit = SUITSTACK.peek(pSuit);
		return pCard.getSuit() == fromSuit.getSuit() && pCard.getRank().ordinal() - fromSuit.getRank().ordinal() == 1;
	}
	/**
	 * 
	 * @param pCard card
	 * @param pSuit suit
	 */
	public void moveToSuitStack(Card pCard, Suit pSuit)
	{
		assert canMoveToSuitStack(pCard, pSuit);
		SUITSTACK.push(pCard);
	}
	
	private static boolean canMoveToWorkStack(Card pCard, Index pIndex)
	{
		if(pCard.getRank() == Rank.KING && WORKSTACK.isEmpty(pIndex))
		{
			return true;
		}
		else if(WORKSTACK.isEmpty(pIndex))
		{
			return false;
		}
		else
		{
			Card fromIndex = WORKSTACK.peek(pIndex);
			return Math.abs(pCard.getSuit().ordinal()-fromIndex.getSuit().ordinal()) == 1 && 
					pCard.getRank().ordinal() - fromIndex.getRank().ordinal() == -1;
		}
		
	}
	
	/**
	 * 
	 * @param pCard card
	 * @param pIndex index
	 */
	public void moveToWorkStack(Card pCard, Index pIndex)
	{
		assert canMoveToWorkStack(pCard, pIndex);
		WORKSTACK.push(pCard, pIndex);
	}
	
	/**
	 * discard a card from the deck.
	 */
	public void discard()
	{
		assert !isEmptyDeck();
		discardPile.push(CARDDECK.draw());
	}
	
	/** replenish the deck
	 * 
	 */
	
//	public void replenish
	
	/**
	 * @return deck status
	 */
	public static boolean isEmptyDeck()
	{
		return CARDDECK.isEmpty();
	}
	
	/**
	 * @return discard pile status
	 */
	public boolean isEmptyDiscardPile()
	{
		return discardPile.isEmpty();
	}
	
	/**
	 * 
	 * @param pSuit suit
	 * @return card
	 */
	public Card peekSuitStackCard(Suit pSuit)
	{
		
		return SUITSTACK.peek(pSuit);
	}
	/**
	 * 
	 * @param pIndex index
	 * @return card
	 */
	public Card peekWorkStackCard(Index pIndex)
	{
		return WORKSTACK.peek(pIndex);
	}
	
	/**
	 * 
	 * @param pIndex index
	 * @return card
	 */
	public int getNumberofVisible(Index pIndex)
	{
		return WORKSTACK.getVisibleCards(pIndex).size();
	}
	
	
	/**
	 * @return the current score of the game
	 */
	public int getScore()
	{
		return SUITSTACK.getScore();
	}
	
	/**
	 * @return an instance of the GameModel class
	 */
	
	public static GameModel getInstance()
	{
		return ENGINE;
	}
	
	
	
	/**
	 * autoplay game.
	 * @param pStrategy 111
	 */
	public void autoPlay(Strategy pStrategy)
	{
		state += pStrategy.makeMove();
		
	}
	
	/**
	 * @return state of the game
	 */
	public int getState()
	{
		return state;
	}
	
	@Override
	public String toString()
	{
		String string = "";
		String workStack = "Working Stack Status";
		String suitStack = "Suit Stack Status";
		String discard = "Discard Pile Status";
		
		string = workStack +"\n";
		String temp = "";
		for(Index index: Index.values())
		{
			temp += Arrays.toString(WORKSTACK.getVisibleCards(index).toArray()) + "\n";
		}
		String segment = "------------------------------------";
		string = string+temp+segment+"\n";
		String a = "suitstack status \n";
		for(Suit suit:Suit.values())
		{
			if(!SUITSTACK.isEmpty(suit))
			{
				a += SUITSTACK.peek(suit).toString()+"  "+suit.toString()+"\n";
			}
			else
			{
				a += "empty "+suit.toString()+"\n";
			}
		}
		
		
		return string + a + state;
	}
	/**
	 * refill.
	 * @param pIndex Index...
	 */
	private void refill(Index pIndex)
	{
		if(WORKSTACK.getVisibleCards(pIndex).size()== 0 && !WORKSTACK.isEmpty(pIndex))
		{
			WORKSTACK.push(WORKSTACK.pop(pIndex), pIndex);
		}
	}
	
/**
 * Autoplay Strategy interface.
 * @author JiajunChen
 *
 */
interface Strategy
{
	int makeMove();
}

/**
 * Strategy one, move only in the stack.
 * @author JiajunChen
 *
 */
class MoveInWorkStack implements Strategy
{
	public int makeMove()
	{	
		int isChanged = 0;
		for(Index p1: Index.values())
		{
			ArrayList<CardView> cards = WORKSTACK.getVisibleCards(p1);
			if(cards.size() == 0)
			{
				continue;
			}
			Card head1 = cards.get(0).getCard();
			Card neck = (cards.size() > 1) ? cards.get(1).getCard() : null;
			Card tail = null;
			if(cards.size() > 1)
			{
				tail = cards.get(cards.size()-1).getCard();
			}
			
			for(Index p2: Index.values())
			{
				if(p1.equals(p2)) 
				{
					continue;
				}
				if(tail == null)
				{
					if(canMoveToWorkStack(head1, p2))
					{
						WORKSTACK.pop(p1);
						moveToWorkStack(head1, p2);
						refill(p1);
						isChanged++;
						continue;
						}
					}
					else
					{
						if(canMoveToWorkStack(tail, p2))
						{
							for(int i = cards.size()-1; i >= 0; i--)
							{
								WORKSTACK.push(cards.get(i).getCard(), p2);
								WORKSTACK.pop(p1);
								isChanged++;
							}
							refill(p1);
							continue;
						}
					}					
				}
			}			
		
		return isChanged;
	}
	
}

/**
 * 
 * @author JiajunChen
 *
 */
class MoveToSuitStack implements Strategy
{
	@Override
	public int makeMove() 
	{
		int isChanged = 0;
		for(Index p1: Index.values())
		{
			Card candidate = !WORKSTACK.isEmpty(p1) ? WORKSTACK.peek(p1) : null;
			if(candidate != null)
			{
				if(canMoveToSuitStack(candidate, candidate.getSuit()))
				{
					moveToSuitStack(candidate, candidate.getSuit());
					isChanged++;
					WORKSTACK.pop(p1);
					refill(p1);
				}
			}
		}
		return isChanged;
	}
}
/**
 * 
 * @author JiajunChen
 *
 */

class FromPileToStack implements Strategy
{

	@Override
	public int makeMove() 
	{
		int isChanged = 0;
		int hasChanged = 1;
		if(discardPile.isEmpty())
		{
			return 0;
		}
		Card candidate = discardPile.peek();
		while(!discardPile.isEmpty() && hasChanged == 1)
		{
			hasChanged = 0;
			if(canMoveToSuitStack(candidate, candidate.getSuit()))
			{
				moveToSuitStack(discardPile.pop(), candidate.getSuit());
				hasChanged = 1;
				isChanged = 1;
			}
			else
			{
				for(Index index:Index.values())
				{
					if(canMoveToWorkStack(candidate, index))
					{
						moveToWorkStack(discardPile.pop(), index);
						isChanged = 1;
						hasChanged = 1;
					}
				}
			}
		}
		return isChanged;
	}
	
}



}
	
	


