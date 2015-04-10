package com.manyi.iw.agent.console.agent.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.agent.console.agent.model.AgentSeekHouseRequest;
import com.manyi.iw.agent.console.base.controller.BaseController;
import com.manyi.iw.agent.console.base.model.Response;
import com.manyi.iw.agent.console.entity.Agent;


@Controller
@RequestMapping(value="/agent",produces={"application/json;charset=UTF-8"})
public class AgentContoller extends BaseController{
    
    /**
     * 功能描述: 返回经纪人未处理约看列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     wangbiao:   2014年6月22日      新建
     * </pre>
     *
     * @param userType
     * @param session
     * @return
     */
    @RequestMapping(value="/agentUnproc")
    @ResponseBody
    public String getAgentUnproc(Integer userType,HttpSession session){
        Agent agent = (Agent)session.getAttribute("agent");
        HashMap<String, String> map = new HashMap<String,String>();
        map.put("agentId", agent.getId()+"");
        map.put("userType",  userType+"");
        return postForObject("/agent/agentUnproc.do", map, String.class);
    }
    
    /**
     * 功能描述: 返回经纪人所有约看
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     wangbiao:   2014年6月22日      新建
     * </pre>
     *
     * @param ashRequest
     * @param session
     * @return
     */
    @RequestMapping(value="/agentAllSeekHouse")
    @ResponseBody
    public String getAgentAllSeekHouse(AgentSeekHouseRequest ashRequest, HttpSession session){
        Agent agent = (Agent)session.getAttribute("agent");
        ashRequest.setAgentId(agent.getId());
        return  postForObject("/agent/agentAllSeekHouse.do", ashRequest, String.class);
    }
    
    
    /**
     * 功能描述:经纪人登录
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月20日      新建
     * </pre>
     *
     * @param mobile
     * @param password
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @RequestMapping(value="/login")
    @ResponseBody
    public Response login(String mobile,String password,HttpSession session) throws JsonParseException, JsonMappingException, IOException{
        Map<String,String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", password);
        String json =  postForObject("/agent/login.do", map, String.class);
        int code = 0;
        String msg = null;
        if(json!=null){
            ObjectMapper mapper = new ObjectMapper();
            Agent agent = mapper.readValue(json, Agent.class);
            session.setAttribute("agent",agent);
            session.setAttribute("agentJsonString", json);
        }else{
            code = -1;
            msg = "手机号或者密码错误，请重试";
        }
        return new Response(code, msg);
    }
    
    /**
     * 功能描述:检测用户是否登录（用户退回到登录页面的时候，如果登录了，自动跳转到首页）
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月20日      新建
     * </pre>
     *
     * @param session
     * @return
     */
    @RequestMapping(value="/logined")
    @ResponseBody
    public Response logined(HttpSession session){
        int code = -1;
        if(session.getAttribute("agent")!=null){
            code = 0;
        }
        return new Response(code,null);
    }
    
    /**
     * 功能描述:从session中获取agent的信息
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月20日      新建
     * </pre>
     *
     * @param session
     * @return
     */
    @RequestMapping(value="/loginInfo")
    @ResponseBody
    public String loginInfo(HttpSession session){
        Object object = session.getAttribute("agentJsonString");
        if(object != null){
            return "agentUser="+(String)object;
        }else{
            return "agentUser=null;";
        }
    }
    
    /**
     * 功能描述:经纪人注销页面
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月21日      新建
     * </pre>
     *
     * @param session
     * @return
     */
    @RequestMapping(value="/logout")
    public String logout(HttpSession session){
        session.removeAttribute("agent");
        session.removeAttribute("agentJsonString");
        return "redirect:/login.jsp";
    }
    
    /**
     * 功能描述:跳转到经纪人详情页面
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月24日      新建
     * </pre>
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/detail")
    public String agentDetail(Model model,HttpSession session){
        model.addAttribute("agent",getAgent(session));
        return "/pages/agent/agentDetail";
    }
}
