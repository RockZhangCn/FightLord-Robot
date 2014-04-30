
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Player {
	private final static String TAG = Player.class.getSimpleName();
	public enum PlayerPosition {Left, Middle, Right };

	private PlayerPosition sitPosition;//
	private boolean isLord;// 
	private int currself;// 
	private boolean person;//
	private List<Card> cards = new ArrayList<Card>();//
	private List<Card> outcards = new ArrayList<Card>();//
	
	private List<Card> alreadyKnownCards = new ArrayList<Card>(); //
	
	private List<CardModel> cardModel = new ArrayList<CardModel>();
	
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
			if(card.getCardIndex() == 11)
				System.out.print("J" + " ");
			
			else if(card.getCardIndex() == 12)
				System.out.print("Q" + " ");
			
			else if(card.getCardIndex() == 13)
				System.out.print("K" + " ");
			
			else if(card.getCardIndex() == 14)
				System.out.print("A" + " ");
			
			else if(card.getCardIndex() == 15)
				System.out.print("2" + " ");

			else if(card.getCardIndex() == 16)
				System.out.print("xw" + " ");

			else if(card.getCardIndex() == 17)
				System.out.print("dw" + " ");

			else System.out.print(card.getCardIndex() + " ");
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
		this.alreadyKnownCards.clear();
	}

	public void clearOut() {
		this.outcards.clear();
	}


	public void sort() {
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
		System.out.println("my card model:");
		for(CardModel cardmodel : cardModel)
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
		int[] value = {3, 3, 6, 7, 7, 8, 8, 9, 10, 10, 12, 14, 14, 15, 15, 16, 17};
		cards = new ArrayList<Card>();
		for( int v : value)
		{
			cards.add(new Card("", v));
		}
		*/

		sort();

		printAllCards();
		
		int curCard = getCardIndex(0);
		int realStart = curCard;
		int cardLineIndex = curCard; 
		int currentLinelen = 0;

		int repeatCount = 1;
		
		for(int i = 0;  i < cards.size() && getCardIndex(i) <= 14 ; i++) // cards[7]
		{
			curCard = getCardIndex(i);
			if(curCard == cardLineIndex)
			{
				currentLinelen++;
				cardLineIndex++;
				repeatCount = 1;
			}

			else if(curCard < cardLineIndex )
			{
				repeatCount++;
				
				switch(repeatCount)
				{
					case 2:
						List<Card> temp2 = new ArrayList<Card>();
						temp2.add(cards.get(i));
						temp2.add(cards.get(i-1));
						cardModel.add(new CardModel(CardModel.Type.PAIR, temp2));
						break;
					
					case 3:
						List<Card> temp3 = new ArrayList<Card>();
						temp3.add(cards.get(i));
						temp3.add(cards.get(i-1));
						temp3.add(cards.get(i-2));
						cardModel.add(new CardModel(CardModel.Type.TRIPPLE, temp3));
						break;
					
					case 4:
						List<Card> temp4 = new ArrayList<Card>();
						temp4.add(cards.get(i));
						temp4.add(cards.get(i-1));
						temp4.add(cards.get(i-2));
						temp4.add(cards.get(i-3));
						cardModel.add(new CardModel(CardModel.Type.FOURBOMB, temp4));
						break;
					default:
						//Error here.
			
				}		
				//System.out.println("We have " + repeatCount + " count " + cards.get(i));
			}
			else if(curCard > cardLineIndex)
			{
				if(currentLinelen >= 5)
				{
					System.out.println("We have lines lenth  = " + currentLinelen + ", line is :");

					int cardValue = 3;
					for(int j = 0; j < currentLinelen ; j++)
					{
						cardValue = realStart + j;	
						System.out.print(new Card("", cardValue) + " " );
					}
					System.out.println("");
				}

				realStart = curCard;
				cardLineIndex = realStart + 1;
				currentLinelen = 1;
				repeatCount = 1;
			}
		}

		if(currentLinelen >= 5)
		{
			System.out.println("We have lines lenth  = " + currentLinelen + ", line is :");

			int cardValue = 3;
			for(int j = 0; j < currentLinelen ; j++)
			{
				cardValue = realStart + j;	
				System.out.print(new Card("", cardValue) + " " );
			}
			System.out.println("");
		}
		return currself;
	}
}
