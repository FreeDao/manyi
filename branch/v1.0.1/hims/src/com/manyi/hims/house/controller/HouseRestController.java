package com.manyi.hims.house.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.manyi.hims.PageResponse;
import com.manyi.hims.RestController;
import com.manyi.hims.house.service.HouseService;
import com.manyi.hims.util.EntityUtils;


/**
 * 任务审核模块
 * @author tiger
 *
 */
@Controller
@RequestMapping("/rest/house")
@SessionAttributes(Global.SESSION_UID_KEY)
public class HouseRestController extends RestController{
	
	@Autowired
	@Qualifier("houseService")
	private HouseService houseService;
	
	public void setHouseService(HouseService houseService) {
		this.houseService = houseService;
	}

	/**
	 * 房源信息  列表
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/houseList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> checktaskList(HttpSession session, @RequestBody HouseReq req) {
		final PageResponse<HouseRes> lists = this.houseService.houseList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
	@Data
	public static class HouseReq{
		private int page=1;//当前第几页
		private int rows=20;//每一页多少
		private String orderby =" ";//排序规则
		private String estateName;//小区名字
		private int areaId;//区域ID
		private int parentId;//父区域ID
		private int sellState;//出租状态
		private int rentState;//出售状态
		private String operServiceState;//审核状态
		
	}
	
	public static class HouseRes {
		private int houseId;//记录id
		private int areaId;//区域
		private String areaName;
		private int townId;//片区
		private String townName;
		private int estateId;//小区
		private String estateName;
		private String builing;//栋座
		private String room;//室号
		private Date sellPublishDate;//发布 出售时间
		private String sellPublishDateStr;
		private Date rentPublishDate;//发布 出租时间
		private String rentPublishDateStr;
		private int sellState;
		private String sellStateStr;
		private int rentState;
		private String rentStateStr;
		private String checkType;//审核 类型(1,发布出售;2,发布出租;3,改盘;4,举报;5,客服轮询;6,抽查看房)
		private String checkTypeStr;
		private int logId ;
		private Date logCreateTime;
		private String logCreateTimeStr;
		private int operServiceState;//审核状态(1,审核成功;2,审核中;3,审核失败)
		private String operServiceStateStr;
		
		public int getOperServiceState() {
			return operServiceState;
		}

		public String getOperServiceStateStr() {
			return operServiceStateStr;
		}

		public void setOperServiceState(int operServiceState) {
			this.operServiceState = operServiceState;
			
			if(EntityUtils.StatusEnum.SUCCESS.getValue() == operServiceState){
				this.operServiceStateStr="审核成功";
			}else if(EntityUtils.StatusEnum.FAILD.getValue() == operServiceState){
				this.operServiceStateStr="审核失败";
			}else if(EntityUtils.StatusEnum.ING.getValue() == operServiceState){
				this.operServiceStateStr="审核中";
			}
		}
		
		public Date getLogCreateTime() {
			return logCreateTime;
		}
		public void setLogCreateTime(Date logCreateTime) {
			this.logCreateTime = logCreateTime;
			if(logCreateTime != null){
				this.logCreateTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logCreateTime);
			}
		}
		public String getLogCreateTimeStr() {
			return logCreateTimeStr;
		}
		public int getLogId() {
			return logId;
		}
		public void setLogId(int logId) {
			this.logId = logId;
		}
		public String getCheckType() {
			return checkType;
		}
		public void setCheckType(String checkType) {
			this.checkType = checkType;
		}
		public String getCheckTypeStr() {
			return checkTypeStr;
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
		public int getHouseId() {
			return houseId;
		}
		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}
		public Date getSellPublishDate() {
			return sellPublishDate;
		}
		public void setSellPublishDate(Date sellPublishDate) {
			this.sellPublishDate = sellPublishDate;
			if(sellPublishDate != null){
				this.sellPublishDateStr= new SimpleDateFormat("yyyy-MM-dd").format(sellPublishDate);
			}else{
				this.sellPublishDateStr="-";
			}
		}
		public Date getRentPublishDate() {
			return rentPublishDate;
		}
		public void setRentPublishDate(Date rentPublishDate) {
			this.rentPublishDate = rentPublishDate;
			if(rentPublishDate != null){
				this.rentPublishDateStr= new SimpleDateFormat("yyyy-MM-dd").format(rentPublishDate);
			}else{
				this.rentPublishDateStr="-";
			}
		}
		public int getSellState() {
			return sellState;
		}
		public void setSellState(int sellState) {
			this.sellState = sellState;
		}
		public int getRentState() {
			return rentState;
		}
		public void setRentState(int rentState) {
			this.rentState = rentState;
		}
		public String getSellPublishDateStr() {
			return sellPublishDateStr;
		}
		public String getRentPublishDateStr() {
			return rentPublishDateStr;
		}
		public String getSellStateStr() {
			return sellStateStr;
		}
		public String getRentStateStr() {
			return rentStateStr;
		}
		public void setSellStateStr(String sellStateStr) {
			this.sellStateStr = sellStateStr;
		}
		public void setCheckTypeStr(String checkTypeStr) {
			this.checkTypeStr = checkTypeStr;
		}
		
		
	}
	
	
}
