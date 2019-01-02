package net.bolino.boggla.util.mytable;

// utilities imports
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     frb
 *@created    2. March 2009
 */
public class MyTableEventMulticaster
{
	/**
	 *  Description of the Field
	 */
	protected Vector<MyTableEventListener> listener = new Vector<MyTableEventListener>();

	/**
	 *  Description of the Method
	 *
	 *@param  l  Description of Parameter
	 */
	public void remove(MyTableEventListener l)
	{
		listener.remove(l);
	}

	/**
	 *  Description of the Method
	 *
	 *@param  l  Description of Parameter
	 */
	public void add(MyTableEventListener l)
	{
		if (!listener.contains(l))
		{
			listener.add(l);
		}
	}

	//public void onMyTableClose(MyTableEvent e)
	//{
	//for(int i=0; i<listener.size(); i++)
	//((MyTableEventListener)listener.elementAt(i)).onMyTableClose(e);
	//}
}
