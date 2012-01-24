package ro.cuzma.picturemgt;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by BookTableHeaderListener corporation, company or business for any purpose
 * whatever) then you should purchase BookTableHeaderListener license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ChangeRoot extends javax.swing.JDialog {
	private JPanel		jPanelMain;
	private JTextField	jTextFieldRoot;
	private JLabel		jLabelRoot;
	private JPanel		jPanelField;
	private JPanel		jPanelButtons;
	private JButton		jButtonBrowse;
	private JButton		jButtonOK;
	private String root;

	/**
	 * Auto-generated main method to display this JDialog
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		ChangeRoot inst = new ChangeRoot(frame);
		inst.setVisible(true);
	}

	public ChangeRoot(JFrame frame) {
		super(frame);
	}

	private void initGUI() {
		try {
			{
				this.setDefaultLookAndFeelDecorated(true);
				this.setName("changeRoot");
				this.setModal(true);
				int widthP = this.getParent().getWidth();
				int width = this.getWidth();
				int heightP = this.getParent().getHeight();
				int height = this.getHeight();
				
				this.setLocation(new java.awt.Point(this.getParent().getLocation().x + (widthP - width)/2, this.getParent().getLocation().y +(heightP - height)/2));
				
			}
			{
				jPanelMain = new JPanel();
				BorderLayout jPanelMainLayout = new BorderLayout();
				jPanelMain.setLayout(jPanelMainLayout);
				getContentPane().add(jPanelMain, BorderLayout.CENTER);
				jPanelMain.setSize(400, 100);
				{
					jPanelButtons = new JPanel();
					GridLayout jPanelButtonsLayout = new GridLayout(1, 2);
					jPanelButtonsLayout.setColumns(2);
					jPanelButtonsLayout.setHgap(5);
					jPanelButtonsLayout.setVgap(5);
					jPanelButtons.setLayout(jPanelButtonsLayout);
					jPanelMain.add(jPanelButtons, BorderLayout.SOUTH);
					{
						jButtonBrowse = new JButton();
						jPanelButtons.add(jButtonBrowse);
						jButtonBrowse.setText("Browse");
						jButtonBrowse.addMouseListener(new MouseAdapter() {
							public void mouseReleased(MouseEvent evt) {
								jButtonBrowseMouseReleased(evt);
							}
						});
					}
					{
						jButtonOK = new JButton();
						jPanelButtons.add(jButtonOK);
						jButtonOK.setText("OK");
						jButtonOK.addMouseListener(new MouseAdapter() {
							public void mouseReleased(MouseEvent evt) {
								jButtonOKMouseReleased(evt);
							}
						});
					}
				}
				{
					jPanelField = new JPanel();
					jPanelMain.add(jPanelField, BorderLayout.CENTER);
					jPanelField.setSize(400, 80);
					jPanelField.setOpaque(false);
					{
						jLabelRoot = new JLabel();
						jPanelField.add(jLabelRoot);
						jLabelRoot.setText("Root");
						jLabelRoot.setPreferredSize(new java.awt.Dimension(80,
								14));
					}
					{
						jTextFieldRoot = new JTextField();
						jPanelField.add(jTextFieldRoot);
						jTextFieldRoot.setPreferredSize(new java.awt.Dimension(
								284, 20));
					}
				}
			}
			this.setSize(400, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jButtonBrowseMouseReleased(MouseEvent evt) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// if(lastDir != null) {
		// chooser.setCurrentDirectory(lastDir);
		// }
		File thFile;
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.jTextFieldRoot.setText(chooser.getSelectedFile()
					.getAbsolutePath());
		}
	}

	private void jButtonOKMouseReleased(MouseEvent evt) {
		this.root = this.jTextFieldRoot.getText();
		this.dispose();
	}

	public String showDialog() {
		initGUI();
		setVisible(true);
		return this.root;
	}
}
