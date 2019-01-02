package net.bolino.boggla.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 *  Description of the Class
 *
 *@author     flb
 *@created    11. Januar 2003
 */
public class NewWordsDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -335369932302543737L;
	/**
	 *  Description of the Field
	 */
	public boolean BUTTONOK = false;
	private GridBagLayout gridBagLayout_basis = new GridBagLayout();
	// edit word
	private JPanel jPanel_edit = new JPanel();
	private GridBagLayout gridBagLayout_edit = new GridBagLayout();
	private JLabel jLabel_word = new JLabel();
	private JTextField jTextField_word = new JTextField();
	// basic word list
	private WordListModel basicWordListModel = new WordListModel();
	private JList jList_basicWordList = new JList();
	private JScrollPane jScrollPane_basicWordList = new JScrollPane();
	private JLabel jLabel_countBasicWordList = new JLabel();
	// new word list
	private WordListModel newWordListModel = new WordListModel();
	private JList jList_newWordList = new JList();
	private JScrollPane jScrollPane_newWordList = new JScrollPane();
	private JLabel jLabel_countNewWordList = new JLabel();
	// add/remove
	private JButton jButton_add = new JButton();
	private JButton jButton_delete = new JButton();
	private JButton jButton_edit = new JButton();
	// control panel
	private JPanel jPanel_control = new JPanel();
	private GridBagLayout gridBagLayout_control = new GridBagLayout();
	private JButton jButton_cancel = new JButton();
	private JButton jButton_ok = new JButton();

	/**
	 *  Constructor for the WordAdminDialog object
	 *
	 *@param  owner  Description of Parameter
	 *@param  title  Description of Parameter
	 */
	public NewWordsDialog(JFrame owner, String title)
	{
		super(owner, title, true);
		try
		{
			jbInit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		setSize(350, 400);
		Point p = owner.getLocation();
		this.setLocation(p.x + 50, p.y + 50);
	}

	/**
	 *  Sets the Elements attribute of the WordAdminDialog object
	 *
	 *@param  wordList  The new WordList value
	 */
	public void setNewWordList(Vector wordList)
	{
		newWordListModel.setElements(wordList);
		jLabel_countNewWordList.setText("( " + wordList.size() + " words )");
	}

	/**
	 *  Sets the Elements attribute of the WordAdminDialog object
	 *
	 *@param  wordList  The new WordList value
	 */
	public void setBasicWordList(Vector wordList)
	{
		basicWordListModel.setElements(wordList);
		jLabel_countBasicWordList.setText("( " + wordList.size() + " words )");
	}

	/**
	 *  Gets the WordList attribute of the WordAdminDialog object
	 *
	 *@return    The WordList value
	 */
	public Vector getNewBasicWordList()
	{
		Vector result = new Vector();
		Object[] obj = basicWordListModel.getAllElements();
		for (int i = 0; i < obj.length; i++)
		{
			result.addElement(obj[i]);
		}
		return result;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jButton_delete_actionPerformed(ActionEvent e)
	{
		int index = jList_basicWordList.getSelectedIndex();
		if (index != -1)
		{
			String word = (String) basicWordListModel.getElementAt(index);
			basicWordListModel.removeElementAt(index);
		  jLabel_countBasicWordList.setText("( " + basicWordListModel.getSize() + " words )");
			jList_basicWordList.updateUI();
			newWordListModel.addElement(word);
		  jLabel_countNewWordList.setText("( " + newWordListModel.getSize() + " words )");
			jList_newWordList.updateUI();
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jButton_cancel_actionPerformed(ActionEvent e)
	{
		setVisible(false);
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jButton_add_actionPerformed(ActionEvent e)
	{
		int index = jList_newWordList.getSelectedIndex();
		if (index != -1)
		{
			String word = (String) newWordListModel.getElementAt(index);
			newWordListModel.removeElementAt(index);
		  jLabel_countNewWordList.setText("( " + newWordListModel.getSize() + " words )");
			jList_newWordList.updateUI();
			basicWordListModel.addElement(word);
      jLabel_countBasicWordList.setText("( " + basicWordListModel.getSize() + " words )");
			jList_basicWordList.updateUI();
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jList_newWordList_mouseClicked(MouseEvent e)
	{
		int index = jList_newWordList.getSelectedIndex();
		if (index != -1)
		{
			jTextField_word.setText((String) newWordListModel.getElementAt(index));
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jButton_ok_actionPerformed(ActionEvent e)
	{
		BUTTONOK = true;
		setVisible(false);
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jButton_edit_actionPerformed(ActionEvent e)
	{
		int index = jList_newWordList.getSelectedIndex();
		if (index != -1)
		{
			newWordListModel.removeElementAt(index);
			newWordListModel.addElement(jTextField_word.getText());
			jList_newWordList.updateUI();
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@exception  Exception  Description of Exception
	 */
	private void jbInit() throws Exception
	{
		this.getContentPane().setLayout(gridBagLayout_basis);
		jPanel_control.setLayout(gridBagLayout_control);
		jPanel_edit.setLayout(gridBagLayout_edit);
		jLabel_word.setText("word");
		jButton_add.setText(">>");
		jButton_add.addActionListener(
			new java.awt.event.ActionListener()
			{
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void actionPerformed(ActionEvent e)
				{
					jButton_add_actionPerformed(e);
				}
			});
		jButton_edit.setText("edit");
		jButton_edit.addActionListener(
			new java.awt.event.ActionListener()
			{
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void actionPerformed(ActionEvent e)
				{
					jButton_edit_actionPerformed(e);
				}
			});
		jButton_delete.setText("<<");
		jButton_delete.addActionListener(
			new java.awt.event.ActionListener()
			{
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void actionPerformed(ActionEvent e)
				{
					jButton_delete_actionPerformed(e);
				}
			});
		jLabel_countBasicWordList.setText("( 123 words )");
		jLabel_countNewWordList.setText("( 456 words )");
		jScrollPane_basicWordList.getViewport().add(jList_basicWordList, null);
		jList_basicWordList.setModel(basicWordListModel);
		jScrollPane_newWordList.getViewport().add(jList_newWordList, null);
		jList_newWordList.setModel(newWordListModel);
		jButton_cancel.setText("cancel");
		jButton_cancel.addActionListener(
			new java.awt.event.ActionListener()
			{
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void actionPerformed(ActionEvent e)
				{
					jButton_cancel_actionPerformed(e);
				}
			});
		jList_newWordList.addMouseListener(
			new java.awt.event.MouseAdapter()
			{
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void mouseClicked(MouseEvent e)
				{
					jList_newWordList_mouseClicked(e);
				}
			});
		jButton_ok.setText("ok");
		jButton_ok.addActionListener(
			new java.awt.event.ActionListener()
			{
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void actionPerformed(ActionEvent e)
				{
					jButton_ok_actionPerformed(e);
				}
			});
		this.getContentPane().add(jPanel_edit, new GridBagConstraints(0, 0, 3, 1, 1.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		jPanel_edit.add(jLabel_word, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 8, 8, 8), 0, 0));
		jPanel_edit.add(jTextField_word, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 8, 8, 8), 0, 0));
		this.getContentPane().add(jScrollPane_newWordList, new GridBagConstraints(0, 1, 1, 3, 1.0, 1.0
				, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		this.getContentPane().add(jButton_add, new GridBagConstraints(1, 1, 1, 1, 0.0, 1.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		this.getContentPane().add(jButton_edit, new GridBagConstraints(1, 2, 1, 1, 0.0, 1.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		this.getContentPane().add(jButton_delete, new GridBagConstraints(1, 3, 1, 1, 0.0, 1.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		this.getContentPane().add(jScrollPane_basicWordList, new GridBagConstraints(2, 1, 1, 3, 1.0, 1.0
				, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		this.getContentPane().add(jLabel_countBasicWordList,   new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		this.getContentPane().add(jLabel_countNewWordList,  new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		this.getContentPane().add(jPanel_control, new GridBagConstraints(0, 5, 3, 1, 1.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		jPanel_control.add(jButton_cancel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(12, 12, 12, 12), 0, 0));
		jPanel_control.add(jButton_ok, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 12, 12, 12), 0, 0));
	}
}
