package com.manyi.iw.soa.seekhouse.user.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.manyi.iw.soa.common.model.AppointmentState;
import com.manyi.iw.soa.entity.Appointment;
import com.manyi.iw.soa.entity.Recommend;
import com.manyi.iw.soa.entity.Seekhouse;
import com.manyi.iw.soa.entity.SeekhouseHistory;
import com.manyi.iw.soa.mapper.AppointmentMapper;
import com.manyi.iw.soa.mapper.RecommendMapper;
import com.manyi.iw.soa.mapper.SeekhouseHistoryMapper;
import com.manyi.iw.soa.mapper.SeekhouseMapper;
import com.manyi.iw.soa.seekhouse.model.SeeHouseVo;
import com.manyi.iw.soa.seekhouse.user.model.SeekhousePositionResponse;
import com.manyi.iw.soa.user.service.UserServiceImpl;

@Service
@Transactional
public class UserSeekHouseServiceImp implements UserSeekHouseServiceI {

    @Autowired
    private SeekhouseMapper seekhouseMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    SeekhouseHistoryMapper seekhouseHistoryMapper;
    @Autowired
    RecommendMapper recommendMapper;

    @Autowired
    private UserServiceImpl userService;

    @Override
    @Transactional(readOnly = true)
    public List<SeeHouseVo> getWaitDealSeeHouse(Long userId, Byte type) {

        return seekhouseMapper.getWaitDealSeeHouse(userId, type);
    }

    /**
     * 功能描述:
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     yufei:   2014年6月18日      新建
     * </pre>
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public List<SeeHouseVo> getCancelSeeHouse(Long userId) {

        return seekhouseMapper.getCancelSeeHouse(userId);
    }

    @Override
    public void updateSeekHouseMemo(Long id, String memo) {
        try {
            memo = URLDecoder.decode(memo, "utf-8");
            seekhouseMapper.updateSeekHouseMemo(id, memo);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public Boolean offSaleSeekHouseByhoustId(Long houseId) {

        List<Long> list = seekhouseMapper.getWillCancelAppointmentIds(houseId);
        for (Long appointmentId : list) {

            Appointment appointment = appointmentMapper.selectByPrimaryKey(appointmentId);

            if (appointment != null && (appointment.getAppointmentState() < 3)) {//签到签的取消流程

                appointmentMapper.updateStateById(new Byte("8"), appointmentId);
                List<Seekhouse> listSeekhouse = seekhouseMapper.getSeekhouseByAppointMentId(appointmentId);
                List<SeekhouseHistory> seekhouseHistories = new ArrayList<SeekhouseHistory>();
                for (Seekhouse seekhouse : listSeekhouse) {
                    SeekhouseHistory seekhouseHistory = new SeekhouseHistory();
                    seekhouseHistory.setAppointmentId(appointmentId);
                    seekhouseHistory.setHouseId(seekhouse.getHouseId());
                    seekhouseHistory.setState(new Byte("8"));
                    seekhouseHistories.add(seekhouseHistory);
                }
                if (seekhouseHistories.size() > 0)
                    seekhouseHistoryMapper.saveSeekhouseHistories(seekhouseHistories);

                seekhouseMapper.updateStateByAppointMentId(new Byte("8"), appointmentId);

            }
            else if (appointment != null && appointment.getAppointmentState() == 3) {//签到后的，

                seekhouseHistoryMapper.updateHouseHistoryStateByappointmentId(new Byte("6"), appointment.getId());//
                if (appointment.getUserCheckinTime() != null) {//分用户签到
                    appointmentMapper.updateStateById(AppointmentState.已到未看房.getValue(), appointmentId);
                }
                else {//和用户没签到
                    appointmentMapper.updateStateById(AppointmentState.已取消.getValue(), appointmentId);
                }
                seekhouseMapper.updateStateByAppointMentId(new Byte("8"), appointment.getId());
            }

        }

        //先删除来源非经济人推荐的房源表记录, 不包括已看记录
        seekhouseMapper.deleteSeekhouseNoRemcommand(houseId);
        //再删除来源是经纪人推荐的看房单记录。并存推荐历史表
        List<Seekhouse> listSeekhouse = seekhouseMapper.getSeekhouseRemcommand(houseId);
        for (Seekhouse s : listSeekhouse) {
            Recommend recommand = new Recommend();
            recommand.setAgentId(s.getAgentId());
            recommand.setCreateTime(s.getCreateTime());
            recommand.setHouseId(s.getHouseId());
            recommand.setStatus(new Byte("3"));//状态为已下架
            recommand.setUserId(s.getUserId());
            //添加房源历史表
            recommendMapper.insert(recommand);
            seekhouseMapper.deleteByPrimaryKey(s.getId());
        }
        //跟新房源历史表
        //   seekhouseHistoryMapper.updateHouseHistoryStateByHouseId(new Byte("9"), houseId);

        return true;
    }

    @Override
    public void deleteSeekHouseById(Long id) {

        seekhouseMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    public List<Seekhouse> getSeekhouseByAppointMentId(Long appointMentId) {

        return seekhouseMapper.getSeekhouseByAppointMentId(appointMentId);
    }

    @Override
    public Boolean addAppointment(String params, Long agentId) {

        JSONObject jsons = JSONObject.parseObject(params);
        Appointment appointment = new Appointment();
        appointment.setAgentId(agentId);
        appointment.setSeehouseNumber("xxsxnono");//TODO 待处理
        appointment.setAppointmentTime(jsons.getDate("appointmentTime"));
        try {
            appointment.setMeetAddress(URLDecoder.decode(jsons.getString("appointmentAddr"), "utf-8"));
            appointment.setMemo(URLDecoder.decode(jsons.getString("usermemo"), "utf-8"));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        appointment.setUserId(jsons.getLong("customerId"));
        appointment.setCreateTime(new Date());
        appointment.setAppointmentState(new Byte("1"));//约会状态待确认

        appointmentMapper.insert(appointment);

        StringTokenizer strTokenizer = new StringTokenizer(jsons.getString("houseIds"), ",", false);

        ArrayList<String> list = new ArrayList<String>(10);
        while (strTokenizer.hasMoreElements()) {//值根据id 更新它的状态 为待确认 
            list.add(strTokenizer.nextToken());
        }
        //值更新这个客户这个经纪人的记录
        seekhouseMapper.updateHousesStateByAppointMentId(agentId, jsons.getLong("customerId"), new Byte("2"),
                appointment.getId(), list);//约 看 状态待确认

        return true;

    }

    @Override
    public void finishSeekhouse(Long id, Long appointmentId, Byte seekfeeling, String memo, Long customerId) {

        seekhouseHistoryMapper.updateHouseHistoryStateById(id, new Byte("5"), seekfeeling, memo);
        Long seekhoueKey = seekhouseHistoryMapper.getseekhouseIdByKey(id);
        seekhouseMapper.updateHousesStateById(new Byte("5"), seekhoueKey, null);

        appointmentMapper.updateStateById(new Byte("6"), appointmentId);
        //已看房源数字段加1 ，把这个用户的状态该为老用户
        userService.updateUserType(customerId);
        userService.updateSawNumStatistics(customerId);

    }

    @Override
    public void nofinishSeekhouse(Long id, Byte nosawReason, String memo) {

        seekhouseHistoryMapper.updateHouseHistoryStateById(id, new Byte("6"), nosawReason, memo);
        Long seekhoueKey = seekhouseHistoryMapper.getseekhouseIdByKey(id);
        seekhouseMapper.updateHousesStateById(new Byte("6"), seekhoueKey, null);

    }

    @Override
    public void afterNofinishSeekhouse(Long id, Byte state, Byte selectValue, String memo) {
        seekhouseHistoryMapper.updateHouseHistoryStateById(id, new Byte("6"), selectValue, memo);
        Long seekhoueKey = seekhouseHistoryMapper.getseekhouseIdByKey(id);
        seekhouseMapper.updateHousesStateZeroById(state, seekhoueKey, memo);

    }

    @Override
    @Transactional(readOnly = true)
    public List<SeeHouseVo> getSeeHouseByAppointmentId(Long agentId, Long appointmentId) {

        return seekhouseMapper.getSeeHouseByAppointmentId(agentId, appointmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeeHouseVo> getSeeHouseHistoryByAppointmentId(Long agentId, Long appointmentId) {
        return seekhouseHistoryMapper.getSeeHouseHistoryByAppointmentId(agentId, appointmentId);
    }

    @Override
    public void addRecommand(String param) {
        //
        JSONObject jsons = JSONObject.parseObject(param);
        List<Seekhouse> list = new ArrayList<Seekhouse>();
        StringTokenizer strTokenizer = new StringTokenizer(jsons.getString("ids"), ",", false);

        //需要单条的处理，如果已在seekhouse中更新状态，否则insert
        while (strTokenizer.hasMoreElements()) {
            Long houseId = Long.valueOf(strTokenizer.nextToken());
            List<Long> unqiune = seekhouseMapper.getSeeHouseByUnqiune(jsons.getLong("agentId"),
                    jsons.getLong("customerId"), houseId);
            if (unqiune != null && unqiune.size() > 0) {//seekhouse表中已存在更新状态
                seekhouseMapper.updateHousesStateById(new Byte("1"), unqiune.get(0), null);
            }
            else {
                Seekhouse seekhouse = new Seekhouse();
                seekhouse.setAppointmentId(0L);
                seekhouse.setHouseId(houseId);
                seekhouse.setAgentId(jsons.getLong("agentId"));//sesion 中获取经纪人id
                seekhouse.setUserId(Long.valueOf(jsons.getString("customerId")));
                seekhouse.setState(new Byte("1"));//约看状态待预约 为1
                list.add(seekhouse);
                seekhouse.setRecommendSource(new Byte("3"));//1，是经纪人直接加待约会  
            }
        }
        seekhouseMapper.addSeekHouses(list);

    }

    @Override
    @Transactional(readOnly = true)
    public List<SeekhousePositionResponse> getSeekhousePositionList(Map<String, Object> map) {
        return seekhouseMapper.getSeekhousePositionList(map);
    }
}
