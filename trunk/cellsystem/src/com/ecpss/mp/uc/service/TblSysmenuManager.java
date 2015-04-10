package com.ecpss.mp.uc.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
@Service(value = "tblSysmenuService")
@Scope(value = "singleton")
public class TblSysmenuManager extends BaseService implements TblSysmenuService{

}
