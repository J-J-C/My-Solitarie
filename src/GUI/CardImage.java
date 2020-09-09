package GUI;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resource.Card;
import resource.Card.Rank;
import resource.Card.Suit;

/**
 * A flyweight patter for card image generation.
 *
 * @author JiajunChen
 */
public final class CardImage {

  private final static String[] prefix = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
      "jack", "queen", "king"};
  private final static String[] sufix = {"clubs", "diamonds", "spades", "hearts"};
  private final static int cardWidth = 80;
  private final static int cardLength = 140;

  private static final CardImage[][] CARDFACTORY = new CardImage[Suit.values().length][Rank
      .values().length];
  private static final HashMap<String, ImageView> table = new HashMap<>();

  private final ImageView cardImage;

  private CardImage(Card pCard) {

    int pIndex = pCard.getRank().ordinal();
    int sIndex = pCard.getSuit().ordinal();

    this.cardImage = new ImageView();
    String path = "/" + prefix[pIndex] + "_of_" + sufix[sIndex] + ".png";
    Image image = new Image(path);
    cardImage.setImage(image);
    cardImage.setFitWidth(cardWidth);
    cardImage.setPreserveRatio(true);
    cardImage.setSmooth(true);
    cardImage.setCache(true);
  }

  public static ImageView getImage(Card pCard) {
    int suitPosition = pCard.getSuit().ordinal();
    int rankPosition = pCard.getRank().ordinal();

    if (CARDFACTORY[suitPosition][rankPosition] == null) {
      CARDFACTORY[suitPosition][rankPosition] = new CardImage(pCard);
      table.put(pCard.toString(), CARDFACTORY[suitPosition][rankPosition].cardImage);
    }
    return CARDFACTORY[suitPosition][rankPosition].cardImage;
  }

  // methods overload
  public static ImageView getImage(String pString) {
    return table.get(pString);
  }


  public static int getCardWidth() {
    return cardWidth;
  }

  public static int getCardLength() {
    return cardLength;
  }

  public static ImageView getBack() {

    ImageView back = new ImageView();
    Image backView = new Image("/b.gif");

    back.setImage(backView);
    back.setFitWidth(cardWidth);
    back.setFitHeight(cardLength);
    back.setPreserveRatio(true);
    back.setSmooth(true);
    back.setCache(true);
    return back;
  }

  public String getId(Card pCard) {
    return pCard.toString();
  }

}
