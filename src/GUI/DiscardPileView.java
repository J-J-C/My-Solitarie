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
			cardImage.setOnDragDetected(new EventHandler<MouseEvent>(){
				
				@Override
				public void handle(MouseEvent event) {
					
					System.out.println("drag");
					ClipboardContent content = new ClipboardContent();
		            content.putString(aCard.toString());
		            System.out.println(content.getString());
		            Dragboard db = cardImage.startDragAndDrop(TransferMode.ANY);
		            db.setDragView(new Image("/drag.png"), 7, 7); 
		            db.setContent(content); 
		            
		            event.consume();
				}
	    	});
			
			cardImage.setOnDragDone(new EventHandler<DragEvent>(){

				@Override
				public void handle(DragEvent event) {
					// TODO Auto-generated method stub
					 /* the drag-and-drop gesture ended */
					
	                System.out.println("onDragDone");
	                
	                event.consume();
				}
				
			});
			
			
			this.getChildren().set(0, cardImage);
			this.getChildren().get(0).setVisible(true);
		}	
	}
}
