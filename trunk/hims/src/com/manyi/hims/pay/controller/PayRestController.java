package com.manyi.hims.pay.controller;

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
import com.manyi.hims.pay.model.PayReq;
import com.manyi.hims.pay.model.PayRes;
import com.manyi.hims.pay.model.StatisPayRes;
import com.manyi.hims.pay.service.PayService;


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

	
	
	/**
	 * 添加一笔记录
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addPay.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> addPay(HttpSession session, @RequestBody AddPayReq req) {
		final int tmp = this.payService.addPay(req);
		Response res = new Response();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(res);
		return dr;
	}
	
	
	/**
	 * callcenter 简单统计 支付数据量
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/statisticsPay.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<StatisPayRes> statisticsPay(HttpSession session, @RequestBody PayReq req) {
		final StatisPayRes res = this.payService.statisticsPay(req);
		DeferredResult<StatisPayRes> dr = new DeferredResult<StatisPayRes>();
		dr.setResult(res);
		return dr;
	}
	
	/**
	 * 下发搜索  合并之后的列表
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/payMergeList.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<PageResponse> payMergeList(HttpSession session, @RequestBody PayReq req) {
		final PageResponse<PayRes> lists = this.payService.payMergeList(req);
		DeferredResult<PageResponse> dr = new DeferredResult<PageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
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
	/**
	 * 导入 合并之后的 数据
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/importMergeExportExcel.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> importMergeExportExcel(HttpSession session, @RequestBody List<ImportPayReq> reqs) {
		final int tmp = this.payService.importMergeExportExcel(reqs);
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
	
}
