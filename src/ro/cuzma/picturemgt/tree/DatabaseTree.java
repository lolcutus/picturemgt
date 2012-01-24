/*
 * Created on 02.03.2006
 * by Laurian Cuzma
 */
package ro.cuzma.picturemgt.tree;

/*
 * This code is based on an example provided by Richard Stanford, BookTableHeaderListener tutorial
 * reader.
 */

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeModelEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.TreeModelListener;

import org.apache.log4j.Logger;

import ro.cuzma.picturemgt.ui.PictureManagement;

/**
 * This code was generated using CloudGarden's Jigloo SWT/Swing GUI Builder,
 * which is free for non-commercial use. If Jigloo is being used commercially
 * (ie, by BookTableHeaderListener corporation, company or business for any
 * purpose whatever) then you should purchase BookTableHeaderListener license
 * for each developer using Jigloo. Please visit www.cloudgarden.com for
 * details. Use of Jigloo implies acceptance of these licensing terms.
 * ************************************* A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED for this machine, so Jigloo or this code cannot be used legally for
 * any corporate or commercial purpose. *************************************
 */
public class DatabaseTree extends JPanel {
	protected DefaultMutableTreeNode	rootNode;
	protected DefaultTreeModel			treeModel;
	protected JTree						tree;
	private Toolkit						toolkit	= Toolkit.getDefaultToolkit();
	private PictureManagement			parentFrame;
	static Logger						logger	= Logger.getLogger(PictureManagement.class);
	public static String				NO_DB	= "No Database";

	public DatabaseTree(PictureManagement parentFrame) {
		super(new GridLayout(1, 0));
		this.parentFrame = parentFrame;
		rootNode = new DefaultMutableTreeNode(NO_DB);
		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new MyTreeModelListener());

		tree = new JTree(treeModel);
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				treeMouseClicked(evt);
			}
		});

		JScrollPane scrollPane = new JScrollPane(tree);
		add(scrollPane);
	}

	public DatabaseTree() {
	};

	/** Remove all nodes except the root node. */
	public void clear() {
		rootNode.removeAllChildren();
		rootNode.setUserObject(NO_DB);
		treeModel.reload();
	}

	/** Remove the currently selected node. */
	public void removeCurrentNode() {
		TreePath currentSelection = tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
			if (parent != null) {
				treeModel.removeNodeFromParent(currentNode);
				return;
			}
		}

		// Either there was no selection, or the root was selected.
		toolkit.beep();
	}

	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = tree.getSelectionPath();

		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}

		return addObject(parentNode, child, true);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
		return addObject(parent, child, false);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = rootNode;
		}

		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		// Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}

	class MyTreeModelListener implements TreeModelListener {
		public void treeNodesChanged(TreeModelEvent e) {
			DefaultMutableTreeNode node;
			node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

			/*
			 * If the event lists children, then the changed node is the child
			 * of the node we've already gotten. Otherwise, the changed node and
			 * the specified node are the same.
			 */
			try {
				int index = e.getChildIndices()[0];
				node = (DefaultMutableTreeNode) (node.getChildAt(index));
			} catch (NullPointerException exc) {
			}

			System.out.println("The user has finished editing the node.");
			System.out.println("New value: " + node.getUserObject());
		}

		public void treeNodesInserted(TreeModelEvent e) {
		}

		public void treeNodesRemoved(TreeModelEvent e) {
		}

		public void treeStructureChanged(TreeModelEvent e) {
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private void addFromHashtable(DefaultMutableTreeNode pos, Hashtable ht) {
		if (ht != null) {
			Vector tmp = new Vector();
			Enumeration keys = ht.keys();
			while (keys.hasMoreElements()) {
				tmp.add(keys.nextElement());
			}
			TreeSet ts = new TreeSet(tmp);
			java.util.Iterator it = ts.iterator();
			String key;
			while (it.hasNext()) {
				key = (String) it.next();
				DefaultMutableTreeNode p1 = addObject(pos, ht.get(key));
				addFromHashtable(p1, ((FoldersNode) ht.get(key)).getNodes());
			}
		}

	}

	public void populateFromFN(FoldersNode fn) {
		rootNode.setUserObject(fn);
		Hashtable nodes = fn.getNodes();
		addFromHashtable(null, nodes);
	}

	private void treeMouseClicked(MouseEvent e) {
		// int selRow = tree.getRowForLocation(e.getX(), e.getY());
		// System.out.println("aa");
		TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
		if (selPath != null) {
			logger.debug("Selected folder: " + selPath.toString());
			if (selPath != null && !selPath.toString().equals("[" + NO_DB + "]")) {
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) selPath.getLastPathComponent();
				if (n != null) {
					FoldersNode fn = (FoldersNode) n.getUserObject();
					this.parentFrame.processChangeFolder(fn);
				}
			}
		}
	}

}
