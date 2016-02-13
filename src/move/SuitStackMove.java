package move;



import model.GameModel;

import model.GameModel.Index;
import model.GameModel.SuitStackIndex;
import resource.Card;

public class SuitStackMove implements Move{
	private Card target;
	private SuitStackIndex destination; 
	private Index origin;
	private boolean fromDiscard = false;
	
	public SuitStackMove(Card pCard, SuitStackIndex pDestination, Index pOrigin){
		this.target = pCard;
		this.destination = pDestination;
		this.origin = pOrigin;
	}
	
	@Override
	public void perform(GameModel pModel) {
		assert pModel.canMoveToSuitStack(target, destination);
		pModel.moveToSuitStack(target, destination);
	}

	@Override
	public void undo(GameModel pModel){
		
		
	}

}
