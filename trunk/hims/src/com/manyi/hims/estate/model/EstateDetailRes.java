package com.manyi.hims.estate.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.manyi.hims.Response;
import com.manyi.hims.entity.aiwu.EstateImage;

@Data
@EqualsAndHashCode(callSuper = false)
public class EstateDetailRes extends Response{
	
	private int estateId;
	private String name;// 区域名（例如：上海、湖北、长宁、古北）
	private Date createTime;
	private String path;// 坐标路径 ，格式（json）：[[0,0],[1,1],[2,2]]
	private String remark;

	/*********************************** FK ***********************************/
	private int parentId;
	/*********************************** FK ***********************************/
	private int status;//1有效，2未审核，3无效, 4删除
	private String statusStr;
	private String serialCode;//对区域进行编号，按照层级关系叠加规则，例如：中国： 00001，北京:0000100001，上海: 0000100002，长宁: 000010000200001
	private int userId;//目前由谁新增的小区，将来可能会让用户新增片区，或者城镇，所以userId放在Aera里面
	
	
	private BigDecimal totalAcreage;// 小区占地面积
	private BigDecimal plotRatio;// 容积率
	private BigDecimal landscapingRatio;// 绿化率
	private int totalBuilding;// 楼数
	private int totalTruckSpace;// 车位数
	private String propertyDevelopers;// 开发商
	
	private BigDecimal strataFee;// 物业费用
	private String introduce;// 介绍
	
	/*********************************** FK ***********************************/
	private int managerId;// PropertyManager's Id
	/*********************************** FK ***********************************/
	private Date finishDate;//小区竣工时间
	private String road;//小区 多少路,多少弄
	private String nameAcronym;//小区名称首字母缩写
	
	/**
	 * 坐标 经度
	 */
	private String longitude;
	/**
	 * 坐标 纬度
	 */
	private String latitude;
	
	/**
	 * 坐标哈希
	 */
	private String axisHash;
	
	/**
	 *  小区边界范围图 放在 阿里云存储的key
	 */
	private String estateRangeImgKey;
	private String estateRangeImgKeyStr;
	
	/**
	 * 小区别名 小区有多个 需要定一个分隔符
	 */
	private String aliasName;
	
	/**
	 * 主门地址 (冗余字段,地址表里新增了地址类型)
	 */
	private String mainGateAddress;
	
	/**
	 * 主门坐标经度
	 */
	private String mainGateLongitude;
	
	/**
	 * 主门坐标纬度
	 */
	private String mainGateLatitude;
	
	/**
	 * 主门坐标哈希
	 */
	private String mainGateAxisHash;
	
	/**
	 * 主门街景link
	 */
	private String mainGateStreetSceneryLink;
	
	/**
	 * 建造年代 (1000-2500)
	 */
	private int constructDate;
	
	/**
	 * 小区类型 1住宅 2别墅 3住宅别墅混合
	 */
	private int type;
	private String typeStr;
	
	/**
	 * 地址 json
	 */
	private String address;
	/**
	 * 自划分 json
	 */
	private String subestate;
	/**
	 * 片区
	 */
	private int townId ;
	private String townName;
	/**
	 * 行政区
	 */
	private int areaId;
	private String areaName;
	/**
	 * 城市
	 */
	private int cityId;
	private String cityName;
	
	/**
	 * 相关图片
	 */
	List<EstateImageRes> images = new ArrayList<EstateImageRes>();
	

	/**
	 * 上传的阿里云的keys json
	 */
	private String imgkeys;
	
}
