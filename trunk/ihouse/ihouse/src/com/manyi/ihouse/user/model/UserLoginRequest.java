package com.manyi.ihouse.user.model;

/**
 * 登陆验证request模型
 * @author hubin
 *
 */
public class UserLoginRequest {
	
	private String mobile; //手机号
	
	private String verifyCode; //验证码
    
    private String mobileSn; //手机设备唯一标识(或GUID)
    
    private String[] houseIds; //离线收藏的房源id

	public String[] getHouseIds() {
        return houseIds;
    }

    public void setHouseIds(String[] houseIds) {
        this.houseIds = houseIds;
    }

    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

    public String getMobileSn() {
        return mobileSn;
    }

    public void setMobileSn(String mobileSn) {
        this.mobileSn = mobileSn;
    }


}
