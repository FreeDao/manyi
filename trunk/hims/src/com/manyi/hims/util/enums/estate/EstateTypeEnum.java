package com.manyi.hims.util.enums.estate;

import lombok.Getter;
import lombok.Setter;

import com.manyi.hims.util.NumberUtils;

/**
 * 小区 类型enum
 * @author fuhao,tiger
 *
 */
public enum EstateTypeEnum {
	
	UNUSED(0, "-"),
	
	DWELLING(1, "住宅"),
	
	COTTAGE(2, "别墅"),
	
	ALL(3, "住宅别墅混合");
	
	@Getter @Setter private int value;
	@Getter @Setter private String desc;

	private EstateTypeEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static EstateTypeEnum getByValue(int value) {
		for (EstateTypeEnum ve : values()) {
			if (NumberUtils.isEqual(value, ve.value)) {
				return ve;
			}
		}
		return UNUSED;
	}


}
