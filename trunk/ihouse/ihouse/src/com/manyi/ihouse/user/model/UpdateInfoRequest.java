package com.manyi.ihouse.user.model;

/**
 * 
 * Function: 更新检查需传递终端类型
 *
 * @author   hu-bin
 * @Date	 2014年6月19日		下午3:43:01
 *
 * @see
 */
public class UpdateInfoRequest {
    
    /**
     * 终端类型 1：android 2：iphone
     */
    private int type;
    
    /**
     * 渠道号
     */
    private int channel;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
    

}
