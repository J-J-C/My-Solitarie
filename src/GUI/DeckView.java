package GUI;

import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.GameModel;
import model.GameModelObserver;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;


public class DeckView extends HBox implements GameModelObserver{
	private static final String BUTTON_STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private static final String BUTTON_STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
	public DeckView(){
		Button deckButton = new Button("", CardImage.getBack());
		//deckButton.setStyle(BUTTON_STYLE_NORMAL);
		
		deckButton.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event){
				if(GameModel.getInstance().isEmptyDeck()){
					GameModel.getInstance().reset();
				}else{
					GameModel.getInstance().discard();
				}
			}
		});
		this.getChildren().add(deckButton);
		GameModel.getInstance().addObserver(this);
		
	}
	
	@Override
	public void stateChanged()
	{
		getChildren().get(0);
	}
	
	public void reset()
	{
		getChildren().get(0).setVisible(true);
	}
	
	
	
	

}
