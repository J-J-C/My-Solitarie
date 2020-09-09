package move;


import model.GameModel;
import resource.Card;

// tested. Worked
public class DiscardMove implements Move {

  /**
   * Creates a discard move.
   */
  public DiscardMove() {
  }

  @Override
  public void perform(GameModel pModel) {
    assert !pModel.isEmptyDeck();
    pModel.discard();
  }

  @Override
  public void undo(GameModel pModel) {
    Card aCard = pModel.popDiscardPile();
    pModel.push(aCard);
  }

}
