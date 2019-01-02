package net.bolino.boggla.test.board;

import java.util.Vector;

import net.bolino.boggla.board.Board;
import net.bolino.boggla.board.Dice;
import junit.framework.Assert;
import junit.framework.TestCase;

public class BoardTest extends TestCase {

	public void testGetDice() {
		Board board = new Board();
		Dice dice = null;
		dice = board.getDice(0);
		Assert.assertEquals('E', dice.getVisible());
		dice = board.getDice(15);
		Assert.assertEquals('A', dice.getVisible());
	}

	private final char[][] DICESET = { { 'N' }, { 'R' }, { 'T' }, { 'E' },
			{ 'O' }, { 'E' }, { 'T' }, { 'W' }, { 'T' }, { 'N' }, { 'L' },
			{ 'M' }, { 'N' }, { 'I' }, { 'E' }, { 'H' } };
	Board board = null;
	String foundWay = "";

	/**
	 * Einfaches Wort in der Mitte der Wuerfel
	 */
	public void testFindWord_einfach() {
		String word = "TOR";
		Vector<Integer> found = board.checkWord(word);

		for (int i = 0; i < found.size(); i++) {
			Integer pos = (Integer) found.get(i);
			foundWay += pos;
		}

		Assert.assertEquals("841", foundWay);
	}

	/**
	 * Erster Treffer fuehrt nicht zum Erfolg
	 */
	public void testFindWord_Irrweg() {
		String word = "NEIN";
		Vector<Integer> found = board.checkWord(word);

		for (int i = 0; i < found.size(); i++) {
			Integer pos = (Integer) found.get(i);
			foundWay += pos;
		}

		Assert.assertEquals("9141312", foundWay);
	}

	/**
	 * Wort im rechten unteren Eck
	 */
	public void testFindWord_lastBoardPos() {
		String word = "LEHM";
		Vector<Integer> found = board.checkWord(word);

		for (int i = 0; i < found.size(); i++) {
			Integer pos = (Integer) found.get(i);
			foundWay += pos;
		}

		Assert.assertEquals("10141511", foundWay);
	}

	/**
	 * Kein Buchstabe zweimal verwenden
	 */
	public void testFindWord_doubleChar() {
		String word = "WETTE";
		Vector<Integer> found = board.checkWord(word);

		for (int i = 0; i < found.size(); i++) {
			Integer pos = (Integer) found.get(i);
			foundWay += pos;
		}

		Assert.assertEquals("73625", foundWay);
	}

	/**
	 * Gross- und Kleinschreibung ignorieren
	 */
	public void testFindWord_ignoreUpperLower() {
		String word = "noT";
		Vector<Integer> found = board.checkWord(word);

		for (int i = 0; i < found.size(); i++) {
			Integer pos = (Integer) found.get(i);
			foundWay += pos;
		}

		Assert.assertEquals("048", foundWay);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		board = new Board();
		board.setDices(DICESET);

		foundWay = "";
	}
}
