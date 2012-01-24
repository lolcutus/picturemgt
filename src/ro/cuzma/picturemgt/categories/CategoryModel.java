package ro.cuzma.picturemgt.categories;

import java.util.TreeSet;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import ro.cuzma.picturemgt.DateCreated;

/**
 * @author Laurian Cuzma
 * 
 *         22.06.2005
 */
public class CategoryModel extends AbstractTableModel {
	public final static String	COL_CATEGORY		= "Category";
	public final static String	COL_SET				= "Set";
	public final static String	COL_GLOBAL			= "Global";
	public final static String	COL_POSITION		= "Position";
	public final static String	COL_ID				= "ID";
	public static int			COL_CATEGORY_POS	= 0;
	public static int			COL_SET_POS			= 1;
	public static int			COL_GLOBAL_POS		= 2;
	public static int			COL_POSITION_POS	= 3;
	public static int			COL_ID_POS			= 4;
	private String[]			columnNames			= { "Category", "Set", "Global", "Position", "ID" };
	private Object[][]			data				= new Object[0][5];

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
	 * contain text ("true"/"false"), rather than BookTableHeaderListener check
	 * box.
	 */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		if (col != 0) {
			return true;
		}
		return false;
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	public void newData(Vector categories) {
		// System.out.println("newData: " + categories.size());
		if (categories == null) {
			data = new Object[0][5];
		} else {
			data = new Object[categories.size()][5];

			TreeSet ts = new TreeSet(categories);
			java.util.Iterator it = ts.iterator();
			// System.out.println(it.);
			int i = 0;
			Category tmp;
			while (it.hasNext()) {
				tmp = (Category) it.next();
				data[i][0] = tmp.getFullName();
				data[i][1] = new Boolean(false);
				data[i][2] = new Boolean(false);
				tmp.setPosition(i + 1);
				data[i][3] = new Integer(tmp.getPosition());
				data[i][4] = new Integer(tmp.getId());
				i++;
				// System.out.println("newData: " + i);
			}
		}
		this.fireTableDataChanged();

	}
}
