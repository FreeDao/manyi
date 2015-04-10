package com.manyi.iw.agent.console.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.manyi.iw.agent.console.base.controller.BaseController;
import com.manyi.iw.agent.console.base.model.Response;
import com.manyi.iw.agent.console.entity.Agent;
import com.manyi.iw.agent.console.entity.User;
import com.manyi.iw.agent.console.entity.UserMemo;
import com.manyi.iw.agent.console.user.model.UserEditBizStatusModel;
import com.manyi.iw.agent.console.user.model.UserMemoRequest;
import com.manyi.iw.agent.console.user.model.UserSearchRequest;

@Controller
@RequestMapping(value="/user",produces={"application/json;charset=UTF-8"})
public class UserController extends BaseController{
    public Map<Byte,String> userBizStatus = new HashMap<>();
    
    
    
    public UserController() {
        super();
        userBizStatus.put((byte)0, "找房中");
        userBizStatus.put((byte)1, "已租房");
        userBizStatus.put((byte)2, "不需要租房");
    }

    /**
     * 功能描述:获取用户列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月17日      新建
     * </pre>
     *
     * @param user
     * @return
     */
    @RequestMapping(value="/list")
    @ResponseBody
    public String getUserList(UserSearchRequest user){
        String result = postForObject("/user/list.do",user, String.class);
        return result;
    }
    
    
    
    /**
     * 功能描述:获取用户详情，并且跳转到pages/detailHead.jsp
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping("/detail")
    public String getUserDetail(Long userId,Model model){
        Map<String, Object> map = new HashMap<>();
        map.put("id",userId);
        User user = new User();
        user.setId(userId);
        String json = postForObject("/user/detail.do",userId,String.class);
        
        
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            model.addAttribute("user",objectMapper.readValue(json,User.class));
            model.addAttribute("userBizStatus", userBizStatus);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "pages/detailHead";
    }
    
    /**
     * 功能描述:获取用户备注列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userMemo
     * @return
     */
    @RequestMapping(value="/memo/list")
    @ResponseBody
    public String getUserMemoList(UserMemoRequest userMemo){
        String json = postForObject("/user/memo/list.do", userMemo, String.class);
        return json;
    }
    
    /**
      *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userMemo
     * @return
     */
    @RequestMapping(value="/memo/add")
    @ResponseBody
    public String addUserMemo(UserMemo userMemo,HttpSession session){
        Agent agent = getAgent(session);
        userMemo.setAgentId(agent.getId());
        return postForObject("/user/memo/add.do", userMemo, String.class);
    }
    
    /**
     * 功能描述:编辑用户状态
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param model
     * @return
     */
    @RequestMapping(value="editBizStatus")
    @ResponseBody
    public String editBizStatus(UserEditBizStatusModel model){
        return postForObject("/user/editBizStatus.do", model, String.class);
    }
    
    @RequestMapping("/appointment/process")
    public String userProcessSeekHouse(Long customerId,Model model){
        model.addAttribute("customerId", customerId);
        return "/pages/user/userProcessSeekHouse";
    }
    
    
    
    public static void main(String[] args) {
//        UserSearchRequest user = new UserSearchRequest();
//        user.setBizStatus((byte)1);
//        user.setPageIndex(44);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(Inclusion.NON_NULL);  
////        System.out.println(mapper.convertValue(user, Map.class));
//        
//        RestTemplate restTemplate = new RestTemplate();
//        
//        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
//        
////        ArrayList list = new ArrayList();
////        list.add(new FormHttpMessageConverter());
//        Long userId = 1l;
//        HashMap<String,String> map = new HashMap<String,String>();
//        
//        map.put("userId", "1");
//        map.put("age", "55");
//        
//        restTemplate.postForObject("http://192.168.1.53:8080/IWAgentSOA/user/list.do", map, String.class);
        //restTemplate.getForObject("http://192.168.1.84:8080/IWAgentSOA/user/list.do", String.class, user);
    }
}
