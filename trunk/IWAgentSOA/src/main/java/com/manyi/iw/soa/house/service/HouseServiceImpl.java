package com.manyi.iw.soa.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.house.model.AreaRequest;
import com.manyi.iw.soa.house.model.AreaResponse;
import com.manyi.iw.soa.house.model.HouseSearchRequest;
import com.manyi.iw.soa.house.model.HouseSearchResponse;
import com.manyi.iw.soa.mapper.AreaMapper;
import com.manyi.iw.soa.mapper.HouseMapper;

@Service("houseService")
@Transactional(readOnly=true)
public class HouseServiceImpl {
    @Autowired
    private AreaMapper areaMapper;
    
    @Autowired
    private HouseMapper houseMapper;
    
    /**
     * 功能描述:根据area的parentId获取area的集合
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月18日      新建
     * </pre>
     *
     * @param area
     * @return
     */
    public List<AreaResponse> getMinAreaList(AreaRequest area){
        return areaMapper.getMinAreaList(area);
    }
    
    
    /**
     * 功能描述:
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月18日      新建
     * </pre>
     *
     * @param house
     * @return
     */
    public PageResponse<HouseSearchResponse> getHouseList(HouseSearchRequest house){
        int total = houseMapper.getHouseListTotal(house);
        house.setTotal(total);
        return new PageResponse<HouseSearchResponse>(total,houseMapper.getHouseList(house));
    }
}
