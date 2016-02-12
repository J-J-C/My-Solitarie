package move;

import model.GameModel;

public interface Move {
	
	void performe(GameModel pModel);
	
	void undo(GameModel pModel);
}
