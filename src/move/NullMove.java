package move;

import model.GameModel;

public class NullMove implements Move {

  @Override
  public void perform(GameModel pModel) {
    // this method does nothing on purpose
  }

  @Override
  public void undo(GameModel pModel) {
    // this method does nothing on purpose
  }


}
