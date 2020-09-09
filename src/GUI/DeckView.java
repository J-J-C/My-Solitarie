package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.GameModel;
import model.GameModelObserver;


public class DeckView extends HBox implements GameModelObserver {

  // code from prof. Robillard Line 16 17
  @SuppressWarnings("unused")
  private static final String BUTTON_STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
  @SuppressWarnings("unused")
  private static final String BUTTON_STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
  private Button deckButton;

  public DeckView() {
    deckButton = new Button("", CardImage.getBack());
    registerClickEvent();
    this.getChildren().add(deckButton);
    GameModel.getInstance().addObserver(this);
  }


  /**
   * Add click functionality for the button. When the button clicked, the top card from the deck
   * will be discarded.
   */
  private void registerClickEvent() {
    deckButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (GameModel.getInstance().isEmptyDeck()) {
          GameModel.getInstance().reset();
        } else {
          GameModel.getInstance().discard();
        }
      }
    });
  }


  @Override
  public void stateChanged() {
    if (GameModel.getInstance().isEmptyDeck()) {
      ImageView temp = new ImageView("/over123.jpg");
      temp.setFitHeight(CardImage.getCardLength());
      temp.setFitHeight(CardImage.getCardWidth());
      temp.setPreserveRatio(true);
      temp.setSmooth(true);
      temp.setCache(true);
      ((Button) getChildren().get(0)).setGraphic(temp);
    } else {
      ((Button) getChildren().get(0)).setGraphic(CardImage.getBack());
    }

  }

  public void reset() {
    getChildren().get(0).setVisible(true);
  }
}
