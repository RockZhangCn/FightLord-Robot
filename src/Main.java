import java.util.Random;
import java.util.List;
import java.util.ArrayList;


public class Main
{
    private static Card cards[] = new Card[54];
    
    public static Player playerLeft = new Player(Player.PlayerPosition.Left, false);
	public static Player playerMyself = new Player(Player.PlayerPosition.Middle, true);
	public static Player playerRight = new Player(Player.PlayerPosition.Right, false);

    private static List<Card> dizhuList = new ArrayList<Card>();

    public static void main(String[] args)
    {
		initData();
		handCards();
    }
    

    //A --- Red Square.
    //B --- Red Peach
    //C --- Black Flower
    //D --- Black Peach
    public static void initCard() {

		// A 2
		cards[0] = new Card("A14", 14);
		cards[1] = new Card("B14", 14);
		cards[2] = new Card("C14", 14);
		cards[3] = new Card("D14", 14);

		cards[4] = new Card("A15", 15);
		cards[5] = new Card("B15", 15);
		cards[6] = new Card("C15", 15);
		cards[7] = new Card("D15", 15);

	    // 3 --- K
		for (int i = 2; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				switch(j)
				{
				case 0:
					cards[i * 4 + j] = new Card("A" + (i > 9 ? "" :"0" ) + (i + 1), i + 1);
					break;
				case 1:
					cards[i * 4 + j] = new Card("B" + (i > 9 ? "" :"0" ) + (i + 1), i + 1);
					break;
				case 2:
					cards[i * 4 + j] = new Card("C" + (i > 9 ? "" :"0" ) + (i + 1), i + 1);
					break;
				case 3:
					cards[i * 4 + j] = new Card("D" + (i > 9 ? "" :"0" ) + (i + 1), i + 1);
				}
			}
		}

		cards[52] = new Card("c" + 16, 16);
		cards[53] = new Card("c" + 17, 17);
	}


	public static void washCards() {

		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			int a = random.nextInt(54);
			int b = random.nextInt(54);
			Card k = cards[a];
			cards[a] = cards[b];
			cards[b] = k;
		}
	}

	public static void initData() {
		playerLeft.init();
		playerMyself.init();
		playerRight.init();
		
		dizhuList.clear();
		
		initCard();
		washCards();
	}


	public static void handCards() {
		System.out.println("Begin handCards");
		int t = 0;

		playerLeft.clear();
		playerMyself.clear();
		playerRight.clear();

		int i = 0;
		for (i = 0; i < 54; i++) 
		{
			if (i > 50)
			{
				dizhuList.add(cards[i]);
				continue;
			}
			switch ((t++) % 3) {
			case 0:
				playerLeft.addCards(cards[i]);
				break;
			case 1:
				playerMyself.addCards(cards[i]);
				break;
			case 2:
				playerRight.addCards(cards[i]);
				break;
			}
		}

		playerLeft.sortPlayerCards();
		playerMyself.sortPlayerCards();
		playerRight.sortPlayerCards();
	
		for(Card c : dizhuList)
			System.out.print( c + " ");

		System.out.println("\n====================================");
		System.out.println("Left User");
		playerLeft.calcTotalPower();
		playerLeft.printCardModel();
		
		System.out.println("====================================");
		System.out.println("Middle User");
		playerMyself.calcTotalPower();
		playerMyself.printCardModel();
		
		System.out.println("====================================");
		System.out.println("Right User");
		playerRight.calcTotalPower();
		playerRight.printCardModel();

	}

}
