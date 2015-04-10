package com.manyi.iw.soa.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.house.model.AreaRequest;
import com.manyi.iw.soa.house.model.AreaResponse;
import com.manyi.iw.soa.house.model.HouseSearchRequest;
import com.manyi.iw.soa.house.model.HouseSearchResponse;
import com.manyi.iw.soa.house.service.HouseServiceImpl;

@RequestMapping(value="/house")
@Controller
public class HouseController {
    //public PageResponse<T>
    @Autowired
    private HouseServiceImpl houseService;
    
    
    
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
    @RequestMapping("/area/list")
    @ResponseBody
    public List<AreaResponse> getMinAreaList(@RequestBody AreaRequest area){
        List<AreaResponse> list = houseService.getMinAreaList(area);
        AreaResponse areaResponse = new AreaResponse();
        areaResponse.setId(0);
        areaResponse.setText("所有");
        list.add(0,areaResponse);
        return list;
    }
    
    /**
     * 功能描述:获取房源列表(包含分页、检索)
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月18日      新建
     * </pre>
     *
     * @param house
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public PageResponse<HouseSearchResponse> getHouseList(@RequestBody HouseSearchRequest house){
        return houseService.getHouseList(house);
    }
}
