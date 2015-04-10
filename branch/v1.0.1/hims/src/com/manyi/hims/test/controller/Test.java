package com.manyi.hims.test.controller;

import com.leo.common.util.MD5Digest;

public class Test {
	
	public static void main(String[] args) {
		System.out.println(MD5Digest.getMD5Digest("111111q"));
		//5f8591a3d4fe06393cad53edff27ba05

//		ApplicationContext ac = new FileSystemXmlApplicationContext("E:\\workspace\\hims\\src\\com\\manyi\\hims\\test\\service\\TomContext.xml");
//		TomTestService tomTestService = (TomTestService)ac.getBean("tomTestService");

		
		/**
		 * 一、
		 * 
		 * @date 2014年4月28日 下午10:26:46
		 * @author Tom  
		 * @description  
		 * 将原有address地址 改到 子小区下
		 */
//		tomTestService.asdf();
		
		
		
		/**
		 * 
		 * 二、
		 * 
		 * step 1:
		 * @date 2014年4月28日 下午10:38:05
		 * @author Tom  
		 * @description  
		 * 将子小区的parentId更换成主校区的parentId
		 */
//		tomTestService.fsd();
//		step 2: delete from Area where dtype = 'Estate'
//		step 3:update Area set name = CONCAT(name,	road) where dtype = 'SubEstate'
//		step 4:update Area set dtype = 'Estate' where dtype = 'SubEstate'

		
		
		

		/**
		 * 三、
		 * 
		 * step 1
		 * @date 2014年4月27日 下午8:18:15
		 * @author Tom  
		 * @description  
		 * 生成Area表serialCode方法：
		 * 调用tomTestService.insertCod("", -1);
		 */ 
		
//		tomTestService.insertCod("", -1);
//		step 2
//		update House h set h.serialCode = (select serialCode from Area where areaId =  h.estateId)
		 

		
//		System.out.println(Math.abs(-5*60*1000));
		
		
	}
	

	
	
	
}
