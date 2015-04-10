package com.manyi.fyb.callcenter.check.model;

import lombok.Data;


@Data
public class CheckReq {
	private int checkType=-1;//审核 类型(1,发布出售;2,发布出租;3,改盘;4,举报;5,客服轮询;6,抽查看房)
	private String operServiceName;//客服名字
	private String operServiceState;//客服state
	private String startPublishDate;//发布时间
	private String endPublishDate;//发布时间
	private String publishName;//发布人
	private int page;//当前第几页
	private int rows;//每一页多少
	private String orderby =" log.createTime desc ";//排序规则
	private int employeeId;//登录人ID
	
	/**
	 * cityType城市类型
	 */
	private int cityType;
	
}
