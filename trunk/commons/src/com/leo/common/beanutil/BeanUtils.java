package com.leo.common.beanutil;

import java.lang.reflect.InvocationTargetException;

/**
*
* <p>Title: mia</p>
*
* <p>Description: bean util</p>
*
* <p>Copyright: Copyright (c) 2005</p>
*
* <p>Company: arcsoft</p>
*
* @author leili
* @version 1.0
*/
public class BeanUtils {
  //private static Log log = LogFactory.getLog(BeanUtils.class);
  public static void setProperty(Object bean , String property , Object param){
    String methodName = "set" + property.substring(0 , 1).toUpperCase() + property.substring(1 , property.length());
    invoke(bean , methodName , param);
  }
  
  
  @SuppressWarnings("unchecked")
  public static <T> T getProperty(Object bean , String property) {
	//log.info("执行了getProperty方法");
	T result = null;
	String methodName = "get" + property.substring(0 , 1).toUpperCase() + property.substring(1 , property.length());
	result =(T)invoke(bean , methodName);
	return result;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> T invoke(Object bean , String methodName , Object... params){
	  try{
		  return (T) MethodUtils.invokeMethod(bean , methodName , params);
	  }catch(InvocationTargetException e){
		  if(e.getTargetException() instanceof RuntimeException){
			  throw (RuntimeException)e.getTargetException();
		  }
		  throw new BeanAccessException(e.getTargetException());
	  }catch(Exception e){
		  if(e instanceof NoSuchMethodException)
			  throw new NoMethodException(e.getMessage());
		  else
			  throw new BeanAccessException(e);
	  }
  }
  
  
  @SuppressWarnings("unchecked")
  public static <T> T invoke(Object bean , String methodName , Object param){
    try{
        return (T) MethodUtils.invokeMethod(bean , methodName , param);	
    }catch(InvocationTargetException e){
		  if(e.getTargetException() instanceof RuntimeException){
			  throw (RuntimeException)e.getTargetException();
		  }
		  throw new BeanAccessException(e.getTargetException());
	  }catch(Exception e){
		  if(e instanceof NoSuchMethodException)
			  throw new NoMethodException(e.getMessage());
		  else
			  throw new BeanAccessException(e);
	  }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T invoke(Object bean, String methodName, Object[] params, Class[] parameterTypes) {
		try {
			return (T) MethodUtils.invokeMethod(bean, methodName, params, parameterTypes);
		} catch (InvocationTargetException e) {
			  if(e.getTargetException() instanceof RuntimeException){
				  throw (RuntimeException)e.getTargetException();
			  }
			throw new BeanAccessException(e.getTargetException());
		}catch(Exception e){
			  if(e instanceof NoSuchMethodException)
				  throw new NoMethodException(e.getMessage());
			  else
				  throw new BeanAccessException(e);
		}
	}


  public static void copyProperties(Object target,Object src){
    try {
      org.apache.commons.beanutils.BeanUtils.copyProperties(target, src);
    }catch(InvocationTargetException e){
		  if(e.getTargetException() instanceof RuntimeException){
			  throw (RuntimeException)e.getTargetException();
		  }
		  throw new BeanAccessException(e.getTargetException());
	}catch (Throwable e) {
      throw new BeanAccessException("Copy the properties is failed,Cause:"+e.getMessage());
    }
  }
      /*
       public static void main(String[] aa) {
         //System.out.println("=====i======>"+Integer.class.isAssignableFrom(int.class));
         //System.out.println("=====b======>"+int.class.isAssignableFrom(int.class));
         try {
           System.out.println("============>" +
       ItestBean.class.getMethod("getM001").getDeclaringClass().
                              getName());
         } catch(SecurityException ex) {
           System.out.println(ex);
         } catch(NoSuchMethodException ex) {
           System.out.println(ex);
         }
       }

       static class Test {

         public int byte2int(byte b) {
           System.out.println(b);
           return b;
         }
       }
       */
}
