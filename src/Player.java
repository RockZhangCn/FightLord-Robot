
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
		int[] value = {3, 4, 4, 5, 6, 7, 8, 9, 10, 12, 12, 13, 13, 13, 14, 14, 14};
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
		for( i = 0; i < cards.size(); i++) // cards[7]
		{
			curCard = getCardIndex(i);
			if(curCard == lineIndexCard)
			{
				if(curCard <= 14) //Disconnect after A.
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
				addRepeatModel(repeatCount, i);
				addSingleLine(currentLinelen, realStartCard);
				
				realStartCard = curCard;
				lineIndexCard = realStartCard + 1;
				currentLinelen = 1;

				repeatCount = 1;
			}
		}
		
		addRepeatModel(repeatCount, i);
		addSingleLine(currentLinelen, realStartCard);

		parseMultipleLine(2);
		parseMultipleLine(3);
		
		addTopModel();
		return 0;
	}
	
	private void parseMultipleLine(int type)
	{
		if( type != 2 && type != 3)
		{
			System.out.println("Parameter Error with type " + type);
			return;
		}

		final int LEN = 5 - type;
		List<Integer> pairList = new ArrayList<Integer>();

		for(CardModel cm : cardModelList)
		{
			if(   ((cm.getType() == CardModel.Type.PAIR) && type == 2)
			|| cm.getType() == CardModel.Type.TRIPPLE
			|| cm.getType() == CardModel.Type.FOURBOMB
			)
			{
				pairList.add(new Integer(cm.getModelName().getCardIndex()));
			}
		}

		int length = pairList.size();

		if(length < LEN)
			return;

		Collections.sort(pairList);

		int startCardIndex = pairList.get(0);
		int linelength = 0;
		for(int i = 0; i < length ; i++)
		{
			if(pairList.get(i) == startCardIndex)	
			{
				
				if(startCardIndex <= 14)
				{
					startCardIndex++;
					linelength++;
				}
			}
			else
			{
				addMultipleLine(linelength, startCardIndex - linelength, type);

				linelength = 1;
				startCardIndex = pairList.get(i);
				if(startCardIndex < 14)
					startCardIndex ++;
			}
		}
		
		addMultipleLine(linelength, startCardIndex - linelength, type);

	}
	
	private void addTopModel()
	{
		int size = cards.size();
		if(cards.get(size - 1).getCardIndex() == 17 && cards.get(size - 2).getCardIndex() == 16)
		{
			List<Card> topcards = new ArrayList<Card>();
			
			topcards.add(cards.get(size - 1));
			topcards.add(cards.get(size - 2));
			
			cardModelList.add(new CardModel(CardModel.Type.TOPBOMB, topcards));
		}
	}
	
	private void addMultipleLine(int lineLength, int startCard, int unit)
	{
		int leastLength = 0;
		switch(unit)
		{
			case 1:
				leastLength = 5;
				break;
				
			case 2:
				leastLength = 3;
				break;
				
			case 3:
				leastLength = 2;
				break;
			default:
				System.out.println("Parameter error");
		}
		
		if(lineLength < leastLength)
			return;
		
		List<Card> multipleLine = new ArrayList<Card>();
		int len = cards.size();
		for(int i = 0; i < len; i++)
		{
			if((cards.get(i).getCardIndex() == startCard )&& lineLength > 0)
			{
				for(int j = 0; j < unit; j++)
					multipleLine.add(cards.get(i + j));
				
				i += unit -1;
				startCard++;
				lineLength--;
			}
		}
		
		switch(unit)
		{
			case 1:
				cardModelList.add(new CardModel(CardModel.Type.SINGLELINE, multipleLine));
				break;
				
			case 2:
				cardModelList.add(new CardModel(CardModel.Type.PAIRLINE, multipleLine));
				break;
				
			case 3:
				cardModelList.add(new CardModel(CardModel.Type.TRIPPLELINE, multipleLine));
				break;
			default:
				System.out.println("Parameter error");
		}
		
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
			case 1:
				break;
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
				System.out.println("Parse cards error");
				//Error here.
		}	
	}
}
