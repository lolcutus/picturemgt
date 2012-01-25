package ro.cuzma.picturemgt.ui;

/*
 * Picture.java is used by the 1.4 TrackFocusDemo.java and DragPictureDemo.java
 * examples.
 */
//	 import javax.swing.InputMap;
//	 import javax.swing.KeyStroke;
//	 import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import javax.accessibility.Accessible;

import ro.cuzma.picturemgt.Picture;
import ro.cuzma.picturemgt.PictureDatabase;
import ro.cuzma.picturemgt.ThreadLoadPitureLabel;
import ro.cuzma.picturemgt.addresses.Address;
import ro.cuzma.picturemgt.addresses.AddressList;
import ro.cuzma.picturemgt.addresses.AddressModel;
import ro.cuzma.picturemgt.categories.Category;
import ro.cuzma.picturemgt.categories.CategoryList;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

public class PictureLabel extends JLabel implements MouseListener, FocusListener, Accessible, ImageObserver {

	private BufferedImage		image;
	private Picture				pic;
	private PictureManagement	parent;
	private PictureDatabase		picDB;

	/**
	 * @return Returns the pic.
	 */
	public Picture getPic() {
		return this.pic;
	}

	public PictureLabel(Picture picture, PictureManagement parent, PictureDatabase picDB) {
		this.pic = picture;
		this.parent = parent;
		this.picDB = picDB;
		setVerticalTextPosition(BOTTOM);
		setHorizontalTextPosition(CENTER);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
		this.setBorder(new LineBorder(Color.BLACK));
		this.setBackground(Color.DARK_GRAY);
		
		this.setPreferredSize(new Dimension(pic.getSizeThumbs(),pic.getSizeThumbs()));
		this.setSize(new Dimension(pic.getSizeThumbs(),pic.getSizeThumbs()));
		setFocusable(true);
		addMouseListener(this);
		addFocusListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		// Since the user clicked on us, let's get focus!
		requestFocusInWindow();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void focusGained(FocusEvent e) {
		// Draw the component with BookTableHeaderListener red border
		// indicating that it has focus.
		// this.setLocation(0,0);
		if (this.parent.getCurPiclabel() != null) {
			this.parent.getCurPiclabel().focusLost();
		}
		this.parent.setCurPiclabel(this);
		this.parent.getTextArea().setText("");
		this.parent.getTextArea().append(pic.getFileFullName() + "\n");
		this.parent.setCurrentPicture(pic);

		// Set CAtegories
		// ===================================================
		Vector categ = pic.getKeywords();
		int rowCountC = this.parent.getCategoryModel().getRowCount();
		for (int j = 0; j < rowCountC; j++) {
			this.parent.getCategoryModel().setValueAt(new Boolean(false), j, 1);
		}
		String categoryT;
		String existingCat;
		for (int i = 0; i < categ.size(); i++) {
			existingCat = ((Category) categ.get(i)).getFullName();
			this.parent.getTextArea().append(existingCat + ";\n");
			for (int j = 0; j < rowCountC; j++) {
				categoryT = (String) this.parent.getCategoryModel().getValueAt(j, 0);
				if (categoryT.equals(existingCat)) {
					this.parent.getCategoryModel().setValueAt(new Boolean(true), j, 1);
				}
			}
		}
		for (int j = 0; j < rowCountC; j++) {
			categoryT = (String) this.parent.getCategoryModel().getValueAt(j, 0);
			if (((Boolean) this.parent.getCategoryModel().getValueAt(j, 2)).booleanValue()) {
				this.parent.getCategoryModel().setValueAt(new Boolean(true), j, 1);
				pic.addKeyword(this.picDB.getCategories().getByFullName(categoryT));
			}
		}
		// ===================================================

		// Set Addresses
		// ===================================================
		Address addr = pic.getAddress();
		int rowCountA = this.parent.getAddressModel().getRowCount();
		for (int j = 0; j < rowCountA; j++) {
			this.parent.getAddressModel().setValueAt(new Boolean(false), j, 1);
		}
		Integer curID;
		Integer existingID;
		int colID = this.parent.getAddressModel().getColNr(AddressModel.COL_ID);
		if (addr != null) {
			existingID = new Integer(addr.getId());
			this.parent.getTextArea().append("add ID: " + existingID);
			for (int j = 0; j < rowCountA; j++) {
				curID = (Integer)this.parent.getAddressModel().getValueAt(j, colID);
				if (curID.equals(existingID)) {
					this.parent.getAddressModel().setValueAt(new Boolean(true), j, 1);
				}
			}
		}
		// }
		int colGlobal = this.parent.getAddressModel().getColNr(AddressModel.COL_GLOBAL);
		int colSet = this.parent.getAddressModel().getColNr(AddressModel.COL_SET);
		for (int j = 0; j < rowCountA; j++) {
			curID = (Integer)this.parent.getAddressModel().getValueAt(j, colID);

			if (((Boolean) this.parent.getAddressModel().getValueAt(j, colGlobal)).booleanValue()) {
				this.parent.getTextArea().append("ID: " + curID + "\n");
				this.parent.getAddressModel().setValueAt(new Boolean(true), j, colSet);
				AddressList add = this.picDB.getAddresses();
				Address addcurent = add.getAddressById(new Integer(curID).intValue());
				pic.setAddress(addcurent);
			}
		}
		// ===================================================

		// SET RATING
		// ===================================================
		int tmp1 = pic.getRating() - 1;
		if (tmp1 == -1) {
			tmp1 = 5;
		}
		String label = "" + pic.getRating();
		if (pic.getRating() == 0)
			label = "None";
		// this.parent.getButtonGroupRating().setSelected(this.parent.getButtonGroupRating().getElements().);
		Enumeration en = this.parent.getButtonGroupRating().getElements();
		JRadioButton jR;
		for (int i = 0; i <= tmp1; i++) {
			jR = (JRadioButton) en.nextElement();
			if (jR.getText().equals(label)) {
				jR.setSelected(true);
			}
		}
		this.parent.getTextArea().append("Rating: " + pic.getRating());
		// ======================================================

		this.setBorder(new LineBorder(Color.BLUE, 3));
		// this.setB
		this.setBackground(Color.BLUE);
		this.setForeground(Color.BLUE);
		// this.parent.getBigPiclabel().removeAll();
		ThreadLoadPitureLabel th = new ThreadLoadPitureLabel("cucu", this.parent.getpreviewLabel(), this);
		th.run();
		// JLabel tmpL = new JLabel();

		// this.parent.getBigPiclabel().add(tmpL);
		// ImageIcon pictureIcon = new ImageIcon();
		// pictureIcon.setImage(image);
		// tmpL.setIcon(pictureIcon);
		// this.parent.getBigPiclabel().repaint();
		// this.set
		this.repaint();
	}

	public void focusLost(FocusEvent e) {
		// Draw the component with BookTableHeaderListener black border
		// indicating that it doesn't have focus.
		// this.setBorder(new LineBorder(Color.BLACK));
		// this.setBackground(Color.DARK_GRAY);
		// this.repaint();
		// Enumeration en = this.parent.getButtonGroupRating().getElements();
		/*
		 * JRadioButton jR; String tmp; while(en.hasMoreElements()) { jR =
		 * (JRadioButton)en.nextElement(); // if (jR.isSelected()){
		 * System.out.println(jR.getText()); tmp = jR.getText(); if
		 * (tmp.equals("None")){ tmp = "0"; } pic.setRating((new
		 * Integer(tmp)).intValue()); } }
		 */

	}

	public void focusLost() {
		this.setBorder(new LineBorder(Color.BLACK));
		this.setBackground(Color.DARK_GRAY);
		this.repaint();
	}

	/*
	 * protected void paintComponent(Graphics graphics) { //this.setSize(image
	 * == null ? 125 : image.getWidth(this), image == null ? 125 :
	 * image.getHeight(this)); Graphics g = graphics.create(); // Draw in our
	 * entire space, even if isOpaque is false. g.setColor(Color.WHITE);
	 * g.fillRect(0, 0, image == null ? 125 : image.getWidth(this), image ==
	 * null ? 125 : image.getHeight(this));
	 * 
	 * if (image != null) { // Draw image at its natural size of 125x125.
	 * System.out.println(image.getHeight(this) + "-" + image.getWidth(this));
	 * 
	 * g.drawImage(image, 0, 0, this); } // Add BookTableHeaderListener border,
	 * red if picture currently has focus if (isFocusOwner()) {
	 * g.setColor(Color.RED); } else { g.setColor(Color.BLACK); } g.drawRect(0,
	 * 0, image == null ? 125 : image.getWidth(this), image == null ? 125 :
	 * image.getHeight(this));
	 * //this.resize(image.getWidth(this),image.getHeight(this)); g.dispose(); }
	 */
	public void loadPicture() {
		ThreadLoadPitureLabel th = new ThreadLoadPitureLabel("cucu", this);
		th.start();
	}

	public void setImage(BufferedImage image) {
		// System.out.println("Load: " + this.pic.getName());
		this.image = image;
	}
}
