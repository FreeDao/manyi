package com.manyi.hims.bd.service;

import com.manyi.hims.bd.controller.HouseResourcePhotoController.HouseFileRequest;
import com.manyi.hims.bd.controller.HouseResourcePhotoController.TaskLookFailRequest;
import com.manyi.hims.bd.controller.HouseResourcePhotoController.TaskRequest;

public interface HouseResourcePhotoService {

	/**
   	 * @date 2014年5月30日 下午8:01:29
   	 * @author Tom
   	 * @description  
   	 * 当尝试3次上传阿里云失败后，不能再上传，图片都在服务器上；这是小概率事件
   	 * 
   	 * 弥补这种情况，我们可以单独对没有上传的图片进行再次上传操作。
   	 * 
   	 */
    public void uploadFile2aliyunOSSAgain();
    
	 /**
	 * @date 2014年5月19日 下午8:01:29
	 * @author Tom
	 * @description  
	 * 提交信息
	 */
	public void taskSubmit(TaskRequest taskRequest);

	/**
	 * @date 2014年5月21日 下午2:21:11
	 * @author Tom
	 * @description  
	 * 没看成任务
	 */
	public void taskLookFail(TaskLookFailRequest taskLookFailRequest);
	
	/**
	 * @date 2014年5月21日 下午2:21:11
	 * @author Tom
	 * @description  
	 * 上传单张图片
	 */
	public void taskUploadSingleFile(HouseFileRequest houseFileRequest);
	
	
	/**
	 * @date 2014年5月28日 下午12:44:19
	 * @author Tom
	 * @description  
	 * 更新任务再次上传状态
	 */
	public void updateBdtask4AliyunOSSState(int taskId);
	
	/**
	 * @date 2014年5月21日 下午2:21:11
	 * @author Tom
	 * @description  
	 * 删除服务器上的垃圾文件
	 */
	public void deleteLajiFile(String filePath);

}
