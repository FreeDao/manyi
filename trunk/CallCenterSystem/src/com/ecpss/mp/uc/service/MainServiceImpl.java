package com.ecpss.mp.uc.service;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.ServletContext;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.controller.ResidenceRestController.ResidenceInfo;
import com.ecpss.mp.entity.Area;
import com.ecpss.mp.entity.HouseDirection;
import com.ecpss.mp.entity.HouseRight;
import com.ecpss.mp.entity.HouseStatus;
import com.ecpss.mp.entity.HouseType;
import com.ecpss.mp.entity.HouseUsage;


@Service(value = "mainService")
@Scope(value = "singleton")
public class MainServiceImpl extends BaseService  implements MainService{
	/**
	 * 初始化数据 字典
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, List> initData(){
		Map<String, List> appconfigs = new HashMap<String, List>();
		CriteriaBuilder cb=getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<HouseStatus> hscq = cb.createQuery(HouseStatus.class);
		hscq.from(HouseStatus.class);
		List<HouseStatus> houseStatus = getEntityManager().createQuery(hscq).getResultList();
		appconfigs.put("hsList", houseStatus);
		
		CriteriaQuery<HouseDirection> hdcq = cb.createQuery(HouseDirection.class);
		hdcq.from(HouseDirection.class);
		List<HouseDirection> houseDirection = getEntityManager().createQuery(hdcq).getResultList();
		appconfigs.put("hdList", houseDirection);
		
		CriteriaQuery<HouseRight> hrcq = cb.createQuery(HouseRight.class);
		hrcq.from(HouseRight.class);
		List<HouseRight> HouseRight = getEntityManager().createQuery(hrcq).getResultList();
		appconfigs.put("hrList", HouseRight);
		
		CriteriaQuery<HouseType> htcq = cb.createQuery(HouseType.class);
		htcq.from(HouseType.class);
		List<HouseType> HouseType = getEntityManager().createQuery(htcq).getResultList();
		appconfigs.put("htList", HouseType);
		
		CriteriaQuery<HouseUsage> hucq = cb.createQuery(HouseUsage.class);
		hucq.from(HouseUsage.class);
		List<HouseUsage> HouseUsage = getEntityManager().createQuery(hucq).getResultList();
		appconfigs.put("huList", HouseUsage);
		
		//先把上海区域的 信息 加载到  内存中
		List<Area> areaList = loadArae("area_province",1+"");
		appconfigs.put("shanghai", areaList);
		
		return appconfigs;
	}
	
	/**
	 * 通过 parentid 得到所有的 地区信息
	 * @param parentId
	 * @return
	 */
	public List<Area> loadArae (String type , String parentId){
		String jpql ="";
		if(type.indexOf("province")>=0){
			jpql = "from City as _area where _area.parent.areaId = ?";
		}else if (type.indexOf("city")>=0){
			jpql = "from Town as _area where _area.parent.areaId = ?";
		}else{
			jpql = "from City as _area where _area.parent.areaId = ?";
			parentId ="1";
		}
		Query areaQuery = getEntityManager().createQuery(jpql);
		areaQuery.setParameter(1, Integer.valueOf(parentId));
		return areaQuery.getResultList();
	}

	/**
	 * 导出到excel
	 */
	@Override
	public String exportToExcel(ServletContext servletContext,String areaName) {

	    WebApplicationContext wac =    WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	    BasicDataSource dbs = (BasicDataSource) wac.getBean("dataSourceSql");
	    String str= "";
	    try {
	    	long starttime = System.currentTimeMillis();
	    	String xlsFile="d:/fangyou_data/filter/"+areaName;
	    	HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
			HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
			// 设置第一个工作表的名称为firstSheet
			// 为了工作表能支持中文，设置字符编码为UTF_16
			workbook.setSheetName(0, areaName);
			
			
			
			Connection con = dbs.getConnection();
			//String sql=" select distinct e.DistrictName , e.EstateName ,p.address from Property p , Estate e where p.EstateID = e.EstateID and e.DistrictName = ? ORDER BY e.EstateName asc ";
			String sql = "  select distinct e.DistrictName , e.EstateName ,p.address from Property p , Estate e where p.EstateID = e.EstateID and e.DistrictName = ? "
					+ "and e.EstateName not like '%新里%' "
					+ "and e.EstateName not like '%老公寓%' "
					+ "and e.EstateName not like '%公房%' "
					+ "and e.EstateName not like '%商铺%' "
					+ "and e.EstateName not like '%办公楼%' "
					+ "and e.EstateName not like '%仓库%' "
					+ "and e.EstateName not like '%厂房%' "
					+ "and e.EstateName not like '%别墅%' "
					+ "and e.EstateName not like '%石库门%' "
					+ "and e.EstateName not like '%洋房%' " 
					+ " ORDER BY e.EstateName asc ";
			
			PreparedStatement sta=con.prepareStatement(sql);
			sta.setString(1, areaName);
			ResultSet res =  sta.executeQuery();
			
			if(res != null){
				System.out.println("取出数据...");
				int i=0;
				
				//设置 表格中 每一列的宽度 
				sheet.setColumnWidth(0, 50*256);
				sheet.setColumnWidth(1, 20*256);
				
				// 产生一行
				HSSFRow rowTitle = sheet.createRow(i);
				
				// 产生第一个单元格
				HSSFCell cell1 = rowTitle.createCell(0);
				// 设置单元格内容为字符串型
				cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell1.setCellValue("小区");
				
				cell1 = rowTitle.createCell(1);
				// 设置单元格内容为字符串型
				cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell1.setCellValue("栋座");
				
				while (res.next()) {
					String ename =res.getString("EstateName");
					String address =res.getString("address");
					
					System.out.println(ename+","+address);
					
					// 产生一行
					HSSFRow row = sheet.createRow(i+1);
					i++;
					// 产生第一个单元格
					HSSFCell cell = row.createCell(0);
					// 设置单元格内容为字符串型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(ename);
					
					cell = row.createCell(1);
					// 设置单元格内容为字符串型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(address);
					
				}
				xlsFile +="-"+i+".xls";
				FileOutputStream fOut = new FileOutputStream(xlsFile);
				workbook.write(fOut);
				fOut.flush();
				fOut.close();
				System.out.println("文件生成...");
			}
			sta.close();
			con.close();
			long endtime = System.currentTimeMillis();
			str="数据导出成功! 用时:"+(endtime-starttime)+"ms, 文件路径:"+xlsFile;
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return str;
		
	}
	
	/**
	 * 测试  其他的 数据库连接(sql server )
	 */
	public List<ResidenceInfo> sqlServertest(ServletContext servletContext){
	    WebApplicationContext wac =    WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	    BasicDataSource dbs = (BasicDataSource) wac.getBean("dataSourceSql");
//	    System.out.println(dbs.getDriverClassName());
	    try {
			Connection con = dbs.getConnection();
			Statement sta= con.createStatement();
			String sql=" select top 100 DistrictName,Status,PropertyType,PropertyUsage,Remark,OwnerMobile,OwnerTel,PropertyNo,OwnerName from Property_copy where OwnerMobile like '%XXX%' and  Propertyno not like 'A%' order by propertyID asc ";
			//分页的方法 
			// select top pageSize * from Property where PropertyID not in (select top (pageSize*(pageNo-1)) propertyId from property order by propertyId ) order by propertyId;
			ResultSet res =  sta.executeQuery(sql);
			List<ResidenceInfo> infos =null;
			if(res != null){
				while (res.next()) {
					ResidenceInfo info = new ResidenceInfo();
					info.setAreaTitle(res.getString("DistrictName"));
					info.setStatusTitle(res.getString("Status"));
					info.setTypeTitle(res.getString("PropertyType"));
					info.setDirectionTitle(res.getString("PropertyUsage"));
					info.setRemark(res.getString("Remark"));
					info.setOwnerMobile(res.getString("OwnerMobile"));
					info.setOwnerName(res.getString("OwnerName"));
					info.setOwnerTel(res.getString("OwnerTel"));
					info.setId(res.getString("PropertyNo"));
					if(infos ==null){
						infos = new ArrayList<ResidenceInfo>();
					}
					infos.add(info);
				}
			}
			con.close();
			return infos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	    
	}
	
}
