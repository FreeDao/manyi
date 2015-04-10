package com.manyi.hims.check.model;

import lombok.Data;

/**
 * @date 2014年4月30日 下午1:55:11
 * @author Tom
 * @description
 * 
 */
@Data
public class FloorRequest {

	/**
	 * 房屋id
	 */
	private int houseId;

	/**
	 * 状态，1审核通过,2审核中，3审核失败, 4无效/删除
	 */
	private int status;

	/**
	 * 地推人员id
	 */
	private int employeeId;

	/**
	 * 看房备注
	 */
	private String lookNote;
	
	/**
	 * 房屋ids
	 */
	private String houseIds;
	
}
