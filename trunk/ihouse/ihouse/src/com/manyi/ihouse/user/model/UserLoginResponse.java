package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;

/**
 * 登陆验证Response模型
 * @author hubin
 *
 */
public class UserLoginResponse extends Response{
	
	private long userId; // 用户ID
	
	private String name; // 用户姓名
	
	private int gender;//性别 1：男，2：女， 3:保密

	private int seekHouseNum; // 看房单数量
	
	private int appointNum; //行程数量（待确认）
    
    private int recentEnd; //最新完成 0：无 1：有
	
	private boolean myupdate; //我的是否有更新    
	
    private String assigneeName; //经纪人姓名
    
    private String assigneeTel; //经纪人电话
    
    private String assigneePhotoUrl; //经纪人照片URL

    private float score; //经纪人评分

	public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public int getRecentEnd() {
        return recentEnd;
    }

    public void setRecentEnd(int recentEnd) {
        this.recentEnd = recentEnd;
    }

    public boolean isMyupdate() {
		return myupdate;
	}

	public void setMyupdate(boolean myupdate) {
		this.myupdate = myupdate;
	}

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getAssigneeTel() {
        return assigneeTel;
    }

    public void setAssigneeTel(String assigneeTel) {
        this.assigneeTel = assigneeTel;
    }

    public String getAssigneePhotoUrl() {
        return assigneePhotoUrl;
    }

    public void setAssigneePhotoUrl(String assigneePhotoUrl) {
        this.assigneePhotoUrl = assigneePhotoUrl;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
