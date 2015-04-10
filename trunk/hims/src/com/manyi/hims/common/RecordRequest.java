/**
 * 
 */
package com.manyi.hims.common;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author zxc
 * 
 */
public class RecordRequest {

	@NotEmpty(message = "{ec_100003}")
	private int historyId;
	private int houseId;
	@NotEmpty(message = "{ec_100003}")
	private int typeId;// 记录 类型ID
	@NotEmpty(message = "{ec_100003}")
	private String typeName;// 记录 类型(1.发布出售2.发布出租3.改盘4.举报5.新增小区)

	private int estateId;

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
