package com.manyi.iw.soa.agent.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.soa.agent.model.AgentSeekHouseModel;
import com.manyi.iw.soa.agent.model.AgentSeekHouseRequest;
import com.manyi.iw.soa.agent.model.AgentUnprocModel;
import com.manyi.iw.soa.agent.service.AgentServiceI;
import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.entity.Agent;


@Controller
@RequestMapping("/agent")
public class AgentController
{
    @Autowired
    private AgentServiceI agentService;
    
    /**
     * 功能描述: 获取经纪人待处理的约看的列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     wangbiao:   2014年6月25日      新建
     * </pre>
     *
     * @param map
     * @return
     */
    @RequestMapping(value="/agentUnproc")
    @ResponseBody
    public PageResponse<AgentUnprocModel> getAgentUnproc(@RequestBody Map<String,Object> map){
        List<AgentUnprocModel> agentUnprocList = agentService.getAgentUnproc(map);
        PageResponse<AgentUnprocModel> response = new PageResponse<AgentUnprocModel>();
        response.setData(agentUnprocList);
        return response;
    }
    
    /**
     * 功能描述:获取所有约看list
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     wangbiao:   2014年6月21日      新建
     * </pre>
     *
     * @param agentSeekHouseRequest
     * @return
     */
    @RequestMapping(value="/agentAllSeekHouse")
    @ResponseBody
    public PageResponse<AgentSeekHouseModel> getAgentAllSeekHouse(@RequestBody AgentSeekHouseRequest agentSeekHouseRequest){
        PageResponse<AgentSeekHouseModel> result = agentService.getAgentSeekHouseList(agentSeekHouseRequest);
        return result;
    }
    
    /**
     * 功能描述:经纪人登录
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月20日      新建
     * </pre>
     *
     * @param map
     * @return
     */
    @RequestMapping(value="/login")
    @ResponseBody
    public Agent login(@RequestBody Map<String,String> map){
        Agent agent = agentService.login(map.get("mobile"),map.get("password"));
        return agent;
    }
}
