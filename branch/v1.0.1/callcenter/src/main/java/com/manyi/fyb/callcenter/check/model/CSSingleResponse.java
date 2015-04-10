package com.manyi.fyb.callcenter.check.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.manyi.fyb.callcenter.base.model.Response;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class CSSingleResponse extends Response{
	
	private List<HouseResourceContactResponse> contactResponses;
	
	private HouseResponse house ;
	
	private HouseResourceResponse houseResource;
	
	private List<HouseResourceHistoryResponse> houseResourceHistories;
	
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
		
		private String subEstateName;
		private String estateName;
		private String townName;
		private String cityName;
		
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

}
