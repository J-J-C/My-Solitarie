package resource;

public class CardView {
	
	private Card aCard;
	private boolean aVisible;
	
	public CardView(Card pCard){
		this.aCard = pCard;
		this.aVisible = false;
	}
	
	
	public void setVisible(boolean b){
		this.aVisible = b;
	}
	
	public Card getCard(){
		return this.aCard;
	}
	
	public boolean isVisible(){
		return this.aVisible;
	}

}
