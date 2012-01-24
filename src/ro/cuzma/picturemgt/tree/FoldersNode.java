/*
 * Created on 02.03.2006
 * by Laurian Cuzma
 */
package ro.cuzma.picturemgt.tree;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import ro.cuzma.picturemgt.Picture;

public class FoldersNode {

    private Hashtable nodes;
    private Hashtable pictures = new Hashtable();
    private String label;
    private String path;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public FoldersNode(String path, String label) {
        this.path = path;
        this.label = label;
    }

    public Hashtable getNodes() {
        return nodes;
    }

    public FoldersNode addNode(String label) {
        if (nodes == null) {
            nodes = new Hashtable();
            nodes.put(label, new FoldersNode(this.path + File.separator + label, label));
        } else {
            if (!nodes.containsKey(label)) {
                nodes.put(label, new FoldersNode(this.path + File.separator + label, label));
            }
        }
        return (FoldersNode) nodes.get(label);

    }

    public void addPicture(Picture pic) {
        /*
         * if (pictures == null){ pictures = new TreeMap(); } pictures.put(pic.getName(),pic);
         */
        FoldersNode tmp = this;
        String path = pic.getPath();
        // System.out.println(path);
        if (path != null) {
            StringTokenizer st = new StringTokenizer(path, File.separator);
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                // System.out.println(temp);
                tmp = tmp.addNode(temp);
            }
        }
        Hashtable tmpH = tmp.getPictures();

        if (tmpH == null) {
            tmpH = new Hashtable();
            tmpH.put(pic.getName(), pic);
        } else {
            if (!tmpH.containsKey(pic.getName())) {
                tmpH.put(pic.getName(), pic);
            }
        }
    }

    public void show(String tabs) {
        System.out.println(tabs + this.getLabel());
        tabs = tabs + "\t";
        if (pictures != null) {
            Vector tmp = new Vector();
            Enumeration keys = pictures.keys();
            while (keys.hasMoreElements()) {
                tmp.add(keys.nextElement());
            }
            TreeSet ts = new TreeSet(tmp);
            java.util.Iterator it = ts.iterator();
            String key;
            while (it.hasNext()) {
                key = (String) it.next();
                // System.out.println("--" + key);
                System.out.println(tabs + ((Picture) pictures.get(key)).getName());
            }
        }
        if (nodes != null) {
            Vector tmp = new Vector();
            Enumeration keys = nodes.keys();
            while (keys.hasMoreElements()) {
                tmp.add(keys.nextElement());
            }
            TreeSet ts = new TreeSet(tmp);
            java.util.Iterator it = ts.iterator();
            // System.out.println(it.);
            int contor = 1;
            String key;
            while (it.hasNext()) {
                key = (String) it.next();
                // System.out.println("--" + key);
                ((FoldersNode) nodes.get(key)).show(tabs);
            }
        }

    }

    /**
     * @return Returns the label.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * @return Returns the pictures.
     */
    public Hashtable getPictures() {
        return this.pictures;
    }

    public String toString() {
        return this.getLabel();
    }

    public String getPath() {
        return path;
    }

}
