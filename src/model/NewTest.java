package model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import resource.Card;
import resource.Card.Rank;
import resource.Card.Suit;
import resource.Deck;

public class NewTest {

	public static void main(String[] args) throws Exception {
		GameModel engine = GameModel.getInstance();
		engine.reset();
		System.out.println(engine.toString());
		engine.moveToSuitStack(new Card(Rank.ACE,Suit.CLUBS), Suit.CLUBS);
		System.out.println(engine.toString());
		
		Field deck = engine.getClass().getDeclaredField("aCardDeck");
		deck.setAccessible(true);
	
		// System.out.println(deck.getClass());
		Method a = Deck.class.getDeclaredMethod("peek");
		Card test = (Card) a.invoke(deck.get(engine));	
		System.out.println(test.toString());
		engine.autoPlay();
		System.out.println(engine.popDiscardPile().toString());
		
		
		
	}

}
