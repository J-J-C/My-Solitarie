package model;


import model.GameModel.Strategy;;
/**
 * 
 * @author JiajunChen
 *
 */
public final class Test 
{
	private Test(){}
	/**
	 * 
	 * @param pArgs randome shit
	 */
	
	public static void main(String[] pArgs){
		int max = 0;
		double win = 0;
		double cum = 0;
		int games = 500;
		int error = 0;
		for(int i = 0; i <= games; i++){
			try{
				int score = driver();	
				if(score > max) max = score;
				if(score > 50) win++;
				cum += score;
			}catch (Exception e) {
				System.out.println("will fix later");
				error++;
			}
		}
		cum /= games;
		System.out.println( "Max score is " + max + "\nWin ratio is " +
				win/games*100 +"\nAverage Points is " + cum);
		System.out.println("Number of illed game: " + error + " out of " + games);
	}
	
	public static int driver(){
		GameModel engine = GameModel.getInstance();
		engine.reset();
//		System.out.println(engine.toString());
		
	    Strategy moveToWorkStack = engine.new MoveInWorkStack();
	    Strategy moveToSuitStack = engine.new MoveToSuitStack();
	    Strategy pileToWorStack = engine.new FromPileToStack();
	    
	    
	    int inState = -1;
	    int outState = -1;
	    int away = 0;
	    while(!engine.isEmptyDeck() && outState != engine.getState()) 
	    {
	    	
	    	outState = engine.getState();
	    	//inState = outState;
	    	int tracker1 = 0;
	    	int tracker2 = 0;
	    	while(inState != engine.getState() && tracker1 < 1000)
	    	{
	    		int test = 0;
	    		inState = engine.getState();
	    		test = inState + 1;
	    		//System.out.println("test");
	    		engine.autoPlay(moveToSuitStack);
		    	engine.autoPlay(moveToWorkStack);
		    	tracker1++;
			    if(!engine.isEmptyDiscardPile() && test != engine.getState() && tracker2 < 1000)
			    {
			    	tracker2++;
			    	test = engine.getState();
//			    	System.out.println("test");
			    	engine.autoPlay(moveToSuitStack);
			    	engine.autoPlay(moveToWorkStack);
			    	engine.autoPlay(pileToWorStack);
			    }
	    	}
	    	away++;
	    	engine.discard();
	    	
//	    System.out.println("Discard " + away + " card(s)");
	    
	    }
	   

//		System.out.println(engine.toString());
//		System.out.println("Score is " + engine.getScore() + "\n");
		return engine.getScore();
	}
	

}
