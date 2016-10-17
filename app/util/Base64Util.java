package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import play.Logger;

public class Base64Util {
	public static String encode(File f) {
		String res = null;
		if(f == null) {
			return res;
		}
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(f);
			byte[] bytes = new byte[(int) f.length()];
			fileInputStreamReader.read(bytes);
			fileInputStreamReader.close();
			byte[] encodedfile = Base64.encodeBase64(bytes);
			res = new String(encodedfile);
		} catch (IOException e) {
			Logger.error(e, e.getLocalizedMessage());
		}
		return res;
	}
}
