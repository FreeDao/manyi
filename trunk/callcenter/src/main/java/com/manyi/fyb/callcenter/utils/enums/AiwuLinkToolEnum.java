package com.manyi.fyb.callcenter.utils.enums;


public enum AiwuLinkToolEnum {
	

		UNUSED(0, "-"),

		/**
		 * 1.百度地图
		 */
		BAIDU(1, "百度地图"),

		/**
		 * 2.腾讯地图
		 */
		TENCENT(2, "腾讯地图"),

		/**
		 * 3.高德地图
		 */
		GAODE(3, "高德地图"),

		/**
		 * 4.搜房
		 */
		SOUFAN(4, "搜房"),
		
		/**
		 * 5.百度API
		 */
		BAIDU_API(5, "百度API ");

		private int value;
		private String desc;

		private AiwuLinkToolEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static AiwuLinkToolEnum getByValue(int value) {
			for (AiwuLinkToolEnum ve : values()) {
				if (value == ve.value) {
					return ve;
				}
			}
			return UNUSED;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

}
