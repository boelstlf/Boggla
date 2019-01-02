package net.bolino.boggla.test.board;

import net.bolino.boggla.board.Dice;
import junit.framework.Assert;
import junit.framework.TestCase;

public class DiceTest extends TestCase {

	public void testGetVisible() {
		Dice dice = new Dice();
		
		char[] chars = {'A', 'B', 'C'};
		dice.setupDice(chars);
		char visibleChar = dice.getVisible();
		Assert.assertEquals('A', visibleChar);
	}

	public void testEmptyDice() {
		Dice dice = new Dice();
		
		char visibleChar = dice.getVisible();
		Assert.assertNull(visibleChar);
	}
	
}
