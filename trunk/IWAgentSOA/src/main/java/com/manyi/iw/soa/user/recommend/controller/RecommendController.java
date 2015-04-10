package com.manyi.iw.soa.user.recommend.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.soa.base.controller.BaseController;
import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.common.model.Response;
import com.manyi.iw.soa.user.recommend.model.RecommendListRequest;
import com.manyi.iw.soa.user.recommend.model.RecommendListResponse;
import com.manyi.iw.soa.user.recommend.model.RecommendSeekhouseListResponse;
import com.manyi.iw.soa.user.recommend.service.RecommendServiceImpl;

@Controller
@RequestMapping("/user/recommend")
public class RecommendController extends BaseController {
    @Autowired
    private RecommendServiceImpl recommendService;
    
    /**
     * 功能描述:添加推荐房源（可批量）
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param map
     * @return
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public Response addRecommend(@RequestBody Map<String, Object>map){
        int code = 0;
        String msg = null;
        int addCount =((ArrayList)map.get("houseIds")).size();
        
        int successCount = recommendService.addRecommend(map); 
        
        if(successCount==0){
            code = -1;
            msg = "添加失败，或许您推荐的房子已存在看房单中";
        }else{
            if(successCount != addCount){
                msg = "您推荐的"+addCount+"套房子中有"+(addCount-successCount)+"套已存在看房单中，剩余"+successCount+"套房子推荐成功";
            }
        }
        return new Response(code, msg);
    }
    
    
    /**
     * 功能描述:获取推荐房源列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param recommendList
     * @return
     */
    @RequestMapping(value="/list")
    @ResponseBody
    public PageResponse<RecommendListResponse> getRecommendList(@RequestBody RecommendListRequest recommendList){
        return recommendService.getRecommendList(recommendList);
    }
    
    
    /**
     * 功能描述:获取看房单列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param recommendList
     * @return
     */
    @RequestMapping(value="/seekhouse/list")
    @ResponseBody
    public PageResponse<RecommendSeekhouseListResponse> getRecommendSeekhouseList(@RequestBody RecommendListRequest recommendList){
        return recommendService.getRecommendSeekhouseList(recommendList);
    }
}
