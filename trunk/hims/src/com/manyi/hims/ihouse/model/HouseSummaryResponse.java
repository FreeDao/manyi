package com.manyi.hims.ihouse.model;

import com.manyi.hims.Response;

/**
 * 房屋描述简介
 * @author shenyamin
 *
 */
public class HouseSummaryResponse extends Response{
	//房屋ID
	private int houseId;
	//数据库rentPrice为bigDecimal类型，保留两位小数字。服务端把decimal类型*10000转换成int，发送到客户端。
	private long rentPrice;
	private int bedrooms;//房间数
	private int livingromms;//客厅数
	private int washingrooms;//卫生间数
	private int balconies;//阳台数
	private int pictures;//图片数
	//其图片显示顺序为：
	//客厅图 > 主卧室图 > 第1次卧图 > 第2次卧图> 第3次卧图（以及第4/第5等）> 
	//主阳台图 > 第1次阳台图（以及第2/第3等）> 厨房图 > 主卫生间图 > 第1次卫生间图（以及第2/第3等） >  其他室内图
	private String pictureUrl;//图片地址，多个地址以";"隔开
	private String zoonName;//小区名称
	private String decorationType;//1 毛坯，2 装修
	private String publicDate;//发布时间:当天验证过的:显示"今天发布" ;其他情况:均显示"x天前发布" 
	private String floorType;//楼层显示:高，中，低
	private boolean favorite = false;//是否收藏
	private String houseArea;//房屋地址:浦东张江，不带详细地址
	
	
	public long getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(long rentPrice) {
		this.rentPrice = rentPrice;
	}
	public int getBedrooms() {
		return bedrooms;
	}
	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}
	public int getLivingromms() {
		return livingromms;
	}
	public void setLivingromms(int livingromms) {
		this.livingromms = livingromms;
	}
	public int getWashingrooms() {
		return washingrooms;
	}
	public void setWashingrooms(int washingrooms) {
		this.washingrooms = washingrooms;
	}
	public int getBalconies() {
		return balconies;
	}
	public void setBalconies(int balconies) {
		this.balconies = balconies;
	}
	public int getPictures() {
		return pictures;
	}
	public void setPictures(int pictures) {
		this.pictures = pictures;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getZoonName() {
		return zoonName;
	}
	public void setZoonName(String zoonName) {
		this.zoonName = zoonName;
	}
	public String getDecorationType() {
		return decorationType;
	}
	public void setDecorationType(int decorationType) {
		switch(decorationType){
		case 0:
			this.decorationType = "毛坯";
		case 1:
			this.decorationType = "装修";
		default:
			this.decorationType = "";
	}
	}
	public String getPublicDate() {
		return publicDate;
	}
	public void setPublicDate(String publicDate) {
		this.publicDate = publicDate;
	}
	public String getFloorType() {
		return floorType;
	}
	public void setFloorType(int floorType) {
		if(floorType >0 && floorType <= 6){
			this.floorType = "低层";
		} else if(floorType >= 7 && floorType <= 12){
			this.floorType = "中层";
		} else if(floorType >= 13){
			this.floorType = "高层";
		} else {
			this.floorType = "";
		}
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public String getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	
	
}
