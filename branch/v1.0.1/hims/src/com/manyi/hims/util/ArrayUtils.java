/**
 * 
 */
package com.manyi.hims.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc
 */
@SuppressWarnings("unchecked")
public class ArrayUtils {

	private static final Logger logger = LoggerFactory.getLogger(ArrayUtils.class);
	
	public static <T extends Object> String[] convert(T[] array) {
		if (Argument.isEmptyArray(array)) {
			return null;
		}
		String[] result = new String[array.length];
		for (int i = 0, j = result.length; i < j; i++) {
			result[i] = array[i].toString();
		}
		return result;
	}

	public static String[] removeBlankElement(String[] array) {
		if (Argument.isEmptyArray(array)) {
			return null;
		}
		List<String> list = new ArrayList<String>(Arrays.asList(array));
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			if (Argument.isBlank(iterator.next())) {
				iterator.remove();
			}
		}
		if (list.isEmpty()) {
			return null;
		} else {
			return list.toArray(new String[0]);
		}
	}

	public static <E> E[] removeNullElement(E[] array) {
		if (Argument.isEmptyArray(array)) {
			return null;
		}
		int notNullValueCount = array.length;
		for (int i = 0, j = array.length; i < j; i++) {
			if (array[i] == null) {
				notNullValueCount--;
			}
		}
		if (notNullValueCount == 0) {
			return null;
		}
		E[] newInstance = (E[]) Array.newInstance(array.getClass().getComponentType(), notNullValueCount);
		for (int i = 0, j = 0; i < array.length; i++) {
			if (array[i] != null) {
				newInstance[j++] = array[i];
			}
		}
		return newInstance;
	}

	public static <E> E[] replaceNullElement(E[] array, IHandle handle) {
		if (handle == null) {
			throw new RuntimeException();
		}
		if (Argument.isEmptyArray(array)) {
			return null;
		}
		for (int i = 0, j = array.length; i < j; i++) {
			if (array[i] == null || handle.isNull(array[i])) {
				array[i] = (E) handle.init(array.getClass().getComponentType());
			}
		}
		return array;
	}

	public static void main(String[] args) {
		String[] s = { "zxc", null, "manyi" ,""};
		s = ArrayUtils.replaceNullElement(s, new IHandle() {

			@Override
			public <T> boolean isNull(T obj) {
				return obj == null || StringUtils.isEmpty((String) obj);
			}

			@Override
			public <T> T init(Class<T> clazz) {
				if (clazz.equals(String.class)) {
					return (T) new String("wu");
				}
				try {
					return clazz.newInstance();
				} catch (InstantiationException e) {
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				}
				logger.error("replaceNullElement: init error");
				return null;
			}
		});
		logger.info(StringUtils.join(s, ";"));
	}
}
