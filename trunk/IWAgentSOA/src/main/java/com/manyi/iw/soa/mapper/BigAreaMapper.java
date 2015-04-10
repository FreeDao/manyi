package com.manyi.iw.soa.mapper;

import com.manyi.iw.soa.entity.BigArea;

public interface BigAreaMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     */
    int insert(BigArea record);

    /**
     *
     */
    int insertSelective(BigArea record);

    /**
     *
     */
    BigArea selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(BigArea record);

    /**
     *
     */
    int updateByPrimaryKey(BigArea record);
}