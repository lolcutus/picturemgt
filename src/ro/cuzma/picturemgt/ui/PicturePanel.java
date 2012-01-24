/*
 * Created on 22.06.2005
 * by Laurian Cuzma
 */
package ro.cuzma.picturemgt.ui;

import java.awt.GridLayout;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import ro.cuzma.picturemgt.PictureTableModel;
import ro.cuzma.tools.FileTools;


//import java.awt.Dimension;


/**
 * @author Laurian Cuzma
 *
 * 22.06.2005
 */
public class PicturePanel extends JPanel {
	JTable table = null;

	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(
				new PicturePanel(FileTools.getFilesInDir("F:\\Multimedia\\Picture\\2005\\2005.04-02 La Nasa","jpg")));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.show();
	}
	public PicturePanel(Vector files) {
		super(new GridLayout(1, 0));

		table = new JTable(new PictureTableModel(files));
		//table.getColumnModel().getColumn(1).setPreferredWidth(1000);
		//table.setPreferredScrollableViewportSize(new Dimension(500, 70));

		//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		add(scrollPane);
	}

	public JTable getTable(){
		return table;
}
	private static void createAndShowGUI() {
		//Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		//Create and set up the window.
		JFrame frame = new JFrame("DirsTable");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
