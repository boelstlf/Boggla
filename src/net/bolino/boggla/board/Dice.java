package net.bolino.boggla.board;

import java.util.Random;

/**
 * @author bolino
 * Model of dice provides methods to shuffel the dice and to return the current visible dice value.
 */
public class Dice {
	private char[] chars = null;
	private int visibleSide = 0;
	Random rand = new Random();

	
	/**
	 * @return char which is visisble to player currently
	 */
	public char getVisible() {
		return chars[visibleSide];
	}
	
	/**
	 * Shuffel the dice visivble side and therefore value.
	 */
	public void shuffel() {
		if(chars!=null) {
			visibleSide = rand.nextInt(chars.length);
		}
	}
	
	/**
	 * Setup dice values with given chars.
	 * @param chars to setup dice
	 */
	public void setupDice(char[] chars) {
		this.chars = chars;
	}
}
