package model;

import model.WorkingStackManager.Index;

import resource.Card;
import model.GameModel.MoveInWorkStack;
import model.GameModel.Strategy;;

public class Test 
{
	/**
	 * 
	 * @param pArgs randome shit
	 */
	public static void main(String[] pArgs)
	{
		GameModel engine = GameModel.getInstance();
		engine.reset();
		System.out.println(engine.toString());
		
	    Strategy moveToWorkStack = engine.new MoveInWorkStack();
	    Strategy moveToSuitStack = engine.new MoveToSuitStack();
	    int state = -1;
	    while(state != engine.getState())
	    {
	    	state = engine.getState();
	    	engine.autoPlay(moveToWorkStack);
		    engine.autoPlay(moveToSuitStack);
		    System.out.println(engine.toString());
		    
	    }
//	    engine.autoPlay(moveToWorkStack);
//	    engine.autoPlay(moveToSuitStack);
//	    System.out.println(engine.toString());
//	    engine.autoPlay(moveToWorkStack);


		System.out.println(engine.toString());
	}

}
