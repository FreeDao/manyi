package com.manyi.hims.check.model;

import java.util.ArrayList;
import java.util.List;

import com.manyi.hims.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AntiCheatResponse extends Response {
	
	private List<AntiCheat> list = new ArrayList<AntiCheat>();
	
	@Data
	public static class AntiCheat {
		private int estateId;
		private String estateName;
		private String building;
		private String room;
		private int houseState;
		private String houseStateStr;
	}
	

}
