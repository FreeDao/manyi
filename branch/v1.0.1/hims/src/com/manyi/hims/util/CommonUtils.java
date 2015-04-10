package com.manyi.hims.util;

import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Response;

public class CommonUtils {
	
	public static DeferredResult<Response> deferredResult(Response response) {
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
}
