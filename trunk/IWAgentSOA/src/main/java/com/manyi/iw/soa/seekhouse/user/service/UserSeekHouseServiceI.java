package com.manyi.iw.soa.seekhouse.user.service;

import java.util.List;
import java.util.Map;

import com.manyi.iw.soa.seekhouse.model.SeeHouseVo;
import com.manyi.iw.soa.seekhouse.user.model.SeekhousePositionResponse;

/**
 * Function: 用户约看相关
 *
 * @author   yufei
 * @Date	 2014年6月17日		下午8:00:10
 *
 * @see 	  
 */

public interface UserSeekHouseServiceI {

    /**
     * 功能描述:根据用户id,返回用户待处理约看列表（包括待预约和已变更）
     * 
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月17日      新建
     * </pre>
     *
     * @param userId
     * @param type 推送来源 0 为用户推送，1是 经纪人添加
     * @return
     */
    public List<SeeHouseVo> getWaitDealSeeHouse(Long userId, Byte type);

    /**
     * 功能描述:
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月18日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    public List<SeeHouseVo> getCancelSeeHouse(Long userId);

    /**
     * 功能描述:根据id更新某个看房备注。
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param id
     * @param memo
     */
    public void updateSeekHouseMemo(Long id, String memo);

    /**
     * 功能描述:下架某个房源 从房源表里删除这个房源记录（如果appointment_id=0 直接删除,如果appointment_id!=0
     * {
     *   判断房源表的所属于约会包含房源数，如果1条就取消{
     *   取消这个约会，还要看这个约会的状态，如果没有签到，就调用取消流程，如果完成签到，就历史房源态为已下架9
     *   }这个约会。
     * }，
     * 如果房源属于某个约会并且这个约会只有一个房源，就把这个约会取消，
     *（如果这个约会是签到前,只从房源表删除，如果是签到后，把历史表中的房源信息状态改为已下架）
     * 
     * <pre>
     *      Modify Reason:(修改原因,不需覆盖，直接追加)
     *      yufei:   2014年6月23日      新建
     * </pre>
     *
     */
    public Boolean offSaleSeekHouseByhoustId(Long id);

    /***
     * 
     * 功能描述：彻底取消某个房源
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param id
     */
    public void deleteSeekHouseById(Long id);

    /**
     * 功能描述:经纪人发其一个约会
     * 保存 约会表一条记录，并批量跟新 关联月刊状态为 2 带确认 设置记录的appointment_id 
     * @TODO 调接口推送消息
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月20日      新建
     * </pre>
     *
     */
    public Boolean addAppointment(String params, Long agentId);

    /**
     *功能描述:更改房源历史表状态为 已看房5   修改状态和 备注字段 ，修改 房源表只是 状态字段 已看房5
     *已看房源数字段加1 ，把这个用户的状态该为老用户
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月22日      新建
     * </pre>
     *
     * @param id
     * @param seekFeeling
     * @param memo
     * @param customerId
     */
    public void finishSeekhouse(Long id, Long appointmentId, Byte seekFeeling, String memo, Long customerId);

    /**
     * 功能描述:更改房源历史表状态为房 未看房 6   修改状态和 备注字段 和未看房原因字段 ，修改 房源表只是 状态字段  未看房 6 
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param id
     * @param nosawReason
     * @param memo
     */
    public void nofinishSeekhouse(Long id, Byte nosawReason, String memo);

    /**
     *  功能描述:当房源为未看房时，后续更改，若为改期中，更改房源历史表 状态改为7，同时修改房源表状态为7 设置历史表的noseereason
     *  并把约会id为0，若为取消中8更改房源历史表 状态改为8，同时修改房源表状态为8  并把约会id为0
     *  同时更改房源表memo
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param id
     * @param state
     */
    public void afterNofinishSeekhouse(Long id, Byte state, Byte selectValue, String memo);

    /***
     * 
     * 功能描述:获取某个经纪人某个约会的所有约看
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月22日      新建
     * </pre>
     *
     * @param agentId
     * @param appointmentId
     * @return
     */
    public List<SeeHouseVo> getSeeHouseByAppointmentId(Long agentId, Long appointmentId);

    /***
     * 
     * 功能描述:获取某个经纪人某个约会的所有约看
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月22日      新建
     * </pre>
     *
     * @param agentId
     * @param appointmentId
     * @return
     */
    public List<SeeHouseVo> getSeeHouseHistoryByAppointmentId(Long agentId, Long appointmentId);
    
    
    /**
     * 功能描述:获取看房单中状态为预约中、该其中、取消中状态的数据的经纬度集合
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月24日      新建
     * </pre>
     *  map
     * @param userId
     * @param agentId
     * @return
     */
    public List<SeekhousePositionResponse> getSeekhousePositionList(Map<String,Object> map);

    /**
     * 功能描述:添加推荐
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param agentId
     * @param customerId
     * @param houseId
     * @return
     */
    public void addRecommand(String param);
}
