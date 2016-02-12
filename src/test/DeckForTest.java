package test;

import java.lang.reflect.Method;
import resource.Deck;
public class DeckForTest {
	


	/**
	 * Models a deck of 52 cards that is not 
	 * shuffled so that the cards drawn can be predicted,
	 * in order of suit then rank.
	 */
	public class TestDeck extends Deck
	{
		/**
		 * Only resets the deck without shuffling it.
		 */
		@Override
		public void shuffle()
		{
			try
			{
				Method reset = Deck.class.getDeclaredMethod("reset");
				reset.setAccessible(true);
				reset.invoke(this, new Object[0]);
			}
			catch( Exception pException )
			{
				pException.printStackTrace();
			}
		}
	}


}
