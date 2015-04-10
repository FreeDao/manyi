package com.manyi.ihouse.user.service;

import org.springframework.stereotype.Service;

import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.user.model.AppointmentHouseRequest;
import com.manyi.ihouse.user.model.DeleteSeekHouseRequest;
import com.manyi.ihouse.user.model.SeekHouseRequest;
import com.manyi.ihouse.user.model.SeekHouseResponse;

@Service(value = "seekHouseService")
public interface SeekHouseService {

    /**
     * 获取"看房单"页面数据
     * @param request
     * @return
     */
    public SeekHouseResponse seekHouse(SeekHouseRequest request);

    /**
     * 将看房单页中选中的房源提交约看
     * @param request
     * @return
     */
    public Response appointmentHouse(AppointmentHouseRequest request);

    /**
     * 删除看房单中的房源
     * @param request
     * @return
     */
    public Response deleteSeekHouse(DeleteSeekHouseRequest request);
    
}
