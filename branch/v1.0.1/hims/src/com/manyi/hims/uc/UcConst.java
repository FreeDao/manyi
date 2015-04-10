package com.manyi.hims.uc;

public enum UcConst {
	;
	public final static int ERROR_10100001 = 10100001;
	public final static String SESSION_UID_KEY = "uid";

	/**************************************** error code *********************************************/
	// 10xxxx UC  11xxxx SELL  12xxxx RANT

	public static final int UC_EMPTY_USERNAME_PASSWORD = 100001;// 用户名和密码不能为空
	
	public static final int UC_ERROR100002 = 100002;//您的账号正在审核中
	public static final int UC_ERROR100003 = 100003;//不能为空  
	public static final int UC_ERROR100004 = 100004;// 该手机号未注册！
	public static final int UC_ERROR100005 = 100005;//请输入正确的验证码
	public static final int UC_ERROR100006 = 100006;//请输入新密码
	public static final int UC_ERROR100007 = 100007;//今日查看房源次数用尽
	public static final int UC_ERROR100008 = 100008;//该房源还在审核中
	public static final int UC_ERROR100009 = 100009;//该小区已存在
	public static final int UC_ERROR100010 = 100010;//请输入小区名
	public static final int UC_ERROR100011 = 100011;//请选择区域板块
	public static final int UC_ERROR100012 = 100012;//请输入小区地址
	public static final int UC_ERROR100013 = 100013;//该房源在审核中
	public static final int UC_ERROR100014 = 100014;//该房源已有改盘在审核中
	public static final int UC_ERROR100015 = 100015;//该房源已有举报在审核中
	public static final int UC_ERROR100016 = 100016;//请填写举报理由
	public static final int UC_ERROR100017 = 100017;//短信发送失败
	public static final int UC_ERROR100018 = 100018;//该帐号已禁用
	public static final int UC_ERROR100019 = 100019;//帐号密码不正确
	public static final int UC_ERROR100020 = 100020; //支付宝账号已经被绑定
	public static final int UC_ERROR100021 = 100021; //支付宝账号格式不对
	
	public static final int UC_ERROR100025 = 100025;//帐号处于非审核失败状态
	public static final int UC_ERROR100026 = 100026;//请不要输入非法字符

	
	//tiger
	public static final int UC_EMPTY_REGIST_1000031 = 1000031;// 该手机号码已经注册!
	public static final int UC_EMPTY_REGIST_1000032 = 1000032;// 验证码不正确!
	public static final int UC_EMPTY_REGIST_1000033 = 1000033;// 必选项不能为空!
	public static final int UC_EMPTY_REGIST_1000034 = 1000034;// 手机号码格式不正确!
	public static final int UC_EMPTY_REGIST_1000035 = 1000035;// 身份证号码格式不正确!
	public static final int UC_EMPTY_REGIST_1000036 = 1000036;// 注册失败
	public static final int UC_EMPTY_REGIST_1000037 = 1000037;// 请输入正确的验证码
	public static final int UC_EMPTY_REGIST_1000038 = 1000038;// 注册验证码发送失败
	public static final int UC_EMPTY_REGIST_1000039 = 1000039;// 网络不稳定,图片上传失败
	public static final int UC_EMPTY_REGIST_1000040 = 1000040;// 密码格式为6-20位数字+字母组合
	
	public static final int UC_EMPTY_REGIST_1000041 = 1000041;// 请输入真实姓名
	public static final int UC_EMPTY_REGIST_1000042 = 1000042;// 请输入工作区域
	public static final int UC_EMPTY_REGIST_1000043 = 1000043;// 请输入身份证号
	public static final int UC_EMPTY_REGIST_1000044 = 1000044;// 请为身份证拍照
	public static final int UC_EMPTY_REGIST_1000045 = 1000045;// 请为名片拍照
	public static final int UC_EMPTY_REGIST_1000046 = 1000046;// 该身份证号已经注册!
	public static final int UC_EMPTY_REGIST_1000047 = 1000047;// 真实姓名至少包含两个字符
	public static final int UC_EMPTY_REGIST_1000048 = 1000048;// 请输入手机号
	public static final int UC_EMPTY_REGIST_1000049 = 1000049;// 该手机号码未注册!
	public static final int UC_EMPTY_REGIST_1000050 = 1000050;// 请输入正确的邀请码!
	
	//tiger

	/**************************************** error code *********************************************/

}