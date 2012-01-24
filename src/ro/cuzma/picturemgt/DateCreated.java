/*
 * Created on 17.03.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ro.cuzma.picturemgt;

import java.io.File;

/**
 * @author cuzma
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DateCreated implements Comparable {

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	private String creationDate;
	private File	file;
	private String	newName;
	
	public DateCreated(File file, String dateCreated){
		this.setCreationDate(dateCreated);
		this.setFile(file);
	}
	
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		if (((DateCreated)arg0).getCreationDate() == null || this.getCreationDate() == null){
			return 1;
		}
		int test = this.creationDate.compareTo(((DateCreated)arg0).getCreationDate());
		if (test == 0){
			this.setCreationDate(this.getCreationDate()+"1");
			return 1;
		}else{
			return test;
		}
		//return this.creationDate.compareTo(((DateCreated)arg0).getCreationDate());
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate The creationDate to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return Returns the file.
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file The file to set.
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return Returns the newName.
	 */
	public String getNewName() {
		return newName;
	}

	/**
	 * @param newName The newName to set.
	 */
	public void setNewName(String newName) {
		this.newName = newName;
	}
}
