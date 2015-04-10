package com.manyi.hims.lookhouse.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.manyi.hims.Response;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserLookHouseRes extends Response{
	
	private int id;//task id
	private int houseId;//houseId
	
	private int cityId;
	private String cityName;
	
	private int areaId;//区域
	private String areaName;
	private int townId;//片区
	private String townName;
	private int estateId;//小区
	private String estateName;
	private String builing;//楼栋号
	private String room;//室号
	private Date createTime;//领取时间
	private String createTimeStr;
	private Date finishDate;//审核完成时间
	private String finishDateStr;
	private Date uploadPhotoTime;//上传照片时间
	private String uploadPhotoTimeStr;
	
	private int houseState;//house状态
	private String houseStateStr;
	/**
	 * task 任务 状态
	 * 0,未领取  1已领取(未完成), 2审核中（提交图片完成）, 3审核成功,4审核失败,5过期
	 */
	private int taskStatus;
	private String taskStatusStr;
	
	private int employeeId;
	private String employeeName;
	private int userId;
	private String userName;
	
	private String note;
	private int layers;
	
	private int bedroomSum;	// 修改后的	几房
	private int livingRoomSum;	// 修改后的	几厅
	private int wcSum;			// 修改后的	几卫

	
}
