package com.manyi.hims.bd.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.manyi.hims.Response;
import com.manyi.hims.bd.controller.BdController.UpdateHouseRequest;


public interface EmployeeService {

	public BdLoginResponse login(String userName,String password);
	
	public TaskDetailsResponse taskDetails(int taskId);
	
	public void updateHouse(UpdateHouseRequest pars);
	
	@Data
	public static class TaskDetailsResponse extends Response{
		private List<HouseResourceContantResponse> hostList = new ArrayList<HouseResourceContantResponse>();
		private String name;//小区名称
		private String address;//地址
		private String building;//楼栋号
		private String room;//室号
		private Date taskDate;//时间
		private String explainStr; //对该房屋情况作一个简要说明
		private String remark; //如果失败，则有相关说明
		private BigDecimal spaceArea = new BigDecimal(0);// 内空面积
		private int bedroomSum;// 几房
		private int livingRoomSum;// 几厅
		private int wcSum;// 几卫
		private BigDecimal sellPrice;//出售价格
		private BigDecimal rentPrice;//出租价格
		private int houseState;//1出租，2出售，3即租又售
		private int houseId;
		private int id;
		
	}
	@Data
	public static class HouseResourceContantResponse{
		private String hostName;//联系人姓名
		private String hostMobile;//联系人电话
	}
	
	@Data
	public static class BdLoginResponse extends Response{
		private int employeeId;
		private String userName;
	}
	
}
