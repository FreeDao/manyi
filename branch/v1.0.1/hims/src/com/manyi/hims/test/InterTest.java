package com.manyi.hims.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.InfoUtils;

public class InterTest {
	public static void main(String[] args) {

		//注册
//		String url="http://192.168.1.102/rest/uc/regist.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
////		pars.put("realName", 5); 
//		pars.put("code", "2222"); 
		
		////出受首页
//		String url="http://192.168.1.185:8081/rest/sell/indexList.rest";
//		String url="http://192.168.1.102:80/rest/sell/indexList.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("App-Key", "fybao.superjia.com");
////		pars.put("App-Secret", "MT0VT5EN1FAP7SGA840OBW2DUFJUAB");
//		InfoUtils.sendRestInter(url, pars);
		
		
		////通过 小区的名字  得到  小区列表
//		String url="http://192.168.1.102/rest/common/findEstateByName.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("name","长寿");
		
		////出组首页
//		String url="http://192.168.1.185:8081/rest/rent/indexList.rest";
//		String url="http://192.168.1.102/rest/rent/indexList.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		InfoUtils.sendRestInter(url, pars);
		
		
//		String url="http://192.168.1.102/rest/common/incrementalCity.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		InfoUtils.sendRestInter(url, pars);
		
		//通过parentid 得到 区域集合
//		String url="http://192.168.1.102/rest/common/findAreaByParentId.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("parentId", 5); 
//		pars.put("flag", false); 
		
		
		/*参数
		 * estateId;//小区ID
			parentId;//区域上一级ID
			areaId;//区域ID
			bedroomSum;//房型(几房)
			startPrice;//起始价格
			endPrice;//截止价格
			startSpaceArea;// 起始内空面积
			endSpaceArea;// 截止内空面积
			start;//数据起始下标
			max;//返回的数据量(返回几条记录)
			markTime;//时间戳
		 * */
		
		///// 出租 搜索
		//sell/findSellByPage.rest
////		String url="http://192.168.1.102/rest/rent/findRentByPage.rest";
//		String url="http://127.0.0.1/rest/sell/findSellByPage.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("parentId",2);
//		pars.put("areaId",32938);
		//33087
		//33274
		//参数: --> {"areaId":2,"bedroomSum":0,"endPrice":0,"endSpaceArea":0,"estateId":0,"markTime":1397804427027,"max":12,"parentId":1,"start":0,"startPrice":0,"startSpaceArea":0}
		//参数: --> {"areaId":2,"bedroomSum":0,"endPrice":0,"endSpaceArea":0,"estateId":0,"markTime":1397804497137,"max":12,"parentId":1,"start":0,"startPrice":0,"startSpaceArea":0}
		//参数: --> {"areaId":2,"bedroomSum":0,"endPrice":0,"endSpaceArea":0,"estateId":0,"markTime":1397804530870,"max":12,"parentId":1,"start":12,"startPrice":0,"startSpaceArea":0}
//		pars.put("estateId", 33087);
		
		//区域 查询
		//1.
//		pars.put("areaId", 18);
//		pars.put("parentId", 5);
		
//		pars.put("max", 12);
//		for (int i = 0; i < 10; i++) {
//			pars.put("start", 12*i);
//			InfoUtils.sendRestInter(url, pars);
//		}
		
		
		//2.
//		pars.put("areaId", 5);
//		pars.put("parentId", 1);
		
		//面积
		//1.
//		pars.put("startSpaceArea", 50);
//		pars.put("endSpaceArea", 70);
		//2.
//		pars.put("endSpaceArea", 100);
		//3.
//		pars.put("startSpaceArea", 10);
		
		//价格
		//1.
//		pars.put("startPrice", 200);
		//2.
//		pars.put("startPrice", 210);
//		pars.put("endPrice", 320);
		//3,
//		pars.put("startPrice", 209);
		
		//房型
//		pars.put("bedroomSum" ,100);
		
//		InfoUtils.sendRestInter(url, pars);
		
		
//		pars.put("markTime", System.currentTimeMillis());
//		pars.put("start", 0);
//		pars.put("max", 10);
		
		
		//发布出售第一步
		//43966
		//chenkHoustIsSell
//		String url="http://192.168.1.102/rest/sell/chenkHoustIsSell.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("estateId", "43966");
//		pars.put("building", "1");
//		pars.put("room", "0124");
//		pars.put("houseType", "2");//出售
//		InfoUtils.sendRestInter(url, pars);
		
		//发布出售信息
		//houseId 1080950785
		String url="http://192.168.1.102/rest/rent/publishRentInfo.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		pars.put("houseId", "1080950785");
		pars.put("userId", 3);
		pars.put("spaceArea", 9999);
		pars.put("bedroomSum", 2);
		pars.put("livingRoomSum", 2);
		pars.put("wcSum", 2);
		pars.put("price", 1005526d);
		pars.put("hostName", "AA");
		pars.put("houstMobile", "13252638912");
		InfoUtils.sendRestInter(url, pars);
		
		//发布出租信息
//		String url="http://192.168.1.102/rest/rent/publishRentInfo.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("houseId", 10);
//		pars.put("userId", 10);
//		pars.put("spaceArea", 56.2);
//		pars.put("bedroomSum", 2);
//		pars.put("price", 999);
//		pars.put("hostName", "A,B");
//		pars.put("houstMobile", "15252412356,15863524152");
		
		//我的 操作记录
//		String url="http://192.168.1.102/rest/sourceLog/indexList.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("userId", 65538);
//		InfoUtils.sendRestInter(url, pars);
		
		//我的 操作记录(分页)
//		String url="http://192.168.1.102/rest/sourceLog/findPageLog.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("userId", 1);
//		pars.put("start", 1);
//		pars.put("end", 1);
		
		
		//改盘第一步
//		String url="http://192.168.1.102/rest/sourceLog/checkCanUpdateDisc.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("estateId", 26);
//		pars.put("building", "777");
//		pars.put("room", "1234");
		
		
		/**
		 * private int userId;//用户ID
		private int houseId;//房源ID
		private int sellType;//出售状态(1.已出售;2,不出售)
		private int rentType;//出租状态(1.已出租;2,不出租)
		private String hostName;//联系人姓名
		private String hostMobile;//联系人电话
		private String remark;//理由
		 */
		//改盘第二步
//		String url="http://192.168.1.102/rest/sourceLog/updateDisc.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("houseId", 32768);
//		pars.put("userId", 1);
//		pars.put("sellType", 1);
//		pars.put("remark", 1);
		
		
		
		//清除 用户历史发布记录
//		String url="http://192.168.1.102/rest/sourceLog/clearPublishLog.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("userId", 1);
		
		//加载出售记录信息
//		String url="http://192.168.1.102/rest/sourceLog/loadSellInfoLog.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("logId", 458752);
		
		//加载出租记录信息
//		String url="http://192.168.1.102/rest/sourceLog/loadRentInfoLog.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("logId", 491520);
			
		
		
//		//发送短信
//		String url = "http://192.168.1.102/rest/uc/findMsgCode.rest";
//		Map<String, Object> pars = new HashMap<String, Object>();
//		pars.put("mobile", "13252417845");
//		InfoUtils.sendRestInter(url, pars);
//
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		InfoUtils.sendRestInter(url, pars);
//		
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		InfoUtils.sendRestInter(url, pars);
//
//		url = "http://192.168.1.102/rest/uc/checkMsgCode.rest";
//		pars = new HashMap<String, Object>();
//		pars.put("checkVerifyCode", "12313132");
		
		
//		InfoUtils.sendRestInter(url, pars);
		
		//注册
//		String url = "http://192.168.1.102/rest/uc/regist.rest";
//		Map<String, Object> pars = new HashMap<String, Object>();
//		InfoUtils.sendRestInter(url, pars);
		
		//callcenter  审核列表
		
//		String url = "http://192.168.1.102/rest/check/checkList.rest";
//		Map<String, Object> pars = new HashMap<String, Object>();
//		InfoUtils.sendRestInter(url, pars);
		
		
		//审核失败 发送  详细信息
		//18502138230
//		String url = "http://127.0.0.1/rest/uc/getFailedDetail.rest";
//		Map<String, Object> pars = new HashMap<String, Object>();
//		pars.put("mobile", "15195973805");
//		InfoUtils.sendRestInter(url, pars);
		
		//65539
//		String url = "http://10.0.0.66/rest/uc/againRegist.rest";
//		Map<String, Object> pars = new HashMap<String, Object>();
//		pars.put("userId", "65539");
//		pars.put("realName", "dd");
//		pars.put("areaId", "4");
//		pars.put("cityId", 1);
//		pars.put("code", "909090099090999");
//		InfoUtils.sendRestInter(url, pars);
		
//		testrun();
		
		//System.out.println(EntityUtils.StatusEnum.SUCCESS.getValue()+EntityUtils.StatusEnum.ING.getDesc());
		
		System.out.println(new Date(1399237502108l));
	}
	
	
	private static void testrun(){
		for (int i = 0; i < 1; i++) {
			new TestRun().start();
		}
	}
}
