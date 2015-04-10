package com.manyi.fyb.callcenter.utils;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailUtil {
	private static Logger logger = LoggerFactory.getLogger(ThumbnailUtil.class);

	private static int THUMBNAIL_WIDTH = 640;
	private static int THUMBNAIL_HEIGHT = 640;
	
	/**
	 * @date 2014年5月26日 下午4:18:30
	 * @param bigImageFile 原图
	 * @param thumbNailFile 缩略图
	 * 
	 * @description  
	 * 按照比例缩放大小（true），
	 * 
	 * 获得指定大小的图片
	 * 
	 * 若图片横比200小，高比300小，不变
	 * 若图片横比200小，高比300大，高缩小到300，图片比例不变 
	 * 若图片横比200大，高比300小，横缩小到200，图片比例不变
	 * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
	 * 
	 */
	public static File getThumbnail(File bigImageFile, File thumbNailFile) {
		try {
			//size(宽度, 高度)
			Thumbnails.of(bigImageFile).size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT).toFile(thumbNailFile);
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return thumbNailFile;
	}
	
	
	/**
	 * @date 2014年5月26日 下午4:18:30
	 * @param bigImageFile 原图
	 * @param scale 比例  (//scale(0.25f)//scale(1.10f))
	 * @param thumbNailFile 缩略图
	 * 
	 * @description  
	 * 按照比例缩放大小（true），
	 * 
	 * 按照比例缩放图片
	 * 
	 */
	public static File getThumbnail(File bigImageFile, double scale, File thumbNailFile) {
		try {
			//scale(0.25f)//scale(1.10f)
			Thumbnails.of(bigImageFile).scale(scale).toFile(thumbNailFile);
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return thumbNailFile;
	}
	
	/**
	 * @date 2014年5月26日 下午4:18:30
	 * @param bigImageFile 原图
	 * @param scale 比例  (//scale(0.25f)//scale(1.10f))
	 * @param thumbNailFile 缩略图
	 * 
	 * @description  
	 * 按照比例缩放大小（false），
	 * 
	 * 按照指定大小进行缩放
	 * 
	 */
	public static File getAssignThumbnail(File bigImageFile, File thumbNailFile) {
		try {
			//keepAspectRatio(false) 默认是按照比例缩放的
			Thumbnails.of(bigImageFile).size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT).keepAspectRatio(false).toFile(thumbNailFile);
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return thumbNailFile;
	}
	
	
//watermark(位置，水印图，透明度)  
//Thumbnails.of("images/a380_1280x1024.jpg")   
//        .size(1280, 1024)  
//        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("images/watermark.png")), 0.5f)   
//        .outputQuality(0.8f)   
//        .toFile("c:/a380_watermark_bottom_right.jpg");  
	
	
//toOutputStream(流对象) 
//OutputStream os = new FileOutputStream("c:/a380_1280x1024_OutputStream.png");  
//Thumbnails.of("images/a380_1280x1024.jpg")   
//        .size(1280, 1024)  
//        .toOutputStream(os);
	
	
//asBufferedImage() 返回BufferedImage  
//	BufferedImage thumbnail = Thumbnails.of("images/a380_1280x1024.jpg")   
//	        .size(1280, 1024)  
//	        .asBufferedImage();  
//	ImageIO.write(thumbnail, "jpg", new File("c:/a380_1280x1024_BufferedImage.jpg"));  


	public static void main(String[] args) {
		ThumbnailUtil.getAssignThumbnail(new File("D:\\temp\\1.jpg"), new File("D:\\temp\\1111.jpg"));
	}
}
