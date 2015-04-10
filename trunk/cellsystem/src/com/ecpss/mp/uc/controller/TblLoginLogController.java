package com.ecpss.mp.uc.controller;


import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.BaseController;
import com.ecpss.mp.Global;
import com.ecpss.mp.uc.service.TblLoginLogService;

@Controller
@RequestMapping("/log")
@SessionAttributes(Global.SESSION_UID_KEY)
public class TblLoginLogController extends BaseController {
	private TblLoginLogService tblloginLogServer;
	
	

	public TblLoginLogService getTblloginLogServer() {
		return tblloginLogServer;
	}
	@Autowired
	@Qualifier("tblLoginLogService")
	public void setTblloginLogServer(TblLoginLogService tblloginLogServer) {
		this.tblloginLogServer = tblloginLogServer;
	}

	

	/**
	 * 返回登录日志列表
	 */
	@RequestMapping(value = "/loginLoglist")
	public String  getLoginLogList(Map<Object, Object> map){
		map.put("home_path", "/log/loginLoglist");
		return "log.loginLog";
	}
	/**
	 * 返回操作日志列表
	 */
	@RequestMapping(value = "/loginLogOperate")
	public String getLogOperate(Map<Object, Object> map){
		map.put("home_path", "/log/loginLogOperate");
		return "log.logOperate";
	}
}
