/**
 * 
 */
package com.ecpss.mp;

/**
 * @author leo.li
 *
 */
public enum Global {
	RELEASE("http","www.yemadai.com"),
	DEV("http","192.168.1.2");
	
	/**************************************** 全局常量 （请务必遵守编码契约）  *********************************************/
	
    public final static String ORDER_MODE_ASC = "asc";
    public final static String ORDER_MODE_DESC = "desc";
    
    //不同用途的常量请空行分隔
    
    public final static String SESSION_UID_KEY = "mp_user_id";
    
    /**************************************** 全局常量 （请务必遵守编码契约）  *********************************************/
	
	
	private String protocol;
	private String hostname;
	private String domainUrl;

	public String getProtocol() {
		return protocol;
	}


	public String getHostname() {
		return hostname;
	}


	public String getDomainUrl() {
		return domainUrl;
	}

	
	Global(String protocol,String hostname){
		this.protocol = protocol;
		this.hostname = hostname;
		this.domainUrl = protocol + "://" + hostname;
	}
	
	public static final Global getInstance(){
		return Global.DEV;
	}
}
