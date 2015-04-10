package com.manyi.hims.pay.controller;

import java.math.BigDecimal;
import java.util.List;

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
import com.manyi.hims.pay.service.PayService;
import com.manyi.hims.util.EntityUtils;


/**
 * 支付模块
 * @author tiger
 *
 */
@Controller
@RequestMapping("/rest/pay")
@SessionAttributes(Global.SESSION_UID_KEY)
public class PayRestController extends RestController{
	
	@Autowired
	@Qualifier("payService")
	private PayService payService;

	public void setPayService(PayService payService) {
		this.payService = payService;
	}

	
//	/**
//	 * 添加一笔记录
//	 * @param session
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(value = "/addPay.rest", produces="application/json")
//	@ResponseBody
//	public DeferredResult<Response> addPay(HttpSession session, @RequestBody AddPayReq req) {
//		final int tmp = this.payService.addPay(req);
//		Response res = new Response();
//		DeferredResult<Response> dr = new DeferredResult<Response>();
//		dr.setResult(res);
//		return dr;
//	}
	
	/**
	 * 房源信息  列表
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/payList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> checktaskList(HttpSession session, @RequestBody PayReq req) {
		final PageResponse<PayRes> lists = this.payService.payList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
	
	/**
	 * 导入 数据
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/importExcel.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> importExcel(HttpSession session, @RequestBody List<ImportPayReq> reqs) {
		final int tmp = this.payService.importExcel(reqs);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	public static class ImportPayReq {
		private int payId;
		private int employeeId;//支付人ID
		private String serialNumber;//支付宝流水号
		private String state;//状态
		private String remark;//失败原因,描述
		public int getPayId() {
			return payId;
		}
		public void setPayId(int payId) {
			this.payId = payId;
		}
		public int getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(int employeeId) {
			this.employeeId = employeeId;
		}
		public String getSerialNumber() {
			return serialNumber;
		}
		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
	}
	
	public static class AddPayReq{
		public AddPayReq() {
			super();
		}
		public AddPayReq(int userId, int source) {
			super();
			this.userId = userId;
			this.source = source;
		}
		
		private int userId;//经纪人ID
		/**
		 * 这笔 数据的 来源(以下动作审核成功的时候,就会添加一笔记录)
		 * 1. 发布出售  7元
		 * 2. 发布出租  5元
		 * 3. 改盘          2元
		 * 4. 举报          100元
		 * 5. 新增小区  10元
		 * 6. 注册成功  5元
		 * 7. 邀请人注册成功 5元
		 */
		private int source;//存入 来源
		
		public int getSource() {
			return source;
		}
		public void setSource(int source) {
			this.source = source;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		
	}
	
	public static class PayReq{
		private int page=1;//当前第几页
		private int rows=20;//每一页多少
		private String orderby =" ";//排序规则
		private int payState;//出售状态
		private String exportTime;//导出数据的日期(年-月-日)
		private boolean ifExport =false;//是否到处是数据
		private String start;//起始 时间 
		private String end;//截止时间
		
		public String getStart() {
			return start;
		}
		public void setStart(String start) {
			this.start = start;
		}
		public String getEnd() {
			return end;
		}
		public void setEnd(String end) {
			this.end = end;
		}
		public boolean isIfExport() {
			return ifExport;
		}
		public void setIfExport(boolean ifExport) {
			this.ifExport = ifExport;
		}
		public int getPayState() {
			return payState;
		}
		public void setPayState(int payState) {
			this.payState = payState;
		}
		public String getExportTime() {
			return exportTime;
		}
		public void setExportTime(String exportTime) {
			this.exportTime = exportTime;
		}
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
		
	}
	
	public static class PayRes {
		private int payId;
		private String userName;//经纪人名称
		private String mobile;//经纪人手机号码
		private String account;//支付宝帐号
		private int userState;//用户状态
		private String userStateStr;
		private BigDecimal paySum;//支付金额
		private int payState;//支付状态
		private String payStateStr;
		private String payReason;//付款理由
		
		public String getPayReason() {
			return payReason;
		}
		public void setPayReason(String payReason) {
			this.payReason = payReason;
		}
		public int getPayId() {
			return payId;
		}
		public void setPayId(int payId) {
			this.payId = payId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public int getUserState() {
			return userState;
		}
		public void setUserState(int userState) {
			this.userState = userState;
			// 1 已审核、 2审核中、3  审核失败  ,4删除/冻结;
			this.userStateStr = EntityUtils.StatusEnum.getByValue(userState).getDesc();
		}
		public BigDecimal getPaySum() {
			return paySum;
		}
		public void setPaySum(BigDecimal paySum) {
			this.paySum = paySum;
		}
		public int getPayState() {
			return payState;
		}
		public void setPayState(int payState) {
			this.payState = payState;
			////0,未付款;1,付款成功,2,付款失败
			switch (payState) {
			case 0:
				this.payStateStr ="未付款";
				break;
			case 1:
				this.payStateStr ="付款成功";
				break;
			case 2:
				this.payStateStr ="付款失败";
				break;

			default:
				break;
			}
		}
		public String getUserStateStr() {
			return userStateStr;
		}
		public String getPayStateStr() {
			return payStateStr;
		}
		
	}
	
	
}
