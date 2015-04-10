package com.manyi.iw.soa.mapper;

import java.util.List;

import com.manyi.iw.soa.entity.User;
import com.manyi.iw.soa.user.model.UserEditBizStatusModel;
import com.manyi.iw.soa.user.model.UserSearchRequest;
import com.manyi.iw.soa.user.model.UserSearchResponse;

public interface UserMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     */
    int insert(User record);

    /**
     *
     */
    int insertSelective(User record);

    /**
     *
     */
    User selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(User record);

    /**
     *
     */
    int updateByPrimaryKeyWithBLOBs(User record);

    /**
     *
     */
    int updateByPrimaryKey(User record);
    
    List<UserSearchResponse> getUserList(UserSearchRequest user);
    
    int getUserListTotal(UserSearchRequest user);
    
    /**
     * 功能描述:修改用户的业务状态
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param model
     * @return
     */
    int editBizStatus(UserEditBizStatusModel model);
    
    /**
     * 功能描述:将用户类型从新用户改为老用户（前提是该用户已经看房成功）
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    int updateUserType(Long userId);
    
}