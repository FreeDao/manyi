package com.manyi.hims.util.enums.estate;

import lombok.Getter;
import lombok.Setter;

import com.manyi.hims.util.NumberUtils;

/**
 * 小区图片状态enum
 * @author fuhao,tiger
 *
 */
public enum EstateImageStatusEnum {
	
	UNUSED(0, "-"),
	
	ENABLE(1, "启用"),
	
	DISABLE(2, "禁用");
	
	@Getter @Setter private int value;
	@Getter @Setter private String desc;

	private EstateImageStatusEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static EstateImageStatusEnum getByValue(int value) {
		for (EstateImageStatusEnum ve : values()) {
			if (NumberUtils.isEqual(value, ve.value)) {
				return ve;
			}
		}
		return UNUSED;
	}

}
