package ro.cuzma.picturemgt.categories;

import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.NDC;
import ro.cuzma.picturemgt.addresses.Address;

public class Category implements Comparable {
	private int					position;

	private String				name;
	private Category			parent;
	public final static String	TAG_HEAD		= "Category";
	public final static String	TAG_LIST		= "CategoryList";
	public final static String	TAG_ID			= "id";
	public final static String	TAG_NAME		= "name";
	public final static String	TAG_POSITION	= "position";
	public final static String	TAG_KEYWORD		= "isKeyword";
	public final static int		IS_KEYWORD		= 1;
	public final static int		IS_NOT_KEYWORD	= 0;
	private Vector<Category>	childrenCat		= new Vector<Category>();
	public final static int		NO_ID			= -1;
	private int					id				= Address.NO_ID;
	private int					isKeyword		= Category.IS_KEYWORD;
	public static String		SEPARATOR		= "\\";
	
	static Logger logger = Logger.getLogger(Category.class);

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Category(int id, String name, int position, Category parent, int isKeyword) {
		this.name = name;
		this.position = position;
		this.parent = parent;
		this.id = id;
		this.id = isKeyword;
	}

	public Category() {
	}

	public int compareTo(Object arg0) {
		Category tmp = (Category) arg0;
		if (this.position - tmp.position == 0)
			this.position++;
		return this.position - tmp.position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getFullName() {
		String rez;
		if (this.getParent() != null) {
			return this.getParent().getFullName() + Category.SEPARATOR + this.getName();
		} else {
			return this.getName();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void processNode(Node categories, Category parent, CategoryList cat) {
		// System.out.println(categories.toString());
		NodeList children = categories.getChildNodes();
		this.setParent(parent);
		// children = children.item(0).getChildNodes();
		if (children != null) {
			int len = children.getLength();
			// System.out.println("childs len: " + len);
			Node tmp;
			for (int i = 0; i < len; i++) {
				tmp = children.item(i);
				if (tmp.getNodeType() == Node.ELEMENT_NODE) {
					// System.out.println(tmp.getNodeName() + "-");
					if (tmp.getNodeName().equalsIgnoreCase(Category.TAG_NAME)) {
						// System.out.println(tmp.getNodeName() + "-" +
						// tmp.getChildNodes().item(0).getNodeValue());
						this.setName(tmp.getChildNodes().item(0).getNodeValue());
					} else if (tmp.getNodeName().equalsIgnoreCase(Category.TAG_POSITION)) {
						try {
							Integer tmpInt = new Integer(tmp.getChildNodes().item(0).getNodeValue());
							this.setPosition(tmpInt.intValue());
						} catch (Exception e) {
						}
					} else if (tmp.getNodeName().equalsIgnoreCase(Category.TAG_KEYWORD)) {
						try {
							Integer tmpInt = new Integer(tmp.getChildNodes().item(0).getNodeValue());
							this.setIsKeyword(tmpInt.intValue());
						} catch (Exception e) {
						}
					} else if (tmp.getNodeName().equalsIgnoreCase(Category.TAG_ID)) {
						try {
							Integer tmpInt = new Integer(tmp.getChildNodes().item(0).getNodeValue());
							this.setId(tmpInt.intValue());
						} catch (Exception e) {
						}
					} else if (tmp.getNodeName().equalsIgnoreCase(Category.TAG_LIST)) {
						Node tmp1;
						int len1 = tmp.getChildNodes().getLength();
						Category newCategory;
						for (int j = 0; j < len1; j++) {
							tmp1 = tmp.getChildNodes().item(j);
							/*
							 * System.out.println("<--" + tmp1.getNodeName() +
							 * "-->");
							 */
							if (tmp1.getNodeName() != null && tmp1.getNodeName().equalsIgnoreCase(Category.TAG_HEAD)) {
								newCategory = new Category();
								this.addChildrenCategory(newCategory);
								newCategory.processNode(tmp1, this, cat);

								cat.addCategory(newCategory);

							}
						}
					}
				}
			}
		}
	}

	public void addChildrenCategory(Category newCategory) {
		this.childrenCat.add(newCategory);
	}

	public Vector<Category> getChildren() {
		return this.childrenCat;
	}

	public String toXML(String indent) {
		logger.debug("Save: " +  this.getFullName());
		String rez = "";
		rez = indent + "<" + Category.TAG_HEAD + ">\r\n";
		rez = rez + indent + "\t<" + Category.TAG_ID + ">" + this.getId() + "</" + Category.TAG_ID + ">\r\n";
		rez = rez + indent + "\t<" + Category.TAG_POSITION + ">" + this.getPosition() + "</" + Category.TAG_POSITION + ">\r\n";
		rez = rez + indent + "\t<" + Category.TAG_NAME + ">" + this.getName() + "</" + Category.TAG_NAME + ">\r\n";
		rez = rez + indent + "\t<" + Category.TAG_KEYWORD + ">" + this.getIsKeyword() + "</" + Category.TAG_KEYWORD + ">\r\n";
		int childredSize = this.getChildren().size();
		if (childredSize > 0) {
			rez = rez + indent + "\t<" + Category.TAG_LIST + ">" + "\r\n";
			Category child = null;
			for (int i = 0; i < childredSize; i++) {
				child = this.getChildren().get(i);
				rez = rez + child.toXML(indent + "\t\t");
			}
			rez = rez + indent + "\t</" + Category.TAG_LIST + ">" + "\r\n";
		}
		rez = rez + indent + "</" + Category.TAG_HEAD + ">\r\n";
		return rez;
	}

	/*public ElementImpl toXML2(DocumentImpl doc) {
		ElementImpl node = new ElementImpl(doc, Category.TAG_HEAD);
		ElementImpl tmp = null;
		tmp = new ElementImpl(doc, Category.TAG_ID);
		tmp.appendChild(new TextImpl(doc, "" + this.getId()));
		node.appendChild(tmp);
		tmp = new ElementImpl(doc, Category.TAG_POSITION);
		tmp.appendChild(new TextImpl(doc, "" + this.getPosition()));
		node.appendChild(tmp);
		tmp = new ElementImpl(doc, Category.TAG_NAME);
		tmp.appendChild(new TextImpl(doc, this.getName()));
		node.appendChild(tmp);
		int childredSize = this.getChildren().size();
		if (childredSize > 0) {
			tmp = new ElementImpl(doc, Category.TAG_LIST);
			Category child = null;
			for (int i = 0; i < childredSize; i++) {
				tmp.appendChild(this.getChildren().get(i).toXML2(doc));
			}
			node.appendChild(tmp);
		}
		return node;
	}*/

	public int getIsKeyword() {
		return isKeyword;
	}

	public void setIsKeyword(int isKeyword) {
		this.isKeyword = isKeyword;
	}
}
