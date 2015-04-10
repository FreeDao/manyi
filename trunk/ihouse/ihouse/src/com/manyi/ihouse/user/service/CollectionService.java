package com.manyi.ihouse.user.service;

import javax.servlet.http.HttpSession;

import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.user.model.CollectionRequest;
import com.manyi.ihouse.user.model.DeleteCollectionRequest;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.user.model.PageListRequest;
import com.manyi.ihouse.user.model.UpdateCollectionRequest;

public interface CollectionService {
    
    /**
     * 
     * 功能描述:收藏房源
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hu-bin:   2014年6月19日      新建
     * </pre>
     *
     * @param request
     * @return
     */
    public Response collect(CollectionRequest request);

    /**
     * 我的收藏
     * @param request
     * @return
     */
    public PageResponse<HouseBaseModel> myCollection(HttpSession session, PageListRequest request);
    
    /**
     * 删除我的收藏中的房源
     * @param request
     * @return
     */
    public Response deleteCollection(DeleteCollectionRequest request);

    /**
     * 
     * 功能描述:同步收藏中的房源
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hu-bin:   2014年6月24日      新建
     * </pre>
     *
     * @param request
     * @return
     */
    public Response updateCollection(UpdateCollectionRequest request);
    
}
