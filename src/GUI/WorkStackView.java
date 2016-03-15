package GUI;

import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.GameModel;
import model.GameModelObserver;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import model.GameModel;
import model.GameModel.StackIndex;
import model.GameModel.SuitStackIndex;
import resource.Card.Rank;
import resource.Card.Suit;
import resource.CardFactory;
import resource.CardView;

public class WorkStackView extends VBox implements GameModelObserver{
	StackIndex aIndex;
	
	
	
	public WorkStackView(StackIndex pIndex){
		aIndex = pIndex;
		this.setSpacing(-80);
		for(CardView aCard: GameModel.getInstance().getAllCards(aIndex)){
			if(aCard.isVisible()){
				this.getChildren().add(CardImage.getImage(aCard.getCard()));
			}else{
				this.getChildren().add(CardImage.getBack());
			}
		}
		this.setVisible(true);
		GameModel.getInstance().addObserver(this);
	}
	
	/**
	 * Brutal Force way to update the state of WorkStack
	 * Very inefficient but does the job lol.
	 */
	@Override
	public void stateChanged() {
		// remove all the children then readd them.
		this.getChildren().removeAll(getChildren());
		for(CardView aCard: GameModel.getInstance().getAllCards(aIndex)){
			if(aCard.isVisible()){
				this.getChildren().add(CardImage.getImage(aCard.getCard()));
			}else{
				this.getChildren().add(CardImage.getBack());
			}
		}
		this.setVisible(true);
	}
	
	
	
	
}
