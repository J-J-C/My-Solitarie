package move;

import java.lang.reflect.*;

import model.GameModel;
import resource.Card;

public class DiscardMove implements Move 
{
	/**
	 * Creates a discard move.
	 */
	public DiscardMove()
	{}
	@Override
	public void performe(GameModel pModel)
	{
		assert !pModel.isEmptyDeck();
		pModel.discard();
	}
	
	@Override
	public void undo(GameModel pModel)
	{
		Card aCard = pModel.popDiscardPile();
		try 
		{
			Field deck = pModel.getClass().getDeclaredField("aCardDeck").getClass().getField("aCards");
			deck.setAccessible(true);
			Method e = deck.getClass().getDeclaredMethod("push");
			e.invoke(deck, aCard);
		} 
		catch (Exception e) 
		{
			System.out.println("Opps bug");
		}
	
	}

}
