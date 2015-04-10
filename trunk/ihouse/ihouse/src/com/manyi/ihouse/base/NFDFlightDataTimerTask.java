package com.manyi.ihouse.base;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.manyi.ihouse.common.service.CommonService;

public class NFDFlightDataTimerTask extends TimerTask {

	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;
	
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// 执行 修改每个用户的上限数据量
		try {
			if(this.commonService == null){
				System.out.println("清理定时任务处理失败 ，原因:commonService = null,java.lang.NullPointerException");
			}
			
			this.commonService.clearUserPublishCount();
			System.out.println("清理定时任务处理完毕...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
