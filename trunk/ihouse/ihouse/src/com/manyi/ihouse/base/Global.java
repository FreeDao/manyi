/**
 * 
 */
package com.manyi.ihouse.base;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.apache.commons.lang.StringUtils;

import com.manyi.ihouse.util.ConfigMap;
import com.manyi.ihouse.util.PropertiesHelper;

/**
 * @author leo.li
 * 
 */
public enum Global {
	RELEASE("http", "www.yemadai.com"), DEV("http", "192.168.1.2");
	
	//时间间隔
	 private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;//一天
	
	static {
		Calendar calendar = Calendar.getInstance();
		/*** 定制每日00:01:00 执行方法 ***/
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 1);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime(); // 第一次执行定时任务的时间

		// 如果第一次执行定时任务的时间 小于 当前的时间
		// 此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		if (date.before(new Date())) {
			Calendar startDT = Calendar.getInstance();
			startDT.setTime(date);
			startDT.add(Calendar.DAY_OF_MONTH, 1);
			date = startDT.getTime();
		}
		Timer timer = new Timer();
		NFDFlightDataTimerTask task = new NFDFlightDataTimerTask();
		// 安排指定的任务在指定的时间开始进行重复的固定延迟执行。
		timer.schedule(task, date, PERIOD_DAY);
		System.out.println("定时任务已经准备完毕...");
	}
	

	/**************************************** 全局常量 （请务必遵守编码契约） *********************************************/

	/**客服电话*/
	public final static String SERVICE_TELEPHONE = "400-700-6622";
	
	public final static String ORDER_MODE_ASC = "asc";
	public final static String ORDER_MODE_DESC = "desc";

	// 不同用途的常量请空行分隔

	public final static String SESSION_UID_KEY = "mp_user_id";
	
	public final static String SESSION_FINDPWD_KEY = "verifyCode";
	
	public static final String REGIST_MSG_CODE="regist_msg_code";
	
	// 应用被禁用
	public final static int APP_STATUS_DISABLE = 20000001;

	// 应用鉴权失败
	public final static int APP_AUTH_FAILED = 20000002;

	public final static String RESTFULL_PATH_PRE = "/rest";

	/**************************************** 全局常量 （请务必遵守编码契约） *********************************************/
	
	
	/**************************************** 短信验证码 *********************************************/
	public final static String urls = "http://www.ztsms.cn:8800/sendXSms.do?"; //提交url，12小时不可重复提交
	
	public final static String url = "http://www.ztsms.cn:8800/sendSms.do?";//提交url，可重复提交
	
	public final static String username = "manyi";//用户名
	
	public final static String password = "rxJnRY5e";//密码
	
//	public final static String productid = "676766";//产品id
	public final static String productid = "676767";//产品id
	
	public final static String registBeforeV = "爱屋找回密码验证码，校验码是：";
	public final static String registAfterV = ",如非本人操作，请忽略本短信。【爱屋】";
	/**************************************** 短信验证码 *********************************************/

	/**************************************阿里云 存储***********************************************/
    public final static String ACCESSKEYID ;
    public final static String ACCESSKEYSECRET ;
    public final static String BUCKETNAME ;
    public final static int aliyun_image_path_timeout ;
	/**************************************阿里云 存储***********************************************/
	
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
        
        ACCESSKEYID = Config.get("S_constants.ACCESSKEYID");
        ACCESSKEYSECRET = Config.get("S_constants.ACCESSKEYSECRET");
        BUCKETNAME = Config.get("S_constants.BUCKETNAME");
        aliyun_image_path_timeout = Config._get("I_constants.aliyun_image_path_timeout");
        


    }

    private static class Config {

        static ConfigMap configMap = new ConfigMap();
        static {
            try {
                configMap = PropertiesHelper.loadProperties("/META-INF/ihouse.properties");
            } catch (Exception e) {
                e.printStackTrace();
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
