package com.manyi.hims.estate.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Global;
import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.area.service.AreaService;
import com.manyi.hims.common.service.CommonService;
import com.manyi.hims.estate.model.AddEstateRequest;
import com.manyi.hims.estate.model.EstateRequest;
import com.manyi.hims.estate.model.EstateResponse;
import com.manyi.hims.estate.model.EstateVerifyReq;
import com.manyi.hims.estate.service.EstateService;
import com.manyi.hims.util.CommonUtils;

/**
 * 小区 管理模块
 * 
 * @author tiger
 * 
 */
@Controller
@RequestMapping("/rest/estate")
@SessionAttributes(Global.SESSION_UID_KEY)
public class EstateRestController extends RestController {

	@Autowired
	@Qualifier("estateService")
	private EstateService estateService;

	public void setEstateService(EstateService estateService) {
		this.estateService = estateService;
	}

	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;

	@Autowired
	private AreaService areaService;

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	/**
	 * 小区详情
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/detailEstate.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> detailEstate(HttpSession session, @RequestBody EstateRequest req) {
		EstateResponse estateResponse = estateService.detailEsate(req.getAreaId());
		final String _address = estateResponse.getAreaRoad();
		return CommonUtils.deferredResult(new Response(){
			String estateRoad = new String();
			
			{
				estateRoad = _address;
			}

			public String getEstateRoad() {
				return estateRoad;
			}

			public void setEstateRoad(String estateRoad) {
				this.estateRoad = estateRoad;
			}
		});
	}
	
	/**
	 * 编辑小区
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editEstate.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> editEstate(HttpSession session, @RequestBody EstateRequest req) {
		Response response = estateService.editEsate(req.getEstateName(), req.getAreaId(), req.getAreaRoad());
		return CommonUtils.deferredResult(response);
	}
	
	/**
	 * 新增加小区
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addEstate.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> addEstate(HttpSession session, @RequestBody AddEstateRequest req) {
		String serialCode = commonService.getSerialCode4Area(req.getTownId());
		Response response = this.estateService.addEsate(req.getEstateName(), req.getTownId(), req.getAreaId(), req.getAreaRoad(),serialCode);
		return CommonUtils.deferredResult(response);
	}

	/**
	 * 房源信息 列表
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/estateList.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<PageResponse> estateList(HttpSession session, @RequestBody EstateReq req) {
		final PageResponse<EstateRes> lists = this.estateService.estateList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}

	/**
	 * 新增小区 列表
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/estateLogList.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<PageResponse> estateLogList(HttpSession session, @RequestBody EstateReq req) {
		final PageResponse<EstateRes> lists = this.estateService.estateLogList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}

	/**
	 * 小区审核状态
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/estateVerify.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> estateVerify(@RequestBody EstateVerifyReq req) {
		this.estateService.estateVerify(req);
		Response result = new EstateResponse();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}

	@RequestMapping(value = "/checkEstate.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> checkEstate(@RequestBody CheckEstateStatusRequest req) {
		estateService.checkEstate(req.getEstateId(), req.getStatus());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}

	public static class CheckEstateStatusRequest {
		private int status;
		private int estateId;

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getEstateId() {
			return estateId;
		}

		public void setEstateId(int estateId) {
			this.estateId = estateId;
		}
	}

	public static class EstateReq {
		private int page = 1;// 当前第几页
		private int rows = 20;// 每一页多少
		private String orderby = " ";// 排序规则
		private String estateName;// 小区名字
		private int areaId;// 区域ID
		private int parentId;// 父区域ID
		private int sourceState;// 审核状态

		public int getPage() {
			return page;
		}

		public void setPage(int page) {
			this.page = page;
		}

		public int getRows() {
			return rows;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}

		public String getOrderby() {
			return orderby;
		}

		public void setOrderby(String orderby) {
			this.orderby = orderby;
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

		public int getParentId() {
			return parentId;
		}

		public void setParentId(int parentId) {
			this.parentId = parentId;
		}

		public int getSourceState() {
			return sourceState;
		}

		public void setSourceState(int sourceState) {
			this.sourceState = sourceState;
		}

	}

	public static class EstateRes {
		private int logId;// 记录id
		private int areaId;// 区域
		private String areaName;
		private int townId;// 片区
		private String townName;
		private int estateId;// 小区
		private String estateName;
		private String road;
		private Date publishDate;// 发布 出售时间
		private String publishDateStr;
		private int sourceState;// 审核状态
		private String sourceStateStr;
		private int publishId;// 发布人
		private String publishName;
		private int sellNum;// 在售数量
		private int rentNum;// 在租数量
		private int houseNum;// 房源数量

		public String getRoad() {
			return road;
		}

		public void setRoad(String road) {
			this.road = road;
		}

		public int getSellNum() {
			return sellNum;
		}

		public void setSellNum(int sellNum) {
			this.sellNum = sellNum;
		}

		public int getRentNum() {
			return rentNum;
		}

		public void setRentNum(int rentNum) {
			this.rentNum = rentNum;
		}

		public int getHouseNum() {
			return houseNum;
		}

		public void setHouseNum(int houseNum) {
			this.houseNum = houseNum;
		}

		public Date getPublishDate() {
			return publishDate;
		}

		public void setPublishDate(Date publishDate) {
			this.publishDate = publishDate;
			try {
				if(publishDate != null){
					this.publishDateStr = new SimpleDateFormat("yyyy-MM-dd").format(publishDate);
				}else{
					this.publishDateStr="";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public int getSourceState() {
			return sourceState;
		}

		public void setSourceState(int sourceState) {
			// 审核状态 1 审核通过 2 审核中 3 审核失败
			this.sourceState = sourceState;
			switch (sourceState) {
			case 1:
				this.sourceStateStr = "审核成功";
				break;
			case 2:
				this.sourceStateStr = "未审核";
				break;
			case 3:
				this.sourceStateStr = "审核失败";
				break;

			default:
				break;
			}
		}

		public int getPublishId() {
			return publishId;
		}

		public void setPublishId(int publishId) {
			this.publishId = publishId;
		}

		public String getPublishDateStr() {
			return publishDateStr;
		}

		public String getSourceStateStr() {
			return sourceStateStr;
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

		public int getTownId() {
			return townId;
		}

		public void setTownId(int townId) {
			this.townId = townId;
		}

		public String getTownName() {
			return townName;
		}

		public void setTownName(String townName) {
			this.townName = townName;
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

		public int getLogId() {
			return logId;
		}

		public void setLogId(int logId) {
			this.logId = logId;
		}

		public String getPublishName() {
			return publishName;
		}

		public void setPublishName(String publishName) {
			this.publishName = publishName;
		}

	}

}
