package com.ecpss.mp.uc.controller;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.Global;
import com.ecpss.mp.Response;
import com.ecpss.mp.RestController;
import com.ecpss.mp.entity.TblLogOperate;
import com.ecpss.mp.entity.TblLogOperatorlogin;
import com.ecpss.mp.uc.service.TblLoginLogService;

@Controller
@RequestMapping("/log_rest")
@SessionAttributes(Global.SESSION_UID_KEY)
public class TblLoginLogRestController extends RestController {
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
	@RequestMapping(value = "/loginLoglist.rest",produces="application/json")
	@ResponseBody
	public Response  getLoginLogList(){
		final List<TblLogOperatorlogin> loginLogList = tblloginLogServer.getLoginLog();
		@SuppressWarnings("unused")
		Response result = new Response(){
			private List<TblLogOperatorlogin> rows = new ArrayList<TblLogOperatorlogin>();
			
			public List<TblLogOperatorlogin> getRows() {
				return rows;
			}

			public void setRows(List<TblLogOperatorlogin> rows) {
				this.rows = rows;
			}

			{
				rows = loginLogList;
			}

			
		};
		return result;
	}
	/**
	 * 返回操作日志列表
	 */
	@RequestMapping(value = "/loginLogOperate.rest",produces="application/json")
	@ResponseBody
	public Response getLogOperate(){
		
		final List<TblLogOperate> operateList = getTblloginLogServer().getLogOperate();
		@SuppressWarnings("unused")
		Response result = new Response(){
			private List<TblLogOperate> rows = new ArrayList<TblLogOperate>();
			
			public List<TblLogOperate> getRows() {
				return rows;
			}
			public void setRows(List<TblLogOperate> rows) {
				this.rows = rows;
			}
			{
				rows = operateList;
			}
			
			
			
		};
		return result;
	}
}
