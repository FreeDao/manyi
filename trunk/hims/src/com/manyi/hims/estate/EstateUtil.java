package com.manyi.hims.estate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class EstateUtil {
	
	/**
	 * 返回某个对象的 属性 json 字符串
	 * @param objs 
	 * @param per 
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	public static String objProperToJsonStr(List<?> objs,String[] pers) throws NoSuchFieldException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException{
		StringBuilder sb=new StringBuilder("[");
		if(objs != null && objs.size() >0 && pers != null){
			 for (int i = 0; i < objs.size() ; i++) {
				Class clazz= objs.get(i).getClass();
				//得到该对象的 某属性的 get方法, 以供下面调用
				if(pers.length>0){
					if(i !=0){
						sb.append(",");
					}
					sb.append("{");
					for(int j =0 ; j<pers.length;j++){
						String per = pers[j];
						String name = per.substring(0, 1).toUpperCase()+per.substring(1);
						String tmp = "get"+name;
						Method getMethod = clazz.getDeclaredMethod(tmp);
						//调用 objs.get(i) 对象的 属性的 get方法 获取值
						Object getmsgObj = getMethod.invoke(objs.get(i), null);
						String getmsg="";
						if(getmsgObj != null){
							getmsg = getmsgObj+"";
						}
						if(j !=0){
							sb.append(",");
						}
						sb.append("\""+name+"\":\""+getmsg+"\"");
					}
					sb.append("}");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * ['','','']转化为 list对象
	 * @param jsonstr
	 * @return
	 */
	public static <T> List<T> jsonStrToList(String jsonstr,Class<T> clazz){
		JSONArray json = JSONArray.fromObject(jsonstr);
		List<T> list= (List<T>) JSONArray.toCollection(json,clazz);
		return list;
	}
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static <T> T json2Obj(String json, Class<T> clz) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		try {
			return objectMapper.readValue(json, clz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
