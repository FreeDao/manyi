package com.manyi.hims.util;


import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;


/**
 * 文件的工具类
 * 
 */
public class ImageUtils {
	private static Logger logger = LoggerFactory.getLogger(ImageUtils.class);


	/**
	 * 文件路径（包含文件）或者文件的目录<br>
	 * fileName的长度>0,返回文件路径 ,例:AAA/BBB/C.txt<br>
	 * fileName=null或者长度为0,返回文件目录,例:AAA/BBB/<br>
	 * @param dir
	 *            目录路径
	 * @param fileName
	 *            文件名称
	 * @param dir
	 * @param fileName
	 * @return
	 **/
	public static Date getTakePhotoDate(File jpegFile) {
		Date date = null;
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
			Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
			if (exifDirectory.containsTag(ExifDirectory.TAG_DATETIME)) {
				try {
					date = exifDirectory.getDate(ExifDirectory.TAG_DATETIME);
				} catch (MetadataException e) {
					logger.info("读取图片拍摄时间错误：{}", e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (JpegProcessingException e) {
			logger.info("读取图片拍摄时间错误：{}", e.getMessage());
			e.printStackTrace();
		}
		return date;
	}
}
