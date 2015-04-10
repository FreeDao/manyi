package com.manyi.hims.house.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.manyi.hims.Response;
import com.manyi.hims.entity.HouseImageFile;
import com.manyi.hims.entity.HouseSupportingMeasures;

@Data
@EqualsAndHashCode(callSuper = false)
public class HouseDetailRes extends Response{
	public HouseDetailRes() {
		// TODO Auto-generated constructor stub
	}
	
	public HouseDetailRes(int errorCode, String message) {
		super();
		super.setErrorCode(errorCode);
		super.setMessage(message);
	}
	
	private int houseId;
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private int floor;// 楼层
	private String room;// 房号（例如：1304室，1004－1008室等）
	private int layers;// 总层高
	private BigDecimal coveredArea = new BigDecimal(0);// 建筑面积
	private BigDecimal spaceArea = new BigDecimal(0);// 内空面积
	private int houseRightId;// 产权	
	private Date updateTime;
	
	/*********************************** FK ***********************************/
	private int certificates;// 拥有的产证 0没有产证，1房产证，2土地证 1&2双证 和HouseCertificate的id进行&操作
	private int houseTypeId;// 类型（对应HouseType'Id）
	private int houseDirectionId;// 朝向（对应HouseDirection'Id）
	private int estateId;// 所属小区或楼盘（对应Estate'Id）
	private int mainOwnerId;// 主产权人ID(对应 Owner'ownerId)
	private String otherOwnersId;//其他产权人Id（对应 Owner'Id）格式：1,2,3,4，需要在程序中解析出id集合
	/*********************************** FK ***********************************/
	private String serialCode;//引用Area表中serialCode编号（具体小区的serialCode）
	private int decorateType; //1 毛坯，2 装修
	
	private int picNum; //图片数目
	private int currBDTaskId;//当前bdtask; 
	private int currUserTaskId;//当前userTask; 
	private int buildingId;//当前楼栋的id; 
	private int subEstateId;//子划分ID
	private String subEstateName;//子划分ID
	/**
	 * 别名  有多个 需要定一个分隔符
	 */
	private String aliasName;
	
	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫
	private int balconySum;// 几阳台
	
	private BigDecimal rentPrice;//出租价格
	private BigDecimal sellPrice;//出售价格
	private boolean isGotPrice;//是否到手价，标记sellPrice是否是到手价
	private Date rentFreeDate;// 可入住时间
	private Date rentTermDate;//租期(出租到期时间)
	private Date entrustDate;//委托时间
	private int houseState;//1出租，2出售，3即租又售，4即不租也不售
	private String houseStateStr;//1出租，2出售，3即租又售，4即不租也不售
	private int status;//状态，1审核通过,2审核中，3审核失败, 4无效/删除
	private String statusStr;//状态，1审核通过,2审核中，3审核失败, 4无效/删除
	
	private List<HouseImageFile> images =new ArrayList<HouseImageFile>();
	/**
	 * 上传的阿里云的keys json
	 */
	private String imgkeys;
	
	/**
	 * 房屋的配置信息
	 */
	HouseSupportingMeasures supporing = new HouseSupportingMeasures();
}
