package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: UserTask
 * 
 */
/**
 * @author tom
 *
 */
@Data
@Entity
@Table(indexes={@Index(name="houseId", columnList = "houseId"), @Index(name="userId", columnList = "userId")})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)

public class UserTask implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(columnDefinition = "BIGINT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "UserTask")
	@TableGenerator(name = "UserTask", allocationSize = 1)
	private int id;
	private int houseId;
	private int userId;  //中介、经纪人 id
	private int operatorId;  //审核人员id 
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date createTime; //领取任务时间、创建任务时间
	private Date uploadPhotoTime; //上传图片时间
	private Date finishDate; // 审核完成时间（成功、失败、过期）
	
	private String longitude;// 坐标 经度
	private String latitude;// 坐标 纬度
	private int taskStatus; // 1已领取(未完成), 2审核中（提交图片完成）, 3审核成功,4审核失败,5过期
	private String note; // 任务失败原因
	private int picNum; // 任务照片数目
	
	private int afterBedroomSum;	// 修改后的	几房
	private int afterLivingRoomSum;	// 修改后的	几厅
	private int afterWcSum;			// 修改后的	几卫
	private int afterDecorateType; //修改后的(中介上传的：1 毛坯，2 装修,) (callcenter 审核的： 1 毛坯 ,3简装,4精装,5豪装)
	
	@Column(scale = 2, precision = 7)
	private int layers; // 总层高
	private String taskImgStr; // 任务图片统计字段
	private String houseTypeStr; // 房型变化字段
	private int aliyunOSSAgain; // 上传图片失败，需要再次上传图片标识 1 需要， 0 不需要


}
