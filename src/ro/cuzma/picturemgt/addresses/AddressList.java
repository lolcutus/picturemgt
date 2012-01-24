package ro.cuzma.picturemgt.addresses;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.TreeSet;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class AddressList {
	private Vector<Address>		addresses	= new Vector<Address>();
	private static int			nrOfcat		= 0;
	public final static String	TAG_HEAD	= "AddressesList";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String toParse = "<AddressesList>" + "<Address>" + "<ID>2</ID>" + "<Position>3</Position>" + "<address/>"
				+ "<city>Timisoara</city>" + "<state>Timis</state>" + "<country>Romania</country>" + "</Address>" + "</AddressesList>";
		ByteArrayInputStream is = new ByteArrayInputStream(toParse.getBytes());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		AddressList adl = null;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			adl = new AddressList();
			adl.processNode(document);
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

		byte[] rez = null;
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			// initialize StreamResult with File object to save to file
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			Result result = new StreamResult(ba);
			/*DocumentImpl doc = new DocumentImpl();
			DOMSource source = new DOMSource(adl.toXML2(doc));
			transformer.transform(source, result);
			System.out.println(ba.toString());*/

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	/*
	 * public Pictureaddresses(Node node) { processNode(node, null); }
	 */
	public AddressList() {
		// processNode(node, null);
	}

	public Address getAddressById(int id) {
		Address tmp = null;
		for (int i = 0; i < addresses.size(); i++) {
			tmp = (Address) addresses.get(i);
			if (tmp.getId() == id)
				return tmp;
		}
		return null;
	}

	/*
	 * private Address addChildren() { Address tmp = new Address();
	 * this.addresses.add(tmp); return tmp; }
	 */
	public void addAddress(Address address) {
		// if id is null then need to generate new one
		if (address.getId() == Address.NO_ID) {
			int newId = this.getMaxID() + 1;
			address.setId(newId);
			System.out.println("Adaug nou: " + newId);
		}
		// need to check is an existing ID in that case we need to update
		Address existAdd = this.getAddressById(address.getId());
		if (existAdd == null) {
			this.addresses.add(address);
		} else {
			existAdd.setAddress(address.getAddress());
			existAdd.setCity(address.getCity());
			existAdd.setState(address.getState());
			existAdd.setCountry(address.getCountry());
		}
	}

	public void processNode(Node addresses) {
		// System.out.println(addresses.toString());
		NodeList children = addresses.getChildNodes();
		Address address = null;
		// children = children.item(0).getChildNodes();
		if (children != null) {
			int len = children.getLength();
			Node tmp;
			// address = this.addChildren();
			for (int i = 0; i < len; i++) {
				tmp = children.item(i);
				if (tmp.getNodeType() == Node.ELEMENT_NODE) {
					if (tmp.getNodeName().equalsIgnoreCase(AddressList.TAG_HEAD)) {
						Node tmp1;
						int len1 = tmp.getChildNodes().getLength();
						Address add = null;
						for (int j = 0; j < len1; j++) {
							tmp1 = tmp.getChildNodes().item(j);
							if (tmp1.getNodeName() != null && tmp1.getNodeName().equalsIgnoreCase(Address.TAG_HEAD)) {
								add = new Address();
								add.processNode(tmp1);
								this.addAddress(add);
							}
						}
					}
				}
			}
		}
	}

	public void processNodeNew(Node addresses) {
		// System.out.println(categories.getNodeName());
		if (addresses.getNodeType() == Node.ELEMENT_NODE) {
			if (addresses.getNodeName().equalsIgnoreCase(AddressList.TAG_HEAD)) {
				int len1 = addresses.getChildNodes().getLength();
				Address newAddress;
				Node n = null;
				for (int j = 0; j < len1; j++) {
					n = addresses.getChildNodes().item(j);
					newAddress = Address.processNode(n);
					if (newAddress != null) {
						this.addAddress(newAddress);
					}
				}
			}
		}
	}

	public String toXML(String indent) {
		Address tmp;
		String rez = "";
		Vector<Address> add = this.getaddresses();
		rez = indent + "<" + AddressList.TAG_HEAD + ">\r\n";
		// System.out.println("addresses count: " + add.size());
		for (int i = 0; i < add.size(); i++) {
			// System.out.println("addresses write: " + i);
			tmp = add.get(i);
			rez = rez + tmp.toXML(indent + "\t");
		}
		rez = rez + indent + "</" + AddressList.TAG_HEAD + ">\r\n";
		return rez;
	}

	public int getMaxID() {
		int max = 0;
		Address tmp;
		// int count = this.getChildren().size();
		Vector cat = this.getaddresses();
		for (int i = 0; i < cat.size(); i++) {
			tmp = (Address) cat.get(i);
			if (max < tmp.getId()) {
				max = tmp.getId();
			}
		}
		return max;
	}

	public String saveACDSee(Address parent) {
		Address tmp;
		String rez = "";
		String strtmp;
		Vector cat = this.getaddresses();
		boolean found = false;
		/*
		 * for (int i = 0; i < cat.size(); i++) { tmp = (Address) cat.get(i); if
		 * (tmp.getParent() == parent) { rez = rez + "<Address>\r\n"; rez = rez
		 * + "<Name>" + tmp.getName() + "</Name>\r\n"; rez = rez +
		 * "<Attributes>0</Attributes>\r\n"; strtmp = saveACDSee(tmp); if
		 * (!strtmp.equals("")) { rez = rez + "<AddressList>\r\n" + strtmp +
		 * "</AddressList>\r\n"; } rez = rez + "</Address>"; } }
		 */
		/*
		 * if (count == 0) { rez = "<Address>\r\n<Name>" + this.getName() +
		 * "</Name>\r\n<Attributes>0</Attributes>\r\n</Address>"; } else { rez =
		 * "<Address>\r\n"; rez = rez + "<Name>" + this.getName() +
		 * "</Name>\r\n"; rez = rez + "<Attributes>0</Attributes>\r\n";
		 * Hashtable children = this.getChildren(); Enumeration en =
		 * children.elements(); boolean existCh = false; while
		 * (en.hasMoreElements()) { if (!existCh) { existCh = true; rez = rez +
		 * "<AddressList>\r\n"; } rez = rez + ((Pictureaddresses)
		 * en.nextElement()).saveACDSee(); } if (existCh) { existCh = true; rez
		 * = rez + "</AddressList>\r\n"; } rez = rez + "</Address>"; }
		 */
		return rez;
	}

	public Vector<Address> getaddresses() {
		return this.addresses;
	}

	public int getNrOfcat() {
		return this.addresses.size();
	}

	public Address getByFullName(String fullName) {
		Address tmp;
		for (int i = 0; i < this.addresses.size(); i++) {
			tmp = (Address) this.addresses.get(i);
			if (tmp.getName().equals(fullName)) {
				return tmp;
			}
		}
		return null;
	}

	public void changePosition(Address current, int newPos) {
		if (newPos < 1) {
			newPos = 1;
		}
		if (newPos > addresses.size()) {
			newPos = addresses.size();
		}
		TreeSet ts = new TreeSet(addresses);
		java.util.Iterator it = ts.iterator();
		System.out.println(current.getPosition());
		int i = 0;
		Address tmp;
		while (it.hasNext()) {
			tmp = (Address) it.next();
			if (tmp.getPosition() >= newPos && tmp != current && tmp.getPosition() < current.getPosition()) {
				tmp.setPosition(tmp.getPosition() + 1);
			}
			i++;
		}
		current.setPosition(newPos);
	}

	/*public ElementImpl toXML2(DocumentImpl doc) {
		ElementImpl node = new ElementImpl(doc, AddressList.TAG_HEAD);
		Vector<Address> add = this.getaddresses();
		for (int i = 0; i < add.size(); i++) {
			node.appendChild(add.get(i).toXML2(doc));
		}
		return node;
	}*/

}
