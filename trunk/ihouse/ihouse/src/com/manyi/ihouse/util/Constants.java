package com.manyi.ihouse.util;

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

	static {
		LOGIN_SESSION = Config.get("S_constants.login_session");
		LINK_FRONT_PART = Config.get("S_constants.link_front_part");
		UC_FRONT_PART = Config.get("S_constants.uc_front_part");
		UC_ORG_IDENTITY = Config.get("S_constants.uc_org_identity");
		HIMS_SERVICE_PATH = Config.get("S_constants.hims_service_path");
		ALIYUN_IMAGE_PATH_PREFIX = Config.get("S_constants.aliyun_image_path_prefix");
	}

	private static class Config {

		static ConfigMap configMap = new ConfigMap();
		static {
			try {
				configMap = PropertiesHelper.loadProperties("/META-INF/ihouse.properties");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("The current application of information in the [/META-INF/ihouse.properties] configuration:current.server");
			}
		}

		public static String get(String key) {
			return configMap.getKV(key, StringUtils.EMPTY);
		}
	}
}
