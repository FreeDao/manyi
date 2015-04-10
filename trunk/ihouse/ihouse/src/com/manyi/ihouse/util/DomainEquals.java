/**
 * 
 */
package com.manyi.ihouse.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class DomainEquals {

    /**
     * 日志操作类
     */
	private static final Logger logger =LoggerFactory.getLogger(DomainEquals.class);
	
    public DomainEquals() {
    }
    
    public static <T> boolean equals(T source, T target) {
        if (source == null || target == null) {
            return false;
        }
        if (!source.getClass().equals(target.getClass())) {
        	return false;
		}
        if(source instanceof Short && target instanceof Short)
        {
        	return ((Short) source).equals((Short) target);
        }
        else if(source instanceof Long && target instanceof Long)
        {
        	return ((Long) source).equals((Long) target);
        }
        else if(source instanceof Integer && target instanceof Integer)
        {
        	return ((Integer) source).equals((Integer) target);
        }
        else if(source instanceof Float && target instanceof Float)
        {
        	return ((Float) source).equals((Float) target);
        }
        else if(source instanceof Double && target instanceof Double)
        {
        	return ((Double) source).equals((Double) target);
        }
        else if(source instanceof Byte && target instanceof Byte)
        {
        	return ((Byte) source).equals((Byte) target);
        }
        else if(source instanceof BigInteger && target instanceof BigInteger)
        {
        	return ((BigInteger) source).equals((BigInteger) target);
        }
        else if(source instanceof BigDecimal && target instanceof BigDecimal)
        {
        	return ((BigDecimal) source).equals((BigDecimal) target);
        }
        else if (source instanceof String && target instanceof String) {
			return StringUtils.equals((String)source, (String)target);
		}
        else{
        	boolean rv = true;
            if (source instanceof Map) {
        		rv = mapOfSrc(source, target, rv);
        	} else {
        		rv = classOfSrc(source, target, rv);
        	}
        	logger.info("THE EQUALS RESULT IS " + rv);
        	return rv;
        }
    }

	private static boolean mapOfSrc(Object source, Object target, boolean rv) {
        HashMap<String, String> map = new HashMap<String, String>();
        map = (HashMap) source;
        for (String key : map.keySet()) {
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>();
                tarMap = (HashMap) target;
                if(tarMap.get(key)==null){
                    rv = false;
                    break;
                }
                if (!map.get(key).equals(tarMap.get(key))) {
                    rv = false;
                    break;
                }
            } else {
                String tarValue = getClassValue(target, key) == null ? "" : getClassValue(target, key).toString();
                if (!tarValue.equals(map.get(key))) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

    private static boolean classOfSrc(Object source, Object target, boolean rv) {
        Class<?> srcClass = source.getClass();
        Field[] fields = srcClass.getDeclaredFields();
        for (Field field : fields) {
            String nameKey = field.getName();
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>();
                tarMap = (HashMap) target;
                String srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey)
                        .toString();
                if(tarMap.get(nameKey)==null){
                    rv = false;
                    break;
                }
                if (!tarMap.get(nameKey).equals(srcValue)) {
                    rv = false;
                    break;
                }
            } else {
                String srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey)
                        .toString();
                String tarValue = getClassValue(target, nameKey) == null ? "" : getClassValue(target, nameKey)
                        .toString();
                if (!srcValue.equals(tarValue)) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

	public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj, new Object[] {});
                } catch (Exception e) {
                     logger.info("反射取值出错：" + e.toString());
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase())
                        || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if (fieldName.toUpperCase().equals("SID")
                        && (ms[i].getName().toUpperCase().equals("ID") || ms[i].getName().substring(3).toUpperCase()
                                .equals("ID"))) {
                    return objValue;
                }
            }
        } catch (Exception e) {
             logger.info("取方法出错！" + e.toString());
        }
        return null;
    }
	
	public static void main(String[] args) {
		System.out.println(DomainEquals.equals(12, 32));
		System.out.println(DomainEquals.equals('s', 32));
		System.out.println(DomainEquals.equals("str", "sgf"));
		System.out.println(DomainEquals.equals(new Integer(123), 123));
		System.out.println(DomainEquals.equals(new Integer(123), new Integer(123)));
	}	
}
