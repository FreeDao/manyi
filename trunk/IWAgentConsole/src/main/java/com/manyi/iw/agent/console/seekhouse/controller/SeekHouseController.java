package com.manyi.iw.agent.console.seekhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.agent.console.base.controller.BaseController;

@Controller
@RequestMapping(value = "/seekHouse", produces = { "application/json;charset=UTF-8" })
public class SeekHouseController extends BaseController {

    @RequestMapping(value = "/{userId}/getWaitDealSeeHouseList")
    @ResponseBody
    public String getWaitDealSeeHouseList(@PathVariable Long userId) {
        String response = postForObject("/seekHouse/getWaitDealSeeHouseList.do", userId ,String.class);
        return response;
    }

    @RequestMapping(value = "/{userId}/getCancelListSeeHouseList")
    @ResponseBody
    public String getCancelListSeeHouseList(@PathVariable Long userId) {

        String response = postForObject("/seekHouse/getCancelListSeeHouseList.do", userId,String.class);
        return response;
    }

    //  @RequestMapping(value="/{userId}/getWaitDealList.do")
    @RequestMapping(value = "/{houseId}/getWillAppointmentList")
    @ResponseBody
    public String getWillAppointmentList(@PathVariable Long houseId) {
        String response = postForObject("/seekHouse/getWillAppointmentList.do", houseId,String.class);
        return response;
    }
    
    /**
     * 功能描述:获取看房单中状态为预约中、该其中、取消中状态的数据的经纬度集合
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月24日      新建
     * </pre>
     *
     * @param userId
     * @param session
     * @return
     */
    @RequestMapping(value="/position/list")
    @ResponseBody
    public String getSeekhousePosition(Long userId,HttpSession session){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("agentId", getAgent(session).getId());
        return postForObject("/userSeekHouse/position/list.do", map, String.class);
    }
    
    
    /**
     * 功能描述:跳转到题图页面
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月24日      新建
     * </pre>
     *
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping("/map/view")
    public String mapView(Long userId,Model model){
        model.addAttribute("userId",userId);
        return "/pages/user/mapView";
    }
}