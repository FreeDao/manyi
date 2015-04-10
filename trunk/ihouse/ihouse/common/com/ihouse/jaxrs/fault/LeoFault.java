/**
 * 
 */
package com.ihouse.jaxrs.fault;

import java.io.PrintStream;
import java.util.Locale;

/**
 * @author hongci.li
 *
 */
public class LeoFault extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public final static String DEFUALT_LANGUAGE = "en_US";
    
//  public static final int Empty_USERNAME_PASSWORD = 1600;//用户名和密码不能为空
//  public static final int INVALID_LASTNAME = 1601;//用户姓氏不正确
//  public static final int INVALID_FIRSTNAME = 1602;//FirstName长度错误
//  public static final int INVALID_ADDRESS = 1603;//address长度错误
//  public static final int INVALID_DTYPE = 1604;//DTYPE长度错误
//  
//  public static final int INVALID_TOKEN = 1000;
//  public static final int NO_METHOD = 1101;
//  public static final int SYSTEM_ERROR = 9000;
//  public static final int INVALID_PARAMETER = 1080;
//  public static final int INVALID_EMAIL = 1110;
//  public static final int INVALID_USERNAME = 1105;
//  public static final int INVALID_PASSWORD = 1106;
//  public static final int USER_EXISTED = 1112;
//  public static final int USER_NOT_EXISTED = 1113;
//  public static final int INVALID_APPID = 1002;
//  public static final int INVALID_APPSIG = 1003;//appsig 无效
//  
//  public static final int USER_AUTH_FAILED = 1201;//用户身份验证失败
//  
//
//  public static final int INVALID_FILE_TYPE = 1332;
//  public static final int NULL_TITLE = 1335;
//
//  public static final int INVALID_PAGE = 1360;
//  public static final int INVALID_PAGE_COUNT = 1361;
//  
//  public static final int DESTINATION_NOT_SUPPORTED = 1400;
//  public static final int INVALID_TRAN_ID = 1420;
//  public static final int NULL_RETURN_VALUE = 1421;
//  public static final int NOUPLOAD_OR_SIZEISNULL=1331;
//  
//  public static final int NO_PHOTO_TO_SHARE=1500;
//  public static final int PHOTO_URL_OUT_OF_BOUND=1510;
    
    
    public static final int SYSTEM_ERROR = 1;
    public static final int INVALID_TOKEN = 2;
    public static final int INVALID_APPID = 3;
    public static final int INVALID_APPSIG = 4;
    public static final int OPTIMISTIC_LOCKING_ERROR =  5;
    
    
    private int errorCode;
    private Locale locale;
    /**
     * @return the rc
     */
    public int getErrorCode() {
        return this.errorCode;
    }

    /**
     * @param rc
     *            the rc to set
     */
    protected void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getKey(){
    	return "ec_" + getErrorCode();
    }



    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getMessage() {
        return FaultUtil.getValue(locale,"ec_" + errorCode);
    }
    
    public String getMessage(Locale locale) {
        return FaultUtil.getValue(locale,"ec_" + errorCode);
    }
    
    public LeoFault(int errorCode) {
        //super(new Message("ec_" + errorCode, FaultUtil.getRcResourceBundle(language)));
        //super(FaultUtil.getRcResourceBundle(language).getString("ec_" + errorCode));
        this.setErrorCode(errorCode);
    }
    
    public LeoFault(int errorCode,Throwable cause) {
        //super(new Message("ec_" + errorCode, FaultUtil.getRcResourceBundle()),cause);
        super(cause);
        this.setErrorCode(errorCode);
    }
    
    public StackTraceElement[] getStackTrace() {
        StackTraceElement[] srcs = super.getStackTrace();
        int length = srcs.length<5?srcs.length:5;
        StackTraceElement[] dest = new StackTraceElement[length];
        System.arraycopy(srcs, 0, dest, 0, length);
        return dest;
    }
    
    //add by chenmeizhen 2008.12.08
    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            s.println(this);
            StackTraceElement[] trace = super.getStackTrace();
            for (int i=0; i < trace.length && i<5; i++)//not exceeds 5 lines
                s.println("\tat " + trace[i]);
       
        }
    }   

}
