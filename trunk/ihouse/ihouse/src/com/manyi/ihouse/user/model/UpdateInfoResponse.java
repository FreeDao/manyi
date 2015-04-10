package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;

/**
 * 
 * Function: 更新版本信息
 *
 * @author   hubin
 * @Date	 2014年6月16日		下午9:39:22
 *
 * @see
 */
public class UpdateInfoResponse extends Response{
    
    /**
     * 最新版本号
     */
    private String version;
    
    /**
     * 是否需要强制更新 0：不需要  1：强制更新
     */
    private int forceUpdate;
    
    /**
     * 更新下载地址
     */
    private String downloadUrl;;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    } 
    
}
