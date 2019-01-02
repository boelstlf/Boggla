package net.bolino.boggla.test.player;

import net.bolino.boggla.player.FoundWord;
import net.bolino.boggla.player.Player;
import net.bolino.boggla.player.RealPlayer;
import junit.framework.Assert;
import junit.framework.TestCase;

public class PlayerTest extends TestCase {

	public void testMarkDoubleWords() {
		Player player1 = new RealPlayer(null, "player1");
		player1.addFoundWord("double");
		player1.addFoundWord("notdouble");
		player1.addFoundWord("doubleUPPER");

		Player player2 = new RealPlayer(null, "player2");
		player2.addFoundWord("double");
		player2.addFoundWord("doubleupper");
		
		player1.markDoubleWords(player2.getFoundWords());
		
		Assert.assertEquals(player1.getFoundWord(0).gettype(), FoundWord.DOUBLEWORD);
		Assert.assertEquals(player1.getFoundWord(1).gettype(), FoundWord.NOTCHECKED);
		Assert.assertEquals(player1.getFoundWord(2).gettype(), FoundWord.DOUBLEWORD);
	}

}
