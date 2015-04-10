/**
 * 
 */
package com.manyi.hims.pay.service;

import java.util.List;

import com.manyi.hims.PageResponse;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.pay.controller.PayRestController.ImportPayReq;
import com.manyi.hims.pay.controller.PayRestController.PayReq;
import com.manyi.hims.pay.controller.PayRestController.PayRes;


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

	
}
