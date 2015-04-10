package com.manyi.hims.rollpoling.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.Response;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.entity.HouseResource_;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceResourceHistory;
import com.manyi.hims.util.EntityUtils;

@Service (value="rollPolingService")
@Scope (value="singleton")
public class RollPolingServiceImpl extends BaseService implements RollPolingService{

	private final static int SELL_ROLL_POLING_PERIOD = 3600*24*10*1000; //10 days
	private final static int RENT_ROLL_POLING_PERIOD = 3600*24*4*1000; //4 days
	private final static int NUM_LIMIT_MAX = -1;

	@Override
	public Response check() {
		// Check HouseResource Table, HouseState Sell More Than 10 Days, HouseState Rent More Than 4 Days
		List<HouseResource> houseResources = this.getRoolPolingRequiredHouseResources(NUM_LIMIT_MAX);
		logger.info("house size,{}",(houseResources == null ? null : houseResources.size()));
		if(houseResources!=null && houseResources.size()>0){
			for(HouseResource houseResource : houseResources){
				this.addHouseResource2History(houseResource);
				this.addHouseResourceTemp(houseResource);
				houseResource.setActionType(EntityUtils.ActionTypeEnum.LOOP.getValue());
				houseResource.setStatus(EntityUtils.StatusEnum.ING.getValue());
				houseResource.setPublishDate(new Date());
				houseResource.setResultDate(null);
			}
		}
		return new Response(0, "成功");
	}
	
	private void addHouseResource2History(HouseResource houseResource){
		House house = this.getEntityManager().find(House.class, houseResource.getHouseId());
		if(house instanceof Residence){
			Residence residence = (Residence)house;
			ResidenceResourceHistory history = new ResidenceResourceHistory();
			history.setActionType(EntityUtils.ActionTypeEnum.LOOP.getValue());
			history.setBalconySum(residence.getBalconySum());
			history.setBedroomSum(residence.getBedroomSum());
			//history.setCheckNum(houseResource.getCheckNum());
			history.setCheckNum(0);
			history.setEntrustDate(houseResource.getEntrustDate());
			history.setGotPrice(houseResource.isGotPrice());
			history.setHouseId(houseResource.getHouseId());
			history.setHouseState(houseResource.getHouseState());
			history.setLivingRoomSum(residence.getLivingRoomSum());
			history.setOperatorId(0);
			history.setPublishDate(new Date());
			history.setRemark(houseResource.getRemark());
			history.setRentFreeDate(houseResource.getRentFreeDate());
			history.setRentPrice(houseResource.getRentPrice());
//			history.setResultDate(houseResource.getResultDate());
			history.setSellPrice(houseResource.getSellPrice());
			history.setStateReason(houseResource.getStateReason());
			history.setUserId(0);
			history.setWcSum(residence.getWcSum());
			history.setStatus(EntityUtils.StatusEnum.ING.getValue()/*houseResource.getStatus()*/);
			logger.info("loop persist history:{}", ReflectionToStringBuilder.toString(history));
			this.getEntityManager().persist(history);
		}
	}
	
	private List<HouseResource> getRoolPolingRequiredHouseResources(int numLimit){
		int rentPeriod = RENT_ROLL_POLING_PERIOD;
		int sellPeriod = SELL_ROLL_POLING_PERIOD;
		Properties properties = new Properties();
		String url = this.getClass().getResource("").getPath().replaceAll("%20", " ");
		String path = url.substring(0, url.indexOf("WEB-INF")) + "WEB-INF/rollpolingConfig.properties";
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
			properties.load(in);
			rentPeriod = Integer.parseInt(properties.getProperty("rent.period"));
			sellPeriod = Integer.parseInt(properties.getProperty("sell.period"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<HouseResource> query = builder.createQuery(HouseResource.class);
		Root<HouseResource> root = query.from(HouseResource.class);
		query.select(root).where(builder.and(
			builder.equal(root.get(HouseResource_.actionType), EntityUtils.ActionTypeEnum.PUBLISH.getValue()),
			builder.equal(root.get(HouseResource_.status), EntityUtils.StatusEnum.SUCCESS.getValue()),
			builder.or(
				builder.and(
						builder.lessThan(root.get(HouseResource_.resultDate), new Date(new Date().getTime()-sellPeriod/*SELL_ROLL_POLING_PERIOD*/)),
						builder.equal(root.get(HouseResource_.houseState), EntityUtils.HouseStateEnum.SELL.getValue())
				),
				builder.and(
					builder.lessThan(root.get(HouseResource_.resultDate), new Date(new Date().getTime()-rentPeriod/*RENT_ROLL_POLING_PERIOD*/)),
					builder.or(
						builder.equal(root.get(HouseResource_.houseState), EntityUtils.HouseStateEnum.RENT.getValue()),
						builder.equal(root.get(HouseResource_.houseState), EntityUtils.HouseStateEnum.RENTANDSELL.getValue())
					)
				)
			)
		)).orderBy(builder.desc(root.get(HouseResource_.resultDate)));
		List<HouseResource> houseResources;
		if(numLimit==NUM_LIMIT_MAX){
			houseResources = this.getEntityManager().createQuery(query).getResultList();
		}
		else{
			houseResources = this.getEntityManager().createQuery(query).setMaxResults(numLimit).getResultList();
		}
		
		if (houseResources != null && houseResources.size() > 0) {
			for (int i=0 ;i<houseResources.size();i++) {
				logger.info("loop{}:{}",i,ReflectionToStringBuilder.toString(houseResources.get(i)));
			}
		}
		
		
		return houseResources;
	}
	
	private void addHouseResourceTemp(HouseResource hr){
		HouseResourceTemp hrt = new HouseResourceTemp();
		Residence residence = getEntityManager().find(Residence.class,hr.getHouseId());
		BeanUtils.copyProperties(hr,hrt);
		hrt.setBedroomSum(residence.getBedroomSum());
		hrt.setBalconySum(residence.getBalconySum());
		hrt.setLivingRoomSum(residence.getLivingRoomSum());
		hrt.setSpaceArea(residence.getSpaceArea());
		hrt.setWcSum(residence.getWcSum());
		getEntityManager().persist(hrt);
	}
	
	@Override
	public Response overdue() {
		// TODO Auto-generated method stub
		return new Response(0, "成功");
	}

}
