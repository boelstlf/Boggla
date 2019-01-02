/**
 * 
 */
package net.bolino.boggla;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.Timer;
import net.bolino.boggla.gui.*;
import net.bolino.boggla.player.IPlayer;
import net.bolino.boggla.player.RealPlayer;
import net.bolino.boggla.player.VirtualPlayer;
import net.bolino.boggla.player.FoundWord;

/**
 * @author bolino
 * 
 */
public class Boggla {
	private BoggleGUI gui = null;
	private int numRound = 0;
	private Vector<IPlayer> playerList = new Vector<IPlayer>();
	private Timer timer = null;;
	private int countdown = 0;
	private int searchTime = 0;
	private Vector<String> basicWordList = new Vector<String>();
	private Random rand = new Random();
	private BogglaSetup setup = null;

	/**
	 * Initialize the game.
	 */
	public Boggla() {
		System.out.println("init game...");
		// load settings from old session or default settings
		setup = new BogglaSetup("BogglaSetup.ini");

		// setup main window
		gui = new BoggleGUI(this);
		gui.setSize(600, 550);
		gui.setLocation(setup.getBogglaLocation());
		gui.setVisible(true);
		// init countdown as inner action listener
		ActionListener timerListener = new ActionListener() {
			/**
			 * Description of the Method
			 * 
			 *@param e
			 *            Description of Parameter
			 */
			public void actionPerformed(ActionEvent e) {
				countdown();
			}
		};
		timer = new Timer(1000, timerListener);
		
		// load reference word list
		this.loadBasicWordList(setup.getWordListFileName());
		// init the list of players, i.e. real players, net players and virtual players
		playerList.add(new RealPlayer(gui.getJPanel_realPlayer(), "real player"));
		// create virtual players
		for(int i=0; i<setup.getNumVitualPlayers(); i++) {
			VirtualPlayer virtualPlayer = new VirtualPlayer(this, "Virtual Player"+(i+1), setup.getVirtualPlayerLocation(i+1), rand.nextLong());
			this.playerList.add(virtualPlayer);
			virtualPlayer.setLevel(setup.getLevelVirtualPlayer());
		}
		// set the time to search
		searchTime = setup.getSearchTime();
	}

	/**
	 * Retrieve reference to setup of Boggla.
	 * @return
	 */
	public BogglaSetup getSetup()
	{
		return setup;
	}
	/**
	 * Start a new round. Increase the round counter. Give all players a trigger
	 * to start searching. Start the count down.
	 */
	public void newGame() {
		numRound++;
		System.out.println("round: " + numRound + "; #player: " + playerList.size());
		for(IPlayer player : playerList) {
			player.startSearching();
		}
		this.startTimer();
		System.out.println("new round started");
	}

	/**
	 * Start the timer for search time count down.
	 */
	public void startTimer() {
		countdown = 0;
		if (!timer.isRunning()) {
			timer.start();
		}
	}

	/**
	 * Count down search time left. This method will be called by an
	 * timer.
	 */
	private void countdown() {
		int count;
		int dummy;

		countdown++;
		if (countdown > searchTime) {
			stopSearch();
		} else {
			count = searchTime - countdown;
			dummy = count / 60;
			gui.setTime(Integer.toString(dummy) + ":"
					+ Integer.toString(count % 60));
		}
	}

	/**
	 * Stop players searching for words.
	 * Get words; mark double, mark invalid, mark impossible; call for calc 
	 */
	private void stopSearch() {
		timer.stop();
		gui.stopSearching();
		System.out.println("round: " + numRound + "; #player: " + playerList.size());
		for (IPlayer player : playerList) {
			player.stopSearching();
		}
		// mark invalid, mark impossible words for each player
		for (IPlayer player : playerList) {
			System.out.println("player: " + player.toString() + "found words: " + player.getFoundWords().size());
			// for each word found
			for(FoundWord foundWord : player.getFoundWords()) {
				// mark all words which are not within reference basic word list
				if(!basicWordList.contains(foundWord.getWord().toLowerCase())) {
					System.out.println("invalid word: " + foundWord.getWord());
					foundWord.settype(FoundWord.INVALIDWORD);
				}
				// mark all words which are not possible with current dice set on board
				else if(gui.getSquarePanel().getBoard().checkWord(foundWord.getWord()).size() == 0) {
					System.out.println("impossible word: " + foundWord.getWord());
					foundWord.settype(FoundWord.IMPOSSIBLEWORD);
				}
				// mark all others
				else {
					foundWord.settype(FoundWord.VALIDWORD);
					System.out.println("valid word: " + foundWord.getWord());
				}
			}
		}		
		// check words found against all words of all other players
		for (IPlayer playerSource : playerList) {
			for (IPlayer playerTarget : playerList) {
				if(playerSource != playerTarget) {
					// mark all words which other players have also
					playerSource.markDoubleWords(playerTarget.getFoundWords());
					
				}
			}
			// calc points for all words which are found by player only and update GUI
			playerSource.calcPointsOfRound();
		}		
	}

	/**
	 * Read reference word list from file. 
	 * These words will be reference whether a found word is valid or not.
	 * 
	 * @param fileName
	 */
	private void loadBasicWordList(String fileName) {
		basicWordList.removeAllElements();
		String lineWordList = "nothing";
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader in = new BufferedReader(file);
			while ((lineWordList = in.readLine()) != null) {
				StringTokenizer t = new StringTokenizer(lineWordList, ",");
				while (t.hasMoreTokens()) {
					String s = t.nextToken().trim();
					if (s.length() > 2) {
						//System.out.println(s);
						basicWordList.add(s.toLowerCase());
					}
				}
			}
		} catch (FileNotFoundException exc) {
			System.out.println("reference word list '" + fileName + "' file not found");
		} catch (IOException exc) {
			System.out.println("reference word list common IO Exception");
		}
	}

	/**
	 * Quit the game.
	 */
	public void quit() {
		int i=0;
		for(IPlayer player : playerList) {
			if(player.getClass().getName().equals("net.bolino.boggla.player.VirtualPlayer")) {
				i++;
				setup.setLocationVirtualPlayer(i, ((VirtualPlayer)player).getVirtualPlayerLocation());
			}
		}
		setup.writeIniFile();
		System.exit(0);
	}

	/**
	 * Get reference word list to check which found words are valid or not.
	 * 
	 * @return the basicWordList
	 */
	public Vector<String> getBasicWordList() {
		return basicWordList;
	}

	/**
	 * Get reference from GUI, e.g. to get reference from board.
	 */
	public BoggleGUI getGUI() {
		return gui;
		
	}

	/**
	 * Searching stopped by user, before time is up.
	 */
	public void unscheduledStopSearch() {
		this.stopSearch();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Boggla();
	}

	/**
	 * Set time for searching.
	 * @param searchTime
	 */
	public void setSearchTime(int searchTime) {
		this.searchTime = searchTime;
		setup.setSearchTime(searchTime);
	}

	/**
	 * @param numVirtualPlayers
	 */
	public void setNumVirtualPlayers(int numVirtualPlayers) {
		System.out.println("resize # virtual player: " + numVirtualPlayers);
		if(numVirtualPlayers > (playerList.size()-1)) {
			for(int i=playerList.size(); i<numVirtualPlayers+1; i++) {
				System.out.println("add new virtual player: " + i);
				VirtualPlayer virtualPlayer = new VirtualPlayer(this, "Virtual Player"+i, new Point(50+i*10, 50+i*10), rand.nextLong());
				this.playerList.add(virtualPlayer);
				virtualPlayer.setLevel(setup.getLevelVirtualPlayer());
				setup.setLocationVirtualPlayer(i, new Point(50+i*10, 50+i*10));
			}
		}
		else if (numVirtualPlayers < (playerList.size()-1)) {
			for(int i=playerList.size()-1; i>numVirtualPlayers; i--) {
				System.out.println("kill virtual player: " + i);
				VirtualPlayer virtualPlayer = (VirtualPlayer) this.playerList.get(i);
				virtualPlayer.killVirtualPlayer();
			}
		}
		setup.setNumVirtualPlayers(numVirtualPlayers);
	}

	/**
	 * @param levelVirtualPlayers
	 */
	public void setLevelVirtualPlayer(int levelVirtualPlayers) {
		for(IPlayer player : playerList) {
			if(VirtualPlayer.class.isInstance(player)) {
				((VirtualPlayer)player).setLevel(levelVirtualPlayers);
			}
		}
		setup.setLevelVirtualPlayer(levelVirtualPlayers);	
	}

	/**
	 * @param basicWordListFileName
	 */
	public void setWordListFileName(String basicWordListFileName) {
		loadBasicWordList(basicWordListFileName);
		setup.setWordListFileName(basicWordListFileName);
	}

	// set a new word list
	public void setWordList(Vector<String> wordList) {
		System.out.println("word size " + wordList.size());
		this.basicWordList = wordList;
		
	}
}
