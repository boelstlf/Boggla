package net.bolino.boggla.board;

import javax.swing.JPanel;


import java.awt.*;
import java.util.Vector;

/**
 *  Description of the Class
 *
 *@author     frb
 *@created    22. February 2009
 */
public class JBoardPanel extends JPanel implements BoardEventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3981720667995164797L;
	private GridBagLayout gridBagLayout_square = new GridBagLayout();

	private final int nDices = 4;
	private JDice[] dice = new JDice[nDices * nDices];
	private Board board = null;

	/**
	 *  Constructor for the JBoardPanel object
	 */
	public JBoardPanel(Board board)
	{
		try
		{
			jbInit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.board = board;
		board.addBoardEventListener(this);
		this.boardChanged();
	}

	/**
	 *  Update dices on GUI.
	 *
	 */
	public void boardChanged()
	{
		char[] diceFace = board.getDices();
		this.removeAll();
		for (int i = 0; i < nDices; i++)
		{
			for (int j = 0; j < nDices; j++)
			{
 				dice[i * nDices + j].setCurrentLetter(diceFace[i * nDices + j]);
				this.add(dice[i * nDices + j], new GridBagConstraints(j, i, 1, 1, 1.0, 1.0
						, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
			}
		}
		this.repaint();
	}

	/**
	 *  Gets the Dices attribute of the JBoardPanel object
	 *
	 *@return    The Dices value
	 */
	public Object[] getDices()
	{
		return dice;
	}

	/**
	 *  Set foreground color of all dices to back to black.
	 */
	public void clearDiceHighlight()
	{
		for (int i = 0; i < (nDices * nDices); i++)
		{
			dice[i].setForeground(Color.black);
		}
		updateUI();
	}

	/**
	 *  Highlight given word if possible.
	 *
	 *@param  word  to highlight
	 */
	public void displayWay(String word)
	{
		clearDiceHighlight();
		Vector<Integer> way = board.checkWord(word);
		int numSteps = way.size();
		for (int i = 0; i < numSteps; i++)
		{
			int d = ((Integer) way.get(i)).intValue();
			dice[d].setForeground(new Color(250, 175, 40));
		}
		updateUI();
	}

	/**
	 *  Description of the Method
	 *
	 *@exception  Exception  Description of Exception
	 */
	private void jbInit() throws Exception
	{
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(gridBagLayout_square);
		for(int i=0; i<16; i++) {
			dice[i] = new JDice('-');
		}
	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
		board.addBoardEventListener(this);
	}
}