package net.bolino.boggla.util.mytable;

// java imports

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

/**
 *  Description of the Class
 *
 *@author     flb
 *@created    11. Januar 2003
 */
public class MyTable extends JTable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7061917010768210312L;

	MyTableEventMulticaster multicaster = null;

	String[] m_headers = null;
	// headers
	Object[][] m_data = null;
	// raw data

	/**
	 *  Constructor for the MyTable object
	 *
	 *@param  headers  Description of Parameter
	 *@param  data     Description of Parameter
	 */
	public MyTable(String[] headers, Object[][] data)
	{
		multicaster = new MyTableEventMulticaster();
		try
		{
			m_data = data;
			m_headers = headers;
			jbInit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 *  Constructor for the MyTable object
	 *
	 *@param  headers  Description of Parameter
	 */
	public MyTable(String[] headers)
	{
		try
		{
			m_headers = headers;
			m_data = new Object[0][0];
			jbInit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 *  Constructor for the MyTable object
	 */
	public MyTable()
	{
		try
		{
			jbInit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 *  Sets the ColumnCellEditor attribute of the MyTable object
	 *
	 *@param  ColName  The new ColumnCellEditor value
	 *@param  obj      The new ColumnCellEditor value
	 */
	public synchronized void setColumnCellEditor(String ColName, Object obj)
	{
		int colPos = 0;
		for (int i = 0; i < m_headers.length; i++)
		{
			if (m_headers[i].equalsIgnoreCase(ColName))
			{
				colPos = i;
			}
		}
		// detect kind of celleditor (combo, radiob, checkbox, ...)
		TableColumnModel columnModel = this.getColumnModel();
		TableColumn a_column = columnModel.getColumn(colPos);
		if (0 == obj.getClass().getName().compareToIgnoreCase("javax.swing.JComboBox"))
		{
			JComboBox CellEditor = (JComboBox) obj;
			a_column.setCellEditor(new DefaultCellEditor(CellEditor));
		}
		else if (0 == obj.getClass().getName().compareToIgnoreCase("javax.swing.JCheckBox"))
		{
			JCheckBox CellEditor = (JCheckBox) obj;
			a_column.setCellEditor(new DefaultCellEditor(CellEditor));
		}
		else if (0 == obj.getClass().getName().compareToIgnoreCase("javax.swing.JTextField"))
		{
			JTextField CellEditor = (JTextField) obj;
			a_column.setCellEditor(new DefaultCellEditor(CellEditor));
		}
	}

	/**
	 *  Sets the ColumnWidth attribute of the MyTable object
	 *
	 *@param  ColName     The new ColumnWidth value
	 *@param  pixel       The new ColumnWidth value
	 *@param  resizeable  The new ColumnWidth value
	 */
	public synchronized void setColumnWidth(String ColName, int pixel, boolean resizeable)
	{
		int colPos = -1;
		for (int i = 0; i < m_headers.length; i++)
		{
			if (m_headers[i].equalsIgnoreCase(ColName))
			{
				colPos = i;
			}
		}
		if (colPos > -1)
		{
			TableColumnModel columnModel = this.getColumnModel();
			TableColumn a_column = columnModel.getColumn(colPos);
			a_column.setMinWidth(pixel);
			a_column.setMaxWidth(pixel);
			a_column.setPreferredWidth(pixel);
			a_column.setWidth(pixel);
			a_column.setResizable(resizeable);
		}
	}

	/**
	 *  Sets the EditableColumns attribute of the MyTable object
	 *
	 *@param  ColNames  The new EditableColumns value
	 */
	public synchronized void setEditableColumns(String[] ColNames)
	{
		if (ColNames == null)
		{
			return;
		}
		int[] colPos = new int[ColNames.length];
		for (int t = 0; t < ColNames.length; t++)
		{
			for (int i = 0; i < m_headers.length; i++)
			{
				if (m_headers[i].equalsIgnoreCase(ColNames[t]))
				{
					colPos[t] = i;
				}
			}
		}
		this.setModel(new MyTableModel(colPos));
	}

	/**
	 *  Description of the Method
	 *
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public synchronized int SetData(Object[][] data)
	{
		m_data = data;
		//SortData(0);
		//this.updateUI();
		return 1;
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public synchronized Object[][] GetSelectedItems()
	{
		int[] nRows = this.getSelectedRows();
		Object[][] res = new Object[nRows.length][m_headers.length];
		for (int i = 0; i < nRows.length; i++)
		{
			for (int n = 0; n < m_headers.length; n++)
			{
				res[i][n] = this.getValueAt(nRows[i], n);
			}
		}
		this.clearSelection();
		return res;
	}

	/**
	 *  Gets the Row attribute of the MyTable object
	 *
	 *@param  index  Description of Parameter
	 *@return        The Row value
	 */
	public synchronized Object[] getRow(int index)
	{
		if (index <= m_data.length)
		{
			return m_data[index];
		}
		else
		{
			return null;
		}
	}

	/**
	 *  Gets the Headers attribute of the MyTable object
	 *
	 *@return    The Headers value
	 */
	public synchronized String[] getHeaders()
	{
		return m_headers;
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public synchronized Object[][] GetAll()
	{
		return m_data;
	}

	/**
	 *  Gets the ColumnIndexAtX attribute of the MyTable object
	 *
	 *@param  PosX  Description of Parameter
	 *@return       The ColumnIndexAtX value
	 */
	public synchronized int getColumnIndexAtX(int PosX)
	{
		TableColumnModel columnModel = this.getColumnModel();
		int viewColumn = columnModel.getColumnIndexAtX(PosX);
		return this.convertColumnIndexToModel(viewColumn);
	}

	/**
	 *  Description of the Method
	 *
	 *@param  objs   Description of Parameter
	 *@param  index  Description of Parameter
	 */
	public synchronized void insertRows(Object[][] objs, int index)
	{
		if (index <= m_data.length)
		{
			Object[][] obj = new Object[m_data.length + objs.length][m_headers.length];

			System.arraycopy(m_data, 0, obj, 0, index);
			System.arraycopy(objs, 0, obj, index, objs.length);
			System.arraycopy(m_data, index, obj, index + objs.length, m_data.length - index);

			m_data = obj;
		}
		else
		{
			this.AddData(objs);
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public synchronized int AddData(Object[][] data)
	{
		RemoveData(data);
		Object[][] obj = new Object[m_data.length + data.length][m_headers.length];

		java.lang.System.arraycopy(m_data, 0, obj, 0, m_data.length);
		java.lang.System.arraycopy(data, 0, obj, m_data.length, data.length);

		m_data = obj;
		SortData(0);
		this.updateUI();

		return 1;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public synchronized int AddDataFast(Object[][] data)
	{
		Object[][] obj = new Object[m_data.length + data.length][m_headers.length];

		java.lang.System.arraycopy(m_data, 0, obj, 0, m_data.length);
		java.lang.System.arraycopy(data, 0, obj, m_data.length, data.length);

		m_data = obj;
		this.updateUI();

		return 1;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public synchronized int RemoveData(Object[][] data)
	{
		//	Object[][] obj = new Object[m_data.length+data.length][m_headers.length];

		boolean bRemove = false;

		for (int i = 0; i < data.length; i++)
		{
			for (int n = 0; n < m_data.length; n++)
			{
				bRemove = true;
				for (int h = 0; h < m_headers.length; h++)
				{
					if (data[i][h] != this.getValueAt(n, h))
					{
						bRemove = false;
						break;
					}
				}
				if (bRemove)
				{
					RemoveRow(n);
				}
			}
		}
		this.updateUI();

		return 1;
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public synchronized int RemoveSelectedRows()
	{
		int[] nRows = this.getSelectedRows();
		for (int i = 0; i < nRows.length; i++)
		{
			Object[][] obj = new Object[m_data.length - 1][m_headers.length];

			java.lang.System.arraycopy(m_data, 0, obj, 0, nRows[i]);
			java.lang.System.arraycopy(m_data, nRows[i] + 1, obj, nRows[i], m_data.length - nRows[i] - 1);

			m_data = obj;
			this.updateUI();
		}
		return 1;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  nRow  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public synchronized int RemoveRow(int nRow)
	{
		Object[][] obj = new Object[m_data.length - 1][m_headers.length];

		java.lang.System.arraycopy(m_data, 0, obj, 0, nRow);
		java.lang.System.arraycopy(m_data, nRow + 1, obj, nRow, m_data.length - nRow - 1);

		m_data = obj;

		return 1;
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public synchronized int RemoveAll()
	{
		if (!(GetAll().length > 0))
		{
			return 1;
		}
		m_data = new Object[0][0];
		this.updateUI();

		return 1;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  strHeader  Description of Parameter
	 *@return            Description of the Returned Value
	 */
	public synchronized int SortData(String strHeader)
	{
		int i = 0;
		for (; i < m_headers.length; i++)
		{
			if (m_headers[i].equals(strHeader))
			{
				break;
			}
		}
		SortData(i - 1);
		return 1;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  nHeader  Description of Parameter
	 *@return          Description of the Returned Value
	 */
	public synchronized int SortData(int nHeader)
	{
		QuickSort.qsort(m_data, nHeader);
		this.updateUI();
		return 1;
	}

	/**
	 *  Description of the Method
	 *
	 *@exception  Exception  Description of Exception
	 */
	private void jbInit() throws Exception
	{
		TableModel dm = new MyTableModel(null);
		// anonymous class

		this.setModel(dm);
		this.setColumnSelectionAllowed(false);
		this.setRowSelectionAllowed(true);

		MouseAdapter listMouseListener =
			new MouseAdapter()
			{
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void mouseClicked(MouseEvent e)
				{
					this_mouseClicked(e);
				}
			};
		JTableHeader th = this.getTableHeader();
		th.addMouseListener(listMouseListener);
	}

	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	private synchronized void this_mouseClicked(MouseEvent e)
	{
		if (!(GetAll().length > 0))
		{
			return;
		}
		TableColumnModel columnModel = this.getColumnModel();
		int viewColumn = columnModel.getColumnIndexAtX(e.getX());
		int column = this.convertColumnIndexToModel(viewColumn);
		String str;
		if (e.getClickCount() == 1 && column != -1)
		{
			str = m_headers[column];
			m_headers[column] = "Sorting ...";
			//int shiftPressed = e.getModifiers() & InputEvent.SHIFT_MASK;
			SortData(column);
			m_headers[column] = str;
		}
	}

	/**
	 *  Description of the Class
	 *
	 *@author     flb
	 *@created    11. Januar 2003
	 */
	private class MyTableModel extends AbstractTableModel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 7857024365292873067L;
		int[] m_EditableColIndex = null;

		/**
		 *  Constructor for the MyTableModel object
		 *
		 *@param  EditableColIndex  Description of Parameter
		 */
		public MyTableModel(int[] EditableColIndex)
		{
			m_EditableColIndex = EditableColIndex;
		}

		/**
		 *  Sets the ValueAt attribute of the MyTableModel object
		 *
		 *@param  aValue  The new ValueAt value
		 *@param  row     The new ValueAt value
		 *@param  column  The new ValueAt value
		 */
		public void setValueAt(Object aValue, int row, int column)
		{
			m_data[row][column] = aValue;
		}

		/**
		 *  Gets the ColumnCount attribute of the MyTableModel object
		 *
		 *@return    The ColumnCount value
		 */
		public int getColumnCount()
		{
			return m_headers.length;
		}

		/**
		 *  Gets the RowCount attribute of the MyTableModel object
		 *
		 *@return    The RowCount value
		 */
		public int getRowCount()
		{
			return m_data.length;
		}

		/**
		 *  Gets the ValueAt attribute of the MyTableModel object
		 *
		 *@param  row  Description of Parameter
		 *@param  col  Description of Parameter
		 *@return      The ValueAt value
		 */
		public Object getValueAt(int row, int col)
		{
			return m_data[row][col];
		}

		/**
		 *  Gets the ColumnName attribute of the MyTableModel object
		 *
		 *@param  column  Description of Parameter
		 *@return         The ColumnName value
		 */
		public String getColumnName(int column)
		{
			return m_headers[column];
		}

		/**
		 *  Gets the ColumnClass attribute of the MyTableModel object
		 *
		 *@param  c  Description of Parameter
		 *@return    The ColumnClass value
		 */
		public Class getColumnClass(int c)
		{
			return getValueAt(0, c).getClass();
		}

		/**
		 *  Gets the CellEditable attribute of the MyTableModel object
		 *
		 *@param  row  Description of Parameter
		 *@param  col  Description of Parameter
		 *@return      The CellEditable value
		 */
		public boolean isCellEditable(int row, int col)
		{
			boolean editable = false;
			if (m_EditableColIndex != null)
			{
				for (int i = 0; i < m_EditableColIndex.length; i++)
				{
					if (m_EditableColIndex[i] == col)
					{
						editable = true;
					}
				}
			}
			return editable;
		}
	}
}
