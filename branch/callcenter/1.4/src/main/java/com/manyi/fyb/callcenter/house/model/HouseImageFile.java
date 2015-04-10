package com.manyi.fyb.callcenter.house.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class HouseImageFile implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int houseId;
	private String type;// 类型：卧室bedroom、客厅livingRoom、卫生间wc、、、EntityUtils.HouseResourceType
	private String description;// 描述 主卧、次卧1、次卧2、门牌1、阳台、外景、、、EntityUtils.HouseResourceType
	private String imgExtensionName;// 图片扩展名称
	private String imgKey;// 阿里云存储的key
	private String imgKeyStr;
	private String thumbnailKey;// 阿里云存储的key ； 图片缩略图
	private int orderId;// 呈现图片的顺序 EntityUtils.HouseResourceType 中第一列
	private Date takePhotoTime;
	private Date addTime;
	
	private int bdTaskId; //bd人员任务id， 默认0
	private int userTaskId; // 中介(经纪人)看房任务id， 默认0 
	private int enable;// 是否可用标识，1可用  0 不可用 ，任务可用，图片就可用
	
}
