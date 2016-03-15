package GUI;

import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.GameModel;
import model.GameModelObserver;
import resource.Card;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;

public class DiscardPileView extends HBox implements GameModelObserver{
	
	
	public DiscardPileView(){
		// set up the GUI of the discard pile
		// will add drag handler later
		ImageView card = CardImage.getBack();
		card.setVisible(false);
		getChildren().add(card);
    	GameModel.getInstance().addObserver(this);	
	}
	
	
	
	@Override
	public void stateChanged() {
		
		if(GameModel.getInstance().isEmptyDiscardPile()){
			this.getChildren().get(0).setVisible(false);
		}else{
			Card aCard = GameModel.getInstance().peekDiscard();
			ImageView cardImage = CardImage.getImage(aCard);
			this.getChildren().set(0, cardImage);
			this.getChildren().get(0).setVisible(true);
		}

		
	}

}
