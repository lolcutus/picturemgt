package ro.cuzma.picturemgt.ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import ro.cuzma.picturemgt.ChangeRoot;
import ro.cuzma.picturemgt.Picture;
import ro.cuzma.picturemgt.PictureDatabase;
import ro.cuzma.picturemgt.SetName;
import ro.cuzma.picturemgt.ThreadLoadPitureLabel;
import ro.cuzma.picturemgt.addresses.Address;
import ro.cuzma.picturemgt.addresses.AddressModel;
import ro.cuzma.picturemgt.categories.Category;
import ro.cuzma.picturemgt.categories.CategoryModel;
import ro.cuzma.picturemgt.tree.DatabaseTree;
import ro.cuzma.picturemgt.tree.FoldersNode;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free
 * for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or
 * business for any purpose whatever) then you should purchase a license for each developer using
 * Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS
 * CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class PictureManagement extends JFrame {
    {
        // Set Look & Feelmy.
        try {
            javax.swing.UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static Logger           logger         = Logger.getLogger(PictureManagement.class);
    private JMenuItem       helpMenuItem;
    private JMenu           jMenuHelp;
    private JScrollPane     jScrollPaneCategories;
    // private JScrollPane jScrollPanePicture;
    private JSplitPane      jSplitPaneCategories;
    private JScrollPane     jScrollPanetext;
    private JMenuItem       jMenuIptcFolder;
    private JMenuItem       jMenuItemSetName;
    private JMenuItem       jMenuItemRemoveFolder;
    private JMenuItem       jMenuItemFolderToACDSee;
    private JMenuItem       jMenuItemToACDSee;
    private JMenuItem       jMenuImportACDSee;
    private JMenuItem       jMenuImportThumbs;
    private JPanel          jPanelPictures;
    private JMenuItem       jMenuItemAddCategory;
    private JMenuItem       jMenuItemFolderToXMP;
    private JScrollPane     jScrollPanePictures;
    private JSeparator      jSeparator3;
    private JSeparator      jSeparator1;
    private JMenuItem       jMenuItemAddAddress;
    private JMenu           jMenuTools;
    private JScrollPane     jScrollPaneAddresses;
    private JTextArea       jTextAreaData;
    private DatabaseTree    jPanelTree;
    private JSplitPane      jSplitPaneH;
    private JSplitPane      jSplitPaneTreePic;
    private JSplitPane      jSplitPaneMain;                                            // @jve:decl-index=0:visual-constraint="10,47"
    private JMenu           jMenuEdit;
    private JMenuItem       exitMenuItem;
    private JSeparator      jSeparator2;
    private JMenuItem       saveAsMenuItem;
    private JMenuItem       saveMenuItem;
    private JMenuItem       openFileMenuItem;
    private JMenuItem       newFileMenuItem;
    private JMenu           jMenuFile;
    private JMenuBar        jMenuBarMaster;
    // private Vector pictures;
    private PictureDatabase picDB;
    private int             currentColumns = 1;
    private FoldersNode     currentFN;
    private int             currentPicSize;
    private JRadioButton    jRadioButton0;
    private JRadioButton    jRadioButton5;
    private JRadioButton    jRadioButton4;
    private JRadioButton    jRadioButton3;
    private JRadioButton    jRadioButton2;
    private JRadioButton    jRadioButton1;
    private ButtonGroup     buttonGroupRating;
    private JPanel          jPanelRating;
    private JPanel          jPanelRatingCat;
    private JMenuItem       jMenuItemAddFolder;
    private JMenuItem       jMenuItemRescanCurentFolder;
    private JTable          jTableCategories;
    private JTable          jTableAddress;
    private JMenu           jMenuExport;
    private JMenuItem       jMenuItemChangeRooot;
    private CategoryModel   jCategoryModel = new CategoryModel();
    private AddressModel    jAddressModel  = new AddressModel();
    private Picture         currentPicture = null;
    private PictureLabel    curPiclabel    = null;
    private JPanel          previewPanel   = null;
    private JLabel          previewLabel   = null;
    private JButton         jButton        = null;

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
        }
        return jButton;
    }

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) {
        PictureManagement inst = new PictureManagement();
        inst.setVisible(true);
        // inst.load(new File(args[0]));
    }

    public PictureManagement() {
        super();
        initGUI();
    }

    private void initGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Picture management");
        try {
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.setSize(825, 466);
            // Menu
            jMenuBarMaster = new JMenuBar();
            // File
            jMenuFile = new JMenu();
            {
                jMenuBarMaster.add(jMenuFile);

                jMenuFile.setText("File");
                // File - New
                newFileMenuItem = new JMenuItem();
                {
                    jMenuFile.add(newFileMenuItem);
                    newFileMenuItem.setText("New");
                    newFileMenuItem.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent evt) {
                            newFileMenuItemMouseReleased(evt);
                        }
                    });
                }
                // File - Open
                openFileMenuItem = new JMenuItem();
                {
                    jMenuFile.add(openFileMenuItem);
                    openFileMenuItem.setText("Open");
                    openFileMenuItem.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent evt) {
                            openFileMenuItemMouseReleased(evt);
                        }
                    });
                }
                // File - Save
                saveMenuItem = new JMenuItem();
                {
                    jMenuFile.add(saveMenuItem);
                    saveMenuItem.setText("Save");
                    saveMenuItem.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent evt) {
                            saveMenuItemMouseReleased(evt);
                        }
                    });
                }
                // File - Save As ...
                saveAsMenuItem = new JMenuItem();
                {
                    jMenuFile.add(saveAsMenuItem);
                    saveAsMenuItem.setText("Save As ...");
                    saveAsMenuItem.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent evt) {
                            jMenuItemSaveAsMouseReleased(evt);
                        }
                    });
                }
                // File - Close

                jSeparator2 = new JSeparator();
                jMenuFile.add(jSeparator2);
                // File - Exit
                exitMenuItem = new JMenuItem();
                {
                    jMenuFile.add(exitMenuItem);
                    exitMenuItem.setText("Exit");
                }
            }
            setJMenuBar(jMenuBarMaster);
            // Edit
            // jMenuEdit = new JMenu();
            jMenuBarMaster.add(getJMenuTools());
            jMenuBarMaster.add(getJMenuEdit());
            jMenuHelp = new JMenu();
            {
                jMenuBarMaster.add(jMenuHelp);
                jMenuHelp.setText("Help");
            }
            jSplitPaneMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            {
                jSplitPaneMain.setResizeWeight(1.0);
                jSplitPaneMain.setSize(new java.awt.Dimension(749, 488));
                // jSplitPaneH
                {
                    jSplitPaneH = new JSplitPane();
                    jSplitPaneH.setPreferredSize(new java.awt.Dimension(390, -1));
                    // jSplitPaneCategories
                    {
                        jSplitPaneCategories = new JSplitPane();
                        jSplitPaneCategories.setResizeWeight(1.0);
                        // jScrollPanePicture
                        {
                            // jScrollPanePicture = new JScrollPane();
                            // jScrollPanePicture.setEnabled(false);
                            // jScrollPanePicture.getVerticalScrollBar().setRequestFocusEnabled(true);
                            // jPanelPictures
                            // jScrollPanePicture.add(jPanelPictures);
                            // jScrollPanePicture.setViewportView(jPanelPictures);
                        }
                        // jPanelRatingCat
                        {
                            jPanelRatingCat = new JPanel();
                            jSplitPaneCategories.add(jPanelRatingCat, JSplitPane.RIGHT);
                            jSplitPaneCategories.add(getJScrollPanePictures(), JSplitPane.LEFT);
                            jPanelRatingCat.setPreferredSize(new java.awt.Dimension(244, 285));
                            jPanelRatingCat.setLayout(new BorderLayout());
                            // jScrollPaneCategories
                            {
                                jScrollPaneCategories = new JScrollPane();
                                jScrollPaneCategories.setPreferredSize(new java.awt.Dimension(544,
                                        435));
                                jTableCategories = new JTable();
                                jScrollPaneCategories.setViewportView(jTableCategories);
                                jTableCategories.setModel(jCategoryModel);
                                jTableCategories.getColumnModel().getColumn(0)
                                        .setPreferredWidth(300);
                                jTableCategories.getColumnModel().getColumn(1)
                                        .setPreferredWidth(20);
                                jTableCategories.getColumnModel().getColumn(2)
                                        .setPreferredWidth(20);
                                jTableCategories.getColumnModel().getColumn(3)
                                        .setPreferredWidth(40);
                                jTableCategories.getColumnModel().getColumn(4)
                                        .setPreferredWidth(40);
                                jTableCategories.addKeyListener(new KeyAdapter() {
                                    public void keyReleased(KeyEvent evt) {
                                        jTableCategoriesKeyReleased(evt);
                                    }
                                });
                                jTableCategories.addMouseListener(new MouseAdapter() {
                                    public void mouseClicked(MouseEvent evt) {
                                        jTableCategoriesMouseClicked(evt);
                                    }
                                });
                            }
                            // jPanelRating
                            {
                                jPanelRating = new JPanel();
                                GridLayout jPanelRatingLayout = new GridLayout(1, 1);
                                jPanelRatingLayout.setColumns(1);
                                jPanelRatingLayout.setHgap(5);
                                jPanelRatingLayout.setVgap(5);
                                jPanelRating.setLayout(jPanelRatingLayout);
                                jPanelRating.setPreferredSize(new java.awt.Dimension(175, 42));
                                buttonGroupRating = new ButtonGroup();
                                jRadioButton1 = new JRadioButton();
                                jRadioButton2 = new JRadioButton();
                                jRadioButton3 = new JRadioButton();
                                jRadioButton4 = new JRadioButton();
                                jRadioButton5 = new JRadioButton();
                                jRadioButton0 = new JRadioButton();
                                buttonGroupRating.add(jRadioButton1);
                                buttonGroupRating.add(jRadioButton2);
                                buttonGroupRating.add(jRadioButton3);
                                buttonGroupRating.add(jRadioButton4);
                                buttonGroupRating.add(jRadioButton5);
                                buttonGroupRating.add(jRadioButton0);
                                jRadioButton0.setText("None");
                                jRadioButton1.setText("1");
                                jRadioButton2.setText("2");
                                jRadioButton3.setText("3");
                                jRadioButton3.setText("3");
                                jRadioButton4.setText("4");
                                jRadioButton5.setText("5");
                                jPanelRating.add(jRadioButton1);
                                jRadioButton1.addMouseListener(new MouseAdapter() {
                                    public void mouseReleased(MouseEvent evt) {
                                        jRadioButtonGroupMouseReleased(evt);
                                    }
                                });
                                jRadioButton2.addMouseListener(new MouseAdapter() {
                                    public void mouseReleased(MouseEvent evt) {
                                        jRadioButtonGroupMouseReleased(evt);
                                    }
                                });
                                jRadioButton3.addMouseListener(new MouseAdapter() {
                                    public void mouseReleased(MouseEvent evt) {
                                        jRadioButtonGroupMouseReleased(evt);
                                    }
                                });
                                jRadioButton4.addMouseListener(new MouseAdapter() {
                                    public void mouseReleased(MouseEvent evt) {
                                        jRadioButtonGroupMouseReleased(evt);
                                    }
                                });
                                jRadioButton5.addMouseListener(new MouseAdapter() {
                                    public void mouseReleased(MouseEvent evt) {
                                        jRadioButtonGroupMouseReleased(evt);
                                    }
                                });
                                jRadioButton0.addMouseListener(new MouseAdapter() {
                                    public void mouseReleased(MouseEvent evt) {
                                        jRadioButtonGroupMouseReleased(evt);
                                    }
                                });
                                jPanelRating.add(jRadioButton2);
                                jPanelRating.add(jRadioButton3);
                                jPanelRating.add(jRadioButton4);
                                jPanelRating.add(jRadioButton5);
                                jPanelRating.add(jRadioButton0);
                            }
                            jPanelRatingCat.add(jScrollPaneCategories, BorderLayout.NORTH);
                            jPanelRatingCat.add(getJScrollPaneAddresses(), BorderLayout.CENTER);
                            jPanelRatingCat.add(jPanelRating, BorderLayout.SOUTH);
                        }
                        // jScrollPanePicture.setIgnoreRepaint(true);
                    }
                    // jSplitPaneTreePic
                    {
                        jSplitPaneTreePic = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                        // jPanelTree
                        {
                            jPanelTree = new DatabaseTree(this);
                            populateTree(jPanelTree);
                            jPanelTree.setMinimumSize(new java.awt.Dimension(10, 200));
                            jPanelTree.setPreferredSize(new java.awt.Dimension(200, 435));
                        }
                        // previewPanel
                        {
                            previewPanel = new JPanel();
                            BorderLayout previewPanelLayout = new BorderLayout();
                            previewPanel.setLayout(previewPanelLayout);
                            previewLabel = new JLabel();
                            previewPanel.add(previewLabel, BorderLayout.CENTER);
                            previewLabel.addMouseListener(new MouseAdapter() {
                                public void mouseReleased(MouseEvent evt) {
                                    previewLabelMouseReleased(evt);
                                }
                            });
                            previewLabel.addComponentListener(new ComponentAdapter() {
                                public void componentResized(ComponentEvent evt) {
                                    previewLabelComponentResized(evt);
                                }
                            });
                        }
                        jSplitPaneTreePic.add(jPanelTree, JSplitPane.TOP);
                        jSplitPaneTreePic.add(previewPanel, JSplitPane.BOTTOM);
                    }
                    jSplitPaneH.add(jSplitPaneCategories, JSplitPane.RIGHT);
                    jSplitPaneH.add(jSplitPaneTreePic, JSplitPane.LEFT);
                    jSplitPaneTreePic.setMinimumSize(new java.awt.Dimension(250, 26));
                    jSplitPaneTreePic.setPreferredSize(new java.awt.Dimension(333, 437));
                }
                // jScrollPanetext
                jScrollPanetext = new JScrollPane();
                {
                    jScrollPanetext.setMinimumSize(new java.awt.Dimension(22, 3));
                    jScrollPanetext.setPreferredSize(new java.awt.Dimension(390, 100));
                    jScrollPanetext.setSize(390, 100);
                    jScrollPanetext.setIgnoreRepaint(true);
                    jTextAreaData = new JTextArea();
                    jScrollPanetext.setViewportView(jTextAreaData);
                }
                jSplitPaneMain.add(jSplitPaneH, JSplitPane.TOP);
                jSplitPaneH.setMinimumSize(new java.awt.Dimension(250, 26));
                jSplitPaneMain.add(jScrollPanetext, JSplitPane.BOTTOM);
            }
            this.getContentPane().add(jSplitPaneMain, BorderLayout.CENTER);
            // jScrollPanePicture.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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

    public void populateTree(DatabaseTree treePanel) {
        /*
         * String p1Name = new String("Parent 1"); String p2Name = new String("Parent 2"); String
         * c1Name = new String("Child 1"); String c2Name = new String("Child 2");
         * DefaultMutableTreeNode p1, p2; p1 = treePanel.addObject(null, p1Name); p2 =
         * treePanel.addObject(null, p2Name); treePanel.addObject(p1, c1Name);
         * treePanel.addObject(p1, c2Name); treePanel.addObject(p2, c1Name); treePanel.addObject(p2,
         * c2Name);
         */
    }

    private void openFileMenuItemMouseReleased(MouseEvent evt) {
        JFileChooser chooser = new JFileChooser();
        // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // if(lastDir != null) {
        // chooser.setCurrentDirectory(lastDir);
        // }
        File thFile;
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            thFile = chooser.getSelectedFile();
            // System.out.println("aaa");
            load(thFile);
        }
    }

    private void newFileMenuItemMouseReleased(MouseEvent evt) {
        /*
         * JFileChooser chooser = new JFileChooser(); //
         * chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // if(lastDir != null) { //
         * chooser.setCurrentDirectory(lastDir); // } File thFile; int returnVal =
         * chooser.showOpenDialog(this);
         */
        // if (returnVal == JFileChooser.APPROVE_OPTION) {
        // thFile = chooser.getSelectedFile();
        this.picDB = new PictureDatabase("");
        // this.picDB.setDatabaseFile(thFile);
        jPanelTree.clear();
        FoldersNode rootF = new FoldersNode(picDB.getRoot(), picDB.getRoot());
        Picture tmp;
        for (int i = 0; i < picDB.getPictures().size(); i++) {
            tmp = (Picture) picDB.getPictures().get(i);
            rootF.addPicture(tmp);
        }
        jPanelTree.populateFromFN(rootF);
        this.jCategoryModel.newData(picDB.getCategoriesVector());
        this.jAddressModel.newData(picDB.getAddressesVector());
    }

    public void processChangeFolder(FoldersNode fn) {

        for (int i = 0; i < jTableCategories.getRowCount(); i++) {
            jTableCategories.setValueAt(false, i, CategoryModel.COL_GLOBAL_POS);
            jTableCategories.setValueAt(false, i, CategoryModel.COL_SET_POS);
        }

        for (int i = 0; i < jTableAddress.getRowCount(); i++) {
            jTableAddress.setValueAt(false, i, AddressModel.COL_GLOBAL_POS);
            jTableAddress.setValueAt(false, i, AddressModel.COL_SET_POS);
        }

        currentPicture = null;
        currentFN = fn;
        jTextAreaData.setText("");
        // jTextAreaData.d
        jPanelPictures.removeAll();
        // System.out.println("I am in");
        Hashtable ht = fn.getPictures();
        if (ht != null) {
            Vector tmp = new Vector();
            Enumeration keys = ht.keys();
            while (keys.hasMoreElements()) {
                tmp.add(keys.nextElement());
            }
            TreeSet ts = new TreeSet(tmp);
            java.util.Iterator it = ts.iterator();
            String key;
            Picture pic;
            int i = 0;
            Vector myPics = new Vector();
            while (it.hasNext()) {
                key = (String) it.next();
                jTextAreaData.append(key + "\n");
                // jTextAreaData.repaint();
                pic = (Picture) ht.get(key);
                PictureLabel mp = new PictureLabel(pic, this, picDB);
                if (i == 0) {
                    currentPicSize = pic.getSizeThumbs();
                    currentColumns = getpicCols();
                    GridLayout jPanelPicturesLayout = new GridLayout(0, currentColumns);
                    jPanelPicturesLayout.setColumns(currentColumns);
                    jPanelPictures.setLayout(jPanelPicturesLayout);
                    currentPicture = pic;
                }
                myPics.add(mp);
                jPanelPictures.add(mp, i);
                if (i == 0) {
                    mp.requestFocusInWindow();
                }
                i++;
            }
            ThreadLoadPitureLabel th = new ThreadLoadPitureLabel("cucu", myPics);
            th.start();
            jPanelPictures.revalidate();
            jPanelPictures.repaint();
        }
    }

    public void loadFromThumbs(File thFile) {
        /*
         * LoadData ld = new LoadData(); pictures = ld.loadDataFromThumbs(thFile);
         * 
         * jPanelTree.clear(); FoldersNode root = new
         * FoldersNode(((Picture)pictures.get(0)).getRoot()); Picture tmp; for(int i =0; i <
         * pictures.size();i++){ tmp = (Picture)pictures.get(i);
         * //tmp.setRoot("F:\\Multimedia\\Picture"); root.addPicture(tmp); }
         * jPanelTree.populateFromFN(root);
         */
    }

    private void jMenuImportThumbsMousePressed(MouseEvent evt) {
        JFileChooser chooser = new JFileChooser();
        // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // if(lastDir != null) {
        // chooser.setCurrentDirectory(lastDir);
        // }
        File thFile;
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            thFile = chooser.getSelectedFile();
            loadFromThumbs(thFile);// ,"f:\\Personal\\Picture");
        }
    }

    private void jMenuImportACDSeeMouseReleased(MouseEvent evt) {
        JFileChooser chooser = new JFileChooser();
        // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // if(lastDir != null) {
        // chooser.setCurrentDirectory(lastDir);
        // }
        File thFile;
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            thFile = chooser.getSelectedFile();
            loadFromACDSee(thFile, "f:\\Personal\\Picture");
        }
    }

    public void loadFromACDSee(File thFile, String root) {
        picDB = new PictureDatabase(root);
        try {
            picDB.loadDataFromACDSee(thFile);
            // LoadData ld = new LoadData();
            // pictures = ld.loadDataFromThumbs(thFile);
            jPanelTree.clear();
            FoldersNode rootF = new FoldersNode(root, root);
            Picture tmp;
            for (int i = 0; i < picDB.getPictures().size(); i++) {
                tmp = (Picture) picDB.getPictures().get(i);
                // tmp.setRoot("F:\\Multimedia\\Picture");
                rootF.addPicture(tmp);
            }
            jPanelTree.populateFromFN(rootF);
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void load(File thFile) {

        try {
            picDB = new PictureDatabase(thFile);
            jPanelTree.clear();
            FoldersNode rootF = new FoldersNode(picDB.getRoot(), picDB.getRoot());
            Picture tmp;
            for (int i = 0; i < picDB.getPictures().size(); i++) {
                tmp = (Picture) picDB.getPictures().get(i);
                // tmp.setRoot("F:\\Multimedia\\Picture");
                rootF.addPicture(tmp);
            }
            jPanelTree.populateFromFN(rootF);
            this.jCategoryModel.newData(picDB.getCategoriesVector());
            this.jAddressModel.newData(picDB.getAddressesVector());
            this.setTitle("Picture Mangement: " + thFile.getAbsolutePath());
            RunMe.setDBFile(thFile);
        } catch (Exception e) {
            picDB = null;
            jPanelTree.clear();
            this.jCategoryModel.newData(null);
            this.jAddressModel.newData(null);
            e.printStackTrace();
        }

    }

    private void reloadTree() {
        // picDB = new PictureDatabase(thFile);
        jPanelTree.clear();
        FoldersNode rootF = new FoldersNode(picDB.getRoot(), picDB.getRoot());
        Picture tmp;
        for (int i = 0; i < picDB.getPictures().size(); i++) {
            tmp = (Picture) picDB.getPictures().get(i);
            // tmp.setRoot("F:\\Multimedia\\Picture");
            rootF.addPicture(tmp);
        }
        jPanelTree.populateFromFN(rootF);
        this.jCategoryModel.newData(picDB.getCategoriesVector());
        /*
         * this.jScrollPaneCategories.revalidate(); this.jScrollPaneCategories.repaint();
         */
        // picture.repaint();
    }

    public JTextArea getTextArea() {
        return jTextAreaData;
    }

    public JPanel getPicturePanel() {
        return jPanelPictures;
    }

    private void jPanelPicturesComponentResized(ComponentEvent evt) {
        if (currentFN != null) {
            int newColumns = getpicCols();
            if (newColumns != currentColumns) {
                // currentColumns = newColumns;
                processChangeFolder(currentFN);
            }
        }
    }

    private int getpicCols() {
        if (currentPicSize == 0)
            return 1;
        int panelSize = 0;
        /*
         * if (jScrollPanePicture.getVerticalScrollBar().isVisible()) { panelSize =
         * jPanelPictures.getVisibleRect().width + (int)
         * jScrollPanePicture.getVerticalScrollBar().getSize().getWidth(); } else { panelSize =
         * jPanelPictures.getVisibleRect().width; } int tmp = panelSize / (currentPicSize + 5); if
         * (tmp == 0) tmp = 1;
         */
        return 4;
    }

    private void jMenuItemChangeRoootMouseReleased(MouseEvent evt) {
        ChangeRoot cr = new ChangeRoot(this);
        // System.out.println(cr.showDialog());
        this.picDB.setRoot(cr.showDialog());
        reloadTree();
    }

    private void saveMenuItemMouseReleased(MouseEvent evt) {
        if (this.picDB.getDatabaseFile() == null) {
            jMenuItemSaveAsMouseReleased(evt);
        } else {
            getTextArea().setText("Saving ...");
            try {
                this.picDB.save();
                getTextArea().append("\nDone");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                getTextArea().append("\n" + e.getMessage());
            }
        }
    }

    public CategoryModel getCategoryModel() {
        return this.jCategoryModel;
    }

    public AddressModel getAddressModel() {
        return this.jAddressModel;
    }

    private void jTableCategoriesMouseClicked(MouseEvent evt) {
        // if (currentPicture != null) {
        if (evt.getButton() == MouseEvent.BUTTON3 && currentPicture != null) {
            int comp = jPanelPictures.getComponentCount();
            PictureLabel mp;
            for (int i = 0; i < comp; i++) {
                mp = (PictureLabel) jPanelPictures.getComponent(i);
                if (mp.getPic() == this.currentPicture && i < comp - 1) {
                    jPanelPictures.getComponent(i + 1).requestFocus();
                }
            }
        }
        if (evt.getButton() == MouseEvent.BUTTON1) {
            int selRow = jTableCategories.getSelectedRow();
            int selCat = ((Integer) jTableCategories.getValueAt(selRow, CategoryModel.COL_ID_POS))
                    .intValue();
            int selCol = jTableCategories.getSelectedColumn();
            if (selCol == CategoryModel.COL_SET_POS && currentPicture != null) {
                if (((Boolean) jTableCategories.getValueAt(selRow, 1)).booleanValue()) {
                    this.currentPicture.addKeyword(picDB.getCategories().getCategoryById(selCat));
                } else {
                    this.currentPicture
                            .removeKeyword(picDB.getCategories().getCategoryById(selCat));
                }
            }
            if (selCol == CategoryModel.COL_CATEGORY_POS) {
                CategoryPanel frame = new CategoryPanel(picDB.getCategories().getCategoryById(
                        new Integer(selCat).intValue()));
                frame.setBounds(new Rectangle((int) (this.getSize().getWidth() - frame.getSize()
                        .getWidth()) / 2, (int) (this.getSize().getHeight() - frame.getSize()
                        .getHeight()) / 2, 100, 100));
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                if (frame.getAction() == CategoryPanel.ACTION_ADD_MOD) {
                    this.picDB.getCategories().addCategory(frame.getCategory(), frame.getNewVal());
                    this.jCategoryModel.newData(picDB.getCategoriesVector());
                    /*
                     * this.jScrollPaneCategories.revalidate();
                     * this.jScrollPaneCategories.repaint();
                     */
                } else if (frame.getAction() == CategoryPanel.ACTION_DELETE) {
                    this.picDB.removeCategory(frame.getCategory());
                    this.jCategoryModel.newData(picDB.getCategoriesVector());
                    /*
                     * this.jScrollPaneCategories.revalidate();
                     * this.jScrollPaneCategories.repaint();
                     */
                }
            }
        }
        // }
        if (evt.getButton() == MouseEvent.BUTTON2) {
            int comp = jPanelPictures.getComponentCount();
            PictureLabel mp;
            for (int i = 0; i < comp; i++) {
                mp = (PictureLabel) jPanelPictures.getComponent(i);
                if (mp.getPic() == this.currentPicture && i < comp - 1) {
                    jPanelPictures.getComponent(i - 1).requestFocus();
                }
            }
        }
    }

    private void jTableAddressesMouseClicked(MouseEvent evt) {
        // if (currentPicture != null) {
        if (evt.getButton() == MouseEvent.BUTTON3) {
            int comp = jPanelPictures.getComponentCount();
            PictureLabel mp;
            for (int i = 0; i < comp; i++) {
                mp = (PictureLabel) jPanelPictures.getComponent(i);
                if (mp.getPic() == this.currentPicture && i < comp - 1) {
                    jPanelPictures.getComponent(i + 1).requestFocus();
                }
            }
        }
        if (evt.getButton() == MouseEvent.BUTTON1) {
            int selRow = jTableAddress.getSelectedRow();
            int selCol = jTableAddress.getSelectedColumn();
            Integer selCat = (Integer) jTableAddress.getValueAt(selRow, AddressModel.COL_ID_POS);
            if (selCol == AddressModel.COL_SET_POS) {
                if (currentPicture != null) {
                    if (((Boolean) jTableAddress.getValueAt(selRow, AddressModel.COL_SET_POS))
                            .booleanValue()) {
                        this.currentPicture.setAddress(picDB.getAddresses().getAddressById(
                                new Integer(selCat).intValue()));
                    } else {
                        this.currentPicture.setAddress((Address) null);
                    }
                }
                // unselect all others
                for (int j = 0; j < getAddressModel().getRowCount(); j++) {
                    if (j != selRow) {
                        getAddressModel().setValueAt(new Boolean(false), j,
                                AddressModel.COL_SET_POS);
                    }
                }

            }
            if (selCol == AddressModel.COL_GLOBAL_POS) {

                for (int j = 0; j < getAddressModel().getRowCount(); j++) {
                    if (j != selRow) {
                        getAddressModel().setValueAt(new Boolean(false), j,
                                AddressModel.COL_GLOBAL_POS);
                    }
                }

            }

            if (selCol == AddressModel.COL_ADDRESS_POS) {
                AddressPanel frame = new AddressPanel(picDB.getAddresses().getAddressById(
                        new Integer(selCat).intValue()));
                frame.setBounds(new Rectangle((int) (this.getSize().getWidth() - frame.getSize()
                        .getWidth()) / 2, (int) (this.getSize().getHeight() - frame.getSize()
                        .getHeight()) / 2, 100, 100));
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                if (frame.getAddress() != null) {
                    this.picDB.getAddresses().addAddress(frame.getAddress());
                    this.jAddressModel.newData(picDB.getAddressesVector());
                    /*
                     * this.jScrollPaneAddresses.revalidate(); this.jScrollPaneAddresses.repaint();
                     */
                }
            }
        }
        // }
        if (evt.getButton() == MouseEvent.BUTTON2 && currentPicture != null) {
            int comp = jPanelPictures.getComponentCount();
            PictureLabel mp;
            for (int i = 0; i < comp; i++) {
                mp = (PictureLabel) jPanelPictures.getComponent(i);
                if (mp.getPic() == this.currentPicture && i < comp - 1) {
                    jPanelPictures.getComponent(i - 1).requestFocus();
                }
            }
        }
    }

    public Picture getCurrentPicture() {
        return currentPicture;
    }

    public void setCurrentPicture(Picture currentPicture) {
        this.currentPicture = currentPicture;
    }

    private void jMenuItemRescanCurentFolderMouseReleased(MouseEvent evt) {
        this.picDB.rescanFolder(this.currentFN.getPath());
    }

    private void jMenuItemAddFolderMouseReleased(MouseEvent evt) {
        if (this.picDB.getRoot().equals("")) {
            JOptionPane.showMessageDialog(this, "Need to set root folder first!");
        } else {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // if(lastDir != null) {
            chooser.setCurrentDirectory(new File(this.picDB.getRoot()));
            // }
            File thFile;
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                thFile = chooser.getSelectedFile();
                if (this.picDB.addFolder(thFile)) {
                    jPanelTree.clear();
                    FoldersNode rootF = new FoldersNode(picDB.getRoot(), picDB.getRoot());
                    Picture tmp;
                    for (int i = 0; i < picDB.getPictures().size(); i++) {
                        tmp = (Picture) picDB.getPictures().get(i);
                        // tmp.setRoot("F:\\Multimedia\\Picture");
                        rootF.addPicture(tmp);
                    }
                    jPanelTree.populateFromFN(rootF);
                    this.jCategoryModel.newData(picDB.getCategoriesVector());
                    /*
                     * this.jScrollPaneCategories.revalidate();
                     * this.jScrollPaneCategories.repaint();
                     */
                }
            }
        }
    }

    /*
     * public JScrollPane getJScrollPanePicture() { return jScrollPanePicture; }
     */

    public ButtonGroup getButtonGroupRating() {
        return buttonGroupRating;
    }

    private void jRadioButtonGroupMouseReleased(MouseEvent evt) {
        String bText = ((JRadioButton) evt.getSource()).getText();
        int tmp = 0;
        if (!bText.equals("None")) {
            tmp = (new Integer(bText)).intValue();
        }
        this.getCurrentPicture().setRating(tmp);
        // TODO add your code for jRadioButton1.mouseReleased
    }

    public PictureLabel getCurPiclabel() {
        return curPiclabel;
    }

    public void setCurPiclabel(PictureLabel curPiclabel) {
        this.curPiclabel = curPiclabel;
    }

    public JLabel getpreviewLabel() {
        return previewLabel;
    }

    public void setpreviewLabel(JLabel previewLabel) {
        this.previewLabel = previewLabel;
    }

    private void previewLabelComponentResized(ComponentEvent evt) {
        if (this.curPiclabel != null) {
            ThreadLoadPitureLabel th = new ThreadLoadPitureLabel("cucu", this.getpreviewLabel(),
                    this.curPiclabel);
            th.run();
        }
    }

    private void jMenuItemToACDSeeMouseReleased(MouseEvent evt) {
        JFileChooser chooser = new JFileChooser();
        // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // if(lastDir != null) {
        chooser.setCurrentDirectory(new File(this.picDB.getRoot()));
        // }
        File thFile;
        int returnVal = chooser.showOpenDialog(this);
        getTextArea().setText("Exporting ...");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            thFile = chooser.getSelectedFile();
            try {
                this.picDB.saveACDSee(thFile);
                getTextArea().append("\nDone");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                getTextArea().append("\n" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void jMenuItemFolderToACDSeeMouseReleased(MouseEvent evt) {
        JFileChooser chooser = new JFileChooser();
        // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // if(lastDir != null) {
        chooser.setCurrentDirectory(new File(this.picDB.getRoot()));
        // }
        File thFile;
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            getTextArea().setText("Exporting ...");
            thFile = chooser.getSelectedFile();
            try {
                this.picDB.saveACDSee(thFile, currentFN.getPath());
                getTextArea().append("\nDone");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                getTextArea().append("\n" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void jMenuItemRemoveFolderMouseReleased(MouseEvent evt) {
        this.picDB.removeFolder(currentFN.getPath());
        jPanelTree.clear();
        FoldersNode rootF = new FoldersNode(picDB.getRoot(), picDB.getRoot());
        Picture tmp;
        for (int i = 0; i < picDB.getPictures().size(); i++) {
            tmp = (Picture) picDB.getPictures().get(i);
            // tmp.setRoot("F:\\Multimedia\\Picture");
            rootF.addPicture(tmp);
        }
        jPanelTree.populateFromFN(rootF);
        // this.jScrollPaneCategories.revalidate();
        // this.jScrollPaneCategories.repaint();
    }

    private void jTableCategoriesKeyReleased(KeyEvent evt) {
        // TODO add your code for jTableCategories.keyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int selRow = jTableCategories.getSelectedRow() - 1;
            // System.out.println("row:" + selRow);
            // PictureCategories.getNrOfcat());
            if (selRow == -1) {
                selRow = this.picDB.getCategories().getNrOfcat() - 1;
            }
            // System.out.println("row:" + selRow);
            String selCatFullName = (String) jTableCategories.getValueAt(selRow, 0);
            Category selCat = this.picDB.getCategories().getByFullName(selCatFullName);
            if (selCat != null) {
                Integer selPos = (Integer) jTableCategories.getValueAt(selRow, 3);
                try {
                    Integer tmp = new Integer(selPos);
                    this.picDB.getCategories().changePosition(selCat, tmp.intValue());
                    this.jCategoryModel.newData(picDB.getCategoriesVector());
                    /*
                     * this.jScrollPaneCategories.revalidate();
                     * this.jScrollPaneCategories.repaint();
                     */
                } catch (Exception e) {
                    // System.out.println("cucu");
                    jTableCategories.setValueAt(selCat.getPosition() + "", selRow, 3);
                }
            }
            // System.out.println(tmp.toString());
        }
    }

    private void previewLabelMouseReleased(MouseEvent evt) {
        if (this.currentPicture != null) {
            // System.out.println("in");
            String ecmd = this.currentPicture.getFileFullName();
            String[] cmd = { "cmd.exe", "/C", ecmd };
            final Process process;
            try {
                process = Runtime.getRuntime().exec(cmd);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else {
            ;
            // System.out.println("cucu");
        }
    }

    private void jMenuItemSetNameMouseReleased(MouseEvent evt) {
        SetName s;
        if (this.currentFN != null) {
            // System.out.println(this.currentFN.getPath());
            s = new SetName(this.currentFN.getPath());
        } else {
            s = new SetName();
        }
        s.setVisible(true);
    }

    private JMenuItem getJMenuIptcFolder() {
        if (jMenuIptcFolder == null) {
            jMenuIptcFolder = new JMenuItem();
            jMenuIptcFolder.setText("Save Database to IPTC for curent Folder");
            jMenuIptcFolder.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    jMenuIptcFolderMouseReleased(evt);
                }
            });
        }
        return jMenuIptcFolder;
    }

    private void jMenuIptcFolderMouseReleased(MouseEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // if(lastDir != null) {
        chooser.setCurrentDirectory(new File(this.picDB.getRoot()));
        // }
        File thFile;
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            getTextArea().setText("Saving IPTC ...");
            thFile = chooser.getSelectedFile();
            int len = this.picDB.getRoot().length();
            getTextArea().append("\n" + picDB.getRoot());
            getTextArea().append("\n" + thFile.getAbsolutePath());
            if (len <= thFile.getAbsolutePath().length()
                    && thFile.getAbsolutePath().substring(0, len).equalsIgnoreCase(picDB.getRoot())) {
                getTextArea().append(
                        "\nWrong folder for new Files.\nCan not contain database root!!!");
            } else {

                getTextArea().append("\nNot Working");
            }
        }
    }

    private JScrollPane getJScrollPaneAddresses() {
        if (jScrollPaneAddresses == null) {
            jScrollPaneAddresses = new JScrollPane();
            jScrollPaneAddresses.setViewportView(getJTableAddress());
        }
        return jScrollPaneAddresses;
    }

    private JTable getJTableAddress() {
        if (jTableAddress == null) {
            jTableAddress = new JTable();

            jTableAddress.setModel(jAddressModel);
            jTableAddress.getColumnModel().getColumn(0).setPreferredWidth(300);
            jTableAddress.getColumnModel().getColumn(1).setPreferredWidth(20);
            jTableAddress.getColumnModel().getColumn(2).setPreferredWidth(20);
            jTableAddress.getColumnModel().getColumn(3).setPreferredWidth(40);
            jTableAddress.getColumnModel().getColumn(4).setPreferredWidth(40);
            jTableAddress.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent evt) {
                    jTableCategoriesKeyReleased(evt);
                }
            });
            jTableAddress.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    jTableAddressesMouseClicked(evt);
                }
            });
        }
        return jTableAddress;
    }

    private JMenu getJMenuEdit() {
        if (jMenuEdit == null) {
            jMenuEdit = new JMenu();
            jMenuEdit.setText("Tools");
            // Edit - Rescan
            jMenuItemRescanCurentFolder = new JMenuItem();
            {
                jMenuEdit.add(jMenuItemRescanCurentFolder);
                jMenuItemRescanCurentFolder.setText("Rescan");
                jMenuItemRescanCurentFolder.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent evt) {
                        jMenuItemRescanCurentFolderMouseReleased(evt);
                    }
                });
            }
            {
                jMenuItemSetName = new JMenuItem();
                jMenuEdit.add(jMenuItemSetName);
                jMenuItemSetName.setText("Set Name");
                jMenuItemSetName.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent evt) {
                        jMenuItemSetNameMouseReleased(evt);
                    }
                });
            }
        }
        return jMenuEdit;
    }

    private JMenu getJMenuTools() {
        if (jMenuTools == null) {
            jMenuTools = new JMenu();
            jMenuTools.setText("Database");
            // File - Database - Export
            jMenuExport = new JMenu();
            jMenuTools.add(jMenuExport);
            jMenuTools.add(getJSeparator1());
            {
                jMenuExport.setText("Export");
                {
                    jMenuItemToACDSee = new JMenuItem();
                    jMenuExport.add(jMenuItemToACDSee);
                    jMenuItemToACDSee.setText("To ACDSee");
                    jMenuItemToACDSee.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent evt) {
                            jMenuItemToACDSeeMouseReleased(evt);
                        }
                    });
                }
                {
                    jMenuItemFolderToACDSee = new JMenuItem();
                    jMenuExport.add(jMenuItemFolderToACDSee);
                    jMenuExport.add(getJMenuItemFolderToXMP());
                    jMenuItemFolderToACDSee.setText("Selected Folder to ACDSee");
                    jMenuItemFolderToACDSee.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent evt) {
                            jMenuItemFolderToACDSeeMouseReleased(evt);
                        }
                    });
                }
            }
            // File - Database - Change Root
            jMenuItemChangeRooot = new JMenuItem();
            jMenuTools.add(jMenuItemChangeRooot);
            {
                jMenuItemChangeRooot.setText("Change Root");
                jMenuItemChangeRooot.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent evt) {
                        jMenuItemChangeRoootMouseReleased(evt);
                    }
                });
            }
            jMenuTools.add(getJSeparator1());

            // Edit - Add folder
            jMenuItemAddFolder = new JMenuItem();
            jMenuTools.add(jMenuItemAddFolder);
            {
                jMenuItemAddFolder.setText("Add Folder");
                jMenuItemAddFolder.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent evt) {
                        jMenuItemAddFolderMouseReleased(evt);
                    }
                });
            }
            {
                jMenuItemRemoveFolder = new JMenuItem();
                jMenuTools.add(jMenuItemRemoveFolder);
                jMenuTools.add(getJSeparator3());
                // jMenuTools.add(getJSeparator1());
                jMenuItemRemoveFolder.setText("Remove Selected Folder");
                jMenuItemRemoveFolder.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent evt) {
                        jMenuItemRemoveFolderMouseReleased(evt);
                    }
                });
            }
            jMenuTools.add(getJMenuIptcFolder());
            jMenuTools.add(getJMenuItemAddAddress());
            jMenuTools.add(getJMenuItemAddCategory());
        }
        return jMenuTools;
    }

    private JMenuItem getJMenuItemAddAddress() {
        if (jMenuItemAddAddress == null) {
            jMenuItemAddAddress = new JMenuItem();
            jMenuItemAddAddress.setText("Add Address");
            jMenuItemAddAddress.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    jMenuItemAddAddressMouseReleased(evt);
                }
            });
        }
        return jMenuItemAddAddress;
    }

    private void jMenuItemAddAddressMouseReleased(MouseEvent evt) {
        AddressPanel frame = new AddressPanel();
        frame.setBounds(new Rectangle(
                (int) (this.getSize().getWidth() - frame.getSize().getWidth()) / 2, (int) (this
                        .getSize().getHeight() - frame.getSize().getHeight()) / 2, 100, 100));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        if (frame.getAddress() != null) {
            this.picDB.getAddresses().addAddress(frame.getAddress());
            this.jAddressModel.newData(picDB.getAddressesVector());
        }

    }

    private JMenuItem getJMenuItemAddCategory() {
        if (jMenuItemAddCategory == null) {
            jMenuItemAddCategory = new JMenuItem();
            jMenuItemAddCategory.setText("Add Category");
            jMenuItemAddCategory.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    jMenuItemAddCategoryMouseReleased(evt);
                }
            });
        }
        return jMenuItemAddCategory;
    }

    private void jMenuItemAddCategoryMouseReleased(MouseEvent evt) {
        CategoryPanel frame = new CategoryPanel();
        frame.setBounds(new Rectangle(
                (int) (this.getSize().getWidth() - frame.getSize().getWidth()) / 2, (int) (this
                        .getSize().getHeight() - frame.getSize().getHeight()) / 2, 100, 100));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        if (frame.getAction() == CategoryPanel.ACTION_ADD_MOD) {
            this.picDB.getCategories().addCategory(frame.getCategory(), frame.getNewVal());
            this.jCategoryModel.newData(picDB.getCategoriesVector());
        }
    }

    private void jMenuItemSaveAsMouseReleased(MouseEvent evt) {
        boolean create = true;
        JFileChooser chooser = new JFileChooser();
        // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // if(lastDir != null) {
        chooser.setCurrentDirectory(new File(this.picDB.getRoot()));
        // }

        int returnVal = chooser.showOpenDialog(this);
        File thFile = chooser.getSelectedFile();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (thFile.exists()) {
                if (JOptionPane.showConfirmDialog(this,
                        "File already exists. Do you want to overide?") != JOptionPane.YES_OPTION) {
                    create = false;
                }
            }
            if (create) {
                getTextArea().setText("Saving ...");
                try {
                    this.picDB.saveAs(thFile);
                    getTextArea().append("\nDone");
                    this.setTitle("Picture Mangement: " + thFile.getAbsolutePath());
                    RunMe.setDBFile(thFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    getTextArea().append("\n" + e.getMessage());
                }
            }
        }
    }

    private JSeparator getJSeparator1() {
        if (jSeparator1 == null) {
            jSeparator1 = new JSeparator();
        }
        return jSeparator1;
    }

    private JSeparator getJSeparator3() {
        if (jSeparator3 == null) {
            jSeparator3 = new JSeparator();
        }
        return jSeparator3;
    }

    private JScrollPane getJScrollPanePictures() {
        if (jScrollPanePictures == null) {
            jScrollPanePictures = new JScrollPane();
            jScrollPanePictures.setPreferredSize(new java.awt.Dimension(266, 285));
            {
                jPanelPictures = new JPanel();
                jScrollPanePictures.setViewportView(jPanelPictures);
                GridLayout jPanelPicturesLayout = new GridLayout(1, 1);
                jPanelPicturesLayout.setColumns(1);
                jPanelPicturesLayout.setHgap(5);
                jPanelPicturesLayout.setVgap(5);
                jPanelPictures.setLayout(jPanelPicturesLayout);
                // jPanelPictures.add(getJButton(), null);
                jPanelPictures.addComponentListener(new ComponentAdapter() {
                    public void componentResized(ComponentEvent evt) {
                        jPanelPicturesComponentResized(evt);
                    }
                });
            }
        }
        return jScrollPanePictures;
    }

    private JMenuItem getJMenuItemFolderToXMP() {
        if (jMenuItemFolderToXMP == null) {
            jMenuItemFolderToXMP = new JMenuItem();
            jMenuItemFolderToXMP.setText("Save XMP for curent folder");
            jMenuItemFolderToXMP.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    jMenuItemFolderToXMPMouseReleased(evt);
                }
            });
        }
        return jMenuItemFolderToXMP;
    }

    private void jMenuItemFolderToXMPMouseReleased(MouseEvent evt) {
        getTextArea().setText("Exporting ...");
        this.picDB.saveXMPforPath(currentFN.getPath());
        getTextArea().append("\nDone");

    }

}
