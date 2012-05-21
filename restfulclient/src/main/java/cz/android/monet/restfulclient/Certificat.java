package cz.android.monet.restfulclient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class Certificat {
	private String fileName;

	public Certificat(String filename) {
		this.fileName = filename;
	}
	
	public void write(Context context, byte[] buffer) throws IOException {
		FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
		fos.write(buffer);
		fos.close();
	}
	
	public byte[] read(Context context) throws IOException {
		FileInputStream fis = context.openFileInput(fileName);
		byte[] buffer = new byte[256];
		fis.read(buffer);
		
		return buffer;
	}
}
