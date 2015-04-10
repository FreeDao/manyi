package com.manyi.ihouse.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.ihouse.base.BaseController;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.user.model.AppointmentHouseRequest;
import com.manyi.ihouse.user.model.DeleteSeekHouseRequest;
import com.manyi.ihouse.user.model.SeekHouseRequest;
import com.manyi.ihouse.user.model.SeekHouseResponse;
import com.manyi.ihouse.user.service.SeekHouseService;
@Controller
@RequestMapping("/seekHouse")
public class SeekHouseController extends BaseController {
    
    @Autowired
    @Qualifier("seekHouseService")
    private SeekHouseService seekHouseService;

    public SeekHouseService getSeekHouseService() {
        return seekHouseService;
    }

    public void setSeekHouseService(SeekHouseService seekHouseService) {
        this.seekHouseService = seekHouseService;
    }

    /**
     * 看房单页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/seekHouse.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> seekHouse(@RequestBody SeekHouseRequest request){
        SeekHouseResponse seekHouseResponse = seekHouseService.seekHouse(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(seekHouseResponse);
        return dr;
    }
    
    /**
     * 删除看房单中的房源
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteSeekHouse.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> deleteSeekHouse(@RequestBody DeleteSeekHouseRequest request){
        Response response = seekHouseService.deleteSeekHouse(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(response);
        return dr;
    }
    
    /**
     * 将看房单页中选中的房源提交约看
     * @param request
     * @return
     */
    @RequestMapping(value = "/appointmentHouse.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> appointmentHouse(@RequestBody AppointmentHouseRequest request){
        Response response = seekHouseService.appointmentHouse(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(response);
        return dr;
    }
}
