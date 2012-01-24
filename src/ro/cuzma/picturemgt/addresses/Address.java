package ro.cuzma.picturemgt.addresses;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Address implements Comparable {
    private int                position;

    private String             address      = "";
    private String             city         = "";
    private String             state        = "";
    private String             country      = "";
    public final static String TAG_ID       = "id";
    public final static String TAG_POSITION = "position";
    public final static String TAG_HEAD     = "Address";
    public final static String TAG_ADDRESS  = "address";
    public final static String TAG_CITY     = "city";
    public final static String TAG_STATE    = "state";
    public final static String TAG_COUNTRY  = "country";
    public final static int    NO_ID        = -1;
    private int                id           = Address.NO_ID;

    public Address(int id, String address, String city, String state, String country, int position) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.position = position;
        this.id = id;

    }

    public Address() {
    }

    public int compareTo(Object arg0) {
        Address tmp = (Address) arg0;
        if (this.position - tmp.position == 0)
            this.position++;
        return this.position - tmp.position;
    }

    public String getName() {
        return address + ": " + city + ", " + state + ", " + country;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != null) {
            this.address = address;
        } else {
            this.address = "";
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city != null) {
            this.city = city;
        } else {
            this.city = "";
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if (state != null) {
            this.state = state;
        } else {
            this.state = "";
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country != null) {
            this.country = country;
        } else {
            this.country = "";
        }
    }

    public String toXML(String indent) {
        String rez = "";
        rez = indent + "<" + Address.TAG_HEAD + ">\r\n";
        rez = rez + indent + "\t<" + Address.TAG_ID + ">" + this.getId() + "</" + Address.TAG_ID
                + ">\r\n";
        rez = rez + indent + "\t<" + Address.TAG_POSITION + ">" + this.getPosition() + "</"
                + Address.TAG_POSITION + ">\r\n";
        rez = rez + indent + "\t<" + Address.TAG_ADDRESS + ">" + this.getAddress() + "</"
                + Address.TAG_ADDRESS + ">\r\n";
        rez = rez + indent + "\t<" + Address.TAG_CITY + ">" + this.getCity() + "</"
                + Address.TAG_CITY + ">\r\n";
        rez = rez + indent + "\t<" + Address.TAG_STATE + ">" + this.getState() + "</"
                + Address.TAG_STATE + ">\r\n";
        rez = rez + indent + "\t<" + Address.TAG_COUNTRY + ">" + this.getCountry() + "</"
                + Address.TAG_COUNTRY + ">\r\n";
        rez = rez + indent + "</" + Address.TAG_HEAD + ">\r\n";
        return rez;
    }

    /*
     * public ElementImpl toXML2(DocumentImpl doc) { ElementImpl node = new ElementImpl(doc,
     * Address.TAG_HEAD); ElementImpl tmp = null; tmp = new ElementImpl(doc, Address.TAG_ID);
     * tmp.appendChild(new TextImpl(doc, "" + this.getId())); node.appendChild(tmp); tmp = new
     * ElementImpl(doc, Address.TAG_POSITION); tmp.appendChild(new TextImpl(doc, "" +
     * this.getPosition())); node.appendChild(tmp); tmp = new ElementImpl(doc, Address.TAG_ADDRESS);
     * tmp.appendChild(new TextImpl(doc, this.getAddress())); node.appendChild(tmp); tmp = new
     * ElementImpl(doc, Address.TAG_CITY); tmp.appendChild(new TextImpl(doc, this.getCity()));
     * node.appendChild(tmp); tmp = new ElementImpl(doc, Address.TAG_STATE); tmp.appendChild(new
     * TextImpl(doc, this.getState())); node.appendChild(tmp); tmp = new ElementImpl(doc,
     * Address.TAG_COUNTRY); tmp.appendChild(new TextImpl(doc, this.getCountry()));
     * node.appendChild(tmp); // node.s return node; }
     */

    public static Address processNode(Node address) {
        Address add = null;
        NodeList children = address.getChildNodes();
        // children = children.item(0).getChildNodes();
        if (children != null) {
            int len = children.getLength();
            // System.out.println(len);
            Node tmp;
            for (int i = 0; i < len; i++) {
                if (add == null) {
                    add = new Address();
                }
                tmp = children.item(i);
                if (tmp.getNodeType() == Node.ELEMENT_NODE) {
                    if (tmp.getNodeName().equalsIgnoreCase(Address.TAG_ADDRESS)) {
                        if (tmp.getChildNodes().item(0) != null) {
                            add.setAddress(tmp.getChildNodes().item(0).getNodeValue());
                        }
                    } else if (tmp.getNodeName().equalsIgnoreCase(Address.TAG_CITY)) {
                        if (tmp.getChildNodes().item(0) != null) {
                            add.setCity(tmp.getChildNodes().item(0).getNodeValue());
                        }
                    } else if (tmp.getNodeName().equalsIgnoreCase(Address.TAG_STATE)) {
                        if (tmp.getChildNodes().item(0) != null) {
                            add.setState(tmp.getChildNodes().item(0).getNodeValue());
                        }
                    } else if (tmp.getNodeName().equalsIgnoreCase(Address.TAG_COUNTRY)) {
                        if (tmp.getChildNodes().item(0) != null) {
                            try {
                                add.setCountry(tmp.getChildNodes().item(0).getNodeValue());
                            } catch (Exception e) {
                            }
                        }
                    } else if (tmp.getNodeName().equalsIgnoreCase(Address.TAG_ID)) {
                        if (tmp.getChildNodes().item(0) != null) {
                            try {
                                Integer tmpInt = new Integer(tmp.getChildNodes().item(0)
                                        .getNodeValue());
                                add.setId(tmpInt.intValue());
                            } catch (Exception e) {
                            }
                        }

                    } else if (tmp.getNodeName().equalsIgnoreCase(Address.TAG_POSITION)) {
                        if (tmp.getChildNodes().item(0) != null) {
                            try {
                                Integer tmpInt = new Integer(tmp.getChildNodes().item(0)
                                        .getNodeValue());
                                add.setPosition(tmpInt.intValue());
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        }
        return add;
    }

    /*
     * public Address(Category cat) { System.out.println(cat.getName()); StringTokenizer st = new
     * StringTokenizer(cat.getName(), ","); this.setId(cat.getId());
     * this.setPosition(cat.getPosition()); this.setCity((String) st.nextElement());
     * this.setState((String) st.nextElement()); this.setCountry((String) st.nextElement());
     * CategoryList.addresses.addAddress(this); }
     */

}
