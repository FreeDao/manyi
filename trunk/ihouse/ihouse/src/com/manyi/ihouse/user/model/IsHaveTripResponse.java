package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;

/**
 * 
 * 是否有行程
 *
 * @author   hubin
 * @Date	 2014年6月16日		下午6:41:01
 *
 * @see
 */
public class IsHaveTripResponse extends Response{
    
    /**
     * 是否有行程 
     * 0-没有；1-有
     */
    private int isHave; 

    public int getIsHave() {
        return isHave;
    }

    public void setIsHave(int isHave) {
        this.isHave = isHave;
    }
    

}
