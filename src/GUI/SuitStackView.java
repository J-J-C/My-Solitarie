package GUI;




import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import model.GameModel;
import model.GameModel.SuitStackIndex;
import model.GameModelObserver;
import resource.Card;
import resource.Card.Rank;
import resource.Card.Suit;
import resource.CardFactory;

public class SuitStackView extends StackPane implements GameModelObserver
{
	
	private final SuitStackIndex aIndex;
	private static final String BORDER_STYLE = "-fx-border-color: lightgray;"
			+ "-fx-border-width: 3;" + " -fx-border-radius: 10.0";
	
	
	
	public SuitStackView(SuitStackIndex pIndex)
	{
		
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
	public void stateChanged() 
	{
		if( GameModel.getInstance().isEmptySuitStack(aIndex))
		{
			getChildren().set(0, CardImage.getBack());
		}
		else
		{
			getChildren().get(0).setVisible(true);
			System.out.println("updated");
			ImageView topCard = CardImage.getImage(GameModel.getInstance().peekSuitStackCard(aIndex));
			if(topCard != (ImageView) this.getChildren().get(this.getChildren().size()-1))
			{
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
	
	
	
	
	
	private void registerDragDetected(ImageView pImageView){
		pImageView.setOnDragDetected(new EventHandler <MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				System.out.println("drag");
				ClipboardContent content = new ClipboardContent();
	            content.putString(GameModel.getInstance().peekSuitStackCard(aIndex).toString());
	            System.out.println(content.getString());
	            Dragboard db = pImageView.startDragAndDrop(TransferMode.ANY);
	            db.setDragView(new Image("/drag.png"), 7, 7); 
	            db.setContent(content); 
	            event.consume();
			}
			
		});
		
	}
	
	private void registerDragOver(ImageView pImageView){
		pImageView.setOnDragOver(new EventHandler <DragEvent>(){

			@Override
			public void handle(DragEvent event) {
				System.out.println("DragOver");
				if (event.getGestureSource() != pImageView && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
					if(GameModel.getInstance().canMoveToSuitStack(CardFactory.getCard(event.getDragboard().getString()), aIndex)){
						event.acceptTransferModes(TransferMode.MOVE);
					}
                    event.consume();
                }
			}
				
				
		});
	}
	
	private void registerDragEntered(ImageView pImageView){
		pImageView.setOnDragEntered(new EventHandler <DragEvent>(){

			@Override
			public void handle(DragEvent event) {
				System.out.println("DragEnter");
				
				event.consume();
				
			}
			
		});
	}
	
	private void registerDragExited(ImageView pImageView){
		pImageView.setOnDragExited(new EventHandler <DragEvent>(){

			@Override
			public void handle(DragEvent event) {
				System.out.println("DragExited");
				event.consume();
			}
			
		});
	}
	
	private void registerDragDone(ImageView pImageView){
		pImageView.setOnDragDone(new EventHandler <DragEvent>(){

			@Override
			public void handle(DragEvent event) {
				System.out.println("DragDone");
				event.consume();
			}
			
		});
	}
	
	
	/**
	 * Register drop event to the suitStack
	 */
	private void registerDropEvent(ImageView pImageView){
		pImageView.setOnDragDropped(new EventHandler<DragEvent>(){
			@Override
			public void handle(DragEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Prepare to Drop");
				Dragboard a = event.getDragboard();
				boolean success = false;
				if(a.hasString()){
					System.out.println(GameModel.getInstance().isEmptySuitStack(aIndex));
					ImageView cardImage = CardImage.getImage(a.getString());
					Card aCard = CardFactory.getCard(a.getString());
					System.out.println(aCard.toString());
					GameModel.getInstance().popDiscardPile();
					GameModel.getInstance().moveToSuitStack(aCard, aIndex);
					success = true;
					System.out.println("aaaa");
    			}
    			event.setDropCompleted(success);
    			event.consume();
			}
		});
	}



	
}
	
	
	
	

