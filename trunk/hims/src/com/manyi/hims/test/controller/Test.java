package com.manyi.hims.test.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import com.leo.common.util.MD5Digest;

public class Test {
	
	public static void main(String[] args) throws Exception {
		System.out.println(MD5Digest.getMD5Digest("111111q"));
		//5f8591a3d4fe06393cad53edff27ba05

//		ApplicationContext ac = new FileSystemXmlApplicationContext("E:\\workspace\\hims\\src\\com\\manyi\\hims\\test\\service\\TomContext.xml");
//		TomTestService tomTestService = (TomTestService)ac.getBean("tomTestService");

//		String imgpath = "D:\\temp\\hims\\8080\\206\\bedRoom1\\1c23786f-ea22-4aa6-8bb9-c3e363d93431.jpg";
//		FileInputStream fis = new FileInputStream(imgpath); 
//		File imgfile = new File(imgpath);
//        BufferedImage buff = ImageIO.read(imgfile); 
		
		
		String aa = "d1d3a5";
		
		System.out.println(aa.toUpperCase());

//        imgfile.get
		
//		long a = System.currentTimeMillis();
//		for (int i = 0 ; i < 10000; i ++) {
//			new Date().getTime();
//		}
//		long b = System.currentTimeMillis();
//		
//		
//		for (int i = 0 ; i < 10000; i ++) {
//			Calendar.getInstance().getTime().getTime();
//		}
//		long c = System.currentTimeMillis();
//		
//		
//		System.out.println(b-a);
//		System.out.println(c-b);

	}
	

	

	
	
}
