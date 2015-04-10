package com.manyi.iw.soa.mapper;

import org.apache.ibatis.annotations.Param;

import com.manyi.iw.soa.entity.UserStatistics;

public interface UserStatisticsMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     */
    int insert(UserStatistics record);

    /**
     *
     */
    int insertSelective(UserStatistics record);

    /**
     *
     */
    UserStatistics selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(UserStatistics record);

    /**
     *
     */
    int updateByPrimaryKeyWithBLOBs(UserStatistics record);

    /**
     *
     */
    int updateByPrimaryKey(UserStatistics record);
    
    int updateStatisticsField(@Param("field") String field,@Param("userId")Long userId);
    
    /**
     * 功能描述:修改用户统计表的推荐房源的值（指定在原数据上相加count（多少））
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param userId
     * @param count
     * @return
     */
    int updateRecommendNum(@Param("userId") Integer userId,@Param("count") Integer count);
}