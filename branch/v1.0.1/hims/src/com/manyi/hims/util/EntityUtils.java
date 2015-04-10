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
	 * 角色 1管理员,2客服经理，3客服人员,4地推经理,5地推人员 6财务
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

		FINANCE_ROLE(6, "财务", "finance_role");

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

		RENTANDSELL(3, "既租又售"),

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

	/**
	 * 启用禁用
	 * 
	 * @author zxc
	 */
	public enum DisabledEnum {

		ENABLED(true, "启用"),

		DISABLED(false, "禁用");

		private boolean value;
		private String desc;

		private DisabledEnum(boolean value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static DisabledEnum getByValue(boolean value) {
			if (value) {
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

		public void setValue(boolean value) {
			this.value = value;
		}
	}

	/**
	 * 奖励 类型(奖励钱财的原因) 这笔 数据的 来源 1. 发布出售 7元 2. 发布出租 5元 3. 改盘 2元 4. 举报 20元 5. 新增小区 10元 6. 注册成功 5元 7. 邀请人注册成功 5元
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
		RENT(2, 5, "奖5元", "发布出租"),

		/**
		 * 3. 改盘 2元
		 */
		CHANGE(3, 2, "奖2元", "改盘"),

		/**
		 * 4. 举报 20元
		 */
		REPORT(4, 20, "奖20元", "举报"),

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
		INVITE(7, 10, "奖10元", "邀请人注册成功");

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

}
