package com.manyi.iw.soa.seekhouse.model;

import java.util.Date;

import lombok.Data;

/**
 * Function: 已有约看模型
 *
 * @author   yufei
 * @Date	 2014年6月18日		上午10:58:58
 *
 * @see 	  
 */
@Data
public class WillAppointMentVo {

    private Date appointmentTime;
    private Byte appointmentNum;
    private String agentName;
    private Byte status;
    private String extend1;
    private Long id;

}
