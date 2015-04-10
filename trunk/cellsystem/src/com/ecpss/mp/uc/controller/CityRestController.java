/**
 * 
 */
package com.ecpss.mp.uc.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.Global;
import com.ecpss.mp.PageResponse;
import com.ecpss.mp.Response;
import com.ecpss.mp.RestController;
import com.ecpss.mp.entity.City;
import com.ecpss.mp.uc.service.CityService;

/**
 * @author lei
 * 
 */
@Controller
@RequestMapping("/city")
@SessionAttributes(Global.SESSION_UID_KEY)
public class CityRestController extends RestController {
	private CityService cityService;

	public CityService getCityService() {
		return cityService;
	}

	@Autowired
	@Qualifier("cityService")
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	@RequestMapping(value = "/view.rest", produces = "application/json")
	@ResponseBody
	public Response view(String id) {
		try {
			String s = new String(id.getBytes("iso-8859-1"), "utf-8");
			id = s;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final List<City> citys = getCityService().getCityById(id);
		@SuppressWarnings("unused")
		Response result = new Response() {
			private String cityName;
			private String cityNo;

			{
				if (citys != null && citys.size() > 0) {
					// BeanUtils.copyProperties(citys.get(0), this);
					this.cityName = citys.get(0).getCityName();
					this.cityNo = citys.get(0).getCityNo();
				}
			}

			public String getCityName() {
				return cityName;
			}

			public void setCityName(String cityName) {
				this.cityName = cityName;
			}

			public String getCityNo() {
				return cityNo;
			}

			public void setCityNo(String cityNo) {
				this.cityNo = cityNo;
			}

		};

		return result;
	}

	/**
	 * 如果访问/xxxxx转发到表单输入页面 如果访问/xxxxxSubmit则进行xxxxx处理
	 * 
	 * @param suffix
	 * @param session
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/city.rest", produces = "application/json")
	@ResponseBody
	public Response city_list(HttpSession session) {

		final List<City> citys1 = getCityService().getCityList();

		@SuppressWarnings("unused")
		Response result = new Response() {

			List<City> rows = new ArrayList<City>();

			{
				rows.addAll(citys1);
				// BeanUtils.copyProperties(citys1, this);
			}

			public List<City> getRows() {
				return rows;
			}

			public void setRows(List<City> rows) {
				this.rows = rows;
			}

		};

		return result;
	}

	@RequestMapping(value = "/list.rest", produces = "application/json")
	@ResponseBody
	public PageResponse<City> city_list2(HttpSession session, Integer pageNo, Integer pageSize) {

		final List<City> citys1 = getCityService().getCityByPage(pageNo, pageSize);

		@SuppressWarnings("unused")
		PageResponse<City> result = new PageResponse<City>() {
			List<City> rows = new ArrayList<City>();
			{
				rows.addAll(citys1);
				// BeanUtils.copyProperties(citys1, this);
			}
			public List<City> getRows() {
				return rows;
			}
			public void setRows(List<City> rows) {
				this.rows = rows;
			}

		};

		return result;
	}

}
