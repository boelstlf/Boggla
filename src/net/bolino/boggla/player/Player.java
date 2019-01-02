package net.bolino.boggla.player;

import java.util.Vector;

/**
 * @author frb
 * @version 1.0
 * @created 04-Mrz-2009 14:06:42
 */
public abstract class Player implements IPlayer {

	/**
	 * List of foundWords.
	 */
	protected Vector<FoundWord> foundWords = new Vector<FoundWord>();
	/**
	 * 
	 */
	protected int roundPoints = 0;
	/**
	 * 
	 */
	protected int totalPoints = 0;
	/**
	 * 
	 */
	protected int round = 0;
	/**
	 * Name of the player
	 */
	protected String name = "default";

	/* (non-Javadoc)
	 * @see net.bolino.boggla.player.IPlayer#newRound()
	 */
	public void startSearching(){
		round++;
		foundWords.removeAllElements();
		// to be implemented at specialization, e.g. real player, virtual player
	}

	/* (non-Javadoc)
	 * @see net.bolino.boggla.player.IPlayer#getFoundWords()
	 */
	public Vector<FoundWord> getFoundWords(){
		return foundWords;
	}

	/* (non-Javadoc)
	 * @see net.bolino.boggla.player.IPlayer#calcPointsOfRound()
	 */
	public void calcPointsOfRound() {
		roundPoints = 0;
		System.out.println("size: " + foundWords.size());
		for(FoundWord foundWord : foundWords) {
			if(foundWord.gettype()==FoundWord.VALIDWORD) {
				roundPoints += foundWord.getpoints();
			}
		}
		totalPoints += roundPoints;		
	}

	/* (non-Javadoc)
	 * @see net.bolino.boggla.player.IPlayer#stopSearching()
	 */
	public void stopSearching() {
		// to be specialized
	}
	
	/* (non-Javadoc)
	 * @see net.bolino.boggla.player.IPlayer#getFoundWord(int)
	 */
	public FoundWord getFoundWord(int index) {
		return foundWords.get(index);
	}

	/* (non-Javadoc)
	 * @see net.bolino.boggla.player.IPlayer#markDoubleWords(java.util.Vector)
	 */
	public void markDoubleWords(Vector<FoundWord> targetFoundWords) {
		for(FoundWord foundWordSource : foundWords) {
			for(FoundWord foundWordTarget : targetFoundWords) {
				if(foundWordSource.getWord().toLowerCase().equals(foundWordTarget.getWord().toLowerCase())) {
					foundWordSource.settype(FoundWord.DOUBLEWORD);
					foundWordTarget.settype(FoundWord.DOUBLEWORD);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.bolino.boggla.player.IPlayer#addFoundWord(java.lang.String)
	 */
	public void addFoundWord(String word) {
		foundWords.add(new FoundWord(word));
		
	}
	
	/**
	 * Get the name of the player.
	 * @return
	 */
	public String getName() {
		return name;
		
	}
	
}