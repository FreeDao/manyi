package com.manyi.iw.soa.mapper;

import java.util.List;

import com.manyi.iw.soa.entity.hims.Area;
import com.manyi.iw.soa.entity.hims.Estate;
import com.manyi.iw.soa.house.model.AreaRequest;
import com.manyi.iw.soa.house.model.AreaResponse;

public interface AreaMapper {
   
    Area selectByPrimaryKey(Integer areaid);
    
    Estate selectByPrimaryKeyWhithEstate(Integer areaid);
    
    List<AreaResponse> getMinAreaList(AreaRequest area);
}