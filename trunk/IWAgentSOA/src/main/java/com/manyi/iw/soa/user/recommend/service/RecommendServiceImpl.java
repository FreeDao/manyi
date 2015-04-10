package com.manyi.iw.soa.user.recommend.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.mapper.RecommendMapper;
import com.manyi.iw.soa.mapper.UserStatisticsMapper;
import com.manyi.iw.soa.user.recommend.model.RecommendListRequest;
import com.manyi.iw.soa.user.recommend.model.RecommendListResponse;
import com.manyi.iw.soa.user.recommend.model.RecommendSeekhouseListResponse;

@Service("recommendService")
@Transactional(readOnly=true)
public class RecommendServiceImpl {
    
    @Autowired
    private RecommendMapper recommendMapper;
    
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    
    
    /**
     * 功能描述:添加房源（可批量）
     *<pre>添加成功的同时也会更新用户统计表中recommend_num的值</pre>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = false)
    public int addRecommend(Map<String,Object> map){
        int successCount = recommendMapper.addRecommend(map);
        boolean result = successCount!=0;
        if(result){
            if(userStatisticsMapper.updateRecommendNum((Integer)map.get("userId"),successCount)!=0){
                return successCount;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
        
    }
    
    /**
     * 功能描述:获取推荐房源的列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param recommendList
     * @return
     */
    public PageResponse<RecommendListResponse> getRecommendList(RecommendListRequest recommendList){
        int total = recommendMapper.getRecommendListTotal(recommendList);
        recommendList.setTotal(total);
        return new PageResponse<RecommendListResponse>(total, recommendMapper.getRecommendList(recommendList));
    }
    
    
    /**
     * 功能描述:获取看房单列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param recommendList
     * @return
     */
    public PageResponse<RecommendSeekhouseListResponse> getRecommendSeekhouseList(RecommendListRequest recommendList){
        int total = recommendMapper.getRecommendSeekhouseListTotal(recommendList);
        recommendList.setTotal(total);
        return new PageResponse<RecommendSeekhouseListResponse>(total, recommendMapper.getRecommendSeekhouseList(recommendList));
    }
}
