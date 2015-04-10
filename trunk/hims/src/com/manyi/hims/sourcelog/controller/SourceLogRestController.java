package com.manyi.hims.sourcelog.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.common.PublishHouseRequest;
import com.manyi.hims.entity.HouseResourceContact;
import com.manyi.hims.sourcelog.SourceLogResponse;
import com.manyi.hims.sourcelog.service.SourceLogService;

/**
 * 发布记录
 * @author tiger
 *
 */
@Controller
@RequestMapping("/rest/sourceLog")
@SessionAttributes(Global.SESSION_UID_KEY)
public class SourceLogRestController extends RestController {
	@Autowired
	@Qualifier("sourceLogService")
	private SourceLogService sourceLogService;
	
	public void setSourceLogService(SourceLogService sourceLogService) {
		this.sourceLogService = sourceLogService;
	}

	
	/**
	 * 清除 用户历史发布记录
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/clearPublishLog.rest", produces="application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> clearPublishLog(HttpSession session, @RequestBody SourceLogRquest req) {
		final int tempNum = this.sourceLogService.clearPublishLog(req.getUserId());
		Response result = new Response(){
			private int num;
			{
				this.num=tempNum;
			}
			public int getNum() {
				return num;
			}

			public void setNum(int num) {
				this.num = num;
			}
			
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	/**
	 * 发布记录小区 首页数据列表
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/indexListAddAreaLog.rest", produces="application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> indexListAddAreaLog(HttpSession session, @RequestBody SourceLogRquest req) {
		final List<SourceLogResponse> entitys= this.sourceLogService.indexListAddAreaLog(req.getUserId());
		Response result = new Response(){
			private List<SourceLogResponse> sourceList = new ArrayList<SourceLogResponse>();
			{
				this.sourceList= entitys;
			}
			
			public List<SourceLogResponse> getSourceList() {
				return sourceList;
			}
			public void setSourceList(List<SourceLogResponse> sourceList) {
				this.sourceList = sourceList;
			}
			
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	/**
	 * 发布记录 首页数据列表
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/indexList.rest", produces="application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> indexList(HttpSession session, @RequestBody SourceLogRquest req) {
		final List<SourceLogResponse> entitys= this.sourceLogService.indexList(req.getUserId());
		Response result = new Response(){
			private List<SourceLogResponse> sourceList = new ArrayList<SourceLogResponse>();
			{
				this.sourceList= entitys;
			}
			
			public List<SourceLogResponse> getSourceList() {
				return sourceList;
			}
			public void setSourceList(List<SourceLogResponse> sourceList) {
				this.sourceList = sourceList;
			}
			
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	/**
	 * 发布记录 小区首页数据列表(分页)
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findPageAddAreaLog.rest", produces="application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> findPageAddAreaLog(HttpSession session, @RequestBody SourceLogRquest req) {
		final List<SourceLogResponse> entitys= this.sourceLogService.findPageAddAreaLog(req.getUserId(), req.getStart(), req.getEnd(), req.getMarkTime());
		Response result = new Response(){
			private List<SourceLogResponse> sourceList = new ArrayList<SourceLogResponse>();
			{
				this.sourceList= entitys;
			}
			
			public List<SourceLogResponse> getSourceList() {
				return sourceList;
			}
			public void setSourceList(List<SourceLogResponse> sourceList) {
				this.sourceList = sourceList;
			}
			
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	/**
	 * 发布记录 首页数据列表(分页)
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findPageLog.rest", produces="application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> findPageLog(HttpSession session, @RequestBody SourceLogRquest req) {
		final List<SourceLogResponse> entitys= this.sourceLogService.findPageLog(req.getUserId(), req.getStart(), req.getEnd(), req.getMarkTime());
		Response result = new Response(){
			private List<SourceLogResponse> sourceList = new ArrayList<SourceLogResponse>();
			{
				this.sourceList= entitys;
			}
			
			public List<SourceLogResponse> getSourceList() {
				return sourceList;
			}
			public void setSourceList(List<SourceLogResponse> sourceList) {
				this.sourceList = sourceList;
			}
			
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	/**
	 * 改盘第一步 ,验证是否可以改盘
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/checkCanUpdateDisc.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<UpdateDiscResponse> checkCanUpdateDisc(HttpSession session, @RequestBody UpdateDiscRequest req) {
		UpdateDiscResponse response= this.sourceLogService.checkCanUpdateDisc(req.getSubEstateId(), req.getBuilding(), req.getRoom());
		DeferredResult<UpdateDiscResponse> dr = new DeferredResult<UpdateDiscResponse>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * 加载出售记录信息
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loadSellInfoLog.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> loadSellInfoLog(HttpSession session, @RequestBody PublishHouseRequest req) {
		LoadInfoResponse response = this.sourceLogService.loadSellInfoLog(req.getLogId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * 加载出租记录信息
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loadRentInfoLog.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> loadRentInfoLog(HttpSession session, @RequestBody PublishHouseRequest req) {
		LoadInfoResponse response = this.sourceLogService.loadRentInfoLog(req.getLogId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * 改盘第二步 ,提交改盘信息
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateDisc.rest", produces="application/json")
	@ResponseBody
	@SuppressWarnings("unused")
	public DeferredResult<Response> updateDisc(HttpSession session, @RequestBody UpdateDiscContentRequest req) {
		final int tmpNum = this.sourceLogService.updateDisc(req);
		Response response =new Response(){
			private int num;
			{
				this.num = tmpNum;
				if(tmpNum == 0){
					this.setMessage("提交成功，审核中");
				}
			}
			
			public int getNum() {
				return num;
			}

			public void setNum(int num) {
				this.num = num;
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	@Data
	public static class SourceLogRquest{
		private int userId;
		private int start;
		private int end;
		private Long markTime;

	}
	@Data
	public static class UpdateDiscRequest {
		private int subEstateId;
		private String building;
		private String room;
		
	}
	@Data
	public static class UpdateDiscResponse extends Response{
		private int houseId;//房子ID
		private boolean sellEnabled;//是否在租
		private boolean rentEnabled;//是否在售
	}
	@Data
	public static class UpdateDiscContentRequest{
		private int userId;//用户ID
		private int houseId;//房源ID
		private int sellType;//出售状态(1.已出售;2,不出售)
		private int rentType;//出租状态(1.已出租;2,不出租)
		private String hostName;//联系人姓名
		private String hostMobile;//联系人电话
		private String remark;//理由
	}
	@Data
	public static class LoadInfoResponse extends Response{
		private BigDecimal spaceArea;// 内空面积
		private int bedroomSum;// 几房
		private int livingRoomSum;// 几厅
		private int wcSum;// 几卫
		private BigDecimal price;// 价格
		private List<HouseResourceContact> hosts = new ArrayList<HouseResourceContact>();// 保存联系人的方式
	}
	
}
