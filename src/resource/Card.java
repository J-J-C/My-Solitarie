package resource;

import model.Immutable;

/**
 * Same as version 7. My last example.
 */
@Immutable(date = "2016-02-08", inspector = "Chen")
public class Card {

  public static final String[] RANKS = {"Ace", "Two", "Three", "Four", "Five",
      "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
  public static final String[] SUITS = {"Clubs", "Diamonds", "Spades", "Hearts"};
  private Rank aRank; // Invariant: != null
  private Suit aSuit; // Invariant: != null

  /**
   * @param pRank The index of the rank in RANKS
   * @param pSuit The index of the suit in SUITS
   * @pre pRank != null && pSuit != null
   */
  public Card(Rank pRank, Suit pSuit) {
    assert pRank != null && pSuit != null;
    aRank = pRank;
    aSuit = pSuit;
  }

  /**
   * @return The index in RANKS corresponding to the rank of the card.
   * @post return != null
   */
  public Rank getRank() {
    return aRank;
  }

  /**
   * Assigns a new rank to the card.
   *
   * @param pRank The new rank.
   * @pre pRank != null
   */
  public void setRank(Rank pRank) {
    aRank = pRank;
  }

  /**
   * @return The index in SUITS corresponding to the suit of the card.
   * @post return != null
   */
  public Suit getSuit() {
    return aSuit;
  }

  /**
   * Assigns a new suit to the card.
   *
   * @param pSuit The new suit.
   * @pre pSuit != null
   */
  public void setSuit(Suit pSuit) {
    aSuit = pSuit;
  }

  @Override
  public boolean equals(Object pCard) {
    if (pCard == null) {
      return false;
    }
    if (pCard == this) {
      return true;
    }

    if (pCard.getClass() != (this.getClass())) {
      return false;
    }
    return this.aRank == ((Card) pCard).getRank() && this.aSuit == ((Card) pCard).getSuit();
  }

  @Override
  public String toString() {
    return aRank + " of " + aSuit;
  }

  /**
   * A card's rank.
   */
  public enum Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX,
    SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;
  }

  /**
   * A card's suit.
   */
  public enum Suit {
    CLUBS, DIAMONDS, SPADES, HEARTS
  }
}
