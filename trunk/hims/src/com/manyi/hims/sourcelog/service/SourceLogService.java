package com.manyi.hims.sourcelog.service;

import java.util.List;

import com.manyi.hims.sourcelog.SourceLogResponse;
import com.manyi.hims.sourcelog.controller.SourceLogRestController.LoadInfoResponse;
import com.manyi.hims.sourcelog.controller.SourceLogRestController.UpdateDiscContentRequest;
import com.manyi.hims.sourcelog.controller.SourceLogRestController.UpdateDiscResponse;

public interface SourceLogService {

	/**
	 * 加载发布记录信息
	 * @param userId 用户ID
	 * @return
	 */
	public List<SourceLogResponse> indexList(int userId);

	/**
	 * 改盘第一步 判断是否能够改盘
	 * @param estateId
	 * @param building
	 * @param room
	 * @return
	 */
	public UpdateDiscResponse checkCanUpdateDisc(int estateId, String building, String room);

	/**
	 * 改盘第二步,提交 改盘信息
	 * @param req
	 * @return
	 */
	public int updateDisc(UpdateDiscContentRequest req);

	/**
	 * 清除 用户历史发布记录
	 * @param userId
	 * @return
	 */
	public int clearPublishLog(int userId);

	/**
	 * 加载出售记录信息
	 * @param logId
	 * @return
	 */
	public LoadInfoResponse loadSellInfoLog(int logId);
	
	/**
	 * 加载出租记录信息
	 * @param logId
	 * @return
	 */
	public LoadInfoResponse loadRentInfoLog(int logId);

	/**
	 * 加载出租记录信息 (分页)
	 * @param userId
	 * @param start
	 * @param end
	 * @param currTime
	 * @return
	 */
	public List<SourceLogResponse> findPageLog(int userId, int start, int end, Long currTime);

	/**
	 * 加载加载新增小区记录信息
	 * @param logId
	 * @return
	 */
	public List<SourceLogResponse> indexListAddAreaLog(int userId);
	
	/**
	 * 加载加载新增小区记录信息(分页)
	 * @param userId
	 * @param start
	 * @param end
	 * @param currTime
	 * @return
	 */
	public List<SourceLogResponse> findPageAddAreaLog(int userId, int start, int end, Long currTime);
}
