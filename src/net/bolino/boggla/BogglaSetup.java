/**
 * 
 */
package net.bolino.boggla;

import java.util.Properties;
import java.io.*;
import javax.swing.JOptionPane;
import java.awt.Point;

/**
 * @author frb
 * 
 */
public class BogglaSetup extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3652614123948945393L;
	private String iniFileName = "noFileName";

	/**
	 * Constructor for the IniFileAdinistration object
	 * 
	 *@param fileName
	 *            Description of Parameter
	 */
	public BogglaSetup(String fileName) {
		this.setDefaultSettings();
		this.iniFileName = fileName;
		loadIniFile();
	}

	/**
	 * Sets the WordListFileName attribute of the IniFileAdministration object
	 * 
	 *@param fileName
	 *            The new WordListFileName value
	 */
	public void setWordListFileName(String fileName) {
		this.setProperty("WordListFileName", fileName);
	}

	/**
	 * Sets the SearchTime attribute of the IniFileAdministration object
	 * 
	 *@param searchTime
	 *            The new SearchTime value
	 */
	public void setSearchTime(int searchTime) {
		this.setProperty("SearchTime", String.valueOf(searchTime));
	}

	/**
	 * Sets the NumVirtualPlayers attribute of the IniFileAdministration object
	 * 
	 *@param numVirtualPlayers
	 *            The new NumVirtualPlayers value
	 */
	public void setNumVirtualPlayers(int numVirtualPlayers) {
		this.setProperty("NumVirtualPlayers", String.valueOf(numVirtualPlayers));
	}

	/**
	 * Sets the HandicapLevel attribute of the IniFileAdministration object
	 * 
	 *@param levelVirtualPlayer
	 *            The new HandicapLevel value
	 */
	public void setLevelVirtualPlayer(int levelVirtualPlayer) {
		this.setProperty("LevelVirtualPlayer", String.valueOf(levelVirtualPlayer));
	}

	/**
	 * Sets the BogglaLocation attribute of the IniFileAdministration object
	 * 
	 *@param location
	 *            The new BogglaLocation value
	 */
	public void setBogglaLocation(Point location) {
		this.setProperty("PosXBoggle", String.valueOf(location.x));
		this.setProperty("PosYBoggle", String.valueOf(location.y));
	}

	/**
	 * Sets the LocationVirtualPlayer attribute of the IniFileAdministration
	 * object
	 * 
	 *@param index
	 *            The new LocationVirtualPlayer value
	 *@param location
	 *            The new LocationVirtualPlayer value
	 */
	public void setLocationVirtualPlayer(int index, Point location) {
		this.setProperty("PosXVirtualPlayer" + index, String.valueOf(location.x));
		this.setProperty("PosYVirtualPlayer" + index, String.valueOf(location.y));
	}

	/**
	 * Gets the WordListFileName attribute of the IniFileAdministration object
	 * 
	 *@return The WordListFileName value
	 */
	public String getWordListFileName() {
		return getProperty("WordListFileName");
	}

	/**
	 * Gets the SearchTime attribute of the IniFileAdinistration object
	 * 
	 *@return The SearchTime value
	 */
	public int getSearchTime() {
		return Integer.parseInt(getProperty("SearchTime"));
	}

	/**
	 * Gets the NumVitualPlayers attribute of the IniFileAdministration object
	 * 
	 *@return The NumVitualPlayers value
	 */
	public int getNumVitualPlayers() {
		return Integer.parseInt(getProperty("NumVirtualPlayers"));
	}

	/**
	 * Gets the HandicapLevel attribute of the IniFileAdministration object
	 * 
	 *@return The HandicapLevel value
	 */
	public int getLevelVirtualPlayer() {
		return Integer.parseInt(getProperty("LevelVirtualPlayer"));
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Point getVirtualPlayerLocation(int index)
	{
		Point location = new Point(50, 50);
		location.x = Integer.parseInt(getProperty("PosXVirtualPlayer" + index));
		location.y = Integer.parseInt(getProperty("PosYVirtualPlayer" + index));
		return location;
	}

	/**
	 * @param index
	 * @return
	 */
	public Point getBogglaLocation()
	{
		Point location = new Point(20, 20);
		location.x = Integer.parseInt(getProperty("PosXBoggle"));
		location.y = Integer.parseInt(getProperty("PosYBoggle"));
		return location;
	}

	/**
	 * Load Boggla settings of last session.
	 */
	public void loadIniFile() {
		try {
			FileInputStream in = new FileInputStream(new File(iniFileName));
			this.load(in);

		} catch (java.io.FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "file '" + iniFileName
					+ "' not found, loading default settings", "error", JOptionPane.ERROR_MESSAGE);
		} catch (java.io.IOException e) {
			JOptionPane.showMessageDialog(null, "IO failure, loading default settings", "error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Write current setting of Boggla session to ini-file.
	 */
	public void writeIniFile() {
		try {
			FileOutputStream out = new FileOutputStream(new File(iniFileName));

			this.store(out,
							"do not change this file, these are the parameters of last Boggla session");
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "file '" + iniFileName
					+ "' not found", "error", JOptionPane.ERROR_MESSAGE);
		} catch (java.io.IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "IO failure", "error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Get default Boggla settings.
	 * 
	 *@return default settings
	 */
	private void setDefaultSettings() {
		this.setProperty("SearchTime", "90");
		this.setProperty("PosXBoggle", "20");
		this.setProperty("PosYBoggle", "20");
		this.setProperty("WordListFileName", "WordLists/BasicWordList_deutsch.dat");

		this.setProperty("NumVirtualPlayers", "2");
		this.setProperty("LevelVirtualPlayer", "1");
		
		this.setProperty("PosXVirtualPlayer1", "30");
		this.setProperty("PosYVirtualPlayer1", "580");
		this.setProperty("PosXVirtualPlayer2", "290");
		this.setProperty("PosYVirtualPlayer2", "580");
	}
}