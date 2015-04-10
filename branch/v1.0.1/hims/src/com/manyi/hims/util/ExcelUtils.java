/**
 * 
 */
package com.manyi.hims.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc
 */
public class ExcelUtils {

	final static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

	public interface IExcel<T> {

		public void initHSSRow(List<T> list, HSSFSheet sheet);
	}

	/**
	 * 默认实现新建excel，row生成规则，以T的属性字段顺序爲正確順序，title的顺序必须与T的属性字段一致
	 * 
	 * @param response
	 * @param list
	 * @param xlsName
	 * @param headTitle
	 * @return
	 */
	public static <T> boolean exportExcel(HttpServletResponse response, List<T> list, String xlsName, String... headTitle) {

		return exportExcel(response, list, xlsName, new IExcel<T>() {

			@Override
			public void initHSSRow(List<T> list, HSSFSheet sheet) {
				HSSFCell cell;
				for (int j = 0; j < list.size(); j++) {
					T row = list.get(j);
					if (row == null) {
						continue;
					}
					HSSFRow hssrow = sheet.createRow(j + 1);
					Map<String, String> fieldValMap = getFieldValueMap(row);
					Iterator<String> ir = getFieldValueMap(row).keySet().iterator();
					int i = 0;
					while (ir.hasNext()) {
						Object value = fieldValMap.get(ir.next());
						if (value != null) {
							cell = hssrow.createCell(i++);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(value + StringUtils.EMPTY);
						}
					}
				}
			}
		}, headTitle);
	}

	/**
	 * 新建excel，row生成规则，请实现IExcel.initHSSRow方法
	 * 
	 * @param response
	 * @param list
	 * @param name
	 * @param iExcel
	 * @param headTitle
	 * @return
	 */
	public static <T> boolean exportExcel(HttpServletResponse response, List<T> list, String name, IExcel<T> iExcel, String... headTitle) {
		if (list == null || list.size() == 0) {
			try {
				response.getOutputStream().print("Not conform to the requirements of data");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		String xlsName = name + (new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date()));
		log.info(xlsName);
		// 产生工作簿对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 产生工作表对象
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, xlsName);

		for (int i = 0; i < headTitle.length; i++) {
			// 设置 表格中 每一列的宽度
			sheet.setColumnWidth(i, 10 * 256);
		}
		HSSFRow rowTitle = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i = 0; i < headTitle.length; i++) {
			// 产生第一个单元格
			cell = rowTitle.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(headTitle[i]);
		}
		// 写row
		iExcel.initHSSRow(list, sheet);
		try {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("content-disposition", "attachment;filename=" + xlsName + ".xls");
			// 写入到 客户端response
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			log.info(e.getMessage());
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		return true;
	}

	/**
	 * 取Bean的属性和值对应关系的MAP
	 */
	public static Map<String, String> getFieldValueMap(Object bean) {
		Class<?> cls = bean.getClass();
		Map<String, String> valueMap = new HashMap<String, String>();
		Method[] methods = cls.getDeclaredMethods();
		Field[] fields = cls.getDeclaredFields();

		for (Field field : fields) {
			try {
				String fieldType = field.getType().getSimpleName();
				String fieldGetName = parGetName(field.getName());
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				if (StringUtils.equals("serialVersionUID", field.getName())) {
					continue;
				}
				Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
				String result = null;
				if (StringUtils.equals("Date", fieldType)) {
					result = fmtDate((Date) fieldVal);
				} else {
					if (null != fieldVal) {
						result = String.valueOf(fieldVal);
					}
				}
				valueMap.put(field.getName(), result);
			} catch (Exception e) {
				continue;
			}
		}
		return valueMap;
	}

	private static boolean checkGetMet(Method[] methods, String fieldGetMet) {
		for (Method met : methods) {
			if (fieldGetMet.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}

	private static String parGetName(String fieldName) {
		if (StringUtils.isEmpty(fieldName)) {
			return null;
		}
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	private static String fmtDate(Date date) {
		if (null == date) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
}
