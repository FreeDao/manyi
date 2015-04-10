package com.manyi.ihouse.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.base.ErrorCode;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.entity.Agent;
import com.manyi.ihouse.entity.Appointment;
import com.manyi.ihouse.entity.Appointment_;
import com.manyi.ihouse.entity.SeekHouse;
import com.manyi.ihouse.entity.SeekHouse_;
import com.manyi.ihouse.entity.SeekhouseHistory;
import com.manyi.ihouse.entity.SequenceNum;
import com.manyi.ihouse.entity.User;
import com.manyi.ihouse.entity.Appointment.AppointmentState;
import com.manyi.ihouse.entity.hims.Estate;
import com.manyi.ihouse.entity.hims.HouseResource;
import com.manyi.ihouse.entity.hims.Residence;
import com.manyi.ihouse.user.model.AppointmentDetailRequest;
import com.manyi.ihouse.user.model.AppointmentModel;
import com.manyi.ihouse.user.model.AppointmentOperateRequest;
import com.manyi.ihouse.user.model.CommentRequest;
import com.manyi.ihouse.user.model.HaveSeeModel;
import com.manyi.ihouse.user.model.HaveSeePageRequest;
import com.manyi.ihouse.user.model.HaveSeePageResponse;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.user.model.HouseSummaryModel;
import com.manyi.ihouse.user.model.IsHaveTripResponse;
import com.manyi.ihouse.user.model.MyRequest;
import com.manyi.ihouse.user.model.SeeDetailResponse;
import com.manyi.ihouse.user.model.SeeHouseSubmitResponse;
import com.manyi.ihouse.user.model.ToBeSeeDetailResponse;
import com.manyi.ihouse.user.model.ToBeSeePageRequest;
import com.manyi.ihouse.user.model.ToBeSeePageResponse;
import com.manyi.ihouse.util.DateUtil;
import com.manyi.ihouse.util.IObjectUtils;

@Service(value="scheduleService")
public class ScheduleServiceImpl extends BaseService implements ScheduleService {

    /**
     * 用户是否有行程
     * (non-Javadoc)
     * @see com.manyi.ihouse.user.service.UserService#isHaveTrip(com.manyi.ihouse.user.model.MyRequest)
     */
    @Override
    @Transactional(readOnly=true)
    public IsHaveTripResponse isHaveTrip(MyRequest request) {
        IsHaveTripResponse res = new IsHaveTripResponse();
        
        EntityManager em = getEntityManager();
        
        User user = em.find(User.class, request.getUserId());
        //从EntityManager中获取CriteriaBuilder工厂
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        //如果验证码正确，切数据库中无此用户，记录数据库
        //构造一个CriteriaQuery对象
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);
        
        Root<Appointment> root = cq.from(Appointment.class);
        cq.select(root);
        
        Expression<Boolean> expwhere = cb.and(cb.equal(root.get(Appointment_.user),user)); //看房单中状态0为“加入看房单”
        cq.where(expwhere);
        TypedQuery<Appointment> query = em.createQuery(cq);
        List<Appointment> list = query.getResultList();
        
        int cnt = list.size();
        
        if(cnt>0){
            res.setIsHave(1);
        }
        else{
            res.setIsHave(0);
        }
        
        return res;
    }
    
    /*行程-待看的列表*/
    @Override
    @Transactional(readOnly=true)
    public ToBeSeePageResponse toBeSee(ToBeSeePageRequest request) {
        long userId = request.getUserId();

        ToBeSeePageResponse toBeSeeResponse = new ToBeSeePageResponse();
        EntityManager entityManager = this.getEntityManager();
        
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            toBeSeeResponse.setErrorCode(ErrorCode.USER_ERROR200009);
            toBeSeeResponse.setMessage("用户不存在");
            return toBeSeeResponse;
        }
        
        //获取经纪人
        Agent assignee = user.getAgent();
        
        //获取约会列表
        
        if(assignee!=null){
            toBeSeeResponse.setAssigneeName(assignee.getName());
            toBeSeeResponse.setAssigneeTel(assignee.getMobile());
            toBeSeeResponse.setAssigneePhotoUrl(assignee.getPhotoUrl());
            toBeSeeResponse.setScore(agentScore(assignee));
        }
        else{
            toBeSeeResponse.setAssigneeName("");
            toBeSeeResponse.setAssigneeTel("");
            toBeSeeResponse.setAssigneePhotoUrl("");
            toBeSeeResponse.setScore(0);
        }
        
        int pageSize = request.getPageSize();
        
        toBeSeeResponse.setPageSize(pageSize);
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        
        Root<Appointment> root = criteriaQuery.from(Appointment.class);
        criteriaQuery.select(root);
        
        Expression<Boolean> exp = criteriaBuilder.and(criteriaBuilder.equal(root.get(Appointment_.user), user),root.get(Appointment_.appointmentState).in(AppointmentState.待确认.getValue(),AppointmentState.待看房.getValue(),AppointmentState.经纪人已签到.getValue()));
        
        criteriaQuery.where(exp);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Appointment_.appointment_time)));
        
        TypedQuery<Appointment> query = entityManager.createQuery(criteriaQuery);
        
        List<Appointment> list = query.getResultList(); //约看列表
        int cnt = list.size();
        
        List<AppointmentModel> applist = new ArrayList<AppointmentModel>();
        
        //经纪人已抵达
        appointListForSort(applist, list, AppointmentState.经纪人已签到.getValue());
        
        //待确认
        appointListForSort(applist, list, AppointmentState.待确认.getValue());
        //待看房
        appointListForSort(applist, list, AppointmentState.待看房.getValue());
        
        //预约中的房源
        CriteriaQuery seekQuery = criteriaBuilder.createQuery(SeekHouse.class);
        Root<SeekHouse> seekRoot = seekQuery.from(SeekHouse.class);
        Expression<Boolean> sexp = criteriaBuilder.and(criteriaBuilder.equal(seekRoot.get(SeekHouse_.user),user),seekRoot.get(SeekHouse_.state).in(SeekHouse.HouseState.预约中.getValue(),SeekHouse.HouseState.改期中.getValue()));
        seekQuery.where(sexp);
        seekQuery.orderBy(criteriaBuilder.desc(seekRoot.get(SeekHouse_.update_time)));
        TypedQuery<SeekHouse> tq = entityManager.createQuery(seekQuery);
        List<HouseBaseModel> houses = new ArrayList<HouseBaseModel>();
        List<SeekHouse> seeklist = tq.getResultList();
        int seekSize = seeklist.size();
        if(seekSize>0){
            cnt ++;
            AppointmentModel seekModel = new AppointmentModel();
            seekModel.setState(4);
            seekModel.setAppointmentId(0);
            seekModel.setDate("");
            seekModel.setMeetAddress("");
            seekModel.setMemo("");
            seekModel.setTime("");
            seekModel.setWeekday("");
            seekModel.setHouseNum(seekSize);
            
            for(int i=0;i<seekSize;i++){
                SeekHouse sh = seeklist.get(i);
                HouseBaseModel hr = new HouseBaseModel();
                hr.setHouseId(sh.getHouseId());
                
                //获取房源信息
                Residence house = this.getEntityManager().find(Residence.class, sh.getHouseId().intValue());
                HouseResource res = this.getEntityManager().find(HouseResource.class, sh.getHouseId().intValue());
                String price = "";
                if(res!=null && res.getRentPrice()!=null){
                    price = ""+res.getRentPrice().intValue();
                }
                hr.setRentPrice(price);
                Estate estate = this.getEntityManager().find(Estate.class, house.getEstateId());
                hr.setEstateName(estate.getName());
                houses.add(hr);
            }
            seekModel.setHouses(houses);
            applist.add(seekModel);
        }
        
        toBeSeeResponse.setTotal(cnt);
        toBeSeeResponse.setRows(applist);
        
        return toBeSeeResponse;
    }
    
    List<AppointmentModel> appointListForSort(List<AppointmentModel> modelList, List<Appointment> appointmentList, int appointmentState){
        
        for(Appointment appointment : appointmentList){
            if(appointment.getAppointmentState()!=appointmentState){
                continue;
            }
            AppointmentModel appointmentModel = new AppointmentModel();
            appointmentModel.setAppointmentId(appointment.getId());
            
            Date apptime = appointment.getAppointment_time();
            
            appointmentModel.setDate(DateUtil.date2string(apptime, "yyyy/MM/dd"));
            appointmentModel.setTime(DateUtil.date2string(apptime, "HH:mm"));
            appointmentModel.setWeekday(DateUtil.getChinaDayOfWeek(apptime));
            
            if(appointment.getAppointmentState()==AppointmentState.待确认.getValue()){
                appointmentModel.setState(2);
            }
            else if(appointment.getAppointmentState()==AppointmentState.待看房.getValue()){
                appointmentModel.setState(3);
            }
            else if(appointment.getAppointmentState()==AppointmentState.经纪人已签到.getValue()){
                appointmentModel.setState(1);
            }
            else{
                appointmentModel.setState(4);
            }
            
            appointmentModel.setMeetAddress(appointment.getMeetAddress());
            appointmentModel.setMemo(IObjectUtils.convertString(appointment.getMemo()));
            
            Set<SeekHouse> set = appointment.getSeekHouse();
            
            appointmentModel.setHouseNum(set.size());
            
            
            Iterator<SeekHouse> iterator = set.iterator();

            List<HouseBaseModel> houses = new ArrayList<HouseBaseModel>();
            
            while(iterator.hasNext()){
                SeekHouse sh = iterator.next();
                HouseBaseModel hr = new HouseBaseModel();

                hr.setHouseId(sh.getHouseId());
                
                //获取房源信息
                Residence house = this.getEntityManager().find(Residence.class, sh.getHouseId().intValue());
                HouseResource res = this.getEntityManager().find(HouseResource.class, sh.getHouseId().intValue());
                String price = "";
                if(res!=null && res.getRentPrice()!=null){
                    price = ""+res.getRentPrice().intValue();
                }
                hr.setRentPrice(price);

                Estate estate = this.getEntityManager().find(Estate.class, house.getEstateId());
                hr.setEstateName(estate.getName());

                houses.add(hr);
            }
            
            appointmentModel.setHouses(houses);
            
            modelList.add(appointmentModel);
            
        }
        
        return modelList;
    }

    /*确认看房*/
    @Override
    public SeeHouseSubmitResponse seeHouseSubmit(AppointmentOperateRequest request) {
        long userId = request.getUserId();
        long appointmentId = request.getAppointmentId();

        EntityManager entityManager = this.getEntityManager();
        
        //生成看房单号
//        String seeHouseId = "R-K-sh-"+createSeehouseSerial();
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Appointment> update = criteriaBuilder.createCriteriaUpdate(Appointment.class);
        Root<Appointment> root = update.from(Appointment.class);
        update.set(root.get(Appointment_.appointmentState), AppointmentState.待看房.getValue());
//        update.set(root.get(Appointment_.seehouseNumber), seeHouseId);
        Expression<Boolean> exp = criteriaBuilder.and(criteriaBuilder.equal(root.get(Appointment_.id), appointmentId));
        update.where(exp);
        
        Query query = this.getEntityManager().createQuery(update);
        
        query.executeUpdate();
        
        CriteriaUpdate<SeekHouse> skUp = criteriaBuilder.createCriteriaUpdate(SeekHouse.class);
        Root<SeekHouse> shroot = skUp.from(SeekHouse.class);
        skUp.set(shroot.get(SeekHouse_.state),SeekHouse.HouseState.待看房.getValue());
        
        Appointment apt = entityManager.find(Appointment.class, appointmentId);
        
        Expression<Boolean> expsk = criteriaBuilder.and(criteriaBuilder.equal(shroot.get(SeekHouse_.appointment),apt));
        
        skUp.where(expsk);
        
        query = this.getEntityManager().createQuery(skUp);
        query.executeUpdate();
        
        
        
        
        //确认看房数据更新
        SeeHouseSubmitResponse response = new SeeHouseSubmitResponse();
        response.setSeeHouseId(apt.getSeehouseNumber());
        response.setErrorCode(0);
        response.setMessage("确认成功，请准时到达看房地点！");
        
        return response;
    }

    /*已确认待看房-详情页*/
    @Override
    @Transactional(readOnly=true)
    public ToBeSeeDetailResponse toBeSeeDetail(AppointmentDetailRequest request) {
        
        long userId = request.getUserId();
        User user = this.getEntityManager().find(User.class, userId);
        long appointmentId = request.getAppointmentId();
        
        ToBeSeeDetailResponse toBeSeeDetailResponse = new ToBeSeeDetailResponse();
        
        Appointment appointment = this.getEntityManager().find(Appointment.class, appointmentId);
        Agent ass = user.getAgent();
        if(ass!=null){
            toBeSeeDetailResponse.setAssigneeName(ass.getName());
            toBeSeeDetailResponse.setAssigneeTel(ass.getMobile());
            toBeSeeDetailResponse.setAssigneePhotoUrl(ass.getPhotoUrl());
            toBeSeeDetailResponse.setScore(agentScore(ass));
        }else{
            toBeSeeDetailResponse.setAssigneeName("");
            toBeSeeDetailResponse.setAssigneeTel("");
            toBeSeeDetailResponse.setAssigneePhotoUrl("");
            toBeSeeDetailResponse.setScore(0);
        }
        toBeSeeDetailResponse.setAppointmentId(appointmentId);
        toBeSeeDetailResponse.setState(AppointmentState.getByValue(appointment.getAppointmentState()).getDesc());
        Date apptime = appointment.getAppointment_time();
        
        toBeSeeDetailResponse.setDate(DateUtil.date2string(apptime, "yyyy/MM/dd"));
        toBeSeeDetailResponse.setTime(DateUtil.date2string(apptime, "HH:mm"));
        toBeSeeDetailResponse.setMeetAddress(appointment.getMeetAddress());
        toBeSeeDetailResponse.setMemo(IObjectUtils.convertString(appointment.getMemo()));
        toBeSeeDetailResponse.setProtocolUrl(getSeeHouseProtocolUrl());
        toBeSeeDetailResponse.setSeeHouseId(appointment.getSeehouseNumber());

        List<HouseSummaryModel> houseInfo = new ArrayList();
        if(appointment.getAppointmentState()==AppointmentState.待确认.getValue()||appointment.getAppointmentState()==AppointmentState.待看房.getValue()){
            Set<SeekHouse> set = appointment.getSeekHouse();
            
            Iterator<SeekHouse> iterator = set.iterator();
            while(iterator.hasNext()){
                
                HouseSummaryModel hr = new HouseSummaryModel();
                
                SeekHouse sh = iterator.next();
                hr.setHouseId(sh.getHouseId());
                if(sh.getState()==SeekHouse.HouseState.待登记.getValue()){
                    hr.setSeeState("待记");
                }
                else if(sh.getState()==SeekHouse.HouseState.已看房.getValue()){
                    hr.setSeeState("已看");
                }
                else if(sh.getState()==SeekHouse.HouseState.未看房.getValue()){
                    hr.setSeeState("未看");
                }
                else{
                    hr.setSeeState("");
                }
                
                //获取房源信息
                Residence house = this.getEntityManager().find(Residence.class, sh.getHouseId().intValue());
                HouseResource res = this.getEntityManager().find(HouseResource.class, sh.getHouseId().intValue());
                
                Estate estate = this.getEntityManager().find(Estate.class, house.getEstateId());
                String price = "";
                if(res!=null && res.getRentPrice()!=null){
                    price = ""+res.getRentPrice().intValue();
                }
                hr.setPrice(price);
                String address = estate.getName();
                if(appointment.getAppointmentState()==AppointmentState.已到已看房.getValue()){
                    address = estate.getName() + house.getBuilding() + "号" + house.getRoom() + "室";
                }
                hr.setEstateId(estate.getAreaId());
                hr.setAddress(address);
                houseInfo.add(hr);
            }
        }
        else{
            Set<SeekhouseHistory> set = appointment.getSeekhouseHistory();
            
            Iterator<SeekhouseHistory> iterator = set.iterator();
            while(iterator.hasNext()){
                
                HouseSummaryModel hr = new HouseSummaryModel();
                
                SeekhouseHistory sh = iterator.next();
                hr.setHouseId(sh.getHouseId());
                if(sh.getState()==SeekHouse.HouseState.待登记.getValue()){
                    hr.setSeeState("待记");
                }
                else if(sh.getState()==SeekHouse.HouseState.已看房.getValue()){
                    hr.setSeeState("已看");
                }
                else if(sh.getState()==SeekHouse.HouseState.未看房.getValue()){
                    hr.setSeeState("未看");
                }
                else{
                    hr.setSeeState("");
                }
                
                //获取房源信息
                Residence house = this.getEntityManager().find(Residence.class, sh.getHouseId().intValue());
                HouseResource res = this.getEntityManager().find(HouseResource.class, sh.getHouseId().intValue());
                
                Estate estate = this.getEntityManager().find(Estate.class, house.getEstateId());
                String price = "";
                if(res!=null && res.getRentPrice()!=null){
                    price = ""+res.getRentPrice().intValue();
                }
                hr.setPrice(price);
                String address = estate.getName();
                if(appointment.getAppointmentState()==AppointmentState.已到已看房.getValue()){
                    address = estate.getName() + house.getBuilding() + "号" + house.getRoom() + "室";
                }
                hr.setEstateId(estate.getAreaId());
                hr.setAddress(address);
                houseInfo.add(hr);
            }
        }
        
        
        toBeSeeDetailResponse.setHouseInfo(houseInfo);
        
        return toBeSeeDetailResponse;
    }

    /*申请改期*/
    @Override
    public Response changeAppointDate(AppointmentOperateRequest request) {
        long userId = request.getUserId();
        long appointmentId = request.getAppointmentId();
        System.out.println("user_id="+userId+",约会ID="+appointmentId+",改期说明="+request.getMemo());
        
        EntityManager entityManager = this.getEntityManager();
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Appointment> update = criteriaBuilder.createCriteriaUpdate(Appointment.class);
        Root<Appointment> root = update.from(Appointment.class);
        update.set(root.get(Appointment_.appointmentState), Appointment.AppointmentState.已改期.getValue());
        update.set(root.get(Appointment_.changeDateMemo), request.getMemo());
        
        Expression<Boolean> exp = criteriaBuilder.and(criteriaBuilder.equal(root.get(Appointment_.id), appointmentId));
        update.where(exp);
        
        Query query = this.getEntityManager().createQuery(update);
        
        query.executeUpdate();
        
        Appointment apt = entityManager.find(Appointment.class, appointmentId);
        //更新历史表
        Set<SeekHouse> set = apt.getSeekHouse();
        
        Iterator<SeekHouse> iterator = set.iterator();
        while(iterator.hasNext()){
            SeekHouse sh = iterator.next();
            SeekhouseHistory shis = new SeekhouseHistory();
            shis.setAppointment(apt);
            shis.setHouseId(sh.getHouseId());
            shis.setState(SeekHouse.HouseState.未看房.getValue());
            shis.setCreate_time(new Date());
            shis.setUpdate_time(new Date());
            entityManager.persist(shis);
            entityManager.flush();
        }
        
        
        //更新房源状态
        String jpsql = "";
        CriteriaUpdate<SeekHouse> skUp = criteriaBuilder.createCriteriaUpdate(SeekHouse.class);
        Root<SeekHouse> shroot = skUp.from(SeekHouse.class);
        skUp.set(shroot.get(SeekHouse_.state),SeekHouse.HouseState.改期中.getValue());
        Appointment nullApt = new Appointment();
        nullApt.setId(0l);
        skUp.set(shroot.get(SeekHouse_.appointment), nullApt);
        
        Expression<Boolean> expsk = criteriaBuilder.and(criteriaBuilder.equal(shroot.get(SeekHouse_.appointment),apt));
        
        skUp.where(expsk);
        
        query = this.getEntityManager().createQuery(skUp);
        query.executeUpdate();
        
        //确认看房数据更新
        Response response = new Response();
        response.setErrorCode(0);
        response.setMessage("改期申请成功，请等待经纪人联系您！");
        
        return response;
    }

    /*取消看房*/
    @Override
    public Response cancelAppoint(AppointmentOperateRequest request) {
        long userId = request.getUserId();
        long appointmentId = request.getAppointmentId();
        //取消看房数据更新
        EntityManager entityManager = this.getEntityManager();
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Appointment> update = criteriaBuilder.createCriteriaUpdate(Appointment.class);
        Root<Appointment> root = update.from(Appointment.class);
        update.set(root.get(Appointment_.appointmentState), Appointment.AppointmentState.已取消.getValue());
        update.set(root.get(Appointment_.cancelMemo), request.getMemo());
        update.set(root.get(Appointment_.cancelReason), request.getReason());
        
        Expression<Boolean> exp = criteriaBuilder.and(criteriaBuilder.equal(root.get(Appointment_.id), appointmentId));
        update.where(exp);
        
        Query query = this.getEntityManager().createQuery(update);
        
        query.executeUpdate();

        Appointment app = this.getEntityManager().find(Appointment.class, appointmentId);
        //更新历史表
        Set<SeekHouse> set = app.getSeekHouse();
        
        Iterator<SeekHouse> iterator = set.iterator();
        while(iterator.hasNext()){
            SeekHouse sh = iterator.next();
            SeekhouseHistory shis = new SeekhouseHistory();
            shis.setAppointment(app);
            shis.setHouseId(sh.getHouseId());
            shis.setState(SeekHouse.HouseState.未看房.getValue());
            shis.setCreate_time(new Date());
            shis.setUpdate_time(new Date());
            entityManager.persist(shis);
            entityManager.flush();
        }
        
        CriteriaUpdate<SeekHouse> upHouse = criteriaBuilder.createCriteriaUpdate(SeekHouse.class);
        Root<SeekHouse> hroot = upHouse.from(SeekHouse.class);
        
        upHouse.set(hroot.get(SeekHouse_.state), SeekHouse.HouseState.取消看房.getValue());
        Appointment nullApt = new Appointment();
        nullApt.setId(0l);
        upHouse.set(hroot.get(SeekHouse_.appointment), nullApt);
        Expression<Boolean> hexp = criteriaBuilder.and(criteriaBuilder.equal(hroot.get(SeekHouse_.appointment), app));
        upHouse.where(hexp);
        
        Query hquery = this.getEntityManager().createQuery(upHouse);
        
        hquery.executeUpdate();
        
        Response response = new Response();
        response.setErrorCode(0);
        response.setMessage("取消成功！");
        
        return response;
    }

    /*行程-已看的列表*/
    @Override
    public HaveSeePageResponse haveSee(HaveSeePageRequest request) {
        long userId = request.getUserId();
        EntityManager entityManager = this.getEntityManager();
        User user = entityManager.find(User.class, userId);
        HaveSeePageResponse haveSeeResponse = new HaveSeePageResponse();
        
        //获取经纪人
        Agent assignee = user.getAgent();
        
        //获取约会列表
        if(assignee!=null){
            haveSeeResponse.setAssigneeName(assignee.getName());
            haveSeeResponse.setAssigneeTel(assignee.getMobile());
            haveSeeResponse.setAssigneePhotoUrl(assignee.getPhotoUrl());
            haveSeeResponse.setScore(agentScore(assignee));
        }
        else{
            haveSeeResponse.setAssigneeName("");
            haveSeeResponse.setAssigneeTel("");
            haveSeeResponse.setAssigneePhotoUrl("");
            haveSeeResponse.setScore(0);
        }
        
        
        int pageSize = request.getPageSize();
        
        haveSeeResponse.setPageSize(pageSize);
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        
        Root<Appointment> root = criteriaQuery.from(Appointment.class);
        criteriaQuery.select(root);
        
        Expression<Boolean> exp = criteriaBuilder.and(
                criteriaBuilder.equal(root.get(Appointment_.user), user),
                root.get(Appointment_.appointmentState).in(AppointmentState.已到已看房.getValue(), AppointmentState.已到未看房.getValue(),
                        AppointmentState.未到未看房.getValue()));

        criteriaQuery.where(exp);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Appointment_.id)));
        
        TypedQuery<Appointment> query = entityManager.createQuery(criteriaQuery);
        
        int offset = request.getOffset();
        
        //据说不分页了
//      query.setFirstResult(offset);
//      query.setMaxResults(pageSize);
        List<Appointment> list = query.getResultList(); //约看列表
        int cnt = list.size();
        haveSeeResponse.setTotal(offset+cnt);
//      haveSeeResponse.setTotalPage((int)Math.ceil((double)cnt/(double)pageSize));
        
        CriteriaUpdate<Appointment> crup = criteriaBuilder.createCriteriaUpdate(Appointment.class);
        Root<Appointment> rootup = crup.from(Appointment.class);
        crup.set(rootup.get(Appointment_.aware_state), 1);
        Expression<Boolean> exp2 = criteriaBuilder.and(
                criteriaBuilder.equal(rootup.get(Appointment_.user), user),rootup.get(Appointment_.aware_state).isNull(),
                rootup.get(Appointment_.appointmentState).in(AppointmentState.已到已看房.getValue(), AppointmentState.已到未看房.getValue(),
                        AppointmentState.未到未看房.getValue()));
        crup.where(exp2);
        Query qur = entityManager.createQuery(crup);
        qur.executeUpdate();
        
        List<HaveSeeModel> hsmList = new ArrayList<HaveSeeModel>();
        
        for(Appointment appointment : list){
            
//          AppointmentModel appointmentModel = new AppointmentModel();
            
            HaveSeeModel haveSeeModel = new HaveSeeModel();
            haveSeeModel.setAppointmentId(appointment.getId());
            
            Date apptime = appointment.getAppointment_time();
            
            haveSeeModel.setDate(DateUtil.date2string(apptime, "yyyy/MM/dd"));
            haveSeeModel.setTime(DateUtil.date2string(apptime, "HH:mm"));
            haveSeeModel.setWeekday(DateUtil.getChinaDayOfWeek(apptime));
            haveSeeModel.setSeeHouseId(appointment.getSeehouseNumber());
            
            int statea = appointment.getAppointmentState();
//          haveSeeModel.setState(statea);
            if(statea == AppointmentState.已到已看房.getValue()){
                if((appointment.getAbility()!=null)||appointment.getAppearance()!=null||appointment.getAttitude()!=null){
                    haveSeeModel.setState(2);
                }
                else{
                    haveSeeModel.setState(1);
                }
            }else if(statea == AppointmentState.已到未看房.getValue()){
                haveSeeModel.setState(3);
            }else{
                haveSeeModel.setState(4);
            }
            
            Set<SeekhouseHistory> set = appointment.getSeekhouseHistory();
            
            haveSeeModel.setHouseNum(set.size());
            
            
            Iterator<SeekhouseHistory> iterator = set.iterator();

            List<HouseSummaryModel> houses = new ArrayList<HouseSummaryModel>();
            
            while(iterator.hasNext()){
                SeekhouseHistory sh = iterator.next();
                HouseSummaryModel hr = new HouseSummaryModel();
                
                hr.setHouseId(sh.getHouseId());
                hr.setSeeState(sh.getState()==SeekHouse.HouseState.已看房.getValue()?"已看":"未看");
                
                //获取房源信息
                Residence house = this.getEntityManager().find(Residence.class, sh.getHouseId().intValue());
                HouseResource res = this.getEntityManager().find(HouseResource.class, sh.getHouseId().intValue());
                
                Estate estate = this.getEntityManager().find(Estate.class, house.getEstateId());
                String price = "";
                if(res!=null&&res.getRentPrice()!=null){
                    price = ""+res.getRentPrice().intValue();
                }
                hr.setPrice(price);
                hr.setAddress(estate.getName());
                
                houses.add(hr);
            }
            
            haveSeeModel.setHouses(houses);
            
            hsmList.add(haveSeeModel);
            
        }
        
        haveSeeResponse.setRows(hsmList);
        return haveSeeResponse;
    }

    /*评价提交*/
    @Override
    public Response comment(CommentRequest request) {
        // TODO Auto-generated method stub
        long appointmentId = request.getAppointmentId();
        int attitude = request.getAttitude();
        int ability = request.getAbility();
        String commentWord = request.getCommentWord();
        if(commentWord.length()>500){
            commentWord = commentWord.substring(0,500);
        }

        System.out.println("约会ID："+appointmentId+"，态度："+attitude+"，能力："+ability+"，评价语："+commentWord);
        EntityManager entityManager = this.getEntityManager();
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Appointment> update = criteriaBuilder.createCriteriaUpdate(Appointment.class);
        Root<Appointment> root = update.from(Appointment.class);
//      update.set(root.get(Appointment_.appointmentState), 6);
        update.set(root.get(Appointment_.ability), request.getAbility());
        update.set(root.get(Appointment_.appearance), request.getAppearance());
        update.set(root.get(Appointment_.attitude), request.getAttitude());
        update.set(root.get(Appointment_.appraise), commentWord);
        
        Expression<Boolean> exp = criteriaBuilder.and(criteriaBuilder.equal(root.get(Appointment_.id), appointmentId));
        update.where(exp);
        
        Query query = this.getEntityManager().createQuery(update);
        
        query.executeUpdate();
        
        Response response = new Response();
        response.setErrorCode(0);
        response.setMessage("评价成功，谢谢！");
        
        return response;
    }

    /*已看的已评价-详情页*/
    @Override
    @Transactional(readOnly=true)
    public SeeDetailResponse commentDetail(AppointmentDetailRequest request) {
        long userId = request.getUserId();
        User user = this.getEntityManager().find(User.class, userId);
        long appointmentId = request.getAppointmentId();
        
        SeeDetailResponse seeDetailResponse = new SeeDetailResponse();
        
        Appointment appointment = this.getEntityManager().find(Appointment.class, appointmentId);
        Agent ass = user.getAgent();
        
        seeDetailResponse.setAppointmentId(appointmentId);
        if(ass!=null){
            seeDetailResponse.setAssigneeName(ass.getName());
            seeDetailResponse.setAssigneeTel(ass.getMobile());
        }
        else{
            seeDetailResponse.setAssigneeName("");
            seeDetailResponse.setAssigneeTel("");
        }
        
        Date apptime = appointment.getAppointment_time();
        
        seeDetailResponse.setDate(DateUtil.date2string(apptime, "yyyy/MM/dd"));
        seeDetailResponse.setTime(DateUtil.date2string(apptime, "HH:mm"));
        seeDetailResponse.setSeeHouseId(appointment.getSeehouseNumber());
        seeDetailResponse.setProtocolUrl(getSeeHouseProtocolUrl());
        seeDetailResponse.setMeetAddress(appointment.getMeetAddress());
        seeDetailResponse.setMemo(IObjectUtils.convertString(appointment.getMemo()));
        
        Set<SeekHouse> set = appointment.getSeekHouse();
        
        Iterator<SeekHouse> iterator = set.iterator();
        List<HouseSummaryModel> houseInfo = new ArrayList();
        while(iterator.hasNext()){
            
            HouseSummaryModel hr = new HouseSummaryModel();
            
            SeekHouse sh = iterator.next();
            hr.setHouseId(sh.getHouseId());
            hr.setSeeState(sh.getState()==SeekHouse.HouseState.已看房.getValue()?"已看":"未看");
            
            //获取房源信息
            Residence house = this.getEntityManager().find(Residence.class, sh.getHouseId().intValue());
            HouseResource res = this.getEntityManager().find(HouseResource.class, sh.getHouseId().intValue());
            
            Estate estate = this.getEntityManager().find(Estate.class, house.getEstateId());
            String price = "";
            if(res!=null && res.getRentPrice()!=null){
                price = ""+res.getRentPrice().intValue();
            }
            hr.setPrice(price);
            hr.setAddress(estate.getName());
            
            houseInfo.add(hr);
        }
        
        seeDetailResponse.setHouseInfo(houseInfo);
        
        return seeDetailResponse;
    }

    /*已取消-详情页*/
    @Override
    @Transactional(readOnly=true)
    public SeeDetailResponse cancelDetail(AppointmentDetailRequest request) {
        
        long userId = request.getUserId();
        User user = this.getEntityManager().find(User.class, userId);
        long appointmentId = request.getAppointmentId();
        
        System.out.println("用户ID："+userId+"约会ID："+appointmentId);
        
        SeeDetailResponse seeDetailResponse = new SeeDetailResponse();
        
        Appointment appointment = this.getEntityManager().find(Appointment.class, appointmentId);
        Agent ass = user.getAgent();
        
        seeDetailResponse.setAppointmentId(appointmentId);
        if(ass!=null){
            seeDetailResponse.setAssigneeName(ass.getName());
            seeDetailResponse.setAssigneeTel(ass.getMobile());
        }
        else{
            seeDetailResponse.setAssigneeName("");
            seeDetailResponse.setAssigneeTel("");
        }

        Date apptime = appointment.getAppointment_time();
        
        seeDetailResponse.setDate(DateUtil.date2string(apptime, "yyyy/MM/dd"));
        seeDetailResponse.setTime(DateUtil.date2string(apptime, "HH:mm"));
        seeDetailResponse.setSeeHouseId(appointment.getSeehouseNumber());
        seeDetailResponse.setProtocolUrl(getSeeHouseProtocolUrl());
        seeDetailResponse.setMeetAddress(appointment.getMeetAddress());
        seeDetailResponse.setMemo(IObjectUtils.convertString(appointment.getMemo()));
        
        Set<SeekHouse> set = appointment.getSeekHouse();
        
        Iterator<SeekHouse> iterator = set.iterator();
        List<HouseSummaryModel> houseInfo = new ArrayList();
        while(iterator.hasNext()){
            
            HouseSummaryModel hr = new HouseSummaryModel();
            
            SeekHouse sh = iterator.next();
            hr.setHouseId(sh.getHouseId());
            hr.setSeeState(sh.getState()==SeekHouse.HouseState.已看房.getValue()?"已看":"未看");
            
            //获取房源信息
            Residence house = this.getEntityManager().find(Residence.class, sh.getHouseId().intValue());
            HouseResource res = this.getEntityManager().find(HouseResource.class, sh.getHouseId().intValue());
            
            Estate estate = this.getEntityManager().find(Estate.class, house.getEstateId());
            String price = "";
            if(res!=null && res.getRentPrice()!=null){
                price = ""+res.getRentPrice().intValue();
            }
            hr.setPrice(price);
            hr.setAddress(estate.getName());
            
            houseInfo.add(hr);
        }
        
        seeDetailResponse.setHouseInfo(houseInfo);
        
        return seeDetailResponse;
    }

    
    /**
     * 
     * 功能描述:计算经纪人评分
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月17日      新建
     * </pre>
     *
     * @return
     */
    float agentScore(Agent agent){
        
        float score = 5.0f;
        
        return score;
    }
    
    /**
     * 
     * 功能描述:生成每日流水号
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hu-bin:   2014年6月19日      新建
     * </pre>
     *
     * @return
     */
    String createSeehouseSerial(){
        String date = DateUtil.date2string(new Date(), "yyyyMMdd");
        
        String jpql = "From SequenceNum sn where sn.sq_name=?";
        
        Query query = this.getEntityManager().createQuery(jpql);
        query.setParameter(1, date);
        int num = 0;
        List<SequenceNum> list = query.getResultList();
        if(list.size()==0){
            SequenceNum sequenceNum = new SequenceNum();
            sequenceNum.setSq_name(date);
            sequenceNum.setSerial(1);
            this.getEntityManager().persist(sequenceNum);
            this.getEntityManager().flush();
            num=1;
        }
        else{
            SequenceNum snum = list.get(0);
            num = snum.getSerial();
            snum.setSerial(++num);
            this.getEntityManager().merge(snum);
//            this.getEntityManager().flush();
        }
        
        String xs=String.valueOf(num);
        String [] ss = {"0000","000","00","0"};
        String str = date+"-"+ss[xs.length()] + xs;
        
        return str;
    }
    
    /**
     * 
     * 功能描述:获取看房协议地址URL
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     hubin:   2014年6月18日      新建
     * </pre>
     *
     * @return
     */
    String getSeeHouseProtocolUrl(){
        return "http://www.baidu.com";
    }
}
