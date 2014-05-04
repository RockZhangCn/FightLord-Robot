
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Player {
	//private final static String TAG = Player.class.getSimpleName();
	public enum PlayerPosition {Left, Middle, Right };

	private PlayerPosition sitPosition;//
	private boolean isLord;// 
	private int currself;// 
	private boolean person;//
	private List<Card> cards = new ArrayList<Card>();//
	private List<Card> outcards = new ArrayList<Card>();//
	
	private List<Card> alreadyKnownCardList = new ArrayList<Card>(); //
	
	private List<CardModel> cardModelList = new ArrayList<CardModel>();
	
	/*
	private List<Card> singleCard = new ArrayList<Card>();  //     
	private List<Card> pairCard = new ArrayList<Card>();   //
	private List<Card> tripple = new ArrayList<Card>();//
	private List<Card> trippleOneSingle = new ArrayList<Card>();//
	private List<Card> trippleOnePair = new ArrayList<Card>(); //
	private List<Card> fourTwoSingle = new ArrayList<Card>(); //
	private List<Card> fourTwoPairs = new ArrayList<Card>(); //
	private List<Card> oneSingleLines = new ArrayList<Card>();
	
	//These two generated from pairCard, trippleOneSingle, trippleOnePair
	private List<Card> twoSingleLines = new ArrayList<Card>();
	private List<Card> trippleSingleLines = new ArrayList<Card>();
	*/
	
	
	private boolean play;// 

	public Player(PlayerPosition pos, boolean person) {
		this.sitPosition = pos;
		this.person = person;
		init();
	}

	public void init() {
		this.currself = 0;
		this.isLord = false;
		this.play = false;
		clear();
	}

	public PlayerPosition getPosition() {
		return sitPosition;
	}

	public void setPosition(PlayerPosition pos) {
		this.sitPosition = pos;
	}

	public void printAllCards()
	{
		for(Card card : cards)
		{	
			System.out.print(card + " ");
		}

		System.out.println("");
			

	}

	public boolean isDizhuflag() {
		return isLord;
	}

	public void setDizhuflag(boolean dizhuflag) {
		this.isLord = dizhuflag;
	}

	public List<Card> getCards() {
		return cards;
	}


	public void addCards(Card card) {
		this.cards.add(card);
	}


	public void removeCards(List<Card> outcards) {
		this.cards.removeAll(outcards);
	}

	public List<Card> getOutcards() {
		return outcards;
	}


	public void addOutcards(Card outcard) {
		this.outcards.add(outcard);
	}

	public int getCurrself() {
		return currself;
	}

	public void setCurrself(int currself) {
		this.currself = currself;
	}

	public boolean isPerson() {
		return person;
	}


	public void clear() {
		this.cards.clear();
		this.outcards.clear();
		this.alreadyKnownCardList.clear();
	}

	public void clearOut() {
		this.outcards.clear();
	}


	public void sortPlayerCards() {
		Collections.sort(this.cards);
	}


	public void outSort() {
		Collections.sort(this.outcards);
	}


	public int size() {
		return this.cards.size();
	}

	public int outSize() {
		return this.outcards.size();
	}

	
	
	public Card getCard(int index) {
		return this.cards.get(index);
	}

	public void printCardModel()
	{
		Collections.sort(cardModelList);
		System.out.println("My card model list:");
		for(CardModel cardmodel : cardModelList)
			System.out.println(cardmodel + " ");

	}
	
	public int getCardIndex(int index) {
		return this.cards.get(index).getCardIndex();
	}

	
	public Card getOutCard(int index) {
		return this.outcards.get(index);
	}

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public int calcTotalPower()
	{
		/*
		int[] value = {3, 3, 6, 7, 7, 7, 8, 9, 10, 14, 14, 14, 14, 15, 15, 16, 17};
		cards = new ArrayList<Card>();
		for( int v : value)
		{
			cards.add(new Card("", v));
		}
		*/
		
		sortPlayerCards();
		printAllCards();
		
		int curCard = getCardIndex(0);
		int realStartCard = curCard;
		int lineIndexCard = curCard; 
		int currentLinelen = 0;

		int repeatCount = 1;
		
		int i = 0;
		for( i = 0;  i < cards.size() ; i++) // cards[7]
		{
			curCard = getCardIndex(i);
			if(curCard == lineIndexCard)
			{
				if(curCard <= 14)
				{
					currentLinelen++;
				}
				lineIndexCard++;
				//Before repeatCount is set to default value.
				addRepeatModel(repeatCount, i);	
				repeatCount = 1;
			}
			else if(curCard < lineIndexCard )
			{
				repeatCount++;
			}
			else if(curCard > lineIndexCard)
			{
				/*
				if(currentLinelen >= 5)
				{
					System.out.println("We have lines lenth  = " + currentLinelen + ", line is :");

					int cardValue = 3;
					for(int j = 0; j < currentLinelen ; j++)
					{
						cardValue = realStartCard + j;	
						System.out.print(new Card("", cardValue) + " " );
					}
					System.out.println("");
				}
				*/
				
				addSingleLine(currentLinelen, realStartCard);

				realStartCard = curCard;
				lineIndexCard = realStartCard + 1;
				currentLinelen = 1;
				
				addRepeatModel(repeatCount, i);
				
				repeatCount = 1;
			}
		}

		addRepeatModel(repeatCount, i);

		addSingleLine(currentLinelen, realStartCard);
		/*
		if(currentLinelen >= 5)
		{
			System.out.println("We have lines lenth  = " + currentLinelen + ", line is :");

			int cardValue = 3;
			for(int j = 0; j < currentLinelen ; j++)
			{
				cardValue = realStartCard + j;	
				System.out.print(new Card("", cardValue) + " " );
			}
			System.out.println("");
		}
		*/
		
		return 0;
	}
	
	public void addSingleLine(int lineLength, int startCard)
	{
		if(lineLength < 5)
			return;
		
		List<Card> singleLine = new ArrayList<Card>();
		
		for(Card c :cards)
		{
			if((c.getCardIndex() == startCard )&& lineLength > 0)
			{
				c.setConflictValue(Card.SINGLINE_CONFLICT);
				singleLine.add(c);
				startCard++;
				lineLength--;
			}
		}
		
		cardModelList.add(new CardModel(CardModel.Type.SINGLELINE, singleLine));
	}
	
	
	
	public void addRepeatModel(int repeatCount, int endIndex)
	{
		switch(repeatCount)
		{
			case 2:
				List<Card> temp2 = new ArrayList<Card>();
				
				cards.get(endIndex-1).setConflictValue(Card.PAIR_CONFLICT);
				cards.get(endIndex-2).setConflictValue(Card.PAIR_CONFLICT);
				
				temp2.add(cards.get(endIndex-1));
				temp2.add(cards.get(endIndex-2));
				
				cardModelList.add(new CardModel(CardModel.Type.PAIR, temp2));
				break;
			
			case 3:
				List<Card> temp3 = new ArrayList<Card>();
				
				temp3.add(cards.get(endIndex-1));
				temp3.add(cards.get(endIndex-2));
				temp3.add(cards.get(endIndex-3));
				
				cards.get(endIndex-1).setConflictValue(Card.TRIPPLE_CONFLICT);
				cards.get(endIndex-2).setConflictValue(Card.TRIPPLE_CONFLICT);
				cards.get(endIndex-3).setConflictValue(Card.TRIPPLE_CONFLICT);
				
				cardModelList.add(new CardModel(CardModel.Type.TRIPPLE, temp3));

				break;
			
			case 4:
				List<Card> temp4 = new ArrayList<Card>();

				cards.get(endIndex-1).setConflictValue(Card.BOMB_CONFLICT);
				cards.get(endIndex-2).setConflictValue(Card.BOMB_CONFLICT);
				cards.get(endIndex-3).setConflictValue(Card.BOMB_CONFLICT);
				cards.get(endIndex-4).setConflictValue(Card.BOMB_CONFLICT);

				temp4.add(cards.get(endIndex-1));
				temp4.add(cards.get(endIndex-2));
				temp4.add(cards.get(endIndex-3));
				temp4.add(cards.get(endIndex-4));
				
				cardModelList.add(new CardModel(CardModel.Type.FOURBOMB, temp4));
				break;
			default:
				//Error here.
	
		}	
	}
}
