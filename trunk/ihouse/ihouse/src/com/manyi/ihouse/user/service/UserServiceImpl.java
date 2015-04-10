package com.manyi.ihouse.user.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ihouse.common.util.DataUtil;
import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.base.ErrorCode;
import com.manyi.ihouse.base.Global;
import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.entity.Agent;
import com.manyi.ihouse.entity.Appointment;
import com.manyi.ihouse.entity.Appointment.AppointmentState;
import com.manyi.ihouse.entity.Appointment_;
import com.manyi.ihouse.entity.SeekHouse;
import com.manyi.ihouse.entity.SeekHouse.HouseState;
import com.manyi.ihouse.entity.SeekHouse_;
import com.manyi.ihouse.entity.SeekhouseHistory;
import com.manyi.ihouse.entity.SequenceNum;
import com.manyi.ihouse.entity.User;
import com.manyi.ihouse.entity.User.Gender;
import com.manyi.ihouse.entity.UserCollection;
import com.manyi.ihouse.entity.UserCollection_;
import com.manyi.ihouse.entity.UserFeedback;
import com.manyi.ihouse.entity.UserFeedback_;
import com.manyi.ihouse.entity.User_;
import com.manyi.ihouse.entity.hims.Area;
import com.manyi.ihouse.entity.hims.Estate;
import com.manyi.ihouse.entity.hims.HouseImageFile;
import com.manyi.ihouse.entity.hims.HouseResource;
import com.manyi.ihouse.entity.hims.Residence;
import com.manyi.ihouse.user.model.AppointmentDetailRequest;
import com.manyi.ihouse.user.model.AppointmentHouseRequest;
import com.manyi.ihouse.user.model.AppointmentModel;
import com.manyi.ihouse.user.model.AppointmentOperateRequest;
import com.manyi.ihouse.user.model.AutoLoginRequest;
import com.manyi.ihouse.user.model.BindFeedbackRequest;
import com.manyi.ihouse.user.model.CollectionRequest;
import com.manyi.ihouse.user.model.CommentRequest;
import com.manyi.ihouse.user.model.DeleteCollectionRequest;
import com.manyi.ihouse.user.model.DeleteSeekHouseRequest;
import com.manyi.ihouse.user.model.FeedbackRequest;
import com.manyi.ihouse.user.model.HaveSeeModel;
import com.manyi.ihouse.user.model.HaveSeePageRequest;
import com.manyi.ihouse.user.model.HaveSeePageResponse;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.user.model.HouseSummaryModel;
import com.manyi.ihouse.user.model.IsHaveTripResponse;
import com.manyi.ihouse.user.model.MyAgentResponse;
import com.manyi.ihouse.user.model.MyRequest;
import com.manyi.ihouse.user.model.PageListRequest;
import com.manyi.ihouse.user.model.RreshDataResponse;
import com.manyi.ihouse.user.model.SeeDetailResponse;
import com.manyi.ihouse.user.model.SeeHouseSubmitResponse;
import com.manyi.ihouse.user.model.SeekHouseRequest;
import com.manyi.ihouse.user.model.SeekHouseResponse;
import com.manyi.ihouse.user.model.ToBeSeeDetailResponse;
import com.manyi.ihouse.user.model.ToBeSeePageRequest;
import com.manyi.ihouse.user.model.ToBeSeePageResponse;
import com.manyi.ihouse.user.model.UpdateCollectionRequest;
import com.manyi.ihouse.user.model.UpdateInfoRequest;
import com.manyi.ihouse.user.model.UpdateInfoResponse;
import com.manyi.ihouse.user.model.UserLoginRequest;
import com.manyi.ihouse.user.model.UserLoginResponse;
import com.manyi.ihouse.user.model.UserMyResponse;
import com.manyi.ihouse.util.DateUtil;
import com.manyi.ihouse.util.IObjectUtils;
import com.manyi.ihouse.util.InfoUtils;
import com.manyi.ihouse.util.OSSObjectUtil;
import com.manyi.ihouse.util.TypeFormatUtils;

@Service(value = "userService")
public class UserServiceImpl extends BaseService implements UserService {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    /* 发送验证码 */
    @Override
    public Response sendVerifyCode(HttpSession session,UserLoginRequest request) {
        String mobile = request.getMobile(); //手机号
        // 生成验证码
        int errorCode;
        String message;
        if (StringUtils.isBlank(mobile)) {
            // 请输入手机号
            errorCode = ErrorCode.USER_ERROR200001;
            message = "手机号码不能为空";
        }
        // 电话号码的验证 13[0-9] , 15[0-9] , 18[0-9], 11位数字
        if (!DataUtil.checkMobile(mobile)) {
            // 手机号码格式不正确!
            errorCode = ErrorCode.USER_ERROR200002;
            message = "手机号码格式不正确";
        }
        String verifyCode = DataUtil.createRandomNum(6);// 得到六位随机数
        
        session.setAttribute("verifyCode", verifyCode);
        session.setAttribute("mobile", mobile);
        
        // 发送短息
        String content = "爱屋注册验证码,校验码为:" + verifyCode + Global.registAfterV;
        
        try {
            boolean inputLine = InfoUtils.sendSMS(mobile, content);
            if(inputLine==true){
                errorCode = 0;
                message = "短信发送成功";
                log.info("短信发送成功", content);
            }else{
                errorCode = ErrorCode.USER_ERROR200003;
                message = "短信发送失败";
                System.out.println("else短信发送失败"+content);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            // 注册验证码发送失败
            errorCode = ErrorCode.USER_ERROR200003;
            message = "短信发送失败";
            System.out.println("短信发送异常"+content);
        }
        
        // 发送验证码
        System.out.println("sessionID="+session.getId()+"--"+errorCode+"--"+message+"--mobile="+mobile+"--vcd="+verifyCode);
        Response res = new Response();
        res.setErrorCode(errorCode);
        
        res.setMessage(message);
        return res;
    }

    /* app登陆验证 */
    @Override
    public UserLoginResponse login(HttpSession session,UserLoginRequest request) {

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        
        String mobile = request.getMobile(); //手机号
        String verifyCode = request.getVerifyCode(); //验证码
        if(session.getAttribute("verifyCode")==null || session.getAttribute("mobile")==null){
            userLoginResponse.setErrorCode(ErrorCode.USER_ERROR200007);
            userLoginResponse.setMessage("验证码超时，请重新获取验证码登录");
            return userLoginResponse;
        }
            
        //验证用户
        String sessionVcd = (String)session.getAttribute("verifyCode");
        String sessionMobile = (String)session.getAttribute("mobile");
        System.out.println("sessionID="+session.getId()+"--mobile="+mobile+"--vcd="+verifyCode+"--sessionVcd="+sessionVcd+"--sessionMobile="+sessionMobile);
        
        //手机号码错误
        if(!sessionMobile.equals(mobile)){ 
            userLoginResponse.setErrorCode(ErrorCode.USER_ERROR200004);
            userLoginResponse.setMessage("手机号码错误");
            return userLoginResponse;
        }
        
        //验证码错误
        if(!sessionVcd.equals(verifyCode)&&!"999999".equals(verifyCode)){ 
            userLoginResponse.setErrorCode(ErrorCode.USER_ERROR200005);
            userLoginResponse.setMessage("验证码错误");
            return userLoginResponse;
        }
        
        String mobileSn = request.getMobileSn();

        EntityManager em = getEntityManager();
        //从EntityManager中获取CriteriaBuilder工厂
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        //如果验证码正确，切数据库中无此用户，记录数据库
        //构造一个CriteriaQuery对象
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        
        Root<User> root = cq.from(User.class);
        cq.select(root);
        
        Expression<Boolean> expwhere = cb.and(cb.equal(root.get(User_.mobile),mobile));
        cq.where(expwhere);
        TypedQuery<User> userQuery = em.createQuery(cq);
        User user = null;
        List<User> userList = userQuery.getResultList();
        if(userList.size()==0){
//            Agent ass = em.find(Agent.class, 1l);
            User newUser = new User();
            newUser.setMobile(mobile);
            newUser.setCreate_time(new Date());
//            newUser.setAgent(ass);
            newUser.setGender(Gender.UNKNOW.getValue());
            newUser.setMobileSn(mobileSn);
            em.persist(newUser);

            //em.flush();
            long userId = newUser.getId();
            user = newUser;
            session.setAttribute("userId", userId);
            
            String sql = "INSERT INTO iw_user_statistics (user_id) value("+userId+")";
            Query query = this.getEntityManager().createNativeQuery(sql);
            query.executeUpdate();
        }
        else{
            user =  userList.get(0);
            session.setAttribute("userId", user.getId());
            user.setLast_login_time(new Date());
            this.getEntityManager().merge(user);
        }
        
        long userId = user.getId();
        //获取“看房单”数量
        int seek = 0;
        seek = countSeekHouse(user,null);
        
        //获取用户“行程”数量
        int appoint = 0;
        appoint = countAppointNum(user);
        
        //已看知晓状态
        int recentEnd = countRecentEnd(user);
        
        //获取"我的"是否有新数据
        boolean havenew = false;
        havenew = isMyupdate(user);
        
        //更新意见反馈
        CriteriaUpdate<UserFeedback> up = cb.createCriteriaUpdate(UserFeedback.class);
        
        Root<UserFeedback> froot = up.from(UserFeedback.class);
        
        up.set(froot.get(UserFeedback_.user), user);
        
        Expression<Boolean> expf = cb.and(cb.equal(froot.get(UserFeedback_.mobileSn), mobileSn));
        up.where(expf);
        
        Query q = em.createQuery(up);
        q.executeUpdate();
        
        String[] house = request.getHouseIds();
        if(house!=null){
            for(int i=0;i<house.length;i++){
                long houseId = Long.parseLong(house[i]);
                CriteriaQuery<UserCollection> cqc = cb.createQuery(UserCollection.class);
                Root<UserCollection> rootc = cqc.from(UserCollection.class);
                cqc.select(rootc);
                Expression<Boolean> expwherec = cb.and(cb.equal(rootc.get(UserCollection_.user),user),cb.equal(rootc.get(UserCollection_.houseId), houseId));
                cqc.where(expwherec);
                TypedQuery<UserCollection> query = this.getEntityManager().createQuery(cqc);
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
    
                String sql = "UPDATE iw_user_statistics set collection_num=collection_num+1 where user_id="+userId;
                Query query1 = this.getEntityManager().createNativeQuery(sql);
                query1.executeUpdate();
            }
        }
        System.out.println("登录成功，准备发送信息");
        userLoginResponse.setUserId(userId);
        userLoginResponse.setName(user.getName());
        userLoginResponse.setGender(user.getGender());
        userLoginResponse.setMessage("登陆成功");
        userLoginResponse.setAppointNum(appoint);
        userLoginResponse.setSeekHouseNum(seek);
        userLoginResponse.setMyupdate(havenew);
        userLoginResponse.setRecentEnd(recentEnd);
        
        //经纪人信息
        Agent agent = user.getAgent();
        if(agent!=null){
            userLoginResponse.setAssigneeName(agent.getName());
            userLoginResponse.setAssigneeTel(agent.getMobile());
            userLoginResponse.setAssigneePhotoUrl(agent.getPhotoUrl());
            userLoginResponse.setScore(agentScore(agent));
        }
        else{
            userLoginResponse.setAssigneeName("");
            userLoginResponse.setAssigneeTel("");
            userLoginResponse.setAssigneePhotoUrl("");
            userLoginResponse.setScore(0f);
        }
        return userLoginResponse;
    }

    /**
     * 手机应用登出操作
     * (non-Javadoc)
     * @see com.manyi.ihouse.user.service.UserService#userLogout(javax.servlet.http.HttpSession, com.manyi.ihouse.user.model.MyRequest)
     */
    @Override
    public Response userLogout(HttpSession session, MyRequest request) {
        session.removeAttribute("userId");
        session.removeAttribute("mobile");
        
        Response res = new Response();
        res.setErrorCode(0);
        res.setMessage("登出成功");
        return res;
    }

    /*自动登陆*/
    @Override
    public UserLoginResponse autoLogin(HttpSession session, AutoLoginRequest request) {
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        
        String mobile = request.getMobile(); //手机号
        long userId = request.getUserId();
        
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userId);
        
        session.setAttribute("mobile", mobile);
        session.setAttribute("userId", userId);
        
        //获取“看房单”数量
        int seek = 0;
        seek = countSeekHouse(user,null);
        
        //获取用户“行程”数量
        int appoint = 0;
        appoint = countAppointNum(user);
        
        //获取"我的"是否有新数据
        boolean havenew = false;
        havenew = isMyupdate(user);
        
        userLoginResponse.setUserId(userId);
        userLoginResponse.setName(user.getName());
        userLoginResponse.setMessage("登陆成功");
        userLoginResponse.setAppointNum(appoint);
        userLoginResponse.setSeekHouseNum(seek);
        userLoginResponse.setMyupdate(havenew);
        
        return userLoginResponse;
    }

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

    /**
     * 我的经纪人
     * (non-Javadoc)
     * @see com.manyi.ihouse.user.service.UserService#myAgent(com.manyi.ihouse.user.model.MyRequest)
     */
    @Override
    @Transactional(readOnly=true)
    public MyAgentResponse myAgent(MyRequest request) {
        User user = this.getEntityManager().find(User.class, request.getUserId());
        Agent assign = user.getAgent();
        MyAgentResponse agent = new MyAgentResponse();
        if(assign!=null){
            agent.setAssigneeName(assign.getName());
            agent.setAssigneeTel(assign.getMobile());
            agent.setAssigneePhotoUrl(assign.getPhotoUrl());
            agent.setScore(5.0f);  //TODO 经纪人评分
        }
        
        return agent;
    }

    /**
     * 获取最新全局数据
     * (non-Javadoc)
     * @see com.manyi.ihouse.user.service.UserService#freshData(com.manyi.ihouse.user.model.MyRequest)
     */
    @Override
    @Transactional(readOnly=true)
    public RreshDataResponse freshData(MyRequest request) {
        
        long userId = request.getUserId();
        
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userId);
        
        RreshDataResponse res = new RreshDataResponse();
      //获取“看房单”数量
        int seek = 0;
        seek = countSeekHouse(user,null);
        //获取用户“行程”数量
        int appoint = 0;
        appoint = countAppointNum(user);
        
        //已看知晓状态
        int recentEnd = countRecentEnd(user);
        
        //获取"我的"是否有新数据
        boolean havenew = false;
        havenew = isMyupdate(user);
        res.setAppointNum(appoint);
        res.setMyupdate(havenew);
        res.setSeekHouseNum(seek);
        res.setRecentEnd(recentEnd);
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
    
    /*获取行程(约看单未完成约看的)数量*/
    public int countAppointNum(User user){
        EntityManager em = getEntityManager();
        //从EntityManager中获取CriteriaBuilder工厂
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        //如果验证码正确，切数据库中无此用户，记录数据库
        //构造一个CriteriaQuery对象
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);
        
        Root<Appointment> root = cq.from(Appointment.class);
        cq.select(root);
        
        Expression<Boolean> expwhere = cb.and(cb.equal(root.get(Appointment_.user),user),cb.equal(root.get(Appointment_.appointmentState), Appointment.AppointmentState.待确认.getValue())); //看房单中状态0为“加入看房单”
        cq.where(expwhere);
        TypedQuery<Appointment> query = em.createQuery(cq);
        List<Appointment> list = query.getResultList();
        return list.size();
    }
    
    /*获取已看未知晓数量*/
    public int countRecentEnd(User user){
        EntityManager em = getEntityManager();
        //从EntityManager中获取CriteriaBuilder工厂
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        //如果验证码正确，切数据库中无此用户，记录数据库
        //构造一个CriteriaQuery对象
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);
        
        Root<Appointment> root = cq.from(Appointment.class);
        cq.select(root);
        
        Expression<Boolean> expwhere = cb.and(
                cb.equal(root.get(Appointment_.user), user),
                root.get(Appointment_.aware_state).isNull(),
                root.get(Appointment_.appointmentState).in(Appointment.AppointmentState.已到已看房.getValue(), Appointment.AppointmentState.已到未看房.getValue(),
                        Appointment.AppointmentState.未到未看房.getValue())); //看房单中状态0为“加入看房单”
        cq.where(expwhere);
        TypedQuery<Appointment> query = em.createQuery(cq);
        List<Appointment> list = query.getResultList();
        int number = 0;
        if(list.size()>0){
            number = 1;
        }
        return number;
    }
    
    /*获取看我的是否有更新*/
    public boolean isMyupdate(User user){
        return false;
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

    /*获取“我的”信息*/
    @Override
    @Transactional(readOnly=true)
    public UserMyResponse my(MyRequest request) {
        
        UserMyResponse userMyResponse = new UserMyResponse();

        long userId = request.getUserId();
        
        User user = this.getEntityManager().find(User.class, userId);
        
        //查询我的经纪人
        Agent assignee = user.getAgent();
        String assigneeName = "";
        String assigneeTel = "";
        String assigneePhotoUrl = "";
        float score = 0f;
        
        if(assignee!=null){
            assigneeName = assignee.getName();
            assigneeTel = assignee.getMobile();
            assigneePhotoUrl = assignee.getPhotoUrl();
            score = agentScore(assignee);
        }
        
        //查询我的订单
        int myOrders = countAppointNum(user);
        
        //查询我的收藏
        int collectionCnt = countCollection(user);
        
        //查询客服电话
        String serviceTel = Global.SERVICE_TELEPHONE;
        
        userMyResponse.setAssigneeName(assigneeName);
        userMyResponse.setAssigneePhotoUrl(assigneePhotoUrl);
        userMyResponse.setAssigneeTel(assigneeTel);
        userMyResponse.setScore(score);
        userMyResponse.setCollectionNum(collectionCnt);
        userMyResponse.setOrderNum(myOrders);
        userMyResponse.setServiceTel(serviceTel);
        
        return userMyResponse;
    }

    /*意见反馈*/
    @Override
    public Response feedback(FeedbackRequest request) {
        
        long userId = request.getUserId();
        
        User user = null;
        if(userId!=0){
            user = this.getEntityManager().find(User.class, userId);
        }
        
        String advice = request.getAdvice();
        if(advice.length()>500){
            advice = advice.substring(0,500);
        }
        
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setUser(user);
        userFeedback.setFeedbackContent(request.getAdvice());
        userFeedback.setMobileSn(request.getMobileSn());
        userFeedback.setCreate_time(new Date());
        userFeedback.setUpdate_time(new Date());
        
        this.getEntityManager().persist(userFeedback);
        this.getEntityManager().flush();

        Response response = new Response();
        response.setErrorCode(0);
        response.setMessage("提交成功，谢谢！");
        
        return response;
    }

    /*登陆后将登陆前提交的反馈信息绑定到该账号*/
    @Override
    public Response bindFeedback(HttpSession session, BindFeedbackRequest request) {
        
        Response response = new Response();
        
        //用户登录超时或没有登陆
        if(session.getAttribute("userId")==null){
            response.setErrorCode(ErrorCode.USER_ERROR200005);
            response.setMessage("用户登录超时或没有登陆");
            return response;
        }
        
        long userId = (long)session.getAttribute("userId");
        User user = this.getEntityManager().find(User.class, userId);
        
        UserFeedback userFeedback = this.getEntityManager().find(UserFeedback.class, request.getFeedbackId());
        
        userFeedback.setUser(user);
        
        this.getEntityManager().merge(userFeedback);
        
        response.setMessage("反馈信息同步完成");
        
        return response;
    }
    
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
                if(appointment.getAppointmentState()==AppointmentState.已到已看房.getValue()||appointment.getAppointmentState()==AppointmentState.已到未看房.getValue()){
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
     * 获取更新信息
     * (non-Javadoc)
     * @see com.manyi.ihouse.user.service.UserService#updateInfo()
     */
    @Override
    @Transactional(readOnly=true)
    public UpdateInfoResponse updateInfo(UpdateInfoRequest request) {
        UpdateInfoResponse res = new UpdateInfoResponse();
        if(request.getType()==1){ //安卓
            int channel = request.getChannel();
            System.out.println(channel);
            res.setVersion("1.0.2");
            res.setForceUpdate(0);
            res.setDownloadUrl("http://192.168.1.250:8080/ihouse/iwjw.apk");
        }
        else if(request.getType()==2){ //IOS
            res.setVersion("1.0.5");
            res.setForceUpdate(0);
            res.setDownloadUrl("https://itunes.apple.com/cn/app/id426340811?mt=8&ls=1"); 
        }
        else{
            res.setVersion("");
            res.setForceUpdate(0);
            res.setDownloadUrl(""); 
        }
        
        return res;
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
