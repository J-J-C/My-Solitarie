package GUI;


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
import model.GameModel.SuitStackIndex;
import model.GameModelObserver;
import resource.Card.Rank;
import resource.Card.Suit;
import resource.CardFactory;

public class SuitStackView extends StackPane implements GameModelObserver{
	
	private static final String BORDER_STYLE = "-fx-border-color: lightgray;"
			+ "-fx-border-width: 3;" + " -fx-border-radius: 10.0";
	
	
	
	public SuitStackView(SuitStackIndex pIndex){
		
		this.setMaxHeight(CardImage.getCardLength());
		this.setMaxWidth(CardImage.getCardWidth());
		this.setStyle(BORDER_STYLE);
		
		Label temp = new Label(pIndex.toString());
		temp.setFont(new Font("Arial", 10));

		this.getChildren().add(temp);
		//aSuitStack.getChildren().add(CardImage.getImage(CardFactory.getCard(Rank.ACE, Suit.values()[b])));
		this.getChildren().add(CardImage.getBack());		
		this.setVisible(true);
		GameModel.getInstance().addObserver(this);
	}



	@Override
	public void stateChanged() {
		// TODO Auto-generated method stub
		
	}
		
}
	
	
	
	

