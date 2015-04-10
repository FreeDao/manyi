package com.manyi.hims.util.enums.building;

import lombok.Getter;
import lombok.Setter;

import com.manyi.hims.util.NumberUtils;

/**
 * 楼栋图片类型
 * @author fuhao,tiger
 *
 */
public enum BuildingImageTypeEnum {
	
	UNUSED(0, "-"),
	
	INNER(1, "内景"),
	
	OUTER(2, "外景"),
	
	MAILBOX(3, "信箱"),
	
	LIFT(4, "电梯照");
	
	@Getter @Setter private int value;
	@Getter @Setter private String desc;

	private BuildingImageTypeEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static BuildingImageTypeEnum getByValue(int value) {
		for (BuildingImageTypeEnum ve : values()) {
			if (NumberUtils.isEqual(value, ve.value)) {
				return ve;
			}
		}
		return UNUSED;
	}

}
