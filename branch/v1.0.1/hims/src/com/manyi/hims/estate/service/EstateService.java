/**
 * 
 */
package com.manyi.hims.estate.service;

import java.util.List;

import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.estate.controller.EstateRestController.EstateReq;
import com.manyi.hims.estate.controller.EstateRestController.EstateRes;
import com.manyi.hims.estate.model.EstateResponse;
import com.manyi.hims.estate.model.EstateVerifyReq;


/**
 * 
 * @author tiger
 *
 */
public interface EstateService {
	
	/**
	 *  小区详情
	 * @param esateName
	 * @param parentId
	 * @param address
	 */
	public EstateResponse detailEsate(String estateId);

	
	/**
	 *  編輯小区
	 * @param esateName
	 * @param parentId
	 * @param address
	 */
	public Response editEsate(String esateName,String estateId,String address);
	
	/**
	 *  新增小区
	 * @param esateName
	 * @param parentId
	 * @param address
	 */
	public Response addEsate(String esateName,int estateId,int areaId,String address,String serialCode);

	/**
	 * @date 2014年4月16日 上午10:04:10
	 * @author Tom  
	 * @description  
	 * 检索没审核的小区相关信息
	 */
	public EstateResponse findNotPassEstateById(int id);
	
	/**
	 * 小区列表
	 * @param req
	 * @return
	 */
	public PageResponse<EstateRes> estateList(EstateReq req);

	/**
	 * 新增小区 列表
	 * @param req
	 * @return
	 */
	public PageResponse<EstateRes> estateLogList(EstateReq req);
	
	/**
	 * 小区审核
	 * @param req
	 * @return
	 */
	public void estateVerify(EstateVerifyReq req);

	/**
	 * @date 2014年4月22日 下午5:25:13
	 * @author Tom  
	 * @description  
	 * 根据小区名称查询小区名称
	 */
	public List<Object> getEstateByName(String estateName);
	/**
	 * 新政小区   审核
	 * @param estateId
	 */
	public void checkEstate(int estateId,int status);

	
}
