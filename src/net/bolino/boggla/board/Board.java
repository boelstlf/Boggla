package net.bolino.boggla.board;

import java.util.Random;
import java.util.Vector;

/**
 * @author bolino Contains the dices of the game. Provides methods for retrieval
 *         and randomize dices.
 */
public class Board {
	protected Vector<BoardEventListener> listener = new Vector<BoardEventListener>();
	private Dice[] dice = new Dice[16];
	Random rand = new Random();

	private final char[][] DICESET = { { 'A', 'E', 'A', 'T', 'O', 'I' },
			{ 'E', 'M', 'C', 'A', 'P', 'D' }, { 'R', 'H', 'S', 'F', 'I', 'E' },
			{ 'S', 'N', 'H', 'Y', 'G', 'L' }, { 'O', 'U', 'I', 'L', 'W', 'R' },
			{ 'W', 'V', 'N', 'A', 'D', 'Z' }, { 'E', 'S', 'N', 'T', 'O', 'D' },
			{ 'I', 'N', 'T', 'I', 'G', 'V' }, { 'R', 'P', 'S', 'L', 'T', 'U' },
			{ 'T', 'O', 'U', 'T', 'N', 'K' }, { 'L', 'R', 'C', 'A', 'S', 'L' },
			{ 'M', 'I', 'R', 'N', 'S', 'H' }, { 'O', 'I', 'X', 'R', 'O', 'F' },
			{ 'A', 'M', 'B', 'J', 'O', 'Q' }, { 'E', 'R', 'M', 'I', 'S', 'O' },
			{ 'H', 'R', 'B', 'I', 'L', 'T' } };

	private final int[][] DICENEIGHBOURS = { { -1, -1, 1, 5, 4, -1, -1, -1 },
			{ -1, -1, 2, 6, 5, 4, 0, -1 }, { -1, -1, 3, 7, 6, 5, 1, -1 },
			{ -1, -1, -1, -1, 7, 6, 2, -1 }, { 0, 1, 5, 9, 8, -1, -1, -1 },
			{ 1, 2, 6, 10, 9, 8, 4, 0 }, { 2, 3, 7, 11, 10, 9, 5, 1 },
			{ 3, -1, -1, -1, 11, 10, 6, 2 }, { 4, 5, 9, 13, 12, -1, -1, -1 },
			{ 5, 6, 10, 14, 13, 12, 8, 4 }, { 6, 7, 11, 15, 14, 13, 9, 5 },
			{ 7, -1, -1, -1, 15, 14, 10, 6 }, { 8, 9, 13, -1, -1, -1, -1, -1 },
			{ 9, 10, 14, -1, -1, -1, 12, 8 },
			{ 10, 11, 15, -1, -1, -1, 13, 9 },
			{ 11, -1, -1, -1, -1, -1, 14, 10 } };

	/**
	 * Initialize Board and Dices
	 */
	public Board() {
		this.setDices(DICESET);
	}

	/**
	 * @param diceset
	 */
	public void setDices(char[][] diceset) {
		for (int i = 0; i < 16; i++) {
			dice[i] = new Dice();
			dice[i].setupDice(diceset[i]);
		}

	}

	/**
	 * @param pos
	 * @return
	 */
	public Dice getDice(int pos) {
		return dice[pos];
	}

	/**
	 * 
	 */
	public void shuffel() {
		boolean[] pos = new boolean[16];
		int r = 0;

		Dice[] dummy = new Dice[16];

		for (int i = 0; i < 16; i++) {
			pos[i] = false;
		}

		for (int i = 0; i < 16; i++) {
			do {
				r = rand.nextInt(16);
			} while (pos[r]);
			pos[r] = true;
			dummy[r] = dice[i];
			((Dice) dummy[r]).shuffel();
		}
		dice = dummy;
		this.boardChanged();
	}

	/**
	 * @param pos
	 * @return
	 */
	public int[] getDiceNeighbours(int pos) {
		return DICENEIGHBOURS[pos];
	}

	@Override
	public String toString() {
		String msg = "";
		for (int i = 0; i < 16; i++) {
			msg += "[" + i + "]" + ((Dice) this.getDice(i)).getVisible() + "; ";
		}
		return msg;
	}

	/**
	 * @return
	 */
	public char[] getDices() {
		char[] dices = new char[16];
		for (int i = 0; i < 16; i++) {
			dices[i] = this.getDice(i).getVisible();
		}
		return dices;
	}

	/**
	 * Check whether given word is possible to be build with given dice set. Returns way of possible dice route.
	 * 
	 * @param word to be checked
	 * @return way of possible dice route. length = 0 if not possible.
	 */
	public Vector<Integer> checkWord(String word) {
		Vector<Integer> way = new Vector<Integer>();
		char wordLetter = word.toUpperCase().charAt(0);
		boolean found = false;
		int i = 0;
		// find first character of word on board
		while (!found && i < 16) {
			if (wordLetter == this.getDice(i).getVisible()) {
				way.add(new Integer(i));
				found = searchNeighbourhood(word, 1, i, way);
			}
			i++;
		}
		if (!found && way.size() > 0) {
			way.remove(way.size() - 1);
		}
		return way;
	}

	/**
	 * @param word
	 * @param foundWordInteger
	 * @param boardPos
	 * @param way
	 */
	private boolean searchNeighbourhood(String word, int wordPos, int boardPos,
			Vector<Integer> way) {
		char wordLetter = word.toUpperCase().charAt(wordPos);
		boolean found = false;

		// get neighbours
		// if neighbour match to next letter in word
		int i = 0;
		int neighbourPos = 0;
		char neighbourLetter = '-';
		// search next word letter at all neighbours
		while (i < 8 && !found) {
			neighbourPos = this.getDiceNeighbours(boardPos)[i];
			// if neighbour isn't out of range and not where we come from
			if (neighbourPos != -1 && !way.contains(new Integer(neighbourPos))) {
				// get neighbour letter
				neighbourLetter = this.getDice(neighbourPos).getVisible();
				// neighbour letter would match?
				if (neighbourLetter == wordLetter) {
					// we are not at the end
					way.addElement(new Integer(neighbourPos));
					if (wordPos == word.length() - 1) {
						found = true;
					} else {
						found = searchNeighbourhood(word, wordPos + 1,
								neighbourPos, way);
					}
				}
			}
			i++;
		}

		// add way
		if (!found) {
			way.remove(way.size() - 1);
		}
		return found;
	}

	/**
	 * Description of the Method
	 * 
	 *@param l
	 *            Description of Parameter
	 */
	public void removeBoardEventListener(BoardEventListener l) {
		listener.remove(l);
	}

	/**
	 * Description of the Method
	 * 
	 *@param l
	 *            Description of Parameter
	 */
	public void addBoardEventListener(BoardEventListener l) {
		if (!listener.contains(l)) {
			listener.add(l);
		}
	}

	/**
	 * 
	 */
	public void boardChanged() {
		for (int i = 0; i < listener.size(); i++)
			((BoardEventListener) listener.elementAt(i)).boardChanged();
	}
}
