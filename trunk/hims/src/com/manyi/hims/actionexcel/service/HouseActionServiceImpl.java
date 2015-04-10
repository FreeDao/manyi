package com.manyi.hims.actionexcel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manyi.hims.BaseService;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.aiwu.Building;
import com.manyi.hims.entity.aiwu.SubEstate;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.AreaStatusEnum;

@Service(value = "houseActionService")
@Scope(value = "singleton")
public class HouseActionServiceImpl extends BaseService implements HouseActionService{
	Logger log = LoggerFactory.getLogger(this.getClass());
	String path = "/mnt/disk/fybao/actions";
	
	/**
	 * 导入 房屋 
	 */
	@Override
	public String importHouse(MultipartFile excel) {
		String msg = new String("导入 房屋   时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 文件:"+ excel.getOriginalFilename() + "\n");
		StringBuffer sb = new StringBuffer(msg);
		int i = 0;
		if (excel != null && !excel.isEmpty()) {
			log.info(msg);
			try {
				// 以下语句读取生成的Excel文件内容
				InputStream fIn = excel.getInputStream();
				HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
				HSSFSheet readSheet = readWorkBook.getSheetAt(0);
				HSSFRow row = readSheet.getRow(0);//读取标题
				HSSFCell cell = row.getCell(0);
				// 获取excel的行数
				int num = readSheet.getLastRowNum();
				System.out.println("第一行标题 数据读取 完毕..");
				msg = "行数:" + num + "\n";
				sb.append(msg);
				log.info(msg);
				for (i = 1; i <= num; i++) {
					row = readSheet.getRow(i);
					if (row != null) {
						//houseId room 楼栋ID building 楼栋号+房源号	estateId 新小区ID 子划分ID estateName adds	cityId	cityName areaId	areaName townId	townName 是否删除(1:删除)
						cell = row.getCell(0);
						String id = "";
						if(cell != null){
							id = ActionExcel.changeCellToString(cell);
							id = id.trim();
						}else{
							msg = " 行数: "+(i+1)+" , 房屋ID为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						cell = row.getCell(1);
						String room = "";
						if(cell != null ){
							room = ActionExcel.changeCellToString(cell);
							room = room.trim();
						}else{
							msg = " 行数: "+(i+1)+" , 室号为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						cell = row.getCell(3);
						String buiNumber = "";
						int buiId = 0;
						int subeId = 0;
						int eid=0;
						if(cell != null ){
							buiNumber = ActionExcel.changeCellToString(cell);
							buiNumber = buiNumber.trim();
							log.info("楼栋number:"+buiNumber);
							List<Building> buis = this.getEntityManager().createQuery("from Building sube where sube.number = ?").setParameter(1, buiNumber).getResultList();
							if(buis != null && buis.size()>0){
								buiId = buis.get(0).getId();
								subeId =buis.get(0).getSubEstateId();
								eid = buis.get(0).getEstateId();
							}else{
								msg = " 行数: "+(i+1)+" , 所属楼栋不存在 \n";
								log.info(msg);
								sb.append(msg);
								continue;
							}
						}else{
							msg = " 行数: "+(i+1)+" , 楼栋ID为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						House house = this.getEntityManager().find(House.class, Integer.parseInt(id));
						//删除 部分 房屋
						cell = row.getCell(17);
						String del="";
						if(cell != null ){
							del = ActionExcel.changeCellToString(cell);
							del = del.trim();
						}
						if("1".equals(del)){
							//删除
							house.setStatus(EntityUtils.StatusEnum.DISABLED.getValue());//无效/删除
						}
						//查询是否存在 这个 房屋
						String jpql="from House h where h.subEstateId =? and h.room = ? and h.buildingId = ?";
						log.info("查询是否存在 这个 房屋 子划分ID :"+subeId +" 楼栋ID: "+buiId+"  room: "+room);
						List<House> houses = this.getEntityManager().createQuery(jpql).setParameter(1, subeId).setParameter(2, room).setParameter(3, buiId).getResultList();
						if(houses != null && houses.size()>0){
							if(!id.equals(houses.get(0).getHouseId()+"")){
								msg = " 行数: "+(i+1)+" , 房间 已经存在  库空房屋的Id: "+houses.get(0).getHouseId()+
										("1".equals(del) == true ? (" excel中Id :"+id+" 已做逻辑删除处理  ") : ("  excel中Id :"+id+" 未做逻辑删除 ") )+" \n";
								log.info(msg);
								sb.append(msg);
								continue;
							}
						}
						//开始解析 内容, 建立  房屋
						house.setEstateId(eid);
						house.setSubEstateId(subeId);
						house.setBuildingId(buiId);
						this.getEntityManager().merge(house);
						
						msg = " 行数: "+(i+1)+" , update 一个房屋  ID : "+house.getHouseId()+" \n";
						log.info(msg);
						sb.append(msg);
						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		msg ="导入房屋  完成 时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +" 处理数量: "+i+"行 , xsl文件导入完成...\n";
		log.info(msg);
		sb.append(msg);
		
		File pathFile = new File(path+"/log/");
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		File logFile = new File(path + "/log/" + "import-house.log");
		try {

			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			FileOutputStream sof = new FileOutputStream(logFile);
			sof.write(sb.toString().getBytes());
			sof.flush();
			sof.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		}
		return sb.toString();
	}

	/**
	 * 导入楼栋
	 */
	@Override
	public String importBilding(MultipartFile excel) {
		String msg = new String("导入 楼栋   时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 文件:"+ excel.getOriginalFilename() + "\n");
		StringBuffer sb = new StringBuffer(msg);
		int i = 0;
		if (excel != null && !excel.isEmpty()) {
			log.info(msg);
			try {
				// 以下语句读取生成的Excel文件内容
				InputStream fIn = excel.getInputStream();
				HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
				HSSFSheet readSheet = readWorkBook.getSheetAt(0);
				HSSFRow row = readSheet.getRow(0);//读取标题
				HSSFCell cell = row.getCell(0);
				// 获取excel的行数
				int num = readSheet.getLastRowNum();
				System.out.println("第一行标题 数据读取 完毕..");
				msg = "行数:" + num + "\n";
				sb.append(msg);
				log.info(msg);
				for (i = 1; i <= num; i++) {
					row = readSheet.getRow(i);
					if (row != null) {
						//楼栋ID	building	子划分ID
						cell = row.getCell(0);
						String id = "";
						if(cell != null){
							id = ActionExcel.changeCellToString(cell);
							id = id.trim();
						}else{
							msg = " 行数: "+(i+1)+" , 楼栋ID为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						cell = row.getCell(1);
						String name = "";
						if(cell != null ){
							name = ActionExcel.changeCellToString(cell);
							name = name.trim();
						}else{
							msg = " 行数: "+(i+1)+" , 楼栋名为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						cell = row.getCell(2);
						String subeNumber = "";
						int subeId = 0;
						int eid = 0;
						if(cell != null ){
							subeNumber = ActionExcel.changeCellToString(cell);
							subeNumber = subeNumber.trim();
							log.info("所属新子划分是否存在: number"+ subeNumber);
							List<SubEstate> subes = this.getEntityManager().createQuery("from SubEstate sube where sube.number = ?").setParameter(1, subeNumber).getResultList();
							if(subes != null && subes.size()>0){
								subeId= subes.get(0).getId();
								eid=subes.get(0).getEstateId();
							}else{
								msg = " 行数: "+(i+1)+" , 所属新子划分不存在\n";
								log.info(msg);
								sb.append(msg);
								continue;
							}
						}else{
							msg = " 行数: "+(i+1)+" , 所属新子划分ID为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						//查询是否存在 这个楼栋
						log.info("查询是否存在 这个楼栋 : number "+id +" name "+name);
						String jpql="from Building sube where sube.number = ? and sube.name = ? and sube.subEstateId =? ";
						List<SubEstate> subes= this.getEntityManager().createQuery(jpql).setParameter(1, id).setParameter(2, name).setParameter(3, subeId).getResultList();
						if(subes != null && subes.size()>0){
							msg = " 行数: "+(i+1)+" , ID : "+id+" 楼栋已经存在 \n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						//开始解析 内容, 建立  楼栋
						Building bui =new Building();
						bui.setAddTime(new Date());
						bui.setName(name);
						bui.setNumber(id);
						bui.setEstateId(eid);
						bui.setSubEstateId(subeId);
						bui.setStatus(AreaStatusEnum.SUCCESS.getValue());
						this.getEntityManager().persist(bui);
						
						msg = " 行数: "+(i+1)+" , 新增一个子划分  ID : "+bui.getId()+" number: "+ bui.getNumber()+" estateId : "+subeNumber+ " \n";
						log.info(msg);
						sb.append(msg);
						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		msg ="导入 楼栋  完成 时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +" 处理数量: "+i+"行 , xsl文件导入完成...\n";
		log.info(msg);
		sb.append(msg);
		
		File pathFile = new File(path+"/log/");
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		File logFile = new File(path + "/log/" + "import-building.log");
		try {

			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			FileOutputStream sof = new FileOutputStream(logFile);
			sof.write(sb.toString().getBytes());
			sof.flush();
			sof.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		}
		return sb.toString();
	}
	
	/**
	 * 导入 子划分
	 */
	@Override
	public String importSubEstate(MultipartFile excel) {
		String msg = new String("导入 子划分  时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 文件:"
				+ excel.getOriginalFilename() + "\n");
		StringBuffer sb = new StringBuffer(msg);
		int i = 0;
		if (excel != null && !excel.isEmpty()) {
			log.info(msg);
			try {
				// 以下语句读取生成的Excel文件内容
				InputStream fIn = excel.getInputStream();
				HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
				HSSFSheet readSheet = readWorkBook.getSheetAt(0);
				HSSFRow row = readSheet.getRow(0);//读取标题
				HSSFCell cell = row.getCell(0);
				// 获取excel的行数
				int num = readSheet.getLastRowNum();
				System.out.println("第一行标题 数据读取 完毕..");
				msg = "行数:" + num + "\n";
				sb.append(msg);
				log.info(msg);
				for (i = 1; i <= num; i++) {
					row = readSheet.getRow(i);
					if (row != null) {
						//子划分ID	子划分名	所属新小区id
						cell = row.getCell(0);
						String subeId = "";
						if(cell != null){
							subeId = ActionExcel.changeCellToString(cell);
							subeId = subeId.trim();
						}else{
							msg = " 行数: "+(i+1)+" , 子划分ID为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						cell = row.getCell(1);
						String subeName = "";
						if(cell != null ){
							subeName = ActionExcel.changeCellToString(cell);
							subeName = subeName.trim();
						}else{
							msg = " 行数: "+(i+1)+" , 子划分名为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						cell = row.getCell(2);
						String estateId = "";
						if(cell != null ){
							estateId = ActionExcel.changeCellToString(cell);
							estateId = estateId.trim();
							try {
								log.info("estateId : "+estateId);
								Estate estate = this.getEntityManager().find(Estate.class, Integer.parseInt(estateId));
								if(estate == null ){
									msg = " 行数: "+(i+1)+" , 所属新小区id不存在\n";
									log.info(msg);
									sb.append(msg);
									continue;
								}
							} catch (NumberFormatException e) {
								msg = " 行数: "+(i+1)+" , 所属新小区id为不是Int类型\n";
								log.info(msg);
								sb.append(msg);
								continue;
							}
							
							
						}else{
							msg = " 行数: "+(i+1)+" , 所属新小区id为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						//查询是否存在 这个子划分
						log.info("查询是否已经存在 这个 子划分栋 : number "+subeName +" estateId "+estateId);
						String jpql="from SubEstate sube where sube.name = ? and sube.estateId =? ";
						List<SubEstate> subes= this.getEntityManager().createQuery(jpql).setParameter(1, subeName).setParameter(2, Integer.parseInt(estateId)).getResultList();
						if(subes != null && subes.size()>0){
							msg = " 行数: "+(i+1)+" , ID : "+subeId+" 子划分已经存在,库中number:"+subes.get(0).getNumber()+" 生成的ID:"+subes.get(0).getId()+" \n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						//开始解析 内容, 建立  子划分表
						SubEstate sube = new SubEstate();
						sube.setAddTime(new Date());
						sube.setEstateId(Integer.parseInt(estateId));
						sube.setName(subeName);
						sube.setNumber(subeId);
						sube.setStatus(AreaStatusEnum.SUCCESS.getValue());
						this.getEntityManager().persist(sube);
						msg = " 行数: "+(i+1)+" , 新增一个子划分  ID : "+sube.getId()+" number: "+ sube.getNumber()+" estateId : "+estateId+ " \n";
						log.info(msg);
						sb.append(msg);
						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		msg ="导入 子划分  完成 时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +" 处理数量: "+i+"行 , xsl文件导入完成...\n";
		log.info(msg);
		sb.append(msg);
		
		File pathFile = new File(path+"/log/");
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		File logFile = new File(path + "/log/" + "import-subestate.log");
		try {

			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			FileOutputStream sof = new FileOutputStream(logFile);
			sof.write(sb.toString().getBytes());
			sof.flush();
			sof.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		}
		return sb.toString();
	}
}
