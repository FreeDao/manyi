package com.manyi.hims.util.enums.estate;

import lombok.Getter;
import lombok.Setter;

import com.manyi.hims.util.NumberUtils;

/**
 * 小区图片类型enum
 * @author fuhao,tiger
 *
 */
public enum EstateImageTypeEnum {
	
	UNUSED(0, "-"),
	
	INNER(1, "内景"),
	
	OUTER(2, "外景");
	
	@Getter @Setter private int value;
	@Getter @Setter private String desc;

	private EstateImageTypeEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static EstateImageTypeEnum getByValue(int value) {
		for (EstateImageTypeEnum ve : values()) {
			if (NumberUtils.isEqual(value, ve.value)) {
				return ve;
			}
		}
		return UNUSED;
	}


}
