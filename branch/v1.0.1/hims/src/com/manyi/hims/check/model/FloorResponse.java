package com.manyi.hims.check.model;

import java.util.List;

import lombok.Data;

import com.manyi.hims.Response;
import com.manyi.hims.sourcemanage.model.SourceManageResponse.Audit;

/**
 * @date 2014年4月23日 下午1:04:39
 * @author Tom
 * @description
 * 举报详情页面
 */
@Data
public class FloorResponse extends Response{

	/**
	 * 举报理由
	 */
	private String remark;

	/**
	 * 房子信息（中远两湾城 - 22号 - 1102室）
	 */
	private String houseInfoMsg;

	/**
	 * 举报信息 （例如：举报:正在出售,正在出租）
	 */
	private String reportMsg;

	/**
	 * 业主信息
	 */
	private List<String> houseResourceContact;
	
	/**
	 * 原发布出售/出租信息的经纪人
	 */
	private List<String> userStr;
	
//	审核记录
	private List<Audit> auditList;
	
//	操作成功标识
	private String operFlag;
	

}

