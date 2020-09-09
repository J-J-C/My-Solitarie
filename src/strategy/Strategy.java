package strategy;

import model.GameModel;
import move.Move;

/**
 * Strategy Interface.
 *
 * @author JiajunChen
 */
public interface Strategy {

  /**
   * @param pEngine gamemodel instance
   * @return move that needs to be performed.
   */
  Move computeNextMove(GameModel pEngine);

}
