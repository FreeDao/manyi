package com.manyi.hims.bd.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.Response;
import com.manyi.hims.area.service.AreaService;
import com.manyi.hims.bd.BdConst;
import com.manyi.hims.bd.controller.HouseResourcePhotoController.HouseFileRequest;
import com.manyi.hims.bd.controller.HouseResourcePhotoController.TaskLookFailRequest;
import com.manyi.hims.bd.controller.HouseResourcePhotoController.TaskRequest;
import com.manyi.hims.bd.controller.HouseResourcePhotoHandler;
import com.manyi.hims.entity.BdTask;
import com.manyi.hims.entity.HouseImageFile;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseSupportingMeasures;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.house.service.HouseService;
import com.manyi.hims.sourcemanage.service.SourceManageService;
import com.manyi.hims.uc.UcConst;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.HouseResourceType;
import com.manyi.hims.util.EntityUtils.TaskStatusEnum;
import com.manyi.hims.util.EntityUtils.houseSuppoertEnum;
import com.manyi.hims.util.FileUtils;
import com.manyi.hims.util.ImageUtils;
import com.manyi.hims.util.OSSObjectUtil;
import com.manyi.hims.util.ThumbnailUtil;
@Service(value = "houseResourcePhotoService")
@Scope(value = "singleton")
public class HouseResourcePhotoServiceImpl extends BaseService implements HouseResourcePhotoService {

	@Value("${constants.uploadBasePath}")
	private String imgPath;
	
	private static Logger logger = LoggerFactory.getLogger(HouseResourcePhotoServiceImpl.class);

	@Autowired
	private SourceManageService sourceManageService;
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private AreaService areaService;
	
    @Autowired
    private HouseResourcePhotoHandler houseResourcePhotoHandler;
    
    /**
	 * @date 2014年5月30日 下午8:01:29
	 * @author Tom
	 * @description  
	 * 
	 *将 imgMap 转换成 imgKeyMap
	 */
    private Map<String, String> changeImgMap2ImgKeyMap(Map<String, String> imgMap, String prefix) {
    	Map<String, String> imgKeyMap = new HashMap<>();
    	
    	String imgKey = "";
    	for (Map.Entry<String, String> entry : imgMap.entrySet()) {
			imgKey = prefix + getImageName(entry.getKey()); 

    		imgKeyMap.put(imgKey, entry.getKey());
    	}
    	return imgKeyMap;

	}
    
    /**
	 * @date 2014年5月30日 下午8:01:29
	 * @author Tom
	 * @description  
	 * 当尝试3次上传阿里云失败后，不能再上传，图片都在服务器上；这是小概率事件
	 * 
	 * 弥补这种情况，我们可以单独对没有上传的图片进行再次上传操作。
	 * 
	 */
    private void uploadFile2aliyunOSSAgain(int houseId) {
//		第一部：图片处理
		//图片所在服务器路径
		String housePhotoPath = this.getFilePath(houseId);
		String aliyunPathPrefix = this.getHouseIdPrefix(houseId);
		
//		1、得到图片
		Map<String, String> imgMap = this.getImageMap(housePhotoPath);
		
		Map<String, String> imgKeyMap = this.changeImgMap2ImgKeyMap(imgMap, aliyunPathPrefix);
    	
//      1、再上传
		OSSObjectUtil.uploadFile(imgKeyMap);
//		
//      2、删除hims服务器上的临时目录下的 
		deleteLajiFile(housePhotoPath);
	}
    
    
    /**
   	 * @date 2014年5月30日 下午8:01:29
   	 * @author Tom
   	 * @description  
   	 * 当尝试3次上传阿里云失败后，不能再上传，图片都在服务器上；这是小概率事件
   	 * 
   	 * 弥补这种情况，我们可以单独对没有上传的图片进行再次上传操作。
   	 * 
   	 * 查询前一天的数据
   	 * 
   	 */
    public void uploadFile2aliyunOSSAgain() {
		List<Object[]> list = this.getEntityManager().createNativeQuery("select id, houseId from BdTask task where aliyunOSSAgain = 1 and finishDate < DATE_ADD(now(), Interval -1 HOUR)").getResultList();
		
		logger.info("人工手动上传房子图片任务开启！");
		for (Object[] obj : list) {
			uploadFile2aliyunOSSAgain(Integer.parseInt(obj[1].toString()));
			
			BdTask t = this.getEntityManager().find(BdTask.class, Integer.parseInt(obj[0].toString()));
			t.setAliyunOSSAgain(0);
			logger.info("再次上传houseId={}的房子图片成功！", Integer.parseInt(obj[1].toString()));
		}
		logger.info("人工手动上传房子图片任务结束！");

	}
    
	
	/**
	 * @date 2014年5月19日 下午8:01:29
	 * @author Tom
	 * @description  
	 * 提交信息
	 * 
	 * 第一部：图片处理
	 * 第二部：更新任务成功状态
	 * 第四步：修改房屋基本信息
	 * 第五步：维护房源信息
	 * 第六部：保存房屋的配套信息
	 * 第七部：保存图片到阿里云
	 * 
	 */
	public void taskSubmit(TaskRequest taskRequest) {

//		第一部：图片处理
		//图片所在服务器路径
		String housePhotoPath = this.getFilePath(taskRequest.getHouseId());
		String aliyunPathPrefix = this.getHouseIdPrefix(taskRequest.getHouseId());
		
//		1、得到图片
		Map<String, String> imgMap = this.getImageMap(housePhotoPath);
		
		if (taskRequest.getPicNum() != (imgMap.size()/2)) {//照片数 = 图片总数/2
			logger.info("上传图片数量与成功上传图片数目不一致！请重新上传！");
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		
//		2、保存图片信息到数据库
		Map<String, String> imgKeyMap = this.saveHouseImageFile(imgMap, taskRequest.getHouseId(), aliyunPathPrefix);
		
		
//		第二部：更新任务成功状态
		this.updateBDTask(mapString(imgMap), taskRequest);
		
		
//		第四步：修改房屋基本信息
		Residence residence = new Residence();
		residence.setHouseId(taskRequest.getHouseId());
		residence.setBedroomSum(taskRequest.getBedroomSum());// 几房
		residence.setLivingRoomSum(taskRequest.getLivingRoomSum());// 几厅
		residence.setWcSum(taskRequest.getWcSum());// 几卫
		residence.setSpaceArea(taskRequest.getSpaceArea());// 内空面积
		residence.setDecorateType(taskRequest.getDecorateType()); //1 毛坯，2 装修
		residence.setPicNum(imgMap.size()/2); //照片数 = 图片总数/2;  
		
		Response response = houseService.checkUpdateResidence(residence);
		if (response.getErrorCode() != 0) {
			logger.info("修改房屋基本信息错误！houseId={}", taskRequest.getHouseId());
			throw new LeoFault(BdConst.BD_ERROR140001);
		}


//		第五步：维护房源信息
		Response response1 = sourceManageService.checkUpdateSource(taskRequest);
		if (response1.getErrorCode() != 0) {
			logger.info("维护房源信息错误！houseId={}", taskRequest.getHouseId());
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		
//		第六部：保存房屋的配套信息
		this.saveHouseSupportingMeasures(taskRequest);
		
		
//		第七部：保存图片到阿里云
		houseResourcePhotoHandler.uploadFile2aliyunOSS(aliyunPathPrefix, imgKeyMap, housePhotoPath, 0, taskRequest.getTaskId(), taskRequest.getHouseId());

	}
	
	
	/**
	 * @date 2014年5月28日 下午12:44:19
	 * @author Tom
	 * @description  
	 * 更新任务再次上传状态
	 */
	public void updateBdtask4AliyunOSSState(int taskId) {
		BdTask bdTask = this.getEntityManager().find(BdTask.class, taskId);
		bdTask.setAliyunOSSAgain(0);
	}
	
	
	
	
	
	/**
	 * @date 2014年5月20日 下午7:06:29
	 * @author Tom
	 * @description  
	 * 更新任务图片统计字段/LBS信息
	 */
	private void updateBDTask(String taskImgString, TaskRequest taskRequest) {
		DecimalFormat df = new DecimalFormat("#.######");
		BdTask bdTask = this.getEntityManager().find(BdTask.class, taskRequest.getTaskId());
		bdTask.setTaskImgStr(taskImgString);
		bdTask.setLatitude(taskRequest.getLatitude());
		bdTask.setLongitude(taskRequest.getLongitude());
		bdTask.setRemark(taskRequest.getRemark());
		bdTask.setTaskStatus(TaskStatusEnum.LOOK_SUCCESS.getValue());
		bdTask.setAliyunOSSAgain(1);
		
		Residence residence = this.getEntityManager().find(Residence.class, taskRequest.getHouseId());
		
		String houseTypeStr = residence.getBedroomSum() + "房" + residence.getLivingRoomSum() + "厅" + residence.getWcSum() + "卫";
		houseTypeStr += " --> ";
		houseTypeStr += taskRequest.getBedroomSum() + "房" + taskRequest.getLivingRoomSum() + "厅" + taskRequest.getWcSum() + "卫";
		
		String spaceAreaStr = df.format(residence.getSpaceArea()) + "平米" + " --> " + taskRequest.getSpaceArea() + "平米";
		
		HouseResource houseResource = this.getEntityManager().find(HouseResource.class, taskRequest.getHouseId());
		
		String rentPriceStr = df.format((houseResource.getRentPrice() == null ? 0 : houseResource.getRentPrice())) + "元/月" + " --> " + taskRequest.getRentPrice() + "元/月";
		String sellPriceStr = df.format((houseResource.getSellPrice() == null ? 0 : houseResource.getSellPrice())) + "万" + " --> " + taskRequest.getSellPrice() + "万";
		
		bdTask.setSpaceAreaStr(spaceAreaStr);
		bdTask.setRentPriceStr(rentPriceStr);
		bdTask.setSellPriceStr(sellPriceStr);
		bdTask.setHouseTypeStr(houseTypeStr);
		
		bdTask.setFinishDate(new Date());
	}
	
	
	/**
	 * @date 2014年5月20日 下午5:06:50
	 * @author Tom
	 * @description  
	 * 保存房屋的配套信息
	 */
	private void saveHouseSupportingMeasures(TaskRequest taskRequest) {
		//bd看房不需要审核直接成功，需要将之前可用配套信息，置为不可用
		List<HouseSupportingMeasures> list = this.getEntityManager().createQuery("from HouseSupportingMeasures where houseId = ? and enable = 1").setParameter(1, taskRequest.getHouseId()).getResultList();
		for (HouseSupportingMeasures houseSupportingMeasures : list) {
			houseSupportingMeasures.setEnable(0);
		}

		HouseSupportingMeasures houseSupportingMeasures = new HouseSupportingMeasures();
		
		BeanUtils.copyProperties(taskRequest.getHouseSupportingMeasures(), houseSupportingMeasures);
		houseSupportingMeasures.setEnable(1);
		houseSupportingMeasures.setHouseId(taskRequest.getHouseId());
		try {
			String s = this.mapString(houseSupportingMeasures);
			houseSupportingMeasures.setSupportingMeasuresStr("".equals(s)?"毛坯":"带装修：" + s);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new LeoFault(BdConst.BD_ERROR140001);
		}

		this.getEntityManager().persist(houseSupportingMeasures);
	}

	/**
	 * @date 2014年5月20日 下午6:39:08
	 * @author Tom
	 * @description  
	 * 获得本地文件父目录
	 */
	private String getFilePath(int houseId) {
//		return this.imgPath + File.separator + houseId + File.separator;
		return this.imgPath + File.separator + houseId + "_bdTask" + File.separator;
	}
	
	/**
	 * @date 2014年5月22日 上午4:22:31
	 * @author Tom
	 * @description  
	 * 返回阿里云的houseId前最
	 */
	private String getHouseIdPrefix(int houseId) {
//		return areaService.getAliyunPath(houseService.getSerialCodeByHouseId(houseId)) + houseId + File.separator;
		return areaService.getAliyunPath(houseService.getSerialCodeByHouseId(houseId)) + houseId + "/";
	}
	
	/**
	 * @date 2014年5月20日 上午9:59:28
	 * @author Tom
	 * @description  
	 * 得到图片的路径map
	 * 	数据格式如下：
	 * 	Map<D:\temp\100001\bedRoom1\1.jpg, bedRoom1>
	 */
	private Map<String, String> getImageMap(String fileFolder) {
		Map<String, String> map = new HashMap<>();
		
		FileUtils.rdFile(map, fileFolder);

		return map;
	}
	
	
	/**
	 * @date 2014年5月26日 下午4:59:57
	 * @author Tom
	 * @description  
	 * 截取图片名称
	 */
	private String getImageName(String path) {
		
		int i = path.lastIndexOf(File.separator) + 1;
		int j = path.lastIndexOf(".");
		
		return path.substring(i, j);

	}
	
	/**
	 * @date 2014年5月20日 下午1:20:27
	 * @author Tom
	 * @description  
	 * 保存图片信息到数据库
	 * 	Map<D:\temp\100001\bedRoom\bedRoom1\1.jpg, bedRoom1>
	 * 
	 * @return
	 * 图片key的封装map
	 */
	private Map<String, String> saveHouseImageFile(Map<String, String> imgMap, int houseId, String prefix) {
		this.getEntityManager().createQuery("delete from HouseImageFile where houseId = ? ").setParameter(1, houseId).executeUpdate();
		
		HouseImageFile houseImageFile = null;
		Map<String, String> imgKeyMap = new HashMap<>();
		String imgKey = "";
		
		for (Map.Entry<String, String> entry : imgMap.entrySet()) {
			imgKey = prefix + getImageName(entry.getKey()); 
			imgKeyMap.put(imgKey, entry.getKey());
			
			//如果不是缩略图
			if (!entry.getKey().contains("thumbnail")) {
				
				houseImageFile = new HouseImageFile();
				houseImageFile.setHouseId(houseId);
				houseImageFile.setDescription(EntityUtils.HouseResourceType.getByKey(entry.getValue()).getDesc());
				houseImageFile.setType(EntityUtils.HouseResourceType.getByKey(entry.getValue()).getType());
				houseImageFile.setImgExtensionName(entry.getKey().substring(entry.getKey().lastIndexOf(".") + 1));
				houseImageFile.setTakePhotoTime(ImageUtils.getTakePhotoDate(new File(entry.getKey())));
				houseImageFile.setImgKey(imgKey);
				houseImageFile.setThumbnailKey(imgKey + ".thumbnail");
				houseImageFile.setOrderId(EntityUtils.HouseResourceType.getByKey(entry.getValue()).getValue());
				houseImageFile.setEnable(1);
				
				this.getEntityManager().persist(houseImageFile);
			}
		}
		return imgKeyMap;
	}
	
	
	/*映射成String*/
	private String mapString(final Map<String, String> iMap){
		Map<String,Integer> con = new HashMap<String, Integer>();
		Iterator<String> it = iMap.values().iterator();
		while(it.hasNext()){
			String key = it.next();
			if(con.get(key) != null){
				con.put(key, con.get(key)+1);
			}else{
				con.put(key, 1);
			}
		}
		return toString(con);
	}

	private String toString(Map<String, Integer> con) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>();  
		list.addAll(con.entrySet());  

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry obj1, Map.Entry obj2) {// 从低往高排序

				if (EntityUtils.HouseResourceType.getByKey(obj1.getKey().toString()).getValue() < EntityUtils.HouseResourceType.getByKey(obj2.getKey().toString()).getValue())
					return -1;
				if (EntityUtils.HouseResourceType.getByKey(obj1.getKey().toString()).getValue() == EntityUtils.HouseResourceType.getByKey(obj2.getKey().toString()).getValue())
					return 0;
				else
					return 1;
			}
		});
		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Integer> entry : list) {
			HouseResourceType rt = EntityUtils.HouseResourceType.getByKey(entry.getKey());
			sb.append(rt.getDesc()).append("(").append(entry.getValue()/2).append("),");
		}
		return sb.toString();
	}
	
	/*房屋附属信息生成字符串，生成冗余字段，方便查询*/
	private String mapString(HouseSupportingMeasures hs) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	    StringBuilder sb = new StringBuilder();
		Class<? extends Object> clazz = hs.getClass();
		Field fields[] = clazz.getDeclaredFields();
		for(Field f:fields){
			if(!f.getName().startsWith("has")){
				continue;
			}
			String methodName = convertMethod("is",f.getName());
			Method get = clazz.getDeclaredMethod(methodName);
			Object key = get.invoke(hs);
			if(new Boolean(key.toString())){
				houseSuppoertEnum s = EntityUtils.houseSuppoertEnum.valueOf(String.valueOf(f.getName()));
				if(s != null){
					sb.append(s.getCn()).append("、");
				}
			}
		}
		if(sb.length() > 0)
		sb.replace(sb.length()-1, sb.length(), "。");
		return sb.toString();
		
	}
	private String convertMethod(String get, String name) {
		return get+Character.toUpperCase(name.charAt(0))+name.substring(1);
	}
	
	/**
	 * @date 2014年5月21日 下午2:21:11
	 * @author Tom
	 * @description  
	 * 没看成任务
	 */
	public void taskLookFail(TaskLookFailRequest taskLookFailRequest) {
		
		BdTask bdTask = this.getEntityManager().find(BdTask.class, taskLookFailRequest.getTaskId());
		bdTask.setRemark(taskLookFailRequest.getRemark());
		bdTask.setTaskStatus(TaskStatusEnum.LOOK_FALIED.getValue());
		bdTask.setFinishDate(new Date());
	}
	
	/**
	 * @date 2014年5月21日 下午2:21:11
	 * @author Tom
	 * @description  
	 * 保存单张图片到服务器
	 */
	public void taskUploadSingleFile(HouseFileRequest houseFileRequest) {
		
		//如果是第一张图片，先清理图片目录
		if (houseFileRequest.getFileOrderFlag() == 1) {
			this.deleteLajiFile(this.getFilePath(houseFileRequest.getHouseId()));
		}
		
		File dpathFile = new File(this.getFilePath(houseFileRequest.getHouseId()) + houseFileRequest.getPhotoFolder());
		// copy本地文件到制定的文件下
		if (!dpathFile.exists()) {
			// 不存在的目录就创建 多级目录
			dpathFile.mkdirs();
		}
		
		String imgKey = java.util.UUID.randomUUID().toString(); 

		String copyPath = dpathFile.getPath() + File.separator + imgKey + ".jpg";
		String thumbnailPath = dpathFile.getPath() + File.separator + imgKey + ".thumbnail.jpg";
				
		File copyPathFile = new File(copyPath);
		try {
			copyPathFile.createNewFile();
			//1、将服务器临时图片文件复制到指定文件夹下
			org.apache.commons.io.FileUtils.copyFile(houseFileRequest.getFile(), copyPathFile);
			
			org.apache.commons.io.FileUtils.deleteQuietly(houseFileRequest.getFile());

			//2、将图片文件生成缩略图
			ThumbnailUtil.getThumbnail(copyPathFile, new File(thumbnailPath));

		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000039);
		}
				
	}
	
	
	/**
	 * @date 2014年5月21日 下午2:21:11
	 * @author Tom
	 * @description  
	 * 删除服务器上的垃圾文件
	 */
	public void deleteLajiFile(String filePath) {
		
		FileUtils.deleteFileDir(new File(filePath));

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
