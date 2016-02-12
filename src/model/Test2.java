package model;

import model.GameModel.FromPileToStack;
import model.GameModel.MoveInWorkStack;
import model.GameModel.MoveToSuitStack;
import model.GameModel.Strategy;

public class Test2 {
	public static void main(String[] args)
	{
		GameModel engine = GameModel.getInstance();
		engine.reset();
		System.out.println(engine.toString());
		
	    Strategy moveToWorkStack = engine.new MoveInWorkStack();
	    Strategy moveToSuitStack = engine.new MoveToSuitStack();
	    Strategy pileToWorStack = engine.new FromPileToStack();
	    
	    
	    int inState = -1;
	    int outState = -1;
	    int away = 0;
	    
	    engine.autoPlay(moveToSuitStack);
	

    	System.out.println(engine.toString());
	    }

	}

