package net.bolino.boggla.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.event.*;

/**
 *  Description of the Class
 *
 *@author     flb
 *@created    1. Januar 2003
 */
public class BoggleOptionsDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -410337403585963498L;
	//
	/**
	 *  Description of the Field
	 */
	public boolean buttonOK = false;
	private GridBagLayout gridBagLayout_basis = new GridBagLayout();
	// tabbed panel
	private JTabbedPane jTabbedPane = new JTabbedPane();
	// virtual player settings
	private JPanel jPanel_virtualPlayers = new JPanel();
	private GridBagLayout gridBagLayout_virtualPlayers = new GridBagLayout();
	private JLabel jLabel_searchTime = new JLabel();
	private JTextField jTextField_searchTime = new JTextField();
	private JLabel jLabel_sec = new JLabel();
	// search settings
	private JPanel jPanel_search = new JPanel();
	private GridBagLayout gridBagLayout_search = new GridBagLayout();
	private JLabel jLabel_numVirtualPlayers = new JLabel();
	private JTextField jTextField_numVirtualPlayers = new JTextField();
	private JLabel jLabel_level = new JLabel();
	private JTextField jTextField_levelVirtualPlayer = new JTextField();
	// basic word list settings
	private JPanel jPanel_basicWordList = new JPanel();
	private GridBagLayout gridBagLayout_basicWordList = new GridBagLayout();
	private JLabel jLabel_basicWordList = new JLabel();
	private JTextField jTextField_basicWordList = new JTextField();
	private JButton jButton_fileChooser = new JButton();
	// net player settings
	private JPanel jPanel_netPlayer = new JPanel();
	private GridBagLayout gridBagLayout_netPlayer = new GridBagLayout();
	private JLabel jLabel_netPlayerPort = new JLabel();
	private JTextField jTextField_netPlayerPort = new JTextField();

	// control panel
	private JPanel jPanel_control = new JPanel();
	private GridBagLayout gridBagLayout_control = new GridBagLayout();
	private JButton jButton_ok = new JButton();
	private JButton jButton_cancel = new JButton();

	/**
	 *  Constructor for the BoggleOptionsDialog object
	 *
	 *@param  owner  Description of Parameter
	 *@param  title  Description of Parameter
	 */
	public BoggleOptionsDialog(JFrame owner, String title)
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
		setSize(400, 200);
		Point p = owner.getLocation();
		this.setLocation(p.x + 50, p.y + 50);
	}

	/**
	 *  Sets the BasicWordListFileName attribute of the BoggleOptionsDialog object
	 *
	 *@param  fileName  The new BasicWordListFileName value
	 */
	public void setBasicWordListFileName(String fileName)
	{
		jTextField_basicWordList.setText(fileName);
	}

	/**
	 *  Sets the NumVirtualPlayers attribute of the BoggleOptionsDialog object
	 *
	 *@param  numVirtualPlayers  The new NumVirtualPlayers value
	 */
	public void setNumVirtualPlayers(int numVirtualPlayers)
	{
		jTextField_numVirtualPlayers.setText(String.valueOf(numVirtualPlayers));
	}

	/**
	 *  Sets the LevelVirtualPlayers attribute of the BoggleOptionsDialog object
	 *
	 *@param  levelVirtualPlayers  The new LevelVirtualPlayers value
	 */
	public void setLevelVirtualPlayers(int levelVirtualPlayers)
	{
		jTextField_levelVirtualPlayer.setText(String.valueOf(levelVirtualPlayers));
	}

	/**
	 *  Sets the SearchTime attribute of the BoggleOptionsDialog object
	 *
	 *@param  searchTime  The new SearchTime value
	 */
	public void setSearchTime(int searchTime)
	{
		jTextField_searchTime.setText(String.valueOf(searchTime));
	}

	/**
	 *  Sets the Port attribute of the BoggleOptionsDialog object
	 *
	 *@param  port  The new Port value
	 */
	public void setPort(int port)
	{
		jTextField_netPlayerPort.setText(String.valueOf(port));
	}


	/**
	 *  Gets the BasicWordListFileName attribute of the BoggleOptionsDialog object
	 *
	 *@return    The BasicWordListFileName value
	 */
	public String getBasicWordListFileName()
	{
		return jTextField_basicWordList.getText();
	}

	/**
	 *  Gets the NumVirtualPlayers attribute of the BoggleOptionsDialog object
	 *
	 *@return    The NumVirtualPlayers value
	 */
	public int getNumVirtualPlayers()
	{
		return Integer.parseInt(jTextField_numVirtualPlayers.getText());
	}

	/**
	 *  Gets the LevelVirtualPlayers attribute of the BoggleOptionsDialog object
	 *
	 *@return    The LevelVirtualPlayers value
	 */
	public int getLevelVirtualPlayers()
	{
		return Integer.parseInt(jTextField_levelVirtualPlayer.getText());
	}

	/**
	 *  Gets the SearchTime attribute of the BoggleOptionsDialog object
	 *
	 *@return    The SearchTime value
	 */
	public int getSearchTime()
	{
		return Integer.parseInt(jTextField_searchTime.getText());
	}

	/**
	 *  Gets the Port attribute of the BoggleOptionsDialog object
	 *
	 *@return    The Port value
	 */
	public int getPort()
	{
		return Integer.parseInt(jTextField_netPlayerPort.getText());
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jButton_cancel_actionPerformed(ActionEvent e)
	{
		buttonOK = false;
		setVisible(false);
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jButton_ok_actionPerformed(ActionEvent e)
	{
		if (checkInput())
		{
			buttonOK = true;
			setVisible(false);
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jButton_fileChooser_actionPerformed(ActionEvent e)
	{
		String defaultDir = "./WordLists";
		// show the filechooser dialog and get the selected filename
		JFileChooser jfilechooser = new JFileChooser(defaultDir);
		jfilechooser.setMultiSelectionEnabled(false);
		jfilechooser.setDialogTitle("basic word list");
		jfilechooser.setFileFilter(
			new javax.swing.filechooser.FileFilter()
			{
				/**
				 *  Gets the Description attribute of the BoggleOptionsDialog object
				 *
				 *@return    The Description value
				 */
				public String getDescription()
				{
					return "dat-Files";
				}

				/**
				 *  Description of the Method
				 *
				 *@param  f  Description of Parameter
				 *@return    Description of the Returned Value
				 */
				public boolean accept(File f)
				{
					return f.getName().toLowerCase().endsWith(".dat");
				}
			});
		jfilechooser.setSelectedFile(new File(jTextField_basicWordList.getText()));
		int erg = jfilechooser.showOpenDialog(this);
		if (erg == JFileChooser.APPROVE_OPTION)
		{
			jTextField_basicWordList.setText(jfilechooser.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void jTabbedPane_stateChanged(ChangeEvent e)
	{
		// ToDO
		// validate input from current tab
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	private boolean checkInput()
	{
		// noch zu tun
		return true;
	}

	/**
	 *  Description of the Method
	 *
	 *@exception  Exception  Description of Exception
	 */
	private void jbInit() throws Exception
	{
		this.getContentPane().setLayout(gridBagLayout_basis);
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
		jPanel_control.setLayout(gridBagLayout_control);
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
		jPanel_netPlayer.setLayout(gridBagLayout_netPlayer);
		jPanel_virtualPlayers.setLayout(gridBagLayout_virtualPlayers);
		jPanel_search.setLayout(gridBagLayout_search);
		jPanel_basicWordList.setLayout(gridBagLayout_basicWordList);
		jLabel_basicWordList.setText("basic word list");
		jTextField_basicWordList.setText("");
		jButton_fileChooser.setText("...");
		jButton_fileChooser.addActionListener(
			new java.awt.event.ActionListener()
			{
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void actionPerformed(ActionEvent e)
				{
					jButton_fileChooser_actionPerformed(e);
				}
			});
		jLabel_searchTime.setText("search time:");
		jTextField_searchTime.setText("90");
		jLabel_sec.setText("sec");
		jLabel_numVirtualPlayers.setText("virtual players:");
		jTextField_numVirtualPlayers.setText("0");
		jLabel_level.setText("level (1-5):");
		jTextField_levelVirtualPlayer.setText("1");
		jLabel_netPlayerPort.setText("port:");
		jTextField_netPlayerPort.setText("6333");
		jTabbedPane.addChangeListener(
			new javax.swing.event.ChangeListener()
			{
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void stateChanged(ChangeEvent e)
				{
					jTabbedPane_stateChanged(e);
				}
			});
		this.getContentPane().add(jTabbedPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		jTabbedPane.add(jPanel_search, "search");
		jTabbedPane.add(jPanel_virtualPlayers, "virtual players");
		jTabbedPane.add(jPanel_basicWordList, "basic word list");
		jTabbedPane.add(jPanel_netPlayer, "net players");
		this.getContentPane().add(jPanel_control, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		jPanel_control.add(jButton_ok, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 24, 4, 24), 0, 0));
		jPanel_control.add(jButton_cancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4, 24, 4, 24), 0, 0));
		jPanel_search.add(jLabel_searchTime, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 24, 4, 0), 0, 0));
		jPanel_search.add(jTextField_searchTime, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 0), 0, 0));
		jPanel_search.add(jLabel_sec, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 4, 4, 24), 0, 0));
		jPanel_basicWordList.add(jLabel_basicWordList, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 24, 4, 0), 0, 0));
		jPanel_basicWordList.add(jTextField_basicWordList, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 0), 0, 0));
		jPanel_basicWordList.add(jButton_fileChooser, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 4, 4, 12), 0, 0));
		jPanel_virtualPlayers.add(jLabel_numVirtualPlayers, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4, 24, 0, 4), 0, 0));
		jPanel_virtualPlayers.add(jTextField_numVirtualPlayers, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 24), 0, 0));
		jPanel_virtualPlayers.add(jLabel_level, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4, 24, 4, 4), 0, 0));
		jPanel_virtualPlayers.add(jTextField_levelVirtualPlayer, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 24), 0, 0));
		jPanel_netPlayer.add(jLabel_netPlayerPort,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 12, 4, 4), 0, 0));
		jPanel_netPlayer.add(jTextField_netPlayerPort,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 8, 8, 8), 0, 0));
	}
}
