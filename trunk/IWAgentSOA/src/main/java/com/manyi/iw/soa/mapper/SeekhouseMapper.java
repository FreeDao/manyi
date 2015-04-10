package com.manyi.iw.soa.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.manyi.iw.soa.entity.Seekhouse;
import com.manyi.iw.soa.seekhouse.model.SeeHouseVo;
import com.manyi.iw.soa.seekhouse.user.model.SeekhousePositionResponse;

public interface SeekhouseMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     */
    int insert(Seekhouse record);

    /**
     *
     */
    int insertSelective(Seekhouse record);

    /**
     *
     */
    Seekhouse selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(Seekhouse record);

    /**
     *
     */
    int updateByPrimaryKey(Seekhouse record);

    /***
     * 
     * 功能描述:获取待处理房源列表包括 待处理和变变更中
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月17日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    public List<SeeHouseVo> getWaitDealSeeHouse(@Param("userId") Long userId, @Param("type") Byte type);

    /**
     * 功能描述:获取取消中的房源列表
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
    public void updateSeekHouseMemo(@Param("id") Long id, @Param("memo") String memo);

    /***
     * 房源下架，删除所有该房源约看
     * 功能描述:TODO(描述这个方法的作用)
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param houseId
     */
    public void deletebyhoustId(@Param("id") Long houseId);

    /**
     * 批量插入约看表 ，默认appoinmnet_id =0
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月23日      新建
     * </pre>
     *
     * @param SeekHouses
     */
    public void addSeekHouses(List<Seekhouse> SeekHouses);

    /**
     * 功能描述:根据appointmentId 修改约会对应的 约看状态  注意为了改期和取消约会用，这时会 把约看的 appointment_id设置为0
     * 
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月20日      新建
     * </pre>
     *
     * @param state
     * @param appointmentId
     */
    public void updateStateByAppointMentId(@Param("state") Byte state, @Param("appointmentId") Long appointmentId);

    /**
     * 功能描述:根据约会id 获取对应约看
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月20日      新建
     * </pre>
     *
     */
    public List<Seekhouse> getSeekhouseByAppointMentId(@Param("id") Long id);

    /**
     *把制定一批house_id 的状态该为制定的状态，并 与某个约会关联。
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param agentId
     * @param customerId
     * @param state
     * @param appointmentId
     * @param list
     */
    public void updateHousesStateByAppointMentId(@Param("agentId") Long agentId, @Param("customerId") Long customerId,
            @Param("state") Byte state, @Param("appointmentId") Long appointmentId,
            @Param("list") ArrayList<String> list);

    /***
     * 功能描述:根据appointmentId 修改约会对应的 约看状态  注意为了改期和取消约会用
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月20日      新建
     * </pre>
     *
     * @param state
     * @param appointmentId
     * @param list
     */
    public void updateStateByAppointMentIdOnly(@Param("state") Byte state, @Param("appointmentId") Long appointmentId);

    /**
     * 功能描述:用户处理单个房源时用，更新房源表状态已备注
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param state
     * @param id
     * @param memo
     */
    public void updateHousesStateById(@Param("state") Byte state, @Param("id") Long id, @Param("memo") String memo);

    /**
     * 功能描述:用户处理单个房源时用，更新房源表状态已备注 同时会把apponitment 赋值为0
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param state
     * @param id
     * @param memo
     */
    public void updateHousesStateZeroById(@Param("state") Byte state, @Param("id") Long id, @Param("memo") String memo);

    /**
     * 功能描述:更改房源表记录的的状态和appointmentId
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param id
     * @param state
     * @param appointmentId
     */
    public void updateHousesStateAppointmentIdById(@Param("id") Long id, @Param("state") Byte state,
            @Param("appointmentId") Byte appointmentId);

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
    public List<SeeHouseVo> getSeeHouseByAppointmentId(@Param("agentId") Long agentId,
            @Param("appointmentId") Long appointmentId);

    /**
     * 功能描述:获取看房单中状态为预约中、该其中、取消中状态的数据的经纬度集合
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月24日      新建
     * </pre>
     *
     * @param userId
     * @param agentId
     * @return
     */
    List<SeekhousePositionResponse> getSeekhousePositionList(Map<String, Object> map);

    /***
     * 
     * 从房源表中删除所有改用户非看房单记录
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param customerId
     */
    public void deleteSeekhouseNoKFDByCustomerId(@Param("customerId") Long customerId);

    /**
     * 获取推荐的房源 没看过的房子
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param houseId
     * @return
     */
    public List<Seekhouse> getSeekhouseRemcommand(@Param("houseId") Long houseId);

    /**
     * 功能描述:删除不是推荐的房源 没看过的房子
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param houseId
     */
    public void deleteSeekhouseNoRemcommand(@Param("houseId") Long houseId);

    /**
     * 功能描述:根据 紧急人，用户和houseId 查询条记录唯一性
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
    public List<Long> getSeeHouseByUnqiune(@Param("agentId") Long agentId, @Param("customerId") Long customerId,
            @Param("houseId") Long houseId);

    /**
     * 功能描述:找到所有只包含一套 对应 房源id的约会id
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月26日      新建
     * </pre>
     *
     * @param houseId
     * @return
     */
    public List<Long> getWillCancelAppointmentIds(@Param("houseId") Long houseId);

}