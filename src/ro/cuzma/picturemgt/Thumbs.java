/*
 * Created on 28.02.2006
 * by Laurian Cuzma
 */
package ro.cuzma.picturemgt;

import java.io.IOException;
import java.io.OutputStream;

public class Thumbs extends  javax.imageio.stream.ImageOutputStreamImpl{
	private byte[] pictureData;
	private int length  = 0;
	private int curlength  = 0;
	private int curPos  = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	// TODO Auto-generated method stub

	}
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		if (length == curlength) increaseData(0);
		pictureData[curlength] = (byte)(b&127);
		System.out.print("curlength: " + curlength + ">" + b + ">");
		System.out.print(b&127);
		System.out.println(">" + pictureData[curlength]);
		curlength ++;
	}
	public void write(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		if (length <= curlength + len){
			if (len > 1024){
				increaseData(len);
			}else{
				increaseData(0);
			}
		}
		for (int i = 0;i < len; i++){
			pictureData[curlength] = b[off+i];
			if (curlength < 10){
			System.out.print("curlength: " + curlength + ">" +  b[off+i] + ">");
			System.out.print( b[off+i]);
			System.out.println(">" + pictureData[curlength] + "off: " + off + " i: " + i);
			}
			//System.out.print((char)b[off+i]);
			curlength ++;
		}
		System.out.println(pictureData.length + "-" + curlength);
		
	}
	public int read() throws IOException {
		// TODO Auto-generated method stub
		System.out.println(">CP: "+curPos + " curlength: "+curlength);
		if (curPos == curlength) return -1;
		curPos++;
		System.out.println(pictureData[curPos-1]);
		return pictureData[curPos-1];
	}
	public int read(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		//System.out.print(">CP"+curPos);
		//System.out.println(">"+curPos + "-" +curlength+ "-" + len);
		if (curPos == curlength) return -1;
		int toRead = 0;
		if (curPos + len > curlength) {
			toRead = curlength - curPos;
		}else{
			toRead = len;	
		}
		//System.out.println("to read"+toRead);
		for (int i = 0;i < toRead; i++){
			b[off+i] = pictureData[curPos];
			System.out.print((char)b[off+i]+"-");
			curPos ++;
		}
		System.out.println("curpos"+curPos);
		return toRead;
	}
	private void increaseData(int val){
		if (val == 0) val = 1024;
		byte[] tmp = new byte[length + val];
		for (int i = 0;i < length; i++) tmp[i] = pictureData[i];
		length = length + val;
		pictureData = tmp;
	}
	/**
	 * @return Returns the pictureData.
	 */
	public byte[] getPictureData() {
		return this.pictureData;
	}

	
	
	
}
