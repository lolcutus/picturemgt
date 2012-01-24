/*
 * Created on 01.03.2006
 * by Laurian Cuzma
 */
package ro.cuzma.picturemgt;

import java.util.Vector;

public class PictureFolder {
	private String pathFromRoot;
	private Vector folderPictures = new Vector();
	
	public PictureFolder(String pathFromRoot){
		this.pathFromRoot = pathFromRoot;
	}
	
	public void addPicture(Picture pic){
		this.folderPictures.add(pic);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	// TODO Auto-generated method stub

	}

}
