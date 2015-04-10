package com.manyi.ihouse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.ServiceException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSErrorCode;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.CopyObjectResult;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.ListObjectsRequest;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.manyi.ihouse.base.Global;

public class OSSObjectUtil {

	private static Logger logger = LoggerFactory.getLogger(OSSObjectUtil.class);
	
    // 使用默认的OSS服务器地址创建OSSClient对象。
	public static OSSClient client = new OSSClient(Global.ACCESSKEYID, Global.ACCESSKEYSECRET);
	
	static{
		ensureBucket(client,  Global.BUCKETNAME);
		setBucketPublicReadable(client, Global.BUCKETNAME);
	}
	
    public static void main(String[] args) throws Exception {
//		OSSObjectUtil.deleteFile4prefix("0000100001/");
        String url = OSSObjectUtil.getUrl("0000100002/000010000200001/00001000020000100002/0000100002000010000200002/113264/8bce63eb-9026-441f-b400-03575fe517d3.thumbnail", 1000*60);
        
        System.out.println(url);
//        String bucketName = ACCESS_ID + "-object-test";
//        String key = "photo1.jpg";
//
//        String uploadFilePath = "d:/temp/photo.jpg";
//        String downloadFilePath = "d:/temp/photo1.jpg";
//
//        // 使用默认的OSS服务器地址创建OSSClient对象。
//        OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//
//        ensureBucket(client, bucketName);
//
//        try {
//            setBucketPublicReadable(client, bucketName);
//
//            System.out.println("正在上传...");
//            uploadFile(client, bucketName, key, uploadFilePath);
//
//            System.out.println("正在下载...");
//            downloadFile(client, bucketName, key, downloadFilePath);
//        } finally {
////            deleteBucket(client, bucketName);
//        }
    }
    
	public static void uploadFile(Map<String, String> map) {
		try {
			int i = 1;
			int n = map.size();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				
				logger.info("正在上传..BUCKETNAME={},..{}/{}..{}", Global.BUCKETNAME, i++, n, entry.getKey());
				uploadFile(client, Global.BUCKETNAME, entry.getKey(), entry.getValue());
				logger.info("上传成功..");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
//			throw new LeoFault(BdConst.BD_ERROR140002);
		}
	}
    
	public static void uploadFile(String key, String fileName) {
		try {
			
			logger.info("正在上传..BUCKETNAME={},..{}", Global.BUCKETNAME, fileName);
			uploadFile(client, Global.BUCKETNAME, key, fileName);
			logger.info("上传成功..");

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
//			throw new LeoFault(BdConst.BD_ERROR140002);
		}
	}
    
    
	public static File downloadFile(String key, String fileName) {
		File file = null;

		try {

			logger.info("正在下载..BUCKETNAME={},..{}", Global.BUCKETNAME, fileName);
			file = downloadFile(client, Global.BUCKETNAME, key, fileName);
			logger.info("下载成功.." + fileName);

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return file;

	}

    // 创建Bucket.
    private static void ensureBucket(OSSClient client, String bucketName) throws OSSException, ClientException{

        try {
            // 创建bucket
            client.createBucket(bucketName);
        } catch (ServiceException e) {
            if (!OSSErrorCode.BUCKES_ALREADY_EXISTS.equals(e.getErrorCode())) {
                // 如果Bucket已经存在，则忽略
                throw e;
            }
        }
    }

    // 删除一个Bucket和其中的Objects 
//    private static void deleteBucket(OSSClient client, String bucketName)
//            throws OSSException, ClientException {
//
//        ObjectListing ObjectListing = client.listObjects(bucketName);
//        List<OSSObjectSummary> listDeletes = ObjectListing
//                .getObjectSummaries();
//        for (int i = 0; i < listDeletes.size(); i++) {
//            String objectName = listDeletes.get(i).getKey();
//            // 如果不为空，先删除bucket下的文件
//            client.deleteObject(bucketName, objectName);
//        }
//        client.deleteBucket(bucketName);
//    }

    
    /**
     * 根据key删除文件
     */
    public static void deleteFile(String key) {
        logger.info("正在删除..BUCKETNAME={},key={}", Global.BUCKETNAME, key);
    	client.deleteObject(Global.BUCKETNAME, key);
    	logger.info("删除成功..");
	}
    
    
    /**
     * 根据给定前坠 删除文件
     */
    public static void deleteFile4prefix(String prefix) {
    	// 构造ListObjectsRequest请求
    	ListObjectsRequest listObjectsRequest = new ListObjectsRequest(Global.BUCKETNAME);

    	// 递归列出fun目录下的所有文件
    	listObjectsRequest.setPrefix(prefix);

    	ObjectListing listing = client.listObjects(listObjectsRequest);

    	// 遍历所有Object
    	for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
    		deleteFile(objectSummary.getKey());
    	}
	}
    
    // 把Bucket设置为所有人可读
    private static void setBucketPublicReadable(OSSClient client, String bucketName) throws OSSException, ClientException {
        //创建bucket
        client.createBucket(bucketName);

        //设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    // 上传文件
    private static void uploadFile(OSSClient client, String bucketName, String key, String filename) throws OSSException, ClientException, IOException {
        File file = new File(filename);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        objectMeta.setContentType("image/jpeg");

        InputStream input = new FileInputStream(file);
        client.putObject(bucketName, key, input, objectMeta);
        input.close();
    }

    // 下载文件
    private static File downloadFile(OSSClient client, String bucketName, String key, String filename) throws OSSException, ClientException {
    	File file = new File(filename);
        client.getObject(new GetObjectRequest(bucketName, key), file);
        return file;
    }
    
    //获得对象返回流
    public static InputStream getObject(String key) throws IOException {

        // 获取Object的输入流
        return client.getObject(Global.BUCKETNAME, key).getObjectContent();
    }
    
    //拷贝Object
    public static void copyObject(String srcKey, String destKey) {
        // 拷贝Object
        CopyObjectResult result = client.copyObject(Global.BUCKETNAME, srcKey, Global.BUCKETNAME, destKey);

        // 打印结果
        // System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
    }
    
    //剪切Object
    public static void cutObject(String srcKey, String destKey) {
        // 拷贝Object
        logger.info("正在剪切..BUCKETNAME={},srcKey={},destKey={}", Global.BUCKETNAME, srcKey, destKey);

        CopyObjectResult result = client.copyObject(Global.BUCKETNAME, srcKey, Global.BUCKETNAME, destKey);
        client.deleteObject(Global.BUCKETNAME, srcKey);
       
        logger.info("剪切成功..");
    }
    
    //动态地生成一个经过签名的URL
    public static String getUrl(String key, int timeout) {

        // 设置URL过期时间
//    	Date expiration = new Date(new Date().getTime() + 120 * 1000);
    	
        // 生成URL
        return client.generatePresignedUrl(Global.BUCKETNAME, key, new Date(new Date().getTime() + timeout)).toString();

	}
}
