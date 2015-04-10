/**
 * 
 */
package com.ecpss.mp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.Global;
import com.ecpss.mp.PageResponse;
import com.ecpss.mp.Response;
import com.ecpss.mp.RestController;
import com.ecpss.mp.entity.Residence;
import com.ecpss.mp.uc.service.ResidenceService;
import com.leo.common.Page;
import com.leo.common.beanutil.BeanUtils;

/**
 * @author lei
 * 
 */
@Controller
@RequestMapping("/residence")
@SessionAttributes(Global.SESSION_UID_KEY)
public class ResidenceRestController extends RestController {
	private ResidenceService residenceService;

	public ResidenceService getResidenceService() {
		return residenceService;
	}

	@Autowired
	@Qualifier("residenceService")
	public void setResidenceService(ResidenceService residenceService) {
		this.residenceService = residenceService;
	}

	@RequestMapping(value = "/view.rest", produces = "application/json")
	@ResponseBody
	public Response view(String id) {
		//final Residence residence = getResidenceService().getEntityById(id);
		Response result = new Response() {

		};

		return result;
	}

	
	@RequestMapping(value = "/list.rest", produces = "application/json")
	@ResponseBody
	public PageResponse<Residence> residence_list2(HttpSession session, ListParams pars) {
		final Page<Residence> residencePage= getResidenceService().getEntityByPage(pars.residence,pars.page, pars.rows,pars.sort,pars.order);
		return new PageResponse<Residence>(){
			{
				
				setRows(new ArrayList<Residence>(residencePage.getRows().size()));
				setTotal(residencePage.getTotal());
				setTotalPage(residencePage.getTotalPage());
//				for(Residence c :residencePage.getRows()){
//					Residence residence = new Residence();
//					BeanUtils.copyProperties(residence,c);
//					getRows().add(residence);
//					
//				}
				getRows().addAll(residencePage.getRows());
			}
		};
	}
	
	@RequestMapping(value = "/list3.rest", produces = "application/json")
	@ResponseBody
	public PageResponse<ResidenceInfo> residence_list3(HttpSession session, ListParams pars) {
		final Page<ResidenceInfo> residencePage= getResidenceService().getResidencesInfoByPage(pars.residence,pars.page, pars.rows,pars.sort,pars.order);
		return new PageResponse<ResidenceInfo>(){
			{
				
				setTotal(residencePage.getTotal());
				setTotalPage(residencePage.getTotalPage());
				if(residencePage.getRows() != null){
					setRows(new ArrayList<ResidenceInfo>(residencePage.getRows().size()));
					for(ResidenceInfo c :residencePage.getRows()){
						ResidenceInfo residence = new ResidenceInfo();
						BeanUtils.copyProperties(residence,c);
						getRows().add(residence);
						
					}
				}else{
					setRows(new ArrayList<ResidenceInfo>(0));
				}
			}
		};
	}
	
	
	public static class ListParams {
		private ResidenceInfo residence; 
		private Integer page; 
		private Integer rows;
		private String sort;
		private String order;
		
		public ResidenceInfo getResidence() {
			return residence;
		}
		public void setResidence(ResidenceInfo residence) {
			this.residence = residence;
		}
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}
		public Integer getRows() {
			return rows;
		}
		public void setRows(Integer rows) {
			this.rows = rows;
		}
		public String getSort() {
			return sort;
		}
		public void setSort(String sort) {
			this.sort = sort;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String ordre) {
			this.order = ordre;
		}

	}
	
	/**
	 * 列表所需的 字段值
	 * @author dev
	 *
	 */
	public static class ResidenceInfo extends Response{
		
		private int hid; // 对象id
		private String building;// 楼座编号（例如：22栋，22坐，22号）
		private int floor;// 楼层
		private String room;// 房号（例如：1304室，1004－1008室等）
		private int layers;// 总层高
		private BigDecimal coveredArea;// 建筑面积
		private BigDecimal spaceArea;// 内空面积
		private int bedroomSum;// 几房
		private int livingRoomSum;// 几厅
		private int wcSum;// 几卫
		private int balconySum;// 几阳台
		
		private int directionId ;  //房屋朝向id
		private int  estateId ;  //小区id
		private int  mainOwnerId ;  //业务id
		private int  rightId ;  //产权id
		private int  typeId ; //房屋类型id
		private int areaId; //区域id
		private int statusId;//状态id
		private int useageId;//用途id
		private String areaType; // 地址类型(area_city, area_province, area_town, area_zone)
		
		private String directionTitle ;  //房屋朝向 title
		private String  estateTitle ; //小区 名称
		private String  mainOwnerTitle ;  //业务真实名称
		private String  rightTitle ;  //产权 名称
		private String  typeTitle ; //房屋类型
		private String areaTitle;//区域名称
		private String statusTitle; //状态 名称
		private int useageTitle;//用途
		
		
		private String remark;
		private String ownerName;
		private String ownerTel;
		private String ownerMobile;
		private String id;
		
		public String getOwnerMobile() {
			return ownerMobile;
		}
		public void setOwnerMobile(String ownerMobile) {
			this.ownerMobile = ownerMobile;
		}
		
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getOwnerName() {
			return ownerName;
		}
		public void setOwnerName(String ownerName) {
			this.ownerName = ownerName;
		}
		public String getOwnerTel() {
			return ownerTel;
		}
		public void setOwnerTel(String ownerTel) {
			this.ownerTel = ownerTel;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAreaType() {
			return areaType;
		}
		public void setAreaType(String areaType) {
			this.areaType = areaType;
		}
		public int getUseageId() {
			return useageId;
		}
		public void setUseageId(int useageId) {
			this.useageId = useageId;
		}
		public int getUseageTitle() {
			return useageTitle;
		}
		public void setUseageTitle(int useageTitle) {
			this.useageTitle = useageTitle;
		}
		public int getAreaId() {
			return areaId;
		}
		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}
		public String getAreaTitle() {
			return areaTitle;
		}
		public void setAreaTitle(String areaTitle) {
			this.areaTitle = areaTitle;
		}
		public int getHid() {
			return hid;
		}
		public void setHid(int hid) {
			this.hid = hid;
		}
		public String getBuilding() {
			return building;
		}
		public void setBuilding(String building) {
			this.building = building;
		}
		public int getStatusId() {
			return statusId;
		}
		public void setStatusId(int statusId) {
			this.statusId = statusId;
		}
		public String getStatusTitle() {
			return statusTitle;
		}
		public void setStatusTitle(String statusTitle) {
			this.statusTitle = statusTitle;
		}
		public int getFloor() {
			return floor;
		}
		public void setFloor(int floor) {
			this.floor = floor;
		}
		public String getRoom() {
			return room;
		}
		public void setRoom(String room) {
			this.room = room;
		}
		public int getLayers() {
			return layers;
		}
		public void setLayers(int layers) {
			this.layers = layers;
		}
		public BigDecimal getCoveredArea() {
			return coveredArea;
		}
		public void setCoveredArea(BigDecimal coveredArea) {
			this.coveredArea = coveredArea;
		}
		public BigDecimal getSpaceArea() {
			return spaceArea;
		}
		public void setSpaceArea(BigDecimal spaceArea) {
			this.spaceArea = spaceArea;
		}
		public int getBedroomSum() {
			return bedroomSum;
		}
		public void setBedroomSum(int bedroomSum) {
			this.bedroomSum = bedroomSum;
		}
		public int getLivingRoomSum() {
			return livingRoomSum;
		}
		public void setLivingRoomSum(int livingRoomSum) {
			this.livingRoomSum = livingRoomSum;
		}
		public int getWcSum() {
			return wcSum;
		}
		public void setWcSum(int wcSum) {
			this.wcSum = wcSum;
		}
		public int getBalconySum() {
			return balconySum;
		}
		public void setBalconySum(int balconySum) {
			this.balconySum = balconySum;
		}
		public int getDirectionId() {
			return directionId;
		}
		public void setDirectionId(int directionId) {
			this.directionId = directionId;
		}
		public int getEstateId() {
			return estateId;
		}
		public void setEstateId(int estateId) {
			this.estateId = estateId;
		}
		public int getMainOwnerId() {
			return mainOwnerId;
		}
		public void setMainOwnerId(int mainOwnerId) {
			this.mainOwnerId = mainOwnerId;
		}
		public int getRightId() {
			return rightId;
		}
		public void setRightId(int rightId) {
			this.rightId = rightId;
		}
		public int getTypeId() {
			return typeId;
		}
		public void setTypeId(int typeId) {
			this.typeId = typeId;
		}
		public String getDirectionTitle() {
			return directionTitle;
		}
		public void setDirectionTitle(String directionTitle) {
			this.directionTitle = directionTitle;
		}
		public String getEstateTitle() {
			return estateTitle;
		}
		public void setEstateTitle(String estateTitle) {
			this.estateTitle = estateTitle;
		}
		public String getMainOwnerTitle() {
			return mainOwnerTitle;
		}
		public void setMainOwnerTitle(String mainOwnerTitle) {
			this.mainOwnerTitle = mainOwnerTitle;
		}
		public String getRightTitle() {
			return rightTitle;
		}
		public void setRightTitle(String rightTitle) {
			this.rightTitle = rightTitle;
		}
		public String getTypeTitle() {
			return typeTitle;
		}
		public void setTypeTitle(String typeTitle) {
			this.typeTitle = typeTitle;
		}

		
	}
	

}
