package com.manyi.iw.agent.console.house.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.agent.console.base.controller.BaseController;
import com.manyi.iw.agent.console.house.model.AreaRequest;
import com.manyi.iw.agent.console.house.model.HouseSearchRequest;

@RequestMapping(value="/house",produces={"application/json;charset=UTF-8"})
@Controller
public class HouseController extends BaseController{
    
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
    public String getMinAreaList(AreaRequest area){
       return postForObject("/house/area/list.do", area,String.class);
    }
    
    /**
     * 功能描述:获取房源列表（包含分页、检索）
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
    public String getHouseList(HouseSearchRequest house){
        return postForObject("/house/list.do", house,String.class);
    }
}
