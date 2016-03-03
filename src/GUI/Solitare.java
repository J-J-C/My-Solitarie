package GUI;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.control.Label;

public class Solitare extends Application{
	
	private DeckView aDeckView = new DeckView();
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("纸牌");
		GridPane root = new GridPane();
        root.setStyle("-fx-background-color: green;");
        root.add(aDeckView, 0, 0);
        
//        ImageView back = new ImageView();
//		Image backView = new Image("/b.gif");
//		
//		Label a = new Label();
//		a.setGraphic(back);
//		back.setImage(backView);
//		back.setFitWidth(80);
//		back.setPreserveRatio(true);
//		back.setSmooth(true);
//		back.setCache(true);
//		root.add(a, 0, 0);
		Scene mainBoard = new Scene(root, 500, 500);
		
		primaryStage.setScene(mainBoard);
		primaryStage.show();

		primaryStage.show();
		
	}

}
