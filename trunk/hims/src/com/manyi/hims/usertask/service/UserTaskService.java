package com.manyi.hims.usertask.service;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.leo.common.Page;
import com.manyi.hims.Response;
import com.manyi.hims.common.HouseInfoResponse;
import com.manyi.hims.usertask.controller.UserTaskController.FindHouseResourceRequest;
import com.manyi.hims.usertask.controller.UserTaskController.FindHouseResponse;

public interface UserTaskService {
	/**
	 * @author fangyouhui
	 * @param uid
	 * @return
	 * 我的拍照任务  待完成统计
	 */
	public int userTaskCount(int uid);
	
	/**
	 * @author fangyouhui
	 * @param uid
	 * @param houseId
	 * 领取任务
	 */
	public Response addUserTask(int uid,int houseId);
	
	/**
	 * @author fangyouhui
	 * @param userTaskId
	 * @return
	 * 任务详情
	 */
	public UserTaskDetailResponse userTaskDetail(int userTaskId);
	
	/**
	 * @author fangyouhui
	 * @param uid
	 * @return
	 * 待审核任务列表
	 */
	public List<UserTaskDetailResponse> userTaskIndex(int uid);
	/**
	 * @author fangyouhui
	 * @param uid
	 * @return
	 * 已完成任务列表
	 */
	public Page<UserTaskDetailResponse> userTaskIndexById(int uid,int first,int max);
	
	@Data
	public static class UserTaskDetailResponse{
		private HouseInfoResponse houseInfo;
		private Date createTime; //领取任务时间、创建任务时间
		private Date currenDate = new Date();//当前时间
		private Date uploadPhotoTime; //上传图片时间
		private int taskStatus; // 1已领取(未完成), 2审核中（提交图片完成）, 3审核成功,4审核失败,5过期
		private String note; // 任务失败原因
		private String taskImgStr; // 任务图片统计字段
		private String supportingMeasuresStr; //配置信息统计字段
		private String houseTypeStr; // 房型变化字段
		private String rentPriceStr; // 出租金额变化字段
		private String sellPriceStr; // 出售金额变化字段
		private String spaceAreaStr; // 面积变化字段
		private List<String> photoStrArray; // 图片列表
		private int id;//userTaskId
	}
	
	
	/**
	 * @date 2014年6月6日 下午2:50:19
	 * @author Tom
	 * @description  
	 * 查询房源是否满足拍照的条件
	 */
	public FindHouseResponse findHouseResource4UserTaskPhoto(FindHouseResourceRequest findHouseResourceRequest);
	
	/**
	 * @date 2014年6月12日 下午5:06:52
	 * @author Tom
	 * @description  
	 * 将48小时前任务 设置过期
	 */
	public void setUserTask48HoursExpired();
}
