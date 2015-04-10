package com.manyi.hims.house.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.PageResponse;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseHistory;
import com.manyi.hims.entity.HouseHistory_;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceHistory;
import com.manyi.hims.house.controller.HouseRestController.HouseReq;
import com.manyi.hims.house.controller.HouseRestController.HouseRes;
import com.manyi.hims.util.EntityUtils.ActionTypeEnum;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;

@Service(value = "houseService")
@Scope(value = "singleton")

public class HouseServiceImpl extends BaseService implements HouseService {

	/**
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	@Override
	public PageResponse<HouseRes> houseList(HouseReq req) {
		String count_sql="SELECT COUNT(1) FROM House h join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ "join Area area on area.areaId = town.parentId left join HouseResource log on log.houseId = h.houseId   where log.status <> 4 and h.houseId <>0 ";
		String sql="SELECT h.houseId,area.areaId areaId , area.name areaName , town.areaId townId,town.name townName,sube.areaId estateId,"
				+ "sube.name estateName,sube.road road,h.building,h.room,log.houseState sellState ,log.publishDate sellPublishDate,"
				+ "log.houseState rentState,log.publishDate rentPublishDate,log.houseId logId ,log.publishDate logCreateTime,log.actionType logType ,log.status "
				+ "FROM House h join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ "join Area area on area.areaId = town.parentId left join HouseResource log on h.houseId = log.houseId "
				+ "where log.status <>4  and h.houseId <>0 ";
		List<Object> pars =new ArrayList<Object>();
		// 通过 区域 进行搜索 房源信息
		if (req.getAreaId() != 0) {
			if (req.getParentId() == 2) {
				// 按照行政区域搜索
				count_sql += " and area.areaId = ? ";
				sql += " and area.areaId = ? ";
				pars.add(req.getAreaId());
			} else {
				// 按照 行政区下面的 片区 进行搜索
				count_sql += " and town.areaId = ? ";
				sql += " and town.areaId = ? ";
				pars.add(req.getAreaId());
			}
		}

		//按照小区 名称 搜索
		if(StringUtils.isNotBlank(req.getEstateName())){
			count_sql += " and ( sube.name like ? or sube.road like ? ) ";
			sql += " and ( sube.name like ? or sube.road like ? ) ";
			pars.add("%"+req.getEstateName()+"%");
			pars.add("%"+req.getEstateName()+"%");
		}
		
		
		// 按照 出售状态搜索
		if (req.getSellState() != 0) {
			count_sql += " and log.houseState = ? ";
			sql += " and log.houseState = ? ";
			pars.add(req.getSellState());
		}
		
		// 审核状态
		if (!StringUtils.isBlank(req.getOperServiceState())) {
			count_sql += " and log.status in ( ";
			sql += " and log.status in ( ";
			String[] serviceStates = req.getOperServiceState().split(",");
			for (int i = 0; i < serviceStates.length; i++) {
				if (i != 0) {
					count_sql += " , ";
					sql += " , ";
				}
				count_sql += serviceStates[i];
				sql += serviceStates[i];
			}
			count_sql += " ) ";
			sql += " ) ";
		}
		
		Query count_query= this.getEntityManager().createNativeQuery(count_sql);
		Query query= this.getEntityManager().createNativeQuery(sql);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				count_query.setParameter(i + 1, pars.get(i));
				query.setParameter(i + 1, pars.get(i));
			}
		}
		
		List<BigInteger> counts = count_query.getResultList();
		int total = 0;
		if (counts != null && counts.size() > 0) {
			total = counts.get(0).intValue();
		}
		PageResponse<HouseRes> page = new PageResponse<HouseRes>();
		if (total > 0) {
			query.setFirstResult((req.getPage()-1)*req.getRows());//起始下标
			query.setMaxResults(req.getRows());//查询出来的数量/条数
			page.setPageSize(req.getRows());
			page.setCurrentPage(req.getPage());
			page.setTotal(total);
			int n = (total/req.getRows());
			page.setTotalPage(n);//总页数
			List<Object[]> objs = query.getResultList();
			if(objs != null && objs.size()>0){
				for (Object[] row : objs) {
					HouseRes house = new HouseRes();
					house.setHouseId(Integer.parseInt(row[0]+""));
					house.setAreaId(Integer.parseInt(row[1]+""));
					house.setAreaName(row[2]+"");
					house.setTownId(Integer.parseInt(row[3]+""));
					house.setTownName(row[4]+"");
					house.setEstateId(Integer.parseInt(row[5]+""));
					house.setEstateName(row[6] + (row[7] == null ? "" : row[7]+""));
					house.setBuiling(row[8]+"");
					house.setRoom(row[9]+"");
					String sellState = row[10] == null ? "0" : row[10]+"";//出售状态
					house.setSellState(Integer.parseInt(sellState));
					house.setSellStateStr(HouseStateEnum.getByValue(Integer.parseInt(sellState)).getDesc());
					house.setSellPublishDate((Date) row[11]);
					String rentState = row[12] == null ? "0" : row[12]+"";//出租状态
					house.setRentState(Integer.parseInt(rentState));
					house.setRentPublishDate((Date) row[13]);
					house.setLogId(Integer.parseInt(row[14] == null ? "" : row[14]+""));
					house.setLogCreateTime((Date) row[15]);
					house.setCheckType(row[16] == null ? "0" : row[16] + "");
					house.setCheckTypeStr(ActionTypeEnum.getByValue(Integer.parseInt(row[16] == null ? "0" : row[16] + "")).getDesc());
					house.setOperServiceState(row[17] == null ? 0 :Integer.parseInt(row[17]+""));
					
					if(page.getRows() == null){
						page.setRows(new ArrayList<HouseRes>());
					}
					page.getRows().add(house);
				}
			}else{
				page.setRows(new ArrayList<HouseRes>());
			}
		}else{
			page.setRows(new ArrayList<HouseRes>());
		}
		
		return page;
	}
	
	
	/**
	 * @date 2014年4月28日 下午5:30:12
	 * @author Tom  
	 * @description  
	 * 将House复制到History表中
	 */
	public void copyHouse2History(House house) {
		HouseHistory houseHistory = new HouseHistory();
		BeanUtils.copyProperties(house, houseHistory);
		this.getEntityManager().persist(houseHistory);
	}
	
	/**
	 * @date 2014年4月23日 下午12:25:07
	 * @author Tom  
	 * @description  
	 * 加载房屋信息（例如： 中远两湾城 - 22号 - 1102室）
	 */
	public String getHouseMsg(int houseId) {
		Residence residence = this.getEntityManager().find(Residence.class, houseId);
		Estate subEstate = this.getEntityManager().find(Estate.class, residence.getEstateId());
		
		return subEstate.getName() + " - " + residence.getBuilding() + "号 - " + residence.getRoom() + "室";
	}
	
	
	
    @Override
    public List<List<String>> houseModifications(int houseId) {
            CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
            CriteriaQuery<HouseHistory> query = builder.createQuery(HouseHistory.class);
            Root<HouseHistory> root = query.from(HouseHistory.class);
            query.select(root).where(builder.equal(root.get(HouseHistory_.houseId), houseId));
            List<HouseHistory> houseHistories = this.getEntityManager().createQuery(query).getResultList();
            List<List<String>> houseModifications = new ArrayList<List<String>>();
            if(houseHistories!=null && houseHistories.size()>0){
                    for(int i=0, n=houseHistories.size();i<n-1;i++){
                            HouseHistory before = houseHistories.get(i);
                            HouseHistory after = houseHistories.get(i+1);
                            if(before instanceof ResidenceHistory){
                                    ResidenceHistory rbefore = (ResidenceHistory)before;
                                    ResidenceHistory rafter = (ResidenceHistory)after;
                                    List<String> modifications = new ArrayList<String>();
                                    if(rbefore.getBedroomSum()!=rafter.getBedroomSum()){
                                            modifications.add("Bedroom change from: " + rbefore.getBedroomSum() + ", to:" + rafter.getBedroomSum());
                                    }
                                    if(rbefore.getLivingRoomSum()!=rafter.getLivingRoomSum()){
                                            modifications.add("Living root change from: " + rbefore.getBedroomSum() + ", to:" + rafter.getBedroomSum());
                                    }
                                    if(rbefore.getWcSum()!=rafter.getWcSum()){
                                            modifications.add("Wc num change from: " + rbefore.getBedroomSum() + ", to:" + rafter.getBedroomSum());
                                    }
                                    if(rbefore.getBalconySum()!=rafter.getBalconySum()){
                                            modifications.add("Balcony num change from: " + rbefore.getBedroomSum() + ", to:" + rafter.getBedroomSum());
                                    }
                                    if(modifications.size()>0){
                                            houseModifications.add(modifications);
                                    }
                            }
                    }
            }
            return houseModifications;
    }
	
}
