package com.manyi.ihouse.user.service;

import javax.servlet.http.HttpSession;

import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.user.model.AppointmentDetailRequest;
import com.manyi.ihouse.user.model.AppointmentHouseRequest;
import com.manyi.ihouse.user.model.AppointmentOperateRequest;
import com.manyi.ihouse.user.model.AutoLoginRequest;
import com.manyi.ihouse.user.model.BindFeedbackRequest;
import com.manyi.ihouse.user.model.CollectionRequest;
import com.manyi.ihouse.user.model.CommentRequest;
import com.manyi.ihouse.user.model.DeleteCollectionRequest;
import com.manyi.ihouse.user.model.DeleteSeekHouseRequest;
import com.manyi.ihouse.user.model.FeedbackRequest;
import com.manyi.ihouse.user.model.HaveSeePageRequest;
import com.manyi.ihouse.user.model.HaveSeePageResponse;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.user.model.IsHaveTripResponse;
import com.manyi.ihouse.user.model.MyAgentResponse;
import com.manyi.ihouse.user.model.MyRequest;
import com.manyi.ihouse.user.model.PageListRequest;
import com.manyi.ihouse.user.model.RreshDataResponse;
import com.manyi.ihouse.user.model.SeeDetailResponse;
import com.manyi.ihouse.user.model.SeeHouseSubmitResponse;
import com.manyi.ihouse.user.model.SeekHouseRequest;
import com.manyi.ihouse.user.model.SeekHouseResponse;
import com.manyi.ihouse.user.model.ToBeSeeDetailResponse;
import com.manyi.ihouse.user.model.ToBeSeePageRequest;
import com.manyi.ihouse.user.model.ToBeSeePageResponse;
import com.manyi.ihouse.user.model.UpdateCollectionRequest;
import com.manyi.ihouse.user.model.UpdateInfoRequest;
import com.manyi.ihouse.user.model.UpdateInfoResponse;
import com.manyi.ihouse.user.model.UserLoginRequest;
import com.manyi.ihouse.user.model.UserLoginResponse;
import com.manyi.ihouse.user.model.UserMyResponse;

public interface UserService {
	
	/**
	 * 发送验证码
	 * @return
	 */
	public Response sendVerifyCode(HttpSession session,UserLoginRequest request);
	
	/**
	 * 登陆验证
	 * @param request
	 * @return
	 */
	public UserLoginResponse login(HttpSession session,UserLoginRequest request);

	/**
	 * 获取“我的”信息
	 * @param request
	 * @return
	 */
	public UserMyResponse my(MyRequest request);

	/**
	 * 获取"看房单"页面数据
	 * @param request
	 * @return
	 */
	public SeekHouseResponse seekHouse(SeekHouseRequest request);

	/**
	 * 将看房单页中选中的房源提交约看
	 * @param request
	 * @return
	 */
	public Response appointmentHouse(AppointmentHouseRequest request);

	/**
	 * 删除看房单中的房源
	 * @param request
	 * @return
	 */
	public Response deleteSeekHouse(DeleteSeekHouseRequest request);

    /**
     * 
     * 功能描述:收藏房源
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hu-bin:   2014年6月19日      新建
     * </pre>
     *
     * @param request
     * @return
     */
    public Response collect(CollectionRequest request);

	/**
	 * 我的收藏
	 * @param request
	 * @return
	 */
	public PageResponse<HouseBaseModel> myCollection(HttpSession session, PageListRequest request);
	
	/**
	 * 删除我的收藏中的房源
	 * @param request
	 * @return
	 */
	public Response deleteCollection(DeleteCollectionRequest request);

    /**
     * 
     * 功能描述:同步收藏中的房源
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hu-bin:   2014年6月24日      新建
     * </pre>
     *
     * @param request
     * @return
     */
    public Response updateCollection(UpdateCollectionRequest request);

	/**
	 * 行程-待看的列表
	 * @param request
	 * @return
	 */
	public ToBeSeePageResponse toBeSee(ToBeSeePageRequest request);

	/**
	 * 确认看房
	 * @param request
	 * @return
	 */
	public SeeHouseSubmitResponse seeHouseSubmit(AppointmentOperateRequest request);

	/**
	 * 已确认待看房-详情页
	 * @param request
	 * @return
	 */
	public ToBeSeeDetailResponse toBeSeeDetail(AppointmentDetailRequest request);

	/**
	 * 申请改期
	 * @param request
	 * @return
	 */
	public Response changeAppointDate(AppointmentOperateRequest request);

	/**
	 * 取消看房
	 * @param request
	 * @return
	 */
	public Response cancelAppoint(AppointmentOperateRequest request);

	/**
	 * 行程-已看的列表
	 * @param request
	 * @return
	 */
	public HaveSeePageResponse haveSee(HaveSeePageRequest request);

	/**
	 * 评价提交
	 * @param request
	 * @return
	 */
	public Response comment(CommentRequest request);

	/**
	 * 已看的已评价-详情页
	 * @param request
	 * @return
	 */
	public SeeDetailResponse commentDetail(AppointmentDetailRequest request);

	/**
	 * 已取消-详情页
	 * @param request
	 * @return
	 */
	public SeeDetailResponse cancelDetail(AppointmentDetailRequest request);

	/**
	 * 意见反馈
	 * @param request
	 * @return
	 */
	public Response feedback(FeedbackRequest request);

	/**
	 * 登陆后将登陆前提交的反馈信息绑定到该账号
	 * @param session
	 * @param request
	 * @return
	 */
	public Response bindFeedback(HttpSession session, BindFeedbackRequest request);
	
	/**
	 * 自动登陆
	 * @param session
	 * @param request
	 * @return
	 */
	public UserLoginResponse autoLogin(HttpSession session, AutoLoginRequest request);

    public MyAgentResponse myAgent(MyRequest request);

    /**
     * 
     * 功能描述:获取最新全局数据
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月16日      新建
     * </pre>
     *
     * @param request
     * @return
     */
    public RreshDataResponse freshData(MyRequest request);

    
    /**
     * 
     * 功能描述:用户是否有行程
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月16日      新建
     * </pre>
     *
     * @param request
     * @return
     */
    public IsHaveTripResponse isHaveTrip(MyRequest request);

    /**
     * 
     * 功能描述:手机应用登出操作
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月17日      新建
     * </pre>
     *
     * @param session
     * @param request
     * @return
     */
    public Response userLogout(HttpSession session, MyRequest request);

    /**
     * 
     * 功能描述:获取更新信息
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月17日      新建
     * </pre>
     *
     * @return
     */
    public UpdateInfoResponse updateInfo(UpdateInfoRequest request);

}
