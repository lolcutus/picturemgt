package ro.cuzma.picturemgt;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.jempbox.xmp.XMPSchemaBasic;
import org.xml.sax.SAXException;

import ro.cuzma.picturemgt.categories.Category;
import ro.cuzma.xmp.jpeg.JpegPicture;
import ro.cuzma.xmp.jpeg.XMPManager;

import com.drew.imaging.jpeg.JpegProcessingException;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PictureDatabase picDB = null;
        try {
            picDB = new PictureDatabase(new File("F:\\dbNew_start.xml"), "c:\\Larry\\MyPictures");
            System.out.println("Pictures" + picDB.getPictures().size());
            System.out.println("Catego" + picDB.getCategories().getCategories().size());
            Picture tmp = (Picture) picDB.getPictures().get(11700);
            // tmp.getKeywords()
            Category tmpC = null;
            System.out.println(tmp.getFileFullName() + "-" + tmp.getRating());
            for (int i = 0; i < tmp.getKeywords().size(); i++) {
                tmpC = (Category) tmp.getKeywords().get(i);
                System.out.println(tmpC.getFullName() + "-" + tmpC.getName());
            }
            try {
                JpegPicture jpeg = new JpegPicture(tmp.getFileFullName());
                // xmpTest.readfromAPP1(picture.getXMPdata());
                XMPManager xm = new XMPManager(jpeg);
                XMPSchemaBasic tmpX = xm.getXmpXML().getBasicSchema();
                if (tmpX == null) {
                    xm.getXmpXML().addBasicSchema();
                    tmpX = xm.getXmpXML().getBasicSchema();
                }
                tmpX.setRating(5);
                tmpX.setNickname("aura");
                tmpX.setRating(4);
                tmpX.setNickname("larry");
                xm.saveXMPintoAPP1();

                jpeg.saveFile("e:\\rez.jpg");
            } catch (JpegProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ParserConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        /*
         * try { picDB.saveAs(new File("f:\\dbNew.xml")); } catch (IOException e) { // TODO
         * Auto-generated catch block e.printStackTrace(); }
         */

    }

}
