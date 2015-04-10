package com.manyi.hims.sell.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import lombok.Data;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.check.model.CSSingleResponse.HouseResourceResponse;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.common.PublishHouseRequest;
import com.manyi.hims.sell.service.SellService;
import com.manyi.hims.sell.service.SellService.SellInfoResponse;

/**
 * 出售 信息
 * @author tiger
 *
 */
@Controller
@RequestMapping("/rest/sell")
@SessionAttributes(Global.SESSION_UID_KEY)
public class SellRestController extends RestController {
	
	private Logger logger = LoggerFactory.getLogger(SellRestController.class);
	
	@Autowired
	@Qualifier("sellService")
	private SellService sellService;
	
	public void setSellService(SellService sellService) {
		this.sellService = sellService;
	}

	/**
	 * 出售 首页数据列表
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/indexList.rest", produces="application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> indexList(HttpSession session,@RequestBody IndexRequest params) {
		final List<HouseEntity> entitys= this.sellService.indexList(params.getCityId());
		logger.info(JSONArray.fromObject(entitys).toString());
		Response result = new Response(){
			private List<HouseEntity> houseList = new ArrayList<HouseEntity>();
			{
				this.houseList= entitys;
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
	 * 出售 页面列表 通过不同的参数搜索数据
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/searchHouseResources.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> searchHouseResources(HttpSession session, @RequestBody HouseSearchRequest req) {
		final Page<HouseResourceResponse> entitys= this.sellService.searchHouseResources(req);
		Response result = new Response(){
			private List<HouseResourceResponse> houseList = new ArrayList<HouseResourceResponse>();
			{
				if (entitys != null && entitys.getRows() != null && entitys.getRows().size() > 0) {
					this.houseList= entitys.getRows();
				}
			}
			public List<HouseResourceResponse> getHouseList() {
				return houseList;
			}
			public void setHouseList(List<HouseResourceResponse> houseList) {
				this.houseList = houseList;
			}
			
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	/**
	 * 发布出售 信息
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/publishSellInfo.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> publishSellInfo(HttpSession session, @RequestBody PublishHouseRequest req) {
		final int resultNum= this.sellService.publishSellInfo(req);
		Response result = new Response(){
			{
				System.out.println(resultNum);
				if (resultNum == 0) {
					this.setMessage("提交成功，进入审核中");
				}
			}
			
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
//	/**
//	 * 修改 发布 出售记录 信息
//	 * @param session
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(value = "/updatePublishSellInfo.rest", produces="application/json")
//	@ResponseBody
//	public DeferredResult<Response> updatePublishSellInfo(HttpSession session, @RequestBody PublishHouseRequest req) {
//		final int resultNum= this.sellService.updatePublishSellInfo(req);
//		Response result = new Response(){
//			{
//				if(resultNum == 0){
//					//发布成功
//					this.setMessage("提交成功，进入审核中");
//				}
//			}
//		};
//		DeferredResult<Response> dr = new DeferredResult<Response>();
//		dr.setResult(result);
//		return dr;
//	}
//	
//	/**
//	 * 查看房源审核详情
//	 */
//	@RequestMapping(value = "/sellRecordDetails.rest", produces="application/json")
//	@ResponseBody
//	public DeferredResult<Response> sellRecordDetails(HttpSession session, @RequestBody SellRecordRequest params) {
//		final HouseEntity houseEntity = sellService.sellDetails(params.getLogId());
//		SellRecordResponce result = new SellRecordResponce();
//		BeanUtils.copyProperties(houseEntity, result);
//		
//		SourceHost sourceHost = sellService.getSellSourceHost(houseEntity.getSourceLogId());
//		result.setHostName(sourceHost.getHostName());
//		result.setHoustMobile(sourceHost.getHostMobile());
//		return CommonUtils.deferredResult(result);
//	}
//	
//	public static class SellRecordRequest{
//		private int logId;
//
//		public int getLogId() {
//			return logId;
//		}
//
//		public void setLogId(int logId) {
//			this.logId = logId;
//		}
//	}
	
	/**
	 * 查看房源详情
	 * @param session
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/sellDetails.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> sellDetails(HttpSession session, @RequestBody updatePublishRequest params) {
		final HouseEntity houseEntity = sellService.sellDetails(params.getHouseId());
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
			private String subEstateName;//子划分名称
			private int areaId;//行政区ID
			private String areaName;//行政区name
			private Date publishDate;//发布时间
			private BigDecimal price;//价格
			private int sourceState;//审核状态
			private String townName;//小区所属片区name
			private String sourceStateStr;//审核状态对应的文本
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

			public String getSubEstateName() {
				return subEstateName;
			}

			public void setSubEstateName(String subEstateName) {
				this.subEstateName = subEstateName;
			}

			public String getTownName() {
				return townName;
			}

			public void setTownName(String townName) {
				this.townName = townName;
			}

		};
		dr.setResult(result);
		return dr;
	}
	/**
	 * 检查房源是否已经出售
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/chenkHoustIsSell.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> chenkHoustIsSell(@RequestBody CheckSellResquest params) {
		DeferredResult<Response> dr = new DeferredResult<Response>();
		final SellInfoResponse sell = sellService.chenkHoustIsSell(params.getEstateId(), params.getBuilding(), params.getRoom(),params.getHouseType());
		@SuppressWarnings("unused")
		Response result = new Response(){
			private int houseId;
			
			{
				BeanUtils.copyProperties(sell, this);
			}
			public int getHouseId() {
				return houseId;
			}

			public void setHouseId(int houseId) {
				this.houseId = houseId;
			}
			
		};
		dr.setResult(result);
		return dr;
	}
	public static class CheckSellResquest{
		private int estateId;
		private String building;
		private String room;
		private int houseType;//发布类型
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
		public int getHouseType() {
			return houseType;
		}
		public void setHouseType(int houseType) {
			this.houseType = houseType;
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
	@Data
	public static class IndexRequest{
		private int cityId;
	}
	
}
