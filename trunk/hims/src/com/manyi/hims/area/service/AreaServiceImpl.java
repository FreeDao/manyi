package com.manyi.hims.area.service;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.entity.Area;

/**
 * @date 2014年4月29日 下午7:33:04
 * @author Tom 
 * @description  
 * 区域服务类
 */
@Service(value = "areaService")
@Scope(value = "singleton")
public class AreaServiceImpl extends BaseService implements AreaService {
	
	
	/**
	 * @date 2014年4月29日 下午7:33:53
	 * @author Tom  
	 * @description  
	 * 根据serialCode获得小区c
	 */
	public Area getArea4SerialCode(String serialCode) {
		Query query = this.getEntityManager().createQuery("from Area a where a.serialCode = ?");
		query.setParameter(1, serialCode);
		query.setHint("org.hibernate.cacheable", true);//来实现读取二级缓存
		return (Area)query.getSingleResult();
	}
	 
	/**
	 * @date 2014年4月29日 下午7:33:53
	 * @author Tom  
	 * @description  
	 * 根据获得小区serialCode  
	 */
	public String getSerialCodeByAreaId(int areaId) {
		Area area = this.getEntityManager().find(Area.class, areaId);
		return area.getSerialCode();
	}
	 
	/**
	 * @date 2014年4月29日 下午7:33:53
	 * @author Tom  
	 * @description  
	 * 根据id 获得parentArea
	 */
	public Area getParentAreaById(int id) {
		Area area = this.getEntityManager().getReference(Area.class, id);
		return this.getEntityManager().getReference(Area.class, area.getParentId());
	}
	
	/**
	 * @date 2014年5月22日 上午1:12:23
	 * @author Tom
	 * @description  
	 * 拼接上传阿里云路径：serialCode 0000100001000070000200106
	 * 
	 * 0000100001/000010000100007/00001000010000700002/0000100001000070000200106/
	 */
	public String getAliyunPath(String serialCode) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; 5 * i < serialCode.length(); i ++ ) {
			sb.append(serialCode.substring(0, 5 * (i + 1)));
			sb.append("/");
		}
		return sb.toString();
	}
}
	

