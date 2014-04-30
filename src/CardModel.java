
import java.util.List;

public class CardModel implements Comparable<CardModel> {

	public enum Type 
	{  
		SINGLE(1),     PAIR(2),     TRIPPLE(3), TRIPPLE1SINGLE(4),     TRIPPLE1PAIR(5),  FOURBOMB(4), FOUR2SINGLE(6), FOUR2PAIR(8), 
	    SINGLELINE(1), PAIRLINE(2), TRIPPLELINE(3), TRIPPLE1SINGLELINE(4), TRIPPLE1PAIRLINE(5),  TOPBOMB(2); 
		               
		private int value = 0;
		
		private Type(int value)
		{
			this.value = value;
		}
		 
		//Base count for Lines type.
		public int value() 
		{
		    return this.value;
		}	
	};

    private int cardCount;
	private int length;//for lines.  repeatTimes;
	private Type type;
	private List<Card> composeCards;
	
	private Card name;
	
	public CardModel(Type type, List<Card> composeCards)
	{
		this.composeCards = composeCards;
		this.type = type;
		
		cardCount = composeCards.size();
		length = cardCount / type.value();
		name = composeCards.get(0);
		
		setConflict();
	}
	
	private void  setConflict()
	{
		for(Card c : composeCards)
		{
			if(type == Type.PAIR)
				c.setConflictValue(1); //1 is defined in Card as final static int.
			else if(type == Type.TRIPPLE)
				c.setConflictValue(2);
			else if(type == Type.FOURBOMB )
				c.setConflictValue(4);
			else if(type == Type.SINGLELINE)
				c.setConflictValue(8);
			else if(type == Type.PAIRLINE)
				c.setConflictValue(16);
			else if(type == Type.TRIPPLELINE)
				c.setConflictValue(32);
		}
	}
	
	public Card getModelName() {
		return this.name;
	}
	
	public int getCardCount() {
		return this.cardCount;
	}
	
	public int getLength() {
		return this.length;
	}

	public Type getType() {
		return this.type;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}

	public List<Card> getComposeCards() {
		return composeCards;
	}
	
	@Override
	public String toString()
	{
		return type.value + " count " + name ;
		
	}
	
	@Override
	public int compareTo(CardModel another) {
		if( this.type.value - another.type.value != 0)
			return this.type.value - another.type.value;
		else
			return this.name.getCardIndex() - another.name.getCardIndex();
	}

}
