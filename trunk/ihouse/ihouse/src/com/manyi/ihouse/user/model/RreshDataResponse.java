package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;

/**
 * 获取最新全局数据
 * Function: TODO ADD FUNCTION
 *
 * @author   hubin
 * @Date	 2014年6月16日		下午6:29:43
 *
 * @see
 */
public class RreshDataResponse extends Response{
    
    private int seekHouseNum; // 看房单数量
    
    private int appointNum; //行程数量
    
    private boolean myupdate; //我的是否有更新
    
    private int recentEnd; //最新完成 0：无 1：有

    public int getSeekHouseNum() {
        return seekHouseNum;
    }

    public void setSeekHouseNum(int seekHouseNum) {
        this.seekHouseNum = seekHouseNum;
    }

    public int getAppointNum() {
        return appointNum;
    }

    public void setAppointNum(int appointNum) {
        this.appointNum = appointNum;
    }

    public boolean isMyupdate() {
        return myupdate;
    }

    public void setMyupdate(boolean myupdate) {
        this.myupdate = myupdate;
    }

    public int getRecentEnd() {
        return recentEnd;
    }

    public void setRecentEnd(int recentEnd) {
        this.recentEnd = recentEnd;
    }
    
    
}
