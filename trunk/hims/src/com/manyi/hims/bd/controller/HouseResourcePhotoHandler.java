package com.manyi.hims.bd.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.manyi.hims.bd.service.HouseResourcePhotoService;
import com.manyi.hims.util.OSSObjectUtil;

@Component
@Scope(value = "prototype")
public class HouseResourcePhotoHandler {

	private static Logger logger = LoggerFactory.getLogger(HouseResourcePhotoHandler.class);

    @Autowired
    private HouseResourcePhotoService houseResourcePhotoService;
    
    @Autowired
    private ThreadPoolTaskExecutor executor;
    
    private void executorUploadFile2aliyunOSS(String aliyunPathPrefix, Map<String, String> imgKeyMap, String housePhotoPath, int flagNum, int taskId, int houseId) {

		if (flagNum == 3) {
			logger.info("异步上传阿里云OSS图片失败，已到3次，不再进行尝试；退出程序！ houseId={}, bdTaskId={}...", houseId, taskId);
			return;
		}
		
		logger.error("第{}次异步上传阿里云OSS图片开始...", flagNum + 1);
		
		try {
    		
//    		1、先删除houseId下照片
//    		OSSObjectUtil.deleteFile4prefix(aliyunPathPrefix);
//    		2、再上传
    		OSSObjectUtil.uploadFile(imgKeyMap);
    		
    		houseResourcePhotoService.updateBdtask4AliyunOSSState(taskId);

//    		3、删除hims服务器上的临时目录下的 
    		houseResourcePhotoService.deleteLajiFile(housePhotoPath);
    		
    		logger.info("第{}次异步上传图片到阿里云任务成功，houseId={}, bdTaskId={}...", flagNum + 1, houseId, taskId);

    	} catch (Exception e) {
			logger.error("第{}次异步上传阿里云OSS图片失败，40秒后重新上传，houseId={}, bdTaskId={}...", flagNum + 1, houseId, taskId);
			e.printStackTrace();
			
			try {
				Thread.sleep(40000);
			} catch (InterruptedException e1) {
				logger.error("第{}次异步上传阿里云OSS图片失败，40秒后重新上传，houseId={}, bdTaskId={}...", flagNum + 1, houseId, taskId);
				e1.printStackTrace();
				
			} finally {
				
				executorUploadFile2aliyunOSS(aliyunPathPrefix, imgKeyMap, housePhotoPath, ++flagNum, taskId, houseId);
			}
		}

	}
    
	public void uploadFile2aliyunOSS(final String aliyunPathPrefix, final Map<String, String> imgKeyMap, final String housePhotoPath, final int flagNum, final int taskId, final int houseId) {
		
		logger.info("异步上传图片到阿里云任务开始，houseId={}, bdTaskId={}...", houseId, taskId);

    	executor.submit(new Runnable() {
            public void run() {
	        	executorUploadFile2aliyunOSS(aliyunPathPrefix, imgKeyMap, housePhotoPath, flagNum, taskId, houseId);
            }
        });
    }
    
}
