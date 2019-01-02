package net.bolino.boggla.gui;

import javax.swing.JToolBar;
import javax.swing.Action;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.Icon;

/**
 *  Description of the Class
 *
 *@author     flb
 *@created    1. Februar 2003
 */
public class JBoggleToolBar extends JToolBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6160687805633780098L;
	private BoggleGUI control = null;
	private ToolBarButton newGame = new ToolBarButton();
	private ToolBarButton stopSearch = new ToolBarButton();
	private ToolBarButton netConnect = new ToolBarButton();
	private ToolBarButton netDisconnect = new ToolBarButton();

	/**
	 *  Constructor for the JBoggleToolBar object
	 *
	 *@param  control  Description of Parameter
	 */
	public JBoggleToolBar(BoggleGUI control)
	{
		this.control = control;
		this.configureToolBar();
		this.init();
	}

	/**
	 *  Sets the JToolBarButtonEnable attribute of the JBoggleToolBar object
	 *
	 *@param  toolbarButton  The new JToolBarButtonEnable value
	 *@param  enable         The new JToolBarButtonEnable value
	 */
	public void setJToolBarButtonEnable(String toolbarButton, boolean enable)
	{
		Component[] comps = this.getComponents();
		int numComp = comps.length;
		for (int i = 0; i < numComp; i++)
		{
			if (comps[i] instanceof ToolBarButton)
			{
				ToolBarButton button = (ToolBarButton) comps[i];
				if (button.getToolTipText().equalsIgnoreCase(toolbarButton))
				{
					button.setEnabled(enable);
				}
			}
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  a  Description of Parameter
	 *@return    Description of the Returned Value
	 */
	public JButton add(Action a)
	{
		return super.add(a);
	}

	/**
	 *  Description of the Method
	 */
	private void configureToolBar()
	{
		newGame.setAction(
			new AbstractAction("new game", new ImageIcon("icons/netConnect.gif"))
			{
				/**
				 *  Description of the Method
				 *
				 *@param  event  Description of Parameter
				 */
				public void actionPerformed(ActionEvent event)
				{
					control.actionPerformed(new ActionEvent(this, 0, "new game"));
				}
			});
		stopSearch.setAction(
			new AbstractAction("stop searching", new ImageIcon("icons/netConnect.gif"))
			{
				/**
				 *  Description of the Method
				 *
				 *@param  event  Description of Parameter
				 */
				public void actionPerformed(ActionEvent event)
				{
					control.actionPerformed(new ActionEvent(this, 0, "stop searching"));
				}
			});
		netConnect.setAction(
			new AbstractAction("connect", new ImageIcon("icons/netConnect.gif"))
			{
				/**
				 *  Description of the Method
				 *
				 *@param  event  Description of Parameter
				 */
				public void actionPerformed(ActionEvent event)
				{
					control.actionPerformed(new ActionEvent(this, 0, "connect"));
				}
			});
		netDisconnect.setAction(
			new AbstractAction("disconnect", new ImageIcon("icons/netDisconnect.gif"))
			{
				/**
				 *  Description of the Method
				 *
				 *@param  event  Description of Parameter
				 */
				public void actionPerformed(ActionEvent event)
				{
					control.actionPerformed(new ActionEvent(this, 0, "disconnect"));
				}
			});
		add(newGame);
		add(stopSearch);
		addSeparator();
		add(netConnect);
		add(netDisconnect);
	}

	/**
	 *  Description of the Method
	 */
	private void init()
	{
		netDisconnect.setEnabled(false);
		stopSearch.setEnabled(false);
	}

	/**
	 *  Description of the Class
	 *
	 *@author     flb
	 *@created    1. Februar 2003
	 */
	private class ToolBarButton extends JButton
	{
		/**
		 *  Constructor for the ToolBarButton object
		 */
		public ToolBarButton()
		{
		}

		/**
		 *  Constructor for the ToolBarButton object
		 *
		 *@param  a  Description of Parameter
		 */
		public ToolBarButton(Action a)
		{
			super((Icon) a.getValue(Action.SMALL_ICON));

			String toolTip = (String) a.getValue(Action.SHORT_DESCRIPTION);
			if (toolTip == null)
			{
				toolTip = (String) a.getValue(Action.NAME);
			}
			if (toolTip != null)
			{
				setToolTipText(toolTip);
			}

			addActionListener(a);
		}

		/**
		 *  Sets the Action attribute of the ToolBarButton object
		 *
		 *@param  a  The new Action value
		 */
		public void setAction(Action a)
		{
			setIcon((Icon) a.getValue(Action.SMALL_ICON));

			String toolTip = (String) a.getValue(Action.SHORT_DESCRIPTION);
			if (toolTip == null)
			{
				toolTip = (String) a.getValue(Action.NAME);
			}
			if (toolTip != null)
			{
				setToolTipText(toolTip);
			}

			addActionListener(a);
		}
	}
}
