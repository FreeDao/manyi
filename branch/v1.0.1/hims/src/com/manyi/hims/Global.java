/**
 * 
 */
package com.manyi.hims;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manyi.hims.util.ConfigMap;
import com.manyi.hims.util.PropertiesHelper;

/**
 * @author leo.li
 * 
 */
public enum Global {

	RELEASE("http", "www.yemadai.com"), DEV("http", "192.168.1.2");

	private static final Logger logger = LoggerFactory.getLogger(Global.class);

	// 时间间隔
	@SuppressWarnings("unused")
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;// 一天

	public static final long TEN_MINUTES = 10 * 60 * 1000;// 10分钟

	/**************************************** 全局常量 （请务必遵守编码契约） *********************************************/

	public final static String ORDER_MODE_ASC = "asc";
	public final static String ORDER_MODE_DESC = "desc";

	// 不同用途的常量请空行分隔

	public final static String SESSION_UID_KEY = "mp_user_id";

	public final static String SESSION_FINDPWD_KEY = "verifyCode";

	public static final String REGIST_MSG_CODE = "regist_msg_code";

	// 应用被禁用
	public final static int APP_STATUS_DISABLE = 20000001;

	// 应用鉴权失败
	public final static int APP_AUTH_FAILED = 20000002;

	// 时间有误，请同步您的时间
	public final static int TIME_ERROR = 20000003;

	public final static String RESTFULL_PATH_PRE = "/rest";

	/**************************************** 全局常量 （请务必遵守编码契约） *********************************************/

	/**************************************** 短信验证码 *********************************************/
	public final static String urls = "http://www.ztsms.cn:8800/sendXSms.do?"; // 提交url，12小时不可重复提交

	public final static String url = "http://www.ztsms.cn:8800/sendSms.do?";// 提交url，可重复提交
	/** 验证码 **/
	public final static String username = "manyi";// 用户名

	public final static String password = "rxJnRY5e";// 密码

	public final static String productid = "676767";// 产品id
	/** 验证码 ***/

	/** 活动 ***/
	// public final static String username = "manyiyx";//用户名
	//
	// public final static String password = "My123456";//密码
	//
	// public final static String productid = "411623";//产品id
	/** 活动 ***/
	// public final static String productid = "676766";//产品id

	public final static String registBeforeV = "房源宝找回密码验证码，校验码是：";
	public final static String registAfterV = ",如非本人操作，请忽略本短信。【房源宝】";
	public final static String registSUCCESS = "您的房源宝账号已审核通过，赶快登录吧。【房源宝】";
	public final static String registFILD = "您的房源宝账号未通过审核，请登录查看原因。【房源宝】";
	/**************************************** 短信验证码 *********************************************/

	/************************************** 阿里云 存储 ***********************************************/
	public final static String ACCESSKEYID = "";
	public final static String ACCESSKEYSECRET = "";
	public final static String BUCKETNAME = "fangyuanbao-user-code-cord";
	/************************************** 阿里云 存储 ***********************************************/

	/***************************** 允许通过的主机 ***************************************************/
	public final static String ALLOW_HOST;

	public final static String PIC_BASE_URL;

	public final static String PUSH_SERVER_URL;
	
	/**
	 * 再审核的间隔时间
	 */
	public final static int CHECK_AGAIN_MIDTIME;
	/**
	 * 再审核最大的次数
	 */
	public final static int CHECK_AGAIN_TIMES;

	/************************************** 发送邮件 ***********************************************/
	public final static String MAIL_USERNAME;

	public final static String MAIL_PASSWORD;

	public final static String SMTP_SERVER;

	public final static String MAIL_USER_FANG;

	public final static String MAIL_USER_ZHENG;

	public final static String MAIL_USER_TAOYE;

	public final static String MAIL_USER_LEO;
	/************************************** 发送邮件 ***********************************************/

	/************************************** 经纪人查看日限月限 ***********************************************/
	public final static int DETAIL_DAY_LIMIT;

	public final static int DETAIL_MONTH_LIMIT;
	/************************************** 经纪人查看日限月限 ***********************************************/

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

	Global(String protocol, String hostname) {
		this.protocol = protocol;
		this.hostname = hostname;
		this.domainUrl = protocol + "://" + hostname;

	}

	public static final Global getInstance() {
		return Global.DEV;
	}

	static {
		ALLOW_HOST = Config.get("S_constants.allowHost");
		PIC_BASE_URL = Config.get("S_constants.picBaseUrl");
		PUSH_SERVER_URL = Config.get("S_constants.PUSH_SERVER_URL");
		MAIL_USERNAME = Config.get("S_mail_userName");
		MAIL_PASSWORD = Config.get("S_mail_password");
		SMTP_SERVER = Config.get("S_smtp_server");
		MAIL_USER_FANG = Config.get("S_mail_user_fang");
		MAIL_USER_ZHENG = Config.get("S_mail_user_zheng");
		MAIL_USER_LEO = Config.get("S_mail_user_leo");
		MAIL_USER_TAOYE = Config.get("S_mail_user_taoye");
		DETAIL_DAY_LIMIT = Config._get("I_constants.detailDayLimit");
		DETAIL_MONTH_LIMIT = Config._get("I_constants.detailMonthLimit");
		CHECK_AGAIN_MIDTIME = Config._get("I_constants.checkAgainMidTime");
		CHECK_AGAIN_TIMES = Config._get("I_constants.checkAgainTimes");
		
		

	}

	private static class Config {

		static ConfigMap configMap = new ConfigMap();
		static {
			try {
				configMap = PropertiesHelper.loadProperties("/META-INF/hims.properties");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("The current application of information in the [/META-INF/hims.properties] configuration:current.server");
			}
		}

		public static String get(String key) {
			return configMap.getKV(key, StringUtils.EMPTY);
		}

		public static int _get(String key) {
			return configMap.getKV(key, 0);
		}
	}
}
