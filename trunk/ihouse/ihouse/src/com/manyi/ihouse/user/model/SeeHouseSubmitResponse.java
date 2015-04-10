package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;

/**
 * 
 * Function: 提交约看返回数据
 *
 * @author   hubin
 * @Date	 2014年6月18日		下午6:57:04
 *
 * @see
 */
public class SeeHouseSubmitResponse extends Response{
    
    private String seeHouseId;//看房单号

    public String getSeeHouseId() {
        return seeHouseId;
    }

    public void setSeeHouseId(String seeHouseId) {
        this.seeHouseId = seeHouseId;
    }

}
