package com.manyi.hims.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.ServiceException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSErrorCode;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;

/**
 * 该示例代码展示了如果在OSS中创建和删除一个Bucket，以及如何上传和下载一个文件。
 * 
 * 该示例代码的执行过程是：
 * 1. 创建一个Bucket（如果已经存在，则忽略错误码）；
 * 2. 上传一个文件到OSS；
 * 3. 下载这个文件到本地；
 * 4. 清理测试资源：删除Bucket及其中的所有Objects。
 * 
 * 尝试运行这段示例代码时需要注意：
 * 1. 为了展示在删除Bucket时除了需要删除其中的Objects,
 *    示例代码最后为删除掉指定的Bucket，因为不要使用您的已经有资源的Bucket进行测试！
 * 2. 请使用您的API授权密钥填充ACCESS_ID和ACCESS_KEY常量；
 * 3. 需要准确上传用的测试文件，并修改常量uploadFilePath为测试文件的路径；
 *    修改常量downloadFilePath为下载文件的路径。
 * 4. 该程序仅为示例代码，仅供参考，并不能保证足够健壮。
 *
 */
public class OSSObjectSample {
	
	
//	#S_constants.ACCESSKEYID=Pd28Pp4CKRL2CxzK
//	#S_constants.ACCESSKEYSECRET=d5wAxW5THMLdszwMtaqxMTFCaSlyhI
//	#S_constants.BUCKETNAME=house-images
			
    private static final String ACCESS_ID = "beiO73m3yhTYFZh4";
    private static final String ACCESS_KEY = "hmSByxIdKTut8TQESI3GTvQLyGyWEL";
    private static String bucketName = "house-test";

    public static void main(String[] args) throws Exception {
//        String bucketName = "house-images";
//        String key = "photo1";
        String key = "0000100001/000010000100002/00001000010000200001/0000100001000020000100020/113309/1178047b-e189-4557-8471-d4913933726f.thumbnail";

        String uploadFilePath = "D:\\temp\\hims\\206\\livingRoom1\\20140609233145.jpg";
        String downloadFilePath = "d:/temp/photo1.jpg";

        // 使用默认的OSS服务器地址创建OSSClient对象。
        OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
        
        getUrl(client, key);
        
        
       /* ensureBucket(client, bucketName);

        try {
            setBucketPublicReadable(client, bucketName);

            System.out.println("正在上传...");
            uploadFile(client, bucketName, key, uploadFilePath);

//            System.out.println("正在下载...");
//            downloadFile(client, bucketName, key, downloadFilePath);
        } finally {
//            deleteBucket(client, bucketName);
        }*/
    }

    private static void getUrl(OSSClient client, String key) {

        // 设置URL过期时间为1小时
        Date expiration = new Date(new Date().getTime() + 120 * 1000);

        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, key, expiration);
        System.out.println(url.toString());

	}
    
    
    // 创建Bucket.
    private static void ensureBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException{

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
    private static void deleteBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException {

        ObjectListing ObjectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> listDeletes = ObjectListing
                .getObjectSummaries();
        for (int i = 0; i < listDeletes.size(); i++) {
            String objectName = listDeletes.get(i).getKey();
            // 如果不为空，先删除bucket下的文件
            client.deleteObject(bucketName, objectName);
        }
        client.deleteBucket(bucketName);
    }

    // 把Bucket设置为所有人可读
    private static void setBucketPublicReadable(OSSClient client, String bucketName)
            throws OSSException, ClientException {
        //创建bucket
        client.createBucket(bucketName);

        //设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    // 上传文件
    private static void uploadFile(OSSClient client, String bucketName, String key, String filename)
            throws OSSException, ClientException, FileNotFoundException {
        File file = new File(filename);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        objectMeta.setContentType("image/jpeg");

        InputStream input = new FileInputStream(file);
        client.putObject(bucketName, key, input, objectMeta);
    }

    // 下载文件
    private static void downloadFile(OSSClient client, String bucketName, String key, String filename)
            throws OSSException, ClientException {
        client.getObject(new GetObjectRequest(bucketName, key),
                new File(filename));
    }
    
   
    
    
    
    
}
