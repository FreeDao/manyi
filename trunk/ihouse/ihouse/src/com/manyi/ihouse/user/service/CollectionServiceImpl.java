package com.manyi.ihouse.user.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.base.ErrorCode;
import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.entity.User;
import com.manyi.ihouse.entity.UserCollection;
import com.manyi.ihouse.entity.UserCollection_;
import com.manyi.ihouse.entity.hims.Area;
import com.manyi.ihouse.entity.hims.HouseImageFile;
import com.manyi.ihouse.user.model.CollectionRequest;
import com.manyi.ihouse.user.model.DeleteCollectionRequest;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.user.model.PageListRequest;
import com.manyi.ihouse.user.model.UpdateCollectionRequest;
import com.manyi.ihouse.util.DateUtil;
import com.manyi.ihouse.util.OSSObjectUtil;
import com.manyi.ihouse.util.TypeFormatUtils;

@Service(value="collectionService")
public class CollectionServiceImpl extends BaseService implements CollectionService {


    /**
     * 收藏房源
     */
    @Override
    public Response collect(CollectionRequest request) {
        long userId = request.getUserId();
        long houseId = request.getHouseId();
        System.out.println("收藏房源user_id="+userId+";houseId="+houseId);
        User user = this.getEntityManager().find(User.class, userId);
        if(user==null){
            Response res = new Response();
            res.setErrorCode(ErrorCode.USER_ERROR200009);
            res.setMessage("用户不存在");
            return res;
        }
        
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserCollection> cq = cb.createQuery(UserCollection.class);
        Root<UserCollection> root = cq.from(UserCollection.class);
        cq.select(root);
        Expression<Boolean> expwhere = cb.and(cb.equal(root.get(UserCollection_.user),user),cb.equal(root.get(UserCollection_.houseId), houseId));
        cq.where(expwhere);
        TypedQuery<UserCollection> query = this.getEntityManager().createQuery(cq);
        List<UserCollection> list = query.getResultList();
        if(list.size()>0){
            Response res = new Response();
            res.setErrorCode(ErrorCode.USER_ERROR200008);
            res.setMessage("房源不可重复收藏！");
            return res;
        }
        
        UserCollection collection = new UserCollection();
        collection.setCreate_time(new Date());
        collection.setHouseId(houseId);
        collection.setUpdate_time(new Date());
        collection.setUser(user);
        
        this.getEntityManager().persist(collection);
        this.getEntityManager().flush();

        String sql = "UPDATE iw_user_statistics set collection_num=collection_num+1 where user_id="+userId;
        Query query1 = this.getEntityManager().createNativeQuery(sql);
        query1.executeUpdate();
        
        Response res = new Response();
        res.setErrorCode(0);
        res.setMessage("收藏成功！");
        return res;
    }

    /*我的收藏*/
    @Override
    @Transactional(readOnly=true)
    public PageResponse<HouseBaseModel> myCollection(HttpSession session, PageListRequest request) {
        long userId = request.getUserId();
        PageResponse<HouseBaseModel> pages = new PageResponse<HouseBaseModel>();
        
        User user = this.getEntityManager().find(User.class, userId);
        
        Integer offset = request.getOffset();
        Integer pageSize = request.getPageSize()==0?10:request.getPageSize();
        
        pages.setPageSize(pageSize);
        
        String jpql = "select uc.house_id,hr.rentPrice,r.picNum,r.bedroomSum,r.livingRoomSum,r.balconySum,r.wcSum,r.floor,r.layers,"
                + "r.decorateType,e.name,hr.publishDate,r.serialCode, hr.houseState,r.estateId "
                + "from iw_user_collection uc LEFT JOIN house r on r.houseId = uc.house_id left join HouseResource hr on hr.houseId = uc.house_id left join area e on e.areaId= r.estateId "
                + "where uc.user_id="+userId+" order by uc.create_time desc";
        
        Query jq = this.getEntityManager().createNativeQuery(jpql);
        
        jq.setFirstResult(offset);
        jq.setMaxResults(pageSize);
        
        List<Object[]> list = jq.getResultList();
        
        int cnt = countCollection(user);
        //获取收藏数量
        pages.setTotal(cnt);
        
        List<HouseBaseModel> hrList = new ArrayList<HouseBaseModel>(); 
        
        //获取房源信息列表
        for(int i=0;i<list.size();i++){
            HouseBaseModel hr = new HouseBaseModel();
            Object[] row = list.get(i);
            hr.setHouseId(((BigInteger)row[0]).longValue());
            String price = "";
            if(row[1]!=null){
                price = ""+((BigDecimal) row[1]).intValue();
            }
            hr.setRentPrice(price);
            if(row[3]!=null)
                hr.setBedroomSum((int) row[3]);
            if(row[4]!=null)
                hr.setLivingRoomSum((int) row[4]);
            if(row[6]!=null)
                hr.setWcSum((int) row[6]);
            if(row[7]!=null)
                hr.setFloor((int) row[7]);
            if(row[8]!=null)
                hr.setLayers((int) row[8]);
            if(row[9]!=null){
                hr.setDecorateType(TypeFormatUtils.decorateTypeStr((int)row[9])); //1 毛坯，2 装修
            }
            else{
                hr.setDecorateType(TypeFormatUtils.decorateTypeStr(0)); //1 毛坯，2 装修
            }
                
            if(row[10]!=null){
                hr.setEstateName((String)row[10]);
            }
            else{
                hr.setEstateName("");
            }
            if(row[11]!=null){
                hr.setPubDate(DateUtil.pubDateFormat((Date) row[11]));
            }else{
                hr.setPubDate("");
            }

            
            //是否可租
            if(row[13]!=null&&((int)row[13]==1||(int)row[13]==3)){
                hr.setEnable(1);
            }else{
                hr.setEnable(0);
            }
            if(row[14]!=null)
                hr.setEstateId((int)row[14]);
            
            int layers = 0;
            if(row[8]!=null)
                layers = (int) row[8];
            if(layers<=6)
                hr.setFloorType("底层");
            else if(layers>6 && layers<=13)
                hr.setFloorType("中层");
            else
                hr.setFloorType("高层");
            
            String serialCode = (String)row[12];
            if(serialCode==null||serialCode.length()<20){
                hr.setTownName("");
                hr.setAreaName("");
            }
            else{
                String twonsc = serialCode.substring(0,15);
                String areasc = serialCode.substring(0,20);
                String jpql2 = "from Area where serialCode=?";
                Query query = this.getEntityManager().createQuery(jpql2);
                query.setParameter(1, twonsc);
                List<Area> ls = query.getResultList();
                if(ls.size()>0){
                    Area area = ls.get(0);
                    hr.setAreaName(area.getName());
                }
                else{
                    hr.setAreaName("");
                }
                
                query.setParameter(1, areasc);
                ls = query.getResultList();
                if(ls.size()>0){
                    Area area = ls.get(0);
                    hr.setTownName(area.getName());
                }
                else{
                    hr.setTownName("");
                }
            }
            
            String jpql3 = "from HouseImageFile where houseId=?";
            Query query = this.getEntityManager().createQuery(jpql3);
            query.setParameter(1, ((BigInteger)row[0]).longValue());
            List<HouseImageFile> lso = query.getResultList();
            String[] picurls = new String[lso.size()];

            hr.setPicNum(lso.size());
            
            Map<String,String> hdUrls = new HashMap<String,String>();
            
            for(int j=0;j<lso.size();j++){
                HouseImageFile houseImageFile = lso.get(j);
//                picurls[j]=OSSObjectUtil.getUrl(houseImageFile.getThumbnailKey(), 600000);
//                hdUrls.put(OSSObjectUtil.getUrl(houseImageFile.getImgKey(), 600000), houseImageFile.getDescription());
                //http://house-images.oss-cn-hangzhou.aliyuncs.com
                picurls[j] = "http://house-images.oss-cn-hangzhou.aliyuncs.com/"+houseImageFile.getThumbnailKey();
                hdUrls.put("http://house-images.oss-cn-hangzhou.aliyuncs.com/"+houseImageFile.getImgKey(), houseImageFile.getDescription());
            }
            
            hr.setPicUrls(picurls);
            hr.setHdUrls(hdUrls);
            
            hrList.add(hr);
        }
        
        pages.setRows(hrList);
        
        return pages;
    }

    /*删除我的收藏中的房源*/
    @Override
    public Response deleteCollection(DeleteCollectionRequest request) {
        long userId = request.getUserId();
        long houseId = request.getHouseId();
        User user = this.getEntityManager().find(User.class, userId);
        
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaDelete<UserCollection> del = cb.createCriteriaDelete(UserCollection.class);
        Root<UserCollection> root = del.from(UserCollection.class);
      
        Expression<Boolean> exp = cb.and(cb.equal(root.get(UserCollection_.user), user),cb.equal(root.get(UserCollection_.houseId),houseId));
        del.where(exp);
        this.getEntityManager().createQuery(del).executeUpdate();
        
        //更新
        String sql = "UPDATE iw_user_statistics set collection_num=collection_num-1 where user_id="+userId;
        Query query1 = this.getEntityManager().createNativeQuery(sql);
        query1.executeUpdate();
        
        Response res = new Response();
        res.setErrorCode(0);
        res.setMessage("删除成功！");
        return res;
        
    }

    /**
     * 同步收藏
     * (non-Javadoc)
     * @see com.manyi.ihouse.user.service.UserService#updateCollection(com.manyi.ihouse.user.model.UpdateCollectionRequest)
     */
    @Override
    public Response updateCollection(UpdateCollectionRequest request) {
        long userId = request.getUserId();
        User user = this.getEntityManager().find(User.class, userId);
        Response res = new Response();
        if(user==null){
            res.setErrorCode(ErrorCode.USER_ERROR200009);
            res.setMessage("用户不存在");
            return res;
        }
        String[] house = request.getHouseIds();
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        int addCol = 0;
        for(int i=0;i<house.length;i++){
            long houseId = Long.parseLong(house[i]);
            CriteriaQuery<UserCollection> cq = cb.createQuery(UserCollection.class);
            Root<UserCollection> root = cq.from(UserCollection.class);
            cq.select(root);
            Expression<Boolean> expwhere = cb.and(cb.equal(root.get(UserCollection_.user),user),cb.equal(root.get(UserCollection_.houseId), houseId));
            cq.where(expwhere);
            TypedQuery<UserCollection> query = this.getEntityManager().createQuery(cq);
            List<UserCollection> list = query.getResultList();
            if(list.size()>0){
                continue;
            }
            UserCollection collection = new UserCollection();
            collection.setCreate_time(new Date());
            collection.setHouseId(houseId);
            collection.setUpdate_time(new Date());
            collection.setUser(user);
            
            this.getEntityManager().persist(collection);
            this.getEntityManager().flush();
            addCol ++;
        }
        if(addCol>0){
            String sql = "UPDATE iw_user_statistics set collection_num=collection_num+1 where user_id="+userId;
            Query query1 = this.getEntityManager().createNativeQuery(sql);
            query1.executeUpdate();
        }
        res.setErrorCode(0);
        res.setMessage("同步成功！");
        return res;
    }
    
    /*查询我的收藏数量*/
    public int countCollection(User user){
        EntityManager em = getEntityManager();
        //从EntityManager中获取CriteriaBuilder工厂
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        //如果验证码正确，切数据库中无此用户，记录数据库
        //构造一个CriteriaQuery对象
        CriteriaQuery<UserCollection> cq = cb.createQuery(UserCollection.class);
        
        Root<UserCollection> root = cq.from(UserCollection.class);
        cq.select(root);
        
        Expression<Boolean> expwhere = cb.and(cb.equal(root.get(UserCollection_.user),user));
        cq.where(expwhere);
        TypedQuery<UserCollection> query = em.createQuery(cq);
        List<UserCollection> list = query.getResultList();
        return list.size();
    }
}
