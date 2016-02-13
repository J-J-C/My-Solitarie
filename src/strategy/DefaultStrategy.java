package strategy;

import java.util.ArrayList;

import java.util.List;

import move.*;
import model.GameModel;
import model.WorkingStackManager.Index;

public class DefaultStrategy implements Strategy{

	@Override
	public Move computeNextMove(GameModel pEngine) {
		
		ArrayList<Move> computedMoves = new ArrayList<>();
		if( pEngine.isEmptyDiscardPile() && !pEngine.isEmptyDeck() )
		{
			return new DiscardMove();
		}
		else
		{
			computedMoves.addAll(fromDiscardToSuitStack(pEngine));
			if(computedMoves.size() > 0)
			{
				return computedMoves.get(0);
			}
			
			computedMoves.addAll(fromWorkStackToSuitStack(pEngine));
			if(computedMoves.size() > 0)
			{
				return computedMoves.get(0);
			}
		}
		
		
		
		return null;
		
	}
	
	private List<Move> fromDiscardToSuitStack(GameModel pEngine)
	{
		ArrayList<Move> moves = new ArrayList<>();
		if(!pEngine.isEmptyDiscardPile())
		{
			if(pEngine.canMoveToSuitStack(pEngine.peekDiscard(),pEngine.peekDiscard().getSuit()))
			{
				SuitStackMove aMove = new SuitStackMove(pEngine.peekDiscard(), null);
				aMove.isFromDiscard();
				moves.add(aMove);
			}
		}
		return moves;
	}
	
	private List<Move> fromWorkStackToSuitStack(GameModel pEngine)
	{
		ArrayList<Move> moves = new ArrayList<>();
		for(Index index: Index.values())
		{
			if(pEngine.canMoveToSuitStack(pEngine.peekWorkStackCard(index), pEngine.peekWorkStackCard(index).getSuit()))
			{
				moves.add( new SuitStackMove(pEngine.peekWorkStackCard(index), index) );
			}
		}
		return moves;
	}

}
