package com.manyi.fyb.callcenter.aiwutools.linktools.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.manyi.fyb.callcenter.aiwutools.linktools.model.LinkSingleRequest;
import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.utils.Constants;
import com.manyi.fyb.callcenter.utils.enums.AiwuLinkToolEnum;

@Controller
@RequestMapping("/linktool")
public class LinkController extends BaseController {
	
	@RequestMapping("/outerpage/directUrl")
	public ModelAndView  getPage(LinkSingleRequest lsr,ModelAndView mav,HttpServletResponse response) throws IOException {
		mav.setViewName("aiwutools/linktool/outerlink");
		
		String cityName = "";
		if(StringUtils.isNotBlank(lsr.getCityName())){
			cityName = URLDecoder.decode(lsr.getCityName(),"UTF-8");
		}
		
		String name = URLDecoder.decode(lsr.getName(),"UTF-8");
		System.out.println(cityName + " " + name);
		lsr.setName(name);
		lsr.setCityName(cityName);
		mav.addObject("linkRequest", lsr);
		if (lsr.getType() == AiwuLinkToolEnum.BAIDU.getValue()) {
			mav.addObject("link", Constants.BAIDU_MAP);
		}else if (lsr.getType() == AiwuLinkToolEnum.TENCENT.getValue()) {
			mav.addObject("link", Constants.TENCENT_MAP);
		}else if (lsr.getType() == AiwuLinkToolEnum.GAODE.getValue()) {
			response.sendRedirect(Constants.GAODE_MAP+"#!poi!!q="+URLEncoder.encode(cityName+" "+name,"UTF-8") );
			return null;
		}else if (lsr.getType() == AiwuLinkToolEnum.SOUFAN.getValue()) {
			mav.addObject("link", Constants.SOU_FAN);
			
			if("深圳".equals(cityName)){
				mav.addObject("link", Constants.SOU_FAN_SZ);
			}else if("上海".equals(cityName)){
				mav.addObject("link", Constants.SOU_FAN_SH);
			}else if("广州".equals(cityName)){
				mav.addObject("link", Constants.SOU_FAN_GZ);
			}
			
		}else if (lsr.getType() == AiwuLinkToolEnum.BAIDU_API.getValue()) {
			mav.addObject("link", Constants.BAIDU_API_MAP);
		}
		
		return mav;
		//return null;
	}
	

	/**
	 * 跳转到 对应的 页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/ihutil")
	private String index(HttpServletRequest request ){
		return "aiwutools/linktool/ihutil";
	}
	
	
}
