package com.manyi.fyb.callcenter.utils;

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

public class OSSObjectUtil {

	private static Logger logger = LoggerFactory.getLogger(OSSObjectUtil.class);
	
    // 使用默认的OSS服务器地址创建OSSClient对象。
	public static OSSClient client = new OSSClient(Constants.ACCESSKEYID, Constants.ACCESSKEYSECRET);
	
	static{
		ensureBucket(client,  Constants.BUCKETNAME);
		setBucketPublicReadable(client, Constants.BUCKETNAME);
	}
	
	public static void main(String[] args) throws Exception {
		//OSSObjectUtil.deleteFile4prefix("0000100001/");

        String bucketName = Constants.BUCKETNAME ;
        String key = "photo1.jpg";

        String uploadFilePath = "/home/fuhao/test/temp/photo.jpg";
        String downloadFilePath = "/home/fuhao/test/temp/photo1.jpg";

        // 使用默认的OSS服务器地址创建OSSClient对象。
        //OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);

        ensureBucket(client, bucketName);

        try {
            setBucketPublicReadable(client, bucketName);

            System.out.println("正在上传...");
            uploadFile(client, bucketName, key, uploadFilePath);

            System.out.println("正在下载...");
            downloadFile(client, bucketName, key, downloadFilePath);
        } finally {
//            deleteBucket(client, bucketName);
        }
        
        System.out.println(getUrl(key , 60 * 10 * 1000));
    }
    
	public static void uploadFile(Map<String, String> map) {
		try {
			int i = 1;
			int n = map.size();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				
				logger.info("正在上传..BUCKETNAME={},..{}/{}..{}", Constants.BUCKETNAME, i++, n, entry.getKey());
				uploadFile(client, Constants.BUCKETNAME, entry.getKey(), entry.getValue());
				logger.info("上传成功..");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			//throw new LeoFault(BdConst.BD_ERROR140002);
		}
	}
    
	public static void uploadFile(String key, String fileName) {
		try {
			
			logger.info("正在上传..BUCKETNAME={},..{}", Constants.BUCKETNAME, fileName);
			uploadFile(client, Constants.BUCKETNAME, key, fileName);
			logger.info("上传成功..");

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			//throw new LeoFault(BdConst.BD_ERROR140002);
		}
	}
    
    
	public static File downloadFile(String key, String fileName) {
		File file = null;

		try {

			logger.info("正在下载..BUCKETNAME={},..{}", Constants.BUCKETNAME, fileName);
			file = downloadFile(client, Constants.BUCKETNAME, key, fileName);
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
        logger.info("正在删除..BUCKETNAME={},key={}", Constants.BUCKETNAME, key);
    	client.deleteObject(Constants.BUCKETNAME, key);
    	logger.info("删除成功..");
	}
    
    
    /**
     * 根据给定前坠 删除文件
     */
    public static void deleteFile4prefix(String prefix) {
    	// 构造ListObjectsRequest请求
    	ListObjectsRequest listObjectsRequest = new ListObjectsRequest(Constants.BUCKETNAME);

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
        return client.getObject(Constants.BUCKETNAME, key).getObjectContent();
    }
    
    //拷贝Object
    public static void copyObject(String srcKey, String destKey) {
        // 拷贝Object
        CopyObjectResult result = client.copyObject(Constants.BUCKETNAME, srcKey, Constants.BUCKETNAME, destKey);

        // 打印结果
        // System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
    }
    
    //剪切Object
    public static void cutObject(String srcKey, String destKey) {
        // 拷贝Object
        logger.info("正在剪切..BUCKETNAME={},srcKey={},destKey={}", Constants.BUCKETNAME, srcKey, destKey);

        CopyObjectResult result = client.copyObject(Constants.BUCKETNAME, srcKey, Constants.BUCKETNAME, destKey);
        client.deleteObject(Constants.BUCKETNAME, srcKey);
       
        logger.info("剪切成功..");
    }
    
    //动态地生成一个经过签名的URL
    public static String getUrl(String key, int timeout) {
        // 设置URL过期时间
//    	Date expiration = new Date(new Date().getTime() + 120 * 1000);
        // 生成URL
        return client.generatePresignedUrl(Constants.BUCKETNAME, key, new Date(new Date().getTime() + timeout)).toString();
	}

	/**
	 * 得到暂时的http链接
	 * @author fuhao
	 * @param key
	 * @return
	 */
	public static String getUrl(String key) {
		return getUrl(key, Constants.ALIYUN_IMAGE_PATH_TIMEOUT);
	}

	/**
	 * 上传 
	 * @author fuhao
	 * @param key
	 * @param uploadFilePath
	 * @return
	 * @throws OSSException
	 * @throws ClientException
	 * @throws IOException
	 */
	public static String upload(String key,String uploadFilePath) throws OSSException,
			ClientException, IOException {
		// String key = "photo1.jpg";
		//String uploadFilePath = "/home/fuhao/test/temp/photo.jpg";
		System.out.println("正在上传...");
		uploadFile(client, Constants.BUCKETNAME, key, uploadFilePath);
		return getUrl(key);
	}
	
	/**
	 * @date 2014年5月22日 上午1:12:23
	 * @author Tom
	 * @description  
	 * 拼接上传阿里云路径：serialCode 0000100001000070000200106
	 * 
	 * 0000100001/000010000100007/00001000010000700002/0000100001000070000200106/
	 */
	public static String getAliyunPath(String serialCode) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; 5 * i < serialCode.length(); i ++ ) {
			sb.append(serialCode.substring(0, 5 * (i + 1)));
			sb.append("/");
		}
		return sb.toString();
	}
}