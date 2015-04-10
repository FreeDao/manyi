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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.entity.Agent;
import com.manyi.ihouse.entity.SeekHouse;
import com.manyi.ihouse.entity.SeekHouse_;
import com.manyi.ihouse.entity.SeekHouse.HouseState;
import com.manyi.ihouse.entity.User;
import com.manyi.ihouse.entity.hims.Area;
import com.manyi.ihouse.entity.hims.HouseImageFile;
import com.manyi.ihouse.user.model.AppointmentHouseRequest;
import com.manyi.ihouse.user.model.DeleteSeekHouseRequest;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.user.model.SeekHouseRequest;
import com.manyi.ihouse.user.model.SeekHouseResponse;
import com.manyi.ihouse.util.DateUtil;
import com.manyi.ihouse.util.OSSObjectUtil;
import com.manyi.ihouse.util.TypeFormatUtils;

@Service("seekHouseService")
public class SeekHouseServiceImpl extends BaseService implements SeekHouseService {

    /*获取 看房单页面 数据*/
    @Override
    @Transactional(readOnly=true)
    public SeekHouseResponse seekHouse(SeekHouseRequest request) {
        long userId = request.getUserId();//用户ID
        int type = request.getType();//查看类型1：我要看的，2：经济人推荐的 
        int pageSize = request.getPageSize();
        User user = this.getEntityManager().find(User.class, userId);
        
        long firstTime = request.getFirstTime();
        String whereStr = "";
        
        if(request.getFirstTime()>0){
            whereStr = " and uc.update_time<='" + DateUtil.date2string(new Date(firstTime), "yyyy-MM-dd HH:mm:ss") + "' ";
        }
        else {
            String dbtimesql = "select now() as Systime";
            Query dbquery = this.getEntityManager().createNativeQuery(dbtimesql);
            Object timeS = dbquery.getSingleResult();
            firstTime = ((Date)timeS).getTime();
//            firstTime = new Date().getTime();
            whereStr = " and uc.update_time<='" + DateUtil.date2string(new Date(firstTime), "yyyy-MM-dd HH:mm:ss") + "' ";
        }
        SeekHouseResponse seekHouseResponse = new SeekHouseResponse();

        List<HouseBaseModel> hrList = new ArrayList<HouseBaseModel>(); 
        
        if(type==1){
            String jpql = "select uc.house_id,hr.rentPrice,r.picNum,r.bedroomSum,r.livingRoomSum,r.balconySum,r.wcSum,r.floor,r.layers,"
                    + "r.decorateType,e.name,hr.publishDate,r.serialCode,iuc.house_id colls, hr.houseState,r.estateId "
                    + "from iw_seekHouse uc left join house r ON r.houseId = uc.house_Id left join HouseResource hr ON hr.houseId=uc.house_Id LEFT JOIN area e on e.areaId=r.estateId "
                    + "LEFT JOIN iw_user_collection iuc on iuc.house_id=uc.house_id and iuc.user_id=uc.user_id "
                    + " where uc.state="+HouseState.开始.getValue()+" and recommend_source=1 "+whereStr+" and uc.user_id="+userId+" order by uc.update_time desc";
            Query jq = this.getEntityManager().createNativeQuery(jpql);
            
            int index = request.getOffset();
            
            jq.setFirstResult(index);
            jq.setMaxResults(pageSize);
            
            List<Object[]> list = jq.getResultList();
            
            int cnt = countSeekHouse(user,(new Date(firstTime)));
            //获取看房单房源数量
            seekHouseResponse.setAssigneeRecommend(countRecommend(user,(new Date(firstTime)))); //TODO
            seekHouseResponse.setWantSee(cnt);
            seekHouseResponse.setFirstTime(firstTime);
            
            seekHouseResponse.setPageSize(request.getPageSize());
            
            seekHouseResponse.setTotal(cnt);
//          seekHouseResponse.setTotalPage((int)Math.ceil((double)cnt/(double)pageSize));
            
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
                if(row[2]!=null)
                    hr.setPicNum((int) row[2]);
                if(row[3]!=null)
                    hr.setBedroomSum((int) row[3]);
                if(row[4]!=null)
                    hr.setLivingRoomSum((int) row[4]);
//              hr.setBalconySum((int) row[5]);
                if(row[6]!=null)
                    hr.setWcSum((int) row[6]);
                if(row[7]!=null)
                    hr.setFloor((int) row[7]);
                if(row[8]!=null)
                    hr.setLayers((int) row[8]);
                
                if(row[9]!=null){
                    hr.setDecorateType(TypeFormatUtils.decorateTypeStr((int)row[9]));
                }
                else{
                    hr.setDecorateType(TypeFormatUtils.decorateTypeStr(0));
                }
                if(row[10]!=null){
                    hr.setEstateName((String)row[10]);
                }else{
                    hr.setEstateName("");
                }
                hr.setPubDate("");
                if(row[11]!=null){
                    hr.setPubDate(DateUtil.pubDateFormat((Date) row[11]));
                }
                
                if(row[13]!=null){
                    hr.setCollectionState(1);
                }
                
                //是否可租
                if(row[14]!=null&&((int)row[14]==1||(int)row[14]==3)){
                    hr.setEnable(1);
                }else{
                    hr.setEnable(0);
                }
                if(row[15]!=null){
                    hr.setEstateId((int)row[15]);
                }
                
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
//                    picurls[j]=OSSObjectUtil.getUrl(houseImageFile.getThumbnailKey(), 600000);
//                    hdUrls.put(OSSObjectUtil.getUrl(houseImageFile.getImgKey(), 600000), houseImageFile.getDescription());

                    picurls[j] = "http://house-images.oss-cn-hangzhou.aliyuncs.com/"+houseImageFile.getThumbnailKey();
                    hdUrls.put("http://house-images.oss-cn-hangzhou.aliyuncs.com/"+houseImageFile.getImgKey(), houseImageFile.getDescription());
                }
                
                hr.setPicUrls(picurls);
                hr.setHdUrls(hdUrls);
                
                hrList.add(hr);
            }
        }
        
        if(type==2){
            String jpql = "select uc.house_id,hr.rentPrice,r.picNum,r.bedroomSum,r.livingRoomSum,r.balconySum,r.wcSum,r.floor,r.layers,"
                    + "r.decorateType,e.name,hr.publishDate,r.serialCode,iuc.house_id colls, hr.houseState,r.estateId "
                    + "from iw_seekHouse uc left join house r ON r.houseId = uc.house_Id left join HouseResource hr ON hr.houseId=uc.house_Id LEFT JOIN area e on e.areaId=r.estateId "
                    + "LEFT JOIN iw_user_collection iuc on iuc.house_id=uc.house_id and iuc.user_id=uc.user_id "
                    + " where uc.state="+HouseState.开始.getValue()+" and recommend_source=2 "+whereStr+" and uc.user_id="+userId+" order by uc.update_time desc";
            
            Query jq = this.getEntityManager().createNativeQuery(jpql);
            
            int index = request.getOffset();
            
            jq.setFirstResult(index);
            jq.setMaxResults(pageSize);
            
            List<Object[]> list = jq.getResultList();
            
            int cnt = countSeekHouse(user,(new Date(firstTime)));
            //获取看房单房源数量
            seekHouseResponse.setAssigneeRecommend(countRecommend(user,(new Date(firstTime)))); //TODO
            seekHouseResponse.setWantSee(cnt);
            seekHouseResponse.setFirstTime(firstTime);
            
            seekHouseResponse.setPageSize(request.getPageSize());
            
            seekHouseResponse.setTotal(cnt);
//          seekHouseResponse.setTotalPage((int)Math.ceil((double)cnt/(double)pageSize));
            
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
                if(row[2]!=null)
                    hr.setPicNum((int) row[2]);
                if(row[3]!=null)
                    hr.setBedroomSum((int) row[3]);
                if(row[4]!=null)
                    hr.setLivingRoomSum((int) row[4]);
//              hr.setBalconySum((int) row[5]);
                if(row[6]!=null)
                    hr.setWcSum((int) row[6]);
                if(row[7]!=null)
                    hr.setFloor((int) row[7]);
                if(row[8]!=null)
                    hr.setLayers((int) row[8]);
                
                if(row[9]!=null){
                    hr.setDecorateType(TypeFormatUtils.decorateTypeStr((int)row[9]));
                }
                else{
                    hr.setDecorateType(TypeFormatUtils.decorateTypeStr(0));
                }
                if(row[10]!=null){
                    hr.setEstateName((String)row[10]);
                }else{
                    hr.setEstateName("");
                }
                hr.setPubDate("");
                if(row[11]!=null){
                    hr.setPubDate(DateUtil.pubDateFormat((Date) row[11]));
                }
                
                if(row[13]!=null){
                    hr.setCollectionState(1);
                }
                
                //是否可租
                if(row[14]!=null&&((int)row[14]==1||(int)row[14]==3)){
                    hr.setEnable(1);
                }else{
                    hr.setEnable(0);
                }
                if(row[15]!=null){
                    hr.setEstateId((int)row[15]);
                }
                
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
//                    picurls[j]=OSSObjectUtil.getUrl(houseImageFile.getThumbnailKey(), 600000);
//                    hdUrls.put(OSSObjectUtil.getUrl(houseImageFile.getImgKey(), 600000), houseImageFile.getDescription());

                    picurls[j] = "http://house-images.oss-cn-hangzhou.aliyuncs.com/"+houseImageFile.getThumbnailKey();
                    hdUrls.put("http://house-images.oss-cn-hangzhou.aliyuncs.com/"+houseImageFile.getImgKey(), houseImageFile.getDescription());
                }
                
                hr.setPicUrls(picurls);
                hr.setHdUrls(hdUrls);
                
                hrList.add(hr);
            }
        }
        
        
        seekHouseResponse.setRows(hrList);
        
        return seekHouseResponse;
    }

    //删除看房单中的房源
    @Override
    public Response deleteSeekHouse(DeleteSeekHouseRequest request) {
        long userId = request.getUserId();
        String houseIds = request.getHouseIds();
        if(userId==0||houseIds==null||"".equals(houseIds)){
          //删除房源
            Response res = new Response();
            res.setErrorCode(0); 
            res.setMessage("没有指定要删除的房源，删除0条房源！");
        }
        User user = this.getEntityManager().find(User.class, userId);
        
        System.out.println("userID="+userId+",房源IDs="+houseIds);
        
        String jqpl="delete from SeekHouse sk where sk.user = ? and sk.houseId in("+houseIds+")";
        Query query = this.getEntityManager().createQuery(jqpl);
        query.setParameter(1, user);
        int tmpNum = query.executeUpdate();
        
        String[] houseL = houseIds.split(",");
        //删除房源
        String sql = "UPDATE iw_user_statistics set applyed_num=applyed_num-"+houseL.length+" where user_id="+userId;
        Query query1 = this.getEntityManager().createNativeQuery(sql);
        query1.executeUpdate();
        
        Response res = new Response();
        res.setErrorCode(0); 
        res.setMessage("删除"+tmpNum+"条房源，删除成功！");
        return res;
    }

    /*将看房单页中选中的房源提交约看*/
    @Override
    public Response appointmentHouse(AppointmentHouseRequest request) {
        long userId = request.getUserId();
        User user = this.getEntityManager().find(User.class, userId);
        String username = request.getUsername();
        String appointTime = request.getAppointmentTime();
        String houseIds = request.getHouseIds();
        
        //如果没有分配经纪人，就为用户分配一个
        if(user.getAgent()==null){
            Agent assignee = this.getEntityManager().find(Agent.class, 1l);
            user.setAgent(assignee);
            
            this.getEntityManager().merge(assignee);
        }
        
        //更新用户信息
        user.setGender(request.getGender());
        user.setName(request.getUsername());
        if(user.getBiz_status()!=0){
            user.setBiz_status(0);
        }
        this.getEntityManager().merge(user);
        Agent agent = user.getAgent();
        //更新看房单表中的信息
        String jqpl="update SeekHouse sk set sk.agent_id=1, sk.state = "+SeekHouse.HouseState.预约中.getValue()+",sk.wishTime=? where sk.user = ? and sk.houseId in("+houseIds+")";
        Query query = this.getEntityManager().createQuery(jqpl);
        query.setParameter(1, request.getAppointmentTime());
        query.setParameter(2, user);
//      query.setParameter(2, houseIds);//使用in时这种赋值方式会报错 什么原因？
        int tmpNum = query.executeUpdate();
        
        String jpsqlsk = "From SeekHouse where user=? and recommend_source=2 and houseId in ("+houseIds+")";
        Query querysk = this.getEntityManager().createQuery(jpsqlsk);
        querysk.setParameter(1, user);
        List<SeekHouse> list = querysk.getResultList();
        
        for(SeekHouse seek : list){
           String in = "INSERT INTO iw_recommend (user_id,agent_id,house_id) VALUES ("+userId+","+user.getAgent().getId()+","+seek.getHouseId()+")";
           Query queryin= this.getEntityManager().createNativeQuery(in);
           queryin.executeUpdate();
        }
        
        //更新
        String sql = "UPDATE iw_user_statistics set applyed_num=applyed_num+"+tmpNum+" where user_id="+userId;
        Query query1 = this.getEntityManager().createNativeQuery(sql);
        query1.executeUpdate();
        
        Response res = new Response();
        res.setErrorCode(0);
        res.setMessage("约看成功，包含"+tmpNum+"套房源，您的经纪人将尽快联系您！");
        return res;
    }
    
    /*获取看房单数量*/
    public int countSeekHouse(User user,Date endTime){
        EntityManager em = getEntityManager();
        //从EntityManager中获取CriteriaBuilder工厂
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        //如果验证码正确，切数据库中无此用户，记录数据库
        //构造一个CriteriaQuery对象
        CriteriaQuery<SeekHouse> cq = cb.createQuery(SeekHouse.class);
        
        Root<SeekHouse> root = cq.from(SeekHouse.class);
        cq.select(root);
        
        Expression<Boolean> expwhere = null;
        if(endTime!=null&&!"".equals(endTime)){
            expwhere = cb.and(cb.equal(root.get(SeekHouse_.user),user),cb.equal(root.get(SeekHouse_.recommend_source), 1),cb.equal(root.get(SeekHouse_.state), HouseState.开始.getValue()),cb.lessThanOrEqualTo(root.get(SeekHouse_.update_time),endTime)); //看房单中状态0为“加入看房单”
        }
        else{
            expwhere = cb.and(cb.equal(root.get(SeekHouse_.user),user),cb.equal(root.get(SeekHouse_.recommend_source), 1),cb.equal(root.get(SeekHouse_.state), HouseState.开始.getValue())); //看房单中状态0为“加入看房单”
        }
//        Expression<Boolean> expwhere = cb.and(cb.equal(root.get(SeekHouse_.user),user),cb.equal(root.get(SeekHouse_.state), HouseState.开始.getValue())); //看房单中状态0为“加入看房单”
        cq.where(expwhere);
        TypedQuery<SeekHouse> query = em.createQuery(cq);
        List<SeekHouse> list = query.getResultList();
        return list.size();
    }
    
    public int countRecommend(User user,Date endTime){
        EntityManager em = getEntityManager();
        //从EntityManager中获取CriteriaBuilder工厂
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        //如果验证码正确，切数据库中无此用户，记录数据库
        //构造一个CriteriaQuery对象
        CriteriaQuery<SeekHouse> cq = cb.createQuery(SeekHouse.class);
        
        Root<SeekHouse> root = cq.from(SeekHouse.class);
        cq.select(root);
        
        Expression<Boolean> expwhere = null;
        if(endTime!=null&&!"".equals(endTime)){
            expwhere = cb.and(cb.equal(root.get(SeekHouse_.user),user),cb.equal(root.get(SeekHouse_.recommend_source), 2),cb.equal(root.get(SeekHouse_.state), HouseState.开始.getValue()),cb.lessThanOrEqualTo(root.get(SeekHouse_.update_time),endTime)); //看房单中状态0为“加入看房单”
        }
        else{
            expwhere = cb.and(cb.equal(root.get(SeekHouse_.user),user),cb.equal(root.get(SeekHouse_.recommend_source), 2),cb.equal(root.get(SeekHouse_.state), HouseState.开始.getValue())); //看房单中状态0为“加入看房单”
        }
//        Expression<Boolean> expwhere = cb.and(cb.equal(root.get(SeekHouse_.user),user),cb.equal(root.get(SeekHouse_.state), HouseState.开始.getValue())); //看房单中状态0为“加入看房单”
        cq.where(expwhere);
        TypedQuery<SeekHouse> query = em.createQuery(cq);
        List<SeekHouse> list = query.getResultList();
        return list.size();
    }
}
