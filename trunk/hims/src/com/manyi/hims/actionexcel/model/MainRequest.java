/**
 * 
 */
package com.manyi.hims.actionexcel.model;

import lombok.Data;

/**
 * @author zxc
 * 
 */
@Data
public class MainRequest {

	private String startDate;
	private String endDate;
	private String mobile;
	private Integer number;
	private int cityType ;//城市

}
