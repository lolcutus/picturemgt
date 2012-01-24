/*
 * Created on 22.06.2005
 * by Laurian Cuzma
 */
package ro.cuzma.picturemgt;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;


/**
 * @author Laurian Cuzma
 *
 * 22.06.2005
 */
public class PictureTableModel extends AbstractTableModel {

	public PictureTableModel(Vector files) {
		if (files != null) {
			data = new Object[files.size()][1];
			for (int i = 0; i < files.size(); i++) {
				data[i][0] = files.get(i);
			}
		} else {
			data = new Object[0][1];
		}

	}

	private String[]	columnNames	= { "File" };
	private Object[][]	data		= null;
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	/*
	 * JTable uses this method to determine the default renderer/ editor for
	 * each cell. If we didn't implement this method, then the last column would
	 * contain text ("true"/"false"), rather than BookTableHeaderListener check box.
	 */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

}