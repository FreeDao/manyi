package com.manyi.hims.lookhouse.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.manyi.hims.Response;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class MakeCallResponse extends Response{
	
	private List<HouseResourceContactResponse> contactResponses;
	
	private HouseResponse house ;
	
	private HouseResourceResponse houseResource;
	
	private List<HouseResourceHistoryResponse> houseResourceHistories;
	
	private List<BdTaskMakeCallHistoryResponse> makeCallHistories;
	
	private BdTaskResponse bdTaskResponse;
	
	private List<EmployeeResponse> empList;
	
	@Data
	public static class HouseResponse {
		private int houseId;
		private String building;// 楼座编号（例如：22栋，22坐，22号）
		private int floor;// 楼层
		private String room;// 房号（例如：1304室，1004－1008室等）
		private int layers;// 总层高
		private BigDecimal coveredArea;// 建筑面积
		private BigDecimal spaceArea;// 内空面积
		private int houseRightId;// 产权	
		private int status;//1有效，2未审核，3无效, 4删除
		private Date updateTime;
		
		/*********************************** FK ***********************************/
		private int certificates;// 拥有的产证 0没有产证，1房产证，2土地证 1&2双证 和HouseCertificate的id进行&操作
		private int houseTypeId;// 类型（对应HouseType'Id）
		private int houseDirectionId;// 朝向（对应HouseDirection'Id）
		private int estateId;// 所属小区或楼盘（对应Estate'Id）
		private int mainOwnerId;// 主产权人ID(对应 Owner'ownerId)
		private String otherOwnersId;//其他产权人Id（对应 Owner'Id）格式：1,2,3,4，需要在程序中解析出id集合
		/*********************************** FK ***********************************/
		private int bedroomSum;// 几房
		private int livingRoomSum;// 几厅
		private int wcSum;// 几卫
		private int balconySum;// 几阳台
		
		private int decorateType; //1 毛坯，2 装修
		
		private int picNum;
		private int currBDTaskId;//当前bdtask;
		
		private String subEstateName;
		private String estateName;
		private String townName;
		private String cityName;
		private String provinceName;
		
		private int subEstateId;//子划分的id
		private String subEstateStr;//子划分的名称
		
	}
	
	@Data
	public static class HouseResourceResponse {
		private int houseId;//1=1 House
		private int userId;
		private BigDecimal rentPrice;//出租价格
		private BigDecimal sellPrice;//出售价格
		private boolean isGotPrice;//是否到手价，标记sellPrice是否是到手价
		private Date rentFreeDate;// 可入住时间
		private Date entrustDate;//委托时间
		private int houseState;//1出租，2出售，3即租又售，4即不租也不售
		private int stateReason;//导致状态的可能原因,默认值0，1已租，2不租（不想租）,3已售，4，不售（不想售）我们提供选择，若以后需要新增理由直接增加。这样需要检索由某种原因导致的房源不租不售信息很方便
		private int actionType;//1发布，2，改盘，3举报，4轮询，5抽查
		private Date publishDate;//发布时间，此房源被发布，改盘，轮询的时间
		private int operatorId;//审核人的Id，每次审核完成清空，此字段跟Employee表关联role,是客服，就是分配的客服人员，是地推，就是分配的地推人员
		private int status;//状态，1审核通过,2审核中，3审核失败, 4无效/删除
		private int checkNum;//客服的审核次数
		private Date resultDate;//客服确认结果 时间(包含 审核通过,审核失败 的结果的时候 的时间)
		private String remark;//备注
		
		
		//transfer
		private String userName;
		private String userMobile;
		private String doorName;
		private String operatorName;
		private String actionTypeStr;
		private String statusStr;
		private String houseStateStr;
		private String stateReasonStr;
		
	}
	
	@Data
	public static class HouseResourceContactResponse {
		private int contactId;
		private String hostName;//联系人姓名
		private String hostMobile;//联系人电话
		private int houseId; //关联房源id
		private boolean enable; //禁用代表历史联系人   启用代表当前联系人
		private Date backTime;//归档时间 '
		private int status;//(默认为未勾选状态，即未打电话状态  标记为0),第一类统一称为“有效”1，第二类为“待确认”2，第三类为“无效”3 added by fuhao tips tom 

	}
	
	@Data
	public static class HouseResourceHistoryResponse {
		private int historyId;
		private int houseId;//对应的House Resource ID，多对1
		private BigDecimal rentPrice;//出租价格
		private BigDecimal sellPrice;//出售价格
		private boolean isGotPrice;//是否到手价，标记sellPrice是否是到手价
		private Date rentFreeDate;// 可入住时间
		private Date entrustDate;//委托时间
		private int houseState;//1出租，2出售，3即租又售，4即不租也不售
		private int stateReason;//导致状态的可能原因,默认值0，1已租，2不租（不想租）,3已售，4，不售（不想售）我们提供选择，若以后需要新增理由直接增加。这样需要检索由某种原因导致的房源不租不售信息很方便
		private int actionType;//1发布，2，改盘，3举报，4轮询，5抽查 对应HouseResourceTypeEnum
		private Date publishDate;//发布时间，此房源被发布，改盘，轮询的时间
		private int operatorId;//审核人的Id，每次审核完成清空，此字段跟Employee表关联role,是客服，就是分配的客服人员，是地推，就是分配的地推人员
		private int status;//状态，1审核通过,2审核中，3审核失败, 4删除
		private int checkNum;//客服的审核次数
		private String remark;//备注	
		private int userId;//经纪人用户对象，若有系统发起，比如：轮询，userId为0
		private Date createTime;//记录时间
		private Date finishDate; //审核完成时间
	}
	
	
	@Data
	public static class BdTaskResponse{
		
		private int id;
		private int houseId;
		private int employeeId;  //地推人员id
		private int manageId;  //管理员id 
		
		private Date createTime;
		private int phoneMakerNum;//打电话的次数
		private Date taskDate; // 任务时间
		private Date finishDate; // 完成时间
		private String longitude;// 坐标 经度
		private String latitude;// 坐标 纬度
		private int taskStatus; // 1任务开启(电话预约中) 2预约失败 3预约成功（看房任务开启）  4看房失败  5看房成功
		private String explainStr; // 对该房屋情况作一个简要说明
		private String remark; // 如果任务失败，则要作一个简要的情况说明
		private String taskImgStr; // 任务图片统计字段
		
	}
	
	@Data
	public static class BdTaskMakeCallHistoryResponse {
		
		/**
		 * historyId
		 */
		private int historyId;
		/**
		 * BDtaskId
		 */
		private int bdTaskId;
		/**
		 * 打电话的人
		 */
		private int phoneMaker;
		
		private String phoneMakerName;
		
		private int employeeId;
		
		private String employeeName;
		
		private int taskStatus;
		
		private String taskStatusStr;
		
		/**
		 * 打电话的内容反馈
		 */
		private String note;
		
		private Date taskDate; // 任务时间
		
		private Date addTime;
	}
	
	@Data
	public static class EmployeeResponse {
		/**
		 * 员工id
		 */
		private int employeeId;
		
		/**
		 * 用户名
		 */
		private String userName;
		
		
		/**
		 * 手机号
		 */
		private String mobile;
		
		/**
		 * email
		 */
		private String email;
		
		/**
		 * 身份证号 
		 */
		private String number;
		
		/**
		 * 是否禁用
		 */
		private boolean disable;
		
		
		/**
		 * 密码
		 */
		private String password;
		
		/**
		 * 身份证姓名
		 */
		private String realName;
		
		/**
		 * 姓别-1男人，0保密，1女人
		 */
		private int gender;
		
		/**
		 * 工号
		 */
		private String jobNumber;
		
		/**
		 * 介绍
		 */
		private String introduce; 
		
		/**
		 * 角色 1管理员,2客服经理，3客服人员,4地推经理,5地推人员 6财务
		 */
		private int role;
		
		/**
		 * 是否在工作模式
		 */
		private boolean workingMode; 
		
		/**
		 * ucserver的用户名
		 */
		private String ucServerName;
		
		/**
		 * ucserver的密码
		 */
		private String ucServerPwd;
		
		/**
		 * ucserver的用户组
		 */
		private String ucServerGroupId;
		
		/**
		 * cityId 城市ID
		 */
		private int cityId;
		/**
		 * areaId 行政区域
		 */
		private int areaId;
		
		/**
		 * cityId 城市ID
		 */
		private String cityName;
		/**
		 * areaId 行政区域
		 */
		private String areaName;
		
		/**
		 * 添加时间
		 */
		private Date addTime; //时间
	}

}
