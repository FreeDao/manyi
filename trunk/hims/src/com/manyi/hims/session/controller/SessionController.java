package com.manyi.hims.session.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Response;

@Controller
@RequestMapping ("/session")
public class SessionController {
	
	@RequestMapping (value="check.rest", produces="applicatioin/json")
	@ResponseBody
	public DeferredResult<Response> check() {
		return null;
	}
}
