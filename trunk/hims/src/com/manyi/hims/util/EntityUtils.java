package com.manyi.hims.util;



public class EntityUtils {
	
	/**
	 * 发布类型
	 * 
	 * @author zxc
	 */
	public enum SourceLogType {

		RENT(1, "发布出租"),

		SELL(2, "发布出售"),

		CHANGE(3, "改盘"),

		REPORT(4, "举报"),
		
		ADD_ESTATE(5, "新增小区");

		private int value;
		private String desc;

		private SourceLogType(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static SourceLogType getByValue(int value) {
			for (SourceLogType ve : values()) {
				if (NumberUtils.isEqual(value, ve.value)) {
					return ve;
				}
			}
			return RENT;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	/**
	 * 区域信息 状态
	 * 审核状态  1有效，2未审核，3无效, 4删除
	 * 
	 * @author tiger
	 * 
	 */
	public enum AreaStatusEnum {
		
		UNUSED(0, "-"),
		
		/**
		 * 1.审核成功
		 */
		SUCCESS(1, "有效"),
		
		/**
		 * 2.审核中
		 */
		ING(2, "审核中"),
		
		/**
		 * 3.审核失败
		 */
		FAILD(3, "无效"),
		
		DISABLED(4, "删除");
		
		private int value;
		private String desc;
		
		private AreaStatusEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}
		
		public static AreaStatusEnum getByValue(int value) {
			for (AreaStatusEnum ve : values()) {
				if (NumberUtils.isEqual(value, ve.value)) {
					return ve;
				}
			}
			return UNUSED;
		}
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	/**
	 * 审核状态 1,审核成功;2,审核中;3,审核失败;4,无效
	 * 
	 * @author tiger
	 * 
	 */
	public enum StatusEnum {

		UNUSED(0, "-"),

		/**
		 * 1.审核成功
		 */
		SUCCESS(1, "审核成功"),

		/**
		 * 2.审核中
		 */
		ING(2, "审核中"),

		/**
		 * 3.审核失败
		 */
		FAILD(3, "审核失败"),

		DISABLED(4, "无效");

		private int value;
		private String desc;

		private StatusEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static StatusEnum getByValue(int value) {
			for (StatusEnum ve : values()) {
				if (NumberUtils.isEqual(value, ve.value)) {
					return ve;
				}
			}
			return UNUSED;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	/**
	 * 性别 1,男;2,女;3,保密;
	 * 
	 * @author tiger
	 * 
	 */
	public enum GenderEnum {

		MALE(1, "男"),

		FEMALE(2, "女"),

		UNKNOW(3, "保密");

		private int value;
		private String desc;

		private GenderEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static GenderEnum getByValue(int value) {
			for (GenderEnum ge : values()) {
				if (NumberUtils.isEqual(value, ge.value)) {
					return ge;
				}
			}
			return UNKNOW;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	/**
	 * ACTION类型 1,发布;2,改盘;3,举报;4,轮询;5,抽查看房
	 * 
	 * @author tiger
	 * 
	 */
	public enum ActionTypeEnum {
		UNKNOW(0, "未知"),

		PUBLISH(1, "发布"),

		CHANGE(2, "改盘"),

		REPORT(3, "举报"),

		LOOP(4, "客服轮询"),

		RAMDOM(5, "抽查看房"),
		
		ADD_ESTATE(6, "新增小区");

		private int value;
		private String desc;

		private ActionTypeEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static ActionTypeEnum getByValue(int value) {
			for (ActionTypeEnum ve : values()) {
				if (NumberUtils.isEqual(value, ve.value)) {
					return ve;
				}
			}
			return UNKNOW;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

	}
	
	/**
	 * 
	 * 排斥任务枚举
	 * 	0,未知; 1,发布;2,改盘;3,举报;4,轮询;5,BD拍照;6,中介拍照;7,没有任何任务
	 * 
	 * @author tom
	 * 
	 */
	public enum TaskRepulsionEnum {
		
		UNKNOW(0, "未知"),

		PUBLISH(1, "发布"),

		CHANGE(2, "改盘"),

		REPORT(3, "举报"),

		LOOP(4, "客服轮询"),

		BD_TASK(5, "BD拍照"),
		
		USER_TASK(6, "中介拍照"),
		
		NOTASK(7, "没有进行任何任务");

		private int value;
		private String desc;

		private TaskRepulsionEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static TaskRepulsionEnum getByValue(int value) {
			for (TaskRepulsionEnum ve : values()) {
				if (NumberUtils.isEqual(value, ve.value)) {
					return ve;
				}
			}
			return UNKNOW;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

	}

	/**
	 * 角色 1管理员,2客服经理，3客服人员,4地推经理,5地推人员 6财务,7运营经理,8运营专员
	 * 
	 * @author zxc
	 */
	public enum RoleEnum {
		ILLEGAL_ROLE(0, "非法角色", "illegal_role"),

		ADMIN_ROLE(1, "管理员", "admin_role"),

		CS_MANAGER_ROLE(2, "客服经理", "cs_manager_role"),

		CS_MEMBER_ROLE(3, "客服人员", "cs_member_role"),

		FLOOR_MANAGER_ROLE(4, "地推经理", "floor_manager_role"),

		FLOOR_MEMBER_ROLE(5, "地推人员", "floor_member_role"),

		FINANCE_ROLE(6, "财务", "finance_role"),
		
		RUN_MANAGER_ROLE(7, "运营经理", "run_manager_role"),
		
		RUN_ROLE(8, "运营专员", "run_role");

		private int roleNum;
		private String roleName;
		private String roleCode;

		private RoleEnum(int roleNum, String roleName, String roleCode) {
			this.roleNum = roleNum;
			this.roleName = roleName;
			this.roleCode = roleCode;
		}

		public static RoleEnum getByValue(int roleNum) {
			for (RoleEnum re : values()) {
				if (NumberUtils.isEqual(roleNum, re.roleNum)) {
					return re;
				}
			}
			return ILLEGAL_ROLE;
		}

		public int getRoleNum() {
			return roleNum;
		}

		public void setRoleNum(int roleNum) {
			this.roleNum = roleNum;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		public String getRoleCode() {
			return roleCode;
		}

		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
		}
	}

	/**
	 * 房屋出售状态 1出租，2出售，3即租又售，4即不租也不售
	 * 
	 * @author zxc
	 */
	public enum HouseStateEnum {

		UNKNOW(0, "未知"),

		RENT(1, "出租"), // 1出租，2出售，3即租又售，4即不租也不售

		SELL(2, "出售"),

		RENTANDSELL(3, "租售"),

		NEITHER(4, "既不租也不售");

		private int value;
		private String desc;

		private HouseStateEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static HouseStateEnum getByValue(int value) {
			for (HouseStateEnum sse : values()) {
				if (NumberUtils.isEqual(value, sse.value)) {
					return sse;
				}
			}
			return UNKNOW;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	/**
	 * 房屋出售状态 1已租，2不租（不想租）,3已售，4，不售（不想售）, 5，不售不租，6，不售已租
	 * 
	 * @author zxc
	 */
	public enum HouseStateResonEnum {

		UNKNOW(0, "未知"),

		HAD_RENTED(1, "已租"),

		NO_RENT(2, "不租"),

		HAD_SELLED(3, "已售"),

		NO_SELL(4, "不售"),

		NORENT_NOSELL(5, "不租不售"),

		NOSELL_HADRENT(6, "不售已租");

		private int value;
		private String desc;

		private HouseStateResonEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static HouseStateResonEnum getByValue(int value) {
			for (HouseStateResonEnum sse : values()) {
				if (NumberUtils.isEqual(value, sse.value)) {
					return sse;
				}
			}
			return UNKNOW;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	/*
	 * decorateType 1 毛坯，2 装修,3简装,4精装,5豪装
	 * 房屋的装修情况
	 */
	public enum HouseDecorateTypeEnum{
		UNKNOW(0, "未知"),
		
		/**
		 * 1毛坯
		 */
		WORKBLANK(1,"毛坯"),
		/**
		 * 2 装修
		 */
		FITMENT(2,"装修"),
		/**
		 * 3简装
		 */
		SIMPLE(3,"简装"),
		/**
		 * 4精装
		 */
		HARDCOVER(4,"精装"),
		/**
		 * 5豪装
		 */
		Howe(5,"豪装")
		;
		
		private int value;
		private String desc;

		private HouseDecorateTypeEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static HouseDecorateTypeEnum getByValue(int value) {
			for (HouseDecorateTypeEnum sse : values()) {
				if (NumberUtils.isEqual(value, sse.value)) {
					return sse;
				}
			}
			return UNKNOW;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 启用禁用
	 * 
	 * @author zxc
	 */
	public enum DisabledEnum {

		ENABLED(true,1, "启用"),

		DISABLED(false,0, "禁用");

		private boolean value;
		private int numVal;
		private String desc;

		private DisabledEnum(boolean value,int numv, String desc) {
			this.value = value;
			this.numVal = numv;
			this.desc = desc;
		}

		public static DisabledEnum getByValue(boolean value) {
			if (value) {
				return DISABLED;
			}
			return ENABLED;
		}
		
		public static DisabledEnum getByNumValue(int value) {
			if (value ==0) {
				return DISABLED;
			}
			return ENABLED;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public boolean isValue() {
			return value;
		}

		public int getNumVal() {
			return numVal;
		}

		public void setNumVal(int numVal) {
			this.numVal = numVal;
		}

		public void setValue(boolean value) {
			this.value = value;
		}
	}

	/**
	 * 奖励 类型(奖励钱财的原因) 这笔 数据的 来源 1. 发布出售 7元 2. 发布出租 7元 3. 改盘 2元 4. 举报 2元 5. 新增小区 10元 6. 注册成功 5元 7. 邀请人注册成功 5元
	 * 
	 * @author tiger
	 * 
	 */
	public enum AwardTypeEnum {

		UNKNOW(0, 0, "未知", ""),

		/**
		 * 1.发布出售 7元
		 */
		SELL(1, 7, "奖7元", "发布出售"),

		/**
		 * 2. 发布出租 5元
		 */
		RENT(2, 7, "奖7元", "发布出租"),

		/**
		 * 3. 改盘 2元
		 */
		CHANGE(3, 2, "奖2元", "改盘"),

		/**
		 * 4. 举报 2元
		 */
		REPORT(4, 2, "奖2元", "举报"),

		/**
		 * 5. 新增小区 10元
		 */
		ADDESTATE(5, 10, "奖10元", "新增小区"),

		/**
		 * 6. 注册成功 10元
		 */
		REGIST(6, 10, "奖10元", "注册成功"),

		/**
		 * 7. 邀请人注册成功 10元
		 */
		INVITE(7, 10, "奖10元", "邀请人注册成功"),
		
		/**
		 * 8. 经纪人看房 审核成功 8元
		 */
		LOOKHOUSE(8, 8, "奖8元", "经纪人看房审核成功 ");

		private int value;
		private int money;
		private String desc;
		private String source;

		private AwardTypeEnum(int v, int m, String d, String source) {
			this.value = v;
			this.money = m;
			this.desc = d;
			this.source = source;
		}

		public static AwardTypeEnum getByValue(int value) {
			for (AwardTypeEnum re : values()) {
				if (NumberUtils.isEqual(value, re.value)) {
					return re;
				}
			}
			return UNKNOW;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public int getMoney() {
			return money;
		}

		public void setMoney(int money) {
			this.money = money;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}
	}
	
	
	/**
	 * 用户审核记录
	 * 1审核成功，2审核失败，3冻结帐号，4启用，5添加推广人员.
	 * 
	 * @author fangyouhui
	 * 
	 */
	public enum UserHistoryEnum {

		UNUSED(0, "-"),

		/**
		 * 1.审核成功
		 */
		SUCCESS(1, "审核成功"),

		/**
		 * 2.审核失败
		 */
		FAILD(2, "审核失败"),

		/**
		 * 3.冻结帐号
		 */
		DISABLED(3, "启用"),

		/**
		 * 4.启用
		 */
		ENABLE(4, "冻结帐号"),
		
		/**
		 * 5.添加推广人员
		 */
		ADDUSER(5, "添加推广人员");

		private int value;
		private String desc;

		private UserHistoryEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static UserHistoryEnum getByValue(int value) {
			for (UserHistoryEnum ve : values()) {
				if (NumberUtils.isEqual(value, ve.value)) {
					return ve;
				}
			}
			return UNUSED;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	/**
	 * @author Tom
	 *
	 */
	public enum LimitNumEnum {
		
		/**
		 * 到手价：不大于10,000,000,000（100亿）；
		 */
		GOT_PRICE(10000000000L, "最大到手价"),


		/**
		 * 租    金：不大于10,000,000（1000万）；
		 */
		RENT_PRICE(10000000L, "最大租金"),
		
		/**
		 * 面    积：不大于10,000平方米；
		 */
		SPACE_AREA(10000L, "最大租金");
		
		private Long value;
		private String desc;

		private LimitNumEnum(Long value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public Long getValue() {
			return value;
		}

		public void setValue(Long value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * @author tom
	 * 房子的照片
	 * 
	 * 第一列： callcenter中呈现图片的order，与手机端拍照列表顺序一致
	 * 第二列：
	 * 第三列：类别  与手机端预定好的
	 * 第四列：照片的描述信息
	 */
	public enum HouseResourceType {
		UNKNOW(0, "未知", "未知", "未知"),
		
		MAILBOX(1, "mailBox", "mailBox", "邮箱"),
		mailBox_or_elevatorRoom(1, "mailBox_or_elevatorRoom", "mailBox_or_elevatorRoom", "邮箱或电梯"),
        ELEVATORROOM(2, "elevatorRoom", "elevatorRoom", "电梯间"),//电梯间（操作板）
		DOORPLATE(3, "doorPlate", "doorPlate", "门牌"),
		
		LIVINGROOM1(4, "livingRoom", "livingRoom1", "厅1"),
		LIVINGROOM2(5, "livingRoom", "livingRoom2", "厅2"),
		LIVINGROOM3(6, "livingRoom", "livingRoom3", "厅3"),
		LIVINGROOM4(7, "livingRoom", "livingRoom4", "厅4"),
		LIVINGROOM5(8, "livingRoom", "livingRoom5", "厅5"),
		LIVINGROOM6(9, "livingRoom", "livingRoom6", "厅6"),
		LIVINGROOM7(10, "livingRoom", "livingRoom7", "厅7"),
		LIVINGROOM8(11, "livingRoom", "livingRoom8", "厅8"),
		LIVINGROOM9(12, "livingRoom", "livingRoom9", "厅9"),
		LIVINGROOM10(13, "livingRoom", "livingRoom10", "厅10"),
		
		BEDROOM1(14, "bedRoom", "bedRoom1", "主卧"),
		BEDROOM2(15, "bedRoom", "bedRoom2", "次卧1"),
		BEDROOM3(16, "bedRoom", "bedRoom3", "次卧2"),
		BEDROOM4(17, "bedRoom", "bedRoom4", "次卧3"),
		BEDROOM5(18, "bedRoom", "bedRoom5", "次卧4"),
		BEDROOM6(19, "bedRoom", "bedRoom6", "次卧5"),
		BEDROOM7(20, "bedRoom", "bedRoom7", "次卧6"),
		BEDROOM8(21, "bedRoom", "bedRoom8", "次卧7"),
		BEDROOM9(22, "bedRoom", "bedRoom9", "次卧8"),
		BEDROOM10(23, "bedRoom", "bedRoom10", "次卧9"),
		BEDROOM11(24, "bedRoom", "bedRoom11", "次卧10"),
		
		WC1(25, "wc", "wc1", "卫生间1"),
		WC2(26, "wc", "wc2", "卫生间2"),
		WC3(27, "wc", "wc3", "卫生间3"),
		WC4(28, "wc", "wc4", "卫生间4"),
		WC5(29, "wc", "wc5", "卫生间5"),
		WC6(30, "wc", "wc6", "卫生间6"),
		WC7(31, "wc", "wc7", "卫生间7"),
		WC8(32, "wc", "wc8", "卫生间8"),
		WC9(33, "wc", "wc9", "卫生间9"),
		WC10(34, "wc", "wc10", "卫生间10"),
		
        KITCHEN(35, "kitchen", "kitchen", "厨房"),
        BALCONY(36, "balcony", "balcony", "阳台"),
        EXTERIOR(37,"exterior", "exterior", "外景");
        

        private int value;
		private String type;
		private String key;
		private String desc;

		private HouseResourceType(int value, String type, String key, String desc) {
			this.value = value;
			this.type = type;
			this.key = key;
			this.desc = desc;
		}

		public static HouseResourceType getByValue(int value) {
			for (HouseResourceType re : values()) {
				if (NumberUtils.isEqual(value, re.value)) {
					return re;
				}
			}
			return UNKNOW;
		}
		
		public static HouseResourceType getByKey(String key) {
			for (HouseResourceType re : values()) {
				if (key.equals(re.key)) {
					return re;
				}
			}
			return UNKNOW;
		}
		
		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
	
     public enum houseSuppoertEnum {
    	//是否含有  true 1 是； false 0 否
    	 //第二列   1基本， 2高级
    	hasTV("电视", "1"), 
    	hasRefrigerator("冰箱", "1"),
    	hasWashingMachine("洗衣机", "1"),
    	hasAirConditioner("空调", "1"),
    	hasWaterHeater("热水器", "1"),
    	hasBed("床", "1"),
    	hasSofa("沙发", "1"),
    	hasBathtub("浴缸/淋浴", "1"),
    	hasCentralHeating("暖气/地暖", "1"),
    	
    	hasCentralAirConditioning("中央空调", "2"),
    	hasCloakroom("衣帽间", "2"),
    	hasReservedParking("车位", "2"),
    	hasCourtyard("院落", "2"),
    	hasGazebo("露台", "2"),
    	hasPenthouse("阁楼", "2");


		private String cn;
		private String isGaoji;

		private houseSuppoertEnum(String cn, String isGaoji) {
			this.cn = cn;
			this.isGaoji = isGaoji;
		}
		
		public String getCn() {
			return cn;
		}

		public void setCn(String cn) {
			this.cn = cn;
		}

		public String getIsGaoji() {
			return isGaoji;
		}

		public void setIsGaoji(String isGaoji) {
			this.isGaoji = isGaoji;
		}
		

	}
     
     
     /**
      * BDTask 任务 状态  【第三列 给app端人员用的状态】
      * @author tiger
      *
      */
     public enum TaskStatusEnum {
    	 /**
    	  * 0,未安排看房任务
    	  */
    	 UNKNOW(0, "未安排看房任务", "未知"),
    	 /**
    	  * 1.看房任务开启
    	  */
    	 START(1,"未预约", "未知"),
    	 /**
    	  * 2,预约失败
    	  */
    	 ORDER_FALIED(2,"预约失败", "未知"),
    	 /**
    	  * 3,再预约
    	  */
    	 ORDER_AGAIN(3,"再预约", "未知"),
    	 /**
    	  * 4,预约成功
    	  */
    	 ORDER_SUCCESS(4,"待看房", "未完成"),
    	 /**
    	  * 5.看房失败
    	  */
    	 LOOK_FALIED(5,"看房失败", "任务失败"),
    	 /**
    	  * 6.看房成功
    	  */
    	 LOOK_SUCCESS(6,"看房成功", "已完成");
    	 
    	 private int value;
    	 private String desc;
    	 private String desc4app;
    	 
    	 private TaskStatusEnum(int value, String desc, String desc4app) {
    		 this.value = value;
    		 this.desc = desc;
    		 this.desc4app = desc4app;
    	 }
    	 
    	 public static TaskStatusEnum getByValue(int value) {
    		 for (TaskStatusEnum ve : values()) {
    			 if (NumberUtils.isEqual(value, ve.value)) {
    				 return ve;
    			 }
    		 }
    		 return UNKNOW;
    	 }
    	 
    	 public int getValue() {
    		 return value;
    	 }
    	 public void setValue(int value) {
    		 this.value = value;
    	 }
    	 public String getDesc() {
    		 return desc;
    	 }
    	 public void setDesc(String desc) {
    		 this.desc = desc;
    	 }
    	 
    	 public String getDesc4app() {
    		 return desc4app;
    	 }
    	 
    	 public void setDesc4app(String desc4app) {
    		 this.desc4app = desc4app;
    	 }
    	 
     }
     
     /**
 	 * UserTask 任务 状态 
 	 * 1已领取(未完成), 2审核中（提交图片完成）, 3审核成功,4审核失败,5过期
 	 * @author tiger
 	 *
 	 */
     public enum UserTaskStatusEnum {
 		/**
 		 * 0,未领取
 		 */
 		UNKNOW(0, "未领取"),
 		/**
 		 * 1.已领取(未完成)
 		 */
 		START(1,"已领取"),
 		/**
 		 * 2,审核中（提交图片完成）
 		 */
 		ING(2,"审核中"),
 		/**
 		 * 3,审核成功
 		 */
 		SUCCESS(3,"审核成功"),
 		/**
 		 * 4,审核失败
 		 */
 		FAILD(4,"审核失败"),
 		/**
 		 * 5.过期
 		 */
 		PASTDUE(5,"过期");
 		
 		private int value;
 		private String desc;
 		
 		private UserTaskStatusEnum(int value, String desc) {
 			this.value = value;
 			this.desc = desc;
 		}

 		public static UserTaskStatusEnum getByValue(int value) {
 			for (UserTaskStatusEnum ve : values()) {
 				if (NumberUtils.isEqual(value, ve.value)) {
 					return ve;
 				}
 			}
 			return UNKNOW;
 		}
 		
 		public int getValue() {
 			return value;
 		}
 		public void setValue(int value) {
 			this.value = value;
 		}
 		public String getDesc() {
 			return desc;
 		}
 		public void setDesc(String desc) {
 			this.desc = desc;
 		}

 	}
     
     
     public enum VerifyCodeEnum {
  		/**
  		 * -2,网络异常，请求失败
  		 */
  		REQUESTFAILED(-2, "网络异常，请求失败"),
  		/**
  		 * -1.用户名或者密码不正确
  		 */
  		USERNAMENOTCORRECTY(-1,"用户名或者密码不正确"),
  		/**
  		 * 1 发送短信成功 
  		 */
  		SUCCESS(1,"发送成功 "),
  		/**
  		 *2  余额不够
  		 */
  		AMOUNTISNULL(2,"余额不够"),
  		/**
  		 *3  扣费失败
  		 */
  		FEEFAILED(3,"扣费失败"),
  		/**
  		 *15 Ip验证失败
  		 */
  		IPVERIFYFAILED(3,"Ip验证失败"),
  		;
  		
  		private int value;
  		private String desc;
  		
  		private VerifyCodeEnum(int value, String desc) {
  			this.value = value;
  			this.desc = desc;
  		}

  		public static VerifyCodeEnum getByValue(int value) {
  			for (VerifyCodeEnum ve : values()) {
  				if (NumberUtils.isEqual(value, ve.value)) {
  					return ve;
  				}
  			}
  			return SUCCESS;
  		}
  		
  		public int getValue() {
  			return value;
  		}
  		public void setValue(int value) {
  			this.value = value;
  		}
  		public String getDesc() {
  			return desc;
  		}
  		public void setDesc(String desc) {
  			this.desc = desc;
  		}

  	}
}
