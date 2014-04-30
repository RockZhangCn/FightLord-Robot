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

    public static void initCard() {

		// A 2
		cards[0] = new Card("c1", 14);
		cards[1] = new Card("c2", 14);
		cards[2] = new Card("c3", 14);
		cards[3] = new Card("c4", 14);

		cards[4] = new Card("c5", 15);
		cards[5] = new Card("c6", 15);
		cards[6] = new Card("c7", 15);
		cards[7] = new Card("c8", 15);

	
		for (int i = 2; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				if (j < 2) {
					cards[i * 4 + j] = new Card("c" + (4 * i + j + 1), i + 1);
				} else {
					cards[i * 4 + j] = new Card("c" + (4 * i + j + 1), i + 1);
				}
			}
		}

		
		cards[52] = new Card("c" + 53, 16);
		cards[53] = new Card("c" + 54, 17);

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

		playerLeft.sort();
		playerMyself.sort();
		playerRight.sort();
	

		System.out.println("Left User");
		playerLeft.calcTotalPower();
		playerLeft.printCardModel();
		System.out.println("Middle User");
		playerMyself.calcTotalPower();
		System.out.println("Right User");
		playerRight.calcTotalPower();

	}

}
