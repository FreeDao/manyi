package com.manyi.ihouse.entity;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 
 * Function: 看房单号流水记录
 *
 * @author   hubin
 * @Date	 2014年6月18日		下午6:24:00
 *
 * @see
 */
@Entity
@Table(name = "iw_sequence_num")
public class SequenceNum {
    
    @Id
    @GeneratedValue(strategy = TABLE, generator = "iwSequenceNum")
    @TableGenerator(name = "iwSequenceNum", allocationSize = 1)
    private long id;
    
    /**
     * 流水名称（日期字符串）
     */
    private String sq_name;
    
    /**
     * 流水号
     */
    private int serial;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSq_name() {
        return sq_name;
    }

    public void setSq_name(String sq_name) {
        this.sq_name = sq_name;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
    
    

}
