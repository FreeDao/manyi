/**
 * 
 */
package com.manyi.ihouse.subway.controller;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.ihouse.base.BaseController;
import com.manyi.ihouse.subway.service.DataImportService;
import com.manyi.ihouse.util.ExcelFileParseUtil;

/**
 * 数据导入测试,只在数据到时使用。
 * @author berlinluo
 * 2014年6月18日
 */

@Controller
@RequestMapping("/dataImport")
public class DataImportController extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("dataImportServiceImpl")
	DataImportService importDataService;
	
	@RequestMapping(value = "/subwayInfo.rest", produces="application/json")
	@ResponseBody
	public int importData() {
		 	ExcelFileParseUtil excel = new ExcelFileParseUtil();
	        excel.setPath("E:/temp/subwayline.xlsx");
	        excel.setSheetName("Sheet1");
	        try{	        	
	        	excel.process();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        List<List<String>> datas = excel.getDatas();
	        
		int result = importDataService.importSubwayInfoForOldTemplate(datas);
		if(logger.isDebugEnabled()){			
			logger.debug("import row=================="+result);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/sh/subwayLine.rest", produces="application/json")
	@ResponseBody
	public int importShSubwayLine() {
		 	ExcelFileParseUtil excel = new ExcelFileParseUtil();
	        excel.setPath("E:/temp/line_data_sh.xlsx");
	        excel.setSheetName("Sheet1");
	        try{	        	
	        	excel.process();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        List<List<String>> datas = excel.getDatas();
	        
		int result = importDataService.importSubwayLine(datas);
		if(logger.isDebugEnabled()){
			logger.debug("import row=================="+result);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/sh/subwayStation.rest", produces="application/json")
	@ResponseBody
	public int importShSubwayStation() {
		//System.out.println("start importSubwayStation ==================>>>>>>>");
		 	ExcelFileParseUtil excel = new ExcelFileParseUtil();
	        excel.setPath("E:/temp/station_data_sh.xlsx");
	        excel.setSheetName("Sheet1");
	        try{	        	
	        	excel.process();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        List<List<String>> datas = excel.getDatas();
	        
		int result = importDataService.importSubwayStation(datas);
		if(logger.isDebugEnabled()){
			logger.debug("import row=================="+result);
		}
		
		return result;
	}
	
	
	//拐点信息导入
	@RequestMapping(value = "/sh/subwayPoint.rest", produces="application/json")
	@ResponseBody
	public int importShSubwayPoint() {
		//System.out.println("start importSubwayStation ==================>>>>>>>");
		String dirPath = "E:/temp/point"; //多条线路文件在一个目录下
		 File dir = new File(dirPath);
		 String[] files = dir.list();
		 for(String str: files){
			 //System.out.println("str=="+str);
			 File file = new File(str);
			 //System.out.println("name="+file.getName()+"  path="+file.getPath()+ "getAbsolutePath=="+file.getAbsolutePath());
			 String name = file.getName();
			 int index = name.indexOf("=");
		//文件命名规则：城市编码=线路名.xslx
		
		 int cityCode = Integer.parseInt(name.substring(0, index));
		 String lineName = name.substring(index+1, name.indexOf("."));
		
		 	ExcelFileParseUtil excel = new ExcelFileParseUtil();
	        excel.setPath(dirPath+"/"+file.getPath());
	        excel.setSheetName("Sheet1");
	        try{	        	
	        	excel.process();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        List<List<String>> datas = excel.getDatas();
	        
	        
	        
		int result = importDataService.importSubwayPoint(datas, lineName, cityCode);
		System.out.println("reuslt is=======>>>>>>>>>>>>fileName="+name+ " export rows="+result);
		
		 }
		
		return 1;
	}
	
	
	
	@RequestMapping(value = "/bj/subwayLine.rest", produces="application/json")
	@ResponseBody
	public int importBjSubwayLine() {
		 	ExcelFileParseUtil excel = new ExcelFileParseUtil();
	        excel.setPath("E:/temp/line_data_bj.xlsx");
	        excel.setSheetName("Sheet1");
	        try{	        	
	        	excel.process();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        List<List<String>> datas = excel.getDatas();
	        
		int result = importDataService.importSubwayLine(datas);
		if(logger.isDebugEnabled()){
			logger.debug("import row=================="+result);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/bj/subwayStation.rest", produces="application/json")
	@ResponseBody
	public int importBjSubwayStation() {
		//System.out.println("start importSubwayStation ==================>>>>>>>");
		 	ExcelFileParseUtil excel = new ExcelFileParseUtil();
	        excel.setPath("E:/temp/station_data_bj.xlsx");
	        excel.setSheetName("Sheet1");
	        try{	        	
	        	excel.process();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        List<List<String>> datas = excel.getDatas();
	        
		int result = importDataService.importSubwayStation(datas);
		if(logger.isDebugEnabled()){
			logger.debug("import row=================="+result);
		}
		
		return result;
	}

}
