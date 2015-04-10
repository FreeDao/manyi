package com.manyi.hims.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author linto
 */

public class ExcelUtil {
	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	private boolean debugAble = logger.isDebugEnabled();
	/*如果存在是否更新*/
	private boolean flag = false;
	/**
	 * 创建新excel
	 * path  文件存存路径
	 * fileName  文件名
	 * sheetName sheet名
	 * head  excel表头        head中的个数及顺序必须和obj中的一致可参考main
	 * obj   对应数据bean,相关属性及对应get方法
	 * 
	 * 
	 * result 0为成功，-1为失败
	 * 
	 * */
	public int createExcel(String path,String fileName,String sheetName,List<String> head,List<Object> obj){
		
		if(debugAble)
			logger.debug("path="+path+" fileName="+fileName+" sheetName="+sheetName+" head="+head+" obj="+obj);
		WritableWorkbook book = null;
		int sheetSize = 0;
		/*获取excel*/
		try {
			if(flag){
				Workbook old = getOldExcel(path,fileName);
				sheetSize = old.getSheets().length;
				book = Workbook.createWorkbook( new File(path,fileName),old);
			}else{
				book = Workbook.createWorkbook( new File(path,fileName));
			}
			fillExcel(book,sheetName,sheetSize,head,obj);
			book.write();
			book.close();
			return 0;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (RowsExceededException e) {
			logger.error(e.getMessage(), e);
		} catch (WriteException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		} catch (SecurityException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		} finally{
			try {
				if(book != null)
				book.close();
			} catch (WriteException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return -1;
	}
	private void fillExcel(WritableWorkbook book,String sheetName,int sheetSize, List<String> head,
			List<Object> objList) throws RowsExceededException, WriteException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		WritableSheet sheet = book.createSheet(sheetName, 0 );
		/*填充头部*/
		for(int i = 0;i<head.size();i++){
			Label label = new Label( i , 0 , head.get(i));
			sheet.addCell(label);
		}
		int column = 1;
	    /*填充数据*/
		for(Object obj:objList){
			fillBody(sheet,column++,obj);
		}
		
	}
	private void fillBody(WritableSheet sheet, int column,Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, RowsExceededException, WriteException {
	    Class<? extends Object> clazz = obj.getClass();
		Field fields[] = clazz.getDeclaredFields();
		int i = 0;
		for(Field f:fields){
			if(f.getName().equals("serialVersionUID"))
			continue;
		String methodName = convertMethod("get",f.getName());
		Method get = clazz.getDeclaredMethod(methodName);
		String data = String.valueOf(get.invoke(obj));
		Label label = new Label( i , column , data);
		sheet.addCell(label);
		i++;
		}
		
	}
	private String convertMethod(String get, String name) {
		return get+Character.toUpperCase(name.charAt(0))+name.substring(1);
	}
	private Workbook getOldExcel(String path, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 下列方法后续更新
	public int alterExcel(){}
	
	public int removeExcel(){}*/

	public static void main(String[] args) {
		List<String> head = new ArrayList<String>();
		head.add("类名");
		head.add("数量");
		head.add("总价");
		
		City city1 = new City("上海",1,123);
		City city2 = new City("南京",8,456);
		City city3 = new City("北京",17,789);
		List<Object> list = new ArrayList<Object>();
		list.add(city1);
		list.add(city2);
		list.add(city3);
		
        new ExcelUtil().createExcel("d:", "test1.xls", "第一页", head, list);
	}

}
class City implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;//
	private int num;
	private int count;// 区域名（例如：上海、湖北、长宁、古北）
	public City(String name,int num,int count){
		this.name = name;
		this.num = num;
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
