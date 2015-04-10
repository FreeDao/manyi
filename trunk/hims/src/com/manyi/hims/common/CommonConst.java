package com.manyi.hims.common;

public enum CommonConst {
	;

	/**************************************** error code *********************************************/
	// 10xxxx UC

	//公共模块的 error_code
	//tiger
	public static final int COMMON_ESTATE_9000001 = 9000001;//小区名称不能为空
	public static final int COMMON_ESTATE_9000002 = 9000002;//城市id不能为空
	public static final int COMMON_9000051 = 9000051;//系统异常,请检查数据
	
	public static final int HOUSE_PUBLISH_1100032 = 1100032;//请输入业主姓名
	public static final int HOUSE_PUBLISH_1100033 = 1100033;//请输入联系电话
	public static final int HOUSE_PUBLISH_1100034 = 1100034;//请输入价格
	public static final int HOUSE_PUBLISH_1100035 = 1100035;//请输入房型
	public static final int HOUSE_PUBLISH_1100036 = 1100036;//请输入面积
	public static final int HOUSE_PUBLISH_1100037 = 1100037;//请输入房型
	public static final int HOUSE_PUBLISH_1100038 = 1100038;//请输入改盘理由
	public static final int HOUSE_PUBLISH_1100039 = 1100039;//楼栋号须为纯数字
	public static final int HOUSE_PUBLISH_1100040 = 1100040;//室号为3-4位数字
	public static final int HOUSE_PUBLISH_1100041 = 1100041;//该小区不存在,请重新搜索
	public static final int HOUSE_PUBLISH_1100042 = 1100042;//联系人姓名至少包含两个字符
	public static final int HOUSE_PUBLISH_1100051 = 1100051;//楼栋号不能以0开头
	public static final int HOUSE_PUBLISH_1100080 = 1100080;//请输入合法的面积
	public static final int HOUSE_PUBLISH_1100081 = 1100081;//请输入合法的价格
	public static final int HOUSE_PUBLISH_1100082 = 1100082;//楼栋号不能以0开头
	public static final int HOUSE_PUBLISH_1100083 = 1100083;//单元号不能以0开头
	
	public static final int CHECK_PUBLISH_1100043 = 1100043;//该房源已有改盘在审核中
	public static final int CHECK_PUBLISH_1100044 = 1100044;//该房源已有举报在审核中
	public static final int CHECK_PUBLISH_1100045 = 1100045;//该房源已有出售在审核中
	public static final int CHECK_PUBLISH_1100046 = 1100046;//该房源已有出租在审核中
	public static final int CHECK_PUBLISH_1100051 = 1100051;//该房源正在审核中
	
	public static final int CHECK_PUBLISH_1100047 = 1100047;//请选择出租状态
	public static final int CHECK_PUBLISH_1100048 = 1100048;//请选择出售状态
	public static final int CHECK_PUBLISH_1100049 = 1100049;//不能同时选择出租出售状态
	public static final int CHECK_PUBLISH_1100050 = 1100050;//请选择出租或出售状态	
	public static final int CHECK_PUBLISH_1100052 = 1100052;//该小区受限，暂时不能发布
	//tiger

	public static final int CHECK_PUBLISH_1100053 = 1100053;//单元号错误
	public static final int CHECK_PUBLISH_1100054 = 1100054;//相同房东号码不能在同一天内提交2次以上
	/**************************************** error code *********************************************/

}