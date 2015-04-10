package com.manyi.iw.soa.mapper;

import java.util.List;

import com.manyi.iw.soa.entity.UserCollection;

public interface UserCollectionMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     */
    int insert(UserCollection record);

    /**
     *
     */
    int insertSelective(UserCollection record);

    /**
     *
     */
    UserCollection selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(UserCollection record);

    /**
     *
     */
    int updateByPrimaryKeyWithBLOBs(UserCollection record);

    /**
     *
     */
    int updateByPrimaryKey(UserCollection record);
    
    /**
     * 功能描述:获取用户的房源搜藏列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    List<UserCollection> getUserCollectionList(Long userId);
    
}