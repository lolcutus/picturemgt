package ro.cuzma.picturemgt.categories;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException; //import org.apache.xerces.parsers.DOMParser;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ro.cuzma.picturemgt.addresses.Address;
import ro.cuzma.picturemgt.addresses.AddressList;
import ro.cuzma.picturemgt.ui.PictureManagement;
import org.apache.log4j.Logger;

public class CategoryList {
	private Vector<Category>	categories	= new Vector<Category>();
	// private static int nrOfcat = 0;
	public final static String	TAG_HEAD	= "Categories";
	static Logger				logger		= Logger.getLogger(CategoryList.class);

	// public static AddressList addresses = new AddressList();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String toParse = "<Categories>" + "<Category>" + "	<ID>76</ID>" + "	<Name>2008</Name>" + "	<Position>2</Position>" + "</Category>"
				+ "<Category>" + "	<ID>91</ID>" + "	<Name>tags</Name>" + "	<Position>93</Position>" + "	<CategoryList>" + "		<Category>"
				+ "			<ID>92</ID>" + "			<Name>zoo</Name>" + "			<Position>22</Position>" + "		</Category>" + "		<Category>"
				+ "			<ID>93</ID>" + "			<Name>elevi Aura</Name>" + "			<Position>23</Position>" + "		</Category>" + "		<Category>"
				+ "			<ID>95</ID>" + "			<Name>web</Name>" + "			<Position>1</Position>" + "		</Category>" + "		<Category>"
				+ "			<ID>96</ID>" + "			<Name>concediu</Name>" + "			<Position>24</Position>" + "		</Category>" + "		<Category>"
				+ "			<ID>97</ID>" + "			<Name>mare</Name>" + "			<Position>25</Position>" + "		</Category>" + "	</CategoryList>"
				+ "</Category>" + "</Categories>";
		ByteArrayInputStream is = new ByteArrayInputStream(toParse.getBytes());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			CategoryList adl = new CategoryList();
			Node tmp1;
			int len1 = document.getChildNodes().getLength();
			for (int j = 0; j < len1; j++) {
				tmp1 = document.getChildNodes().item(j);
				adl.processNodeNew(tmp1);
			}
			System.out.println(adl.toXML(""));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public PictureCategories(Node node) { processNode(node, null); }
	 */
	public CategoryList() {
		// processNode(node, null);
	}

	public Category getCategoryById(int id) {
		Category tmp = null;
		// System.out.println("id:" + id);
		for (int i = 0; i < categories.size(); i++) {
			tmp = (Category) categories.get(i);
			if (tmp.getId() == id)
				return tmp;

		}
		// System.out.println("nu gasit:" + id);
		return null;
	}

	public Category getCategoryByFullName(String fullName) {
		Category tmp = null;
		for (int i = 0; i < categories.size(); i++) {
			tmp = (Category) categories.get(i);
			if (tmp.getFullName().equalsIgnoreCase(fullName))
				return tmp;

		}
		// System.out.println("nu gasit:" + id);
		return null;
	}

	public void addCategory(Category category) {
		logger.debug("add: " + category.getFullName());
		if (category.getParent() != null) {
			Category tmp = getCategoryByFullName(category.getParent().getFullName());
			if (tmp != null) {
				tmp.addChildrenCategory(category);
			}
		}
		this.categories.add(category);

	}

	public void addCategory(Category category, String newVal) {
		// check if id already exist then we need to delete old and add new :)

		// add category, maybe path is changed
		// process parents
		StringTokenizer st = new StringTokenizer(newVal, Category.SEPARATOR);
		Category cat = null;
		Category parent = null;
		String full = "";
		Vector tmp = new Vector();
		while (st.hasMoreTokens()) {
			tmp.add(st.nextElement());
		}
		int i = 0;
		for (i = 0; i < tmp.size() - 1; i++) {
			if (parent != null) {
				full = parent.getFullName() + Category.SEPARATOR + tmp.get(i);
			} else {
				full = (String) tmp.get(i);
			}
			cat = this.getByFullName(full);
			if (cat == null) {
				cat = new Category();
				cat.setId(this.getMaxID() + 1);
				cat.setName((String) tmp.get(i));
				cat.setParent(parent);
				this.addCategory(cat);
			}
			parent = cat;
		}
		Category existCat = this.getCategoryById(category.getId());
		if (existCat != null) {
			Category newCat = new Category(existCat.getId(), (String) tmp.get(i), 0, parent, category.getIsKeyword());
			Category tmpCat = null;
			boolean found = false;
			// check if we have already an tag with the same name
			// logger.debug("cat: " + newCat.getId() + "-" +
			// newCat.getFullName());
			for (int j = 0; j < this.categories.size(); j++) {
				tmpCat = this.categories.get(j);
				// logger.debug("tmp: " + tmpCat.getId() + "-" +
				// tmpCat.getFullName());
				if (tmpCat.getId() != newCat.getId() && tmpCat.getFullName().equalsIgnoreCase(newCat.getFullName())) {
					found = true;
					break;
				}
			}
			if (found) {
				logger.debug("id: " + tmpCat.getId() + "-" + category.getId() + " with the same name: " + tmpCat.getFullName());
			} else {
				existCat.setName((String) tmp.get(i));
				existCat.setParent(parent);
				existCat.setIsKeyword(category.getIsKeyword());
			}
		} else {
			// check if we have already an tag with the same name
			category.setName((String) tmp.get(i));
			category.setParent(parent);
			existCat = this.getCategoryByFullName(category.getFullName());
			if (existCat != null) {
				logger.debug("id: " + existCat.getId() + "-" + category.getId() + " with the same name: " + category.getFullName());
			} else {
				if (category.getId() == category.NO_ID) {
					category.setId(this.getMaxID() + 1);
				}
				this.addCategory(category);
			}
		}

	}

	public void removeCategory(Category category) {
		Category parent = category.getParent();
		if (parent != null) {
			parent.getChildren().remove(category);
		}
		this.getCategories().remove(category);
	}

	public CategoryList(int id, String name) {
		Category tmp = this.addChildren();
		tmp.setName(name);
		tmp.setId(id);
	}

	private Category addChildren() {
		Category tmp = new Category();
		this.categories.add(tmp);
		return tmp;
	}

	public void processNode(Node categories, Category parent) {
		// System.out.println(categories.toString());
		NodeList children = categories.getChildNodes();
		Category newParent = parent;
		newParent = null;
		// children = children.item(0).getChildNodes();
		if (children != null) {
			int len = children.getLength();
			// System.out.println("childs len: " + len);
			Node tmp;
			newParent = this.addChildren();
			newParent.setParent(parent);
			for (int i = 0; i < len; i++) {
				tmp = children.item(i);
				if (tmp.getNodeType() == Node.ELEMENT_NODE) {
					// System.out.println(tmp.getNodeName() + "-");
					if (tmp.getNodeName().equalsIgnoreCase("Name")) {
						// System.out.println(tmp.getNodeName() + "-" +
						// tmp.getChildNodes().item(0).getNodeValue());
						newParent.setName(tmp.getChildNodes().item(0).getNodeValue());
					} else if (tmp.getNodeName().equalsIgnoreCase("Position")) {
						try {
							Integer tmpInt = new Integer(tmp.getChildNodes().item(0).getNodeValue());
							newParent.setPosition(tmpInt.intValue());
						} catch (Exception e) {
						}
					} else if (tmp.getNodeName().equalsIgnoreCase("ID")) {
						try {
							Integer tmpInt = new Integer(tmp.getChildNodes().item(0).getNodeValue());
							newParent.setId(tmpInt.intValue());
						} catch (Exception e) {
						}
					} else if (tmp.getNodeName().equalsIgnoreCase("CategoryList")) {
						Node tmp1;
						int len1 = tmp.getChildNodes().getLength();
						CategoryList pcTmp;
						for (int j = 0; j < len1; j++) {
							tmp1 = tmp.getChildNodes().item(j);
							/*
							 * System.out.println("<--" + tmp1.getNodeName() +
							 * "-->");
							 */
							if (tmp1.getNodeName() != null && tmp1.getNodeName().equalsIgnoreCase("Category")) {
								// System.out.println(tmp1.toString());
								this.processNode(tmp1, newParent);
							}
						}
					}
				}
			}
		}
	}

	public void processNodeNew(Node categories) {
		// System.out.println(categories.getNodeName());
		if (categories.getNodeType() == Node.ELEMENT_NODE) {
			if (categories.getNodeName().equalsIgnoreCase(CategoryList.TAG_HEAD)) {
				Node tmp1;
				int len1 = categories.getChildNodes().getLength();
				Category newCategory;

				for (int j = 0; j < len1; j++) {
					tmp1 = categories.getChildNodes().item(j);
					if (tmp1.getNodeName() != null && tmp1.getNodeName().equalsIgnoreCase(Category.TAG_HEAD)) {
						newCategory = new Category();
						newCategory.processNode(tmp1, null, this);
						this.addCategory(newCategory);

					}
				}
			}
		}
	}

	/*
	 * public void show(String start) { System.out.println(start +
	 * this.getName()); start = start + "\t"; Vector children =
	 * this.getChildren(); Enumeration en = children.elements(); while
	 * (en.hasMoreElements()) { ((PictureCategories)
	 * en.nextElement()).show(start); } }
	 */
	public String save(String indent, Category parent) {
		// int count = this.getChildren().size();
		Category tmp;
		String rez = "";
		String strtmp;
		Vector cat = this.getCategories();
		boolean found = false;
		if (parent == null) {
			rez = rez + indent + "<Categories>" + getMaxID() + "</Categories>\r\n";
		}
		for (int i = 0; i < cat.size(); i++) {
			tmp = (Category) cat.get(i);
			if (tmp.getParent() == parent) {
				rez = rez + indent + "<Category>\r\n";
				rez = rez + indent + "\t<ID>" + tmp.getId() + "</ID>\r\n";
				rez = rez + indent + "\t<Name>" + tmp.getName() + "</Name>\r\n";
				rez = rez + indent + "\t<Position>" + tmp.getPosition() + "</Position>\r\n";
				strtmp = save(indent + "\t\t", tmp);
				if (!strtmp.equals("")) {
					rez = rez + indent + "\t<CategoryList>\r\n" + strtmp + indent + "\t</CategoryList>\r\n";
				}
				rez = rez + indent + "</Category>\r\n";
			}
		}
		/*
		 * if (count == 0) { rez = indent + "<Category><Name>" + this.getName()
		 * + "</Name></Category>\n"; } else { rez = indent + "<Category>\n"; rez
		 * = rez + indent + "\t<Name>" + this.getName() + "</Name>\n"; Hashtable
		 * children = this.getChildren(); Enumeration en = children.elements();
		 * boolean existCh = false; while (en.hasMoreElements()) { if (!existCh)
		 * { existCh = true; rez = rez + indent + "\t<CategoryList>\n"; } rez =
		 * rez + ((PictureCategories) en.nextElement()).save(indent + "\t\t"); }
		 * if (existCh) { existCh = true; rez = rez + indent +
		 * "\t</CategoryList>\n"; } rez = rez + indent + "</Category>\n"; }
		 */
		return rez;
	}

	/*
	 * public void show(String start) { System.out.println(start +
	 * this.getName()); start = start + "\t"; Vector children =
	 * this.getChildren(); Enumeration en = children.elements(); while
	 * (en.hasMoreElements()) { ((PictureCategories)
	 * en.nextElement()).show(start); } }
	 */
	public int getMaxID() {
		int max = 0;
		Category tmp;
		// int count = this.getChildren().size();
		Vector cat = this.getCategories();
		for (int i = 0; i < cat.size(); i++) {
			tmp = (Category) cat.get(i);
			if (max < tmp.getId()) {
				max = tmp.getId();
			}
		}
		return max;
	}

	public String saveACDSee(Category parent) {
		Category tmp;
		String rez = "";
		String strtmp;
		Vector cat = this.getCategories();
		boolean found = false;
		for (int i = 0; i < cat.size(); i++) {
			tmp = (Category) cat.get(i);
			if (tmp.getParent() == parent) {
				rez = rez + "<Category>\r\n";
				rez = rez + "<Name>" + tmp.getName() + "</Name>\r\n";
				rez = rez + "<Attributes>0</Attributes>\r\n";
				strtmp = saveACDSee(tmp);
				if (!strtmp.equals("")) {
					rez = rez + "<CategoryList>\r\n" + strtmp + "</CategoryList>\r\n";
				}
				rez = rez + "</Category>";
			}
		}
		/*
		 * if (count == 0) { rez = "<Category>\r\n<Name>" + this.getName() +
		 * "</Name>\r\n<Attributes>0</Attributes>\r\n</Category>"; } else { rez
		 * = "<Category>\r\n"; rez = rez + "<Name>" + this.getName() +
		 * "</Name>\r\n"; rez = rez + "<Attributes>0</Attributes>\r\n";
		 * Hashtable children = this.getChildren(); Enumeration en =
		 * children.elements(); boolean existCh = false; while
		 * (en.hasMoreElements()) { if (!existCh) { existCh = true; rez = rez +
		 * "<CategoryList>\r\n"; } rez = rez + ((PictureCategories)
		 * en.nextElement()).saveACDSee(); } if (existCh) { existCh = true; rez
		 * = rez + "</CategoryList>\r\n"; } rez = rez + "</Category>"; }
		 */
		return rez;
	}

	public Vector<Category> getCategories() {
		return this.categories;
	}

	public int getNrOfcat() {
		return this.categories.size();
	}

	public Category getByFullName(String fullName) {
		Category tmp;
		for (int i = 0; i < this.categories.size(); i++) {
			tmp = (Category) this.categories.get(i);
			if (tmp.getFullName().equals(fullName)) {
				return tmp;
			}
		}
		return null;
	}

	public void changePosition(Category current, int newPos) {
		if (newPos < 1) {
			newPos = 1;
		}
		if (newPos > categories.size()) {
			newPos = categories.size();
		}
		TreeSet ts = new TreeSet(categories);
		java.util.Iterator it = ts.iterator();

		int i = 0;
		Category tmp;
		if (newPos >= current.getPosition()) {
			while (it.hasNext()) {
				tmp = (Category) it.next();
				if (tmp.getPosition() <= newPos && tmp != current && tmp.getPosition() > current.getPosition()) {
					tmp.setPosition(tmp.getPosition() - 1);
				}
				i++;
			}
		} else {
			while (it.hasNext()) {
				tmp = (Category) it.next();
				if (tmp.getPosition() >= newPos && tmp != current && tmp.getPosition() < current.getPosition()) {
					tmp.setPosition(tmp.getPosition() + 1);
				}
				i++;
			}
		}
		current.setPosition(newPos);
	}

	public String toXML(String indent) {
		Category tmp;
		String rez = "";
		Vector<Category> add = this.getCategories();
		rez = indent + "<" + CategoryList.TAG_HEAD + ">\r\n";
		// System.out.println("cat count: " + add.size());
		for (int i = 0; i < add.size(); i++) {
			tmp = add.get(i);
			if (tmp.getParent() == null && tmp.getName().equals("Adresses") == false) {
				rez = rez + tmp.toXML(indent + "\t");
			}
		}
		rez = rez + indent + "</" + CategoryList.TAG_HEAD + ">\r\n";
		return rez;
	}

	/*
	 * public ElementImpl toXML2(DocumentImpl doc) { ElementImpl node = new
	 * ElementImpl(doc, AddressList.TAG_HEAD); Vector<Category> add =
	 * this.getCategories(); Category tmp; for (int i = 0; i < add.size(); i++)
	 * { tmp = add.get(i); if (tmp.getParent() == null &&
	 * tmp.getName().equals("Adresses") == false) {
	 * node.appendChild(tmp.toXML2(doc)); } } return node; }
	 */
}
