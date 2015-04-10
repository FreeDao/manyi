package com.manyi.hims.pay.util;

import java.math.BigDecimal;

import com.manyi.hims.entity.Pay;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.AwardTypeEnum;


public class PayUtil {
	
	/**
	 * 构造一个 pay对象,在审核通过的时候使用这个方法
	 * 返回的pay最后 需要手动保存
	 * 
	 * 1. 发布出售  7元
	 * 2. 发布出租  5元
	 * 3. 改盘          2元
	 * 4. 举报          20元
	 * 5. 新增小区  10元
	 * 6. 注册成功  10元
	 * 7. 邀请人注册成功 10元
	 * @param req
	 * @return
	 */
	public static Pay createPay(AddPayReq req) {
		Pay pay = new Pay();
		pay.setSource(req.getSource());
		pay.setUserId(req.getUserId());
		pay.setPayState(0);//支付状态
		
		// 这笔 数据的 来源(以下动作审核成功的时候,就会添加一笔记录)
		AwardTypeEnum type = EntityUtils.AwardTypeEnum.getByValue(req.getSource());
		pay.setPaySum(new BigDecimal(type.getMoney()));
		pay.setPayReason(type.getSource()+type.getDesc());
		return pay;
	}
}
