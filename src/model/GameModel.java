package model;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import move.Move;
import move.NullMove;
import resource.Card;
import resource.Card.Rank;
import resource.Card.Suit;
import resource.CardView;
import resource.Deck;
import strategy.Strategy;

/**
 * GameModel.
 *
 * @author JiajunChen
 */

// in sigleton, only the instacne can be static 
@Immutable(inspector = "Jiajun ", date = "2016/02/15")
public final class GameModel {

  public static final GameModel ENGINE = new GameModel();

  private final Deck aCardDeck = new Deck();
  // private static Stack<Card> remainingPile = new Stack<Card>();
  private final SuitStackManager aSuitStack = new SuitStackManager();
  private final WorkingStackManager aWorkStack = new WorkingStackManager();
  private Stack<Card> aDiscardPile = new Stack<Card>();
  //private Strategy aStrategy = new DefaultStrategy();
  private Stack<Move> moveStack = new Stack<>();
  private ArrayList<GameModelObserver> aListener = new ArrayList<>();
  @SuppressWarnings("unused")
  private int aState = 0;

  private GameModel() {
  }

  /**
   * @return an instance of the GameModel class
   */

  public static GameModel getInstance() {
    return ENGINE;
  }

  /**
   * reset the game.
   */
  public void reset() {
    aCardDeck.shuffle();
    aSuitStack.reset();
    aDiscardPile = new Stack<Card>();
    aWorkStack.initialize(aCardDeck);
    notifyObserver();
    //aStrategy = new DefaultStrategy();
  }

  /**
   * check is a move to suit stack is valid or not
   *
   * @return can move or not
   */
  public boolean canMoveToSuitStack(Card pCard, SuitStackIndex pSuit) {
    if (aSuitStack.isEmpty(pSuit)) {
      return pCard.getRank() == Rank.ACE;
    }
    Card fromSuit = aSuitStack.peek(pSuit);
    return pCard.getSuit() == fromSuit.getSuit()
        && pCard.getRank().ordinal() - fromSuit.getRank().ordinal() == 1;
  }

  /**
   * The actual move method
   *
   * @param pCard card
   * @param pSuit suit
   */
  public void moveToSuitStack(Card pCard, SuitStackIndex pSuit) {
    assert canMoveToSuitStack(pCard, pSuit);

    // move the card out from its source.
    Index a = findCardIndex(pCard);
    if (a.getClass().equals(CardSources.class)) {
      popDiscardPile();
    } else {
      popCard(a);
      if (a.getClass() == StackIndex.class) {
        if (!isEmptyWorkStack((StackIndex) a)) {
          peekWorkStackCard((StackIndex) a);
        }
      }
    }

    // add the card to the target.
    aSuitStack.push(pCard, pSuit);

    // notify observers
    notifyObserver();
  }

  /**
   *
   */
  public boolean canMoveToWorkStack(Card pCard, StackIndex destination) {
    if (pCard.getRank() == Rank.KING && aWorkStack.isEmpty(destination)) {
      return true;
    } else if (aWorkStack.isEmpty(destination)) {
      return false;
    } else {
      Card fromIndex = aWorkStack.peek(destination);
      if (pCard.getRank().ordinal() - fromIndex.getRank().ordinal() == -1) {
        if (pCard.getSuit() == Suit.DIAMONDS || pCard.getSuit() == Suit.HEARTS) {
          return fromIndex.getSuit() == Suit.CLUBS || fromIndex.getSuit() == Suit.SPADES;
        } else {
          return fromIndex.getSuit() == Suit.DIAMONDS || fromIndex.getSuit() == Suit.HEARTS;
        }
      } else {
        return false;
      }
    }

  }

  /**
   * Move the card to specified index.
   *
   * @param pCard card
   * @param pIndex index
   */
  public void moveToWorkStack(Card pCard, StackIndex pIndex) {
    assert canMoveToWorkStack(pCard, pIndex);
    // move the card out from its source.
    Index a = findCardIndex(pCard);

    if (a.getClass().equals(CardSources.class)) {
      popDiscardPile();
      aWorkStack.push(pCard, pIndex);
    } else if (a.getClass().equals(SuitStackIndex.class)) {
      popCard(a);
      aWorkStack.push(pCard, pIndex);
    } else {

      Card[] tail = seriesCard(pCard);

      for (Card aCard : tail) {
        if (aCard == null) {
          break;
        }
        if (!isEmptyWorkStack((StackIndex) a)) {
          popCard(a);
        }
        if (!isEmptyWorkStack((StackIndex) a)) {
          peekWorkStackCard((StackIndex) a);
        }
        aWorkStack.push(aCard, pIndex);
      }
    }
    // notify observers
    notifyObserver();

  }

  /**
   * discard a card from the deck.
   */
  public void discard() {
    assert !isEmptyDeck();
    aDiscardPile.push(aCardDeck.draw());
    aState++;
    notifyObserver();
  }

  /**
   * @return deck status
   */
  public boolean isEmptyDeck() {
    return aCardDeck.isEmpty();
  }

  /**
   * check if discard pile is empty
   *
   * @return discard pile status
   */
  public boolean isEmptyDiscardPile() {
    return aDiscardPile.isEmpty();
  }

  /**
   * check if current suit stack is empty
   *
   * @return if current Suit Stack is Empty
   */

  public boolean isEmptySuitStack(SuitStackIndex aIndex) {

    return aSuitStack.isEmpty(aIndex);
  }

  /**
   * check if current work stack is empty
   *
   * @param WorkStack Index
   * @return if current Work Stack is empty
   */

  public boolean isEmptyWorkStack(StackIndex aIndex) {

    return aWorkStack.isEmpty(aIndex);
  }

  /**
   * @param pSuit suit
   * @return Card
   */
  public Card peekSuitStackCard(SuitStackIndex pSuit) {

    return aSuitStack.peek(pSuit);
  }

  /**
   * @param pIndex index
   * @return card
   */
  public Card peekWorkStackCard(StackIndex pIndex) {
    return aWorkStack.peek(pIndex);
  }

  /**
   * Pop a card from the index. Used for autoPlay only
   */
  public Card popCard(Index pIndex) {
    if (pIndex.getClass().equals(StackIndex.class)) {
      if (!isEmptyWorkStack((StackIndex) pIndex)) {
        return aWorkStack.pop((StackIndex) pIndex);
      }
    } else if (pIndex.getClass().equals(SuitStackIndex.class)) {
      return aSuitStack.pop((SuitStackIndex) pIndex);
    } else {
      return null;
    }
    return null;

  }

  /**
   * @return card
   */
  public Card peekDiscard() {
    return aDiscardPile.peek();
  }

  /**
   *
   */
  public Card popDiscardPile() {
    Card temp = aDiscardPile.pop();
    notifyObserver();
    return temp;

  }

  /**
   * push a card back to the deck.
   */
  public void push(Card pCard) {
    aCardDeck.push(pCard);
  }

  /**
   * Return the list of card that is visible in the Index
   *
   * @param pIndex index
   * @return card
   */
  public List<CardView> getAllCards(StackIndex pIndex) {
    return aWorkStack.getCards(pIndex);
  }

  /**
   * @return the current score of the game
   */
  public int getScore() {
    return aSuitStack.getScore();
  }

  /**
   * Set to play strategy for the GameModel. Used for autoplay.
   */
  public void setStrategy(Strategy pStrategy) {
    //this.aStrategy = pStrategy;
  }

  /**
   * Add GUI component to the Game Model.
   */
  public void addObserver(GameModelObserver pView) {
    this.aListener.add(pView);
  }

  /**
   * Notify the GUI of the program.
   */
  private void notifyObserver() {
    for (GameModelObserver observer : aListener) {
      observer.stateChanged();
    }
  }

  /**
   * Find the original location of the card
   *
   * @param Card pCard
   * @return Index aIndex
   */
  private Index findCardIndex(Card pCard) {

    if (!isEmptyDiscardPile() && peekDiscard() == pCard) {
      return CardSources.DISCARD_PILE;
    }

    for (StackIndex aIndex : StackIndex.values()) {
      for (CardView aView : getAllCards(aIndex)) {
        if (aView.getCard() == pCard) {
          return aIndex;
        }
      }
    }
    for (SuitStackIndex aIndex : SuitStackIndex.values()) {
      if (peekSuitStackCard(aIndex) == pCard) {
        return aIndex;
      }
    }
    return null;
  }

  private Card[] seriesCard(Card pCard) {
    Card[] tail = new Card[52];
    Index location = findCardIndex(pCard);
    int pointer = 0;
    boolean found = false;
    if (location.getClass() == StackIndex.class) {
      StackIndex aIndex = (StackIndex) location;
      if (pCard == peekWorkStackCard(aIndex)) {
        tail[pointer] = pCard;
      } else {
        for (CardView cardView : getAllCards(aIndex)) {
          if (cardView.getCard() == pCard || found) {
            tail[pointer] = cardView.getCard();
            pointer++;
            found = true;
          }
        }
      }
      return tail;
    }
    return null;
  }

  /**
   * Undo feature of the game
   */
  public boolean undo() {

    if (!moveStack.isEmpty()) {
      Move aMove = moveStack.pop();
      if (aMove.getClass().equals(NullMove.class)) {
        return false;
      } else {
        aMove.undo(this);
        return true;
      }
    } else {
      return false;
    }


  }

  /**
   * Places where a card can be obtained.
   */
  public enum CardSources implements Index {DISCARD_PILE}

  /**
   * Represents the different stacks where cards can be accumulated.
   */
  public enum StackIndex implements Index {FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH}

  /**
   * Represents the different stacks where completed suits can be accumulated.
   */
  public enum SuitStackIndex implements Index {
    FIRST, SECOND, THIRD, FOURTH;
  }

  /**
   * autoplay game.
   * @param strategy
   * @param pStrategy 111
   */
//	public boolean autoPlay()
//	{
//		Move move = aStrategy.computeNextMove(this);
//		move.perform(this);
//		moveStack.push(move);
//		return move.getClass().equals(NullMove.class);
//	}

  public interface Index {

  }

}
	
	


