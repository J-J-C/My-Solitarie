package model;


//import java.util.ArrayList;


import java.util.Arrays;
import java.util.Stack;

import ca.mcgill.cs.stg.solitaire.model.Move;
import ca.mcgill.cs.stg.solitaire.model.NullMove;
import resource.Deck;
import resource.Card;
import resource.Card.Rank;
import resource.Card.Suit;
//import resource.CardView;
import model.WorkingStackManager.Index;

import strategy.Strategy;

/**
 * GameModel.
 * @author JiajunChen
 *
 */

// in sigleton, only the instacne can be static 
public final class GameModel 
{
	public static final GameModel ENGINE = new GameModel();
	
	private final Deck aCardDeck = new Deck();
	private Stack<Card> aDiscardPile = new Stack<Card>();
	// private static Stack<Card> remainingPile = new Stack<Card>();
	private final SuitStackManager aSuitStack = new SuitStackManager();
	private final WorkingStackManager aWorkStack = new WorkingStackManager();
	private Strategy aStrategy = new DefaultStrategy();
	private int aState = 0;

	private GameModel(){}
	
	/**
	 * reset the game.
	 */
	public void reset()
	{
		aState = 0;
		aCardDeck.shuffle();
		aSuitStack.reset();
		aDiscardPile = new Stack<Card>();
		aWorkStack.initialize(aCardDeck);
	}
	
	private boolean canMoveToSuitStack(Card pCard, Suit pSuit)
	{
		if(aSuitStack.isEmpty(pSuit))
		{
			return pCard.getRank() == Rank.ACE;
		}
		Card fromSuit = aSuitStack.peek(pSuit);
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
		aSuitStack.push(pCard);
	}
	
	private boolean canMoveToWorkStack(Card pCard, Index pIndex)
	{
		if(pCard.getRank() == Rank.KING && aWorkStack.isEmpty(pIndex))
		{
			return true;
		}
		else if(aWorkStack.isEmpty(pIndex))
		{
			return false;
		}
		else
		{
			Card fromIndex = aWorkStack.peek(pIndex);
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
	}
	
	/** replenish the deck
	 * 
	 */
	
//	public void replenish
	
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
	public Card peekSuitStackCard(Suit pSuit)
	{
		
		return aSuitStack.peek(pSuit);
	}
	/**
	 * 
	 * @param pIndex index
	 * @return card
	 */
	public Card peekWorkStackCard(Index pIndex)
	{
		return aWorkStack.peek(pIndex);
	}
	
	public Card popDiscardPile()
	{
		return aDiscardPile.pop();
		
	}
	/**
	 * 
	 * @param pIndex index
	 * @return card
	 */
	public int getNumberofVisible(Index pIndex)
	{
		return aWorkStack.getVisibleCards(pIndex).size();
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
	 * autoplay game.
	 * @param pStrategy 111
	 */
	public boolean autoPlay()
	{
		Move move = aPlayingStrategy.computeNextMove(this);
		move.perform(this);
		return !(move instanceof NullMove);
		
	}
	
	/**
	 * @return state of the game
	 */
	public int getState()
	{
		return aState;
	}
	/**
	 * 
	 * @return card
	 */
	public Card peekDiscard()
	{
		return aDiscardPile.peek();
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
		for(Index index: Index.values())
		{
			temp += Arrays.toString(aWorkStack.getVisibleCards(index).toArray()) + "\n";
		}
		String segment = "------------------------------------";
		string = string+temp+segment+"\n";
		String a = "Suit Stack Status \n";
		for(Suit suit:Suit.values())
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
			ArrayList<CardView> cards = aWorkStack.getVisibleCards(p1);
			if(cards.size() == 0)
			{
				continue;
			}
			Card head1 = cards.get(0).getCard();
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
			if(head1 != null)
			{
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
			}			}
		
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
			Card candidate =  null;
			if(!WORKSTACK.isEmpty(p1))
			{
				candidate = WORKSTACK.peek(p1);
			}
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
		
		if(canMoveToSuitStack(candidate, candidate.getSuit()))
			{
				moveToSuitStack(candidate, candidate.getSuit());
				return 1;
			}
		
		
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
	
	


