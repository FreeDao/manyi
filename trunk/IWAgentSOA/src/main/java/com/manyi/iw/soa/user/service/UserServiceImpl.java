package com.manyi.iw.soa.user.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.manyi.iw.soa.appointment.service.AppointmentServiceI;
import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.entity.User;
import com.manyi.iw.soa.entity.UserCollection;
import com.manyi.iw.soa.entity.UserMemo;
import com.manyi.iw.soa.mapper.UserCollectionMapper;
import com.manyi.iw.soa.mapper.UserMapper;
import com.manyi.iw.soa.mapper.UserMemoMapper;
import com.manyi.iw.soa.mapper.UserStatisticsMapper;
import com.manyi.iw.soa.user.controller.UserController;
import com.manyi.iw.soa.user.model.UserCollectionRequest;
import com.manyi.iw.soa.user.model.UserEditBizStatusModel;
import com.manyi.iw.soa.user.model.UserMemoRequest;
import com.manyi.iw.soa.user.model.UserMemoResponse;
import com.manyi.iw.soa.user.model.UserSearchRequest;
import com.manyi.iw.soa.user.model.UserSearchResponse;

@Service("userService")
@Transactional(readOnly=true)
public class UserServiceImpl {
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserCollectionMapper userCollectionMapper;
    
    @Autowired
    private UserMemoMapper userMemoMapper;
    
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    
    @Autowired
    private AppointmentServiceI appointmentService;
    
    /**
     * 功能描述:用户查询列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月18日      新建
     * </pre>
     *
     * @param user
     * @return
     */
    public List<UserSearchResponse> getUserList(UserSearchRequest user){
        Integer lastLoginTimeSearch = user.getLastLoginTimeSearch();
        if(lastLoginTimeSearch!= null && lastLoginTimeSearch!=0){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
            
            switch (lastLoginTimeSearch) {
                case 1:
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-7);
                    user.setMinLastLoginTime(calendar.getTime());
                    break;
                case 2:
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-7);
                    user.setMaxLastLoginTime(calendar.getTime());
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-7);
                    user.setMinLastLoginTime(calendar.getTime());
                    break;
                case 3:
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-14);
                    user.setMaxLastLoginTime(calendar.getTime());
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-16);
                    user.setMinLastLoginTime(calendar.getTime());
                    break;
                case 4:
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-30);
                    user.setMaxLastLoginTime(calendar.getTime());
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-30);
                    user.setMinLastLoginTime(calendar.getTime());
                    break;
                case 5:
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-60);
                    user.setMaxLastLoginTime(calendar.getTime());
                    break;
            }
        }
        
        int total = userMapper.getUserListTotal(user);
        user.setTotal(total);
        return userMapper.getUserList(user);
    }
    
    /**
     * 功能描述:添加房源收藏
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userCollection
     * @return
     */
    @Transactional(readOnly=false)
    public boolean addUserCollection(UserCollection userCollection){
        return userCollectionMapper.insert(userCollection)!=0;
    }
    
    /**
     * 功能描述:根据搜藏id删除指定房源搜藏
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param id
     * @return
     */
    @Transactional(readOnly=false)
    public boolean delUserCollection(Long id){
        return userCollectionMapper.deleteByPrimaryKey(id)!=0;
    }
    
    /**
     * 功能描述:获取用户的房源搜藏列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @return
     */
    public List<UserCollection> getUserCollectionList(Long userId){
        return userCollectionMapper.getUserCollectionList(userId);
    }
    
    /**
     * 功能描述:获取用户详情
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    public User getUserDetail(Long userId){
       return  userMapper.selectByPrimaryKey(userId);
    }
    
    /**
     * 功能描述:获取用户备注列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userMemo
     * @return
     */
    public PageResponse<UserMemoResponse> getUserMemoList(UserMemoRequest userMemo){
        int total = userMemoMapper.getUserMemoListTotal(userMemo.getUserId());
        userMemo.setTotal(total);
        return new PageResponse<UserMemoResponse>(total,userMemoMapper.getUserMemoList(userMemo));
    }
    
    /**
     * 功能描述:添加用户备注
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userMemo
     * @return
     */
    @Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
    public boolean addUserMemo(UserMemo userMemo){
        boolean result = userMemoMapper.insert(userMemo)!=0;
        return result;
    }
    
    /**
     * 功能描述:修改用户的业务状态
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param model
     * @return
     */
    @Transactional(readOnly=false)
    public boolean editBizStatus(UserEditBizStatusModel model){
        boolean result = userMapper.editBizStatus(model)!=0;
        appointmentService.cancelAppointmentByCustomer(model.getUserId());
        return result;
    }
    
    /**
     * 功能描述:修改推荐房源数，自身加1
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly=false)
    public boolean updateRecommendNumStatistics(Long userId){
        return userStatisticsMapper.updateStatisticsField("recommend_num", userId)!=0;
    }
    
    /**
     * 功能描述:修改已看房源数，自身加1
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly=false)
    public boolean updateSawNumStatistics(Long userId){
        return userStatisticsMapper.updateStatisticsField("saw_num", userId)!=0;
    }
    
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
    @Transactional(readOnly=false)
    public boolean updateUserType(Long userId){
        return userMapper.updateUserType(userId)!=0;
    }
    
}
