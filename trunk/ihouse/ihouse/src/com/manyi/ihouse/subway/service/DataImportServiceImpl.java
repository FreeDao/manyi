/**

 * 
 */
package com.manyi.ihouse.subway.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.entity.SubwayLine;
import com.manyi.ihouse.entity.SubwayLine_;
import com.manyi.ihouse.entity.SubwayPoint;
import com.manyi.ihouse.entity.SubwayStation;
import com.manyi.ihouse.util.Geohash;

/**
 * 地铁信息导入
 * @author berlinluo
 * 2014年6月18日
 */

@Service(value="dataImportServiceImpl")
public class DataImportServiceImpl extends BaseService implements DataImportService {
	
	
	//private final Long LAN_LON_MULT=10000_0000L; //经纬度保存到数据库时，需乘此值

	
	
	//通过解释过来的EXCEL行数据导入
	@Transactional(propagation=Propagation.REQUIRED)
	public int importSubwayInfoForOldTemplate(List<List<String>> infoList){
		if(infoList==null || infoList.size()<2){
			return 0;
		}
		
		
				
		int rowSize = infoList.size();
		//int midStation= (rowSize-1)/2 ; //线路中间站点位置
		Map<String,SubwayLine> subwayLineMap = new HashMap<String,SubwayLine>();
		
		//Map<String,Set<SubwayStation>> subwayMap = new HashMap<String,Set<SubwayStation>>();
		for(int i=1;i<rowSize;i++){    
			SubwayLine subwayLine = null;			
		
			boolean needLanAndLon =false;		
			
			String lineKey = infoList.get(i).get(0)+infoList.get(i).get(1);
			if(!subwayLineMap.containsKey(lineKey)){
				//newLineStart=i;
				subwayLine = new SubwayLine();				
				
				needLanAndLon=true;
				
				subwayLine.setLineCityCode(Integer.parseInt(infoList.get(i).get(0).trim()));//第一列城市
				subwayLine.setLineDesc(null);//描述暂定空
				subwayLine.setLineName(infoList.get(i).get(1).trim());//第二列地铁线名
				subwayLine.setLineStatus(Integer.parseInt(infoList.get(i).get(5).trim()) );//地铁状态(建成，在建)
				subwayLineMap.put(lineKey, subwayLine);
			}else{				
				subwayLine = subwayLineMap.get(lineKey);
			}
			
			/**
			//最后一条线
			if(i==rowSize-1){
				needLanAndLon = true;										
				newLineEnd = i;	
			}
			
			//每条线的中间站位置，作为线的经纬度位置
			if(needLanAndLon){
				needLanAndLon = false;				
				List<String> centerRow = infoList.get(newLineStart+(newLineEnd-1-newLineStart)/2) ;
				String centerRowKey = centerRow.get(0)+centerRow.get(1);
				SubwayLine subwayLineTemp = subwayLineMap.get(centerRowKey);
				String latAndLon =centerRow.get(4).trim(); //经度和纬度,第四列
				int splitCharIndex = latAndLon.indexOf(",");
				BigDecimal lineLat = (new BigDecimal(latAndLon.substring(0, splitCharIndex) )).multiply(new BigDecimal(100000000));
				BigDecimal linLon = (new BigDecimal(latAndLon.substring(splitCharIndex+1,latAndLon.length()) )).multiply(new BigDecimal(100000000));
				
				subwayLineTemp.setLineLat(lineLat.longValue());
				subwayLineTemp.setLineLon(linLon.longValue());	
				String geoHash= Geohash.encode(lineLat.doubleValue()/100000000, linLon.doubleValue()/100000000) ;
				subwayLineTemp.setLineGeoHash(geoHash);
				subwayLineMap.put(centerRowKey, subwayLineTemp);
				newLineStart = newLineEnd;
				
			}
			**/
			
			//开始地铁站信息
			SubwayStation subwayStation = new SubwayStation();
			//subwayStation.setSubwayLine(null);
			subwayStation.setStationDesc(null);
			String latAndLon =infoList.get(i).get(4).trim(); //经度和纬度,第四列
			int splitCharIndex = latAndLon.indexOf(",");
			String lat = latAndLon.substring(0, splitCharIndex) ;
			String lon = latAndLon.substring(splitCharIndex+1,latAndLon.length());
			
			subwayStation.setStationLat(lat);
			subwayStation.setStationLon(lon);
			subwayStation.setStationName(infoList.get(i).get(3).trim());
			subwayStation.setStationOrder(infoList.get(i).get(2).trim());
			subwayStation.setStationStatus(0);//默认0状态，在用
			subwayStation.setSubwayline(subwayLine);			
			String geoHash= Geohash.encode(lat, lon) ;
			subwayStation.setStationGeoHash(geoHash);
			
			subwayLine.getSubwayStations().add(subwayStation);
			
			
			
			if(needLanAndLon==true){//中间位置站点经纬度为线路的经纬度
				subwayLine.setLineLat(lat);
				subwayLine.setLineLon(lon);				
				subwayLine.setLineGeoHash(geoHash);
				needLanAndLon=false;
			}
			       	
       }
		
		//遍历所有地铁线，入库
		Set<Entry<String, SubwayLine>> subSet= subwayLineMap.entrySet();
		Iterator<Entry<String, SubwayLine>> ite = subSet.iterator();		
		while(ite.hasNext()){
			Entry<String, SubwayLine>  entry= ite.next();
			SubwayLine line = entry.getValue();
			System.out.println("SubwayLine hash="+line.getLineGeoHash());
			this.getEntityManager().persist(line);
			this.getEntityManager().flush();
		}
		
				
		return rowSize-1;
		
	}
	
	
	

	//通过解释过来的EXCEL行数据导入地铁线数据
	@Transactional(propagation=Propagation.REQUIRED)
	public int importSubwayLine(List<List<String>> infoList){
		//没有要导入的数据(第一行为标题)
		if(infoList==null || infoList.size()<2){
			return 0;
		}
		
		int count = 0; //导入计数
		int rowSize = infoList.size();		
		for(int i=1;i<rowSize;i++){   
			Integer cityCode = Integer.parseInt(infoList.get(i).get(0).trim());
			String	lineName = 	infoList.get(i).get(1).trim();			
			EntityManager em = getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			// 构造一个CriteriaQuery对象
			CriteriaQuery<SubwayLine> cq = cb.createQuery(SubwayLine.class);

			Root<SubwayLine> root = cq.from(SubwayLine.class);
			cq.select(root);

			// 城市编码，线路名称条件
			Expression<Boolean> expwhere = cb.and(
					cb.equal(root.get(SubwayLine_.lineCityCode), cityCode),
					cb.equal(root.get(SubwayLine_.lineName), lineName));
			cq.where(expwhere);
			TypedQuery<SubwayLine> query = em.createQuery(cq);
			List<SubwayLine> list = query.getResultList();

			if (list.size() > 0) {//防止重复的地铁线路
				continue;
			}
			
			
			
			SubwayLine subwayLine = new SubwayLine();	
			subwayLine.setLineCityCode(Integer.parseInt(infoList.get(i).get(0).trim()));//第一列城市
			subwayLine.setLineDesc(null);//描述暂定空
			subwayLine.setLineName(infoList.get(i).get(1).trim());//第二列地铁线名
			subwayLine.setLineStatus(Integer.parseInt(infoList.get(i).get(4).trim()) );//地铁状态(建成，在建)
			String lon = infoList.get(i).get(2).trim();//经度第3列
			String lat = infoList.get(i).get(3).trim();//纬度第4列
			
			System.out.println("lat and lon="+lat+" lon="+lon);
			
			subwayLine.setLineLat(lat);
			subwayLine.setLineLon(lon);			
			String geoHash= Geohash.encode(lat, lon) ;
			subwayLine.setLineGeoHash(geoHash);
			this.getEntityManager().persist(subwayLine);
			this.getEntityManager().flush();
			count++;
			
		}
		
		return count;		
	}
	
	
	//导入地铁站信息
	@Transactional(propagation=Propagation.REQUIRED)
	public int importSubwayStation(List<List<String>> infoList){
		//没有要导入的数据(第一行为标题)
		if(infoList==null || infoList.size()<2){
			return 0;
		}
		
		int count = 0; //导入计数
		int rowSize = infoList.size();		
		Map<String,SubwayLine> subwayLineMap = new HashMap<String,SubwayLine>();
		for(int i=1;i<rowSize;i++){   
			SubwayLine subwayLine = null;
			Integer cityCode = Integer.parseInt(infoList.get(i).get(0).trim());
			String	lineName = 	infoList.get(i).get(1).trim();
			String lineKey = cityCode.intValue()+lineName;
			
			if(subwayLineMap.containsKey(lineKey)){//已经查询出来过的地铁线
				subwayLine = subwayLineMap.get(lineKey);
			}else{//否则从数据库再取
				EntityManager em = getEntityManager();
				CriteriaBuilder cb = em.getCriteriaBuilder();
				// 构造一个CriteriaQuery对象
				CriteriaQuery<SubwayLine> cq = cb.createQuery(SubwayLine.class);

				Root<SubwayLine> root = cq.from(SubwayLine.class);
				cq.select(root);

				// 城市编码，线路名称条件
				Expression<Boolean> expwhere = cb.and(
						cb.equal(root.get(SubwayLine_.lineCityCode), cityCode),
						cb.equal(root.get(SubwayLine_.lineName), lineName));
				cq.where(expwhere);
				TypedQuery<SubwayLine> query = em.createQuery(cq);
				List<SubwayLine> list = query.getResultList();

				if (list.size() > 0) {
					subwayLine = list.get(0);
					subwayLineMap.put(cityCode.intValue() + lineName,
							subwayLine);
				}
			}
						 
			
			SubwayStation subwayStation = new SubwayStation();			
			subwayStation.setStationDesc(null);
			String lon = infoList.get(i).get(4).trim();//经度第3列
			String lat = infoList.get(i).get(5).trim();//纬度第4列
						
			subwayStation.setStationLat(lat);
			subwayStation.setStationLon(lon);
			subwayStation.setStationName(infoList.get(i).get(3).trim());
			subwayStation.setStationOrder(infoList.get(i).get(2).trim());
			subwayStation.setStationStatus(Integer.parseInt(infoList.get(i).get(6).trim()) );//站点状态
			subwayStation.setSubwayline(subwayLine);	
			String geoHash= Geohash.encode(lat, lon) ;
			subwayStation.setStationGeoHash(geoHash);
			this.getEntityManager().persist(subwayStation);
			this.getEntityManager().flush();
			count++;
			
		}
		
		return count;
		
	}
	

	//导入地铁拐点信息	
	@Transactional(propagation=Propagation.REQUIRED)
	public int importSubwayPoint(List<List<String>> infoList,String lineNameArg, int cityCodeArg){
		//没有要导入的数据(第一行为标题)
		if(infoList==null || infoList.size()<2){
			return 0;
		}
		
		int count = 0; //导入计数
		int rowSize = infoList.size();	
		int cityCode = cityCodeArg;
		String	lineName = lineNameArg;
		SubwayLine subwayLine = null;
		
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		// 构造一个CriteriaQuery对象
		CriteriaQuery<SubwayLine> cq = cb.createQuery(SubwayLine.class);
		Root<SubwayLine> root = cq.from(SubwayLine.class);
		cq.select(root);

		// 城市编码，线路名称条件
		Expression<Boolean> expwhere = cb.and(
				cb.equal(root.get(SubwayLine_.lineCityCode), cityCode),
				cb.equal(root.get(SubwayLine_.lineName), lineName));
		cq.where(expwhere);
		TypedQuery<SubwayLine> query = em.createQuery(cq);
		List<SubwayLine> list = query.getResultList();

		//先有地铁信息，再导入拐点信息
		if (list==null ||list.size() == 0) {
			return 0;
		}else{
			subwayLine = list.get(0);
		}
		
		for(int i=1;i<rowSize;i++){  	
			SubwayPoint subwayPoint = new SubwayPoint();			
			subwayPoint.setPointDesc(null);
			String lon = infoList.get(i).get(0).trim();//经度第3列
			String lat = infoList.get(i).get(1).trim();//纬度第4列
						
			subwayPoint.setPointLat(lat);
			subwayPoint.setPointLon(lon);
			subwayPoint.setPointOrder(i);//Integer.parseInt(infoList.get(i).get(0).trim()) 实际数据跟行的顺序对应
			subwayPoint.setPointStatus(1);//站点状态
			subwayPoint.setSubwayLine(subwayLine);	
			String geoHash= Geohash.encode(lat, lon) ;
			subwayPoint.setPointGeoHash(geoHash);
			this.getEntityManager().persist(subwayPoint);
			this.getEntityManager().flush();
			count++;			
		}
		
		return count;
		
	}
	
	
	

}
