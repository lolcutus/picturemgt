/*
 * Created on 14.03.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ro.cuzma.picturemgt;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;

import com.drew.metadata.exif.ExifDirectory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeSet;
import java.util.Vector;


/**
 * @author cuzma
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PictureProperties {
	private Vector files = new Vector();
	private Vector dateCreated = new Vector();
	public static void main(String[] args) {
		PictureProperties pp =  new PictureProperties();
		try {
			Vector tst = pp.getFiles("F:\\Personal\\Picture\\_Current\\2007.04-02 Brasov");
			TreeSet ts = new TreeSet(tst);
			java.util.Iterator it = ts.iterator();
			DateCreated tmp = null;
			int contor = 1;
			while (it.hasNext()){
				tmp = (DateCreated)it.next();
				//System.out.println(tmp.getFile().getName() + "->" + tmp.getCreationDate()+ "->" + pp.newName(tmp.getFile().getName(),contor));
				pp.fixDeanIonut(tmp.getFile());
				contor++;
			}
			/*for (int i = 0;i< ts.size();i++){
				tmp = (DateCreated)ts.h .get(i);
				System.out.println(tmp.getFile().getName() + "->" + tmp.getCreationDate());
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getProperties(File fileName,int byteStart,int nrBytes){
		String picDate = "";
		byte[] test = new byte[nrBytes + 1];
		try {
			RandomAccessFile cuvinte = new RandomAccessFile(fileName, "r");
			String line = ""; 
			//System.out.println(fileName);
		    //int max = cuvinte.read(test,188,19);
			boolean cont = true;
			int i=0;
		    while(cont){
		    	try {
		    		byte b = cuvinte.readByte();
		    	//picDate = picDate +  b;
		    		//System.out.print((char)b);
		    	} catch (IOException e) {
					// TODO Auto-generated catch block
					cont = false;
				}
		    	i++;
		    	if (i>byteStart) cont = false;
		    }
		    //int max = cuvinte.read(test,188,19); 
		    for (int j = 0;j<nrBytes;j++){
				picDate = picDate + (char)cuvinte.readByte();
			}
		    cuvinte.close();
			
			//picDate = line;//.substring(188,207);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return picDate;
	}
	
	public String getDateTimeFromJpeg(File curFile){
		Metadata metadata;
		try {
			metadata = JpegMetadataReader.readMetadata(curFile);
			Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
			return exifDirectory.getString(ExifDirectory.TAG_DATETIME_ORIGINAL);
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	private void parseDir(String dir) throws Exception{
		int test = 0;
		File curDir = new File(dir);
		String fileName = "";
		if(curDir.isDirectory()){
			File[] allFielsInDir = curDir.listFiles();
			for(int i = 0; i < allFielsInDir.length; i++){
				if(!allFielsInDir[i].getAbsoluteFile().isDirectory()){
					test++;
					fileName = allFielsInDir[i].getName();
					if(fileName.toLowerCase().endsWith(".jpg")){
						files.add(allFielsInDir[i]);
						/*if(this.type == 1){
						 System.out.println(allFielsInDir[i].getParent() + "\\" + this.newName(fileName, i + 1)
								+ "-|-" + fileName);
							   }
							   if(this.type == 2){
						 allFielsInDir[i].renameTo(new File(allFielsInDir[i].getParent() + "\\" +
						  this.newName(fileName, i + 1)));
							   }*/
					}
				} else{
					System.out.println(allFielsInDir[i].getName());
//					if(!allFielsInDir[i].getName().equalsIgnoreCase("__filme")){
//						try{
//							;//parseDir(allFielsInDir[i].getCanonicalPath());
//						} catch(IOException ex){
//						}
//					}
				}
			}
			File tmp = null;
			for(int i = 0; i < files.size(); i++){
				tmp = (File)files.get(i);
				fileName = tmp.getName();
				/*if(this.type == 1){
					String tmpS = getProperties(tmp,187,19);
					if (!tmpS.substring(0,3).equals("200")){
						tmpS =  getProperties(tmp,205,19);
					}
					if (!tmpS.substring(0,3).equals("200")){
						tmpS =  getProperties(tmp,293,19);
					}
					if (!tmpS.substring(0,3).equals("200")){
						tmpS =  getProperties(tmp,1425,19);
					}
					if (!tmpS.substring(0,3).equals("200")){
						tmpS =  getProperties(tmp,1349,19);
					}*/
					//System.out.println(i + " Add: " + fileName + "->" + tmpS);
					dateCreated.add(new DateCreated(tmp,getDateTimeFromJpeg(tmp)));
					//dateCreated.add(new DateCreated(tmp,fixDeanIonut(tmp)));
					//System.out.println(tmp.getParent() + "\\"  + fileName + "->" + tmpS);
			}
		}
	}
	public Vector getFiles(String dir) throws Exception{
		parseDir(dir);
		//System.out.println(dateCreated.size());
		return dateCreated;
	}
	public String newName(String fileName, int contor) throws Exception{
		String rez;
		try{
			String extension = fileName.substring(fileName.lastIndexOf("."));
			String name = "";
			if(contor < 10){
				rez = "00" + contor + extension.toLowerCase();
			} else if(contor < 100){
				rez = "0" + contor + extension.toLowerCase();
			} else{
				rez = contor + extension.toLowerCase();
			}
		} catch(Exception ex){
			System.out.println("Error: " + fileName);
			throw ex;
		}

		//System.out.println(fileName  + "-" + rez);
		return rez;
	}
	public String fixDeanIonut(File tmp){
		GregorianCalendar gc = new GregorianCalendar();
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(tmp);
			Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
			String cameraMake = exifDirectory.getString(ExifDirectory.TAG_MAKE);
			String cameraModel = exifDirectory.getString(ExifDirectory.TAG_MODEL);
			String dateTime = exifDirectory.getString(ExifDirectory.TAG_DATETIME);
			
			gc.set((new Integer(dateTime.substring(0,4))).intValue(),
					(new Integer(dateTime.substring(5,7))).intValue(),
					(new Integer(dateTime.substring(8,10))).intValue(),
					(new Integer(dateTime.substring(11,13))).intValue(),
					(new Integer(dateTime.substring(14,16))).intValue(),
					(new Integer(dateTime.substring(17,19))).intValue());
			//exifDirectory.s
			//int hour = (new Integer(dateTime.substring(11,13))).intValue() - 12;
			//String rez = dateTime.substring(0,4) + ":06:24 " + hour;// +dateTime.substring(0,4) ;
			System.out.println(cameraMake + ":" + dateTime + "->" + showCalendar(gc));
			if (cameraMake.equals("OLYMPUS IMAGING CORP.")){
				setIonut(gc);
			}else if (cameraMake.equals("Panasonic")){
				setPanasonic(gc);
			}
			System.out.println(cameraMake + ":" + dateTime + "->" + showCalendar(gc));
			ro.cuzma.tools.FileTools.replaceInFile(tmp, dateTime, showCalendar(gc), true);
			//System.out.println(dateTime + "->" + showCalendar(gc));
			exifDirectory.setString(ExifDirectory.TAG_DATETIME,showCalendar(gc));
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return showCalendar(gc);
	}
	public  String showCalendar( java.util.Calendar cl){
		String year = cl.get(Calendar.YEAR) + ":";
		int tmp = cl.get(Calendar.MONTH);
		String month = "";
		if (tmp < 10) {
			month = "0" + tmp+":";
		}else{
			month = tmp+":";
		}
		tmp = cl.get(Calendar.DAY_OF_MONTH);
		String day = "";
		if (tmp < 10) {
			day = "0" + tmp+" ";
		}else{
			day = tmp+" ";
		}
		tmp = cl.get(Calendar.HOUR_OF_DAY);
		String hour = "";
		if (tmp < 10) {
			hour = "0" + tmp+":";
		}else{
			hour = tmp+":";
		}
		tmp = cl.get(Calendar.MINUTE);
		String min = "";
		if (tmp < 10) {
			min = "0" + tmp+":";
		}else{
			min = tmp+":";
		}
		tmp = cl.get(Calendar.SECOND);
		String sec = "";
		if (tmp < 10) {
			sec = "0" + tmp+"";
		}else{
			sec = tmp+"";
		}
		return year+month+day+hour+min+sec;
	}
	
	public  void setDean( java.util.Calendar cl){
		cl.add(Calendar.HOUR,11);
		cl.add(Calendar.DAY_OF_MONTH,-33);
		cl.add(Calendar.MINUTE,50);
		
	}
	public  void setPanasonic( java.util.Calendar cl){
		cl.add(Calendar.HOUR,1);
	}
	public  void setIonut( java.util.Calendar cl){
		cl.add(Calendar.HOUR,0);
		cl.add(Calendar.DAY_OF_MONTH,-37);
		cl.add(Calendar.MINUTE,-8);
		cl.add(Calendar.SECOND,-20);
		
	}
	
}
