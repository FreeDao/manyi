package com.manyi.hims.sourcemanage.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.manyi.hims.Response;

/**
 * @date 2014年4月17日 下午12:47:51
 * @author Tom
 * @description 房源管理详情页 响应对象
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SourceManageResponse extends Response {
	
//	是否正在被审核
	private Boolean isAuditing;
	
//	当前审核状态
	private String presentStatus;
	
	// 房源编号：MY98686
	private int houseId;
	// 栋座：
	private String building;
	
	// 房型：2室2厅1卫
	// 2室
	private int bedroomSum;
	// 2厅
	private int livingRoomSum;
	// 1卫
	private int wcSum;
	
	// 室号：
	private String room;
	// 面积：
	private BigDecimal spaceArea;

//	主区
	private String estateName;
//	板块
	private String townName;
//	区域
	private String cityName;
	
	
//	出租或出售 sell、rent、notsell、notrent、
	private int houseState;//1出租，2出售，3即租又售，4即不租也不售
	private BigDecimal rentPrice;//出租价格
	private BigDecimal sellPrice;//出售价格
	private String actionTypeStr;//1发布，2，改盘，3举报，4轮询，5抽查
	
	
	
	private String status;
	private String sourceLogId;
	
	
//	价格
	private BigDecimal price;
//	当前联系人
	private List<String> houseContactTrue;
//	历史联系人
	private List<String> houseContactFalse;
	private List<Audit> auditList;
	private List<HouseHis> houseHisList;
	
	
	
	 

	@Data
	public static class Audit{
		
//		审核类型
		private String type;
//		审核完成时间
		private String finishDate;
//		审核状态
		private String auditState;
//		备注
		private String note;
		
		private int historyId;
		 
	}
	
	@Data
	public static class HouseHis{
		
//		发布类型
		private String logText;
//		创建时间
		private String addTime;
//		发布人
		private String userName;
		

	}
	
}
