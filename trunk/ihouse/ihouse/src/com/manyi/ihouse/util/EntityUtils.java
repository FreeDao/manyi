package com.manyi.ihouse.util;


public class EntityUtils {
	/**
	 * 审核状态
	 * 1,审核成功;2,审核中;3,审核失败;4,无效
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
			return null;
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
	 * 性别
	 * 1,男;2,女;3,保密;
	 * @author tiger
	 *
	 */
	public enum GenderEnum {

		MALE(1, "男"),

		FEMALE(2, "女"),

		UNKONW(3, "保密");

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
			return null;
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
	 * ACTION类型
	 * 1,发布;2,改盘;3,举报;4,轮询;5,抽查看房
	 * @author tiger
	 *
	 */
	public enum ActionTypeEnum {
		UNKNOW(0, "未知"),
		
		PUBLISH(1, "发布"),
		
		CHANGE(2, "改盘"),
		
		REPORT(3, "举报"),
		
		LOOP(4, "客服轮询"),
		
		RAMDOM(5, "抽查看房");
		
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
			return null;
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
			return null;
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
	 * 房屋出售状态
	 * 1出租，2出售，3即租又售，4即不租也不售
	 * 
	 * @author zxc
	 */
	public enum HouseStateEnum {
		
		RENT(1,"出租"),//1出租，2出售，3即租又售，4即不租也不售
		
		SELL(2,"出售"),
		
		RENTANDSELL(3,"既租又售"),
		
		NEITHER(4,"既不租也不售");

		private int value;
		private String desc;
		
		private HouseStateEnum(int value,String desc) {
			this.value = value;
			this.desc = desc;
		}
		
		public static HouseStateEnum getByValue(int value) {
			for (HouseStateEnum sse : values()) {
				if (NumberUtils.isEqual(value, sse.value)) {
					return sse;
				}
			}
			return null;
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
	 * 房屋出售状态
	 * 
	 * @author zxc
	 */
	public enum HouseStateResonEnum {
		
		HAD_RENTED(1,"已租"),//1出租，2出售，3即租又售，4即不租也不售
		
		NO_RENT(2,"不租"),
		
		HAD_SELLED(3,"已售"),
		
		NO_SELL(4,"不售");

		private int value;
		private String desc;
		
		private HouseStateResonEnum(int value,String desc) {
			this.value = value;
			this.desc = desc;
		}
		
		public static HouseStateResonEnum getByValue(int value) {
			for (HouseStateResonEnum sse : values()) {
				if (NumberUtils.isEqual(value, sse.value)) {
					return sse;
				}
			}
			return null;
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
		
		ENABLED(true,"启用"),
		
		DISABLED(false,"禁用");

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
			return  ENABLED;
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
	
	
	public enum HouseResourceAction{
		PUBLISH(1),
		UPDATE(2),
		ROLL(3),
		REPORT(4),
		SPOTTEST(5);
		private final int action;
	    HouseResourceAction(int action) {
	    	this.action = action;
	    }
	    public int getValue() {
	    	return this.action;
	    }
	    public static HouseResourceAction parse(int iAction) {
	    	HouseResourceAction action = null;
            for (HouseResourceAction item : HouseResourceAction.values()) {
                if (item.getValue()==iAction) {
                    action = item;
                    break;
                }
            }
            return action;
        }
	}
	
	public enum HouseState{
		RENT(1),
		SELL(2),
		RENTSELL(3),
		NONE(4);
		private final int state;
	    HouseState(int state) {
	    	this.state = state;
	    }
	    public int getValue() {
	    	return this.state;
	    }
	    public static HouseState parse(int iState) {
	    	HouseState state = null;
            for (HouseState item : HouseState.values()) {
                if (item.getValue()==iState) {
                    state = item;
                    break;
                }
            }
            return state;
        }
	}
	
	/**
	 * 奖励 类型(奖励钱财的原因)
	 *  这笔 数据的 来源
	 *	 1. 发布出售  7元    
	 *	 2. 发布出租  5元    
	 *	 3. 改盘          2元
	 *	 4. 举报          100元
	 *	 5. 新增小区  10元   
	 *	 6. 注册成功   5元    
	 *	 7. 邀请人注册成功 5元  
	 *
	 * @author tiger
	 *
	 */
	public enum AwardTypeEnum{
		
		/**
		 * 1.发布出售  7元  
		 */
		SELL(1,7,"返7元"),
		
		/**
		 * 2. 发布出租  5元    
		 */
		RENT(2,5,"返5元"),
		
		/**
		 * 3. 改盘          2元  
		 */
		CHANGE(3,2,"返2元"),
		
		/**
		 * 4. 举报          100元
		 */
		REPORT(4,100,"返100元"),
		
		/**
		 * 5. 新增小区  10元   
		 */
		ADDESTATE(5,10,"返10元"),
		
		/**
		 * 6. 注册成功   5元   
		 */
		REGIST(5,10,"返10元"),
		
		/**
		 * 7. 邀请人注册成功 5元 
		 */
		INVITE(7,5,"返5元");
		
		private int value;
		private int money;
		private String desc;
		
		private AwardTypeEnum(int v , int m , String d) {
			this.value= v;
			this.money= m;
			this.desc= d;
		}
		
		public static AwardTypeEnum getByValue(int value) {
			for (AwardTypeEnum re : values()) {
				if (NumberUtils.isEqual(value, re.value)) {
					return re;
				}
			}
			return null;
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
	}
}
