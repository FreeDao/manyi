package com.manyi.hims.check.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.manyi.hims.Response;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class IsShanghaiResponse extends Response {
	
	/**
	 * isShanghai 是否上海手机号 1为是 其余不是
	 */
	private int isShanghai;

}
