package com.manyi.hims.estate.controller;

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
import com.manyi.hims.estate.model.EstateDetailRes;
import com.manyi.hims.estate.model.EstateReq;
import com.manyi.hims.estate.model.EstateRequest;
import com.manyi.hims.estate.model.EstateRes;
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
	public DeferredResult<Response> editEstate(HttpSession session, @RequestBody EstateDetailRes req) {
		Response response = estateService.editEsate(req);
		return CommonUtils.deferredResult(response);
	}
	
	/**
	 * 编辑小区 时 , 加载 小区 页面
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findEstateById.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> findEstateById(HttpSession session, @RequestBody EstateRequest req) {
		EstateDetailRes response = estateService.findEstateById(req.getAreaId());//小区Id
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
	
}
