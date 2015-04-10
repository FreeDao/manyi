package com.manyi.fyb.callcenter.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	private static final Logger logger = LoggerFactory.getLogger(Constants.class);

	public static String LOGIN_SESSION;

	public static String LINK_FRONT_PART;
	
	public static String UC_FRONT_PART;
	
	public static String UC_ORG_IDENTITY;
	
	public static String HIMS_SERVICE_PATH;
	
	public static String ALIYUN_IMAGE_PATH_PREFIX;
	
	public static String BAIDU_MAP;
	
	public static String TENCENT_MAP;
	
	public static String GAODE_MAP;
	
	public static String SOU_FAN;
	
	public static String SOU_FAN_SH;
	public static String SOU_FAN_GZ;
	public static String SOU_FAN_SZ;
	
	public static String BAIDU_API_MAP;
	
	/************************************** 阿里云 存储 ***********************************************/
	public final static String ACCESSKEYID ;
	public final static String ACCESSKEYSECRET ;
	public final static String BUCKETNAME ;
	public final static int ALIYUN_IMAGE_PATH_TIMEOUT ;
	

	static {
		LOGIN_SESSION = Config.get("S_constants.login_session");
		LINK_FRONT_PART = Config.get("S_constants.link_front_part");
		UC_FRONT_PART = Config.get("S_constants.uc_front_part");
		UC_ORG_IDENTITY = Config.get("S_constants.uc_org_identity");
		HIMS_SERVICE_PATH = Config.get("S_constants.hims_service_path");
		ALIYUN_IMAGE_PATH_PREFIX = Config.get("S_constants.aliyun_image_path_prefix");
		
		BAIDU_MAP = Config.get("S_constants.baidu_map");
		TENCENT_MAP = Config.get("S_constants.tencent_map");
		GAODE_MAP = Config.get("S_constants.gaode_map");
		SOU_FAN = Config.get("S_constants.sou_fan");
		SOU_FAN_SH = Config.get("S_constants.sou_fan_sh");
		SOU_FAN_GZ = Config.get("S_constants.sou_fan_gz");
		SOU_FAN_SZ = Config.get("S_constants.sou_fan_sz");
		BAIDU_API_MAP = Config.get("S_constants.baidu_api_map");
		
		ACCESSKEYID = Config.get("S_constants.ACCESSKEYID");
		ACCESSKEYSECRET = Config.get("S_constants.ACCESSKEYSECRET");
		BUCKETNAME = Config.get("S_constants.BUCKETNAME");
		ALIYUN_IMAGE_PATH_TIMEOUT = Config.getInt("I_constants.aliyun_image_path_timeout");
	}

	private static class Config {

		static ConfigMap configMap = new ConfigMap();
		static {
			try {
				configMap = PropertiesHelper.loadProperties("/META-INF/callcenter.properties");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("The current application of information in the [/META-INF/callcenter.properties] configuration:current.server");
			}
		}

		public static String get(String key) {
			return configMap.getKV(key, StringUtils.EMPTY);
		}
		
		public static int getInt(String key) {
			return configMap.getKV(key, 0);
		}
	}
}
