package com.manyi.iw.soa.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.manyi.iw.soa.agent.model.AgentSeekHouseModel;
import com.manyi.iw.soa.agent.model.AgentSeekHouseRequest;
import com.manyi.iw.soa.agent.model.AgentUnprocModel;
import com.manyi.iw.soa.entity.Agent;

public interface AgentMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     */
    int insert(Agent record);

    /**
     *
     */
    int insertSelective(Agent record);

    /**
     *
     */
    Agent selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(Agent record);

    /**
     *
     */
    int updateByPrimaryKey(Agent record);
    
    /**
     * 功能描述:使用手机号、密码进行登录获取经纪人的信息
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月17日      新建
     * </pre>
     *
     * @param mobile
     * @param password 
     * @return
     */
    Agent login(@Param("mobile")String mobile,@Param("password") String password);
    
    
    /**
     * 功能描述: 获取经纪人待处理的约看
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     wangbiao:   2014年6月20日      新建
     * </pre>
     *
     * @param map  存入agentId和userType
     * @return
     */
    public List<AgentUnprocModel> getAgentUnproc(Map<String,Object> map);
    
    public List<AgentSeekHouseModel> getAgentAllSeekHouse(AgentSeekHouseRequest agentSeekHouseRequest);
    
    public int getAgentAllSeekHouseTotal(AgentSeekHouseRequest agentSeekHouseRequest);
}