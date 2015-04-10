package com.manyi.hims.util;

import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Response;
import com.manyi.hims.util.EntityUtils.StatusEnum;

public class CommonUtils {
	
	/**
	 * 异步输出转换
	 * @param response
	 * @return
	 */
	public static <T> DeferredResult<T> deferredResultGenic(T response) {
		DeferredResult<T> dr = new DeferredResult<T>();
		dr.setResult(response);
		return dr;
	}
	
	public static DeferredResult<Response> deferredResult(Response response) {
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	/**
	 * 轮询审核状态转换
	 * @param status
	 * @return
	 */
	public static int loopTransformStatus(int status) {
		if (status == StatusEnum.FAILD.getValue()) {
			return  StatusEnum.SUCCESS.getValue();
		}else if (status == StatusEnum.SUCCESS.getValue()) {
			return StatusEnum.FAILD.getValue();
		}else {
			return status;
		}
		
	}
	
	/**
	 * 轮询审核状态转换获取文本
	 * @param status
	 * @return
	 */
	public static String loopTransformStatusDesc(int status) {
		return StatusEnum.getByValue(loopTransformStatus(status)).getDesc();
	}
	
}
