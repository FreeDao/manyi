package com.manyi.iw.soa.appointment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manyi.iw.soa.appointment.model.AppointmentSearchRequest;
import com.manyi.iw.soa.appointment.model.AppointmentSearchResponse;
import com.manyi.iw.soa.appointment.model.UnprocessApmResponse;
import com.manyi.iw.soa.common.model.PageResponse;
import com.manyi.iw.soa.entity.Appointment;
import com.manyi.iw.soa.entity.Seekhouse;
import com.manyi.iw.soa.entity.SeekhouseHistory;
import com.manyi.iw.soa.mapper.AppointmentMapper;
import com.manyi.iw.soa.mapper.SeekhouseHistoryMapper;
import com.manyi.iw.soa.mapper.SeekhouseMapper;
import com.manyi.iw.soa.seekhouse.model.WillAppointMentVo;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentServiceI {

    @Autowired
    AppointmentMapper appointmentMapper;
    @Autowired
    SeekhouseMapper seekhouseMapper;
    @Autowired
    SeekhouseHistoryMapper seekhouseHistoryMapper;

    @Override
    public UnprocessApmResponse findUnprocessApm(Long agnetId) {

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WillAppointMentVo> getWillAppointment(Long houseId) {

        return appointmentMapper.getWillAppointment(houseId);
    }

    @Override
    public void changeTimeAppointment(Long appointmentId) {

        appointmentMapper.updateStateById(new Byte("7"), appointmentId);
        seekhouseMapper.updateStateByAppointMentId(new Byte("7"), appointmentId);

        List<Seekhouse> list = seekhouseMapper.getSeekhouseByAppointMentId(appointmentId);
        List<SeekhouseHistory> seekhouseHistories = new ArrayList<SeekhouseHistory>();

        for (Seekhouse seekhouse : list) {
            SeekhouseHistory seekhouseHistory = new SeekhouseHistory();
            seekhouseHistory.setAppointmentId(appointmentId);
            seekhouseHistory.setHouseId(seekhouse.getHouseId());
            seekhouseHistory.setState(new Byte("7"));
            seekhouseHistories.add(seekhouseHistory);
        }
        if (seekhouseHistories.size() > 0)
            seekhouseHistoryMapper.saveSeekhouseHistories(seekhouseHistories);
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        appointmentMapper.updateStateById(new Byte("8"), appointmentId);
        seekhouseMapper.updateStateByAppointMentId(new Byte("8"), appointmentId);

        List<Seekhouse> list = seekhouseMapper.getSeekhouseByAppointMentId(appointmentId);
        List<SeekhouseHistory> seekhouseHistories = new ArrayList<SeekhouseHistory>();

        for (Seekhouse seekhouse : list) {
            SeekhouseHistory seekhouseHistory = new SeekhouseHistory();
            seekhouseHistory.setAppointmentId(appointmentId);
            seekhouseHistory.setHouseId(seekhouse.getHouseId());
            seekhouseHistory.setState(new Byte("8"));
            seekhouseHistories.add(seekhouseHistory);
        }
        if (seekhouseHistories.size() > 0)
            seekhouseHistoryMapper.saveSeekhouseHistories(seekhouseHistories);
    }

    @Override
    public void confirmAppointment(Long appointmentId) {

        appointmentMapper.updateStateById(new Byte("2"), appointmentId);
        seekhouseMapper.updateStateByAppointMentIdOnly(new Byte("3"), appointmentId);
    }

    @Override
    public void agentCheckIn(Long appointmentId) {

        appointmentMapper.updateCheckinStateById(new Byte("3"), appointmentId, new Date(), new Date());

        List<Seekhouse> list = seekhouseMapper.getSeekhouseByAppointMentId(appointmentId);
        List<SeekhouseHistory> listhistory = new ArrayList<SeekhouseHistory>();
        for (Seekhouse seekhouse : list) {
            SeekhouseHistory seekhouseHistory = new SeekhouseHistory();
            seekhouseHistory.setAppointmentId(appointmentId);
            seekhouseHistory.setHouseId(seekhouse.getHouseId());
            seekhouseHistory.setState(new Byte("4"));
            listhistory.add(seekhouseHistory);
        }
        if (listhistory.size() > 0)
            seekhouseHistoryMapper.saveSeekhouseHistories(listhistory);

        seekhouseMapper.updateStateByAppointMentIdOnly(new Byte("4"), appointmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AppointmentSearchResponse> getAppointmentList(AppointmentSearchRequest appointmentSearch) {
        int total = appointmentMapper.getAppointmentListTotal(appointmentSearch);
        appointmentSearch.setTotal(total);
        return new PageResponse<>(total, appointmentMapper.getAppointmentList(appointmentSearch));
    }

    @Override
    @Transactional(readOnly = true)
    public Appointment getAppointment(Long id) {
        return appointmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void cancelAppointmentByCustomer(Long customerId) {
        //获取这个用户所有的约会未取消或改期的约会，状态签到前的调取消逻辑，状态签到后的，
        List<Appointment> list = appointmentMapper.getAppointmentInUseByCustomerId(customerId);
        for (Appointment appointment : list) {
            if (appointment.getAppointmentState() < 3) {//状态签到前的,取消逻辑
                List<Seekhouse> listSeekhouse = seekhouseMapper.getSeekhouseByAppointMentId(appointment.getId());
                List<SeekhouseHistory> seekhouseHistories = new ArrayList<SeekhouseHistory>();
                for (Seekhouse seekhouse : listSeekhouse) {
                    SeekhouseHistory seekhouseHistory = new SeekhouseHistory();
                    seekhouseHistory.setAppointmentId(appointment.getId());
                    seekhouseHistory.setHouseId(seekhouse.getHouseId());
                    seekhouseHistory.setState(new Byte("6"));//
                    seekhouseHistories.add(seekhouseHistory);
                }
                if (seekhouseHistories.size() > 0)
                    seekhouseHistoryMapper.saveSeekhouseHistories(seekhouseHistories);

                seekhouseMapper.updateStateByAppointMentId(new Byte("8"), appointment.getId());
                appointmentMapper.updateStateById(new Byte("8"), appointment.getId());
            }
            else if (appointment.getAppointmentState() == 3) {
                //签到后的,状态等于3的改为已取消，  状态大于3的不处理
                //  分为用户签到和用户未签到，根据用户签到字段判断,默认为null

                seekhouseHistoryMapper.updateHouseHistoryStateByappointmentId(new Byte("6"), appointment.getId());//

                if (appointment.getUserCheckinTime() != null) {//用户已签到 改为已到未看房5
                    appointmentMapper.updateStateById(new Byte("5"), appointment.getId());
                }
                else {///用户未签到 改为取消
                    appointmentMapper.updateStateById(new Byte("8"), appointment.getId());
                }

                seekhouseMapper.updateStateByAppointMentId(new Byte("8"), appointment.getId());

            }

        }

        //根据这个客户所有记录大于0的记录,但是不等于8即取消中的数据    //id 删除房源表中客户关联不在看房单的记录
        seekhouseMapper.deleteSeekhouseNoKFDByCustomerId(customerId);
    }

    @Override
    public void agentCheckInUserNo(Long appointmentId, Byte state) {

        //  存历史表，约会状态为未到未看房，房源状态在历史表中未未到未看房，在房源表表的的状态，更具具体后续state而定 

        appointmentMapper.updateCheckinStateById(new Byte("4"), appointmentId, new Date(), null);

        List<Seekhouse> list = seekhouseMapper.getSeekhouseByAppointMentId(appointmentId);
        List<SeekhouseHistory> listhistory = new ArrayList<SeekhouseHistory>();
        for (Seekhouse seekhouse : list) {
            SeekhouseHistory seekhouseHistory = new SeekhouseHistory();
            seekhouseHistory.setAppointmentId(appointmentId);
            seekhouseHistory.setHouseId(seekhouse.getHouseId());
            seekhouseHistory.setState(new Byte("6"));
            listhistory.add(seekhouseHistory);
        }
        if (listhistory.size() > 0)
            seekhouseHistoryMapper.saveSeekhouseHistories(listhistory);

        seekhouseMapper.updateStateByAppointMentId(state, appointmentId);

    }

    @Override
    public void updateStateById(Byte state, Long appointmentId) {
        appointmentMapper.updateStateById(state, appointmentId);
    }
}
