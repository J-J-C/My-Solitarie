package resource;

import java.util.Collections;
import resource.Card.Rank;
import resource.Card.Suit;


import java.util.List;
import java.util.Stack;

public class Deck
{
	private final Stack<Card> aCards = new Stack<>();
	// flyweight pattern
	private static final CardFactory factory = new CardFactory();
	
	public Deck(){}
	
	// There are different ways of doing this (left as an exercise).
	public List<Card> getCards(){
		return Collections.unmodifiableList(aCards);
		// return new Deck(pDeck);
		// return new ArrayList<Card> (aCards);
		// return (List<Card>) aCards.clone();
		
	}
	
	public Deck( Deck pDeck ){
		for( Card card : pDeck.aCards ){
			aCards.add(new Card(card));
		}
	}
	
	// using the cardfactory to generate cards
	public void shuffle(){
		aCards.clear();
		for( Suit suit : Suit.values() ){
			for( Rank rank : Rank.values()){
				aCards.push(factory.getCard(rank, suit));
			}
		}
		Collections.shuffle(aCards);
	}
	
	public boolean isEmpty(){
		return aCards.isEmpty();
	}
	
	public Card draw(){
		return aCards.pop();
	}
}
