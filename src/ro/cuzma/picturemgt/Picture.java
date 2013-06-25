/*
 * Created on 24.02.2006
 * by Laurian Cuzma
 */
package ro.cuzma.picturemgt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import org.apache.log4j.Logger;
import org.jempbox.xmp.XMPSchemaBasic;
import org.jempbox.xmp.XMPSchemaDublinCore;
import org.jempbox.xmp.XMPSchemaPhotoshop;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.cuzma.picturemgt.addresses.Address;
import ro.cuzma.picturemgt.addresses.AddressList;
import ro.cuzma.picturemgt.categories.Category;
import ro.cuzma.picturemgt.categories.CategoryList;
import ro.cuzma.tools.FileTools;
import ro.cuzma.tools.StringTools;
import ro.cuzma.xmp.jpeg.JpegPicture;
import ro.cuzma.xmp.jpeg.XMPManager;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;

public class Picture implements Comparable<Picture> {
	// private String drive;
	private String				path;
	private String				name;
	private Vector<Category>	keywords			= new Vector<Category>();
	// private String driveID;
	private File				picture;
	private byte[]				thumbsPic;
	private int					sizeThumbs			= 100;
	private String				root;
	private int					rating				= 0;
	// private CategoryList categories;
	// private AddressList addresses;
	private Date				picdate				= null;
	private Address				address				= null;
	public final static String	TAG_HEAD			= "Picture";
	public final static String	TAG_NAME			= "name";
	public final static String	TAG_PATH			= "path";
	public final static String	TAG_ADDRESS			= "address";
	public final static String	TAG_CATEGORYLIST	= "CategoryList";
	public final static String	TAG_CATEGORY		= "Category";
	public final static String	TAG_RATING			= "Rating";
	public final static String	FILE_PATH_SEPARATOR	= "|";
	static Logger				logger				= Logger.getLogger(Picture.class);

	private Date getCreateData() {
		Metadata metadata;
		try {
			metadata = JpegMetadataReader.readMetadata(this.getPicture());
			Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
			try {
				picdate = exifDirectory.getDate(ExifDirectory.TAG_DATETIME_ORIGINAL);
			} catch (MetadataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return picdate;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Picture pic;
		try {
			pic = new Picture("[1997-001][camin][001][Caminul 15].jpg", "f:\\Personal\\Picture", "1997\\1997-001 Camin");
			pic.ShowInJPanel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Picture(String name, String root, String path) {
		this.setName(name);
		this.setRoot(root);
		this.path = path;

	}

	public Picture(Node xmlPic, String root, CategoryList categories, AddressList address) {
		// this.categories = categories;
		if (xmlPic.getNodeName().equals("Picture")) {
			processNode(xmlPic, root, categories, address);
		} else if (xmlPic.getNodeName().equals("Asset")) {
			processACDSeeNode(xmlPic, root);
		}
	}

	public Picture(String root, File pic) {
		this.root = root;
		String tmp = pic.getParent().substring(root.length() + 1);
		this.path = replaceFileSeparator(tmp);
		this.name = pic.getName();
	}

	private String replaceFileSeparator(String source) {
		String result = "";
		char[] chars = source.toCharArray();
		for (int i = 0; i < source.length(); i++) {
			if (chars[i] == File.separatorChar) {
				result += FILE_PATH_SEPARATOR;
			} else {
				result += chars[i];
			}
		}
		return result;
	}

	public String toACDSee() {
		String returnStr = "<Asset>\r\n";
		returnStr = returnStr + "<Name>" + this.getName() + "</Name>\r\n";
		returnStr = returnStr + "<Folder>&lt;Local\\" + ro.cuzma.tools.OS.getVolumeSerial(this.getDrive()).replace("-", "")
				+ "&gt;" + this.getRoot().substring(2) + "\\" + this.getPath() + "\\</Folder>\r\n";
		returnStr = returnStr + "<FileType>JPEG</FileType>\r\n";
		returnStr = returnStr + "<ImageType>4673610</ImageType>\r\n";
		Vector tmp = this.getKeywords();
		if (this.getRating() != 0) {
			returnStr = returnStr + "<Rating>" + this.getRating() + "</Rating>\r\n";
		}
		for (int i = 0; i < tmp.size(); i++) {
			if (i == 0) {
				returnStr = returnStr + "<AssetCategoryList>\r\n";
			}
			returnStr = returnStr + "<AssetCategory>" + ((Category) tmp.get(i)).getFullName() + "</AssetCategory>";
			if (i == tmp.size() - 1) {
				returnStr = returnStr + "</AssetCategoryList>\r\n";
			}
		}
		returnStr = returnStr + "</Asset>";
		return returnStr;
	}

	public void show() {
		System.out.print(this.getRoot() + File.separator + this.getPath() + File.separator + this.getName() + " - ");
		Vector tmp = this.getKeywords();
		for (int i = 0; i < tmp.size(); i++) {
			System.out.print(tmp.get(i) + ";");
		}
		System.out.println("");
	}

	public void ShowInJPanel() {
		JFrame frame = new JFrame();
		JPanel jp = new JPanel();
		try {
			BorderLayout thisLayout = new BorderLayout();
			jp.setLayout(thisLayout);
			jp.setPreferredSize(new Dimension(400, 300));
			ImageIcon pictureIcon = new ImageIcon();
			BufferedImage currentImage;
			try {
				currentImage = ImageIO.read(new ByteArrayInputStream(this.getThumbsPic()));
				pictureIcon.setImage(currentImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			JLabel jLabelPicture = new JLabel(pictureIcon);
			// jSplitPaneRight.add(jLabelPicture, JSplitPane.BOTTOM);
			jLabelPicture.setVerticalTextPosition(JLabel.BOTTOM);
			jLabelPicture.setHorizontalTextPosition(JLabel.CENTER);
			// jLabelPicture.setText(this.picture.getAbsolutePath());
			// this.removeAll();
			jp.add(jLabelPicture);
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.getContentPane().add(jp);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * @param thumbsPic
	 *            The thumbsPic to set.
	 */
	public byte[] generateThumbsPic() {

		File pic = this.getPicture();
		if (pic != null) {
			this.thumbsPic = JPEG.resize(this.sizeThumbs, pic).toByteArray();
			return this.thumbsPic;
		} else {
			// System.out.println("No file");
			return null;
		}
	}

	public byte[] generateThumbsPic(int size) {
		File pic = this.getPicture();
		if (pic != null) {
			// this.thumbsPic = JPEG.resize(size, pic).toByteArray();
			return JPEG.resize(size, pic).toByteArray();
		} else {
			// System.out.println("No file");
			return null;
		}
	}

	/**
	 * @return Returns the drive.
	 */
	public String getDrive() {
		return this.root.substring(0, 1);
	}

	/**
	 * @return Returns the keywords.
	 */
	public Vector<Category> getKeywords() {
		return this.keywords;
	}

	/**
	 * @param keywords
	 *            The keywords to set.
	 */
	public void addKeyword(Category keyword) {
		// Address address =
		boolean found = false;
		int i = 0;
		int key;
		int key2;
		Category tst;
		while (!found && i < this.getKeywords().size()) {
			key = keyword.getId();
			tst = (Category) this.getKeywords().get(i);
			System.out.println(tst.getFullName() + "-" + this.name);
			key2 = tst.getId();
			if (key2 == key) {
				found = true;
			}
			i++;
		}
		if (!found) {
			this.keywords.add(keyword);
		}
	}

	/**
	 * @param keywords
	 *            The keywords to set.
	 */
	public void removeKeyword(Category keyword) {
		boolean found = false;
		int i = 0;
		while (!found && i < this.getKeywords().size()) {
			if (((Category) this.getKeywords().get(i)).getId() == keyword.getId()) {
				found = true;
				this.getKeywords().remove(i);
			}
			i++;
		}
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the path.
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * @param path
	 *            The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return Returns the sizeThumbs.
	 */
	public int getSizeThumbs() {
		return this.sizeThumbs;
	}

	/**
	 * @param sizeThumbs
	 *            The sizeThumbs to set.
	 */
	public void setSizeThumbs(int sizeThumbs) {
		this.sizeThumbs = sizeThumbs;
	}

	/**
	 * @param thumbsPic
	 *            The thumbsPic to set.
	 */
	public void setThumbsPic(byte[] thumbsPic) {
		this.thumbsPic = thumbsPic;
	}

	/**
	 * @return Returns the thumbsPic.
	 */
	public byte[] getThumbsPic() {
		return this.thumbsPic;
	}

	/**
	 * @return Returns the root.
	 */
	public String getRoot() {
		return this.root;
	}

	/**
	 * @param root
	 *            The root to set.
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	public String toString() {
		return this.getName();
	}

	/**
	 * @return Returns the picture.
	 */
	public File getPicture() {
		this.picture = new File(getFileFullName());
		return this.picture;
	}

	public String toXML(String indent) {
		String rez = indent + "<" + Picture.TAG_HEAD + ">\r\n";
		rez = rez + indent + "\t<" + Picture.TAG_PATH + ">" + this.getPath() + "</" + Picture.TAG_PATH + ">\r\n";
		rez = rez + indent + "\t<" + Picture.TAG_NAME + ">" + this.getName() + "</" + Picture.TAG_NAME + ">\r\n";
		if (this.getRating() != 0) {
			rez = rez + indent + "\t<" + Picture.TAG_RATING + ">" + this.getRating() + "</" + Picture.TAG_RATING + ">\r\n";
		}
		if (this.address != null) {
			rez = rez + indent + "\t<" + Picture.TAG_ADDRESS + ">" + this.address.getId() + "</" + Picture.TAG_ADDRESS
					+ ">\r\n";
		}
		int total = this.getKeywords().size();
		if (total > 0) {
			rez = rez + indent + "\t<" + Picture.TAG_CATEGORYLIST + ">" + "\r\n";
			for (int i = 0; i < total; i++) {
				rez = rez + indent + "\t\t<" + Picture.TAG_CATEGORY + ">" + this.getKeywords().get(i).getId() + "</"
						+ Picture.TAG_CATEGORY + ">\r\n";
			}
			rez = rez + indent + "\t</" + Picture.TAG_CATEGORYLIST + ">" + "\r\n";
		}
		rez = rez + indent + "</Picture>\r\n";
		return rez;
	}

	private void processACDSeeNode(Node xmlPic, String root) {
		/*
		 * NodeList children = xmlPic.getChildNodes(); String folder = "";
		 * String fileType = ""; String name = ""; String rating = "0"; if
		 * (children != null) { int len = children.getLength(); //
		 * System.out.println("childs len: " + len); Node tmp; for (int i = 0; i
		 * < len; i++) { tmp = children.item(i); if (tmp.getNodeType() ==
		 * Node.ELEMENT_NODE) { // System.out.print(tmp.getNodeName() + "-"); if
		 * (tmp.getNodeName().equalsIgnoreCase("name")) { name =
		 * tmp.getChildNodes().item(0).getNodeValue(); } else if
		 * (tmp.getNodeName().equalsIgnoreCase("folder")) { folder =
		 * tmp.getChildNodes().item(0).getNodeValue(); } else if
		 * (tmp.getNodeName().equalsIgnoreCase("fileType")) { fileType =
		 * tmp.getChildNodes().item(0).getNodeValue(); } else if
		 * (tmp.getNodeName().equalsIgnoreCase("rating")) { rating =
		 * tmp.getChildNodes().item(0).getNodeValue(); } else if
		 * (tmp.getNodeName().equalsIgnoreCase( "AssetCategoryList")) { NodeList
		 * catList = tmp.getChildNodes(); // System.out.print(tmp.getNodeName()
		 * + "-"); int len1 = catList.getLength(); Node tmp1; for (int j = 0; j
		 * < len1; j++) { tmp1 = catList.item(j); if (tmp1.getNodeType() ==
		 * Node.ELEMENT_NODE) { // System.out.print(tmp.getNodeName() + "-"); if
		 * (tmp1.getNodeName().equalsIgnoreCase( "AssetCategory")) {
		 * this.addKeyword(tmp1.getChildNodes() .item(0).getNodeValue()); } } }
		 * } } } if (fileType != null && fileType.equalsIgnoreCase("JPEG")) {
		 * this.setName(name); this.setRoot(root); folder =
		 * folder.substring(folder.indexOf("\\") + 1); folder =
		 * folder.substring(folder.indexOf("\\") + 1, folder .length() - 1); if
		 * (folder.indexOf(root.substring(3)) != 0) { this.name = null; } else {
		 * this.path = folder.substring(root.length() - 2); } Integer rat = new
		 * Integer(rating); this.setRating(rat.intValue()); } else {
		 * this.setName(null); } } else { this.setName(null); }
		 */
	}

	private void processNode(Node xmlPic, String root, CategoryList categories, AddressList address) {
		NodeList children = xmlPic.getChildNodes();
		String path = "";
		String name = "";
		String rating = "0";
		if (children != null) {
			int len = children.getLength();
			// System.out.println("childs len: " + len);
			Node tmp;
			for (int i = 0; i < len; i++) {
				tmp = children.item(i);
				if (tmp.getNodeType() == Node.ELEMENT_NODE) {
					// System.out.print(tmp.getNodeName() + "-");
					if (tmp.getNodeName().equalsIgnoreCase("name")) {
						name = tmp.getChildNodes().item(0).getNodeValue();
					} else if (tmp.getNodeName().equalsIgnoreCase("path")) {
						path = tmp.getChildNodes().item(0).getNodeValue();
					} else if (tmp.getNodeName().equalsIgnoreCase("rating")) {
						rating = tmp.getChildNodes().item(0).getNodeValue();
					} else if (tmp.getNodeName().equalsIgnoreCase("address")) {
						setAddress(tmp.getChildNodes().item(0).getNodeValue(), address);
					} else if (tmp.getNodeName().equalsIgnoreCase("Th")) {
						String cucu = tmp.getChildNodes().item(0).getNodeValue();
						this.thumbsPic = new byte[cucu.length()];
						this.thumbsPic = cucu.getBytes();
					} else if (tmp.getNodeName().equalsIgnoreCase("CategoryList")) {
						NodeList catList = tmp.getChildNodes();
						// System.out.print(tmp.getNodeName() + "-");
						int len1 = catList.getLength();
						Node tmp1;
						for (int j = 0; j < len1; j++) {
							tmp1 = catList.item(j);
							if (tmp1.getNodeType() == Node.ELEMENT_NODE) {
								// System.out.print(tmp.getNodeName() + "-");
								if (tmp1.getNodeName().equalsIgnoreCase("Category")) {
									Integer itmp = new Integer(tmp1.getChildNodes().item(0).getNodeValue());
									/*
									 * Address add =
									 * address.getAddressById(itmp.intValue());
									 * if (add != null) { this.address = add; }
									 * else {
									 */
									logger.debug("size of category: " + categories.getCategories().size());
									this.addKeyword(categories.getCategoryById(itmp.intValue()));
									// }
									/*
									 * this.addKeyword(categories.getByFullName(tmp1
									 * .getChildNodes()
									 * .item(0).getNodeValue()));
									 */
								}
							}
						}
					}
				}
			}
			this.setName(name);
			this.setRoot(root);
			this.path = path;
			Integer rat = new Integer(rating);
			this.setRating(rat.intValue());
		} else {
			this.setName(null);
		}
	}

	public void writeThumbs(RandomAccessFile rw) {
		try {
			String full = this.getFileFullName();
			full = ro.cuzma.tools.StringTools.rightPad(full, 256, ' ');
			rw.writeBytes(full);
			// System.out.println("" + this.thumbsPic.length);
			rw.writeBytes(StringTools.leftPad(this.thumbsPic.length + "", 10, '0'));
			rw.write(this.thumbsPic);
			// rw.
			// rw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadThumbs() {
		try {
			File tmp = new File(this.getRoot() + "\\db.th");
			if (!tmp.exists()) {
				tmp.createNewFile();
			}
			RandomAccessFile rw = new RandomAccessFile(tmp, "rw");
			byte nameB[] = new byte[256];
			byte lengthB[] = new byte[10];
			rw.read(nameB, 0, 256);
			rw.read(lengthB, 0, 10);
			int lenght = (new Integer(new String(lengthB))).intValue();
			this.thumbsPic = new byte[lenght];
			// rw.seek(start);
			rw.read(this.thumbsPic, 0, lenght);
			rw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadThumbs(RandomAccessFile rw, Hashtable toAdd) {
		try {
			byte nameB[] = new byte[256];
			byte lengthB[] = new byte[10];
			int test = 1;
			while (test > 0) {
				test = rw.read(nameB, 0, 256);
				if (test > 0) {
					rw.read(lengthB, 0, 10);
					int lenght = (new Integer(new String(lengthB))).intValue();
					byte thPic[] = new byte[lenght];
					// rw.seek(start);
					rw.read(thPic, 0, lenght);
					String name = new String(nameB);
					// Bytes bt = new Bytes(thPick);
					toAdd.put(name.trim(), thPic);
				}
			}
			// rw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFileFullName() {
		return getFilePath() + File.separator + this.getName();
	}

	public String getFilePathNoRoot() {
		return this.getPath().replace(FILE_PATH_SEPARATOR, File.separator + "");
	}

	public String getFilePath() {
		return this.getRoot() + File.separator + getFilePathNoRoot();
	}

	/*
	 * public ElementImpl toXML2(DocumentImpl doc) { ElementImpl node = new
	 * ElementImpl(doc, Picture.TAG_HEAD); ElementImpl tmp = null; tmp = new
	 * ElementImpl(doc, Picture.TAG_NAME); tmp.appendChild(new TextImpl(doc,
	 * this.getName())); node.appendChild(tmp); tmp = new ElementImpl(doc,
	 * Picture.TAG_PATH); tmp.appendChild(new TextImpl(doc, this.getPath()));
	 * node.appendChild(tmp); if (this.getAddress() != null) { tmp = new
	 * ElementImpl(doc, Picture.TAG_ADDRESS); tmp.appendChild(new TextImpl(doc,
	 * "" + this.getAddress().getId())); node.appendChild(tmp); } if
	 * (this.getRating() != 0) { tmp = new ElementImpl(doc, Picture.TAG_RATING);
	 * tmp.appendChild(new TextImpl(doc, "" + this.getRating()));
	 * node.appendChild(tmp); } Vector<Category> add = this.getKeywords(); if
	 * (add.size() > 0) { tmp = new ElementImpl(doc, Picture.TAG_CATEGORYLIST);
	 * ElementImpl tmp2 = null; for (int i = 0; i < add.size(); i++) { tmp2 =
	 * new ElementImpl(doc, Picture.TAG_CATEGORY); tmp2.appendChild(new
	 * TextImpl(doc, "" + add.get(i).getId())); tmp.appendChild(tmp2); }
	 * node.appendChild(tmp); } return node; }
	 */

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	private void setAddress(String addressID, AddressList add) {
		setAddress(add.getAddressById(new Integer(addressID).intValue()));
	}

	public void saveWithXMP(String exportPath) {
		JpegPicture jpeg;
		boolean writeSomething = false;
		try {
			jpeg = new JpegPicture(this.getFileFullName());
			XMPManager xm = new XMPManager(jpeg);
			if (this.getRating() != 0) {
				XMPSchemaBasic tmpX = xm.getXmpXML().getBasicSchema();
				if (tmpX == null) {
					xm.getXmpXML().addBasicSchema();
					tmpX = xm.getXmpXML().getBasicSchema();
				}
				tmpX.setRating(this.getRating());
				writeSomething = true;
			}

			XMPSchemaDublinCore dc = null;

			Category cat = null;
			for (int i = 0; i < this.getKeywords().size(); i++) {
				cat = this.getKeywords().get(i);
				if (cat.getIsKeyword() == Category.IS_KEYWORD) {
					if (dc == null) {
						dc = xm.getXmpXML().getDublinCoreSchema();
						if (dc == null) {
							xm.getXmpXML().addDublinCoreSchema();
							dc = xm.getXmpXML().getDublinCoreSchema();
						}
						dc.removeSubjectTag();
						writeSomething = true;
					}
					dc.addSubject(cat.getName());
				}
			}
			if (this.getAddress() != null) {
				XMPSchemaPhotoshop ps = xm.getXmpXML().getPhotoshopSchema();
				if (ps == null) {
					ps = xm.getXmpXML().addPhotoshopSchema();
				}
				ps.setCity(this.getAddress().getCity());
				ps.setState(this.getAddress().getState());
				ps.setCountry(this.getAddress().getCountry());
				writeSomething = true;
			}
			String dir = exportPath + File.separator + this.getFilePathNoRoot();
			File dirXmp = new File(dir);
			if (!dirXmp.exists()) {
				dirXmp.mkdirs();
			}
			String fileDest = dir + File.separator + this.getName();
			if (writeSomething) {
				logger.debug("Save XMP to: " + fileDest);
				xm.saveXMPintoAPP1();
				jpeg.saveFile(fileDest);
			} else {
				FileTools.copyfile(this.getFileFullName(), fileDest);

			}

		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// xmpTest.readfromAPP1(picture.getXMPdata());
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(Picture o) {
		return this.getName().compareTo(o.getName());
	}

}
