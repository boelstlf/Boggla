/**
 * 
 */
package net.bolino.boggla.player;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import net.bolino.boggla.gui.BoggleGUI;
import net.bolino.boggla.util.mytable.MyTable;

/**
 * @author frb
 * 
 */
public class JPlayerPanel extends JPanel {

	private static final long serialVersionUID = -7972146683323742013L;
	/**
	 * 
	 */
	private BoggleGUI parent = null;
	private GridBagLayout gridBagLayout_player = new GridBagLayout();
	private RealPlayer realPlayer = null;

	private JLabel jLabel_newWord = new JLabel();
	private JTextField jTextField_newWord = new JTextField();
	private JButton jButton_delWord = new JButton();
	// valid word list
	private JLabel jLabel_pointList = new JLabel();
	private MyTable jTable_pointList = new MyTable(new String[] { "word",
			"points" });
	private JScrollPane jScrollPane_pointList = new JScrollPane(
			jTable_pointList);
	// found word list
	private JLabel jLabel_foundWordList = new JLabel();
	private DefaultListModel foundWordList = new DefaultListModel();
	private JList jList_words = new JList(foundWordList);
	private JScrollPane jScrollPane_wordList = new JScrollPane(jList_words);
	// history of points of each round
	private JLabel jLabel_roundHistory = new JLabel("round history");
	private MyTable jTable_roundHistory = new MyTable(new String[] { "round",
			"points" });
	private JScrollPane jScrollPane_roundHistory = new JScrollPane(
			jTable_roundHistory);
	// points sum over all games
	private JLabel jLabel_pointsTotal = new JLabel("total points");
	private JTextField jTextField_pointsTotal = new JTextField();

	/**
	 * 
	 */
	public JPlayerPanel(BoggleGUI parent) {
		this.parent = parent;
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.nixgehtmehr();
	}

	/**
	 * @param validWord
	 * @param points
	 */
	public void addValidWord(String validWord, int points) {
		jTable_pointList.AddData(new Object[][] {{new String(validWord), new Integer(points) }});
		
	}
	/**
	 * Gets the Words attribute of the BoggleGUI object
	 * 
	 *@return The Words value
	 */
	public Enumeration getWords() {
		return foundWordList.elements();
	}

	/**
	 * Clear list of points collected for this round
	 */
	public void clearPointList() {
		jTable_pointList.RemoveAll();
	}

	/**
	 * Description of the Method
	 */
	public void clearWordList() {
		foundWordList.removeAllElements();
	}

	/**
	 * Add word to found list.
	 * 
	 *@param e
	 *            Description of Parameter
	 */
	void jTextField_newWord_actionPerformed(ActionEvent e) {
		String newWord = jTextField_newWord.getText();
		foundWordList.addElement(newWord);
		jTextField_newWord.setText("");
	}

	/**
	 * Delete selected word from found list.
	 * 
	 *@param e
	 *            Description of Parameter
	 */
	void jButton_delWord_actionPerformed(ActionEvent e) {
		int selected = jList_words.getSelectedIndex();
		if (selected != -1) {
			foundWordList.removeElementAt(selected);
		}
	}

	/**
	 * Highlight word on board.
	 * 
	 *@param e
	 *            Description of Parameter
	 */
	void jList_words_mouseClicked(MouseEvent e) {
		int wordPos = jList_words.getSelectedIndex();
		System.out.println(wordPos);
		if (wordPos >= 0) {
			String word = (String) foundWordList.elementAt(wordPos);
			parent.getSquarePanel().displayWay(word);
		}
	}

	/**
	 * Clear highlighting when mouse left found list.
	 * 
	 *@param e
	 *            Description of Parameter
	 */
	void jList_words_mouseExited(MouseEvent e) {
		parent.getSquarePanel().clearDiceHighlight();
	}

	/**
	 * Highlight the word at the square when word in table is clicked
	 * 
	 *@param e
	 *            Description of Parameter
	 */
	void jTable_pointList_mouseClicked(MouseEvent e) {
		Object[][] obj = jTable_pointList.GetSelectedItems();
		String word = (String) obj[0][0];
		parent.getSquarePanel().displayWay(word);
	}

	/**
	 * Clear to highlight the word at the square whenever the mouse leaves the
	 * word list table.
	 * 
	 *@param e
	 *            Description of Parameter
	 */
	void jTable_pointList_mouseExited(MouseEvent e) {
		parent.getSquarePanel().clearDiceHighlight();
	}

	/**
	 * Game round stopped, disable entering new words
	 */
	public void nixgehtmehr() {
		jTextField_newWord.setEditable(false);
		jTextField_newWord.setText("");
	}

	/**
	 * Sets the Points attribute of the VirtualPlayerGUI object
	 * 
	 *@param points
	 *            The new Points value
	 */
	public void addPoints(int round, int points) {
		jTable_roundHistory.AddData(new Object[][] { {
				new Integer(round), new Integer(points) } });
	}
	
	/**
	 * @param totalPoints
	 */
	public void setTotalPoints(int totalPoints)
	{
		jTextField_pointsTotal.setText(String.valueOf(totalPoints));		
	}

	/**
	 * Description of the Method
	 * 
	 *@exception Exception
	 *                Description of Exception
	 */
	private void jbInit() throws Exception {
		jList_words.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setLayout(gridBagLayout_player);
		jLabel_newWord.setText("enter new word");
		jLabel_pointList.setText("list of valid words and received points");
		jLabel_foundWordList.setText("list of words found");
		jTextField_newWord.setEditable(true);
		jTextField_pointsTotal.setText("0");
		jTextField_pointsTotal.setEditable(false);
		// new found word entered
		jTextField_newWord
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jTextField_newWord_actionPerformed(e);
					}
				});
		jButton_delWord.setText("delete word");
		// button delete word pressed
		jButton_delWord.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton_delWord_actionPerformed(e);
			}
		});
		jList_words.addMouseListener(new java.awt.event.MouseAdapter() {
			// mouse clicked on word in list
			public void mouseClicked(MouseEvent e) {
				jList_words_mouseClicked(e);
			}
			// mouse left word list
			public void mouseExited(MouseEvent e) {
				jList_words_mouseExited(e);
			}
		});
		jTable_pointList.addMouseListener(new java.awt.event.MouseAdapter() {
			// mouse clicked on word in list
			public void mouseClicked(MouseEvent e) {
				jTable_pointList_mouseClicked(e);
			}
			// mouse left word list
			public void mouseExited(MouseEvent e) {
				jTable_pointList_mouseExited(e);
			}
		});
		this.add(jLabel_newWord, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4,
						4, 4, 4), 0, 0));
		this.add(jTextField_newWord, new GridBagConstraints(1, 0, 1, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(4, 4, 4, 4), 0, 0));
		this.add(jButton_delWord, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(
						4, 4, 4, 4), 0, 0));
		this.add(jLabel_foundWordList, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4,
						4, 4, 4), 0, 0));
		this.add(jScrollPane_wordList, new GridBagConstraints(0, 2, 3, 1, 1.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0));
		this.add(jLabel_pointList, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4,
						4, 4, 4), 0, 0));
		this.add(jScrollPane_pointList, new GridBagConstraints(0, 4, 1, 1, 1.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0));
		this.add(jLabel_roundHistory, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4,
						4, 4, 4), 0, 0));
		this.add(jScrollPane_roundHistory, new GridBagConstraints(1, 4, 2, 1, 1.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0));
		this.add(jLabel_pointsTotal, new GridBagConstraints(0, 5, 1, 1, 0.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(4, 4, 4, 4), 0, 0));
		this.add(jTextField_pointsTotal, new GridBagConstraints(1, 5, 2, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(4, 4, 4, 4), 0, 0));
	}

	/**
	 * Get reference of real player.
	 * 
	 * @return the realPlayer
	 */
	public RealPlayer getRealPlayer() {
		return realPlayer;
	}

	/**
	 * Set reference of real player.
	 * 
	 * @param realPlayer
	 *            the realPlayer to set
	 */
	public void setRealPlayer(RealPlayer realPlayer) {
		this.realPlayer = realPlayer;
	}

	/**
	 * Enable enter words and set focus to text field in order to start writing words immediately.
	 */
	public void startSearching() {
		jTextField_newWord.setEditable(true);
		jTextField_newWord.setText("");		
		jTextField_newWord.requestFocus();
	}
}