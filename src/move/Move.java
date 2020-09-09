package move;

import model.GameModel;

public interface Move {

  void perform(GameModel pModel);

  void undo(GameModel pModel);
}
