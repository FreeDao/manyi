package com.manyi.iw.soa.mapper;

import java.util.List;
import java.util.Map;

import com.manyi.iw.soa.entity.Recommend;
import com.manyi.iw.soa.user.recommend.model.RecommendListRequest;
import com.manyi.iw.soa.user.recommend.model.RecommendListResponse;
import com.manyi.iw.soa.user.recommend.model.RecommendSeekhouseListResponse;

public interface RecommendMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     */
    int insert(Recommend record);

    /**
     *
     */
    int insertSelective(Recommend record);

    /**
     *
     */
    Recommend selectByPrimaryKey(Long id);

    /**
     *
     */
    int updateByPrimaryKeySelective(Recommend record);

    /**
     *
     */
    int updateByPrimaryKey(Recommend record);
    
    
    /**
     * 功能描述:添加推荐房源（可批量）
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param map
     * @return
     */
    int addRecommend(Map<String,Object> map);
    
    /**
     * 功能描述:获取推荐房源列表的数量
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param recommendList
     * @return
     */
    int getRecommendListTotal(RecommendListRequest recommendList);
    
    /**
     * 功能描述:获取推荐房源列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param recommendList
     * @return
     */
    List<RecommendListResponse> getRecommendList(RecommendListRequest recommendList);
    
    
    /**
     * 功能描述:获取看房单列表数量
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     carvink:   2014年6月22日      新建
     * </pre>
     *
     * @param recommendList
     * @return
     */
    int getRecommendSeekhouseListTotal(RecommendListRequest recommendList);
    
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
    List<RecommendSeekhouseListResponse> getRecommendSeekhouseList(RecommendListRequest recommendList);
}