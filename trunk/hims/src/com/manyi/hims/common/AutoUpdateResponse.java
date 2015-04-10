package com.manyi.hims.common;

import lombok.Data;

import com.manyi.hims.Response;

@Data
public class AutoUpdateResponse extends Response{
	private String url;//软件app下载地址
	
	private String version;  //服务器版本号
	
	private boolean ifForced; //是否强制更新
	
	private String message; //是否强制更新
	
}
