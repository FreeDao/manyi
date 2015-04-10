package com.manyi.hims.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.Bucket;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

public class OSSUtil {
	
	/**
	 * accessKeyId 与 accessKeySecret 
	 * 是由系统分配给用户的，称为ID对，用于标识用户，为访问OSS做签名验证。
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @return
	 */
	public static OSSClient loginOSSClient(String accessKeyId,String accessKeySecret){
		OSSClient client = new OSSClient(accessKeyId, accessKeySecret);
		return client;
	}
	
	
	/**
	 * 获取一个Bucket,如果没有bucket就创建一个新的
	 * @param client OSSClient实力对象
	 * @param bucketName bucket的名称
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Bucket findBucket(OSSClient client ,String bucketName) {
		Bucket bk = null;
		if(client.isBucketExist(bucketName)){
			List<Bucket> lists= client.listBuckets();
			for (Bucket bucket: lists) {
				if(bucket.getName().equals(bucketName)){
					bk =  bucket;
					break;
				}
			}
		}else{
		    // 新建一个Bucket
			bk = client.createBucket(bucketName);
		}
		return bk;
	}
	
	/**
	 * 得到 这个bucketName 所有的Object对象
	 * @param client
	 * @param bucketName
	 * @return
	 */
	public static ObjectListing findBucketLists(OSSClient client,String bucketName){
		ObjectListing lists= client.listObjects(bucketName);
		return lists;
	}
	
	/**
	 * Object通过InputStream的形式上传到OSS中
	 * @param client
	 * @param bucketName
	 * @param key
	 * @param file
	 * @throws IOException 
	 */
	public static String putObject(OSSClient client,String bucketName, String key, File file)  throws IOException{
		InputStream content =new FileInputStream(file);
	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();
	    // 必须设置ContentLength
	    meta.setContentLength(file.length());
	    // 上传Object
	    PutObjectResult result = client.putObject(bucketName, key, content, meta);
	    content.close();
	    return result.getETag();//新建的额OSSObject 的ETag值
	}
	
}
