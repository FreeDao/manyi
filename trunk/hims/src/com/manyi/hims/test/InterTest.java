package com.manyi.hims.test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.manyi.hims.entity.Area;
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
////		String url="http://192.168.1.185:8081/rest/sell/indexList.rest";
//		String url="http://127.0.0.1:80/rest/sell/indexList.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
////		pars.put("App-Key", "fybao.superjia.com");
////		pars.put("App-Secret", "MT0VT5EN1FAP7SGA840OBW2DUFJUAB");
//		InfoUtils.sendRestInter(url, pars);
		
//		System.out.println(Math.floor(1.7));
//		System.out.println(Math.ceil(1.01));
		
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
//		String url="http://192.168.1.102/rest/rent/publishRentInfo.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("houseId", "1080950785");
//		pars.put("userId", 3);
//		pars.put("spaceArea", 9999);
//		pars.put("bedroomSum", 2);
//		pars.put("livingRoomSum", 2);
//		pars.put("wcSum", 2);
//		pars.put("price", 1005526d);
//		pars.put("hostName", "AA");
//		pars.put("houstMobile", "13252638912");
//		InfoUtils.sendRestInter(url, pars);
		
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
		
		
//		//我的 操作记录
//		String url="http://192.168.1.102/rest/sourceLog/indexListAddAreaLog.rest";
//		Map<String, Object> pars=new HashMap<String, Object>();
//		pars.put("userId", 688130);
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
		
		//新增小区
		// /rest/uc/addEstate
		/*
		 * private String esateName;
		private int estateId;
		private String address;
		private int uid;
		 * */
		
//		String url = "http://127.0.0.1/rest/uc/addEstate.rest";
//		Map<String, Object> pars = new HashMap<String, Object>();
//		pars.put("estateId", "32768");
//		pars.put("uid", "655360");
//		pars.put("address", "获得丰厚复合风管");
//		pars.put("esateName", "荡漾小区");
//		InfoUtils.sendRestInter(url, pars);
		
		//System.out.println(EntityUtils.StatusEnum.SUCCESS.getValue()+EntityUtils.StatusEnum.ING.getDesc());
		
//		System.out.println(new Date(1399237502108l));
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date d1  = new Date();
//		Date d2  = new Date();
//		SimpleDateFormat sd = new SimpleDateFormat("MMMM EEEE");//中文的星期几
//		String s =sd.format(d1);
//		System.out.println(s);
//		System.out.println(sdf.format(d1));
//		
//		long d11 = d1.getTime();
//		long d22 = d2.getTime();
//		System.out.println("ddd:"+(d11 - d22));
		
//		java.util.Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		String t1= sdf.format(cal.getTime());
//		System.out.println(t1);
//		
//		java.util.Calendar cal2 = Calendar.getInstance();
//		cal2.set(Calendar.HOUR_OF_DAY, 0);
//		cal2.set(Calendar.MINUTE, 0);
//		cal2.set(Calendar.SECOND, 0);
//		System.out.println(sdf.format(cal2.getTime()));//第一天
//		
//		cal2.add(Calendar.DAY_OF_MONTH, 1);//加一天
//		
//		String t2= sdf.format(cal2.getTime());// 第二天
//		System.out.println(t2);
//		
//		double d = 0;
//		int num =47;
//		int success = 3;
//		DecimalFormat df = new DecimalFormat("0.00%");
//		if(num != 0 && success !=0){
//			d = (((double)success)/num);
//		}
//		System.out.println(df.format(d));
		
		
//		String[] strs = new String[]{"拨打电话:1254879633","拨打电话:8545521243"};
//		for (int i = 0; i < strs.length; i++) {
//			String s =strs[i].replace("拨打电话:", "");
//			System.out.println("replace:"+s);	
//			
//			String s1 = strs[i].substring(5);
//			System.out.println(s1);
//		}
//	}
//	
//	
//	private static void testrun(){
//		for (int i = 0; i < 1; i++) {
//			new TestRun().start();
//		}
		
		
		final Area a1 = new Area();
		a1.setName("111");
		a1.setAreaId(111);
		
		 Area a2 =a1;
		//BeanUtils.copyProperties(a1, a2);
//		a2=a1;
		// new Area();
		 a2 = new Area();
		a2.setName("222");
		System.out.println(a1.getName()+" "+a2.getName()+" "+a2.getAreaId());
		
		String s1="11111111";
		String s2=s1;
		 s2="2222222";
		System.out.println(s1 +" "+s2);
		
		
		String s= "aaaaa";
		Boolean falg=true;
		StringBuilder sb =new StringBuilder("sb");
		test(a1,s,falg,sb);
		System.out.println("area-name : "+a1.getName()+" s: "+s +" flag: "+falg +" sb: "+sb.toString());
		
		int[] a = new int[] { 2, 3, 4, 3, 4, 2, 2, 5 };
		int result = 0;
		for (int i = 0; i < a.length; i++) {
			result ^= a[i];
		}
		System.out.println(result);
	}
	
	private static String test(Area a , String s ,Boolean flag,StringBuilder sb){
		s="oooooo";
		sb.append(" sb ");
		flag = false;
		a.setName("dddddddd");
		a = null;
		sb = null;
		return s;
	}
	
}
