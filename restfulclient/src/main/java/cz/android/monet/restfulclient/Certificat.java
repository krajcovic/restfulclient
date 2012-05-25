/*
 * 
 */
package cz.android.monet.restfulclient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

// TODO: Auto-generated Javadoc
/**
 * The Class Certificat.
 */
public class Certificat {
	
	/** The file name. */
	private String fileName;

	/**
	 * Instantiates a new certificat.
	 *
	 * @param filename the filename
	 */
	public Certificat(String filename) {
		this.fileName = filename;
	}
	
	/**
	 * Write.
	 *
	 * @param context the context
	 * @param buffer the buffer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void write(Context context, byte[] buffer) throws IOException {
		FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
		fos.write(buffer);
		fos.close();
	}
	
	/**
	 * Read.
	 *
	 * @param context the context
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public byte[] read(Context context) throws IOException {
		FileInputStream fis = context.openFileInput(fileName);
		byte[] buffer = new byte[256];
		fis.read(buffer);
		
		return buffer;
	}
}
