package com.manyi.hims.common.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.leo.common.beanutil.BeanUtils;
import com.leo.common.util.DateUtil;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.area.service.AreaService;
import com.manyi.hims.common.AutoUpdateResponse;
import com.manyi.hims.common.CommonConst;
import com.manyi.hims.common.FeedbackRequest;
import com.manyi.hims.common.HouseDetailInfo;
import com.manyi.hims.common.RecordHouseRequest;
import com.manyi.hims.common.RecordRequest;
import com.manyi.hims.common.RecordResponse;
import com.manyi.hims.common.service.CommonService;
import com.manyi.hims.common.service.CommonService.SellInfoResponse;
import com.manyi.hims.entity.Area;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.HouseResourceViewCount;
import com.manyi.hims.rent.RentConst;
import com.manyi.hims.rent.service.RentService;
import com.manyi.hims.sell.service.SellService;
import com.manyi.hims.util.CommonUtils;
import com.manyi.hims.util.EntityUtils;

/**
 * 公共 模块 1.获取区域板块 片区信息 2.由小区名字 模糊搜索小区
 * 
 * @author tiger
 * 
 */
@Controller
@RequestMapping("/rest/common")
@SessionAttributes(Global.SESSION_UID_KEY)
public class CommonRestController extends RestController {

	final String CITY_VERSION_CODE = "city_version_code";
	static Map<String, String> versionMap = new HashMap<String, String>();

	private static Logger logger = LoggerFactory.getLogger(CommonRestController.class);

	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;

	@Autowired
	private AreaService areaService;

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Autowired
	@Qualifier("sellService")
	private SellService sellService;

	public void setSellService(SellService sellService) {
		this.sellService = sellService;
	}

	@Autowired
	@Qualifier("rentService")
	private RentService rentService;

	public void setRentService(RentService rentService) {
		this.rentService = rentService;
	}

	@RequestMapping(value = "/test.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> test(HttpSession session) {
		commonService.getSerialCode4Area(1);
		return CommonUtils.deferredResult(new Response());
	}

	/**
	 * 意见反馈 API
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/feedback.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> feedback(HttpSession session, @RequestBody FeedbackRequest req) {
		boolean result = commonService.insertFeedback(req.getUid(), req.getContext());
		Response res = new Response();
		res.setMessage(result ? "提交成功" : "提交失败");
		return CommonUtils.deferredResult(res);
	}

	/**
	 * 修改 发布出租记录,发布出售记录 信息
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateRecordInfo.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> updateRecordInfo(HttpSession session, @RequestBody RecordHouseRequest req) {
		Response result = new Response();
		int resultNum = 0;
		if (req.getSourceLogTypeId() == EntityUtils.HouseStateEnum.RENT.getValue()) {
			resultNum = rentService.updatePublishRentInfo(req);
		} else if (req.getSourceLogTypeId() == EntityUtils.HouseStateEnum.SELL.getValue()) {
			resultNum = sellService.updatePublishSellInfo(req);
		}
		if (resultNum == 0) {
			result.setMessage("提交成功，进入审核中");
		}
		return CommonUtils.deferredResult(result);
	}

	/**
	 * 查看房源审核详情
	 */
	@RequestMapping(value = "/recordDetails.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> recordDetails(HttpSession session, @RequestBody RecordRequest params) {
		// if (params.getTypeId() == 0) {
		// throw new LeoFault(RentConst.TYPE_ERROR120009);
		// }
		RecordResponse result = new RecordResponse();
		if (params.getTypeId() == EntityUtils.ActionTypeEnum.ADD_ESTATE.getValue()) {
			if (params.getHistoryId()== 0) {
				throw new LeoFault(RentConst.HISTORY_ERROR120011);
			}
			result = commonService.loadEstateInfo(params.getHistoryId());
		} else {
			if (params.getHouseId() == 0) {
				throw new LeoFault(RentConst.Rent_ERROR120006);
			}
			if (params.getHistoryId() == 0) {
				throw new LeoFault(RentConst.HISTORY_ERROR120011);
			}
			result = commonService.loadInfoLog(params.getHouseId(), params.getHistoryId());
		}
		return CommonUtils.deferredResult(result);
	}

	/**
	 * 增量更新城市列表
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/incrementalCity.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> incrementalCity(HttpSession session, @RequestBody IncrementalCityRequest req) {

		final List<City> list = commonService.getAllCityList();
		Collections.sort(list, new Comparator<City>() {

			@Override
			public int compare(City o1, City o2) {
				return o1.getCreateTime().getTime() > o2.getCreateTime().getTime() ? -1 : 1;
			}

		});
		Date cityDate = list.get(0).getCreateTime();
		String _version = DateUtil.formatDate("yyyyMMddHHmmss", cityDate);
		boolean isUpdate = StringUtils.equals(_version, req.version);
		CityResponse cityResponse = new CityResponse();
		cityResponse.setCityList(!isUpdate ? list : Collections.<City> emptyList());
		cityResponse.setUpdate(!isUpdate);
		cityResponse.setVersion(_version);
		return CommonUtils.deferredResult(cityResponse);
	}

	public static class CityResponse extends Response {

		private String version;

		private List<City> cityList;

		private boolean isUpdate;

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public boolean isUpdate() {
			return isUpdate;
		}

		public void setUpdate(boolean isUpdate) {
			this.isUpdate = isUpdate;
		}

		public List<City> getCityList() {
			return cityList;
		}

		public void setCityList(List<City> cityList) {
			this.cityList = cityList;
		}
	}

	/**
	 * 通过名字模糊查询出来小区列表
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findEstateByName.rest", produces = "application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> findEstateByName(HttpSession session, @RequestBody EstateRequest req) {
		if (req.getCityId() == 0) {
			// 城市id 不能为空
			throw new LeoFault(CommonConst.COMMON_ESTATE_9000002);
		}
		logger.info(JSONObject.fromObject(req).toString());
		final List<EstateResponse> lists = this.commonService.findEstateByName(areaService.getSerialCodeByAreaId(req.getCityId()), req.getName());
		logger.info(JSONArray.fromObject(lists).toString());
		Response result = new Response() {
			List<EstateResponse> estateList = new ArrayList<EstateResponse>();
			{
				this.estateList = lists;
			}

			public List<EstateResponse> getEstateList() {
				return estateList;
			}

			public void setEstateList(List<EstateResponse> estateList) {
				this.estateList = estateList;
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	/**
	 * 通过小区id查询 子划分列表
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findSubEstateListByEstateId.rest", produces = "application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> findSubEstateListByEstateId(@RequestBody SubEstateRequest req) {
		if (req.getEstateId() == 0) {
			// 小区id不能为空，，，，，系统异常,请检查数据
			throw new LeoFault(CommonConst.COMMON_9000051);
		}
		final List<SubEstateResponse> lists = this.commonService.getSubEstateResponseList(req.getEstateId());
		
		Response result = new Response() {
			List<SubEstateResponse> subEstateList = new ArrayList<SubEstateResponse>();
			{
				this.subEstateList = lists;
			}
			public List<SubEstateResponse> getSubEstateList() {
				return subEstateList;
			}
			public void setSubEstateList(List<SubEstateResponse> subEstateList) {
				this.subEstateList = subEstateList;
			}

			
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}

	/**
	 * 判断是否需要自动更新
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/automaticUpdates.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> automaticUpdates(HttpSession session, @RequestBody VersionRequest versionRequest) {
		final AutoUpdateResponse result = commonService.automaticUpdates(versionRequest.getVersion());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;

	}

	/**
	 * parentid
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findAreaByParentId.rest", produces = "application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> findAreaByParentId(HttpSession session, @RequestBody AreaRequest req) {
		final List<Area> lists = this.commonService.findAreaByParentId(req.getParentId(), req.isFlag());
		Response result = new Response() {
			List<AreaResponse> areaList = new ArrayList<AreaResponse>();
			{
				if (lists != null && lists.size() > 0) {
					for (int i = 0; i < lists.size(); i++) {
						Area area = lists.get(i);
						AreaResponse ar = new AreaResponse();
						BeanUtils.copyProperties(ar, area);
						this.areaList.add(ar);
					}
				}
			}

			public List<AreaResponse> getAreaList() {
				return areaList;
			}

			public void setAreaList(List<AreaResponse> areaList) {
				this.areaList = areaList;
			}

		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}

	/**
	 * @date 2014年4月29日 下午3:33:56
	 * @author Tom
	 * @description 通过AreaId 得到 区域信息
	 * 
	 *              for app client 2个位置的请求： 一个是按照（页面位于发布出租、出售上方）区域areaId进行查询，其中第一个节点是全部 一个是按照（页面位于用户注册的第二步，完善您的信息）工作区域查询，其中不需要全部的阶段
	 * 
	 * @param hasAll
	 *            true 包含全部节点，false 不包含
	 */
	@RequestMapping(value = "/findAreaByAreaId.rest", produces = "application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> findAreaByAreaId(HttpSession session, @RequestBody AreaRequest4App req) {
		final List<Area> lists = commonService.findAreaBySerialCode(areaService.getSerialCodeByAreaId(req.getAreaId()));
		final AreaRequest4App inReq = req;

		Response result = new Response() {
			List<AreaResponse4App> areaList = new ArrayList<AreaResponse4App>();
			{
				if (!inReq.notHasAll) {
					AreaResponse4App areaResponse = new AreaResponse4App();
					areaResponse.setName("全部");
					areaResponse.setFlag(true);
					// areaResponse.setSerialCode(inReq.getSerialCode());
					areaResponse.setAreaId(inReq.getAreaId());
					this.areaList.add(areaResponse);
				}
				if (lists != null && lists.size() > 0) {
					for (int i = 0; i < lists.size(); i++) {
						Area area = lists.get(i);
						AreaResponse4App ar = new AreaResponse4App();
						ar.setFlag(false);
						BeanUtils.copyProperties(ar, area);
						this.areaList.add(ar);
					}
				}
			}

			public List<AreaResponse4App> getAreaList() {
				return areaList;
			}

			public void setAreaList(List<AreaResponse4App> areaList) {
				this.areaList = areaList;
			}

		};

		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}

	@RequestMapping(value = "/getHouseCount.rest", produces = "application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> getHouseCount(@RequestBody HouseCountRequest params) {
		final HouseResourceViewCount view = commonService.houseCount(params.getUid()/*,params.getHouseId()*/);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		Response result = new Response() {
			private int count;
			private int monthCount;

			{
				count = view.getPublishCount();
				monthCount = view.getPublishMonthCount();
			}

			public int getCount() {
				return count;
			}

			public void setCount(int count) {
				this.count = count;
			}
			
			public int getMonthCount() {
				return count;
			}

			public void setMonthCount(int monthCount) {
				this.monthCount = monthCount;
			}

		};

		dr.setResult(result);
		return dr;
	}

	/**
	 * 发布（出租、出售）第一步
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/release.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> release(@RequestBody CheckSellResquest params) {
		
		logger.info(JSONObject.fromObject(params).toString());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		final SellInfoResponse sell = commonService.chenkHoustIsSell(params.getSubEstateId(), params.getBuilding(), params.getRoom(),
				params.getHouseType());
		@SuppressWarnings("unused")
		Response result = new Response() {
			private int houseId;

			{
				this.setHouseId(sell.getHouseId());

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

	/**
	 * 获取房源信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/getHouseDetailInfo.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> getHouseDetailInfo(@RequestBody HouseDetailInfoRequest params) {
		
		logger.info(JSONObject.fromObject(params).toString());
		final List<HouseDetailInfo> result = commonService.getHouseDetailInfo(params.getType(), params.getHouseId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		Response response = new Response(){
			@Getter
			@Setter
			private List<HouseDetailInfo> houseList = new ArrayList<HouseDetailInfo>();
			{
				houseList = result ;
			}
		};
		dr.setResult(response);
		return dr;
	}
	@RequestMapping(value = "/sendSMS.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> sendSMS(@RequestBody SendSMSRequest params) {
		
		logger.info(JSONObject.fromObject(params).toString());
		commonService.sendSMS(params.getMobile(), params.getContent());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	@Data
	public static class SendSMSRequest{
		private String mobile;
		private String content;
	}
	@Data
	public static class SubEstateRequest{
		private int estateId;
	}
	@Data
	public static class HouseDetailInfoRequest{
		private int type;
		private int[] houseId;
	}
	@Data
	public static class CheckSellResquest {
		private int subEstateId;
		private String building;
		private String room;
		private int houseType;// 发布类型 1出租，2出售 3租售
	}

	@Data
	public static class HouseCountRequest {
		private int uid;
//		private int houseId;
	}

	@Data
	public static class EstateRequest {
		private String name;
		private int cityId;
	}

	@Data
	public static class EstateResponse {
		private int estateId;
		private String name;
		private List<SubEstateResponse> subEstatelList;
	}
	
	@Data
	public static class SubEstateResponse {
		private int subEstateId;
		private String subEstateName;
	}

	@Data
	public static class AreaRequest4App {
		// 是否包含全部节点
		private boolean notHasAll;
		private int areaId;
	}

	@Data
	public static class AreaResponse4App {
		private int areaId;
		private String name;
		private boolean flag;// 是否子节点 ，true,是；false否

	}

	/**
	 * parentid
	 * 
	 * @author tiger
	 * 
	 */
	public static class AreaRequest {
		private int parentId;
		private boolean flag = true;

		public int getParentId() {
			return parentId;
		}

		public void setParentId(int parentId) {
			this.parentId = parentId;
		}

		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

	}

	/**
	 * @author Tom
	 *
	 */
	@Data
	public static class VersionRequest {
		private String version;
	}

	public static class AreaResponse {
		private int areaId;
		private int parentId;
		private String name;

		public int getAreaId() {
			return areaId;
		}

		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}

		public int getParentId() {
			return parentId;
		}

		public void setParentId(int parentId) {
			this.parentId = parentId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class IncrementalCityRequest {
		@NotEmpty(message = "{ec_100003}")
		private int uid;

		@NotEmpty(message = "{ec_100003}")
		private String version;

		public int getUid() {
			return uid;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}
	}
}
