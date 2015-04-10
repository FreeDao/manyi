package com.manyi.iw.soa.mapper;

import java.util.List;

import com.manyi.iw.soa.entity.hims.House;
import com.manyi.iw.soa.entity.hims.Residence;
import com.manyi.iw.soa.house.model.HouseSearchRequest;
import com.manyi.iw.soa.house.model.HouseSearchResponse;

public interface HouseMapper {
   
    House selectByPrimaryKey(Integer houseid);

    Residence selectByPrimaryKeyWithResidence(Integer houseid);
    
    int getHouseListTotal(HouseSearchRequest house);
    
    List<HouseSearchResponse>  getHouseList (HouseSearchRequest house);
}