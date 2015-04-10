package com.manyi.hims.common.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.manyi.hims.common.service.CommonService;

public class InitLimitQuartzTask extends AutomatonTask {
	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public void dayStart() {
		// TODO Auto-generated method stub
		if (manually) {
			return;
		}
		logger.info("start day InitLimitQuartzTask.... at {}", new Date());
		try {
			String jpql = "update HouseResourceViewCount p set p.publishCount = 0 ";
			int temp = this.commonService.clearUserPublishCount(jpql);
			System.out.println("清理定时任务处理完毕... 清理数量:  " + temp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void monthStart() {
		// TODO Auto-generated method stub
		if (manually) {
			return;
		}
		logger.info("start month InitLimitQuartzTask.... at {}", new Date());
		try {
			String jpql = "update HouseResourceViewCount p set p.publishMonthCount = 0 ";
			int temp = this.commonService.clearUserPublishCount(jpql);
			System.out.println("清理定时任务处理完毕... 清理数量:  " + temp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
