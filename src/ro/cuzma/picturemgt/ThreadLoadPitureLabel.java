/*
 * Created on 03.03.2006
 * by Laurian Cuzma
 */
package ro.cuzma.picturemgt;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ro.cuzma.picturemgt.ui.PictureLabel;

public class ThreadLoadPitureLabel extends Thread {
    PictureLabel picture;
    JLabel preview;
    boolean loadVector;
    boolean loadPreview;
    Vector myPics;

    public ThreadLoadPitureLabel(String str, PictureLabel pic) {
        super(str);
        this.picture = pic;
        loadVector = false;
    }

    public ThreadLoadPitureLabel(String str, Vector pic) {
        super(str);
        this.myPics = pic;
        loadVector = true;
    }

    public ThreadLoadPitureLabel(String str, JLabel label, PictureLabel pic) {
        super(str);
        this.picture = pic;
        preview = label;
        loadPreview = true;
    }

    public void run() {
        if (loadPreview) {
            double width = preview.getParent().getWidth();
            double height = preview.getParent().getHeight();
            double maxVal = width;
            if (width < height)
                maxVal = height;
            byte[] arr = picture.getPic().generateThumbsPic((int) maxVal);
            ImageIcon pictureIcon = new ImageIcon();
            BufferedImage currentImage;
            try {
                currentImage = ImageIO.read(new ByteArrayInputStream(arr));
                double widthP = currentImage.getWidth();
                double heightP = currentImage.getHeight();
                // pictureIcon.setImage(currentImage);
                if (width / widthP < height / heightP) {
                    System.out.println("1-Panel-" + width + "-" + height + "-Old-" + widthP + "-"
                            + heightP + "-New-" + (int) width + "-"
                            + (int) (heightP * (width / widthP)));
                    pictureIcon.setImage(currentImage.getScaledInstance((int) width,
                            (int) (heightP * (width / widthP)), Image.SCALE_DEFAULT));

                } else {
                    System.out.println("2-Panel-" + width + "-" + height + "-Old-" + widthP + "-"
                            + heightP + "-New-" + (int) ((height / heightP) * widthP) + "-"
                            + (int) height);
                    pictureIcon.setImage(currentImage.getScaledInstance(
                            (int) ((height / heightP) * widthP), (int) height, Image.SCALE_FAST));

                }
                // this.picture.setImage(pictureIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }
            preview.setIcon(pictureIcon);
            preview.repaint();
        } else if (loadVector) {
            // load file
            PictureLabel tmp;
            Hashtable thPic = new Hashtable();
            if (this.myPics.size() > 0) {
                tmp = (PictureLabel) this.myPics.get(0);
                File tmpFi = new File(tmp.getPic().getFilePath()
                        + File.separator + "db.th");
                if (tmpFi.exists()) {
                    RandomAccessFile rw;
                    try {
                        rw = new RandomAccessFile(tmpFi, "rw");
                        Picture.loadThumbs(rw, thPic);
                        rw.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            boolean saveTh = false;
            for (int i = 0; i < this.myPics.size(); i++) {
                // System.out.println("pic: " + i);
                tmp = (PictureLabel) this.myPics.get(i);
                byte[] arr = tmp.getPic().getThumbsPic();
                if (arr == null) {
                    arr = (byte[]) thPic.get(tmp.getPic().getFileFullName());
                    if (arr == null) {
                        saveTh = true;
                        arr = tmp.getPic().generateThumbsPic();
                    }
                }
                ImageIcon pictureIcon = new ImageIcon();
                BufferedImage currentImage;
                try {
                    currentImage = ImageIO.read(new ByteArrayInputStream(arr));

                    pictureIcon.setImage(currentImage);
                    tmp.setSize(currentImage.getWidth(), currentImage.getHeight());
                    tmp.setImage(currentImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tmp.setIcon(pictureIcon);

                tmp.repaint();
            }
            if (saveTh) {
                tmp = (PictureLabel) this.myPics.get(0);
                File tmpF = new File(tmp.getPic().getFilePath()+ File.separator + "db.th");
                if (!tmpF.exists()) {
                    try {
                        tmpF.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    tmpF.delete();
                    try {
                        tmpF.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                try {
                    RandomAccessFile rw = new RandomAccessFile(tmpF, "rw");
                    for (int i = 0; i < this.myPics.size(); i++) {
                        tmp = (PictureLabel) this.myPics.get(i);
                        // System.out.println("Write thumbs for:" +tmp.getPic().getName());
                        tmp.getPic().writeThumbs(rw);
                    }
                    rw.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            byte[] arr = picture.getPic().getThumbsPic();
            if (arr == null) {
                arr = picture.getPic().generateThumbsPic();
            }
            ImageIcon pictureIcon = new ImageIcon();
            BufferedImage currentImage;
            try {
                currentImage = ImageIO.read(new ByteArrayInputStream(arr));
                pictureIcon.setImage(currentImage);
                this.picture.setImage(currentImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            picture.setIcon(pictureIcon);
            picture.repaint();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }
}
