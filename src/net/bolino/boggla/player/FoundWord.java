package net.bolino.boggla.player;

/**
 * Represents a word found by the player at current round, i.e. given dice set on
 * board.
 * @author frb
 * @version 1.0
 * @created 04-Mrz-2009 14:44:15
 */
/**
 * @author frb
 * 
 */
public class FoundWord {

	public static final int INVALIDWORD = 3;
	public static final int IMPOSSIBLEWORD = 2;
	public static final int DOUBLEWORD = 1;
	public static final int VALIDWORD = 0;
	public static final int NOTCHECKED = -1;
	/**
	 * word the player found at current round, i.e. for given dice set on board
	 */
	private String foundWord;
	/**
	 * type of foundWord, i.e. 0 = word valid for given dice set and unique for
	 * all players, i.e. player gets points for 1 = word is valid, but found by
	 * another player too, no points 2 = word not possible for given dice set 3
	 * = word not valid as not found in reference word list
	 */
	private int type = FoundWord.NOTCHECKED;
	/**
	 * 
	 */
	public Player m_Player;

	/**
	 * 
	 */
	public FoundWord() {

	}

	/**
	 * @param word
	 */
	public FoundWord(String word) {
		this.foundWord = word;

	}

	/**
	 * word the player found at current round, i.e. for given dice set on board
	 */
	public String getWord() {
		return foundWord;
	}

	/**
	 * points the player gets for given word
	 */
	public int getpoints() {
		int points = 0;
		if(foundWord.length()<3)
			points = 0;
		else if(foundWord.length()==3 || foundWord.length()==4)
			points = 1;
		else if(foundWord.length()==5)
			points = 2;
		else if(foundWord.length()==6)
			points = 3;
		else if(foundWord.length()>=7)
			points = 5;
		return points;
	}

	/**
	 * type of foundWord, i.e. 0 = word valid for given dice set and unique for
	 * all players, i.e. player gets points for 1 = word is valid, but found by
	 * another player too, no points 2 = word not possible for given dice set 3
	 * = word not valid as not found in reference word list
	 */
	public int gettype() {
		return type;
	}

	/**
	 * word the player found at current round, i.e. for given dice set on board
	 * 
	 * @param newVal
	 */
	public void setfoundWord(String newVal) {
		foundWord = newVal;
	}

	/**
	 * type of foundWord, i.e. 0 = word valid for given dice set and unique for
	 * all players, i.e. player gets points for 1 = word is valid, but found by
	 * another player too, no points 2 = word not possible for given dice set 3
	 * = word not valid as not found in reference word list
	 * 
	 * @param newVal
	 */
	public void settype(int newVal) {
		type = newVal;
	}

}