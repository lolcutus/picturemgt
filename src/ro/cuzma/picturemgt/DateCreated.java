/*
 * Created on 17.03.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ro.cuzma.picturemgt;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cuzma
 * 
 *         TODO To change the template for this generated type comment go to Window - Preferences -
 *         Java - Code Style - Code Templates
 */
public class DateCreated implements Comparable<DateCreated> {

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    private Date creationDate;
    private File file;
    private String newName;
    private String dateAsString;

    public DateCreated(File file, String dateCreated) {
        dateAsString = dateCreated;
        this.setCreationDate(dateCreated);
        this.setFile(file);
    }

    public int compareTo(DateCreated arg0) {
        // TODO Auto-generated method stub
        if (arg0.getCreationDate() == null || this.getCreationDate() == null) {
            return 1;
        }
        int test = this.creationDate.compareTo(((DateCreated) arg0).getCreationDate());
        if (test == 0) {
            return this.file.getName().compareTo(arg0.getFile().getName());
        }
        return test;
    }

    /**
     * @return Returns the creationDate.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    public String toString() {
        return dateAsString;
    }

    /**
     * @param creationDate
     *            The creationDate to set.
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = stringToDate(creationDate);
    }

    /**
     * @return Returns the file.
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file
     *            The file to set.
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
     * @param newName
     *            The newName to set.
     */
    public void setNewName(String newName) {
        this.newName = newName;
    }

    private Date stringToDate(String dateInput) {
        Date date = null;
        try {
            DateFormat formatter;

            formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            date = (Date) formatter.parse(dateInput);
            System.out.println("Today is " + date);
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
        }
        return date;
    }
}
