package com.manyi.iw.soa.user.model;

import java.sql.Timestamp;
import java.util.Date;

import com.manyi.iw.soa.entity.UserStatistics;

import lombok.Data;

@Data
public class UserSearchResponse {
    private Long id;
    
    private String mobile;
    
    private String realName;
    
    private Long agentId;
    
    private Byte gender;
    
    private Timestamp lastLoginTime;
    
    private Byte bizStatus;
    
    /** 已申请约看 */
    private Integer applyedNum;

    /** 已看房源数 */
    private Integer sawNum;

    /** 看房单房源数 */
    private Integer inchartNum;

    /** 推荐房源数 */
    private Integer recommendNum;

    /** 待处理申请数 */
    private Integer waitdealNum;

    /** 收藏房源数 */
    private Integer collectionNum;
    
    /** 创建时间 */
    private Timestamp createTime;
}
