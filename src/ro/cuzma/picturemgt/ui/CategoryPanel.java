package ro.cuzma.picturemgt.ui;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import org.apache.log4j.Logger;

import com.sun.crypto.provider.JceKeyStore;

import ro.cuzma.picturemgt.categories.Category;

public class CategoryPanel extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 992634661757277121L;
	// ===========
	Category			category	= null;
	public final static String	ACTION_DELETE	= "DELETE";
	public final static String	ACTION_ADD_MOD	= "ADD_MOD";
	public final static String	ACTION_NONE	= "NONE";
	private String action = CategoryPanel.ACTION_NONE;
	static Logger logger = Logger.getLogger(Category.class);
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
	private JButton		jButtonDelete;
	private JLabel		jLabelCategory;
	private JLabel		jLabelKeyword;
	private JTextField	jTextCategory;
	private String		newVal		= "";
	private JPanel		jPanelEdits;
	private JCheckBox   jChekBoxKeyword;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		Category add = new Category();
		add.setName("gigi");
		add.setParent(null);
		CategoryPanel frame = new CategoryPanel(add);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public CategoryPanel() {
		super();
		initGUI();
		jButtonDelete.setVisible(false);
	}

	public CategoryPanel(Category category) {
		super();
		initGUI();
		this.category = category;
		if(this.category.getIsKeyword() == Category.IS_KEYWORD){
			jChekBoxKeyword.setSelected(true);
		}
		jTextCategory.setText(this.category.getFullName());
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
					jButtonDelete = new JButton();
					jPanelButtons.add(jButtonDelete);
					jButtonDelete.setText("Delete");
					jButtonDelete.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent evt) {
							jButtonDeleteMouseReleased(evt);
						}
					});
				}
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
				jPanelEditsLayout.rowHeights = new int[] { 7, 7,7,7 };
				jPanelEditsLayout.columnWeights = new double[] { 0.1, 0.1 };
				jPanelEditsLayout.rowWeights = new double[] { 0.1, 0.1 , 0.1, 0.1};
				this.add(jPanelEdits, BorderLayout.CENTER);
				jPanelEdits.setLayout(jPanelEditsLayout);
				jPanelEdits.setPreferredSize(new java.awt.Dimension(404, 267));
				{
					jLabelCategory = new JLabel();
					jPanelEdits.add(jLabelCategory, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabelCategory.setText("Category");
				}
				{
					jTextCategory = new JTextField();
					jPanelEdits.add(jTextCategory, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					jLabelKeyword = new JLabel();
					jPanelEdits.add(jLabelKeyword, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabelKeyword.setText("is Keyword?");
				}
				{
					jChekBoxKeyword = new JCheckBox();
					jPanelEdits.add(jChekBoxKeyword, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jButtonCancelMouseReleased(MouseEvent evt) {
		action = CategoryPanel.ACTION_NONE;
		this.category = null;
		setVisible(false);
		dispose();

	}

	private void jButtonAddMouseReleased(MouseEvent evt) {
		action = CategoryPanel.ACTION_ADD_MOD;
		if (category == null) {
			category = new Category();
		}
		newVal = jTextCategory.getText();
		if (jChekBoxKeyword.isSelected()){
			category.setIsKeyword(Category.IS_KEYWORD);
			logger.debug("Set IsKeyword");
		}else{
			category.setIsKeyword(Category.IS_NOT_KEYWORD);
			logger.debug("unset IsKeyword");
		}
		setVisible(false);
		dispose();

	}

	public Category getCategory() {
		return category;
	}

	public String getNewVal() {
		return newVal;
	}
	
	private void jButtonDeleteMouseReleased(MouseEvent evt) {
		action = CategoryPanel.ACTION_DELETE;
		if (JOptionPane.showConfirmDialog(this,"Are you sure") == JOptionPane.YES_OPTION){
			setVisible(false);
			dispose();
		}
		

	}

	public String getAction() {
		return action;
	}
}
