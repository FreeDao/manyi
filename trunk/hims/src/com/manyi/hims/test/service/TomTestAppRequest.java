package com.manyi.hims.test.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.leo.jackson.FileSerializer;
import com.manyi.hims.util.InfoUtils;
import com.manyi.hims.util.OSSObjectUtil;

public class TomTestAppRequest {
	public static void main(String[] args) throws Exception {

		TomTestAppRequest tomTestAppRequest = new TomTestAppRequest();
		
//		危险 请勿操作
//		tomTestAppRequest.findAreaByAreaId();
//		tomTestAppRequest.findByPage();//success
//		tomTestAppRequest.findEstateByName();  //success
		
//		tomTestAppRequest.automaticUpdates();
//		tomTestAppRequest.uploadPhoto();
//		tomTestAppRequest.downPhoto();
//		tomTestAppRequest.deletePhoto();
//		tomTestAppRequest.taskLookFail();
//		tomTestAppRequest.taskWeek();
		
//houseId
//		doorPlate
//		livingRoom
//      		livingRoom1
//              livingRoom2
//		bedRoom  
//              bedRoom1
//              bedRoom2
//		toilet
//              toilet1
//              toilet2		
		
		
//		FileUtils.UnZipFolder("C:\\Users\\tom.MANYI-SH\\AppData\\Local\\Temp\\manyi_4838772043258563658.zip", "D:\\temp");
		
//		FileUtils.deleteFileDir(new File("D:\\temp\\100001"));
		
		
		
//		System.out.println("asdfasdf");
		
//		
//		
//		HouseSupportingMeasures houseSupportingMeasures = new HouseSupportingMeasures();
//		houseSupportingMeasures.setHasTV(true);
//		houseSupportingMeasures.setHasRefrigerator(true);
//		houseSupportingMeasures.setHasWashingMachine(false);
//		
//		TaskRequest taskRequest = new TaskRequest();
//		taskRequest.setHouseId(1);
//		taskRequest.setHouseSupportingMeasures(houseSupportingMeasures);
//		taskRequest.setFileZip(new File("D:\\temp\\100001.zip"));
//		
//		
//		File file = new File("D:\\temp\\100001.zip");
//		File fi=new File("C:/fi.txt");
//		FileOutputStream fos = new FileOutputStream(fi);
//		ObjectOutputStream oos = new ObjectOutputStream(fos);
//		oos.writeObject(file);
//		oos.flush();
//		oos.close();
//		fos.close();
		
		
//		System.out.println(JSONObject.fromObject(taskRequest).toString());
		
	}
	
	public static class TaskRequest {
		
		private int taskId;
		private int houseId;
		
		private File fileZip;
		
		private HouseSupportingMeasures houseSupportingMeasures;

		public int getTaskId() {
			return taskId;
		}

		public void setTaskId(int taskId) {
			this.taskId = taskId;
		}

		public int getHouseId() {
			return houseId;
		}

		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}

		@JsonSerialize(using=FileSerializer.class)
		public File getFileZip() {
			return fileZip;
		}

		public void setFileZip(File fileZip) {
			this.fileZip = fileZip;
		}

		public HouseSupportingMeasures getHouseSupportingMeasures() {
			return houseSupportingMeasures;
		}

		public void setHouseSupportingMeasures(
				HouseSupportingMeasures houseSupportingMeasures) {
			this.houseSupportingMeasures = houseSupportingMeasures;
		}
		
	}
	
	/**
	 * @date 2014年5月21日 下午8:47:36
	 * @author Tom
	 * @description  
	 * 从阿里云下载图片
	 */
	public void downPhoto() {

//		// 构造ListObjectsRequest请求
//		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(Global.BUCKETNAME);
//
//		// 递归列出fun目录下的所有文件
//		listObjectsRequest.setPrefix("0000100001");
//
//		ObjectListing listing = OSSObjectUtil.client.listObjects(listObjectsRequest);
//
//		// 遍历所有Object
//		System.out.println("Objects:");
//		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
//		    System.out.println(objectSummary.getKey());
//		}
//
//		// 遍历所有CommonPrefix
//		System.out.println("\nCommonPrefixs:");
//		for (String commonPrefix : listing.getCommonPrefixes()) {
//		    System.out.println(commonPrefix);
//		}
		
//		File file = OSSObjectUtil.downloadFile("0000100001/000010000100001/00001000010000100001/0000100001000010000100001/3/1cd5d25d-a4d5-4be0-bdc5-04460ad20eca", "1.jpg");
//		try {
//			org.apache.commons.io.FileUtils.copyFile(file, new File("D:\\temp\\1.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		OSSObjectUtil.deleteFile4prefix("0000100001/");
		
	}
	
	/**
	 * @date 2014年5月21日 下午9:00:01
	 * @author Tom
	 * @description  
	 * 
	 */
	public void uploadPhoto() {
		String uuid = java.util.UUID.randomUUID().toString();
		System.out.println(uuid);
		//9cadf914-b2ac-4aec-88ab-d7bf5c23c2cf
		OSSObjectUtil.uploadFile("0000100001/" + uuid, "D:\\temp\\100002\\bedRoom\\bedRoom1\\1.jpg");
	}
	
	/**
	 * @date 2014年5月21日 下午9:00:01
	 * @author Tom
	 * @description  
	 * 
	 */
	public void deletePhoto() {
		String uuid = java.util.UUID.randomUUID().toString();
		System.out.println(uuid);

		OSSObjectUtil.deleteFile("9cadf914-b2ac-4aec-88ab-d7bf5c23c2cf");
	}
	
	@Data
	public static class HouseSupportingMeasures {

		//基础配置  //是否含有  true 1 是； false 0 否
		private boolean hasTV; //  电视 
		private boolean hasRefrigerator;//  冰箱 
		private boolean hasWashingMachine;//  洗衣机 
	}
	 

	/**
	 * @date 2014年4月29日 下午9:13:56
	 * @author Tom  
	 * @description  
	 * 检测版本
	 * 
	 * 请求地址：
	 * /rest/common/automaticUpdates.rest
	 */
	public void automaticUpdates() {
		String url = "http://localhost:8080/rest/common/automaticUpdates.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		
		pars.put("version", "1.0");
		
		InfoUtils.sendRestInter(url, pars);

	}

	/**
	 * @date 2014年5月21日 下午5:42:55
	 * @author Tom
	 * @description  
	 * 看房app 没看成
	 */
	public void taskLookFail() {
		String url = "http://localhost:8080/rest/bd/taskLookFail.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		
		pars.put("taskId", "13");
		pars.put("remark", "没看成没看成没看成没看成没看成");
		
		InfoUtils.sendRestInter(url, pars);

	}
	
	/**
	 * @date 2014年5月21日 下午5:42:55
	 * @author Tom
	 * @description  
	 * 周任务
	 */
	public void taskWeek() {
		String url = "http://localhost:8080/rest/bd/taskWeek.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		
		pars.put("employeeId", "11");
		
		InfoUtils.sendRestInter(url, pars);

	}
	
	/**
	 * @date 2014年4月29日 下午9:13:56
	 * @author Tom  
	 * @description  
	 * app 端  选择小区名称 进行模糊查询
	 * 
	 * 请求地址：
	 * /rest/common/findEstateByName.rest
	 */
	public void findEstateByName() {
		String url = "http://localhost:8080/rest/common/findEstateByName.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		

		
		pars.put("name", "大");
		
		InfoUtils.sendRestInter(url, pars);

	}
	
	
	/**
	 * @date 2014年4月29日 下午9:13:56
	 * @author Tom  
	 * @description  
	 * app 端  选择区域、房型/面积/价格 查询
	 * 
	 * 请求地址：
	 * houseresource/findRentByPage.rest
	 * houseresource/findSellByPage.rest
	 */
	public void findByPage() {
//		String url = "http://localhost:8080/rest/houseresource/findRentByPage.rest";
		String url = "http://localhost:8080/rest/houseresource/findSellByPage.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		

		
		pars.put("areaId", "37398");
//		pars.put("bedroomSum", "0");//房型(几房)
		
//		pars.put("startPrice", "0");//起始价格
//		pars.put("endPrice", "600");//截止价格
		
		pars.put("startSpaceArea", "0");// 起始内空面积
		pars.put("endSpaceArea", "50");// 截止内空面积
//		
//		pars.put("start", "1");//数据起始下标
		pars.put("max", "12");//返回的数据量(返回几条记录)
		
		InfoUtils.sendRestInter(url, pars);

	}
	
	/**
	 * @date 2014年4月29日 下午9:13:56
	 * @author Tom  
	 * @description  
	 * app 端 级联查询 区域列表
	 */
	public void findAreaByAreaId() {
		String url = "http://localhost:8080/rest/common/findAreaByAreaId.rest";
		Map<String, Object> pars = new HashMap<String, Object>();
		pars.put("areaId", "1");
		pars.put("notHasAll", "true");
		InfoUtils.sendRestInter(url, pars);

	}
	
	 
}
