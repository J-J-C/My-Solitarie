package resource;

/**
 * @author JiajunChen
 */
public class CardView {

  private Card aCard;
  private boolean aVisible;

  /**
   * @param pCard the card
   */
  public CardView(Card pCard) {
    this.aCard = pCard;
    this.aVisible = false;
  }

  /**
   * @return the card
   */
  public Card getCard() {
    return this.aCard;
  }

  /**
   * @return visible or not
   */
  public boolean isVisible() {
    return this.aVisible;
  }

  /**
   * @param pVisible visible or not
   */
  public void setVisible(boolean pVisible) {
    this.aVisible = pVisible;
  }

  @Override
  public String toString() {
    return this.aCard.toString();
  }

}
