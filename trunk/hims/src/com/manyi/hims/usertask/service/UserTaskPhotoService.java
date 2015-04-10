package com.manyi.hims.usertask.service;

import java.lang.reflect.InvocationTargetException;

import com.manyi.hims.entity.HouseSupportingMeasures;
import com.manyi.hims.usertask.controller.UserTaskPhotoController.HouseFileRequest;
import com.manyi.hims.usertask.controller.UserTaskPhotoController.TaskRequest;

public interface UserTaskPhotoService {

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
	 * 上传单张图片
	 */
	public void taskUploadSingleFile(HouseFileRequest houseFileRequest);
	
	
	/**
	 * @date 2014年5月28日 下午12:44:19
	 * @author Tom
	 * @description  
	 * 更新任务再次上传状态
	 */
	public void updateUserTask4AliyunOSSState(int taskId);
	
	/**
	 * @date 2014年5月21日 下午2:21:11
	 * @author Tom
	 * @description  
	 * 删除服务器上的垃圾文件
	 */
	public void deleteLajiFile(String filePath);

	/*房屋附属信息生成字符串，生成冗余字段，方便查询*/
	public String mapString(HouseSupportingMeasures hs) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
