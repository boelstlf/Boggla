package net.bolino.boggla.gui;

import net.bolino.boggla.Boggla;
import net.bolino.boggla.board.Board;
import net.bolino.boggla.board.JBoardPanel;
import net.bolino.boggla.player.JPlayerPanel;
import net.bolino.boggla.util.WordStatistics;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Description of the Class
 * 
 *@author frb
 *@created March 15th 2009
 */
public class BoggleGUI extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6488144146566866912L;
	private Boggla controller = null;
	private GridBagLayout gridBagLayout_boggle = new GridBagLayout();
	// panel of square and dices
	Board board = new Board();
	private JBoardPanel jPanel_board = new JBoardPanel(board);

	// real player panel
	private JPlayerPanel jPanel_realPlayer = new JPlayerPanel(this);

	// info panel
	private JPanel jPanel_info = new JPanel();
	private GridBagLayout gridBagLayout_info = new GridBagLayout();
	// time left
	private JLabel jLabel_timeLeft = new JLabel();
	private JTextField jTextField_timeLeft = new JTextField();

	// menu bar
	private JBoggleMenu jmenubar = null;
	// tool bar
	private JBoggleToolBar toolbar = null;

	/**
	 * Constructor for the BoggleGUI object
	 * 
	 *@param controller
	 *            Description of Parameter
	 */
	public BoggleGUI(Boggla controller) {
		this.controller = controller;
		addWindowListener(new WindowAdapter() {
			/**
			 * Description of the Method
			 * 
			 *@param e
			 *            Description of Parameter
			 */
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the Time attribute of the BoggleGUI object
	 * 
	 *@param timeLeft
	 *            The new Time value
	 */
	public void setTime(String timeLeft) {
		jTextField_timeLeft.setText(timeLeft);
	}

	/**
	 * Gets the ToolBar attribute of the BoggleGUI object
	 * 
	 *@return The ToolBar value
	 */
	public JBoggleToolBar getJBoggleToolBar() {
		return toolbar;
	}

	/**
	 * Gets the SquarePanel attribute of the BoggleGUI object
	 * 
	 *@return The SquarePanel value
	 */
	public JBoardPanel getSquarePanel() {
		return jPanel_board;
	}

	/**
	 * Gets the JMenuBar attribute of the BoggleGUI object
	 * 
	 *@return The JMenuBar value
	 */
	public JBoggleMenu getJBoggleMenu() {
		return jmenubar;
	}

	/**
	 * Invokes if a action performed.
	 * 
	 *@param e
	 *            action event
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("new game")) {
			newGame();
		} else if (cmd.equals("stop searching")) {
			stopSearch();
		} else if (cmd.equals("edit word list")) {
			editWordList();
		} else if (cmd.equals("read new words")) {
			readNewWords();
		} else if (cmd.equals("letter statistics")) {
			calcLetterStatistics();
		} else if (cmd.equals("options")) {
			showOptionsDialog();
		} else if (cmd.equals("connect")) {
			connectToMaster();
		} else if (cmd.equals("disconnect")) {
			disconnectMaster();
		} else if (cmd.equals("master")) {
			setAsMaster();
		} else if (cmd.equals("help")) {
			help();
		} else if (cmd.equals("about")) {
			about();
		} else if (cmd.equals("exit")) {
			quit();
		}
	}

	/**
	 * Description of the Method
	 */
	private void calcLetterStatistics() {
		WordStatistics stats = new WordStatistics();
		stats.calcStatistics(controller.getBasicWordList());	
	}

	/**
	 * Description of the Method
	 */
	public void readNewWords() {
		// controller.readNewWords();
	}

	/**
	 * Description of the Method
	 */
	public void connectToMaster() {
		// TODO controller.connectToMaster();
	}

	/**
	 * Description of the Method
	 */
	public void disconnectMaster() {
		// TODO controller.disconnectMaster();
	}

	/**
	 * User stopped searching.
	 */
	public void stopSearch() {
		controller.unscheduledStopSearch();
	}

	/**
	 * Description of the Method
	 */
	public void newGame() {
		jPanel_board.getBoard().shuffel();
		controller.newGame();
		jmenubar.setMenuItemEnable("file", "stop searching", true);
		jmenubar.setMenuItemEnable("file", "new game", false);
		toolbar.setJToolBarButtonEnable("new game", false);
		toolbar.setJToolBarButtonEnable("stop searching", true);
	}

	/**
	 * Set the player as master for playing via network.
	 */
	private void setAsMaster() {
		// TODO controller.setAsMaster();
	}

	/**
	 * Show dialog to edit, read, update, delete the current word list. Save and
	 * load the word list.
	 */
	private void editWordList() {
		WordAdminDialog wordAdminDialog = new WordAdminDialog(this, "Edit Word List");
		wordAdminDialog.setWordList(controller.getBasicWordList());
		wordAdminDialog.setVisible(true);
		if (wordAdminDialog.buttonClose)
		{
			controller.setWordList(wordAdminDialog.getWordList());
		}
	}

	/**
	 * Show the options dialog to do setting with regard to level, number of
	 * virtual players, net work players, etc.
	 */
	private void showOptionsDialog() {
		BoggleOptionsDialog optionsDialog = new BoggleOptionsDialog(this, "Boggle settings");
		// set current settings
		optionsDialog.setNumVirtualPlayers(controller.getSetup().getNumVitualPlayers());
		optionsDialog.setLevelVirtualPlayers(controller.getSetup().getLevelVirtualPlayer());
		optionsDialog.setSearchTime(controller.getSetup().getSearchTime());
		optionsDialog.setBasicWordListFileName(controller.getSetup().getWordListFileName());
		optionsDialog.setPort(1234);
		// show dialog
		optionsDialog.setVisible(true);
		if (optionsDialog.buttonOK)
		{
			// write new settings
			controller.setSearchTime(optionsDialog.getSearchTime());
			controller.setNumVirtualPlayers(optionsDialog.getNumVirtualPlayers());
			controller.setLevelVirtualPlayer(optionsDialog.getLevelVirtualPlayers());
			controller.setWordListFileName(optionsDialog.getBasicWordListFileName());
			//manager.setPort(optionsDialog.getPort());
		}
	}

	/**
	 * Show the help text.
	 */
	private void help() {
		String aboutMsg = "Boggle v0.2 HELP\n" + "sorry, \nnot yet implemented";
		JOptionPane.showMessageDialog(this, aboutMsg, "Boggla v0.2",
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
						"icons/BogglaLogo.gif"));
	}

	/**
	 * Show the about message dialog.
	 */
	private void about() {
		String aboutMsg = "Boggle v0.2\n"
				+ "Wörtersuchspiel für 1..99 Spieler\n"
				+ "im Alter von 6..99.\n\n\n" + "last change 19. Januar 2003\n"
				+ "copyright 2003, all rights reserved"
				+ "for contact mail to: boelstler@gmx.net";
		JOptionPane.showMessageDialog(this, aboutMsg, "Boggla v0.2",
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
						"icons/BogglaLogo.gif"));
	}

	/**
	 * Description of the Method
	 * 
	 *@exception Exception
	 *                Description of Exception
	 */
	private void jbInit() throws Exception {
		toolbar = new JBoggleToolBar(this);
		jmenubar = new JBoggleMenu(this);
		this.setJMenuBar(jmenubar);
		this.setTitle("Boggle");
		this.getContentPane().setLayout(gridBagLayout_boggle);
		jPanel_info.setLayout(gridBagLayout_info);
		jLabel_timeLeft.setText("time left");
		jTextField_timeLeft.setText("1:30");
		jTextField_timeLeft.setEditable(false);
		this.getContentPane().add(
				jPanel_board,
				new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(4, 4, 4, 4), 80, 75));
		this.getContentPane().add(
				jPanel_realPlayer,
				new GridBagConstraints(1, 1, 1, 5, 100.0, 100.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(4, 4, 4, 4), 0, 0));
		this.getContentPane().add(
				jPanel_info,
				new GridBagConstraints(0, 2, 1, 1, 0.0, 100.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(4, 4, 4, 4), 0, 0));
		jPanel_info.add(jLabel_timeLeft, new GridBagConstraints(0, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				new Insets(4, 4, 4, 4), 0, 0));
		jPanel_info.add(jTextField_timeLeft, new GridBagConstraints(1, 0, 1, 1,
				1.0, 0.0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		this.getContentPane().add(
				toolbar,
				new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
						GridBagConstraints.NORTHWEST,
						GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4),
						0, 0));
	}

	/**
	 * Quit game
	 */
	private void quit() {
		controller.quit();
	}

	/**
	 * @return the jPanel_realPlayer
	 */
	public JPlayerPanel getJPanel_realPlayer() {
		return jPanel_realPlayer;
	}

	/**
	 * Enable new round controls again.
	 */
	public void stopSearching() {
		jmenubar.setMenuItemEnable("file", "stop searching", false);
		jmenubar.setMenuItemEnable("file", "new game", true);
		toolbar.setJToolBarButtonEnable("new game", true);
		toolbar.setJToolBarButtonEnable("stop searching", false);
	}
}
