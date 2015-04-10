package com.manyi.hims.actionexcel.service;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ActionExcel {
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
			//readWorkBook.getSheetAt(1);
			HSSFRow readRow = readSheet.getRow(0);
			HSSFCell readCell = readRow.getCell(0);
			System.out.println("第一个单元是：" + readCell.getStringCellValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String changeCellToString(HSSFCell cell) {
		String returnValue = "";

		if (null != cell) {

			switch (cell.getCellType()) {

			case HSSFCell.CELL_TYPE_NUMERIC: // 数字

				Double doubleValue = cell.getNumericCellValue();
				String str = doubleValue.toString();
				BigDecimal bg = new BigDecimal(str);
				str = bg.toPlainString();
				if(str.endsWith(".0")){
					str = str.replace(".0", "");
				}

				returnValue = str;

				break;

			case HSSFCell.CELL_TYPE_STRING: // 字符串

				returnValue = cell.getStringCellValue();

				break;

			case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔

				Boolean booleanValue = cell.getBooleanCellValue();

				returnValue = booleanValue.toString();

				break;

			case HSSFCell.CELL_TYPE_BLANK: // 空值

				returnValue = "";

				break;

			case HSSFCell.CELL_TYPE_FORMULA: // 公式

				returnValue = cell.getStringCellValue();

				break;

			case HSSFCell.CELL_TYPE_ERROR: // 故障

				returnValue = "";

				break;

			default:

				System.out.println("未知类型");

				break;

			}

		}

		return returnValue.trim();

	}
}