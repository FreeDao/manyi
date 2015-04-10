package com.manyi.iw.soa.mapper;

import java.util.List;

import com.manyi.iw.soa.entity.UserMemo;
import com.manyi.iw.soa.user.model.UserMemoRequest;
import com.manyi.iw.soa.user.model.UserMemoResponse;


public interface UserMemoMapper {
 
    int deleteByPrimaryKey(Long id);
    
    int insert(UserMemo record);
    

    /**
     *
     */
    int insertSelective(UserMemo record);

    /**
     *
     */
    UserMemo selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(UserMemo record);

    /**
     *
     */
    int updateByPrimaryKeyWithBLOBs(UserMemo record);

    /**
     *
     */
    int updateByPrimaryKey(UserMemo record);
    
    int getUserMemoListTotal(Long userId);
    
    List<UserMemoResponse> getUserMemoList(UserMemoRequest userMemo);
}