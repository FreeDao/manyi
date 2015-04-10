package com.manyi.iw.soa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manyi.iw.soa.entity.SeekhouseHistory;
import com.manyi.iw.soa.seekhouse.model.SeeHouseVo;

public interface SeekhouseHistoryMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     */
    int insert(SeekhouseHistory record);

    /**
     *
     */
    int insertSelective(SeekhouseHistory record);

    /**
     *
     */
    SeekhouseHistory selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(SeekhouseHistory record);

    /**
     *
     */
    int updateByPrimaryKey(SeekhouseHistory record);

    /***
     * 
     * 功能描述:批量添加约会历史表记录
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月20日      新建
     * </pre>
     *
     * @param SeekhouseHistories
     */
    public void saveSeekhouseHistories(List<SeekhouseHistory> seekhouseHistories);

    /**
     * 功能描述:更新约看历史表状态和备注
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param id
     */
    public void updateHouseHistoryStateById(@Param("id") Long id, @Param("state") Byte state,
            @Param("nosawReason") Byte value, @Param("memo") String memo);

    /**
     * 功能描述:通过约看历史表id 得到对对应约看id
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月21日      新建
     * </pre>
     *
     * @param id
     * @return
     */
    public Long getseekhouseIdByKey(@Param("id") Long id);

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
    public List<SeeHouseVo> getSeeHouseHistoryByAppointmentId(@Param("agentId") Long agentId,
            @Param("appointmentId") Long appointmentId);

    /**
     * 功能描述:更新某个房源的历史表状态为已下架
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param state
     * @param houseId
     */
    public void updateHouseHistoryStateByHouseId(@Param("state") Byte state, @Param("houseId") Long houseId);

    /**
     * 
     * 更新某个约会所有历史表状态为已取消
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月24日      新建
     * </pre>
     *
     * @param state
     * @param customerId
     */
    public void updateHouseHistoryStateByappointmentId(@Param("state") Byte state,
            @Param("appointmentId") Long appointmentId);
}