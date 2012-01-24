package ro.cuzma.picturemgt.ui;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ro.cuzma.picturemgt.addresses.Address;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class AddressPanel extends javax.swing.JDialog {

	// ===========
	Address				address	= null;
	// ===========

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel		jPanelButtons;
	private JButton		jButtonCancel;
	private JButton		jButtonAdd;
	private JLabel		jLabelState;
	private JTextField	jTextCountry;
	private JTextField	jTextState;
	private JLabel		jLabelCountry;
	private JTextField	jTextCity;
	private JLabel		jLabelCity;
	private JTextField	jTextAddress;
	private JLabel		jLabelAddress;
	private JPanel		jPanelEdits;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		Address add = new Address();
		add.setAddress("aaa");
		add.setCity("Timisiora");
		add.setState("Timis");
		add.setCountry("Romania");
		AddressPanel frame = new AddressPanel(add);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public AddressPanel() {
		super();
		initGUI();
	}

	public AddressPanel(Address address) {
		super();
		initGUI();
		this.address = address;
		jTextAddress.setText(this.address.getAddress());
		jTextState.setText(this.address.getState());
		jTextCity.setText(this.address.getCity());
		jTextCountry.setText(this.address.getCountry());
		jButtonAdd.setText("Update");
		
	}

	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(333, 125));
			this.setSize(333, 125);
			this.setModal(true);
			{
				jPanelButtons = new JPanel();
				FlowLayout jPanelButtonsLayout = new FlowLayout();
				this.add(jPanelButtons, BorderLayout.SOUTH);
				jPanelButtons.setLayout(jPanelButtonsLayout);
				{
					jButtonCancel = new JButton();
					jPanelButtons.add(jButtonCancel);
					jButtonCancel.setText("Cancel");
					jButtonCancel.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent evt) {
							jButtonCancelMouseReleased(evt);
						}
					});
				}
				{
					jButtonAdd = new JButton();
					jPanelButtons.add(jButtonAdd);
					jButtonAdd.setText("Add");
					jButtonAdd.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent evt) {
							
							jButtonAddMouseReleased(evt);
						}
					});
				}

			}
			{
				jPanelEdits = new JPanel();
				GridBagLayout jPanelEditsLayout = new GridBagLayout();
				jPanelEditsLayout.columnWidths = new int[] { 50, 250 };
				jPanelEditsLayout.rowHeights = new int[] { 7, 7, 7, 7 };
				jPanelEditsLayout.columnWeights = new double[] { 0.1, 0.1 };
				jPanelEditsLayout.rowWeights = new double[] { 0.1, 0.1, 0.1, 0.1 };
				this.add(jPanelEdits, BorderLayout.CENTER);
				jPanelEdits.setLayout(jPanelEditsLayout);
				jPanelEdits.setPreferredSize(new java.awt.Dimension(404, 267));
				{
					jLabelAddress = new JLabel();
					jPanelEdits.add(jLabelAddress, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabelAddress.setText("Address");
				}
				{
					jTextAddress = new JTextField();
					jPanelEdits.add(jTextAddress, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					jLabelCity = new JLabel();
					jPanelEdits.add(jLabelCity, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabelCity.setText("City");
				}
				{
					jTextCity = new JTextField();
					jPanelEdits.add(jTextCity, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					jLabelState = new JLabel();
					jPanelEdits.add(jLabelState, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabelState.setText("State");
				}
				{
					jLabelCountry = new JLabel();
					jPanelEdits.add(jLabelCountry, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabelCountry.setText("Country");
				}
				{
					jTextState = new JTextField();
					jPanelEdits.add(jTextState, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					jTextCountry = new JTextField();
					jPanelEdits.add(jTextCountry, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jButtonCancelMouseReleased(MouseEvent evt) {
		this.address = null;
		setVisible(false);
		dispose();

	}
	private void jButtonAddMouseReleased(MouseEvent evt) {
		if (address == null){
			address =  new Address();
		}
		this.address.setAddress(jTextAddress.getText());
		this.address.setCity(jTextCity.getText());
		this.address.setState(jTextState.getText());
		this.address.setCountry(jTextCountry.getText());
		setVisible(false);
		dispose();

	}

	public Address getAddress() {
		return address;
	}



}
