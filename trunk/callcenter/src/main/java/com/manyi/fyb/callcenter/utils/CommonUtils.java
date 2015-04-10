package com.manyi.fyb.callcenter.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

public class CommonUtils {
	public static void responseCommon(HttpServletResponse response,String contentType,String text) throws IOException{
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);
		response.getWriter().write(text);
		response.getWriter().flush();
	}
	
	public static void responseCommon(HttpServletResponse response,String text) throws IOException{
		responseCommon(response, "text/html",text);
	}
	
	public static boolean checkAndMkdirs(String path) {
		File f = new File(path);
		if (f.getParentFile() != null) {
			if ( !f.getParentFile().canWrite()) {//不可写
				return f.getParentFile().mkdirs() ;
			}else {
				return true;
			}
		}
		return false;
	}
	
	/** 
	 * 写文件到本地 
	 * @param in 
	 * @param fileName 
	 * @throws IOException 
	 */
	public static void copyFile(InputStream in,String path) throws FileNotFoundException,IOException{
		
		if(checkAndMkdirs(path) == false) throw new FileNotFoundException("创建文件夹失败，无法写入");
		
		FileOutputStream fs = new FileOutputStream(path);
		byte[] buffer = new byte[1024 * 1024];
		//int bytesum = 0;
		int byteread = 0;
		while ((byteread = in.read(buffer)) != -1) {
			//bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		in.close();
	}


}
