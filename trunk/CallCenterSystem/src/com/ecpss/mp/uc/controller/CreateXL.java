package com.ecpss.mp.uc.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CreateXL {
	public static String xlsFile = "d:/test.xls"; // 产生的Excel文件的名称

	public static void main(String args[]) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
			HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
			// 设置第一个工作表的名称为firstSheet
			// 为了工作表能支持中文，设置字符编码为UTF_16
			workbook.setSheetName(0, "firstSheet");
			sheet.setColumnWidth(0, 50*256);//设置单元格 50字的宽度
			// 产生一行
			HSSFRow row = sheet.createRow(0);
			// 产生第一个单元格
			HSSFCell cell = row.createCell(0);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 往第一个单元格中写入信息
			cell.setCellValue("测试成功");
			FileOutputStream fOut = new FileOutputStream(xlsFile);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
			System.out.println("文件生成...");
			
			// 以下语句读取生成的Excel文件内容
			FileInputStream fIn = new FileInputStream(xlsFile);
			HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
			HSSFSheet readSheet = readWorkBook.getSheet("firstSheet");
			HSSFRow readRow = readSheet.getRow(0);
			HSSFCell readCell = readRow.getCell(0);
			System.out.println("第一个单元是：" + readCell.getStringCellValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}