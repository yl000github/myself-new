package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FileUtil {
	Log log=LogFactory.getLog(FileUtil.class);
	public static boolean write(InputStream is,String des){
		try {
			OutputStream os;
			os = new FileOutputStream(des);
			byte[]b=new byte[1024];
			int n;
			while((n=is.read(b))!=-1){
				os.write(b,0,n);
			}
			os.flush();
			os.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static boolean copy(String src,String des){
		InputStream is;
		try {
			is = new FileInputStream(src);
			return write(is, des);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
