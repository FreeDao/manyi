/**
 * 
 */
package com.manyi.ihouse.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zxc
 */
@SuppressWarnings("unchecked")
public class ArrayUtils {
	
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
	    
	    public static <E> E[] replaceNullElement(E[] array,IHandle handle){
	    	if (handle == null) {
				throw new RuntimeException();
			}
	        if (Argument.isEmptyArray(array)) {
	        	return null;
	        }
	        int nullValueCount = 0;
	        for (int i = 0, j = array.length; i < j; i++) {
	            if (array[i] == null || handle.isNull(array[i])) {
	                nullValueCount++;
	            }
	        }
	        return nullValueCount == 0 ? array : arrayNewInstance(array, handle,nullValueCount);
	    }

		private static <E> E[] arrayNewInstance(E[] array, IHandle handle,int length) {
			E[] newInstance = (E[]) Array.newInstance(array.getClass().getComponentType(), length);
			for (E instance : newInstance) {
				handle.init(instance);
			}
			E[] result = ArrayUtils.removeNullElement(array);
			List<E> list = new ArrayList<E>(Arrays.asList(result));
			list.addAll(Arrays.asList(newInstance));
			return (E[]) list.toArray();
		}
		
		public static void main(String[] args) {
			String[] s = { "12", null, "manyi" };
			s = ArrayUtils.replaceNullElement(s, new IHandle() {
				
				@Override
				public <T> boolean isNull(T obj) {
					return obj == null;
				}
				
				@Override
				public <T> T init(T obj) {
					if (obj instanceof String) {
						return (T) new String("wu");
					}
					return obj;
				}
			});
			System.out.println(s);
		}
}
