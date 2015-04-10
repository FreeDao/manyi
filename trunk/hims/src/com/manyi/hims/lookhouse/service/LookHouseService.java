/**
 * 
 */
package com.manyi.hims.lookhouse.service;

import java.util.List;

import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.check.model.CSSingleRequest;
import com.manyi.hims.entity.HouseImageFile;
import com.manyi.hims.lookhouse.model.LookHouseReq;
import com.manyi.hims.lookhouse.model.LookHouseRes;
import com.manyi.hims.lookhouse.model.PlanReq;
import com.manyi.hims.lookhouse.model.PlanRes;
import com.manyi.hims.lookhouse.model.RandomReq;
import com.manyi.hims.lookhouse.model.SubmitRequest;
import com.manyi.hims.lookhouse.model.UserLookHouseReq;
import com.manyi.hims.lookhouse.model.UserLookHouseRes;
import com.manyi.hims.lookhouse.model.UserTaskSubmitRequest;


/**
 * 
 * @author tiger
 *
 */
public interface LookHouseService {

	/**
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	public PageResponse<LookHouseRes> lookHouseList(LookHouseReq req);

	/**
	 * 选择房子,启动 看房任务
	 * @param houseIds
	 * @return
	 */
	public Response addBdTask(LookHouseReq req);

	/**
	 *  随机抽查看房 选择房子,启动 看房任务
	 * @param req
	 * @return
	 */
	public Response randomBdTask(RandomReq req);
	
	/**
	 * 电话预约任务详情页，关于BD看房
	 * @param css
	 * @return
	 */
	public Response single(CSSingleRequest css);
	
	/**
	 * 电话预约任务，关于BD看房
	 * @param sr
	 * @return
	 */
	public Response submit(SubmitRequest sr);
	
	/**
	 * 中介看房任务审核
	 * @param ut
	 * @return
	 */
	public Response userTaskSubmit(UserTaskSubmitRequest ut);
	
	/**
	 *  查看BD某个时间段的排班情况
	 * @param req
	 * @return
	 */
	public PageResponse<PlanRes> planList(PlanReq req);

	/**
	 * 经纪人 看房 房源任务信息  列表
	 * @param req
	 * @return
	 */
	public PageResponse<UserLookHouseRes> userLookHouseList(UserLookHouseReq req);

	/**
	 * 通过 user task id 查询 user task 任务
	 * @param id
	 * @return
	 */
	public UserLookHouseRes getUserTaskById(int id);

	/**
	 *  通过 user task id 获得缩略图 图片
	 * @param id
	 * @return
	 */
	public List<HouseImageFile> getHouseImageList(int id);
    
}
