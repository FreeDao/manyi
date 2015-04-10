package com.leo.common.util;


import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Created by IntelliJ IDEA.
 * User: 
 * Date: 2008-12-30
 * Time: 16:06:05
 * To change this template use File | Settings | File Templates.
 */  
public class SafeUtils {
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	
	protected static final String TIME_FORMAT = "HH:mm:ss";
	
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
 
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    private static Log log = LogFactory.getLog(SafeUtils.class.getName());
    public static int getInt(Object obj){
        int tmpret =0;
        if(obj!=null){
                   try{
                	   String objStr=obj.toString();
                	   if(objStr.indexOf(".")>-1){
                		   double objDou=Double.parseDouble(objStr);
                		   objStr=Math.round(objDou)+"";
                	   }
                	   tmpret = Integer.parseInt(objStr);
                   }catch(Exception ex){
                       
                   }
        }
       return tmpret;
    }
    
public static Long getLong(Object obj,Long defvalue){
 Long tmpret = defvalue;
 if(obj!=null){
     try{
             tmpret = Long.parseLong(obj.toString());
     }catch(Exception ex){}
 }
 return tmpret;
}

public static Long getLong(Object obj){
	 Long tmpret = 0L;
	 if(obj!=null){
	     try{
	             tmpret = Long.parseLong(obj.toString());
	     }catch(Exception ex){}
	 }
	 return tmpret;
	}

    public static int getInt(Object obj,int defvalue){
        int tmpret =defvalue;
        if(obj!=null){
                   try{
                   tmpret = Integer.parseInt(obj.toString());
                   }catch(Exception ex){

                   }
        }
       return tmpret;
    }
     public static Integer getInteger(Object obj){
        Integer tmpret = new Integer(0);
        if(obj!=null){
                   try{
                       tmpret = Integer.valueOf(obj.toString());
                   }catch(Exception ex){
                       log.error("getInteger:"+ex);
                   }
        }
       return tmpret;
    }
      public static Integer getInteger(Object obj,int defvalue){
        Integer tmpret = new Integer(defvalue);
        if(obj!=null){
                   try{
                       tmpret = Integer.valueOf(obj.toString());
                   }catch(Exception ex){
                       log.error("getInteger:"+ex);
                   }
        }
       return tmpret;
    }
      public static java.util.Date getDateFromTimeStamp(Object obj){
    	  java.util.Date d = null;
    	  if(obj!=null){
    		  java.sql.Timestamp timestamp = (java.sql.Timestamp)obj;
    		  d = new java.util.Date(timestamp.getTime());
    	  }
    	  return d;
      }
      public static java.sql.Timestamp getTimeStampFromSQL(Object obj){
    	  java.sql.Timestamp timestamp = (java.sql.Timestamp)obj;
    	  return timestamp;
      }      
      public static java.util.Date getDateFromString(Object obj,String format){
    	  java.util.Date tmpret = null;
          if(obj!=null){
                     try{
                    	 SimpleDateFormat   sdf   =   new   SimpleDateFormat(format); 
                    	 tmpret  =   sdf.parse(obj.toString()); 
                     }catch(Exception ex){
                         
                     }
          }
         return tmpret;
      }
      
      /**
  	 * 判断对象是否可安全转为为整数
  	 * */
  	public static boolean isDia(Object obj){
  		boolean result = false;
  		if (obj == null || (""+obj).trim().equals("")) {
  			return false;
  		}
  		String s = ""+obj;
  		char[] c = s.toCharArray();
  		for (int i = 0; i < c.length; i++) {
  			if (c[i]>'9' || c[i]<'0') {
  				return false;
  			}
  		}
  		return true;
  	}
    public static double getDouble(Object obj){
        double tmpvalue=0;
        try{
        	 if(obj!=null&&!"".equals(obj))
        	 {
        		 tmpvalue = Double.parseDouble(obj.toString());
        	 }
                
        }catch(Exception ex){
            log.error("getDouble :"+ex);
        }
        return tmpvalue;
    }
    public static double getDouble(Object obj,double defvalue){
            double tmpvalue=defvalue;
            try{
                    tmpvalue = Double.parseDouble(obj.toString());
            }catch(Exception ex){
                log.error("getDouble :"+ex);
            }
            return tmpvalue;
        }
        
    public static String getString(Object obj){
        String tmpret="";
        if(obj!=null)
            tmpret = obj.toString();
        return tmpret.trim();
    }
    public static String getStringWithUrlEncode(Object obj){
        String tmpret="";
        if(obj!=null){
         try{
            tmpret = URLDecoder.decode(obj.toString(),"utf-8");
         }catch(Exception ex){
             log.error("getStringWithUrlEncode:"+ex);
         }
        }
        return tmpret.trim();
    }
    public static java.sql.Date getSqlDate(Object obj){
        java.sql.Date tmpdate = null;
        if(obj!=null){
                try{
                    tmpdate = java.sql.Date.valueOf(obj.toString());
                }catch(Exception ex){
                    log.error("getDate :"+ex);
                }
        }
        return tmpdate;
    }
    public static java.sql.Date getSqlDate(String obj){
        java.sql.Date tmpdate = null;
        if(obj!=null){
                try{
                    tmpdate = java.sql.Date.valueOf(obj.toString());
                }catch(Exception ex){
                    log.error("getDate :"+ex);
                }
        }
        return tmpdate;
    }
    public static java.sql.Date getSqlDate(Object obj,java.sql.Date defvalue){
        java.sql.Date tmpdate = defvalue;
        if(obj!=null){
                try{
                    tmpdate = java.sql.Date.valueOf(obj.toString());
                }catch(Exception ex){
                    log.error("getDate :"+ex);
                }
        }
        return tmpdate;
    }
    
    /**
     * 获取当前时间
     * @return
     */
    public static int getCurrentUnixTime(){
    	int tmpCurrentTime = (int)(System.currentTimeMillis()/1000);
    	return tmpCurrentTime;
    }
    public static java.util.Date getCurrentTime(){
        Calendar   ca           =   Calendar.getInstance();
        java.util.Date   tmpnow     =   new java.util.Date(ca.getTimeInMillis());
        return tmpnow;
    }
    public static java.sql.Date getSqlCurrentTime(){
        Calendar   ca           =   Calendar.getInstance();
        java.sql.Date   tmpnow     =   new java.sql.Date(ca.getTimeInMillis());
        return tmpnow;
    }
    public static String getSqlCurrentTimeStr(String format){
        String tmpret="";
        try {
                Calendar   ca           =   Calendar.getInstance();
                SimpleDateFormat   sdf   = new SimpleDateFormat(format);
                tmpret = sdf.format(ca.getTime());
        }catch(Exception ex){
            log.error("getCurrentTimeStr:"+ex);
        }
        return tmpret;
    }
    public static String getSqlCurrentTimeStr(int seconds,String format){
        String tmpret="";
        try {
                Calendar   ca           =   Calendar.getInstance();
                ca.add(Calendar.SECOND, seconds);
                SimpleDateFormat   sdf   = new SimpleDateFormat(format);
                tmpret = sdf.format(ca.getTime());
        }catch(Exception ex){
            log.error("getCurrentTimeStr:"+ex);
        }
        return tmpret;
    }
    public static String formatDate(java.sql.Date date,String format){
        String tmpdatestr="";
        try{
                SimpleDateFormat f=new SimpleDateFormat(format);
                tmpdatestr =  f.format(date);
        }catch(Exception ex){
            log.error("formatDate :"+ex);
        }
        return tmpdatestr;
    }
    public static String formatDate(java.util.Date date,String format){
        String tmpdatestr="";
        try{
                SimpleDateFormat f=new SimpleDateFormat(format);
                tmpdatestr =  f.format(date);
        }catch(Exception ex){
            log.error("formatDate :"+ex);
        }
        return tmpdatestr;
    }
    public static java.util.Date getUtilDate(java.sql.Date date){
        java.util.Date tmpUtilDate=new java.util.Date(date.getTime());
        return tmpUtilDate;               
    }
    
    public static java.sql.Date getUtilDate(java.util.Date date,String format){
    	String tmpdatestr="";
        try{
                SimpleDateFormat f=new SimpleDateFormat(format);
                tmpdatestr =  f.format(date);
        }catch(Exception ex){
            log.error("formatDate :"+ex);
        }
        return new java.sql.Date(new Date(tmpdatestr).getTime());              
    }
    public static java.sql.Time getSqlTime(java.sql.Date date){
       java.sql.Time sTime=new java.sql.Time(date.getTime());
        return sTime;
    }
    public static java.sql.Timestamp getSqlTimeStamp(java.sql.Date date){
       java.sql.Timestamp stp=new java.sql.Timestamp(date.getTime());
        return stp;
    }
    public static String formatDouble(double number,String format){
        String tmpret="";
        try{
                DecimalFormat df = new DecimalFormat(format);
                df.format(number);
        }catch(Exception ex){
            log.error(" formatDobule:"+ex);
        }
        return tmpret;
    }
    
    
    
    public static Map removeNullObject(String string,Object[] object){
    	Map map = new HashMap();
    	String str = string;
    	Object[] obj = object;
    	map.put("string", str);
    	map.put("objects", obj);
    	if (string == null || "".equals(string)) {
			return map;
		}
    	String[] s = str.split("[?]");
    	if (s.length <= 1) {
    		return map;
		}
    	List list = new ArrayList();
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < s.length; i++) {
			if (i+1 < obj.length && obj[i] == null ) {
				//若下个有逗号的话，则去掉，若上个有=号的话，则去掉往前到空格或逗号的地方为止。
				s[i] = removePre(""+s[i]);
				s[i+1] = removeNext(""+s[i+1]);
			}
		}
    	
    	for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null || i==obj.length-1) {
				list.add(obj[i]);
			}
		}
    	
    	for (int i = 0; i < s.length; i++) {
    		if (s[i].trim().length()>1 && (s[i].trim().endsWith("+")||s[i].trim().endsWith("=")||s[i].trim().endsWith(",")||s[i].trim().endsWith("("))) {
    			sb.append(" "+s[i].trim()+"?");
			}else{
				sb.append(s[i].trim());
			}
		}
    	String sql = removeDouhao(sb.toString());
    	map.put("string", sql);
    	map.put("objects", list.toArray());
    	return map;
    	
    }
    
    private static String removeDouhao(String str){
    	String[] s = str.split("WHERE|where");
    	String string = s[0];
    	if (s[0].trim().endsWith(",")) {
			 string = new String(s[0].trim().toCharArray(),0,s[0].trim().toCharArray().length-1);
		}
    	return string+" WHERE "+s[1];
    }
    
    private static String removePre(String str){
    	if (str == null || str.trim().equals("") || !str.contains("=")) {
			return str;
		}
    	String regEx="\\w*\\s+=|\\w*=";  
    	Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        boolean result=m.find();
        if (result) {
        	str = str.trim();
        	char[] ch = str.toCharArray();
        	if (ch[ch.length-1] != '=') {
				return str;
			}
        label1:    for (int i = ch.length-1; i >= 0; i--) {
    				if (ch[i] == ' '|| ch[i] == ',') {
        					break label1;
    				}else
            		ch[i] = ' ';
    			}
            ch[ch.length-1] = ' ';
            	return new String(ch);
		}
    	return str;
    }
    
    private static String removeNext(String str){
    	if (str == null || str.trim().equals("") || !str.contains(",")) {
			return str;
		}
    	String regEx=",\\w|,\\W|,";  
    	Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        boolean result=m.find();
        if (result) {
        	return str.replaceFirst(",", " ");
		}

    	return str;
    }
    
    public static String formatInt(int val,int len){
    	String tmpstr="";
    	tmpstr = ""+val;
    	int tmporglen = tmpstr.length();
    	for(int i=tmporglen;i<len;i++)tmpstr = "0"+tmpstr;
    	return tmpstr;
    }
    public static java.util.Date parseDateString(String str,String format){
    	java.util.Date tmpret = null;
    	try{
    		SimpleDateFormat   sdf   =   new   SimpleDateFormat(format); 
    		tmpret  =   sdf.parse(str);
    	}catch(Exception ex){
    		
    	}
    	return tmpret;
    }
    public static void makesurePathExists(String    path){       
        java.io.File    file    =   new    java.io.File(path);       
       if(file.isDirectory()){       
                   
        }else{       
            file.mkdir();              
        }       
    }    
    public static int getByHourMiniuteSecond(String hms){    	
    	int tmpret =0;
    	if((hms!=null)&&(hms.length()==6)){
    		tmpret = SafeUtils.getInt(hms.substring(0,2))*3600
    		+SafeUtils.getInt(hms.substring(2,4))*60
    		+SafeUtils.getInt(hms.substring(4,6));    		
    	}
    	return tmpret;
    }
    public static String stripAllBlank(String str){
    	String tmpstr = "";
    	StringBuilder tmpsb = new StringBuilder();
    	int i;
    	for(i=0;i<str.length();i++){
    		if(str.charAt(i)!=' ')tmpsb.append(str.charAt(i));
    	}
    	tmpstr = tmpsb.toString();
    	tmpsb = null;
    	return tmpstr;
    }
    
    

    public static Map<String, String[]> getParamsMap(String queryString, String enc) {   
      Map<String, String[]> paramsMap = new HashMap<String, String[]>();   
      if (queryString != null && queryString.length() > 0) {   
        int ampersandIndex, lastAmpersandIndex = 0;   
        String subStr, param, value;   
        String[] paramPair, values, newValues;   
        do {   
          ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;   
          if (ampersandIndex > 0) {   
            subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);   
            lastAmpersandIndex = ampersandIndex;   
          } else {   
            subStr = queryString.substring(lastAmpersandIndex);   
          }   
          paramPair = subStr.split("=");   
          param = paramPair[0];   
          value = paramPair.length == 1 ? "" : paramPair[1];   
          try {   
            value = URLDecoder.decode(value, enc);   
          } catch (UnsupportedEncodingException ignored) {   
          }   
          if (paramsMap.containsKey(param)) {   
            values = paramsMap.get(param);   
            int len = values.length;   
            newValues = new String[len + 1];   
            System.arraycopy(values, 0, newValues, 0, len);   
            newValues[len] = value;   
          } else {   
            newValues = new String[] { value };   
          }   
          paramsMap.put(param, newValues);   
        } while (ampersandIndex > 0);   
      }   
          return paramsMap;
    }
    
    public  static String getMd5String(String str){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");	    	
	    	char hexDigits[] = {'0','1','2','3','4', '5', '6', '7','8','9','a', 'b', 'c','d','e','f'};
	    	byte[] source = str.getBytes("utf-8");
	    	md.update(source);
	    	byte[] tmp = md.digest();
	    	char[] chars = new char[32];
	    	for(int i=0,j=0;i< 16;i++){
	    		byte b = tmp[i];
	    		//取字节中高 4 位的数字转换
	    		chars[j++] = hexDigits[b>>>4 & 0xf];
	    		// 取字节中低 4 位的数字转换
	    		chars[j++] = hexDigits[b&0xf];
	    	}
	    	str= new String(chars);
		} catch (Exception e){
			log.error(e,e);
		}
		return str;
    }
    
    public static String getFormatDateTimeFromUnixTime(String str,String format){
		Integer tmpunixtime=0;
		try{
			if((format==null)||(format.equals("")))format = DATE_TIME_FORMAT;
			SimpleDateFormat   tmpfmt   =   new   SimpleDateFormat(format);
			Date tmpdate = tmpfmt.parse(str);
			
			tmpunixtime  = Math.round(tmpdate.getTime()/1000);
		}catch(Exception ex){
			
		}
		return getString(tmpunixtime);
	}
    
    public static String getFormatDateTimeFromUnixTime(String strunixtime){
		String tmpdatestr="";
		try{
			Integer unixtime=getInteger(strunixtime);
			if((unixtime!=null)&&(unixtime!=0))
			{
			Timestamp  tmpdate = new Timestamp (unixtime*1000L);
		SimpleDateFormat   tmpfmt   =   new   SimpleDateFormat(DATE_TIME_FORMAT); 
		tmpdatestr = tmpfmt.format(tmpdate);
			}
		}catch(Exception ex)
		{
			
		}
		return tmpdatestr;
	}
    
    public static String getFormatDateTimeFromUnixTime(Integer unixtime){
		String tmpdatestr="";
		try{
			if((unixtime!=null)&&(unixtime!=0))
			{
			Timestamp  tmpdate = new Timestamp (unixtime*1000L);
		SimpleDateFormat   tmpfmt   =   new   SimpleDateFormat(DATE_TIME_FORMAT); 
		tmpdatestr = tmpfmt.format(tmpdate);
			}
		}catch(Exception ex)
		{
			
		}
		return tmpdatestr;
	}
	public static Integer getUnixTimeFromString(String str,String format){
		Integer tmpunixtime=0;
		try{
			if((format==null)||(format.equals("")))format = DATE_TIME_FORMAT;
			SimpleDateFormat   tmpfmt   =   new   SimpleDateFormat(format);
			Date tmpdate = tmpfmt.parse(str);
			
			tmpunixtime  = Math.round(tmpdate.getTime()/1000);
		}catch(Exception ex){
			
		}
		return tmpunixtime;
	}
	/**
	 * 格式化多行文本输出，将\r\n替换为<br/>
	 * @param value
	 * @return
	 */
	public static String getDisplayLabelForMultilineText(String value){
		String tmpval = value;
		tmpval = tmpval.replace("\r\n","<br/>");
		return tmpval;
	}
	
	static String LocalIPFlag="";
	private static String getLocalIPFlag(){
		if (LocalIPFlag.length()>0) { return LocalIPFlag; }
		try {
			InetAddress[] inets=InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
			for (int i = 0; i < inets.length; i++) {
				int flag=inets[i].getAddress()[3]<<0  &  0xff ;
				if ( flag>=1 ) {
					 LocalIPFlag="000"+flag;
					 LocalIPFlag=LocalIPFlag.substring(LocalIPFlag.length()-2, LocalIPFlag.length());
				}
			}
		} catch (Exception e) {
			log.error(e,e);
		}
		return LocalIPFlag;
	}
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(getNextId());
		}
	}
	private static AtomicInteger idIntger = new AtomicInteger(1);
	public static long getNextId() {
		if (idIntger.get() >= 900) {
			idIntger.set(1);
		}
		String id="000"+idIntger.getAndIncrement();
		id=id.substring(id.length()-3, id.length());
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  getLong((System.currentTimeMillis() /1000) + "" +getLocalIPFlag()+ ""+ id);
	}
	private static AtomicInteger contentIdIntger = new AtomicInteger(1);
	
	public synchronized static long getNextContentIdId() {
		if (contentIdIntger.get() >= 100) {
			contentIdIntger.set(1);
		}
		
		try {
			Thread.sleep(10);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String str=""+System.currentTimeMillis();
		str=str.substring(0, str.length()-1);
		
		String str2="000"+contentIdIntger.getAndIncrement();
		str2=str2.substring(str2.length()-2, str2.length() );
		 
		return  getLong( str+getLocalIPFlag()+str2);
	}
	/**
	 * 解析FTP 全路径 分解为 5个字符串
	 * 1, user
	 * 2, password
	 * 3, ip
	 * 4. port
	 * 5. 文件路径
	 * <p>
	 * 作用描述：
	 * </p>
	 * <p>
	 * 修改说明：
	 * </p>
	 *@param str
	 *@return
	 */
	public static String[] getSingleMatchValue(String str )
	{
		String[] values=null;
		if (str==null) {
			return values;
		}
		 
		String[] strs=str.split("://");
		if (strs==null||strs.length==0) {
			return values;
		}
		if (strs==null||strs.length<2||!strs[0].equalsIgnoreCase("ftp")) {
			return values;
		}
		values=new String[5];
		String address=strs[1].replaceAll("//", "/");
		values[0]=address.substring(0, address.indexOf(':'));
		address=address.substring(address.indexOf(':')+1, address.length());
		String result="";
		Pattern p=Pattern.compile("@[0-9.:]{4,25}");
		Matcher m=p.matcher(address);
		if (m.find()&& m.toMatchResult().group()!=null) {
			result=m.toMatchResult().group().trim();
		}else{
				result="";
			}
		values[1]=address.split(result)[0] ;
		values[4]=address.split(result)[1];
		values[4]=values[4].substring( values[4].indexOf('/')+1);
		values[2]=result.substring( result.indexOf('@')+1);
		if (values[2].contains(":")) {
			String[] temp=values[2].split(":");
			values[2]=temp[0];
			values[3]=temp[1];
		}else{
			values[3]="21";
		}
		 
		return values;
	}
	
	public static String getCurrentTimeStr(String format) {
		String tmpret = "";
		try {
			Calendar ca = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			tmpret = sdf.format(ca.getTime());
		} catch (Exception ex) {
			log.error("getCurrentTimeStr:" + ex);
		}
		return tmpret;
	}
	
	
	
	
}
