package net.bolino.boggla.gui;

import javax.swing.*;
import java.awt.event.*;

/**
 * Description of the Class
 * 
 *@author flb
 *@created 11. Januar 2003
 */
public class JBoggleMenu extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6059238030875254949L;
	// reference to owner of menubar
	JFrame frame = null;
	// setMnemonic 'f'
	JMenu fileMenu = new JMenu("File");
	// setMnemonic 'x'
	JMenu extrasMenu = new JMenu("Extras");
	// setMnemonic 'x'
	JMenu netMenu = new JMenu("Net");
	// setMnemonic '?'
	JMenu hlpMenu = new JMenu("?");
	// Items to file
	JMenuItem newGame = new JMenuItem("new game", 'n');
	JMenuItem stopGame = new JMenuItem("stop searching", 's');
	JMenuItem exit = new JMenuItem("exit", 'x');
	// Items to extras
	JMenuItem editWordList = new JMenuItem("edit word list", 'e');
	JMenuItem newWords = new JMenuItem("read new words", 'r');
	JMenuItem letterStat = new JMenuItem("letter statistics", 'l');
	JMenuItem options = new JMenuItem("options", 'o');
	// Items to net
	JMenuItem netConnect = new JMenuItem("connect", 'c');
	JMenuItem netDisconnect = new JMenuItem("disconnect", 'd');
	JCheckBoxMenuItem netMaster = new JCheckBoxMenuItem("master");
	// Items to ?
	JMenuItem help = new JMenuItem("help", 'h');
	JMenuItem about = new JMenuItem("about", 'b');

	/**
	 * Constructor for the SDMLEditorMenu object
	 * 
	 *@param frame
	 *            Description of Parameter
	 */
	public JBoggleMenu(JFrame frame) {
		this.frame = frame;
		configureMenu();
		init();
	}

	/**
	 * Sets the MenuItemSelected attribute of the SDMLEditorMenu object
	 * 
	 *@param szMenu
	 *            The new MenuItemSelected value
	 *@param szMenuItem
	 *            The new MenuItemSelected value
	 *@param b
	 *            The new MenuItemSelected value
	 */
	public void setMenuItemSelected(String szMenu, String szMenuItem, boolean b) {
		JMenu jmenu;
		JMenuItem jmenuitem;
		for (int i = 0; i < this.getMenuCount(); i++) {
			jmenu = (JMenu) this.getMenu(i);
			if (jmenu != null) {
				if (szMenu.equalsIgnoreCase(jmenu.getText())) {
					if (!jmenu.isEnabled() && b) {
						jmenu.setEnabled(b);
					}
					for (int t = 0; t < jmenu.getItemCount(); t++) {
						jmenuitem = (JMenuItem) jmenu.getItem(t);
						if (jmenuitem != null) {
							if (szMenuItem
									.equalsIgnoreCase(jmenuitem.getText())) {
								jmenuitem.setSelected(b);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Sets the MenuItemEnable attribute of the SDMLEditorMenu object
	 * 
	 *@param szMenu
	 *            The new MenuItemEnable value
	 *@param szMenuItem
	 *            The new MenuItemEnable value
	 *@param b
	 *            The new MenuItemEnable value
	 */
	public void setMenuItemEnable(String szMenu, String szMenuItem, boolean b) {
		JMenu jmenu;
		JMenuItem jmenuitem;
		for (int i = 0; i < this.getMenuCount(); i++) {
			jmenu = (JMenu) this.getMenu(i);
			if (szMenu.equalsIgnoreCase(jmenu.getText())) {
				if (!jmenu.isEnabled() && b) {
					jmenu.setEnabled(b);
				}
				for (int t = 0; t < jmenu.getItemCount(); t++) {
					jmenuitem = (JMenuItem) jmenu.getItem(t);
					if (jmenuitem != null) {
						if (szMenuItem.equalsIgnoreCase(jmenuitem.getText())) {
							jmenuitem.setEnabled(b);
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the MenuItemEnable attribute of the SDMLEditorMenu object
	 * 
	 *@param szMenu
	 *            Description of Parameter
	 *@param szMenuItem
	 *            Description of Parameter
	 *@return The MenuItemEnable value
	 */
	public boolean isMenuItemEnable(String szMenu, String szMenuItem) {
		boolean enable = false;
		JMenu jmenu;
		JMenuItem jmenuitem;
		for (int i = 0; i < this.getMenuCount(); i++) {
			jmenu = (JMenu) this.getMenu(i);
			if (szMenu.equalsIgnoreCase(jmenu.getText())) {
				for (int t = 0; t < jmenu.getItemCount(); t++) {
					jmenuitem = (JMenuItem) jmenu.getItem(t);
					if (szMenuItem.equalsIgnoreCase(jmenuitem.getText())) {
						enable = jmenuitem.isEnabled();
					}
				}
			}
		}
		return enable;
	}

	/**
	 * Description of the Method
	 */
	private void configureMenu() {
		netMaster.setMnemonic('m');
		// configure the menu
		fileMenu.setMnemonic('f');
		this.add(makeMenu(fileMenu, new Object[] { newGame, stopGame, null,
				exit }, frame));
		extrasMenu.setMnemonic('x');
		this.add(makeMenu(extrasMenu, new Object[] { newWords, editWordList,
				null, letterStat, null, options }, frame));
		netMenu.setMnemonic('n');
		this.add(makeMenu(netMenu, new Object[] { netMenu, netConnect,
				netDisconnect, null, netMaster }, frame));
		hlpMenu.setMnemonic('?');
		this.add(makeMenu(hlpMenu, new Object[] { help, null, about }, frame));
	}

	/**
	 * Description of the Method
	 */
	private void init() {
		setMenuItemEnable("net", "connect", true);
		setMenuItemEnable("net", "disconnect", false);
		setMenuItemEnable("file", "stop searching", false);
		setMenuItemEnable("file", "new game", true);
		setMenuItemSelected("net", "master", false);
	}

	/**
	 * Description of the Method
	 * 
	 *@param parent
	 *            Description of Parameter
	 *@param items
	 *            Description of Parameter
	 *@param target
	 *            Description of Parameter
	 *@return Description of the Returned Value
	 */
	public static JMenu makeMenu(Object parent, Object[] items, Object target) {
		JMenu m = null;
		if (parent instanceof JMenu) {
			m = (JMenu) parent;
		} else if (parent instanceof String) {
			m = new JMenu((String) parent);
		} else {
			return null;
		}

		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				m.addSeparator();
			} else {
				m.add(makeMenuItem(items[i], target));
			}
		}
		return m;
	}

	/**
	 * Description of the Method
	 * 
	 *@param item
	 *            Description of Parameter
	 *@param target
	 *            Description of Parameter
	 *@return Description of the Returned Value
	 */
	public static JMenuItem makeMenuItem(Object item, Object target) {
		JMenuItem r = null;
		if (item instanceof String) {
			r = new JMenuItem((String) item);
		} else if (item instanceof JMenuItem) {
			r = (JMenuItem) item;
		} else {
			return null;
		}

		if (target instanceof ActionListener) {
			r.addActionListener((ActionListener) target);
		}
		return r;
	}

	/**
	 * Description of the Method
	 * 
	 *@param items
	 *            Description of Parameter
	 *@param target
	 *            Description of Parameter
	 *@return Description of the Returned Value
	 */
	public static JPopupMenu makePopupMenu(Object[] items, Object target) {
		JPopupMenu m = new JPopupMenu();
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				m.addSeparator();
			} else {
				m.add(makeMenuItem(items[i], target));
			}
		}
		return m;
	}
}
