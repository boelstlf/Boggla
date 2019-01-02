package net.bolino.boggla.board;

import javax.swing.*;
import java.awt.Color;
import javax.swing.border.*;
import java.awt.*;

/**
 *  Description of the Class
 *
 *@author     frb
 *@created    22. February 2009
 */
public class JDice extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443471483252059121L;
	private char letter = '-';
	private Border border1;

	/**
	 *  Constructor for the Dice object
	 *
	 *@param  letters  Description of Parameter
	 *@param  seed     Description of Parameter
	 */
	public JDice(char letter)
	{
		this.letter = letter;
		try
		{
			jbInit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *  Sets the CurrentLetter attribute of the JDice object
	 *
	 *@param  curLetter  The new CurrentLetter value
	 */
	public void setCurrentLetter(char letter)
	{
		this.letter = letter;
		this.updateUI();
	}

	/**
	 *  Gets the CurrentLetter attribute of the Dice object
	 *
	 *@return    The CurrentLetter value
	 */
	public char getCurrentLetter()
	{
		return letter;
	}

	/**
	 *  Update the panel.
	 *
	 *@param  g  graphics handler
	 */
	public synchronized void paintComponent(Graphics g)
	{
		g.drawString(String.valueOf(letter), getWidth() / 2 - 3, getHeight() / 2 + 4);
	}

	/**
	 *  Description of the Method
	 *
	 *@exception  Exception  Description of Exception
	 */
	private void jbInit() throws Exception
	{
		border1 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED, Color.black, new Color(178, 140, 0)), BorderFactory.createEmptyBorder(1, 1, 1, 1));
		this.setBackground(Color.orange);
		this.setFont(new java.awt.Font("Dialog", 1, 12));
		this.setBorder(border1);
	}
}
