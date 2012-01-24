package ro.cuzma.picturemgt.addresses;

import java.util.TreeSet;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import ro.cuzma.picturemgt.DateCreated;

/**
 * @author Laurian Cuzma
 * 
 *         22.06.2005
 */
public class AddressModel extends AbstractTableModel {
	/*
	 * public CategoryModel(Vector categories) { / if (files != null) { data =
	 * new Object[files.size()][2]; for (int i = 0; i < files.size(); i++) {
	 * data[i][0] = files.get(i); JButton tmp = new JButton();
	 * tmp.setText("Start"); data[i][1] = new Boolean(false); } } else { data =
	 * new Object[0][1]; } }
	 */
	public final static String	COL_ADDRESS			= "Address";
	public final static String	COL_SET				= "Set";
	public final static String	COL_GLOBAL			= "Global";
	public final static String	COL_POSITION		= "Position";
	public final static String	COL_ID				= "ID";
	public static int			COL_ADDRESS_POS		= 0;
	public static int			COL_SET_POS			= 1;
	public static int			COL_GLOBAL_POS		= 2;
	public static int			COL_POSITION_POS	= 3;
	public static int			COL_ID_POS			= 4;
	private String[]			columnNames			= { "Address", "Set", "Global", "Position", "ID" };
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

	public int getColNr(String colNAme) {
		int ret = -1;
		if (colNAme.equals(AddressModel.COL_ADDRESS)) {
			ret = AddressModel.COL_ADDRESS_POS;
		} else if (colNAme.equals(AddressModel.COL_SET)) {
			ret = AddressModel.COL_SET_POS;
		} else if (colNAme.equals(AddressModel.COL_GLOBAL)) {
			ret = AddressModel.COL_GLOBAL_POS;
		} else if (colNAme.equals(AddressModel.COL_POSITION)) {
			ret = AddressModel.COL_POSITION_POS;
		} else if (colNAme.equals(AddressModel.COL_ID)) {
			ret = AddressModel.COL_ID_POS;
		} else {
			ret = -1;
		}
		return ret;
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
		if (col > 0) {
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

	public void newData(Vector<Address> category) {
		if (category == null) {
			data = new Object[0][5];
		} else {
			data = new Object[category.size()][5];
			TreeSet<Address> ts = new TreeSet<Address>(category);
			java.util.Iterator<Address> it = ts.iterator();
			// System.out.println(it.);
			int i = 0;
			Address tmp;
			while (it.hasNext()) {
				tmp = (Address) it.next();
				data[i][4] = new Integer(tmp.getId());
				data[i][0] = tmp.getName();
				data[i][1] = new Boolean(false);
				data[i][2] = new Boolean(false);
				tmp.setPosition(i + 1);
				data[i][3] = new Integer(tmp.getPosition());
				i++;
			}
		}
		this.fireTableDataChanged();
	}
}
