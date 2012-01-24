/*
 * Created on 28.02.2006
 * by Laurian Cuzma
 */
package ro.cuzma.picturemgt;

import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import java.awt.*;
import javax.swing.*;

public class JPEG {
	public JPEG() {}

	// i = la largeur de l'image redimensionner
	// s = le chemin d'acces ? l'image original
	// s1 = le chemin d'acces ? l'image retaillée
	public static ByteArrayOutputStream resize(int i, File picture) {
		try {
			File file = picture;

			// Parametrage de la lecture
			ImageIO.setUseCache(true);

			BufferedImage src = ImageIO.read(file);

			double d = src.getWidth();
			double d1 = src.getHeight();
			double d2 = i;
			double d3 = d2 / d;

			if (d1 * d3 > d2) {
				d3 = d2 / d1;
			}

			if (d3 > 0.8D) {
				d3 = 1.0D;
			}

			int j = (int) (d * d3);
			int k = (int) (d1 * d3);

			AffineTransform tx = new AffineTransform();
			tx.scale(d3, d3);

			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			rh.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			rh.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			rh.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			AffineTransformOp op = new AffineTransformOp(tx, rh);
			BufferedImage biNew = new BufferedImage(j, k, src.getType());

			op.filter(src, biNew);
			ByteArrayOutputStream iop = new ByteArrayOutputStream(1024);
			ImageIO.write(biNew, "jpg", iop);
			return iop;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String args[]) {
		JPEG jpeg = new JPEG();
		ByteArrayOutputStream baos = jpeg.resize(300, new File(
				"F:\\Multimedia\\Picture\\2005\\2005.04-02 La Nasa\\[2005.04-02][La Nasa][001].jpg"));
		byte[] tmp = baos.toByteArray();
		try {
			FileOutputStream fo = new FileOutputStream("e:\\a2.jpg");
			fo.write(tmp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*for(int i = 0;i < tmp.length;i++){
			System.out.print(tmp[i]);
		}*/

	}
}
