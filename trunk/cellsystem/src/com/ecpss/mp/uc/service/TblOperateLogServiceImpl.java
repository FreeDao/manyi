package com.ecpss.mp.uc.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
@Service(value = "tblOperateLogService")
@Scope(value = "singleton")
public class TblOperateLogServiceImpl extends BaseService implements TblOperateLogService{

}
