package com.manyi.iw.soa.agent.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manyi.iw.soa.agent.model.AgentSeekHouseModel;
import com.manyi.iw.soa.agent.model.AgentSeekHouseRequest;
import com.manyi.iw.soa.agent.model.AgentUnprocModel;
import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.entity.Agent;
import com.manyi.iw.soa.mapper.AgentMapper;


@Service("agentService")
@Transactional(readOnly=true)
public class AgentServiceImpl implements AgentServiceI
{
    @Autowired
    AgentMapper agentMapper;
    
    @Override
    public Agent getUserById(Long id)
    {
        System.out.println("--->0");
        Agent agent = agentMapper.selectByPrimaryKey(id);
        return agent;
    }

    @Override
    public Agent login(String mobile,String password) {
        return agentMapper.login(mobile, password);
    }

	@Override
	public List<AgentUnprocModel> getAgentUnproc(Map<String, Object> map) {
		return  agentMapper.getAgentUnproc(map);
	}

    @Override
    public PageResponse<AgentSeekHouseModel> getAgentSeekHouseList(AgentSeekHouseRequest agentSeekHouseRequest) {
        int total = agentMapper.getAgentAllSeekHouseTotal(agentSeekHouseRequest);
        agentSeekHouseRequest.setTotal(total);
        List<AgentSeekHouseModel> list = agentMapper.getAgentAllSeekHouse(agentSeekHouseRequest);
        return new PageResponse<AgentSeekHouseModel>(total,list);
    }
}
