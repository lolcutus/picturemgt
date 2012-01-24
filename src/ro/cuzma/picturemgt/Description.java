package ro.cuzma.picturemgt;

import java.io.File;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class Description{
	int type = 0;
	String number;
	String text;
	public Description(){
	}

	public static void main(String[] args){
		Description desc = new Description();
		//System.out.println(desc.newName("001-Cucu.jpg","2003-001","Acasa"));
		if(args.length != 4){
			desc.showHelp();
		} else{
			if(args[0].equalsIgnoreCase("show")){
				desc.type = 1;
			} else if(args[0].equalsIgnoreCase("change")){
				desc.type = 2;
			} else{
				desc.showHelp();
			}
			if(desc.type != 0){
				desc.number = args[2];
				desc.text = args[3];
				desc.doAction(args[1]);
			}
		}
	}

	private void showHelp(){
		System.out.println("larry.poze.Description show/change dir number text");
	}

	public void doAction(String dir){
		PictureProperties pp =  new PictureProperties();
		try {
			Vector tst = pp.getFiles(dir);
			System.out.println(tst.size());
			TreeSet ts = new TreeSet(tst);
			System.out.println(ts.size());
			java.util.Iterator it = ts.iterator();
			//System.out.println(it.);
			DateCreated tmp = null;
			int contor = 1;
			String fileName = "";
			while (it.hasNext()){
				tmp = (DateCreated)it.next();
				//System.out.println(tmp.getFile().getName() + "-" + contor);
				fileName = tmp.getFile().getName();
				if(fileName.toLowerCase().endsWith(".jpg")){
					String newName = Description.newName(fileName, number, text, contor);
					contor++;
					//System.out.println("            "+this.type+"-"+newName);
					if(newName != null){
						if(this.type == 1){
							System.out.println(tmp.getFile().getParent() + "\\" +tmp.getFile().getName()+ "->" +newName + "->" + tmp.getCreationDate());
						}
						if(this.type == 2 && newName != null){
							//System.out.println("                      " + tmp.getFile().getParent() + "\\"  +tmp.getFile().getName()+ "->" + newName + "->" + tmp.getCreationDate());
							tmp.getFile().renameTo(new File(tmp.getFile().getParent() + "\\" + newName));
						}
					} else{
						System.out.println(fileName + " can not be change!!!!");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*private void parseDir(String dir){
		File curDir = new File(dir);
		String fileName = "";
		if(curDir.isDirectory()){
			File[] allFielsInDir = curDir.listFiles();
			for(int i = 0; i < allFielsInDir.length; i++){
				fileName = allFielsInDir[i].getName();
				if(fileName.toLowerCase().endsWith(".jpg")){
					String newName = this.newName(fileName, number, text);
					if(newName != null){
						if(this.type == 1){
							System.out.println(allFielsInDir[i].getParent() + "\\" + newName);
						}
						if(this.type == 2 && newName != null){
							allFielsInDir[i].renameTo(new File(allFielsInDir[i].getParent() + "\\" + newName));
						}
					} else{
						System.out.println(fileName + " can not be change!!!!");
					}
				}
			}
		}
	}*/

	public static String newName(String fileName, String number, String text, int contor){
		//System.out.println(contor);
		//System.out.println(fileName + "-" + contor);
		try{
			String extension = fileName.substring(fileName.lastIndexOf("."));
			//int pos = fileName.indexOf("-");
			String position = "";
			String description = "";
			if(contor < 10){
				position = "00" + contor;
			} else if(contor < 100){
				position = "0" + contor;
			} else{
				position = contor + "";
			}
			/*if(pos != -1){
				position = fileName.substring(0, pos);
				description = fileName.substring(fileName.indexOf("-") + 1, fileName.indexOf("."));
				return "[" + number + "][" + text + "][" + position + "][" + description + "]" + extension.toLowerCase();
			} else{*/
				//position = fileName.substring(0, fileName.indexOf("."));
			//System.out.println("         [" + number + "][" + text + "][" + position + "]" + extension.toLowerCase());
				return "[" + number + "][" + text + "][" + position + "]" + extension.toLowerCase();
			//}

		} catch(Exception ex){

			System.out.println(fileName + ":" + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	public static String replaceStr(String old, String what, String with){
		int index = old.indexOf(what);
		int whatLenght = what.length();
		String temp = "";
		while(index != -1){
			temp = temp + old.substring(0, index) + with;
			old = old.substring(index + whatLenght);
			index = old.indexOf(what);
		}
		temp = temp + old;
		return temp;
	}
}
