package com.manyi.iw.soa.user.recommend.model;

import lombok.Data;
import com.manyi.iw.soa.common.model.Entity;

@Data
public class RecommendListRequest extends Entity {
    private Long userId;
    
    private Long agentId;
}
