/**
 * 
 */
package com.manyi.hims.util;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc
 */
@SuppressWarnings("rawtypes")
public class Argument {
	public static boolean isPositive(Integer argument) {
		return argument != null && argument > 0;
	}

	public static boolean isPositive(Number argument) {
		if (argument == null) {
			return false;
		}
		return argument.floatValue() > 0f || argument.intValue() > 0;
	}

	public static boolean isNull(Object argument) {
		return argument == null;
	}

	public static boolean isBlank(String argument) {
		return StringUtils.isBlank(argument);
	}

	public static boolean isEmpty(Collection argument) {
		return isNull(argument) || argument.isEmpty();
	}

	public static boolean isNotNull(Object argument) {
		return argument != null;
	}

	public static boolean isNotEmpty(Collection argument) {
		return !isEmpty(argument);
	}

	public static boolean isNotEmptyArray(Object[] array) {
		return !isEmptyArray(array);
	}

	public static boolean isEmptyArray(Object[] array) {
		return isNull(array) || array.length == 0;
	}

	public static boolean isNotBlank(String argument) {
		return StringUtils.isNotBlank(argument);
	}

	public static boolean integerEqual(Integer num1, Integer num2) {
		return num1 == null ? num2 == null : num1.equals(num2);
	}
}
