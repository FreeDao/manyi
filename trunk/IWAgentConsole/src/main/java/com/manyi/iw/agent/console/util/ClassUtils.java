package com.manyi.iw.agent.console.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * Function: Class相关操作
 *
 * @author   carvink
 * @Date	 2014年6月18日		上午11:49:31
 *
 * @see 	  
 */
public class ClassUtils {
	/**
	 * 父类为Object类型的所有子类
	 */
	public static final int CHILD_OBJECT=0;
	/**
	 * 父类为Interfece类型的所有子实现类
	 */
	public static final int CHILD_OBJECT_IMPL=1;
	/**
	 * 父类为Interface类型的所有子接口
	 */
	public static final int CHILD_INTERFACE=2;
	/**
	 * 父类为Interface类型的所有子类（接口）
	 */
	public static final int CHILD_MIX=3;
	/**
	 * 给一个接口，返回这个接口的所有实现类
	 * @param c 一个接口对象，若为空，则返回空
	 * @return
	 */
	@SuppressWarnings("all")
	public static List<Class> getChildClassesBySuper(Class c,int child){
		List<Class> returnClassList =null; //返回结果
		//如果不是一个接口，则不做处理
		//if(c.isInterface()){
			 returnClassList=new ArrayList<Class>();
			String packageName = c.getPackage().getName(); //获得当前的包名
			try {
				List<Class> allClass = getClasses(packageName); //获得当前包下以及子包下的所有类
				if(child==ClassUtils.CHILD_OBJECT||child==ClassUtils.CHILD_MIX){
					for(int i=0;i<allClass.size();i++){
						if(c.isAssignableFrom(allClass.get(i))){ //判断是不是一个接口
							if(!c.equals(allClass.get(i))){ //本身不加进去
								returnClassList.add(allClass.get(i));
							}
						}
					}
				}else if(child==ClassUtils.CHILD_OBJECT_IMPL){
					for(int i=0;i<allClass.size();i++){
						if(c.isAssignableFrom(allClass.get(i))&&!allClass.get(i).isInterface()){ //判断是不是一个接口
							if(!c.equals(allClass.get(i))){ //本身不加进去
								returnClassList.add(allClass.get(i));
							}
						}
					}
				}else if(child==ClassUtils.CHILD_INTERFACE){
					for(int i=0;i<allClass.size();i++){
						if(c.isAssignableFrom(allClass.get(i))&&allClass.get(i).isInterface()){ //判断是不是一个接口
							if(!c.equals(allClass.get(i))){ //本身不加进去
								returnClassList.add(allClass.get(i));
							}
						}
					}
				}else return null;
				//判断是否是同一个接口
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
		return returnClassList;
	}
	/**
	 * 从一个包中查找出所有的类，在jar包中不能查找
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("all")
	private static List<Class> getClasses(String packageName)throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}
	@SuppressWarnings("all")
	private static List<Class> findClasses(File directory, String packageName)throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." +file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' +file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
	
	public static boolean classInstance(Class parent,Class child){
		try {
			child.asSubclass(parent);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 获取传入Class对象的name字段，包含父类字段
	 * @author 程康
	 * <p>创建时间2014-5-15下午1:59:26</p>
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Field getDeclaredField(Class clazz,String name){
		Field field = null;
		try {
			if(clazz == Object.class){
				return null;
			}
			field = clazz.getDeclaredField(name);
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
			return getDeclaredField(clazz.getSuperclass(), name);
		}
		return field;
	}
	
	public static List<Field> getDeclaredFields(Class clazz){
		return getDeclaredFields(clazz,null);
	}
	
	private static List<Field> getDeclaredFields(Class clazz,List<Field> fields){
		if(fields == null)fields = new ArrayList<Field>();
		if(clazz != Object.class){
			fields.addAll(ArrayUtils.asList(clazz.getDeclaredFields()));
			return getDeclaredFields(clazz.getSuperclass(),fields);
		}
		return fields;
	}
}
