


public class Card implements Comparable<Card> {

	private String cid;//
	private int cardIndex; //
	private int powerValue; //
	
	//pair 1.  tripple  2  four 4  line 8. pairline 16
	public final static int PAIR_CONFLICT = 1;
	public final static int TRIPPLE_CONFLICT = 2;
	public final static int BOMB_CONFLICT = 4;
	public final static int SINGLINE_CONFLICT = 8;
	public final static int PAIRLINE_CONFLICT = 16;
	public final static int TRIPPLELINE_CONFLICT = 32;
	
	private int conflictValue = 0;

	
	public void setConflictValue(int confilict)
	{
		conflictValue |= confilict;
	}
	
	public int getConflictValue()
	{
		return this.conflictValue;
	}
	
	//UI related begin
	// {
	private int x = 0; 
	private int y = 0; 

	private int unx;
	private int uny;

	private int ulx;
	private int uly;


	private int dnx;
	private int dny;


	private int dlx;
	private int dly;

	private int sw;

	private int width; //
	private int height; 

	private boolean clicked = false;//
	//UI related end.
	// }
	
	//private final static String TAG = Card.class.getSimpleName();

    Card(String cid, int cardIndex)
    {
		this.cid = cid;
		this.cardIndex = cardIndex;
		calcPowerValue();
	}
    
    @Override
    public String toString()
    {
    	String midStr = new Integer(cardIndex).toString();
    	
    	if(cardIndex == 11)
    		midStr = "J";
    	if(cardIndex == 12)
    		midStr = "Q";
    	if(cardIndex == 13)
    		midStr = "K";
    	if(cardIndex == 14)
    		midStr = "A";
    	if(cardIndex == 15)
    		midStr = "2";
    	if(cardIndex == 16)
    		midStr = "xw";
    	if(cardIndex == 17)
    		midStr = "dw";
    	
    	return "[" + this.cid + "]" + midStr;
    }
	
	public int getPowerValue()
	{
		return powerValue;
	}

	private void calcPowerValue() {
		// TODO Auto-generated method stub
		if(cardIndex < 3)
		{
			powerValue = 0;
		}
		else if(cardIndex <= 6)
		{
			powerValue = cardIndex;
		}
		else if(cardIndex <= 10)
		{
			powerValue = cardIndex + 1;
		}
		else if(cardIndex <= 14) //J Q K A
		{
			powerValue = cardIndex + 3;
		}
		else if(cardIndex <= 17)
		{
			powerValue = cardIndex + 5;
		}
		else
		{
			powerValue = 17 + 5;
		}
			
	}



	public int getUnx() {
		return unx;
	}

	public void setUnx(int unx) {
		this.unx = unx;
	}

	public int getUny() {
		return uny;
	}

	public void setUny(int uny) {
		this.uny = uny;
	}

	public int getUlx() {
		return ulx;
	}

	public void setUlx(int ulx) {
		this.ulx = ulx;
	}

	public int getUly() {
		return uly;
	}

	public void setUly(int uly) {
		this.uly = uly;
	}

	public int getDnx() {
		return dnx;
	}

	public void setDnx(int dnx) {
		this.dnx = dnx;
	}

	public int getDny() {
		return dny;
	}

	public void setDny(int dny) {
		this.dny = dny;
	}

	public int getDlx() {
		return dlx;
	}

	public void setDlx(int dlx) {
		this.dlx = dlx;
	}

	public int getDly() {
		return dly;
	}

	public void setDly(int dly) {
		this.dly = dly;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}


	public int getCardIndex() {
		return cardIndex;
	}

	public String getCid() {
		return cid;
	}

	public int getSw() {
		return sw;
	}

	@Override
	public int compareTo(Card another) {
		return this.cardIndex - another.cardIndex;
	}
}
