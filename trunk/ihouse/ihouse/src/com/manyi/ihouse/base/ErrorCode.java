package com.manyi.ihouse.base;

/**
 */
public enum ErrorCode {
	;
	/**************************************** error code *********************************************/
	// 20xxxx App用户操作 21xxxx 经纪人操作  22xxxx 其他

	/** 手机号码不能为空 */
	public static final int USER_ERROR200001 = 200001;
	/**手机号码格式不正确*/
	public static final int USER_ERROR200002 = 200002;
	/**短信发送失败*/
	public static final int USER_ERROR200003 = 200003;
	/**手机号码错误*/
	public static final int USER_ERROR200004 = 200004;
	/**验证码错误*/
	public static final int USER_ERROR200005 = 200005;
	/**用户登录超时或没有登陆*/
	public static final int USER_ERROR200006 = 200006;
	/**验证码超时*/
	public static final int USER_ERROR200007 = 200007;
    /**房源不可重复收藏*/
    public static final int USER_ERROR200008 = 200008;
    /**用户不存在*/
    public static final int USER_ERROR200009 = 200009;
	
	/**区域信息为空**/
	public static final int MAP_ERROR400001 = 400001;
	/** 用户坐标信息为空**/
	public static final int MAP_ERROR400002 = 400002;
	/**************************************** error code *********************************************/

}