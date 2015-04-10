package com.manyi.iw.agent.console.user.recommend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.agent.console.base.controller.BaseController;
import com.manyi.iw.agent.console.base.model.Response;
import com.manyi.iw.agent.console.user.recommend.model.RecommendListRequest;
import com.manyi.iw.agent.console.util.StringUtils;

@Controller
@RequestMapping(value="/user/recommend",produces={"application/json;charset=UTF-8"})
public class RecommendController extends BaseController {
    
    
    /**
     * 功能描述:添加推荐房源（可批量）
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param userId
     * @param houseIds
     * @param session
     * @return
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public String addRecommend(Long userId,Long[] houseIds,HttpSession session){
        String error = "{errorCode:-1,message:'操作失败，请重试'}";
        if(houseIds.length == 0)return error;
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("houseIds",houseIds);
        map.put("agentId",getAgent(session).getId());
        return postForObject("/user/recommend/add.do",map,String.class);
    }
    
    /**
     * 功能描述:转发到recommend.jsp页面
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping("/recommend")
    public String recommend(Long userId,Model model){
        model.addAttribute("userId",userId);
        return "/pages/user/recommend";
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
     * @param session
     * @return
     */
    @RequestMapping(value="/list")
    @ResponseBody
    public String getRecommendList(RecommendListRequest recommendList,HttpSession session){
        recommendList.setAgentId(getAgent(session).getId());
        return postForObject("/user/recommend/list.do", recommendList,String.class);
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
     * @param session
     * @return
     */
    @RequestMapping(value="/seekhouse/list")
    @ResponseBody
    public String getRecommendSeekhouseList(RecommendListRequest recommendList,HttpSession session){
        recommendList.setAgentId(getAgent(session).getId());
        return postForObject("/user/recommend/seekhouse/list.do", recommendList,String.class);
    }
    
    
}
