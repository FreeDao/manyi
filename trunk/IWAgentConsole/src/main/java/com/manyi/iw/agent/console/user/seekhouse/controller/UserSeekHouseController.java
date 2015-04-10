package com.manyi.iw.agent.console.user.seekhouse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.agent.console.base.controller.BaseController;
import com.manyi.iw.agent.console.util.JsonBuilder;

@Controller
@RequestMapping(value = "/userSeekHouse", produces = { "application/json;charset=UTF-8" })
public class UserSeekHouseController extends BaseController {

    /**
     * 功能描述:待处理列表 预约中，改期中
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getWaitDealSeeHouseList")
    @ResponseBody
    public String getWaitDealSeeHouseList(HttpServletRequest request) {
        JsonBuilder jsonBuilder = JsonBuilder.start().put("customerId", request.getParameter("customerId"))
                .put("type", request.getParameter("type"));
        String response = postForObject("/userSeekHouse/getWaitDealSeeHouseList.do", jsonBuilder.getJson(),
                String.class);

        return response;
    }

    /**
     * 功能描述:房源取消列表
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getCancelListSeeHouseList")
    @ResponseBody
    public String getCancelListSeeHouseList(Long custemerId) {
        String response = postForObject("/userSeekHouse/getCancelListSeeHouseList.do", custemerId, String.class);
        return response;
    }

    /**
     * 功能描述:已有约看列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param houseId
     * @return
     */
    @RequestMapping(value = "/getWillAppointmentList")
    @ResponseBody
    public String getWillAppointmentList(Long houseId) {

        String response = postForObject("/userSeekHouse/getWillAppointmentList.do", houseId, String.class);

        return response;
    }

    /**
     * 功能描述:记录联系房东信息
     * 如果 type 是房子已出租（1）  或者 房子停止出租（2）  取消所有，待预约，约看待处理房源。 
     * TODO 同时调  callcenter 接口  
     * 如果type 是 3 或者 4 记录文本
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param houseId
     * @return
     */
    @RequestMapping(value = "/recordContact", method = RequestMethod.POST)
    @ResponseBody
    public String recordContact(String params, HttpSession session) {
        //        String memo = "";
        //        try {
        //            memo = URLEncoder.encode(request.getParameter("memo"), "utf-8");
        //        }
        //        catch (UnsupportedEncodingException e) {
        //            e.printStackTrace();
        //        }
        //        JsonBuilder jsonBuilder = JsonBuilder.start().put("id", request.getParameter("id"))
        //                .put("type", request.getParameter("type")).put("memo", memo);
        //params;
        //
        JsonBuilder jsonBuilder = JsonBuilder.start().put("params", params).put("agentId", getAgent(session).getId());
        String response = postForObject("/userSeekHouse/recordContact.do", jsonBuilder.getJson(), String.class);

        return response;
    }

    /**
     * 
     * 功能描述:取消某个约看
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelSeekHouse", method = RequestMethod.POST)
    @ResponseBody
    public String cancelkSeeHouse(HttpServletRequest request) {
        String id = request.getParameter("id");
        String response = postForObject("/userSeekHouse/cancelSeekHouse.do", id, String.class);

        return response;
    }

    /**
     * 
     * 功能描述:取消某个约看
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addSeekHouse", method = RequestMethod.POST)
    @ResponseBody
    public String addSeekHouse(HttpServletRequest request) {
        String params = request.getParameter("params");
        String response = postForObject("/userSeekHouse/addSeekHouse.do", params, String.class);
        return response;
    }

    /**
     * 
     * 功能描述: 保存推荐的房源
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建S
     * </pre>
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addRecommand", method = RequestMethod.POST)
    @ResponseBody
    public String addRecommand(HttpServletRequest request, HttpSession session) {

        JsonBuilder jsonBuilder = JsonBuilder.start().put("ids", request.getParameter("ids"))
                .put("customerId", request.getParameter("customerId")).put("agentId", getAgent(session).getId());
        String response = postForObject("/userSeekHouse/addRecommand.do", jsonBuilder.getJson(), String.class);
        return response;
    }

    /****
     * 
     * 经纪人发起约会，推送消息
     * TODO 调接口 发送 消息
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月19日      新建
     * </pre>
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/addApponitment")
    @ResponseBody
    public String addApponitmentSeekHouse(HttpServletRequest request, HttpSession session) {

        JsonBuilder jsonBuilder = JsonBuilder.start().put("params", request.getParameter("params"))
                .put("agentId", getAgent(session).getId());

        String response = postForObject("/userSeekHouse/addApponitment.do", jsonBuilder.getJson(), String.class);
        return response;
    }
}
