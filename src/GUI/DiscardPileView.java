package GUI;

import javafx.scene.image.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import model.GameModel;
import model.GameModelObserver;
import resource.Card;
import javafx.event.EventHandler;

public class DiscardPileView extends HBox implements GameModelObserver{
	
	
	public DiscardPileView(){
		// set up the GUI of the discard pile
		// will add drag handler later
		ImageView nullImage = new ImageView();
		
		getChildren().add(nullImage);
    	GameModel.getInstance().addObserver(this);

	}
	
	
	
	@Override
	public void stateChanged() {
		
		if(GameModel.getInstance().isEmptyDiscardPile()){
			
			this.getChildren().removeAll(getChildren());
		}else{
			Card aCard = GameModel.getInstance().peekDiscard();
			ImageView cardImage = CardImage.getImage(aCard);
			cardImage.setOnDragDetected(new EventHandler<MouseEvent>(){
				
				@Override
				public void handle(MouseEvent event) {
					
					ClipboardContent content = new ClipboardContent();
		            content.putString(aCard.toString());
		            Dragboard db = cardImage.startDragAndDrop(TransferMode.ANY);
		            db.setDragView(new Image("/drag.png"), 7, 7); 
		            db.setContent(content); 
		            
		            event.consume();
				}
	    	});	
			cardImage.setOnDragDone(new EventHandler<DragEvent>(){

				@Override
				public void handle(DragEvent event) {
	                
	                event.consume();
				}
				
			});
			
			if(this.getChildren().size() == 0){
				this.getChildren().add(cardImage);
			}else{
				this.getChildren().set(0, cardImage);
				this.getChildren().get(0).setVisible(true);
			}
			
		}	
	}
}
