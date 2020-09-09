package GUI;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.GameModel;
import model.GameModel.StackIndex;
import model.GameModel.SuitStackIndex;

public class Solitare extends Application {

  private static final int MARGIN_OUTER = 10;
  private DeckView aDeckView = new DeckView();
  private DiscardPileView aDiscardPileView = new DiscardPileView();
  private List<SuitStackView> aSuitStackView = new ArrayList<>();
  private List<WorkStackView> aWorkStackView = new ArrayList<>();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    GameModel.getInstance().reset();
    createWorkStacks();
    createSuitStacks();
    primaryStage.setTitle("纸牌");
    GridPane root = new GridPane();
    root.setStyle("-fx-background-color: green;");
    root.setHgap(MARGIN_OUTER);
    root.setVgap(MARGIN_OUTER);
    root.setPadding(new Insets(MARGIN_OUTER));
    root.add(aDeckView, 0, 0);
    root.add(aDiscardPileView, 1, 0);

    int a = 0;
    for (WorkStackView temp : aWorkStackView) {
      root.add(temp, a, 1);
      a++;
    }
    a = a - 4;
    for (SuitStackView temp : aSuitStackView) {
      root.add(temp, a, 0);
      a++;
    }

    Scene mainBoard = new Scene(root, 700, 530);

    primaryStage.setScene(mainBoard);

    primaryStage.show();

  }

  private void createWorkStacks() {
    for (StackIndex aIndex : GameModel.StackIndex.values()) {
      aWorkStackView.add(new WorkStackView(aIndex));
    }
  }

  private void createSuitStacks() {
    for (SuitStackIndex aIndex : GameModel.SuitStackIndex.values()) {
      aSuitStackView.add(new SuitStackView(aIndex));
    }
  }

}
