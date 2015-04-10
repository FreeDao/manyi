package com.manyi.hims.check.controller;

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
import com.manyi.hims.RestController;
import com.manyi.hims.check.model.CheckReq;
import com.manyi.hims.check.service.CheckService;
import com.manyi.hims.util.EntityUtils;


/**
 * 任务审核模块
 * @author tiger
 *
 */
@Controller
@RequestMapping("/rest/check")
@SessionAttributes(Global.SESSION_UID_KEY)
public class CheckRestController extends RestController{
	
	@Autowired
	@Qualifier("checkService")
	private CheckService checkService;
	
	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}
	/**
	 * 审核 列表
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/checkList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> checktaskList(HttpSession session, @RequestBody CheckReq req) {
		final PageResponse<CheckRes> lists = this.checkService.checktaskList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
	public static class CheckRes {
		private int logId;//记录id
		private int areaId;//区域
		private String areaName;
		private int townId;//片区
		private String townName;
		private int estateId;//小区
		private String estateName;
		private int houseId;//房屋信息ID
		private String builing;//栋座
		private String room;//室号
		private int publishId;//发布人
		private String publishName;
		private Date publishDate;//发布时间
		private String publishDateStr;
		private int operServiceId;//客服id 
		private String operServiceName;//客服名字
		private int operServiceState;//客服state
		private String operServiceStateStr;
		private String checkType;//审核 类型(1,发布出售;2,发布出租;3,改盘;4,举报;5,客服轮询;6,抽查看房)
		private String checkTypeStr;
		
		public int getHouseId() {
			return houseId;
		}
		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}
		public int getOperServiceId() {
			return operServiceId;
		}
		public void setOperServiceId(int operServiceId) {
			this.operServiceId = operServiceId;
		}
		public String getOperServiceName() {
			return operServiceName;
		}
		public void setOperServiceName(String operServiceName) {
			this.operServiceName = operServiceName;
		}
		public int getOperServiceState() {
			return operServiceState;
		}
		public void setOperServiceState(int operServiceState) {
			this.operServiceState = operServiceState;
			this.operServiceStateStr = EntityUtils.StatusEnum.getByValue(operServiceState).getDesc();
		}
		public String getOperServiceStateStr() {
			return operServiceStateStr;
		}
		public void setOperServiceStateStr(String operServiceStateStr) {
			this.operServiceStateStr = operServiceStateStr;
		}
		public void setCheckTypeStr(String checkTypeStr) {
			this.checkTypeStr = checkTypeStr;
		}
		public String getCheckType() {
			return checkType;
		}
		public void setCheckType(String checkType) {
			this.checkType = checkType;
			//0出售,1出租，2，改盘，3举报，4轮询，5抽查 
			if("0".equals(checkType)){
				this.checkTypeStr="发布出售";
			}else if("1".equals(checkType)){
				this.checkTypeStr="发布出租";
			}else if("2".equals(checkType)){
				this.checkTypeStr="改盘";
			}else if("3".equals(checkType)){
				this.checkTypeStr="举报";
			}else if("4".equals(checkType)){
				this.checkTypeStr="客服轮询";
			}else if("5".equals(checkType)){
				this.checkTypeStr="抽查看房";
			}else if("10".equals(checkType)){
				this.checkTypeStr="发布出租出售";
			}
		}
		public String getCheckTypeStr() {
			return checkTypeStr;
		}
		public int getLogId() {
			return logId;
		}
		public void setLogId(int logId) {
			this.logId = logId;
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
		public String getBuiling() {
			return builing;
		}
		public void setBuiling(String builing) {
			this.builing = builing;
		}
		public String getRoom() {
			return room;
		}
		public void setRoom(String room) {
			this.room = room;
		}
		public int getPublishId() {
			return publishId;
		}
		public void setPublishId(int publishId) {
			this.publishId = publishId;
		}
		public String getPublishName() {
			return publishName;
		}
		public void setPublishName(String publishName) {
			this.publishName = publishName;
		}

		public Date getPublishDate() {
			return publishDate;
		}
		public void setPublishDate(Date publishDate) {
			this.publishDate = publishDate;
			try {
				this.publishDateStr  =new SimpleDateFormat("yyyy-MM-dd HH:mm").format(publishDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}
		public String getPublishDateStr() {
			return publishDateStr;
		}
		public void setPublishDateStr(String publishDateStr) {
			this.publishDateStr = publishDateStr;
		}

		
		
	}
	
	
}
