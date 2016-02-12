package move;

import model.GameModel;
import model.WorkingStackManager.Index;
import resource.Card;

public class WorkStackMove implements Move 
{
	private Card aCard;
	private Index destination; 
	private Index origin;
	
	public WorkStackMove(Card pCard, Index pIndex){
		this.aCard = pCard;
		this.destination = pIndex;
	}
	
	public void setOrigin(Index pIndex){
		this.origin = pIndex;
	}
	
	@Override
	public void performe(GameModel pModel) {
		assert pModel.
	}

	@Override
	public void undo(GameModel pModel) {
		// TODO Auto-generated method stub
		
	}

}
