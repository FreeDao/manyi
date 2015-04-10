package com.manyi.hims.client;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.manyi.hims.util.InfoUtils;

public class TestClient {
	public static  void main(String[] args) {
		Map<String, Object> pars=new HashMap<String, Object>();
		String basicUrl = "http://10.0.0.34:8080";
		
		//登陆验证
		/*String url = basicUrl+"/rest/uc/userLogin.rest";
		pars.put("loginName", "18621001613");
		pars.put("password", "wang1987");*/
		
		//发布记录 首页数据列表
		/*String url = basicUrl+"/rest/sourceLog/indexList.rest";
		pars.put("userId", "65536");*/
		
		//我的账号
		/*String url = basicUrl+"/rest/uc/myAccount.rest";
		pars.put("uid", "655401"); */
		
		//我的奖金
		/*String url=basicUrl+"/rest/uc/award.rest";
		pars.put("uid","65540");
		pars.put("markTime", Calendar.getInstance().getTime().getTime());*/
		
		//我的奖金加载列表
		/*String url=basicUrl+"/rest/uc/findAwardPage.rest";
		pars.put("userId", "65540");
		pars.put("start", "12");
		pars.put("end", "23");
		pars.put("markTime", Calendar.getInstance().getTimeInMillis());*/
		
		//绑定支付宝
		/*String url=basicUrl+"/rest/uc/bindPaypal.rest";
		pars.put("uid", "65540");
		pars.put("paypalAccount", "ygduanwenping@163.com");*/
		
		//修改支付宝绑定
		/*String url=basicUrl+"/rest/uc/changePaypal.rest";
		pars.put("uid", "65540");
		pars.put("paypalAccount", "woduanwenping@163.com");
		pars.put("password", "123456x");*/
		
		//小区模糊查询
		/*String url=basicUrl+"/rest/common/findEstateByName.rest";
		pars.put("name", "政宁");*/
		
		//自动更新
	/*	String url=basicUrl+"/rest/common/automaticUpdates.rest";
		pars.put("name","S");*/
		
		//小区首字母查询初始化数据
		String url = basicUrl +"/rest/uc/initAcronymData.rest";
		pars.put("name","初始化数据");
		
		/*String url=basicUrl+"/rest/uc/findLoginPwd.rest";
		pars.put("phone", "18721004210");*/
		
		//经纪人注册用户
	/*	String url = basicUrl +"/rest/uc/regist.rest";
		pars.put("realName", "段文平"); 
		pars.put("code", "362329198806123018"); 
		pars.put("mobile", "13774235488");
		pars.put("password", "1234567x");
		pars.put("areaId", "1");
		pars.put("vilidate", "A1B2");*/
		//java.io.File cardFile = new java.io.File("D://dwp//pic//temp1.bmp");
		//File codeFile = new File("D://dwp//pic//temp2.bmp");
		//pars.put("cardFile", cardFile);
		//pars.put("codeFile", codeFile);
		
		InfoUtils.sendRestInter(url, pars);
	}
}
