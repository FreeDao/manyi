package com.manyi.ihouse.about.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.ihouse.about.model.MobilePropertyRequest;
import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.entity.MobileProperty;

@Service(value = "aboutService")
@Scope(value="singleton")
public class AboutServiceImpl extends BaseService implements AboutService{
	
	@Override
	public Response mobilePropertyService(MobilePropertyRequest request){
		System.out.println("------------------------OSType:" + request.getOsType());
		System.out.println("------------------------OSVersion:" + request.getOsVersion());
		System.out.println("------------------------Brand:" + request.getBrand());
		System.out.println("------------------------IMEI:" + request.getImei());
		System.out.println("------------------------Model:" + request.getModel());
		System.out.println("------------------------NetType:" + request.getNetType());
		System.out.println("------------------------Number:" + request.getPhoneNumber());
		System.out.println("------------------------Support:" + request.getSupport());
		MobileProperty property = new MobileProperty();
		property.setBrand(request.getBrand());
		property.setImei(request.getImei());
		property.setModel(request.getModel());
		property.setNetType(request.getNetType());
		property.setOsType(request.getOsType());
		property.setOsVersion(request.getOsVersion());
		property.setPhoneNumber(request.getPhoneNumber());
		property.setSupport(request.getSupport());
		this.getEntityManager().persist(property);
		this.getEntityManager().flush();
		Response response = new Response();
		response.setMessage("信息获取成功");
		return response;
	}

	
}
