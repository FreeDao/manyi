package com.manyi.iw.agent.console.user.recommend.model;

import lombok.Data;

import com.manyi.iw.agent.console.entity.Entity;

@Data
public class RecommendListRequest extends Entity {
    private Long userId;
    
    private Long agentId;
}
