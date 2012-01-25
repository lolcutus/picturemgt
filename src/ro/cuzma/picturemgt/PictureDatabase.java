package ro.cuzma.picturemgt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ro.cuzma.picturemgt.addresses.Address;
import ro.cuzma.picturemgt.addresses.AddressList;
import ro.cuzma.picturemgt.categories.Category;
import ro.cuzma.picturemgt.categories.CategoryList;
import ro.cuzma.tools.FileTools;
import ro.cuzma.tools.OS;

public class PictureDatabase {
    private CategoryList       categories   = new CategoryList();                      ;
    private AddressList        addresses    = new AddressList();
    private Vector<Picture>    pictures     = new Vector<Picture>();
    static Logger              logger       = Logger.getLogger(PictureDatabase.class);

    private String             root         = "";
    public File                databaseFile;
    public final static String TAG_HEAD     = "database";
    public final static String TAG_ROOT     = "root";
    public final static String TAG_PICTURES = "Pictures";

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PictureDatabase pd = new PictureDatabase("f:\\Personal\\Picture");

        try {
            pd.loadDataFromACDSee(new File("f:\\de acasa\\ACDSee-Acasa-2006.03.05.txt"));
            pd.databaseFile = new File("f:\\aaa.xml");
            pd.save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public PictureDatabase(String root) {
        this.root = root;
    }

    public PictureDatabase(File dbFile) throws SAXException, IOException,
            ParserConfigurationException {
        load(dbFile);
    }

    public PictureDatabase(String root, String databaseName) {
        this.databaseFile = new File(root + File.separator + databaseName + ".xml");
        this.root = root;
    }

    public void loadDataFromThumbs(File fileFrom) {

    }

    private void traverseACDSee(Node node) {
        // is there anything to do?
        if (node == null) {
            return;
        }
        int type = node.getNodeType();
        switch (type) {
        // print document
        case Node.DOCUMENT_NODE: {
            traverseACDSee(((Document) node).getDocumentElement());
            break;
        }
            // print element with attributes
        case Node.ELEMENT_NODE: {

            // System.out.println("node: " + node.getLocalName());
            if (node.getLocalName().equals("CategoryList")) {
                Node tmp1;
                int len1 = node.getChildNodes().getLength();
                // PictureCategories pcTmp;
                for (int j = 0; j < len1; j++) {
                    tmp1 = node.getChildNodes().item(j);
                    // System.out.println("## " + tmp1.getLocalName());
                    if (tmp1.getLocalName() != null
                            && tmp1.getLocalName().equalsIgnoreCase("Category")) {
                        this.categories.processNode(tmp1, null);
                        // pcTmp = new PictureCategories(tmp1);
                        // categories.add(pcTmp);
                    }
                    // pcTmp.addChildren(pcTmp);
                }
            } else if (node.getLocalName().equals("Asset")) {
                Picture pic = new Picture(node, this.root, this.categories, this.addresses);
                if (pic.getName() != null) {
                    this.pictures.add(pic);
                }
            } else {
                NodeList children = node.getChildNodes();
                if (children != null) {
                    int len = children.getLength();
                    // System.out.println("childs len: " + len);
                    for (int i = 0; i < len; i++) {
                        traverseACDSee(children.item(i));
                    }
                }
            }
            break;
        }
        }
    }

    private void traverse(Node node) {
        // is there anything to do?
        if (node == null) {
            return;
        }
        int type = node.getNodeType();
        switch (type) {
        // print document
        case Node.DOCUMENT_NODE: {
            traverse(((Document) node).getDocumentElement());
            break;
        }
            // print element with attributes
        case Node.ELEMENT_NODE: {

            // System.out.println("node: " + node.getNodeName());
            if (node.getNodeName().equals(CategoryList.TAG_HEAD)
                    && node.getParentNode().getNodeName().equals(PictureDatabase.TAG_HEAD)) {
                categories.processNodeNew(node);
            } else if (node.getNodeName().equals(AddressList.TAG_HEAD)
                    && node.getParentNode().getNodeName().equals(PictureDatabase.TAG_HEAD)) {
                this.addresses.processNodeNew(node);
            } else if (node.getNodeName().equals("Pictures")) {
                // System.out.println("INTRU: " + node.getNodeName());
                Node tmp1;
                int len1 = node.getChildNodes().getLength();
                // System.out.println("## " + len1);
                Picture pcTmp;
                // System.out.println("## " + len1);
                for (int j = 0; j < len1; j++) {
                    tmp1 = node.getChildNodes().item(j);
                    if (tmp1.getNodeName() != null
                            && tmp1.getNodeName().equalsIgnoreCase("Picture")) {
                        // System.out.println("INTRU-TMP1: " +
                        // tmp1.getNodeName());
                        pcTmp = new Picture(tmp1, this.root, this.categories, this.addresses);
                        pictures.add(pcTmp);
                    }
                    // pcTmp.addChildren(pcTmp);
                }
            } else if (node.getNodeName().equals("root")) {
                if (node.getChildNodes().item(0) != null) {
                    this.root = node.getChildNodes().item(0).getNodeValue();
                }
            } else {
                NodeList children = node.getChildNodes();
                if (children != null) {
                    int len = children.getLength();
                    // System.out.println("childs len: " + len);
                    for (int i = 0; i < len; i++) {
                        traverse(children.item(i));
                    }
                }
            }
            break;
        }
        }
    }

    public void loadDataFromACDSee(File fileFrom) throws ParserConfigurationException {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(fileFrom);
            this.traverseACDSee(doc);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(File fileFrom) throws SAXException, IOException, ParserConfigurationException {
        this.databaseFile = fileFrom;
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileFrom);
        this.traverse(doc);

    }

    public void show() {
        Picture tmp;
        for (int i = 0; i < this.pictures.size(); i++) {
            tmp = (Picture) this.pictures.get(i);
            tmp.show();
        }
    }

    public Vector<Category> getCategoriesVector() {
        return categories.getCategories();
    }

    public Vector<Address> getAddressesVector() {
        return addresses.getaddresses();
    }

    public File getDatabaseFile() {
        return databaseFile;
    }

    public Vector<Picture> getPictures() {
        return pictures;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
        for (int i = 0; i < this.pictures.size(); i++) {
            ((Picture) this.pictures.get(i)).setRoot(root);
        }
    }

    /*
     * public void save() throws IOException { String filePath = databaseFile.getAbsolutePath();
     * File tmp = new File(filePath + ".old"); tmp.delete(); databaseFile.renameTo(tmp);
     * databaseFile.createNewFile(); RandomAccessFile ra = new RandomAccessFile(databaseFile, "rw");
     * ra.writeBytes("<database>\r\n"); ra.writeBytes("\t<root>" + this.getRoot() + "</root>\r\n");
     * ra.writeBytes("\t<CategoryList>\r\n"); ra.writeBytes(categories.save("\t\t", null));
     * ra.writeBytes("\t</CategoryList>\r\n"); ra.writeBytes("\t<Pictures>\r\n"); for (int i = 0; i
     * < this.pictures.size(); i++) { ra.writeBytes(((Picture) this.pictures.get(i)).toXML("\t\t"));
     * } ra.writeBytes("\t</Pictures>\r\n"); ra.writeBytes("</database>"); ra.close(); }
     */

    public void save() throws IOException {
        String filePath = databaseFile.getAbsolutePath();
        File tmp = new File(filePath + ".old");
        tmp.delete();
        databaseFile.renameTo(tmp);
        databaseFile.createNewFile();
        RandomAccessFile ra = new RandomAccessFile(databaseFile, "rw");
        ra.writeBytes("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        ra.writeBytes("<database>\r\n");
        ra.write(SpecialToUtf8("\t<root>" + this.getRoot() + "</root>\r\n"));
        ra.write(SpecialToUtf8(addresses.toXML("\t")));
        ra.write(SpecialToUtf8(categories.toXML("\t")));
        ra.writeBytes("\t<Pictures>\r\n");
        for (int i = 0; i < this.pictures.size(); i++) {
            ra.write(SpecialToUtf8(((Picture) this.pictures.get(i)).toXML("\t\t")));
        }
        ra.writeBytes("\t</Pictures>\r\n");
        ra.writeBytes("</database>");
        ra.close();
    }

    public byte[] SpecialToUtf8(String source) throws UnsupportedEncodingException {
        return source.getBytes("UTF8");
    }

    /*
     * public void saveXML() throws IOException { String filePath = databaseFile.getAbsolutePath();
     * File tmp = new File(filePath + ".old"); tmp.delete(); databaseFile.renameTo(tmp);
     * databaseFile.createNewFile(); RandomAccessFile ra = new RandomAccessFile(databaseFile, "rw");
     * byte[] rez = null; Transformer transformer; try { transformer =
     * TransformerFactory.newInstance().newTransformer();
     * transformer.setOutputProperty(OutputKeys.INDENT, "yes");
     * transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
     * transformer.setOutputProperty(OutputKeys.METHOD, "xml");
     * transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // initialize
     * StreamResult with File object to save to file ByteArrayOutputStream ba = new
     * ByteArrayOutputStream(); Result result = new StreamResult(ba); DocumentImpl doc =
     * this.toXML(); DOMSource source = new DOMSource(doc); transformer.transform(source, result);
     * ra.writeBytes(ba.toString());
     * 
     * } catch (TransformerConfigurationException e) { e.printStackTrace(); } catch
     * (TransformerFactoryConfigurationError e) { e.printStackTrace(); } catch (TransformerException
     * e) { e.printStackTrace(); }
     * 
     * ra.close(); }
     */

    /*
     * public DocumentImpl toXML() { DocumentImpl doc = new DocumentImpl(); ElementImpl node = new
     * ElementImpl(doc, AddressList.TAG_HEAD); ElementImpl tmp = null; tmp = new ElementImpl(doc,
     * PictureDatabase.TAG_ROOT); tmp.appendChild(new TextImpl(doc, this.getRoot()));
     * node.appendChild(tmp); node.appendChild(this.getCategories().toXML2(doc));
     * node.appendChild(this.getAddresses().toXML2(doc)); ElementImpl pics = new ElementImpl(doc,
     * PictureDatabase.TAG_PICTURES); for (int i = 0;i < this.getPictures().size();i++){
     * pics.appendChild(this.getPictures().get(i).toXML2(doc)); } node.appendChild(pics); return
     * doc; }
     */

    public void saveAs(File newFile) throws IOException {
        databaseFile = newFile;
        save();
    }

    public void saveACDSee(File newFile) throws IOException {
        String filePath = newFile.getAbsolutePath();
        File tmp = new File(filePath + ".old");
        tmp.delete();
        newFile.renameTo(tmp);
        newFile.createNewFile();
        RandomAccessFile ra = new RandomAccessFile(newFile, "rw");
        ra.writeBytes("<ACDDB Version=\"1.5.0\">\r\n");
        // ra.writeBytes("\t<root>" + this.getRoot() + "</root>\r\n");
        ra.writeBytes("\t<CategoryList>\r\n");
        // System.out.println(this.categories.size());
        /*
         * for (int i = 0; i < this.categories.size(); i++) { ra.writeBytes(((PictureCategories)
         * this.categories.get(i)) .save("\t\t")); }
         */
        ra.writeBytes(categories.saveACDSee(null));
        ra.writeBytes("\t</CategoryList>\r\n");
        ra.writeBytes("\t<AssetList>\r\n");
        Vector<String> tmpV = new Vector<String>();
        String path = "";
        boolean addPath = true;
        for (int i = 0; i < this.pictures.size(); i++) {
            ra.writeBytes(((Picture) this.pictures.get(i)).toACDSee());
            path = ((Picture) this.pictures.get(i)).getFilePath();
            // path = ((Picture) this.pictures.get(i)).getPath();
            addPath = true;
            for (int j = 0; j < tmpV.size(); j++) {
                if (path.equals((String) tmpV.get(j))) {
                    addPath = false;
                    break;
                }
            }
            if (addPath) {
                tmpV.add(path);
            }
        }
        String drive;
        String fName;
        String fPath;
        for (int j = 0; j < tmpV.size(); j++) {
            drive = ((String) tmpV.get(j)).substring(0, 1);
            fName = ((String) tmpV.get(j)).substring(((String) tmpV.get(j)).lastIndexOf("\\") + 1);
            fPath = ((String) tmpV.get(j)).substring(2, ((String) tmpV.get(j)).lastIndexOf("\\"));
            ra.writeBytes("\t<Asset>\r\n");
            ra.writeBytes("\t\t<Name>" + fName + "</Name>\r\n");
            ra.writeBytes("\t\t<Folder>&lt;Local\\"
                    + ro.cuzma.tools.OS.getVolumeSerial(drive).replace("-", "") + "&gt;" + fPath
                    + "</Folder>\r\n");
            ra.writeBytes("\t\t<FileType>Folders</FileType>\r\n");
            ra.writeBytes("\t\t<ImageType></ImageType>\r\n");
            ra.writeBytes("\t</Asset>\r\n");
        }
        ra.writeBytes("\t</AssetList>\r\n");
        ra.writeBytes("<FolderRootList>\r\n");
        ra.writeBytes("<FolderRoot>\r\n");
        ra.writeBytes("<Name>" + this.getDrive() + ":</Name>\r\n");
        ra.writeBytes("<DiscID>"
                + ro.cuzma.tools.OS.getVolumeSerial(this.getDrive()).replace("-", "")
                + "</DiscID>\r\n");
        ra.writeBytes("<RootType>Local</RootType>\r\n");
        ra.writeBytes("<SysPath>" + this.getDrive() + ":</SysPath>\r\n");
        ra.writeBytes("<DBRootPath>Local\\"
                + ro.cuzma.tools.OS.getVolumeSerial(this.getDrive()).replace("-", "")
                + "</DBRootPath>\r\n");
        ra.writeBytes("<VolumeLabel>" + OS.getVolumeLabel(this.getDrive()) + "</VolumeLabel>\r\n");
        ra.writeBytes("<FileSystemType>NTFS</FileSystemType>\r\n");
        ra.writeBytes("</FolderRoot></FolderRootList>\r\n");
        ra.writeBytes("</ACDDB>");
        ra.close();
    }

    public String getDrive() {
        return this.root.substring(0, 1);
    }

    public void saveACDSee(File newFile, String picFolder) throws IOException {
        String filePath = newFile.getAbsolutePath();
        File tmp = new File(filePath + ".old");
        tmp.delete();
        newFile.renameTo(tmp);
        newFile.createNewFile();
        RandomAccessFile ra = new RandomAccessFile(newFile, "rw");
        ra.writeBytes("<ACDDB Version=\"1.4.0\">\r\n");
        ra.writeBytes("<CategoryList>\r\n");
        // System.out.println(this.categories.size());
        /*
         * for (int i = 0; i < this.categories.size(); i++) { ra.writeBytes(((PictureCategories)
         * this.categories.get(i)) .saveACDSee()); }
         */
        ra.writeBytes(categories.saveACDSee(null));
        ra.writeBytes("</CategoryList>\r\n");
        ra.writeBytes("<AssetList>\r\n");
        Picture tmpPic;
        String picPath;
        for (int i = 0; i < this.pictures.size(); i++) {
            tmpPic = (Picture) this.pictures.get(i);
            picPath = tmpPic.getFilePath();
            // System.out.println(picFolder + "<->" + picPath);
            if (picPath.startsWith(picFolder)) {
                ra.writeBytes(tmpPic.toACDSee());
            }
        }
        ra.writeBytes("</AssetList>\r\n");
        ra.writeBytes("<FolderRootList>\r\n");
        ra.writeBytes("<FolderRoot>\r\n");
        ra.writeBytes("<Name>" + this.getDrive() + ":</Name>\r\n");
        ra.writeBytes("<DiscID>"
                + ro.cuzma.tools.OS.getVolumeSerial(this.getDrive()).replace("-", "")
                + "</DiscID>\r\n");
        ra.writeBytes("<RootType>Local</RootType>\r\n");
        ra.writeBytes("<SysPath>" + this.getDrive() + ":</SysPath>\r\n");
        ra.writeBytes("<DBRootPath>Local\\"
                + ro.cuzma.tools.OS.getVolumeSerial(this.getDrive()).replace("-", "")
                + "</DBRootPath>\r\n");
        ra.writeBytes("<VolumeLabel>" + OS.getVolumeLabel(this.getDrive()) + "</VolumeLabel>\r\n");
        ra.writeBytes("<FileSystemType>NTFS</FileSystemType>\r\n");
        ra.writeBytes("</FolderRoot></FolderRootList>\r\n");
        ra.writeBytes("</ACDDB>");
        ra.close();
    }

    /*
     * public void saveItcp(File newFile, String picFolder) throws IOException { String filePath =
     * newFile.getAbsolutePath(); System.out.println(filePath); Picture tmpPic; String picPath; for
     * (int i = 0; i < this.pictures.size(); i++) { tmpPic = (Picture) this.pictures.get(i); picPath
     * = tmpPic.getRoot() + "\\" + tmpPic.getPath();
     * 
     * if (picPath.startsWith(picFolder)) { System.out.println(picFolder + "<->" + picPath);
     * tmpPic.saveIptc(filePath); } }
     * 
     * }
     */

    // public Vector getCategories()
    public void rescanFolder(String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            Vector files = FileTools.getFilesInDir(path, "jpg");
            for (int i = 0; i < files.size(); i++) {
                System.out.println(files.get(i));
            }
        }
    }

    public void removeFolder(String path) {
        Picture tmpPic;
        String picPath;
        for (int i = 0; i < this.pictures.size(); i++) {
            boolean remove = true;
            while (remove) {
                if (i < this.pictures.size()) {
                    tmpPic = (Picture) this.pictures.get(i);
                    picPath = tmpPic.getFilePath();
                    // System.out.println(path +"<->" + picPath);
                    if (picPath.equals(path)) {
                        System.out.println("Remove: " + tmpPic.getName());
                        this.pictures.remove(i);
                    } else {
                        remove = false;
                    }
                } else {
                    remove = false;
                }
            }
        }
    }

    public boolean addFolder(File dir) {
        // System.out.println(dir.getAbsolutePath() + "-" + this.getRoot());
        if (dir.getAbsolutePath().toLowerCase().indexOf(this.getRoot().toLowerCase()) != 0) {
            return false;
        }
        // System.out.println("1");
        boolean add = false;
        boolean tmp = false;
        if (dir.isDirectory()) {
            // System.out.println("2");
            Vector files = FileTools.getFilesInDir(dir.getAbsolutePath(), "jpg");
            Picture picTmp;
            for (int i = 0; i < files.size(); i++) {
                System.out.println(files.get(i));
                picTmp = new Picture(this.root, (File) files.get(i));
                tmp = addPicture(picTmp);
                if (tmp) {
                    add = true;
                }
            }
        }
        // System.out.println("3");
        return add;
    }

    private boolean addPicture(Picture pic) {
        boolean found = false;
        // System.out.println(pic.getName());
        Picture tmp;
        int i = 0;
        while (!found && i < this.pictures.size()) {
            tmp = (Picture) this.pictures.get(i);
            if (tmp.getPath().equals(pic.getPath()) && tmp.getName().equals(pic.getName())) {
                found = true;
            }
            i++;
        }
        if (!found) {
            // System.out.println("-add");
            this.pictures.add(pic);
        }
        return !found;
    }

    public CategoryList getCategories() {
        return categories;
    }

    public AddressList getAddresses() {

        return addresses;
    }

    public void removeCategory(Category category) {
        Picture tmp = null;
        for (int i = 0; i < this.getPictures().size(); i++) {
            this.getPictures().get(i).getKeywords().remove(category);
        }
        this.getCategories().removeCategory(category);
    }

    public void setDatabaseFile(File databaseFile) {
        this.databaseFile = databaseFile;
    }

    public void saveXMPforPath(String path) {
        Picture pic = null;
        String picPath = null;
        logger.debug("Path to set: " + path);
        for (int i = 0; i < this.getPictures().size(); i++) {
            pic = this.getPictures().get(i);
            picPath = pic.getFileFullName();
            if (picPath.startsWith(path)) {
                logger.debug("Picture path: " + picPath);
                pic.saveWithXMP();
            }

        }
    }
}
