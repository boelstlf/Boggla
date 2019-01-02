/**
 * 
 */
package net.bolino.boggla.player;

import java.util.Vector;

/**
 * @author frb
 * 
 */
public interface IPlayer {
	
	/**
	 * Start searching of words.
	 */
	public void startSearching();

	/**
	 * Stop searching of words.
	 */
	public void stopSearching();

	/**
	 * Calculate the points of found words for this round.
	 */
	public void calcPointsOfRound();

	/**
	 * Get the list of found words of round.
	 * 
	 * @return list of found words
	 */
	public Vector<FoundWord> getFoundWords();
	
	/**
	 * Get found word at index.
	 *
	 * @param index
	 * @return
	 */
	public FoundWord getFoundWord(int index);

	/**
	 * Check words that are double and mark them.
	 * 
	 * @param foundWords list of words to be checked against.
	 */
	public void markDoubleWords(Vector<FoundWord> foundWords);

	/**
	 * Add a new word found on board.
	 * @param word
	 */
	public void addFoundWord(String word);
}
