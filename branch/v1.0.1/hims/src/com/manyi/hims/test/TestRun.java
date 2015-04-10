package com.manyi.hims.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.manyi.hims.util.InfoUtils;

public class TestRun extends Thread {
	@Override
	public void run() {
		for (int i = 2; i < 10000;i++) {

//			rentindexList();
//			sellindexList();
//			findEstateByName();
//			findAreaByParentId();
//			myIndexList();
//			loadData();
//			sellAndRentPage();
			
			//registTest(i);
			
			//发布出售1W
			//addSellInfo(i);
			
			//发布出租1W
			addRentInfo(i);
		}
	}
	
	private void rentindexList(){
		////出租首页
		String url="http://192.168.1.102/rest/sell/indexList.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		
		InfoUtils.sendRestInter(url, pars);
	}
	
	private void sellindexList(){
		////出租首页
		String url="http://192.168.1.102/rest/sell/indexList.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		
		InfoUtils.sendRestInter(url, pars);
	}
	
	private void findEstateByName(){
		String url="http://192.168.1.102/rest/common/findEstateByName.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		pars.put("name","长寿");
		
		InfoUtils.sendRestInter(url, pars);
	}
	
	private void findAreaByParentId(){
		//通过parentid 得到 区域集合
		String url="http://192.168.1.102/rest/common/findAreaByParentId.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		pars.put("parentId", 5); 
		pars.put("flag", true); 
		InfoUtils.sendRestInter(url, pars);
	}

	private void myIndexList() {
		// 我的 操作记录
		String url = "http://192.168.1.102/rest/sourceLog/indexList.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		pars.put("userId", 1);
		InfoUtils.sendRestInter(url, pars);
	}
	
	private void loadData(){
		//加载出售记录信息
		String url="http://192.168.1.102/rest/sourceLog/loadSellInfoLog.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		pars.put("logId", 458752);
		InfoUtils.sendRestInter(url, pars);
		
//		加载出租记录信息
		 url="http://192.168.1.102/rest/sourceLog/loadRentInfoLog.rest";
		pars=new HashMap<String, Object>();
		pars.put("logId", 491520);
		InfoUtils.sendRestInter(url, pars);
	}
	
	private void sellAndRentPage(){
		String url="http://192.168.1.102/rest/rent/findRentByPage.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		pars.put("estateId",26 );
		InfoUtils.sendRestInter(url, pars);
		
		url="http://192.168.1.102/rest/sell/findSellByPage.rest";
		pars=new HashMap<String, Object>();
		pars.put("estateId",26 );
		InfoUtils.sendRestInter(url, pars);
	}
	
	private void registTest(int i){
		String url="http://192.168.1.102/rest/uc/regist.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		pars.put("areaId",5 );
		pars.put("mobile", i);
		pars.put("password", "123"+i);
		pars.put("code", "4201681986091"+i);
		pars.put("realName", "姓名"+i);
		InfoUtils.sendRestInter(url, pars);
	}
	
	private void addSellInfo(int i){
		String url="http://192.168.1.102/rest/sell/chenkHoustIsSell.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		pars.put("estateId", 131072);
		int b = (i/200)+1;//[1-50]
		int f = (i%200);
		String tmp= f+"000";
		pars.put("building", b);
		pars.put("room", tmp.substring(0,4));
		JSONObject jsonobj = InfoUtils.sendRestInter(url, pars);
		if(jsonobj != null){
			if(jsonobj.containsKey("houseId")){
				int houseid = jsonobj.getInt("houseId");
				url="http://192.168.1.102/rest/sell/publishSellInfo.rest";
				pars=new HashMap<String, Object>();
				pars.put("houseId", houseid);
				pars.put("userId",1);
				pars.put("spaceArea", 56.5);
				pars.put("bedroomSum", 2);
				pars.put("livingRoomSum",1);
				pars.put("wcSum", 1);
				pars.put("price", 2560);
				pars.put("hostName", "联系人A"+i);
				pars.put("houstMobile", "13596458596");
				InfoUtils.sendRestInter(url, pars);
			}
		}
	}
	private void addRentInfo(int i){
		String url="http://192.168.1.102/rest/rent/chenkHoustIsRent.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		pars.put("estateId", 360448);
		int b = (i/200)+1;//[1-50]
		int f = (i%200);
		String tmp= f+"000";
		pars.put("building", b);
		pars.put("room", tmp.substring(0,4));
		JSONObject jsonobj = InfoUtils.sendRestInter(url, pars);
		if(jsonobj != null){
			if(jsonobj.containsKey("houseId")){
				int houseid = jsonobj.getInt("houseId");
				url="http://192.168.1.102/rest/rent/publishRentInfo.rest";
				pars=new HashMap<String, Object>();
				pars.put("houseId", houseid);
				pars.put("userId",1);
				pars.put("spaceArea", 126.5);
				pars.put("bedroomSum", 2);
				pars.put("livingRoomSum",1);
				pars.put("wcSum", 1);
				pars.put("price", 3000);
				pars.put("hostName", "联系人B"+i);
				pars.put("houstMobile", "18996458596");
				InfoUtils.sendRestInter(url, pars);
			}
		}
	}
	
}