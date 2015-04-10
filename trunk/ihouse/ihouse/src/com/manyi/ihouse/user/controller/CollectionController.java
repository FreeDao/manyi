package com.manyi.ihouse.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.ihouse.base.BaseController;
import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.user.model.CollectionRequest;
import com.manyi.ihouse.user.model.DeleteCollectionRequest;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.user.model.PageListRequest;
import com.manyi.ihouse.user.model.UpdateCollectionRequest;
import com.manyi.ihouse.user.service.CollectionService;

@Controller
@RequestMapping("/collection")
public class CollectionController extends BaseController {
    
    @Autowired
    @Qualifier("collectionService")
    private CollectionService collectionService;
    
    public CollectionService getCollectionService() {
        return collectionService;
    }

    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    /**
     * 添加收藏房源
     * @param request
     * @return
     */
    @RequestMapping(value = "/collect.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> collect(@RequestBody CollectionRequest request){
        Response response = collectionService.collect(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(response);
        return dr;
    }
    
    /**
     * 我的收藏
     * @param request
     * @return
     */
    @RequestMapping(value = "/myCollection.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> myCollection(HttpSession session, @RequestBody PageListRequest request){
        PageResponse<HouseBaseModel> collectionResponse =  collectionService.myCollection(session, request);
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(collectionResponse);
        return dr;
    }
    
    /**
     * 删除我的收藏中的房源
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteCollection.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> deleteCollection(@RequestBody DeleteCollectionRequest request){
        Response response = collectionService.deleteCollection(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(response);
        return dr;
    }
    
    /**
     * 同步收藏中的房源
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateCollection.rest", produces="application/json")
    @ResponseBody
    public  DeferredResult<Response> updateCollection(@RequestBody UpdateCollectionRequest request){
        Response response = collectionService.updateCollection(request);
        
        DeferredResult<Response> dr = new DeferredResult<Response>();
        dr.setResult(response);
        return dr;
    }
}
