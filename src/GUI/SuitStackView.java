package GUI;


import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import model.GameModel;
import model.GameModel.SuitStackIndex;
import model.GameModelObserver;
import resource.Card;
import resource.CardFactory;

public class SuitStackView extends StackPane implements GameModelObserver {

  private static final String BORDER_STYLE = "-fx-border-color: lightgray;"
      + "-fx-border-width: 3;" + " -fx-border-radius: 10.0";
  private final SuitStackIndex aIndex;


  public SuitStackView(SuitStackIndex pIndex) {

    aIndex = pIndex;
    this.setMaxHeight(CardImage.getCardLength());
    this.setMaxWidth(CardImage.getCardWidth());
    this.setStyle(BORDER_STYLE);

    Label temp = new Label(pIndex.toString());
    temp.setFont(new Font("Arial", 10));

    this.getChildren().add(temp);
    this.getChildren().add(CardImage.getBack());
    this.setVisible(true);
    GameModel.getInstance().addObserver(this);

    registerDragExited((ImageView) this.getChildren().get(1));
    registerDragEntered((ImageView) this.getChildren().get(1));
    registerDragOver((ImageView) this.getChildren().get(1));
    registerDropEvent((ImageView) this.getChildren().get(1));


  }

  @Override
  public void stateChanged() {
    if (GameModel.getInstance().isEmptySuitStack(aIndex)) {
      getChildren().removeAll(getChildren());
      ImageView back = CardImage.getBack();
      registerDragExited(back);
      registerDragEntered(back);
      registerDragOver(back);
      registerDropEvent(back);
      getChildren().add(back);

    } else {
      getChildren().get(0).setVisible(true);
      ImageView topCard = CardImage.getImage(GameModel.getInstance().peekSuitStackCard(aIndex));
      if (topCard != (ImageView) this.getChildren().get(this.getChildren().size() - 1)) {
        registerDragDetected(topCard);
        registerDropEvent(topCard);
        registerDragExited(topCard);
        registerDragEntered(topCard);
        registerDragOver(topCard);
        registerDragDone(topCard);
        this.getChildren().add(topCard);
      }

    }
  }


  private void registerDragDetected(ImageView pImageView) {
    pImageView.setOnDragDetected(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        ClipboardContent content = new ClipboardContent();
        content.putString(GameModel.getInstance().peekSuitStackCard(aIndex).toString());
        Dragboard db = pImageView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(new Image("/drag.png"), 7, 7);
        db.setContent(content);
        event.consume();
      }

    });

  }

  private void registerDragOver(ImageView pImageView) {
    pImageView.setOnDragOver(new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        if (event.getGestureSource() != pImageView && event.getDragboard().hasString()) {
          /* allow for both copying and moving, whatever user chooses */
          if (GameModel.getInstance()
              .canMoveToSuitStack(CardFactory.getCard(event.getDragboard().getString()), aIndex)) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
          event.consume();
        }
      }


    });
  }

  private void registerDragEntered(ImageView pImageView) {
    pImageView.setOnDragEntered(new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        event.consume();
      }
    });
  }

  private void registerDragExited(ImageView pImageView) {
    pImageView.setOnDragExited(new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        event.consume();
      }

    });
  }

  private void registerDragDone(ImageView pImageView) {
    pImageView.setOnDragDone(new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        event.consume();
      }
    });
  }


  /**
   * Register drop event to the suitStack
   */
  private void registerDropEvent(ImageView pImageView) {
    pImageView.setOnDragDropped(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        // TODO Auto-generated method stub
        Dragboard a = event.getDragboard();
        boolean success = false;
        if (a.hasString()) {
          Card aCard = CardFactory.getCard(a.getString());
          GameModel.getInstance().moveToSuitStack(aCard, aIndex);
          success = true;
        }
        event.setDropCompleted(success);
        event.consume();
      }
    });
  }


}
	
	
	
	

