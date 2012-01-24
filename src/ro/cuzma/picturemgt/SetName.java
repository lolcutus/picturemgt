package ro.cuzma.picturemgt;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import ro.cuzma.tools.FileTools;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free
 * for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using
 * Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS
 * CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class SetName extends javax.swing.JFrame {

    {
        // Set Look & Feel
        try {
            javax.swing.UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JMenuBar jMenuBarMain;
    private JMenu jMenuFile;
    private JMenuItem jMenuClose;
    private JLabel jLabelText;
    private JPanel jPanelLabels;
    private JLabel jLabelNumber;
    private JLabel jLabelPath;
    private JButton jButtonRename;
    private JButton jButtonPreview;
    private JTextField jTextFieldNumber;
    private java.awt.List jListPreview;
    private JSeparator jSeparator1;
    private JMenuItem jMenuItemBrowse;
    private JPanel jPanel1;
    private JTextField jTextFieldText;
    private JPanel jPanelEdit;
    private JScrollPane jScrollPaneFiles;
    private JTextField jTextFieldPath;
    private JPanel jPanelMain;

    File lastDir = new File("f:\\Multimedia\\Picture");
    Vector jpgFiles;
    int lastIndex = -1;
    String numberPart;
    String textPart;

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) {
        SetName inst = new SetName();
        inst.setVisible(true);
    }

    public SetName() {
        super();
        initGUI();
    }

    public SetName(String lastName) {
        super();
        lastDir = new File(lastName);
        initGUI();
    }

    private void initGUI() {
        try {
            {
                jMenuBarMain = new JMenuBar();
                setJMenuBar(jMenuBarMain);
                {
                    jMenuFile = new JMenu();
                    jMenuBarMain.add(jMenuFile);
                    jMenuFile.setText("File");
                    {
                        jMenuItemBrowse = new JMenuItem();
                        jMenuFile.add(jMenuItemBrowse);
                        jMenuItemBrowse.setText("Browse");
                        jMenuItemBrowse.addMouseListener(new MouseAdapter() {
                            public void mouseReleased(MouseEvent evt) {
                                jMenuItemBrowseMouseReleased(evt);
                            }
                        });
                    }
                    {
                        jSeparator1 = new JSeparator();
                        jMenuFile.add(jSeparator1);
                    }
                    {
                        jMenuClose = new JMenuItem();
                        jMenuFile.add(jMenuClose);
                        jMenuClose.setText("Close");
                        jMenuClose.addMouseListener(new MouseAdapter() {
                            public void mouseReleased(MouseEvent evt) {
                                System.exit(0);
                            }

                            public void mouseClicked(MouseEvent evt) {
                                System.out.println("jMenuClose.mouseClicked, event=" + evt);
                                // TODO add your code for jMenuClose.mouseClicked
                            }
                        });
                    }
                }
            }
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            {
                jPanelMain = new JPanel();
                BorderLayout jPanelMainLayout = new BorderLayout();
                jPanelMain.setLayout(jPanelMainLayout);
                this.getContentPane().add(jPanelMain, BorderLayout.NORTH);
                {
                    jPanelLabels = new JPanel();
                    GridLayout jPanel1Layout = new GridLayout(3, 1);
                    jPanel1Layout.setColumns(1);
                    jPanel1Layout.setRows(3);
                    jPanelMain.add(jPanelLabels, BorderLayout.WEST);
                    jPanelLabels.setLayout(jPanel1Layout);
                    {
                        jLabelPath = new JLabel();
                        jPanelLabels.add(jLabelPath);
                        jLabelPath.setText("Path");
                    }
                    {
                        jLabelNumber = new JLabel();
                        jPanelLabels.add(jLabelNumber);
                        jLabelNumber.setText("Number");
                    }
                    {
                        jLabelText = new JLabel();
                        jPanelLabels.add(jLabelText);
                        jLabelText.setText("Text");
                    }
                }
                {
                    jPanelEdit = new JPanel();
                    GridLayout jPanel1Layout1 = new GridLayout(3, 1);
                    jPanel1Layout1.setColumns(1);
                    jPanel1Layout1.setRows(3);
                    jPanelMain.add(jPanelEdit, BorderLayout.CENTER);
                    jPanelEdit.setLayout(jPanel1Layout1);
                    {
                        jTextFieldPath = new JTextField();
                        jPanelEdit.add(jTextFieldPath);
                    }
                    {
                        jTextFieldNumber = new JTextField();
                        jPanelEdit.add(jTextFieldNumber);
                    }
                    {
                        jTextFieldText = new JTextField();
                        jPanelEdit.add(jTextFieldText);
                    }
                }
            }
            {
                jScrollPaneFiles = new JScrollPane();
                this.getContentPane().add(jScrollPaneFiles, BorderLayout.CENTER);
                {
                    ListModel jListPreviewModel = new DefaultComboBoxModel(new String[] {
                            "Item One", "Item Two" });
                    jListPreview = new java.awt.List();
                    jScrollPaneFiles.setViewportView(jListPreview);
                    // jListPreview.setModel(jListPreviewModel);
                }
            }
            {
                jPanel1 = new JPanel();
                GridLayout jPanel1Layout2 = new GridLayout(1, 2);
                jPanel1Layout2.setColumns(2);
                this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
                jPanel1.setLayout(jPanel1Layout2);
                {
                    jButtonPreview = new JButton();
                    jPanel1.add(jButtonPreview);
                    jButtonPreview.setText("Preview");
                    jButtonPreview.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent evt) {
                            jButtonPreviewMouseReleased(evt);
                        }
                    });
                }
                {
                    jButtonRename = new JButton();
                    jPanel1.add(jButtonRename);
                    jButtonRename.setText("Rename");
                    jButtonRename.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent evt) {
                            jButtonRenameMouseReleased(evt);
                        }
                    });
                }
            }
            pack();
            this.setSize(400, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Auto-generated method for setting the popup menu for BookTableHeaderListener component
     */
    private void setComponentPopupMenu(final java.awt.Component parent,
            final javax.swing.JPopupMenu menu) {
        parent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger())
                    menu.show(parent, e.getX(), e.getY());
            }

            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger())
                    menu.show(parent, e.getX(), e.getY());
            }
        });
    }

    private void jMenuItemBrowseMouseReleased(MouseEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (lastDir != null) {
            chooser.setCurrentDirectory(lastDir);
        }
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            lastDir = chooser.getSelectedFile();
            jTextFieldPath.setText(lastDir.getAbsolutePath());
            jListPreview.removeAll();
            int tmp = lastDir.getName().indexOf(" ");
            if (tmp != -1) {
                jTextFieldNumber.setText(lastDir.getName().substring(0, tmp));
                jTextFieldText.setText(lastDir.getName().substring(tmp + 1));
            }
            jListPreview.removeAll();
            Vector tmpV = FileTools.getFilesInDir(chooser.getSelectedFile().getAbsolutePath(),
                    "jpg");
            for (int i = 0; i < tmpV.size(); i++) {
                jListPreview.add(((File) tmpV.get(i)).getName());
            }
        }
    }

    private void jButtonPreviewMouseReleased(MouseEvent evt) {
        try {
            lastDir = new File(jTextFieldPath.getText());
            numberPart = jTextFieldNumber.getText();
            textPart = jTextFieldText.getText();

            // System.out.println(chooser.getSelectedFile().getAbsolutePath());
            PictureProperties pp = new PictureProperties();
            jpgFiles = pp.getFiles(lastDir.getAbsolutePath());
            DateCreated dt = null;
            TreeSet ts = new TreeSet(jpgFiles);
            java.util.Iterator it = ts.iterator();
            // System.out.println(it.);
            int contor = 1;
            jListPreview.removeAll();
            while (it.hasNext()) {
                dt = (DateCreated) it.next();
                dt.setNewName(ro.cuzma.picturemgt.Description.newName(dt.getFile().getName(),
                        numberPart, textPart, contor));
                contor++;
                jListPreview.add(dt.getCreationDate() + "-" + dt.getFile().getName() + "-"
                        + dt.getNewName());
            }
            // FileTools.getFilesInDir(lastDir.getAbsolutePath(),"jpg");
        } catch (Exception ex) {
            jListPreview.removeAll();
            jListPreview.add(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void jButtonRenameMouseReleased(MouseEvent evt) {
        DateCreated dt = null;
        for (int i = 0; i < jpgFiles.size(); i++) {
            dt = (DateCreated) jpgFiles.get(i);
            dt.getFile().renameTo(
                    new File(dt.getFile().getParent() + File.separator + dt.getNewName()));

        }
        javax.swing.JOptionPane.showMessageDialog(null, "Done");
        jpgFiles = null;
        // jTextFieldNumber.setText("");
        // jTextFieldText.setText("");
        // jTextFieldPath.setText("");
        jListPreview.removeAll();
        Vector tmpV = FileTools.getFilesInDir(lastDir.getAbsolutePath(), "jpg");
        for (int i = 0; i < tmpV.size(); i++) {
            jListPreview.add(((File) tmpV.get(i)).getName());
        }
    }

}
