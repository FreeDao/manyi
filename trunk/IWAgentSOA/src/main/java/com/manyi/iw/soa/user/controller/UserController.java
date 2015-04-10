package com.manyi.iw.soa.user.controller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.ResolverUtil.Test;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.common.model.Response;
import com.manyi.iw.soa.entity.User;
import com.manyi.iw.soa.entity.UserCollection;
import com.manyi.iw.soa.entity.UserMemo;
import com.manyi.iw.soa.mapper.UserMapper;
import com.manyi.iw.soa.user.model.UserEditBizStatusModel;
import com.manyi.iw.soa.user.model.UserMemoRequest;
import com.manyi.iw.soa.user.model.UserMemoResponse;
import com.manyi.iw.soa.user.model.UserSearchRequest;
import com.manyi.iw.soa.user.model.UserSearchResponse;
import com.manyi.iw.soa.user.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
    
    
    @Autowired
    private UserServiceImpl userService;
    
    
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
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    public PageResponse<UserSearchResponse> getUserList(@RequestBody UserSearchRequest user){
        
        System.out.println(user.getPageIndex());
        List<UserSearchResponse> list = userService.getUserList(user);
        System.out.println(user.getTotal());
        return new PageResponse<UserSearchResponse>(user.getTotal(),list);
    }
    
    /**
     * 功能描述:添加房源收藏
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userCollection
     * @return
     */
    @RequestMapping(value="/collection/add")
    @ResponseBody
    public String addUserCollection(/*@RequestBody */UserCollection userCollection){
        if(userService.addUserCollection(userCollection)){
            return "success";
        }else{
            return "failure";
        }
    }
    
    
    /**
     * 功能描述:根据搜藏id删除指定房源搜藏
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param id
     * @return
     */
    @RequestMapping(value="/collection/del")
    @ResponseBody
    public String delUserCollection(/*@RequestBody */Long id){
        if(userService.delUserCollection(id)){
            return "success";
        }else{
            return "failure";
        }
    }
    
    /**
     * 功能描述:获取用户的房源搜藏列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    @RequestMapping(value="/collection/list")
    @ResponseBody
    public List<UserCollection> getUserCollectionList(/*@RequestBody*/ Long userId){
        return userService.getUserCollectionList(userId); 
    }
    
    /**
     * 功能描述:获取用户详情
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    @RequestMapping(value="/detail")
    @ResponseBody
    public User getUserDetial(@RequestBody Long userId){
        return userService.getUserDetail(userId);
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
    public PageResponse<UserMemoResponse> getUserMemoList(@RequestBody UserMemoRequest userMemo){
        userMemo.setSortField("id");
        userMemo.setSortOrder("desc");
        return userService.getUserMemoList(userMemo);
    }
    
    /**
     * 功能描述:添加用户备注
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
    public Response addUserMemo(@RequestBody UserMemo userMemo){
        int code = 0;
        String msg;
        if(userService.addUserMemo(userMemo)){
            msg = "添加成功";
        }else{
            code = -1;
            msg = "添加失败，请重试";
        }
        return new Response(code,msg);
    }
    
    /**
     * 功能描述:编辑用户的业务状态
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月19日      新建
     * </pre>
     *
     * @param model
     * @return
     */
    @RequestMapping(value="/editBizStatus")
    @ResponseBody
    public Response editBizStatus(@RequestBody UserEditBizStatusModel model){
        int code = 0;
        String msg;
        if(userService.editBizStatus(model)){
            msg="修改成功";
        }else{
            code = -1;
            msg = "修改失败，请重试";
        }
        return new Response(code,msg);
    }
    
 
    
}
