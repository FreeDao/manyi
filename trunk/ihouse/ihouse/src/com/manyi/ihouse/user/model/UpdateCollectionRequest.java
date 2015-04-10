package com.manyi.ihouse.user.model;

/**
 * 
 * Function: 房源同步
 *
 * @author   hu-bin
 * @Date	 2014年6月24日		下午6:54:52
 *
 * @see
 */
public class UpdateCollectionRequest {
    
    private long userId; //用户ID
    
    private String[] houseIds; //房源id 多个是用半角“，”分开

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String[] getHouseIds() {
        return houseIds;
    }

    public void setHouseIds(String[] houseIds) {
        this.houseIds = houseIds;
    }

}
