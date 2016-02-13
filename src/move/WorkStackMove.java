package move;



import model.GameModel;
import model.GameModel.*;


import resource.Card;

/**
 * Move inside the workStack
 * @author JiajunChen
 *
 */
public class WorkStackMove implements Move 
{
	private Card target;
	private StackIndex destination; 
	private Index origin;
	
	public WorkStackMove(Card pCard, Index pOrigin, StackIndex pDestination)
	{
		this.target = pCard;
		this.origin = pOrigin;
		this.destination = pDestination;
	}
	
	@Override
	public void perform(GameModel pModel) 
	{
		assert pModel.canMoveToWorkStack(target, destination);
		if(!origin.equals(CardSources.DISCARD_PILE)){
			pModel.popCard(origin);
		}else{
			pModel.popDiscardPile();
		}
		pModel.moveToWorkStack(target, destination);
	}

	@Override
	public void undo(GameModel pModel)
	{
		// pModel.moveToWorkStack(target, origin);
	}



}
