package com.alacamli_secim.bluebirdapp.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class FileHelper {

	public static boolean writeToFile(Context context, Serializable obj, String filename) {
		try {
			FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(obj);
			os.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static<T> T readFromFile(Context context, String fileName) {
		try {
			FileInputStream fis = context.openFileInput(fileName);
			ObjectInputStream is = new ObjectInputStream(fis);
			
			@SuppressWarnings("unchecked")
			T obj = (T) is.readObject();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}