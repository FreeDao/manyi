/**
 * 
 */
package com.manyi.hims.pay.service;

import java.util.List;

import com.manyi.hims.PageResponse;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.pay.controller.PayRestController.ImportPayReq;
import com.manyi.hims.pay.model.PayReq;
import com.manyi.hims.pay.model.PayRes;
import com.manyi.hims.pay.model.StatisPayRes;


/**
 * 
 * @author tiger
 *
 */
public interface PayService {

	/**
	 * 通过搜索 得到对应的  支付列表内容
	 * @param req
	 * @return
	 */
	public PageResponse<PayRes> payList(PayReq req);

	public int addPay(AddPayReq req);

	public int importExcel(List<ImportPayReq> reqs);
	
	public void addUserCount(int userId);

	/**
	 * 下发搜索  合并之后的列表
	 * @param req
	 * @return
	 */
	public PageResponse<PayRes> payMergeList(PayReq req);

	/**
	 * 导入 合并之后的 数据
	 * @param reqs
	 * @return
	 */
	public int importMergeExportExcel(List<ImportPayReq> reqs);

	/**
	 * callcenter 简单统计 支付数据量
	 */
	public StatisPayRes statisticsPay(PayReq req);

	
}
