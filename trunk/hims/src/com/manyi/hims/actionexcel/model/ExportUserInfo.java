/**
 * 
 */
package com.manyi.hims.actionexcel.model;

import java.util.Date;

import lombok.Data;

/**
 * @author zxc select
 * 
 *         re.houseId as houseId,re.hostName as hostName,re.hostMobile as hostMobile, max(re.createTime) as createTime,re.estateName as
 *         estateName,re.road as road,re.building as building,re.floor as floor,re.room as room
 */
@Data
public class ExportUserInfo {

	private String houseId;
	private String hostName;
	private String hostMobile;
	private String historyId;
	private int actionType;
	private int houseState;
	private int status;
	private String room;
	private String building;
	private String estateName;
	private String userName;
	private String userMobile;
	private int ifInner;
	private Date publishDate;
	private String userAreaStr;

}
