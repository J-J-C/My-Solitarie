package GUI;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import model.GameModel;
import model.GameModel.StackIndex;
import model.GameModelObserver;
import resource.Card;
import resource.CardFactory;
import resource.CardView;

public class WorkStackView extends VBox implements GameModelObserver {

  StackIndex aIndex;


  /**
   * Initialize the Working Stack
   */
  public WorkStackView(StackIndex pIndex) {
    // this method is bug-free
    aIndex = pIndex;
    this.setSpacing(-80);
    for (CardView aCard : GameModel.getInstance().getAllCards(aIndex)) {
      if (aCard.isVisible()) {
        ImageView topCard = CardImage.getImage(aCard.getCard());
        registerDragDetected(topCard, aCard.getCard());
        registerDropEvent(topCard);
        registerDragExited(topCard);
        registerDragEntered(topCard);
        registerDragOver(topCard);
        registerDragDone(topCard);
        this.getChildren().add(topCard);
      } else {
        this.getChildren().add(CardImage.getBack());
      }
    }
    this.setVisible(true);
    GameModel.getInstance().addObserver(this);
  }

  /**
   * Brutal Force way to update the state of WorkStack Very inefficient but does the job lol.
   */
  @Override
  public void stateChanged() {
    // remove all the children, then add them back in.
    this.getChildren().removeAll(getChildren());
    if (GameModel.getInstance().isEmptyWorkStack(aIndex)) {
      ImageView nullImage = new ImageView();
      Image image = new Image("/red_joker.png");
      nullImage.setImage(image);
      nullImage.setFitWidth(80);
      nullImage.setFitHeight(140);
      nullImage.setPreserveRatio(true);
      nullImage.setSmooth(true);
      nullImage.setCache(true);
      registerDropEvent(nullImage);
      registerDragExited(nullImage);
      registerDragEntered(nullImage);
      registerDragOver(nullImage);
      registerDragDone(nullImage);
      this.getChildren().add(nullImage);
    } else {
      for (CardView aCard : GameModel.getInstance().getAllCards(aIndex)) {
        if (aCard.isVisible()) {
          ImageView topCard = CardImage.getImage(aCard.getCard());
          registerDragDetected(topCard, aCard.getCard());
          registerDropEvent(topCard);
          registerDragExited(topCard);
          registerDragEntered(topCard);
          registerDragOver(topCard);
          registerDragDone(topCard);
          this.getChildren().add(topCard);

        } else {
          this.getChildren().add(CardImage.getBack());
        }
      }
    }
    this.setVisible(true);
  }


  /**
   * TODO
   */
  private void registerDragDetected(ImageView pImageView, Card aCard) {
    pImageView.setOnDragDetected(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        ClipboardContent content = new ClipboardContent();
        // TODO: here is the problem
        content.putString(aCard.toString());
        Dragboard db = pImageView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(new Image("/drag.png"), 7, 7);
        db.setContent(content);
        event.consume();
      }

    });

  }

  /**
   *
   */
  private void registerDragOver(ImageView pImageView) {
    pImageView.setOnDragOver(new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        if (event.getGestureSource() != pImageView && event.getDragboard().hasString()) {

          if (GameModel.getInstance()
              .canMoveToWorkStack(CardFactory.getCard(event.getDragboard().getString()), aIndex)) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
          event.consume();
        }
      }


    });
  }

  /**
   * This should be bug-free
   */
  private void registerDragEntered(ImageView pImageView) {
    pImageView.setOnDragEntered(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        event.consume();
      }
    });
  }

  /**
   * This should be bug-free
   */
  private void registerDragExited(ImageView pImageView) {
    pImageView.setOnDragExited(new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {
        event.consume();
      }

    });
  }

  /**
   * This should be bug-free
   */
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
          GameModel.getInstance().moveToWorkStack(aCard, aIndex);
          success = true;
        }
        event.setDropCompleted(success);
        event.consume();
      }
    });
  }


}
