package com.manyi.hims.rent.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.leo.common.Page;
import com.manyi.hims.Global;
import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseRecordEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.common.PublishHouseRequest;
import com.manyi.hims.rent.service.RentService;

/**
 * 出租 信息
 * 
 * @author tiger
 * 
 */
@Controller
@RequestMapping("/rest/rent")
@SessionAttributes(Global.SESSION_UID_KEY)
public class RentRestController extends RestController {

	@Autowired
	@Qualifier("rentService")
	private RentService rentService;

	public void setRentService(RentService rentService) {
		this.rentService = rentService;
	}

	/**
	 * 出租首页数据列表
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/indexList.rest", produces = "application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> indexList(HttpSession session) {
		final List<HouseEntity> entitys = this.rentService.indexList();
		Response result = new Response() {
			private List<HouseEntity> houseList = new ArrayList<HouseEntity>();
			{
				this.houseList = entitys;
			}

			public List<HouseEntity> getHouseList() {
				return houseList;
			}

			public void setHouseList(List<HouseEntity> houseList) {
				this.houseList = houseList;
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	
	/**
	 * 修改 发布 出租记录 信息
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updatePublishRentInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> updatePublishRentInfo(HttpSession session, @RequestBody PublishHouseRequest req) {
		final int resultNum= this.rentService.updatePublishRentInfo(req);
		Response result = new Response(){
			{
				if(resultNum == 0){
					//发布成功
				}
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	/**
	 * 发布出租 信息
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/publishRentInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> publishRentInfo(HttpSession session, @RequestBody PublishHouseRequest req) {
		final int resultNum= this.rentService.publishRentInfo(req);
		Response result = new Response(){
			{
				if(resultNum == 0){
					this.setMessage("提交成功，进入审核中");
					
				}
			}
			
			
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	/**
	 * 查看房源详情
	 * @param session
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/rentDetails.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> RentDetails(HttpSession session, @RequestBody updatePublishRequest params) {
		final HouseEntity houseEntity = rentService.rentDetails(params.getHouseId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		@SuppressWarnings("unused")
		Response result = new Response(){
			private int houseId;//房屋ID
			private String building;// 楼座编号（例如：22栋，22坐，22号）
			private String room;// 房号（例如：1304室，1004－1008室等）
			private BigDecimal spaceArea;// 内空面积
			private int bedroomSum;// 几房
			private int livingRoomSum;// 几厅
			private int wcSum;// 几卫
			
			private int estateId;//小区ID
			private String estateName;//小区名称
			private int areaId;//行政区ID
			private String areaName;//行政区name
			private Date publishDate;//发布时间
			private int sourceState;//审核状态
			private String sourceStateStr;//审核状态对应的文本
//			private String publishStr;//发布时间,格式化后的时间
			
			private BigDecimal price;//价格
			
			{
				BeanUtils.copyProperties(houseEntity, this);
			}

			public int getHouseId() {
				return houseId;
			}

			public void setHouseId(int houseId) {
				this.houseId = houseId;
			}

			public String getBuilding() {
				return building;
			}

			public void setBuilding(String building) {
				this.building = building;
			}

			public String getRoom() {
				return room;
			}

			public void setRoom(String room) {
				this.room = room;
			}

			public BigDecimal getSpaceArea() {
				return spaceArea;
			}

			public void setSpaceArea(BigDecimal spaceArea) {
				this.spaceArea = spaceArea;
			}

			public int getBedroomSum() {
				return bedroomSum;
			}

			public void setBedroomSum(int bedroomSum) {
				this.bedroomSum = bedroomSum;
			}

			public int getLivingRoomSum() {
				return livingRoomSum;
			}

			public void setLivingRoomSum(int livingRoomSum) {
				this.livingRoomSum = livingRoomSum;
			}

			public int getWcSum() {
				return wcSum;
			}

			public void setWcSum(int wcSum) {
				this.wcSum = wcSum;
			}

			public int getEstateId() {
				return estateId;
			}

			public void setEstateId(int estateId) {
				this.estateId = estateId;
			}

			public String getEstateName() {
				return estateName;
			}

			public void setEstateName(String estateName) {
				this.estateName = estateName;
			}

			public int getAreaId() {
				return areaId;
			}

			public void setAreaId(int areaId) {
				this.areaId = areaId;
			}

			public String getAreaName() {
				return areaName;
			}

			public void setAreaName(String areaName) {
				this.areaName = areaName;
			}

			public Date getPublishDate() {
				return publishDate;
			}

			public void setPublishDate(Date publishDate) {
				this.publishDate = publishDate;
			}


			public BigDecimal getPrice() {
				return price;
			}

			public void setPrice(BigDecimal price) {
				this.price = price;
			}

			public int getSourceState() {
				return sourceState;
			}

			public void setSourceState(int sourceState) {
				this.sourceState = sourceState;
			}

			public String getSourceStateStr() {
				return sourceStateStr;
			}

			public void setSourceStateStr(String sourceStateStr) {
				this.sourceStateStr = sourceStateStr;
			}


//			public int getPublishCount() {
//				return publishCount;
//			}
//
//			public void setPublishCount(int publishCount) {
//				this.publishCount = publishCount;
//			}

			
		};
		dr.setResult(result);
		return dr;
	}
	
	/**
	 * 发布记录出租详情
	 * @param session
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/rentRecordDetails.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> rentRecordDetails(HttpSession session, @RequestBody RecordPublishRequest params) {
		final HouseRecordEntity houseEntity = rentService.rentRecordDetails(params.getLogId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		@SuppressWarnings("unused")
		Response result = new Response(){
			private int houseId;//房屋ID
			private String building;// 楼座编号（例如：22栋，22坐，22号）
			private String room;// 房号（例如：1304室，1004－1008室等）
			private BigDecimal spaceArea;// 内空面积
			private int bedroomSum;// 几房
			private int livingRoomSum;// 几厅
			private int wcSum;// 几卫
			
			private int estateId;//小区ID
			private String estateName;//小区名称
			private int areaId;//行政区ID
			private String areaName;//行政区name
			private Date publishDate;//发布时间
			private int sourceState;//审核状态
			private String sourceStateStr;//审核状态对应的文本
			private int sourceLogTypeId; //发布状态
			private String sourceLogTypeStr; //发布状态对应的文本
//			private String publishStr;//发布时间,格式化后的时间
			
			private BigDecimal price;//价格
			
			{
				BeanUtils.copyProperties(houseEntity, this);
			}

			public int getHouseId() {
				return houseId;
			}

			public void setHouseId(int houseId) {
				this.houseId = houseId;
			}

			public String getBuilding() {
				return building;
			}

			public void setBuilding(String building) {
				this.building = building;
			}

			public String getRoom() {
				return room;
			}

			public void setRoom(String room) {
				this.room = room;
			}

			public BigDecimal getSpaceArea() {
				return spaceArea;
			}

			public void setSpaceArea(BigDecimal spaceArea) {
				this.spaceArea = spaceArea;
			}

			public int getBedroomSum() {
				return bedroomSum;
			}

			public void setBedroomSum(int bedroomSum) {
				this.bedroomSum = bedroomSum;
			}

			public int getLivingRoomSum() {
				return livingRoomSum;
			}

			public void setLivingRoomSum(int livingRoomSum) {
				this.livingRoomSum = livingRoomSum;
			}

			public int getWcSum() {
				return wcSum;
			}

			public void setWcSum(int wcSum) {
				this.wcSum = wcSum;
			}

			public int getEstateId() {
				return estateId;
			}

			public void setEstateId(int estateId) {
				this.estateId = estateId;
			}

			public String getEstateName() {
				return estateName;
			}

			public void setEstateName(String estateName) {
				this.estateName = estateName;
			}

			public int getAreaId() {
				return areaId;
			}

			public void setAreaId(int areaId) {
				this.areaId = areaId;
			}

			public String getAreaName() {
				return areaName;
			}

			public void setAreaName(String areaName) {
				this.areaName = areaName;
			}

			public Date getPublishDate() {
				return publishDate;
			}

			public void setPublishDate(Date publishDate) {
				this.publishDate = publishDate;
			}


			public BigDecimal getPrice() {
				return price;
			}

			public void setPrice(BigDecimal price) {
				this.price = price;
			}

			public int getSourceState() {
				return sourceState;
			}

			public void setSourceState(int sourceState) {
				this.sourceState = sourceState;
			}

			public String getSourceStateStr() {
				return sourceStateStr;
			}

			public void setSourceStateStr(String sourceStateStr) {
				this.sourceStateStr = sourceStateStr;
			}

			public int getSourceLogTypeId() {
				return sourceLogTypeId;
			}

			public void setSourceLogTypeId(int sourceLogTypeId) {
				this.sourceLogTypeId = sourceLogTypeId;
			}

			public String getSourceLogTypeStr() {
				return sourceLogTypeStr;
			}

			public void setSourceLogTypeStr(String sourceLogTypeStr) {
				this.sourceLogTypeStr = sourceLogTypeStr;
			}

			
//			public int getPublishCount() {
//				return publishCount;
//			}
//
//			public void setPublishCount(int publishCount) {
//				this.publishCount = publishCount;
//			}

			
		};
		dr.setResult(result);
		return dr;
	}
	/**
	 * 检查房源是否已经出租
	 * @param params
	 * @return
	 */
//	@RequestMapping(value = "/chenkHoustIsRent.rest", produces="application/json")
//	@ResponseBody
//	public DeferredResult<Response> chenkHoustIsRent(@RequestBody CheckRentResquest params) {
//		DeferredResult<Response> dr = new DeferredResult<Response>();
//		final RantInfoResponse rant = rentService.chenkHoustIsRent(params.getEstateId(), params.getBuilding(), params.getRoom());
//		@SuppressWarnings("unused")
//		Response result = new Response(){
//			private int houseId;
//			
//			{
//				BeanUtils.copyProperties(rant, this);
//			}
//			public int getHouseId() {
//				return houseId;
//			}
//
//			public void setHouseId(int houseId) {
//				this.houseId = houseId;
//			}
//			
//		};
//		dr.setResult(result);
//		return dr;
//	}
	public static class CheckRentResquest{
		private int estateId;
		private String building;
		private String room;
		public int getEstateId() {
			return estateId;
		}
		public void setEstateId(int estateId) {
			this.estateId = estateId;
		}
		public String getBuilding() {
			return building;
		}
		public void setBuilding(String building) {
			this.building = building;
		}
		public String getRoom() {
			return room;
		}
		public void setRoom(String room) {
			this.room = room;
		}
	}
	public static class updatePublishRequest{
		private int houseId;
		public int getHouseId() {
			return houseId;
		}
		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}
	}
	public static class RecordPublishRequest{
		private int logId;

		public int getLogId() {
			return logId;
		}

		public void setLogId(int logId) {
			this.logId = logId;
		}
		
	}
	
}
