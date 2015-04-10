package com.manyi.ihouse.user.model;

/**
 * 获取我的页面信息的request请求
 *
 * @author   hubin
 * @Date	 2014年6月16日		下午5:25:52
 *
 * @see
 */
public class MyRequest {
    
    /**
     * 用户ID
     */
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
