package com.manyi.ihouse.entity;

import static javax.persistence.GenerationType.TABLE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="iw_seekhouse_history")
public class SeekhouseHistory {
    /** GenerationType.IDENTITY  */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /** 房源id */
    @Column(name="house_id")
    private Long houseId;

    /** 约看状态 */
    private Integer state;

    /**  */
    private String memo;

    /**
     * 修改时间
     */
    private Date update_time; //
    
    /**
     * 创建时间
     */
    private Date create_time; 
    
    private String nosaw_reason;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="appointment_id")
    private Appointment appointment; //约会


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Long getHouseId() {
        return houseId;
    }


    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }


    public Integer getState() {
        return state;
    }


    public void setState(Integer state) {
        this.state = state;
    }


    public String getMemo() {
        return memo;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }


    public Date getUpdate_time() {
        return update_time;
    }


    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }


    public Date getCreate_time() {
        return create_time;
    }


    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }


    public String getNosaw_reason() {
        return nosaw_reason;
    }


    public void setNosaw_reason(String nosaw_reason) {
        this.nosaw_reason = nosaw_reason;
    }


    public Appointment getAppointment() {
        return appointment;
    }


    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    
}